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
package org.japo.java.bll.service.usuarios;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.entities.Usuario;
import org.japo.java.bll.service.Service;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServiceUsuarioExistencia extends Service {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String json;

    try {
      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        // Recurso NO Disponible
        json = "{\"response\": \"Sesión Caducada\"}";
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);

        // Validar Acceso
        if (adminBLL.validarAccesoServicio(getClass().getSimpleName())) {
          // Request > ID Entidad
          String user = request.getParameter("user");

          // ID Entidad > Entidad
          Usuario u = usuarioDAL.obtenerUsuario(user);

          // List > JSON
          if (u != null) {
            json = "{\"ok\":true, \"msg\":\"Usuario SI existe\", \"user\":\"" + user + "\"}";
          } else {
            json = "{\"ok\":false, \"msg\":\"Usuario NO existe\", \"user\":\"" + user + "\"}";
          }
        } else {
          // Acceso NO Autorizado
          json = "{\"ok\":false, \"msg\":\"Acceso NO Autorizado\"}";
        }
      }
    } catch (Exception e) {
      // Recurso NO Disponible
      json = "{\"ok\":false, \"msg\":\"Recurso NO Disponible\"}";
    }

    // Redirección JSP
    forward(json);
  }
}
