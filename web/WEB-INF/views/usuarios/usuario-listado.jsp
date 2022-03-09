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

    // Parametros Cabecera Listado
    String[] thField = {"id", "user", "perfil"};
    String[] thName = {"Id", "Nombre", "Perfil"};

    // Parámetros
    request.setAttribute("tabla", "usuarios");
    request.setAttribute("entidad", "usuario");
    request.setAttribute("titulo", "Listado de Usuarios");
    request.setAttribute("th-field", thField);
    request.setAttribute("th-name", thName);
%>

<!DOCTYPE html>
<html lang="es">

  <head>
    <%@include file="../../partials/crud/listado-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <!-- Cabecera de la Página -->
      <%@include file="../../partials/general/header.jspf"%>

      <main>

        <!-- Cabecera del Listado -->
        <%@include file="../../partials/crud/listado-header.jspf"%>

        <!-- Paginador Inicial -->
        <% if (usuarios.size() > 1) {%>
        <%@include file="../../partials/general/paginator.jspf"%>
        <% }%>

        <% if (usuarios.isEmpty()) { %>

        <h2>No hay usuarios disponibles</h2>

        <% } else {%>

        <table>

          <thead>
            <%@include file="../../partials/general/thead.jspf"%>
          </thead>

          <tbody>
            <%@include file="../../partials/usuarios/tbody.jspf"%>
          </tbody>

        </table>

        <% }%>

        <!-- Paginador Final -->
        <% if (usuario.getPerfilID() != Perfil.BASIC) {%>
        <%@include file="../../partials/general/paginator.jspf"%>
        <% }%>

      </main>

      <!-- Pie de la Página -->
      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <!-- Scripts Página -->
    <script src="public/js/usuarios/usuario-listado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
    <script src="public/js/partials/partial-paginator.js"></script>
  </body>
</html>
