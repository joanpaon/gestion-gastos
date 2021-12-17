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
package org.japo.java.pll.command.abonos;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AbonoBLL;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.ProyectoBLL;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoBorrado extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Capas de Negocio
        AbonoBLL abonoBLL = new AbonoBLL();
        AdminBLL adminBLL = new AdminBLL();
        ProyectoBLL proyectoBLL = new ProyectoBLL();
        UsuarioBLL usuarioBLL = new UsuarioBLL();

        // Sesión
        HttpSession sesion = request.getSession(false);

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
                    Abono a = abonoBLL.obtenerAbono(id);

                    // ID Proyecto > Proyecto
                    Proyecto p = proyectoBLL.obtenerProyecto(a.getProyecto());

                    // ID Usuario > Usuario
                    Usuario u = usuarioBLL.obtenerUsuario(a.getUsuario());

                    // Enlaza Datos > JSP
                    request.setAttribute("abono", a);
                    request.setAttribute("proyecto", p);
                    request.setAttribute("usuario", u);

                    // Confirmar Borrado
                    page = "abonos/abono-borrado";
                } else if (op.equals("proceso")) {
                    // ID > Registro Borrado - true | false
                    boolean operacionOK = abonoBLL.borrarAbono(id);

                    // Validar Operación
                    page = operacionOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
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
