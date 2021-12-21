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
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.entities.Proyecto;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoBorrado extends Command {

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
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // URL > ID Objeto
          int id = Integer.parseInt(request.getParameter("id"));

          // request > ID Operación
          String op = request.getParameter("op");

          // ID Entidad + BD > JSP Modificación
          if (op == null || op.equals("captura")) {
            // ID Entidad + BD > Entidad
            Proyecto proyecto = proyectoDAL.obtenerProyecto(id);

            // Enlaza Datos > JSP
            request.setAttribute("proyecto", proyecto);

            // Confirmar Borrado
            page = "proyectos/proyecto-borrado";
          } else if (op.equals("proceso")) {
            // ID > Registro Borrado - true | false
            boolean checkOK = proyectoDAL.borrarProyecto(id);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Datos borrados correctamente";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=proyecto-listado";

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

    // Redirección JSP
    forward(page);
  }
}
