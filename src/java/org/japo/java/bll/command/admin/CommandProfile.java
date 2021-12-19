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
package org.japo.java.bll.command.admin;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProfile extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Entidad
    EntityUsuario usuarioIni;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Datos
    UsuarioDAL usuarioDAL = new UsuarioDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else {
        // Obtener ID EntityUsuario
        int id = Integer.parseInt(request.getParameter("id"));

        // Obtener Operación
        String op = request.getParameter("op");

        if (op == null || op.equals("captura")) {
          // ID > Entidad a Modificar
          usuarioIni = usuarioDAL.obtenerUsuario(id);

          // Inyectar Datos > JSP
          request.setAttribute("usuario", usuarioIni);

          // JSP
          page = "admin/profile";
        } else if (op.equals("proceso")) {
          // ID > Entidad a Modificar
          usuarioIni = usuarioDAL.obtenerUsuario(id);

          // Request > Parámetros
          String user = request.getParameter("user").trim();
          String pass = request.getParameter("pass").trim();
          String email = request.getParameter("email").trim();
          String icono = request.getParameter("icono").trim();
          int perfilID = Integer.parseInt(request.getParameter("perfil").trim());
          String info = request.getParameter("info").trim();

          // Parámetros > Entidad
          EntityUsuario usuarioFin = new EntityUsuario(id, user, pass, email,
                  icono, perfilID, info,
                  usuarioIni.getStatus(), usuarioIni.getData(),
                  usuarioIni.getCreatedAt(), usuarioIni.getUpdatedAt());
          
          // Ejecutar Operación
          boolean procesoOK = usuarioDAL.modificarUsuario(usuarioFin);

          // Validar Operación
          page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO Disponible
          page = "errors/page404";
        }
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Recurso NO Disponible
      page = "errors/page404";
    }

    // Redirección JSP
    forward(page);
  }
}
