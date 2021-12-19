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
package org.japo.java.bll.command.usuarios;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandUsuarioListado extends Command {

  // Nombre de la tabla
  public static final String TABLE_NAME = "usuarios";

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    UsuarioDAL usuarioDAL = new UsuarioDAL();

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
        pl.setRowCount(usuarioDAL.contarUsuarios(pl));

        // Navegación > Parámetros Listado
        UtilesGastos.definirNavegacionListado(pl, request);

        // Lista de Usuarios
        List<EntityUsuario> usuarios = usuarioDAL.obtenerUsuarios(pl);

        // Inyecta Datos > JSP
        request.setAttribute("usuarios", usuarios);

        // Inyecta Parámetros Listado > JSP
        request.setAttribute("filter-fld", pl.getFilterField());
        request.setAttribute("filter-exp", pl.getFilterValue());
        request.setAttribute("sort-fld", pl.getOrderField());
        request.setAttribute("sort-dir", pl.getOrderProgress());
        request.setAttribute("row-count", pl.getRowCount());
        request.setAttribute("row-index", pl.getRowIndex());
        request.setAttribute("rows-page", pl.getRowsPage());

        // JSP
        page = "usuarios/usuario-listado";
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
