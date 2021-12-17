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
package org.japo.java.pll.command.admin;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandSignup extends Command {

    // Constantes
    private static final int DEF_PERFIL = 2; // Usuario Básico

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        try {
            // Obtener Operación
            String op = request.getParameter("op");

            // Capas de Negocio
            UsuarioBLL usuarioBLL = new UsuarioBLL();

            // Procesar Operación
            if (op == null || op.equals("captura")) {
                // Fase Captura - Recuperar Sesión
                page = "admin/signup";
            } else if (op.equals("proceso")) {
                // Request > Parámetros
                String user = request.getParameter("user").trim();
                String pass = request.getParameter("pass").trim();
                String email = request.getParameter("email").trim();
                String icono = request.getParameter("icono").trim();
                String info = request.getParameter("info").trim();

                // Parámetros > Entidad
                Usuario u = new Usuario(0, user, pass, email, icono,
                        DEF_PERFIL, info, 
                        0, null, null, null);

                // Entidad > Inserción BD - true | false
                boolean operacionOK = usuarioBLL.insertarUsuario(u);

                // Validar Inserción BD
                page = operacionOK ? "success/registro-completado" : "errors/credencial-erronea";
            } else {
                // URL de Acceso Errónea
                page = "errors/page404";
            }
        } catch (Exception e) {
            // Credencial Erronea
            page = "errors/credencial-erronea";
        }

        // Redirección JSP
        forward(page);
    }
}
