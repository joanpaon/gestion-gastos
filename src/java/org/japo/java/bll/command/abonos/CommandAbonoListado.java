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
package org.japo.java.bll.command.abonos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.entities.EntityAbono;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoListado extends Command {

  // Nombre de la tabla
  public static final String TABLE_NAME = "abonos";

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Negocio
    AbonoDAL abonoDAL = new AbonoDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Parámetros Listado
        ParametrosListado pl = UtilesGastos.iniciarParametrosListado(TABLE_NAME, request);

        // Filtro > Parámetros Listado
        UtilesGastos.definirFiltradoListado(pl, request);

        // Ordenación > Parámetros Listado
        UtilesGastos.definirOrdenacionListado(pl, request);

        // Total de Filas > Parámetros Listado
        pl.setRowCount(abonoDAL.contarAbonos(pl));

        // Navegación > Parámetros Listado
        UtilesGastos.definirNavegacionListado(pl, request);

        // BD > Lista de Abonos ( Modificados )
        List<EntityAbono> abonos = abonoDAL.obtenerAbonos(pl);

        // Inyecta Datos Listado > JSP
        request.setAttribute("abonos", abonos);

        // Inyecta Parámetros Listado > JSP
        request.setAttribute("filter-fld", pl.getFilterField());
        request.setAttribute("filter-exp", pl.getFilterValue());
        request.setAttribute("sort-fld", pl.getOrderField());
        request.setAttribute("sort-dir", pl.getOrderProgress());
        request.setAttribute("row-count", pl.getRowCount());
        request.setAttribute("row-index", pl.getRowIndex());
        request.setAttribute("rows-page", pl.getRowsPage());

        // JSP
        page = "abonos/abono-listado";
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
