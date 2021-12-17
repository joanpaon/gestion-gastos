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
package org.japo.java.pll.command.proyectos;

import org.japo.java.pll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.CuotaBLL;
import org.japo.java.bll.ProyectoBLL;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoModificacion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Entidad
        Proyecto pIni;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        UsuarioBLL usuarioBLL = new UsuarioBLL();
        CuotaBLL cuotaBLL = new CuotaBLL();
        ProyectoBLL proyectoBLL = new ProyectoBLL();

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
                    // BD > Entidades
                    pIni = proyectoBLL.obtenerProyecto(id);
                    List<Usuario> listaUsr = usuarioBLL.obtenerUsuarios();
                    List<Cuota> listaCta = cuotaBLL.obtenerCuotas();

                    // Inyección de Datos
                    request.setAttribute("proyecto", pIni);
                    request.setAttribute("usuario-lista", listaUsr);
                    request.setAttribute("cuota-lista", listaCta);

                    // JSP
                    page = "proyectos/proyecto-modificacion";
                } else if (op.equals("proceso")) {
                    // ID Entidad > Registro BD > Entidad
                    pIni = proyectoBLL.obtenerProyecto(id);

                    // Request > Parámetros
                    String nombre = request.getParameter("nombre").trim();
                    String info = request.getParameter("info").trim();
                    String icono = request.getParameter("icono").trim();
                    int propietario = Integer.parseInt(request.getParameter("propietario").trim());
                    int cuota = Integer.parseInt(request.getParameter("cuota").trim());

                    // Parámetros > Entidad
                    Proyecto pFin = new Proyecto(id, nombre, info, icono,
                            propietario, cuota,
                            pIni.getStatus(), pIni.getData(),
                            pIni.getCreatedAt(), pIni.getUpdatedAt());

                    // Ejecutar Operación
                    boolean procesoOK = proyectoBLL.modificarProyecto(pFin);

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