<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.libraries.UtilesGastos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Sesión > Usuario
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    // Datos Inyectados
    List<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html lang="es">
  <head>
    <% request.setAttribute("tabla", "usuarios");%>
    <%@include file="../../partials/partial-listado-page-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/partial-header.jspf"%>

      <main>
        <% request.setAttribute("titulo-listado", "Listado de Usuarios");%>
        <%@include file="../../partials/partial-list-header.jspf"%>

        <% if (usuarios.size() > 1) {%>
        <%@include file="../../partials/partial-paginator.jspf"%>
        <% }%>

        <% if (usuarios.isEmpty()) { %>

        <h2>No hay usuarios disponibles</h2>

        <% } else { %>

        <table>
          <thead>
          <th>
            <% request.setAttribute("th-field", "id");%>
            <% request.setAttribute("th-name", "Id");%>
            <%@include file="../../partials/partial-table-header01.jspf"%>
          </th>
          <th>
            <% request.setAttribute("th-field", "user");%>
            <% request.setAttribute("th-name", "Nombre");%>
            <%@include file="../../partials/partial-table-header02.jspf"%>
          </th>
          <th>
            <% request.setAttribute("th-field", "perfil");%>
            <% request.setAttribute("th-name", "Perfil");%>
            <%@include file="../../partials/partial-table-header03.jspf"%>
          </th>
          <th>Acciones</th>
          </thead>

          <tbody>
            <% for (Usuario _usuario : usuarios) {%>
            <tr>
              <td><%= _usuario.getId()%></td>
              <td><%= _usuario.getUser()%></td>
              <td><%= _usuario.getPerfilInfo()%></td>
              <td>
                <a class="btn btn-consultar" 
                   href="controller?cmd=usuario-consulta&id=<%= _usuario.getId()%>" 
                   title="Consulta">C</a>
                <a class="btn btn-modificar" 
                   href="controller?cmd=usuario-modificacion&id=<%= _usuario.getId()%>" 
                   title="Modificación">M</a>
                <a class="btn btn-borrar" 
                   href="controller?cmd=usuario-borrado&id=<%= _usuario.getId()%>" 
                   title="Eliminación">B</a>
              </td>
            </tr>
            <% } %>
          </tbody>
        </table>

        <% }%>

        <% if (usuario.getPerfilID() != Perfil.BASIC) {%>
        <%@include file="../../partials/partial-paginator.jspf"%>
        <% }%>
      </main>

      <%@include file="../../partials/partial-footer.jspf"%>
    </div>

    <!-- Scripts Página -->
    <script src="public/js/usuarios/usuario-listado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
    <script src="public/js/partials/partial-paginator.js"></script>
  </body>
</html>
