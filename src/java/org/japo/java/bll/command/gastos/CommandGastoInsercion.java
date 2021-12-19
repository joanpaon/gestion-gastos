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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.dal.GastoDAL;
import org.japo.java.dal.PartidaDAL;
import org.japo.java.entities.EntityAbono;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityGasto;
import org.japo.java.entities.EntityPartida;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandGastoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    AbonoDAL abonoDAL = new AbonoDAL();
    GastoDAL gastoDAL = new GastoDAL();
    PartidaDAL partidaDAL = new PartidaDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Obtener Operación
        String op = request.getParameter("op");

        // ID Entidad + BD > JSP Modificación
        if (op == null || op.equals("captura")) {
          // Session > EntityUsuario
          EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

          // BD > Lista de Abonos
          List<EntityAbono> abonos;

          // Discriminar Perfil
          switch (usuario.getPerfilID()) {
            case EntityPerfil.BASIC:
              // Lista de Gastos - SÓLO Usuario Actual
              abonos = abonoDAL.obtenerAbonos(usuario.getId());
              break;
            case EntityPerfil.ADMIN:
            case EntityPerfil.DEVEL:
              // Lista de Gastos - TODOS los Usuarios
              abonos = abonoDAL.obtenerAbonos();
              break;
            default:
              // Lista de Gastos - Vacía
              abonos = new ArrayList<>();
          }

          // BD > Lista de Partidas
          List<EntityPartida> partidas = partidaDAL.obtenerPartidas();

          // Inyecta Datos > JSP
          request.setAttribute("abonos", abonos);
          request.setAttribute("partidas", partidas);

          // JSP
          page = "gastos/gasto-insercion";
        } else if (op.equals("proceso")) {
          // Request > Parámetros
          int abonoID = Integer.parseInt(request.getParameter("abono").trim());
          double importe = Double.parseDouble(request.getParameter("importe").trim());
          String info = request.getParameter("info").trim();
          int partidaID = Integer.parseInt(request.getParameter("partida").trim());
          String recibo = request.getParameter("recibo").trim();
          Date fecha;
          try {
            fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha"));
          } catch (ParseException e) {
            fecha = new Date();
          }

          // Parámetros > Entidad
          EntityGasto gasto = new EntityGasto(0, abonoID, importe, info, partidaID, recibo,
                  0, null, fecha, null);

          // Entidad > Inserción BD - true | false
          boolean procesoOK = gastoDAL.insertarGasto(gasto);

          // Validar Proceso
          page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO disponible
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

    // Redirección JSP
    forward(page);
  }
}
