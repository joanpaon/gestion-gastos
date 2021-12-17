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
package org.japo.java.pll.command.perfiles;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.PerfilBLL;
import org.japo.java.entities.Perfil;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPerfilModificacion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Entidad
        Perfil pIni;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        PerfilBLL perfilBLL = new PerfilBLL();

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
                    pIni = perfilBLL.obtenerPerfil(id);

                    // Validar Operación
                    if (pIni == null) {
                        // Recurso NO Disponible
                        page = "errors/page404";
                    } else {
                        // Inyección de Datos
                        request.setAttribute("perfil", pIni);

                        // JSP
                        page = "perfiles/perfil-modificacion";
                    }
                    // JSP > BD Actualizada
                } else if (op.equals("proceso")) {
                    // ID Entidad > Registro BD > Entidad
                    pIni = perfilBLL.obtenerPerfil(id);

                    // Request > Parámetros
                    String nombre = request.getParameter("nombre").trim();
                    String info = request.getParameter("info").trim();
                    String icono = request.getParameter("icono").trim();

                    // Parámetros > Entidad
                    Perfil pFin = new Perfil(id, nombre, info, icono,
                            pIni.getStatus(), pIni.getData(),
                            pIni.getCreatedAt(), pIni.getUpdatedAt());

                    // Ejecutar Operación
                    boolean procesoOK = perfilBLL.modificarPerfil(pFin);

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
