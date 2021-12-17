<%@page import="org.japo.java.entities.Proceso"%>
<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      List<Proceso> listaPrc = (ArrayList<Proceso>) request.getAttribute("lista-procesos");
      List<Perfil> listaGrp = (ArrayList<Perfil>) request.getAttribute("lista-perfiles");
  %>

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
    <link rel="stylesheet" href="public/css/perfilpermisos/perfil-permiso-insercion.css" /> 
  </head>
  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Incorporación de Permisos de Perfil</h2>
          <a class="btn btn-listar" accept-charset="Windows-1252" 
             href="controller?cmd=perfil-permiso-listado">Listado</a>
        </header> 
        <form action="controller?cmd=perfil-permiso-insercion&op=proceso"
              method="post" accept-charset="Windows-1252">
          <div class="fieldset">
            <label for="proceso">Proceso</label>
            <select id="proceso" name="proceso">
              <option disabled selected value></option>
              <% for (Proceso p : listaPrc) {%>
              <option value="<%= p.getId()%>"><%= p.getInfo()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil">
              <option disabled selected value></option>
              <% for (Perfil g : listaGrp) {%>
              <option value="<%= g.getId()%>"><%= g.getNombre()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" />
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Aceptar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/perfilpermisos/perfil-permiso-insercion.js"></script>
  </body>
</html>
