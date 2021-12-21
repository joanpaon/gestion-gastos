<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.japo.java.entities.Proyecto"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Cuota"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Proyecto proyecto = (Proyecto) request.getAttribute("proyecto");
  List<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
  List<Cuota> cuotas = (ArrayList<Cuota>) request.getAttribute("cuotas");
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
    <link rel="stylesheet" href="public/css/proyectos/proyecto-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Proyectos</h2>
          <a class="btn btn-listar" href="controller?cmd=proyecto-listado">Listado</a>
        </header> 

        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=proyecto-modificacion&id=<%= proyecto.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="nombre">Nombre</label>
            <input id="nombre" type="text" name="nombre" value="<%= proyecto.getNombre()%>"/>
          </div>
          <div class="fieldset">
            <label for="propietario">Propietario</label>
            <select id="propietario" name="propietario">
              <% for (Usuario u : usuarios) { %>
              <% if (u.getId() == proyecto.getPropietarioID()) {%>
              <option value="<%= u.getId()%>" selected><%= u.getUser()%></option>
              <% } else {%>
              <option value="<%= u.getId()%>"><%= u.getUser()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="cuota">Cuota</label>
            <select id="cuota" name="cuota">
              <% for (Cuota c : cuotas) { %>
              <% if (c.getId() == proyecto.getCuotaID()) {%>
              <option value="<%= c.getId()%>" selected><%= c.getNombre()%></option>
              <% } else {%>
              <option value="<%= c.getId()%>"><%= c.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" value="<%= proyecto.getInfo()%>"/>
          </div>
          <div class="fieldset">
            <label for="icono">Icono</label>
            <textarea id="icono" name="icono" rows="6"/><%= proyecto.getIcono()%></textarea>
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/proyectos/proyecto-modificacion.js"></script>
  </body>
</html>
