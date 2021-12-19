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
package org.japo.java.bll.command;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public abstract class Command implements ICommand {

  // Interfaz de Comunicación con el Contenedor
  protected ServletContext context;

  // Petición del Cliente Encapsulada
  protected HttpServletRequest request;

  // Respuesta al Cliente Encapsulada
  protected HttpServletResponse response;

  // Inicialización del Comando
  @Override
  public void init(
          ServletContext context,
          HttpServletRequest request,
          HttpServletResponse response) {
    this.context = context;
    this.request = request;
    this.response = response;
  }

  // Ejecución del Comando
  protected void forward(String cmd) throws ServletException, IOException {
    // Validar Tipo de Salida
    if (cmd.startsWith("controller")) {
      response.sendRedirect(cmd);
    } else {
      // Nombre Comando ( Petición ) > Nombre Vista ( Respuesta )
      cmd = String.format("/WEB-INF/views/%s.jsp", cmd.toLowerCase());
      
      // Contexto + Nombre Vista > Despachador
      RequestDispatcher dispatcher = context.getRequestDispatcher(cmd);

      // Despachador + Petición + Respuesta > Redirección a Vista
      dispatcher.forward(request, response);
    }
  }
}
