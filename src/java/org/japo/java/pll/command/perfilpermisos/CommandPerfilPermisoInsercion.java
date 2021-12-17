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
package org.japo.java.pll.command.perfilpermisos;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.PerfilBLL;
import org.japo.java.bll.PerfilPermisoBLL;
import org.japo.java.bll.ProcesoBLL;
import org.japo.java.entities.PerfilPermiso;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proceso;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPerfilPermisoInsercion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // Nombre JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        PerfilBLL perfilBLL = new PerfilBLL();
        PerfilPermisoBLL perfilPermisoBLL = new PerfilPermisoBLL();
        ProcesoBLL procesoBLL = new ProcesoBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // Obtener Operación
                String op = request.getParameter("op");

                // Formulario Captura Datos
                if (op == null || op.equals("captura")) {
                    // BD > Lista de Procesos
                    List<Proceso> listaPrc = procesoBLL.obtenerProcesos();

                    // BD > Lista de Grupos
                    List<Perfil> listaPrf = perfilBLL.obtenerPerfiles();

                    // Inyecta Datos > JSP
                    request.setAttribute("lista-procesos", listaPrc);
                    request.setAttribute("lista-perfiles", listaPrf);

                    // Nombre JSP
                    page = "perfilpermisos/perfil-permiso-insercion";
                } else if (op.equals("proceso")) {
                    // Request > Parámetros
                    int proceso = Integer.parseInt(request.getParameter("proceso"));
                    int perfil = Integer.parseInt(request.getParameter("perfil"));
                    String info = request.getParameter("info");

                    // Parámetros > Entidad
                    PerfilPermiso pg = new PerfilPermiso(0, proceso, perfil, info,
                            0, null, null, null);

                    // Entidad > Inserción BD - true | false
                    boolean operacionOK = perfilPermisoBLL.insertarPerfilPermiso(pg);

                    // Validar Inserción BD
                    page = operacionOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
                } else {
                    // Recurso NO disponible
                    page = "errors/page404";
                }
            } else {
                // Acceso NO Autorizado
                page = "errors/acceso-denegado";
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Recurso NO disponible
            page = "errors/page404";
        }

        // Command > JSP
        forward(page);
    }
}
