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
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.entities.Abono;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoConsulta extends Command {

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
          // Request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // ID Entidad + BD > Entidad
          Abono abono = abonoDAL.obtenerAbono(id);

          // Enlaza Datos > JSP
          request.setAttribute("abono", abono);

          // JSP
          page = "abonos/abono-consulta";
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
