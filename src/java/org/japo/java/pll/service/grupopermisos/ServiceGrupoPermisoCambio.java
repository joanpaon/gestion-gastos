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
package org.japo.java.pll.service.grupopermisos;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.PerfilPermisoBLL;
import org.japo.java.entities.PerfilPermiso;
import org.japo.java.pll.service.Service;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServiceGrupoPermisoCambio extends Service {

    @Override
    public void process() throws ServletException, IOException {
        // JSP
        String json;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        PerfilPermisoBLL grupoPermisoBLL = new PerfilPermisoBLL();

        try {
            // Validar Acceso
            if (adminBLL.validarAccesoServicio(sesion, getClass().getSimpleName())) {
                // Request > Id Proceso
                int proceso = Integer.parseInt(request.getParameter("process"));

                // Request > Id Grupo
                int grupo = Integer.parseInt(request.getParameter("group"));

                // Request > State
                boolean state = Boolean.parseBoolean(request.getParameter("state"));

                PerfilPermiso pg = new PerfilPermiso(0, proceso, grupo, null, 0, null, null, null);

                // Action + State > Actualizar Permisos Usuario
                boolean procesoOK = grupoPermisoBLL.cambiarPerfilPermiso(pg, state);

                // Resultado > JSON
                if (procesoOK) {
                    json = "{\"ok\":true, \"msg\":\"Permiso SI Cambiado\"}";
                } else {
                    json = "{\"ok\":false, \"msg\":\"Permiso NO Cambiado\"}";
                }
            } else {
                json = "{\"ok\":false, \"msg\":\"Acceso NO Autorizado\"}";
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Recurso NO Disponible
            json = "{\"ok\":false, \"msg\":\"Recurso NO Disponible\"}";
        }

        // Redirección JSP
        forward(json);
    }
}
