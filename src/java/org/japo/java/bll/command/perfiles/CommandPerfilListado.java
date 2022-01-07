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
package org.japo.java.bll.command.perfiles;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.PerfilDAL;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPerfilListado extends Command {

    // Constantes Referenciales
    private static final String BASE_DATOS = "gestion_gastos";
    private static final String TABLA = "perfiles";

    // Constantes de Atributos - Filtro
    private static final String FILTRO_CAMPOS = "filter-fld";
    private static final String FILTRO_PATRON = "filter-exp";

    // Constantes de Atributos - Ordenación
    private static final String ORDEN_CAMPO = "sort-fld";
    private static final String ORDEN_AVANCE = "sort-dir";

    // Constantes de Atributos - Paginación
    private static final String FILAS_TOTAL = "row-count";
    private static final String FILA_ACTUAL = "row-index";
    private static final String FILAS_PAGINA = "rows-page";

    // Atributo ParámetrosListado - Sesión
    private static final String PARAMETROS_LISTADO_SESION = "parametros-listado-perfiles";

    @Override
    public void process() throws ServletException, IOException {
        // JSP
        String page = "messages/message";

        try {
            // Sesión
            HttpSession sesion = request.getSession(false);

            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                seleccionarMensaje(MSG_SESION_INVALIDA);
            } else {
                // Capas de Negocio
                AdminBLL adminBLL = new AdminBLL(sesion);

                // Capas de Datos
                PerfilDAL perfilDAL = new PerfilDAL(sesion);

                if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
                    // Sesión > Usuario
                    Usuario usuario = (Usuario) sesion.getAttribute("usuario");

                    // Sesion > ParametrosListado ( Usuarios )
                    ParametrosListado pl = (ParametrosListado) sesion.getAttribute(PARAMETROS_LISTADO_SESION);
                    pl = pl != null ? pl : new ParametrosListado(BASE_DATOS, TABLA, usuario);

                    // Campos de Listado > Parámetros Listado
                    String[] camposListado = {"perfiles.id", "perfiles.nombre", "perfiles.info"};
                    pl.setFilterFields(new ArrayList<>(Arrays.asList(camposListado)));

                    // Request + Filtro > Parámetros Listado
                    UtilesGastos.definirFiltroListado(pl, request);

                    // Request + Orden > Parámetros Listado
                    UtilesGastos.definirOrdenListado(pl, request);

                    // Request + Navegación > Parámetros Listado
                    UtilesGastos.definirNavegacionListado(pl, request);

                    // BD > Lista de Perfiles
                    List<Perfil> perfiles;

                    // Determinar Perfil Usuario
                    switch (usuario.getPerfilID()) {
                        case Perfil.DEVEL:
                            // BD > Lista de Pefiles
                            perfiles = perfilDAL.obtenerPerfiles(pl);
                            break;
                        case Perfil.ADMIN:
                            // BD > Lista de Pefiles
                            perfiles = perfilDAL.obtenerPerfiles(pl);
                            break;
                        case Perfil.BASIC:
                        default:
                            // Usuario Actual (Únicamente) > Lista de Usuarios
                            Perfil perfil = perfilDAL.obtenerPerfil(usuario.getPerfilID());
                            perfiles = new ArrayList<>();
                            perfiles.add(perfil);
                            pl.setRowCount(1L);
                    }

                    // Inyecta Datos Listado > JSP
                    request.setAttribute("perfiles", perfiles);

                    // Inyecta Parámetros Listado > JSP
                    request.setAttribute(FILTRO_CAMPOS, pl.getFilterField());
                    request.setAttribute(FILTRO_PATRON, pl.getFilterValue());
                    request.setAttribute(ORDEN_CAMPO, pl.getOrderField());
                    request.setAttribute(ORDEN_AVANCE, pl.getOrderAdvance());
                    request.setAttribute(FILAS_TOTAL, pl.getRowCount());
                    request.setAttribute(FILA_ACTUAL, pl.getRowIndex());
                    request.setAttribute(FILAS_PAGINA, pl.getRowsPage());

                    // JSP
                    page = "perfiles/perfil-listado";

                    // ParámetrosListado > Sesion
                    sesion.setAttribute(PARAMETROS_LISTADO_SESION, pl);
                } else {
                    seleccionarMensaje(MSG_ACCESO_DENEGADO);
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            seleccionarMensaje(MSG_ERROR404);
        }

        // Redirección JSP
        forward(page);
    }
}
