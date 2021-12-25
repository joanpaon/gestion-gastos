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
package org.japo.java.bll.command.gastos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.dal.GastoDAL;
import org.japo.java.dal.PartidaDAL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Gasto;
import org.japo.java.entities.Partida;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandGastoModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Entidad
      Gasto gastoIni;

      // Sesión
      HttpSession sesion = request.getSession(false);

      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        GastoDAL gastoDAL = new GastoDAL(sesion);
        PartidaDAL partidaDAL = new PartidaDAL(sesion);
        AbonoDAL abonoDAL = new AbonoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // request > Operación
          String op = request.getParameter("op");

          // ID Entidad + BD > JSP Modificación
          if (op == null || op.equals("captura")) {
            // ID Entidad > Registro BD > Entidad
            gastoIni = gastoDAL.obtenerGasto(id);

            // BD > Lista de Abonos
            List<Abono> abonos = abonoDAL.obtenerAbonos();

            // BD > Lista de Partidas
            List<Partida> partidas = partidaDAL.obtenerPartidas();

            // Inyecta Datos > JSP
            request.setAttribute("gasto", gastoIni);
            request.setAttribute("abonos", abonos);
            request.setAttribute("partidas", partidas);

            // JSP
            page = "gastos/gasto-modificacion";
          } else if (op.equals("proceso")) {
            // ID Entidad > Registro BD > Entidad
            gastoIni = gastoDAL.obtenerGasto(id);

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
            Gasto gastoFin = new Gasto(id, abono, importe, info,
                    partida, recibo,
                    gastoIni.getStatus(), gastoIni.getData(),
                    fecha, gastoIni.getUpdatedAt());

            // Ejecutar Operación
            boolean checkOK = gastoDAL.modificarGasto(gastoFin);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Se han modificado correctamente los datos del usuario";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=gasto-listado";

              // Inyeccion de Parámetros
              parametrizarMensaje(titulo, mensaje, imagen, destino);
            } else {
              seleccionarMensaje(MSG_OPERACION_CANCELADA);
            }
          } else {
            seleccionarMensaje(MSG_ERROR404);
          }
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
