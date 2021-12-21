<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Usuario usuario = (Usuario) request.getAttribute("usuario");
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
    <link rel="stylesheet" href="public/css/usuarios/usuario-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Usuarios</h2>
          <a class="btn btn-listar" href="controller?cmd=usuario-listado">Listado</a>
        </header> 
        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=usuario-modificacion&id=<%= usuario.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user"
                   pattern="\w{3,20}" required                  
                   value="<%= usuario.getUser()%>"/>
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="text" name="pass" 
                   pattern="\w{3,20}" required    
                   value="<%= usuario.getPass()%>"/>
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required
                   value="<%= usuario.getEmail()%>"/>
          </div>
          <div class="fieldset">
            <label for="icono">Icono</label>
            <textarea id="avatar" name="icono" rows="6"/><%= usuario.getIcono()%></textarea>
          </div>
          <div class="fieldset">
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil">
              <% for (Perfil perfil : perfiles) { %>
              <% if (perfil.getId() == usuario.getPerfilID()) {%>
              <option value="<%= perfil.getId()%>" selected><%= perfil.getNombre()%></option>
              <% } else {%>
              <option value="<%= perfil.getId()%>"><%= perfil.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" 
                   value="<%= usuario.getInfo()%>"/>
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/usuarios/usuario-modificacion.js"></script>
  </body>
</html>
