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
package org.japo.java.bll.command.cuotas;

import org.japo.java.bll.command.Command;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.dal.CuotaDAL;
import org.japo.java.entities.EntityCuota;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandCuotaModificacion extends Command {

  @Override
  @SuppressWarnings("ConvertToStringSwitch")
  public void process() throws ServletException, IOException {
    // JSP
    String page;

    // Entidad
    EntityCuota cuotaIni;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    CuotaDAL cuotaDAL = new CuotaDAL();

    try {
      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        page = "errors/sesion-caducada";
        // Validar Acceso
      } else if (adminBLL.validarAccesoComando(sesion, getClass().getSimpleName())) {
        // request > ID Entidad
        int id = Integer.parseInt(request.getParameter("id"));

        // request > Operación
        String op = request.getParameter("op");

        // Entidad > JSP
        if (op == null || op.equals("captura")) {
          // ID Entidad > Registro BD > Entidad
          cuotaIni = cuotaDAL.obtenerCuota(id);

          // Validar Operación
          if (cuotaIni == null) {
            // Recurso NO Disponible
            page = "errors/page404";
          } else {
            // Inyección de Datos
            request.setAttribute("cuota", cuotaIni);

            // JSP
            page = "cuotas/cuota-modificacion";
          }
          // JSP > BD Actualizada
        } else if (op.equals("proceso")) {
          // ID Entidad > Registro BD > Entidad
          cuotaIni = cuotaDAL.obtenerCuota(id);

          // Request > Parámetros
          String nombre = request.getParameter("nombre").trim();
          String info = request.getParameter("info").trim();

          // Parámetros > Entidad
          EntityCuota cuotaFin = new EntityCuota(id, nombre, info,
                  cuotaIni.getStatus(), cuotaIni.getData(),
                  cuotaIni.getCreatedAt(), cuotaIni.getUpdatedAt());

          // Ejecutar Operación
          boolean procesoOK = cuotaDAL.modificarCuota(cuotaFin);

          // Validar Operación
          page = procesoOK ? "success/operacion-realizada" : "errors/operacion-cancelada";
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
