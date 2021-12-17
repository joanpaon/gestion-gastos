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
public final class CommandPerfilPermisoModificacion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Entidad Inicial
        PerfilPermiso ppIni;

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
                // request > ID Entidad
                int id = Integer.parseInt(request.getParameter("id"));

                // request > ID Operación
                String op = request.getParameter("op");

                // ID Entidad + BD > JSP Modificación
                if (op == null || op.equals("captura")) {
                    // ID > Objeto Entidad
                    ppIni = perfilPermisoBLL.obtenerPerfilPermiso(id);

                    // BD > Lista de Procesos
                    List<Proceso> listaPrc = procesoBLL.obtenerProcesos();

                    // BD > Lista de Perfiles
                    List<Perfil> listaGrp = perfilBLL.obtenerPerfiles();

                    // Inyectar Datos > JSP
                    request.setAttribute("permiso-perfil", ppIni);
                    request.setAttribute("proceso-lista", listaPrc);
                    request.setAttribute("perfil-lista", listaGrp);

                    // JSP
                    page = "perfilpermisos/perfil-permiso-modificacion";
                } else if (op.equals("proceso")) {
                    // ID Permiso Perfil > Objeto Entidad
                    ppIni = perfilPermisoBLL.obtenerPerfilPermiso(id);

                    // Request > Parámetros
                    int proceso = Integer.parseInt(request.getParameter("proceso"));
                    int perfil = Integer.parseInt(request.getParameter("perfil"));
                    String info = request.getParameter("info");

                    // Entidad Final
                    PerfilPermiso ppFin = new PerfilPermiso(id, proceso, perfil, info,
                            ppIni.getStatus(), ppIni.getData(),
                            ppIni.getCreatedAt(), ppIni.getUpdatedAt());

                    // Entidad > Modificación Registro BD
                    boolean procesoOK = perfilPermisoBLL.modificarPerfilPermiso(ppFin);

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
