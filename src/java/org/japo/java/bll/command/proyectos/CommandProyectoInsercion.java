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
package org.japo.java.bll.command.proyectos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.CuotaDAL;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.EntityCuota;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    UsuarioDAL usuarioDAL = new UsuarioDAL();
    CuotaDAL cuotaDAL = new CuotaDAL();
    ProyectoDAL proyectoDAL = new ProyectoDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Usuario Actual
        EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

        // Obtener Operación
        String op = request.getParameter("op");

        // Invoca Formulario de Captura de Datos
        if (op == null || op.equals("captura")) {
          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado();
          pl.setUser(usuario);
          
          // Lista de Usuarios - Dependiendo del perfil
          List<EntityUsuario> usuarios = usuarioDAL.obtenerUsuarios(pl);
          
          // Lista de Cuotas
          List<EntityCuota> cuotas = cuotaDAL.obtenerCuotas();

          // Inyección de Datos
          request.setAttribute("usuarios", usuarios);
          request.setAttribute("cuotas", cuotas);

          // JSP
          page = "proyectos/proyecto-insercion";
        } else if (op.equals("proceso")) {
          // Request > Parámetros
          String nombre = request.getParameter("nombre").trim();
          String info = request.getParameter("info").trim();
          String icono = request.getParameter("icono").trim();

          int propietario;
          if (usuario.getPerfilID() == EntityPerfil.DEVEL || usuario.getPerfilID() == EntityPerfil.ADMIN) {
            propietario = Integer.parseInt(request.getParameter("propietario").trim());
          } else {
            propietario = usuario.getId();
          }
          int cuota = Integer.parseInt(request.getParameter("cuota").trim());

          // Parámetros > Entidad
          EntityProyecto proyecto = new EntityProyecto(nombre, info, icono,
                  propietario, cuota);

          // Entidad > Inserción BD - true | false
          boolean procesoOK = proyectoDAL.insertarProyecto(proyecto);

          // Validar Proceso
          page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO disponible
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

    // Redirección JSP
    forward(page);
  }
}
