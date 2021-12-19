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
import org.japo.java.entities.EntityPermiso;
import org.japo.java.bll.service.Service;
import org.japo.java.dal.PermisoDAL;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServicePermisoCambio extends Service {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String json;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capa de Datos
    PermisoDAL dal = new PermisoDAL();

    try {
      // Validar Acceso
      if (adminBLL.validarAccesoServicio(sesion, getClass().getSimpleName())) {
        // Request > Id Proceso
        int proceso = Integer.parseInt(request.getParameter("process"));

        // Request > Id Grupo
        int grupo = Integer.parseInt(request.getParameter("group"));

        // Proceso + Grupo > Nuevo EntityPermiso de Perfil
        EntityPermiso pp = new EntityPermiso(0, proceso, grupo, null, 0, null, null, null);

        // Request > Estado
        boolean estado = Boolean.parseBoolean(request.getParameter("state"));

        // EntityPermiso de Perfil + Estado > Gestion Permisos Perfil
        boolean procesoOK = estado ? dal.insertarPermiso(pp) : dal.borrarPermiso(pp);

        // Resultado > JSON
        if (procesoOK) {
          json = "{\"ok\":true, \"msg\":\"Permiso SI Cambiado\"}";
        } else {
          json = "{\"ok\":false, \"msg\":\"Permiso NO Cambiado\"}";
        }
      } else {
        json = "{\"ok\":false, \"msg\":\"Acceso NO Autorizado\"}";
      }
    } catch (NumberFormatException | NullPointerException e) {
      // Recurso NO Disponible
      json = "{\"ok\":false, \"msg\":\"Recurso NO Disponible\"}";
    }

    // Redirección JSP
    forward(json);
  }
}
