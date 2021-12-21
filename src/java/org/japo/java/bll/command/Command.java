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

  public static final String MSG_OPERACION_REALIZADA = "operacion-realizada";
  public static final String MSG_OPERACION_CANCELADA = "operacion-cancelada";
  public static final String MSG_SESION_INVALIDA = "sesion-invalida";
  public static final String MSG_SESION_CADUCADA = "sesion-caducada";
  public static final String MSG_ACCESO_DENEGADO = "acceso-denegado";
  public static final String MSG_ERROR404 = "error404";

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

  protected String seleccionarMensaje(String selector) {
    // Parámetros Mensaje
    String titulo = "Situación Indefinida";
    String mensaje = "Identificando las circunstancias de esta situación";
    String imagen = "public/img/asking.png";
    String destino = "controller?cmd=landing";
    String page = "messages/message";

    switch (selector) {
      case MSG_OPERACION_REALIZADA:
        titulo = "Operación Realizada con Éxito";
        mensaje = "Se han borrado correctamente los datos seleccionados";
        imagen = "public/img/tarea.png";
        destino = "controller?cmd=usuario-listado";
        break;
      case MSG_OPERACION_CANCELADA:
        titulo = "Operación Cancelada";
        mensaje = "No se ha podido completar la operación";
        imagen = "public/img/cancelar.png";
        destino = "javascript:window.history.back();";
        break;
      case MSG_SESION_CADUCADA:
        titulo = "Sesión Caducada";
        mensaje = "Identifíquese para continuar su trabajo";
        imagen = "public/img/expired.jpg";
        destino = "controller?cmd=login";
        break;
      case MSG_SESION_INVALIDA:
        titulo = "Sesión Inválida";
        mensaje = "Identifíquese para continuar su trabajo";
        imagen = "public/img/expired.jpg";
        destino = "controller?cmd=login";
        break;
      case MSG_ACCESO_DENEGADO:
        titulo = "Acceso NO Autorizado";
        mensaje = "Nivel de Acceso Insuficiente para ese Recurso";
        imagen = "public/img/cancelar.png";
        destino = "javascript:window.history.back();";
        break;
      case MSG_ERROR404:
        titulo = "Operación Cancelada";
        mensaje = "Intento de acceso a un recurso NO disponible";
        imagen = "public/img/cancelar.png";
        destino = "javascript:window.history.back();";
        break;
    }

    // Inyecta Datos
    parametrizarMensaje(titulo, mensaje, imagen, destino);

    // Devuelve el nombre de la página
    return page;
  }

  protected void parametrizarMensaje(String titulo, String mensaje, 
          String imagen, String destino) {
    // Inyecta Datos
    request.setAttribute("titulo", titulo);
    request.setAttribute("mensaje", mensaje);
    request.setAttribute("imagen", imagen);
    request.setAttribute("destino", destino);
  }
}
