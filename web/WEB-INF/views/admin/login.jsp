<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
    <link rel="stylesheet" href="public/css/admin/login.css" />
  </head>

  <body>
    <!-- Web Content-->
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />
      
      <main>
        <form action="controller?cmd=login&op=proceso" 
              method="post" accept-charset="Windows-1252">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input
              id="user"
              type="text"
              name="user"
              required
              pattern="\w{3,20}"
              autocomplete="username"
              />
            <label for="user">👤</label>
            <div class="feedback user">Nombre de usuario incorrecto</div>
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input
              id="pass"
              type="password"
              name="pass"
              required
              pattern="\w{3,20}"
              autocomplete="current-password"
              />
            <label for="pass">🔑</label>
            <div class="feedback pass">Contraseña de usuario incorrecta</div>
          </div>
          <div class="buttonset">
            <button type="submit">Aceptar ✅</button> 
            <!--<button type="button">Aceptar ✅</button>-->
            <button type="reset">Reiniciar ❌</button>
            <a class="signup" href="controller?cmd=signup">Registro 👀</a>
          </div>
        </form>
      </main>
      
      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/login.js"></script>
  </body>
</html>
