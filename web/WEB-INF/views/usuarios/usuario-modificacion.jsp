<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Usuario u = (Usuario) request.getAttribute("usuario");
      List<Perfil> lista = (ArrayList<Perfil>) request.getAttribute("lista");
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
              action="controller?cmd=usuario-modificacion&id=<%= u.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user"
                   pattern="\w{3,20}" required                  
                   value="<%= u.getUser()%>"/>
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="text" name="pass" 
                   pattern="\w{3,20}" required    
                   value="<%= u.getPass()%>"/>
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required
                   value="<%= u.getEmail()%>"/>
          </div>
          <div class="fieldset">
            <label for="avatar">Avatar</label>
            <textarea id="avatar" name="avatar" rows="6"/><%= u.getIcono()%></textarea>
          </div>
          <div class="fieldset">
            <label for="lista">Perfil</label>
            <select id="lista" name="lista">
              <% for (Perfil g : lista) { %>
              <% if (g.getId() == u.getPerfil()) {%>
              <option value="<%= g.getId()%>" selected><%= g.getNombre()%></option>
              <% } else {%>
              <option value="<%= g.getId()%>"><%= g.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" 
                   value="<%= u.getInfo()%>"/>
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
