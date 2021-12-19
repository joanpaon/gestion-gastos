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
import org.japo.java.entities.EntityPartida;
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPartidaInsercion extends Command {

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
    PartidaDAL partidaDAL = new PartidaDAL();
    ProyectoDAL proyectoDAL = new ProyectoDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Usuario Actual
        EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

        // Obtener Operación
        String op = request.getParameter("op");

        // Invoca Formulario de Captura de Datos
        if (op == null || op.equals("captura")) {
          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado();
          pl.setUser(usuario);

          // Lista de Proyectos - Dependiendo del perfil
          List<EntityProyecto> proyectos = proyectoDAL.obtenerProyectos(pl);

          // Inyección de Datos
          request.setAttribute("proyectos", proyectos);

          // JSP
          page = "partidas/partida-insercion";
        } else if (op.equals("proceso")) {
          // Request > Parámetros
          String nombre = request.getParameter("nombre").trim();
          String info = request.getParameter("info").trim();
          String icono = request.getParameter("icono").trim();
          int proyectoID = Integer.parseInt(request.getParameter("proyecto").trim());

          // Parámetros > Entidad
          EntityPartida partida = new EntityPartida(nombre, info, icono, proyectoID);

          // Entidad > Inserción BD - true | false
          boolean procesoOK = partidaDAL.insertarPartida(partida);

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
