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
package org.japo.java.bll.command.proyectos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.CuotaDAL;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Entidad
      Proyecto proyectoIni;

      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);
        CuotaDAL cuotaDAL = new CuotaDAL(sesion);
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // request > ID Entidad
          int id = Integer.parseInt(request.getParameter("id"));

          // request > Operación
          String op = request.getParameter("op");

          // Entidad > JSP
          if (op == null || op.equals("captura")) {
            // ID Entidad > Objeto Entidad
            proyectoIni = proyectoDAL.obtenerProyecto(id);

            // BD > Lista de Usuarios
            List<Usuario> usuarios = usuarioDAL.obtenerUsuarios();

            // BD > Lista de Cuotas
            List<Cuota> cuotas = cuotaDAL.obtenerCuotas();

            // Inyección de Datos
            request.setAttribute("proyecto", proyectoIni);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("cuotas", cuotas);

            // JSP
            page = "proyectos/proyecto-modificacion";
          } else if (op.equals("proceso")) {
            // Usuario Actual
            Usuario usuario = (Usuario) sesion.getAttribute("usuario");

            // ID Entidad > Registro BD > Entidad
            proyectoIni = proyectoDAL.obtenerProyecto(id);

            // Request > Parámetros
            String nombre = request.getParameter("nombre").trim();
            String info = request.getParameter("info").trim();
            String icono = request.getParameter("icono").trim();

            int propietario;
            switch (usuario.getPerfilID()) {
              case Perfil.DEVEL:
                propietario = Integer.parseInt(request.getParameter("propietario").trim());
                break;
              case Perfil.ADMIN:
                propietario = Integer.parseInt(request.getParameter("propietario").trim());
                break;
              default:
                propietario = usuario.getPerfilID();
            }
            int cuota = Integer.parseInt(request.getParameter("cuota").trim());

            // Parámetros > Entidad
            Proyecto proyectoFin = new Proyecto(nombre, info,
                    icono, propietario, cuota,
                    proyectoIni.getStatus(), proyectoIni.getData(),
                    proyectoIni.getCreatedAt(), proyectoIni.getUpdatedAt());

            // Ejecutar Operación
            boolean checkOK = proyectoDAL.modificarProyecto(proyectoFin);

            // Validar Operación
            if (checkOK) {
              // Parámetros
              String titulo = "Operación Realizada con Éxito";
              String mensaje = "Datos modificados correctamente";
              String imagen = "public/img/tarea.png";
              String destino = "controller?cmd=proyecto-listado";

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
