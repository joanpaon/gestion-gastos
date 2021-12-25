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

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.japo.java.bll.AdminBLL;
import org.japo.java.bll.command.Command;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.AtributosLista;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CommandProyectoListado extends Command {

  @Override
  public void process() throws ServletException, IOException {
    // JSP
    String page = "messages/message";

    try {
      // Sesión
      HttpSession sesion = request.getSession(false);

      // Validar Sesión
      if (!UtilesGastos.validarSesion(sesion)) {
        seleccionarMensaje(MSG_SESION_INVALIDA);
      } else {
        // Capas de Negocio
        AdminBLL adminBLL = new AdminBLL(sesion);

        // Capas de Datos
        ProyectoDAL proyectoDAL = new ProyectoDAL(sesion);
        UsuarioDAL usuarioDAL = new UsuarioDAL(sesion);

        if (adminBLL.validarAccesoComando(getClass().getSimpleName())) {
          // Sesión > Usuario
          Usuario usuario = (Usuario) sesion.getAttribute("usuario");

          // Atributos de Lista
          AtributosLista al = new AtributosLista("gestion_gastos", "proyectos", usuario);

          // Request > ID Objetivo > Entidad Objetivo
          int objetivoId;
          try {
            objetivoId = Integer.parseInt(request.getParameter("objetivo-id"));
          } catch (NumberFormatException | NullPointerException e) {
            objetivoId = 0;
          }
          
          // Request > ID Objetivo > Entidad Objetivo
          Usuario objetivo = usuarioDAL.obtenerUsuario(objetivoId);
          
          // Entidad Objetivo > Atributos de Lista
          al.setObjetivo(objetivo);
          
          // Filtro > Atributos de Lista
          UtilesGastos.definirListaFiltro(al, request);

          // Ordenación > Atributos de Lista
          UtilesGastos.definirListaOrden(al, request);

          // Total de Filas > Atributos de Lista
          proyectoDAL.contarFilas(al);

          // Navegación > Atributos de Lista
          UtilesGastos.definirListaPagina(al, request);

          // BD > Lista de Proyectos
          List<Proyecto> proyectos = proyectoDAL.obtenerProyectos(al);

          // BD > Lista de Usuarios
          List<Usuario> usuarios = usuarioDAL.obtenerUsuarios();

          // Inyecta Datos Listado > JSP
          request.setAttribute("proyectos", proyectos);
          request.setAttribute("objetivo", objetivo);
          request.setAttribute("usuarios", usuarios);

          // Inyecta Filtro > JSP
          request.setAttribute("filtro-campos", al.getFiltro().getCampos());
          request.setAttribute("filtro-patron", al.getFiltro().getPatron());
          request.setAttribute("filtro-exacto", al.getFiltro().isExacto());

          // Inyecta Orden > JSP
          request.setAttribute("orden-campo", al.getOrden().getCampo());
          request.setAttribute("orden-avance", al.getOrden().getAvance());

          // Inyecta Pagina > JSP
          request.setAttribute("pagina-indice", al.getPagina().getIndice());
          request.setAttribute("pagina-filas-pagina", al.getPagina().getFilasPagina());
          request.setAttribute("pagina-filas-total", al.getPagina().getFilasTotal());

          // JSP
          page = "proyectos/proyecto-listado";
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
