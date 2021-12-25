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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.command.Command;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandLogin extends Command {

  // Duración de Sesión - 1800 seg ( 30 min - default )
  private static final int DURACION_SESION = 1800;

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Obtener Operación
      String op = request.getParameter("op");

      // Request > Sesión
      HttpSession sesion = request.getSession(false);

      // Procesar Operación
      if (UtilesGastos.validarSesion(sesion)) {
        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // Validar Perfil
        switch (usuario.getPerfilID()) {
          case Perfil.DEVEL:
            page = "controller?cmd=main-devel";
            break;
          case Perfil.ADMIN:
            page = "controller?cmd=main-admin";
            break;
          default:
            page = "controller?cmd=main-basic";
        }
      } else if (op == null || op.equals("captura")) {
        page = "admin/login";
      } else if (op.equals("proceso")) {
        // Request > Credenciales
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        // Validar Credencial
        if (user == null || !Usuario.validarUsername(user)) {
          seleccionarMensaje(MSG_ACCESO_DENEGADO);
        } else if (pass == null || !Usuario.validarPassword(pass)) {
          seleccionarMensaje(MSG_ACCESO_DENEGADO);
        } else {
          // Crear Nueva Sesión
          sesion = request.getSession(true);

          // Capas de Negocio
          UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);

          // Nombre Usuario + BD > Objeto Usuario
          Usuario usuario = usuarioDAL.obtenerUsuario(user);

          // Validar Objeto Usuario
          if (usuario == null) {
            seleccionarMensaje(MSG_ACCESO_DENEGADO);
          } else if (!pass.equals(usuario.getPass())) {
            seleccionarMensaje(MSG_ACCESO_DENEGADO);
          } else {
            // Establecer duracion sesion ( Segundos )
            sesion.setMaxInactiveInterval(DURACION_SESION);

            // Usuario > Sesión
            sesion.setAttribute("usuario", usuario);

            // Discrimina Perfil Usuario
            switch (usuario.getPerfilID()) {
              case Perfil.DEVEL:
                page = "controller?cmd=main-devel";
                break;
              case Perfil.ADMIN:
                page = "controller?cmd=main-admin";
                break;
              default:
                page = "controller?cmd=main-basic";
            }
          }
        }
      } else {
        seleccionarMensaje(MSG_ERROR404);
      }
    } catch (NullPointerException e) {
      seleccionarMensaje(MSG_ERROR404);
    }

    // Redirección JSP
    forward(page);
  }
}
