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
    <link rel="stylesheet" href="public/css/admin/signup.css" /> 
    <link rel="stylesheet" href="public/css/partials/partial-header.css" /> 
    <link rel="stylesheet" href="public/css/partials/partial-footer.css" /> 
  </head>

  <body>
    <!-- Web Content-->
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>
        <header>
          <h2>Registro de Usuarios</h2>
          <a class="btn btn-listar" accept-charset="Windows-1252"
             href="controller?cmd=landing">Inicio</a>
        </header> 
        <form method="post" accept-charset="Windows-1252" autocomplete="off"
              action="controller?cmd=signup&op=proceso">
          <input autocomplete="off" type="text" style="display:none;">
          <input autocomplete="off" type="password" style="display:none;">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user"
                   required
                   pattern="\w{3,20}" 
                   readonly
                   onfocus="this.removeAttribute('readonly');" />
            <div class="feedback user">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="password" name="pass"               
                   required
                   pattern="\w{3,20}"
                   readonly
                   onfocus="this.removeAttribute('readonly');" />
            <div class="feedback pass">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="conf">Contraseña</label>
            <input id="conf" type="password" 
                   required
                   pattern="\w{3,20}"
                   readonly
                   onfocus="this.removeAttribute('readonly');" />
            <div class="feedback conf">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required />
            <div class="feedback email">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="icono">Avatar</label>
            <textarea id="icono" name="icono" rows="6"></textarea>
            <div class="feedback icono">&#9888;</div>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" />
            <div class="feedback info">&#9888;</div>
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Aceptar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <!-- Application Scripts -->
    <script src="public/js/admin/signup.js"></script>  
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
