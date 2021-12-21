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
import org.japo.java.entities.Abono;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoListado extends Command {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        // Parámetros
        String titulo = "Sesión Caducada";
        String mensaje = "Identificación requerida para continuar";
        String imagen = "public/img/expired.jpg";
        String destino = "controller?cmd=login";

        // Inyeccion de Parámetros
        parametrizarMensaje(titulo, mensaje, imagen, destino);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        AbonoDAL abonoDAL = new AbonoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // Sesión > Usuario
          Usuario usuario = (Usuario) sesion.getAttribute("usuario");

          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado("gestion_gastos", "abonos", usuario);

          // Filtro > Parámetros Listado
          UtilesGastos.definirFiltradoListado(pl, request);

          // Ordenación > Parámetros Listado
          UtilesGastos.definirOrdenacionListado(pl, request);

          // Total de Filas > Parámetros Listado
          pl.setRowCount(abonoDAL.contarAbonos(pl));

          // Navegación > Parámetros Listado
          UtilesGastos.definirNavegacionListado(pl, request);

          // BD > Lista de Abonos
          List<Abono> abonos = abonoDAL.obtenerAbonos(pl);

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
          // Parámetros
          String titulo = "Acceso NO Autorizado";
          String mensaje = "Nivel de Acceso Insuficiente para ese Recurso";
          String imagen = "public/img/cancelar.png";
          String destino = "javascript:window.history.back();";

          // Inyeccion de Parámetros
          parametrizarMensaje(titulo, mensaje, imagen, destino);
        }
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Parámetros
      String titulo = "Operación Cancelada";
      String mensaje = "Intento de acceso a un recurso NO disponible";
      String imagen = "public/img/cancelar.png";
      String destino = "javascript:window.history.back();";

      // Inyeccion de Parámetros
      parametrizarMensaje(titulo, mensaje, imagen, destino);
    }

    // Redirección JSP
    forward(page);
  }
}
