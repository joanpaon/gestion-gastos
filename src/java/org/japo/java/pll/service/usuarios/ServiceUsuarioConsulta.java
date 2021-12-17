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
package org.japo.java.pll.service.usuarios;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.entities.Usuario;
import org.japo.java.pll.service.Service;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServiceUsuarioConsulta extends Service {

    @Override
    public void process() throws ServletException, IOException {
        // JSP
        String json;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        UsuarioBLL usuarioBLL = new UsuarioBLL();

        try {
            // Validar Acceso
            if (adminBLL.validarAccesoServicio(sesion, getClass().getSimpleName())) {
                // Request > ID Entidad
                int id = Integer.parseInt(request.getParameter("id"));

                // ID Entidad > Entidad
                Usuario u = usuarioBLL.obtenerUsuario(id);

                // List > JSON
                json = new Gson().toJson(u);
            } else {
                // Acceso NO Autorizado
                json = "{\"response\": \"Acceso NO Autorizado\"}";
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Recurso NO Disponible
            json = "{\"response\": \"Recurso NO Disponible\"}";
        }

        // Redirección JSP
        forward(json);
    }
}
