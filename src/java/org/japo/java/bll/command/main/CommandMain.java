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
package org.japo.java.bll.command.main;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandMain extends Command {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
      } else {
        // Sesión > EntityUsuario
        EntityUsuario u = (EntityUsuario) sesion.getAttribute("usuario");

        // Validar EntityUsuario > Acceso
        if (u == null) {
          page = "errors/acceso-denegado";
        } else if (u.getPerfilID() == EntityPerfil.BASIC) {
          page = "main/main-basico";
        } else if (u.getPerfilID() == EntityPerfil.ADMIN) {
          page = "main/main-admin";
        } else if (u.getPerfilID() == EntityPerfil.DEVEL) {
          page = "main/main-dev";
        } else {
          page = "errors/acceso-denegado";
        }
      }
    } catch (Exception e) {
      // Recurso NO Disponible
      page = "errors/page404";
    }

    // Redirección JSP
    forward(page);
  }
}
