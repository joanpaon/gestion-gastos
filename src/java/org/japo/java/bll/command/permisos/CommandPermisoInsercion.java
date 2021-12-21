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
package org.japo.java.bll.command.permisos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.PerfilDAL;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPermisoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        PerfilDAL perfilDAL = new PerfilDAL(sesion);
        PermisoDAL permisoDAL = new PermisoDAL(sesion);
        ProcesoDAL procesoDAL = new ProcesoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // Obtener Operación
          String op = request.getParameter("op");

          // Formulario Captura Datos
          if (op == null || op.equals("captura")) {
            // BD > Lista de Procesos
            List<Proceso> procesos = procesoDAL.obtenerProcesos();

            // BD > Lista de Perfiles
            List<Perfil> perfiles = perfilDAL.obtenerPerfiles();

            // Inyecta Datos > JSP
            request.setAttribute("procesos", procesos);
            request.setAttribute("perfiles", perfiles);

            // Nombre JSP
            page = "permisos/permiso-insercion";
          } else if (op.equals("proceso")) {
            // Request > Parámetros
            int proceso = Integer.parseInt(request.getParameter("proceso"));
            int perfil = Integer.parseInt(request.getParameter("perfil"));
            String info = request.getParameter("info");

            // Parámetros > Entidad
            Permiso permiso = new Permiso(proceso, perfil, info);

            // Entidad > Inserción BD - true | false
            boolean checkOK = permisoDAL.insertarPermiso(permiso);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Datos insertados correctamente";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=permiso-listado";

              // Inyeccion de Parámetros
              parametrizarMensaje(titulo, mensaje, imagen, destino);
            } else {
              seleccionarMensaje(MSG_OPERACION_CANCELADA);
            }
          } else {
            seleccionarMensaje(MSG_ERROR404);
          }
        } else {
          seleccionarMensaje(MSG_ACCESO_DENEGADO);
        }
      }
    } catch (NumberFormatException | NullPointerException e) {
      seleccionarMensaje(MSG_ERROR404);
    }

    // Command > JSP
    forward(page);
  }
}
