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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AbonoBLL;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.ProyectoBLL;
import org.japo.java.bll.UsuarioBLL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoInsercion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // Nombre JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AbonoBLL abonoBLL = new AbonoBLL();
        AdminBLL adminBLL = new AdminBLL();
        ProyectoBLL proyectoBLL = new ProyectoBLL();
        UsuarioBLL usuarioBLL = new UsuarioBLL();

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
                    // BD > Lista de Proyectos
                    List<Proyecto> listaPro = proyectoBLL.obtenerProyectos();

                    // BD > Lista de Usuarios
                    List<Usuario> listaUsr;

                    // Sesión > Usuario
                    Usuario user = (Usuario) sesion.getAttribute("usuario");

                    // Usuario > Perfil
                    int perfil = user.getPerfil();

                    // Discriminar Usuario
                    switch (perfil) {
                        case Perfil.BASICO:
                        case Perfil.AVANZADO:
                            // Lista de Usuarios - Vacía
                            listaUsr = new ArrayList<>(Arrays.asList(user));
                            break;
                        case Perfil.ADMIN:
                        case Perfil.DEV:
                            // Lista de Usuarios - Todos
                            listaUsr = usuarioBLL.obtenerUsuarios();
                            break;
                        default:
                            // Lista de Usuarios - Vacía
                            listaUsr = new ArrayList<>();
                    }

                    // Inyecta Datos > JSP
                    request.setAttribute("lista-proyectos", listaPro);
                    request.setAttribute("lista-usuarios", listaUsr);

                    // Nombre JSP
                    page = "abonos/abono-insercion";
                } else if (op.equals("proceso")) {
                    // Request > Parámetros
                    int proyecto = Integer.parseInt(request.getParameter("proyecto").trim());
                    int usuario = Integer.parseInt(request.getParameter("usuario").trim());
                    String info = request.getParameter("info").trim();

                    // Parámetros > Entidad
                    Abono a = new Abono(0, proyecto, usuario, info, 0, null, null, null);

                    // Entidad > Inserción BD - true | false
                    boolean operacionOK = abonoBLL.insertarAbono(a);

                    // Validar Inserción BD
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
            // Recurso NO disponible
            page = "errors/page404";
        }

        // Command > JSP
        forward(page);
    }
}
