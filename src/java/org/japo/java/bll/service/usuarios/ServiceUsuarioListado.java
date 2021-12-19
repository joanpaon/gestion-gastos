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
package org.japo.java.bll.service.usuarios;

import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
//import org.japo.java.entities.Grupo;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.bll.service.Service;
import org.japo.java.dal.UsuarioDAL;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ServiceUsuarioListado extends Service {

  @Override
  public void process() throws ServletException, IOException {
    // JSON Salida
    String json;

    // Sesión
    HttpSession sesion = request.getSession(false);

    // Capas de Negocio
    AdminBLL adminBLL = new AdminBLL();

    // Capas de Datos
    UsuarioDAL usuarioDAL = new UsuarioDAL();

    try {
      // Validar Acceso
      if (adminBLL.validarAccesoServicio(sesion, getClass().getSimpleName())) {
        // Lista de Usuarios
        List<EntityUsuario> listaUsr = usuarioDAL.obtenerUsuarios();

        // List > JSON
        json = new Gson().toJson(listaUsr);

        // Objeto Gson
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // List > JSON
//                json = gson.toJson(listaUsr);
      } else {
        // Acceso NO Autorizado
        json = "{'response': 'Acceso NO Autorizado'}";
      }
    } catch (Exception e) {
      // Recurso NO Disponible
      json = "{'response': 'Recurso NO Disponible'}";
    }

    // Envío JSON
    forward(json);
  }
}
