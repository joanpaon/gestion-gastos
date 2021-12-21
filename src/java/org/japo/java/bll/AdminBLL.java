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
package org.japo.java.bll;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AdminBLL {

  // Sesión
  HttpSession sesion;

  // Capas de Datos
  private final PermisoDAL permisoDAL;
  private final ProcesoDAL procesoDAL;

  public AdminBLL(HttpSession sesion) {
    this.sesion = sesion;

    permisoDAL = new PermisoDAL(sesion);
    procesoDAL = new ProcesoDAL(sesion);
  }

  public boolean validarAccesoComando(String comando) {
    // Semáforo
    boolean checkOK;

    try {
      // Sesion > Usuario
      Usuario usuario = (Usuario) sesion.getAttribute("usuario");

      // Usuario > Perfil
      int perfil = usuario.getPerfilID();

      // Validar Perfil Desarrollador
      if (perfil == Perfil.DEVEL) {
        checkOK = true;
      } else {
        // Perfil + BD > Lista de Comandos
        List<Permiso> permisos = permisoDAL.obtenerPermisos(perfil);

        // Nombre Comando > Entidad Comando
        Proceso proceso = procesoDAL.obtenerProceso(comando);

        // Valida Acceso Comando
        int posicion = UtilesGastos.buscarProcesoLista(proceso, permisos);

        // Semaforo: true | false
        checkOK = posicion > -1;
      }
    } catch (Exception e) {
      checkOK = false;
    }

    // Retorno: true | false
    return checkOK;
  }

  public boolean validarAccesoServicio(String servicio) {
    return true;
  }
}
