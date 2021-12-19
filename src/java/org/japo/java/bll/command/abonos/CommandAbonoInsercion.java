/* 
 * Copyright 2021 José A. Pacheco Ondoño - japolabs@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.bll.command.abonos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.EntityAbono;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // Nombre JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    AbonoDAL abonoDAL = new AbonoDAL();
    ProyectoDAL proyectoDAL = new ProyectoDAL();
    UsuarioDAL usuarioDAL = new UsuarioDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Obtener Operación
        String op = request.getParameter("op");

        // Formulario Captura Datos
        if (op == null || op.equals("captura")) {
          // BD > Lista de Proyectos
          List<EntityProyecto> proyectos = proyectoDAL.obtenerProyectos();

          // BD > Lista de Usuarios
          List<EntityUsuario> usuarios;

          // Sesión > EntityUsuario
          EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

          // EntityUsuario > EntityPerfil
          int perfil = usuario.getPerfilID();

          // Discriminar EntityUsuario
          switch (perfil) {
            case EntityPerfil.BASIC:
              // Lista de Usuarios - Vacía
              usuarios = new ArrayList<>(Arrays.asList(usuario));
              break;
            case EntityPerfil.ADMIN:
            case EntityPerfil.DEVEL:
              // Lista de Usuarios - Todos
              usuarios = usuarioDAL.obtenerUsuarios();
              break;
            default:
              // Lista de Usuarios - Vacía
              usuarios = new ArrayList<>();
          }

          // Inyecta Datos > JSP
          request.setAttribute("proyectos", proyectos);
          request.setAttribute("usuarios", usuarios);

          // Nombre JSP
          page = "abonos/abono-insercion";
        } else if (op.equals("proceso")) {
          // Request > Parámetros
          int proyecto = Integer.parseInt(request.getParameter("proyecto").trim());
          int usuario = Integer.parseInt(request.getParameter("usuario").trim());
          String info = request.getParameter("info").trim();

          // Parámetros > Entidad
          EntityAbono abono = new EntityAbono(0, proyecto, usuario, info, 0, null, null, null);

          // Entidad > Inserción BD - true | false
          boolean operacionOK = abonoDAL.insertarAbono(abono);

          // Validar Inserción BD
          page = operacionOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO Disponible
          page = "errors/page404";
        }
      } else {
        // Acceso NO Autorizado
        page = "errors/acceso-denegado";
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Recurso NO disponible
      page = "errors/page404";
    }

    // Command > JSP
    forward(page);
  }
}
