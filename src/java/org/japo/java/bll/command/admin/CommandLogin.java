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
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityUsuario;

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
    String page;

    try {
      // Obtener Operación
      String op = request.getParameter("op");

      // Capas de Negocio
      UsuarioDAL usuarioDAL = new UsuarioDAL();

      // Request > Sesión
      HttpSession sesion = request.getSession(false);

      // Procesar Operación
      if (sesion == null) {
        // Acceso Denegado - Acceso directo por URL
        page = "error/acceso-denegado";
      } else if (sesion.getAttribute("usuario") != null) {
        // Sesión + EntityUsuario Existente - Aceso permitido
        page = "success/acceso-concedido";
      } else if (op == null || op.equals("captura")) {
        // Credenciales > Request
        page = "admin/login";
      } else if (op.equals("proceso")) {
        // Request > Credenciales
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        // Validar Credencial
        if (user == null || !EntityUsuario.validarUsername(user)) {
          // Credencial Incorrecta - Nombre de EntityUsuario Incorrecto
          page = "errors/credencial-erronea";
        } else if (pass == null || !EntityUsuario.validarPassword(pass)) {
          // Credencial Incorrecta - Contraseña Incorrecta
          page = "errors/credencial-erronea";
        } else {
          // Nombre EntityUsuario + BD > Objeto EntityUsuario
          EntityUsuario usuario = usuarioDAL.obtenerUsuario(user);

          // Validar Objeto EntityUsuario
          if (usuario == null) {
            // Credencial Incorrecta - EntityUsuario NO Registrado
            page = "errors/credencial-erronea";
          } else if (!pass.equals(usuario.getPass())) {
            // Credencial Incorrecta - Contraseña NO coincide
            page = "errors/credencial-erronea";
          } else {
            // Elimina Sesión Previa
            sesion.invalidate();

            // Crear Nueva Sesión
            sesion = request.getSession(true);

            // Establecer duracion sesion ( Segundos )
            sesion.setMaxInactiveInterval(DURACION_SESION);

            // EntityUsuario > Sesión
            sesion.setAttribute("usuario", usuario);

            // Discrimina Perfil Usuario
            switch (usuario.getPerfilID()) {
              case EntityPerfil.DEVEL:
                page = "controller?cmd=main-devel";
                break;
              case EntityPerfil.ADMIN:
                page = "controller?cmd=main-admin";
                break;
              default:
                page = "controller?cmd=main-basic";
            }
          }
        }
      } else {
        // Formato de URL incorrecto
        page = "errors/page404";
      }
    } catch (NullPointerException e) {
      // Recurso NO disponible
      page = "errors/page404";
    }

    // Redirección JSP
    forward(page);
  }
}
