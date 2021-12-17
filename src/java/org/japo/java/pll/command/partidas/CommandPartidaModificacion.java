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
package org.japo.java.pll.command.partidas;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.PartidaBLL;
import org.japo.java.entities.Partida;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPartidaModificacion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Entidad
        Partida cIni;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        PartidaBLL partidaBLL = new PartidaBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // request > ID Entidad
                int id = Integer.parseInt(request.getParameter("id"));

                // request > Operación
                String op = request.getParameter("op");

                // Entidad > JSP
                if (op == null || op.equals("captura")) {
                    // ID Entidad > Registro BD > Entidad
                    cIni = partidaBLL.obtenerPartida(id);

                    // Inyección de Datos
                    request.setAttribute("partida", cIni);

                    // JSP
                    page = "partidas/partida-modificacion";
                } else if (op.equals("proceso")) {
                    // ID Entidad > Registro BD > Entidad
                    cIni = partidaBLL.obtenerPartida(id);

                    // Request > Parámetros
                    String nombre = request.getParameter("nombre").trim();
                    String info = request.getParameter("info").trim();
                    String icono = request.getParameter("icono").trim();

                    // Parámetros > Entidad
                    Partida cFin = new Partida(id, nombre, info, icono,
                            cIni.getStatus(), cIni.getData(),
                            cIni.getCreatedAt(), cIni.getUpdatedAt());

                    // Ejecutar Operación
                    boolean procesoOK = partidaBLL.modificarPartida(cFin);

                    // Validar Operación
                    page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
                } else {
                    // Recurso NO Disponible
                    page = "errors/page404";
                }
            } else {
                // Acceso NO Autorizado
                page = "errors/acceso-denegado";
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Recurso NO Disponible
            page = "errors/page404";
        }

        // Redirección JSP
        forward(page);
    }
}
