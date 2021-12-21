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
package org.japo.java.bll.command.partidas;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.PartidaDAL;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.entities.Partida;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPartidaModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Entidad
      Partida partidaIni;

      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
        // Validar Acceso
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        PartidaDAL partidaDAL = new PartidaDAL(sesion);
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // request > Operación
          String op = request.getParameter("op");

          // Entidad > JSP
          if (op == null || op.equals("captura")) {
            // ID Entidad > Objeto Entidad
            partidaIni = partidaDAL.obtenerPartida(id);
            
            // BD > Lista de Proyectos
            List<Proyecto> proyectos = proyectoDAL.obtenerProyectos();

            // Inyección de Datos
            request.setAttribute("partida", partidaIni);
            request.setAttribute("proyectos", proyectos);

            // JSP
            page = "partidas/partida-modificacion";
          } else if (op.equals("proceso")) {
            // ID Entidad > Registro BD > Entidad
            partidaIni = partidaDAL.obtenerPartida(id);

            // Request > Parámetros
            String nombre = request.getParameter("nombre").trim();
            String info = request.getParameter("info").trim();
            String icono = request.getParameter("icono").trim();
            int proyectoID = Integer.parseInt(request.getParameter("proyecto").trim());

            // Parámetros > Entidad
            Partida partidaFin = new Partida(id, nombre, info, icono, proyectoID,
                    partidaIni.getStatus(), partidaIni.getData(),
                    partidaIni.getCreatedAt(), partidaIni.getUpdatedAt());

            // Ejecutar Operación
            boolean checkOK = partidaDAL.modificarPartida(partidaFin);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Se han modificado correctamente los datos de la partida";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=partida-listado";

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
