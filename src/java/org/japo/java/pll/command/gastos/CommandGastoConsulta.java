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
package org.japo.java.pll.command.gastos;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AbonoBLL;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.GastoBLL;
import org.japo.java.bll.PartidaBLL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Gasto;
import org.japo.java.entities.Partida;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandGastoConsulta extends Command {

    @Override
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capa de Negocio
        AbonoBLL abonoBLL = new AbonoBLL();
        AdminBLL adminBLL = new AdminBLL();
        GastoBLL gastoBLL = new GastoBLL();
        PartidaBLL partidaBLL = new PartidaBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // Request > ID Entidad
                int id = Integer.parseInt(request.getParameter("id"));

                // ID Entidad > Entidad
                Gasto pgo = gastoBLL.obtenerGasto(id);

                // FK Entidad > Entidad
                Abono a = abonoBLL.obtenerAbono(pgo.getAbono());
                Partida pda = partidaBLL.obtenerPartida(pgo.getPartida());

                // Inyecta Datos > JSP
                request.setAttribute("gasto", pgo);
                request.setAttribute("abono", a);
                request.setAttribute("partida", pda);

                // JSP
                page = "gastos/gasto-consulta";
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
