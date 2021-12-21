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
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandAbonoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
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
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);
        UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // Obtener Operación
          String op = request.getParameter("op");

          // Formulario Captura Datos
          if (op == null || op.equals("captura")) {
            // BD > Lista de Proyectos
            List<Proyecto> proyectos = proyectoDAL.obtenerProyectos();

            // BD > Lista de Usuarios
            List<Usuario> usuarios = usuarioDAL.obtenerUsuarios();

            // Inyección Datos
            request.setAttribute("proyectos", proyectos);
            request.setAttribute("usuarios", usuarios);

            // JSP
            page = "abonos/abono-insercion";
          } else if (op.equals("proceso")) {
            // Request > Parámetros
            int proyecto = Integer.parseInt(request.getParameter("proyecto").trim());
            int usuario = Integer.parseInt(request.getParameter("usuario").trim());
            String info = request.getParameter("info").trim();

            // Parámetros > Entidad
            Abono abono = new Abono(proyecto, usuario, info);

            // Entidad > Inserción BD - true | false
            boolean checkOK = abonoDAL.insertarAbono(abono);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Se han modificado correctamente los datos del usuario";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=abono-listado";

              // Inyeccion de Parámetros
              parametrizarMensaje(titulo, mensaje, imagen, destino);
            } else {
              // Parámetros
              String titulo = "Operación Cancelada";
              String mensaje = "No se han modificado los datos del usuario";
              String imagen = "public/img/cancelar.png";
              String destino = "javascript:window.history.back();";

              // Inyeccion de Parámetros
              parametrizarMensaje(titulo, mensaje, imagen, destino);
            }
          } else {
            // Parámetros
            String titulo = "Operación Cancelada";
            String mensaje = "Intento de acceso a un recurso NO disponible";
            String imagen = "public/img/cancelar.png";
            String destino = "javascript:window.history.back();";

            // Inyeccion de Parámetros
            parametrizarMensaje(titulo, mensaje, imagen, destino);
          }
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

    // Command > JSP
    forward(page);
  }
}
