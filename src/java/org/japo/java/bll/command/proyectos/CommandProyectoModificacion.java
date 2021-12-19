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
import org.japo.java.entities.EntityCuota;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;
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
    String page;

    // Entidad
    EntityProyecto proyectoIni;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    UsuarioDAL usuarioDAL = new UsuarioDAL();
    CuotaDAL cuotaDAL = new CuotaDAL();
    ProyectoDAL proyectoDAL = new ProyectoDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // Usuario Actual
        EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

        // request > ID Entidad
        int id = Integer.parseInt(request.getParameter("id"));

        // request > Operación
        String op = request.getParameter("op");

        // Entidad > JSP
        if (op == null || op.equals("captura")) {
          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado();
          pl.setUser(usuario);

          // BD > Entidades
          proyectoIni = proyectoDAL.obtenerProyecto(id);
          List<EntityUsuario> usuarios = usuarioDAL.obtenerUsuarios(pl);
          List<EntityCuota> cuotas = cuotaDAL.obtenerCuotas();

          // Inyección de Datos
          request.setAttribute("proyecto", proyectoIni);
          request.setAttribute("usuarios", usuarios);
          request.setAttribute("cuotas", cuotas);

          // JSP
          page = "proyectos/proyecto-modificacion";
        } else if (op.equals("proceso")) {
          // ID Entidad > Registro BD > Entidad
          proyectoIni = proyectoDAL.obtenerProyecto(id);

          // Request > Parámetros
          String nombre = request.getParameter("nombre").trim();
          String info = request.getParameter("info").trim();
          String icono = request.getParameter("icono").trim();
          int propietario;
          switch (usuario.getPerfilID()) {
            case EntityPerfil.DEVEL:
              propietario = Integer.parseInt(request.getParameter("propietario").trim());
              break;
            case EntityPerfil.ADMIN:
              propietario = Integer.parseInt(request.getParameter("propietario").trim());
              break;
            default:
                  propietario = usuario.getPerfilID();
          }
          int cuota = Integer.parseInt(request.getParameter("cuota").trim());

          // Parámetros > Entidad
          EntityProyecto proyectoFin = new EntityProyecto(nombre, info,
                  icono, propietario, cuota,
                  proyectoIni.getStatus(), proyectoIni.getData(),
                  proyectoIni.getCreatedAt(), proyectoIni.getUpdatedAt());

          // Ejecutar Operación
          boolean checkOK = proyectoDAL.modificarProyecto(proyectoFin);

          // Validar Operación
          page = checkOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
        } else {
          // Recurso NO Disponible
          page = "errors/page404";
        }
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