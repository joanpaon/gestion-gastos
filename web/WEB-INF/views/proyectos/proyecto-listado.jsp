<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Proyecto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Session > Usuario
    Usuario usuario = (Usuario) (session.getAttribute("usuario"));

    // Datos Inyectados
    List<Proyecto> proyectos = (ArrayList<Proyecto>) request.getAttribute("proyectos");

    // Parametros Cabecera Listado
    String[] thField = {"id", "nombre", "propietario", "cuota"};
    String[] thName = {"Id", "Nombre", "Propietario", "Cuota"};

    // Parámetros
    request.setAttribute("tabla", "proyectos");
    request.setAttribute("entidad", "proyecto");
    request.setAttribute("titulo", "Listado de Proyectos");
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
        <% if (proyectos.size() > 1) {%>
        <%@include file="../../partials/general/paginator.jspf"%>
        <% }%>

        <% if (proyectos.isEmpty()) { %>

        <h2>No hay proyectos disponibles</h2>

        <% } else {%>

        <table>

          <thead>
            <%@include file="../../partials/general/thead.jspf"%>
          </thead>

          <tbody>
            <%@include file="../../partials/proyectos/tbody.jspf"%>
          </tbody>

        </table>

        <% }%>

        <!-- Paginador Final -->
        <% if (usuario.getPerfilID() != Perfil.BASIC) {%>
        <%@include file="../../partials/general/paginator.jspf"%>
        <% }%>

      </main>

      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <!-- Scripts Página -->
    <script src="public/js/proyectos/proyecto-listado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
    <script src="public/js/partials/partial-paginator.js"></script>
  </body>
</html>
