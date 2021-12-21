<%@page import="org.japo.java.entities.Proceso"%>
<%@page import="org.japo.java.entities.Permiso"%>
<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Permiso permiso = (Permiso) request.getAttribute("permiso");
  List<Proceso> procesos = (ArrayList<Proceso>) request.getAttribute("procesos");
  List<Perfil> perfiles = (ArrayList<Perfil>) request.getAttribute("perfiles");
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
    <link rel="stylesheet" href="public/css/permisos/permiso-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Permisos de Perfil</h2>
          <a class="btn btn-listar" href="controller?cmd=permiso-listado">Listado</a>
        </header> 
        <form method="post" accept-charset="Windows-1252" 
              action="controller?cmd=permiso-modificacion&id=<%= permiso.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="proceso">Proceso</label>
            <select id="proceso" name="proceso">
              <% for (Proceso proceso : procesos) { %>
              <% if (proceso.getId() == permiso.getProcesoId()) {%>
              <option value="<%= proceso.getId()%>" selected><%= proceso.getInfo()%></option>
              <% } else {%>
              <option value="<%= proceso.getId()%>"><%= proceso.getInfo()%></option>
              <% }%>
              <% }%>
            </select>
          </div>          
          <div class="fieldset">
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil">
              <% for (Perfil perfil : perfiles) { %>
              <% if (perfil.getId() == permiso.getPerfilId()) {%>
              <option value="<%= perfil.getId()%>" selected><%= perfil.getNombre()%></option>
              <% } else {%>
              <option value="<%= perfil.getId()%>"><%= perfil.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" value="<%= permiso.getInfo()%>" required />
          </div>          
          <div class="botones">
            <button class="btn btn-submit" type="submit">Aceptar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/permisos/permiso-modificacion.js"></script>
  </body>
</html>
