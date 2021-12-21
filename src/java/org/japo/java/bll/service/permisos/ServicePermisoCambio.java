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
package org.japo.java.bll.service.permisos;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.entities.Permiso;
import org.japo.java.bll.service.Service;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServicePermisoCambio extends Service {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String json;

    try {
      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        // Recurso NO Disponible
        json = "{\"response\": \"Sesión Caducada\"}";
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        PermisoDAL permisoDAL = new PermisoDAL(sesion);

        // Validar Acceso
        if (adminBLL.validarAccesoServicio(getClass().getSimpleName())) {
          // Request > Id Proceso
          int proceso = Integer.parseInt(request.getParameter("process"));

          // Request > Id Grupo
          int grupo = Integer.parseInt(request.getParameter("group"));

          // Proceso + Grupo > Nuevo Permiso de Perfil
          Permiso pp = new Permiso(0, proceso, grupo, null, 0, null, null, null);

          // Request > Estado
          boolean estado = Boolean.parseBoolean(request.getParameter("state"));

          // Permiso de Perfil + Estado > Gestion Permisos Perfil
          boolean procesoOK = estado ? permisoDAL.insertarPermiso(pp) : permisoDAL.borrarPermiso(pp);

          // Resultado > JSON
          if (procesoOK) {
            json = "{\"ok\":true, \"msg\":\"Permiso SI Cambiado\"}";
          } else {
            json = "{\"ok\":false, \"msg\":\"Permiso NO Cambiado\"}";
          }
        } else {
          json = "{\"ok\":false, \"msg\":\"Acceso NO Autorizado\"}";
        }
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Recurso NO Disponible
      json = "{\"ok\":false, \"msg\":\"Recurso NO Disponible\"}";
    }

    // Redirección JSP
    forward(json);
  }
}
