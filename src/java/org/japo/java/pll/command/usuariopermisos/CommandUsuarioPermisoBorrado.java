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
package org.japo.java.pll.command.usuariopermisos;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.ProcesoBLL;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.bll.UsuarioPermisoBLL;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.UsuarioPermiso;
import org.japo.java.entities.Proceso;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandUsuarioPermisoBorrado extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        ProcesoBLL procesoBLL = new ProcesoBLL();
        UsuarioBLL usuarioBLL = new UsuarioBLL();
        UsuarioPermisoBLL usuarioPermisoBLL = new UsuarioPermisoBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // URL > ID Objeto
                int id = Integer.parseInt(request.getParameter("id"));

                // request > ID Operación
                String op = request.getParameter("op");

                // ID Entidad + BD > JSP Modificación
                if (op == null || op.equals("captura")) {
                    // ID Entidad + BD > Entidad
                    UsuarioPermiso up = usuarioPermisoBLL.obtenerUsuarioPermiso(id);

                    // ID Proceso > Proceso
                    Proceso p = procesoBLL.obtenerProceso(up.getProceso());

                    // ID Usuario > Usuario
                    Usuario g = usuarioBLL.obtenerUsuario(up.getUsuario());

                    // Enlaza Datos > JSP
                    request.setAttribute("usuario-permiso", up);
                    request.setAttribute("proceso", p);
                    request.setAttribute("usuario", g);

                    // Confirmar Borrado
                    page = "usuariopermisos/usuario-permiso-borrado";
                } else if (op.equals("proceso")) {
                    // ID > Registro Borrado - true | false
                    boolean operacionOK = usuarioPermisoBLL.borrarUsuarioPermiso(id);

                    // Validar Operación
                    page = operacionOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
                } else {
                    // Acceso NO Autorizado
                    page = "errors/acceso-denegado";
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
