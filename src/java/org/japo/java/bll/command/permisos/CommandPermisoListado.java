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
package org.japo.java.bll.command.permisos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPermisoListado extends Command {

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
        PermisoDAL permisoDAL = new PermisoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // Sesión > Usuario
          Usuario usuario = (Usuario) sesion.getAttribute("usuario");

          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado("gestion_gastos", "permisos", usuario);

          // Filtro > Parámetros Listado
          UtilesGastos.definirListaFiltro(pl, request);

          // Ordenación > Parámetros Listado
          UtilesGastos.definirListaOrden(pl, request);

          // Total de Filas > Parámetros Listado
          pl.setRowCount(permisoDAL.contarPermisos(pl));

          // Navegación > Parámetros Listado
          UtilesGastos.definirListaPagina(pl, request);

          // BD > Lista de Permisos de Perfil
          List<Permiso> permisos = permisoDAL.obtenerPermisos(pl);

          // Inyecta Datos Listado > JSP
          request.setAttribute("permisos", permisos);

          // Inyecta Parámetros Listado > JSP
          request.setAttribute("filter-fld", pl.getFilterField());
          request.setAttribute("filter-exp", pl.getFilterValue());
          request.setAttribute("sort-fld", pl.getOrderField());
          request.setAttribute("sort-dir", pl.getOrderProgress());
          request.setAttribute("row-count", pl.getRowCount());
          request.setAttribute("row-index", pl.getRowIndex());
          request.setAttribute("rows-page", pl.getRowsPage());

          // JSP
          page = "permisos/permiso-listado";
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
