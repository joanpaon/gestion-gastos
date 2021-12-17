<%@page session="false" %>
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
    <link rel="stylesheet" href="public/css/errors/error.css"/>
  </head>
  
  <body>
    <div id="container">
      <h1>Acceso Denegado</h1>
      <span>&#128078;</span>
      <h2>Privilegios insuficientes para esa operación</h2>
      <a class="btn btn-listar" href="controller?cmd=main">Inicio</a>
    </div>

    <!-- Application Scripts -->
    <script src="public/js/errors/acceso-denegado.js"></script>
  </body>
</html>
