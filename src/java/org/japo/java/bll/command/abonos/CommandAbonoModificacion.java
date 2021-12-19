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
public final class CommandAbonoModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Entidad
    EntityAbono abonoIni;

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
        // request > ID Entidad
        int id = Integer.parseInt(request.getParameter("id"));

        // request > ID Operación
        String op = request.getParameter("op");

        // ID Entidad + BD > JSP Modificación
        if (op == null || op.equals("captura")) {
          // ID > Objeto Entidad
          abonoIni = abonoDAL.obtenerAbono(id);

          // Session > EntityUsuario
          EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

          // EntityUsuario > Pefil
          int perfil = usuario.getPerfilID();

          // BD > Lista de Usuarios
          List<EntityUsuario> usuarios;
          switch (perfil) {
            case EntityPerfil.BASIC:
              // Sólo EntityUsuario Actual > Lista de Usuarios
              usuarios = new ArrayList<>(Arrays.asList(usuario));
              break;
            case EntityPerfil.ADMIN:
            case EntityPerfil.DEVEL:
              // Todos los Usuarios > Lista de Usuarios
              usuarios = usuarioDAL.obtenerUsuarios();
              break;
            default:
              // Lista de Usuarios - Vacía
              usuarios = new ArrayList<>();
          }

          // BD > Lista de Proyectos
          List<EntityProyecto> proyectos = proyectoDAL.obtenerProyectos();

          // Inyectar Datos > JSP
          request.setAttribute("abono", abonoIni);
          request.setAttribute("proyectos", proyectos);
          request.setAttribute("usuarios", usuarios);

          // JSP
          page = "abonos/abono-modificacion";
        } else if (op.equals("proceso")) {
          // ID > Objeto Entidad
          abonoIni = abonoDAL.obtenerAbono(id);

          // Request > Parámetros
          int proyecto = Integer.parseInt(request.getParameter("proyecto").trim());
          int usuario = Integer.parseInt(request.getParameter("usuario").trim());
          String info = request.getParameter("info").trim();

          // Parámetros > Entidad
          EntityAbono abonoFin = new EntityAbono(id, proyecto, usuario, info,
                  abonoIni.getStatus(), abonoIni.getData(),
                  abonoIni.getCreatedAt(), abonoIni.getUpdatedAt());

          // Entidad > Modificación Registro BD
          boolean procesoOK = abonoDAL.modificarAbono(abonoFin);

          // Validar Operación
          page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO Disponible
          page = "errors/page404";
        }
      } else {
        // Acceso NO Autorizado
        page = "errors/acceso-denegado";
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Recurso NO Disponible
      page = "errors/page404";
    }

    // Redirección JSP
    forward(page);
  }
}
