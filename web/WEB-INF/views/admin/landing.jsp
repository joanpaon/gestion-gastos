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
    <link rel="stylesheet" href="public/css/admin/landing.css" />
    <link rel="stylesheet" href="public/css/partials/partial-header.css" /> 
    <link rel="stylesheet" href="public/css/partials/partial-footer.css" /> 
  </head>

  <body>
    <!-- Web Content-->
    <div id="container">
      <%@include file="../../partials/partial-header.jspf"%>

      <main>
        <div class="holder">
          <p><a href="controller?cmd=login">Login</a></p>
          <img src="https://picsum.photos/600/400" 
               width="600" height="400" alt="Foto" />
        </div>
      </main>

      <%@include file="../../partials/partial-footer.jspf"%>
    </div>

    <!-- Application Scripts -->
    <script src="public/js/admin/landing.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
