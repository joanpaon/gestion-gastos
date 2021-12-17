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
import org.japo.java.bll.ProyectoBLL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.ProyectoLista;
import org.japo.java.libraries.UtilesGastos;
import static org.japo.java.pll.command.cuotas.CommandCuotaListado.TABLE_NAME;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoListado extends Command {

    @Override
    public void process() throws ServletException, IOException {
        // JSP
        String page;

        // Sesión
        HttpSession sesion = request.getSession(false);

        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL();
        ProyectoBLL proyectoBLL = new ProyectoBLL();

        try {
            // Validar Sesión
            if (!UtilesGastos.validarSesion(sesion)) {
                page = "errors/sesion-caducada";
                // Validar Acceso
            } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
                // Parámetros Listado
                ParametrosListado p = UtilesGastos.iniciarParametrosListado(TABLE_NAME, request);

                // Filtro > Parámetros Listado
                UtilesGastos.definirFiltradoListado(p, request);

                // Ordenación > Parámetros Listado
                UtilesGastos.definirOrdenacionListado(p, request);

                // Total de Filas > Parámetros Listado
                p.setRowCount(proyectoBLL.contarProyectos(p));

                // Navegación > Parámetros Listado
                UtilesGastos.definirNavegacionListado(p, request);

                // BD > Lista de Cuotas
                List<ProyectoLista> lista = proyectoBLL.obtenerProyectos(p);

                // Inyecta Datos Listado > JSP
                request.setAttribute("lista", lista);

                // Inyecta Parámetros Listado > JSP
                request.setAttribute("filter-exp", p.getFilterExp());
                request.setAttribute("sort-fld", p.getSortFld());
                request.setAttribute("sort-dir", p.getSortDir());
                request.setAttribute("row-count", p.getRowCount());
                request.setAttribute("row-index", p.getRowIndex());
                request.setAttribute("rows-page", p.getRowsPage());

                // JSP
                page = "proyectos/proyecto-listado";
            } else {
                // Acceso NO Autorizado
                page = "errors/acceso-denegado";
            }
        } catch (Exception e) {
            // Recurso NO Disponible
            page = "errors/page404";
        }

        // Redirección JSP
        forward(page);
    }
}
