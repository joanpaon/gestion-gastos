<%@page import="org.japo.java.entities.Abono"%>
<%@page import="org.japo.java.entities.Proyecto"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Abono abono = (Abono) request.getAttribute("abono");
  List<Proyecto> proyectos = (ArrayList<Proyecto>) request.getAttribute("proyectos");
  List<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html lang="es">

  <head>
    <!-- These lines go in the first 1024 bytes -->
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>Gestión de Gastos</title>

    <!-- References -->
    <meta name="author" content="2021 - José A. Pacheco Ondoño - japolabs@gmail.com" />
    <meta name="description" content="Gestión de Gastos" />

    <!-- Configuration -->
    <meta name="keywords" content="" />
    <meta name="robots" content="noindex, nofollow" />

    <!-- Viewport Setup for mobile devices -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- Favicon -->
    <link href="public/img/favicon.ico" rel="icon" type="image/x-icon" />

    <!-- Style Sheet Links -->
    <link rel="stylesheet" href="public/css/abonos/abono-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Abonos</h2>
          <a class="btn btn-listar" href="controller?cmd=abono-listado">Listado</a>
        </header> 

        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=abono-modificacion&id=<%= abono.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="proyecto">Proyecto</label>
            <select id="proyecto" name="proyecto">
              <% for (Proyecto proyecto : proyectos) { %>
              <% if (proyecto.getId() == abono.getProyectoID()) {%>
              <option value="<%= proyecto.getId()%>" selected><%= proyecto.getNombre()%></option>
              <% } else {%>
              <option value="<%= proyecto.getId()%>"><%= proyecto.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="usuario">Usuario</label>
            <select id="usuario" name="usuario">
              <% for (Usuario usuario : usuarios) { %>
              <% if (usuario.getId() == abono.getUsuarioID()) {%>
              <option value="<%= usuario.getId()%>" selected><%= usuario.getUser()%></option>
              <% } else {%>
              <option value="<%= usuario.getId()%>"><%= usuario.getUser()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" value="<%= abono.getInfo()%>" required />
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Scripts -->
    <script src="public/js/abonos/abono-modificacion.js"></script>
  </body>
</html>
