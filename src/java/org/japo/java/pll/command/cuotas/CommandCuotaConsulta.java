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
package org.japo.java.pll.command.cuotas;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.CuotaBLL;
import org.japo.java.entities.Cuota;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandCuotaConsulta extends Command {

    @Override
    public void process() throws ServletException, IOException {
        // Nombre JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        CuotaBLL cuotaBLL = new CuotaBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // Request > ID Entidad
                int id = Integer.parseInt(request.getParameter("id"));

                // ID Entidad > Entidad
                Cuota c = cuotaBLL.obtenerCuota(id);

                // Inyecta Datos > JSP
                request.setAttribute("cuota", c);

                // Nombre JSP
                page = "cuotas/cuota-consulta";
            } else {
                // Acceso NO Autorizado
                page = "errors/acceso-denegado";
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Recurso NO Disponible
            page = "errors/page404";
        }

        // Redirección
        forward(page);
    }
}
