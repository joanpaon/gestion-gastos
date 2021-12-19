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
package org.japo.java.bll.command.permisos;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.PerfilDAL;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.EntityPermiso;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityProceso;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandPermisoInsercion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // Nombre JSP
    String page;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    PerfilDAL perfilDAL = new PerfilDAL();
    PermisoDAL permisoDAL = new PermisoDAL();
    ProcesoDAL procesoDAL = new ProcesoDAL();

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

        // Formulario Captura Datos
        if (op == null || op.equals("captura")) {
          // Parámetros Listado
          ParametrosListado pl = new ParametrosListado();
          pl.setUser(usuario);

          // BD > Lista de Procesos
          List<EntityProceso> procesos = procesoDAL.obtenerProcesos(pl);

          // BD > Lista de Perfiles
          List<EntityPerfil> perfiles = perfilDAL.obtenerPerfiles(pl);

          // Inyecta Datos > JSP
          request.setAttribute("procesos", procesos);
          request.setAttribute("perfiles", perfiles);

          // Nombre JSP
          page = "permisos/permiso-insercion";
        } else if (op.equals("proceso")) {
          // Request > Parámetros
          int proceso = Integer.parseInt(request.getParameter("proceso"));
          int perfil = Integer.parseInt(request.getParameter("perfil"));
          String info = request.getParameter("info");

          // Parámetros > Entidad
          EntityPermiso permiso = new EntityPermiso(proceso, perfil, info);

          // Entidad > Inserción BD - true | false
          boolean operacionOK = permisoDAL.insertarPermiso(permiso);

          // Validar Inserción BD
          page = operacionOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
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

    // Command > JSP
    forward(page);
  }
}
