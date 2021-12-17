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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AbonoBLL;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.GastoBLL;
import org.japo.java.bll.PartidaBLL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Gasto;
import org.japo.java.entities.Partida;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandGastoModificacion extends Command {

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void process() throws ServletException, IOException {
        // Nombre JSP
        String page;

        // Entidad
        Gasto pIni;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capa de Negocio
        AdminBLL adminBLL = new AdminBLL();
        GastoBLL gastoBLL = new GastoBLL();
        PartidaBLL partidaBLL = new PartidaBLL();
        AbonoBLL abonoBLL = new AbonoBLL();

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

                // ID Entidad + BD > JSP Modificación
                if (op == null || op.equals("captura")) {
                    // ID Entidad > Registro BD > Entidad
                    pIni = gastoBLL.obtenerGasto(id);

                    // Session > Usuario
                    Usuario user = (Usuario) sesion.getAttribute("usuario");

                    // Usuario > Pefil
                    int perfil = user.getPerfil();

                    // BD > Lista de Abonos
                    List<Abono> listaAbo;

                    // Discriminar Perfil
                    switch (perfil) {
                        case Perfil.BASICO:
                        case Perfil.AVANZADO:
                            // Lista de Abonos - Usuario Actual
                            listaAbo = abonoBLL.obtenerAbonos(user.getId());
                            break;
                        case Perfil.ADMIN:
                        case Perfil.DEV:
                            // Lista de Abonos - Todos los Usuarios
                            listaAbo = abonoBLL.obtenerAbonos();
                            break;
                        default:
                            // Lista de Abonos - Vacía
                            listaAbo = new ArrayList<>();
                    }

                    // BD > Lista de Partidas
                    List<Partida> listaPda = partidaBLL.obtenerPartidas();

                    // Inyecta Datos > JSP
                    request.setAttribute("gasto", pIni);
                    request.setAttribute("lista-abonos", listaAbo);
                    request.setAttribute("lista-partidas", listaPda);

                    // Nombre JSP
                    page = "gastos/gasto-modificacion";
                } else if (op.equals("proceso")) {
                    // ID Entidad > Registro BD > Entidad
                    pIni = gastoBLL.obtenerGasto(id);

                    // Request > Parámetros
                    int abono = Integer.parseInt(request.getParameter("abono").trim());
                    double importe = Double.parseDouble(request.getParameter("importe").trim());
                    String info = request.getParameter("info").trim();
                    int partida = Integer.parseInt(request.getParameter("partida").trim());
                    String recibo = request.getParameter("recibo").trim();
                    Date fecha;
                    try {
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").trim());
                    } catch (ParseException e) {
                        fecha = new Date();
                    }

                    // Parámetros > Entidad
                    Gasto pFin = new Gasto(id, abono, importe, info,
                            partida, recibo,
                            pIni.getStatus(), pIni.getData(),
                            fecha, pIni.getUpdatedAt());

                    // Ejecutar Operación
                    boolean procesoOK = gastoBLL.modificarGasto(pFin);

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
