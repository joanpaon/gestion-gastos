<%@page import="org.japo.java.entities.Proceso"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      List<Proceso> listaPrc = (ArrayList<Proceso>) request.getAttribute("proceso-lista");
      List<Usuario> listaUsr = (ArrayList<Usuario>) request.getAttribute("usuario-lista");
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
    <link rel="stylesheet" href="public/css/usuariopermisos/usuario-permiso-insercion.css" /> 
  </head>
  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Incorporación de Permisos de Usuario</h2>
          <a class="btn btn-listar" accept-charset="Windows-1252" 
             href="controller?cmd=usuario-permiso-listado">Listado</a>
        </header> 
        <form action="controller?cmd=usuario-permiso-insercion&op=proceso"
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
            <label for="usuario">Usuario</label>
            <select id="usuario" name="usuario">
              <option disabled selected value></option>
              <% for (Usuario g : listaUsr) {%>
              <option value="<%= g.getId()%>"><%= g.getUser()%></option>
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
    <script src="public/js/usuariopermisos/usuario-permiso-insercion.js"></script>
  </body>
</html>
