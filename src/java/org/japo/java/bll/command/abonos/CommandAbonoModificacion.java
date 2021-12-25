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

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.command.Command;
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
public final class CommandAbonoModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Entidad
      Abono abonoIni;

      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        AbonoDAL abonoDAL = new AbonoDAL(sesion);
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);
        UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // request > ID Operación
          String op = request.getParameter("op");

          // Captura de Datos
          if (op == null || op.equals("captura")) {
            // ID Entidad > Objeto Entidad
            abonoIni = abonoDAL.obtenerAbono(id);

            // BD > Lista de Usuarios
            List<Usuario> usuarios = usuarioDAL.obtenerUsuarios();

            // BD > Lista de Proyectos
            List<Proyecto> proyectos = proyectoDAL.obtenerProyectos();

            // Inyectar Datos > JSP
            request.setAttribute("abono", abonoIni);
            request.setAttribute("proyectos", proyectos);
            request.setAttribute("usuarios", usuarios);

            // JSP
            page = "abonos/abono-modificacion";
          } else if (op.equals("proceso")) {
            // ID > Objeto Entidad
            abonoIni = abonoDAL.obtenerAbono(id);

            // Request > Parámetros
            int proyecto = Integer.parseInt(request.getParameter("proyecto").trim());
            int usuario = Integer.parseInt(request.getParameter("usuario").trim());
            String info = request.getParameter("info").trim();

            // Parámetros > Entidad
            Abono abonoFin = new Abono(id, proyecto, usuario, info,
                    abonoIni.getStatus(), abonoIni.getData(),
                    abonoIni.getCreatedAt(), abonoIni.getUpdatedAt());

            // Entidad > Modificación Registro BD
            boolean checkOK = abonoDAL.modificarAbono(abonoFin);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Se han modificado correctamente los datos del usuario";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=usuario-listado";

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
