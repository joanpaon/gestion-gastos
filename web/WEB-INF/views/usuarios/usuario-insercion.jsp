<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
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
    <link rel="stylesheet" href="public/css/usuarios/usuario-insercion.css" /> 
  </head>
  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Incorporación de Usuarios</h2>
          <a class="btn btn-perfiles" accept-charset="Windows-1252"
             href="controller?cmd=usuario-listado">Listado</a>
        </header> 
        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=usuario-insercion&op=proceso">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user" 
                   pattern="\w{3,20}" required />
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="text" name="pass" 
                   pattern="\w{3,20}" required />
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required />
          </div>
          <div class="fieldset">
            <label for="icono">Avatar</label>
            <textarea id="icono" name="icono" rows="6"></textarea>
          </div>
          <div class="fieldset">
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil">
              <option disabled selected value></option>
              <% for (Perfil perfil : perfiles) {%>
              <option value="<%= perfil.getId()%>"><%= perfil.getNombre()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" />
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/usuarios/usuario-insercion.js"></script>
  </body>
</html>
