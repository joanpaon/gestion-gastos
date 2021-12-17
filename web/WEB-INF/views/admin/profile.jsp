<%@page import="org.japo.java.entities.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Usuario u = (Usuario) request.getAttribute("usuario");
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
    <link rel="stylesheet" href="public/css/admin/profile.css" /> 
  </head>
  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Perfil de Usuarios</h2>
          <a class="btn btn-listar" accept-charset="Windows-1252"
             href="controller?cmd=login">Inicio</a>
        </header> 
        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=profile&op=proceso&id=<%= u.getId()%>">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user"
                   required
                   pattern="\w{3,20}"
                   autocomplete="username"
                   value="<%= u.getUser()%>"/>
            <div class="feedback user">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="password" name="pass"               
                   required
                   pattern="\w{3,20}"
                   autocomplete="current-password"
                   value="<%= u.getPass()%>"/>
            <div class="feedback pass">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="conf">Contraseña</label>
            <input id="conf" type="password" 
                   required
                   pattern="\w{3,20}"
                   autocomplete="current-password"
                   value="<%= u.getPass()%>"/>
            <div class="feedback conf">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required 
                   value="<%= u.getEmail()%>"/>
            <div class="feedback email">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="icono">Icono</label>
            <textarea id="icono" name="icono" rows="6"><%= u.getIcono()%></textarea>
            <div class="feedback icono">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" 
                   value="<%= u.getInfo()%>"/>
            <div class="feedback info">&#9888;</div>
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
    <script src="public/js/admin/profile.js"></script>
  </body>
</html>
