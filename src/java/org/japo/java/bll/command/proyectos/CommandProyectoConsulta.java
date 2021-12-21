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
public final class CommandProyectoConsulta extends Command {

  @Override
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
          // Request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // ID Entidad > Entidad
          Proyecto proyecto = proyectoDAL.obtenerProyecto(id);

          // Inyecta Datos > JSP
          request.setAttribute("proyecto", proyecto);

          // Nombre JSP
          page = "proyectos/proyecto-consulta";
        } else {
          seleccionarMensaje(MSG_ACCESO_DENEGADO);
        }
      }
    } catch (NumberFormatException | NullPointerException e) {
      seleccionarMensaje(MSG_ERROR404);
    }

    // Redirección
    forward(page);
  }
}
