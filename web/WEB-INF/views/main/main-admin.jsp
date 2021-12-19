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
    <link rel="stylesheet" href="public/css/main/main-admin.css" />
  </head>

  <body>
    <!-- Web Content-->
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />
      
      <main>
        <h1>Operaciones Perfil Administrador</h1>
        <nav>
          <a href="controller?cmd=abono-listado">Abonos</a>
          <a href="controller?cmd=cuota-listado">Cuotas</a>
          <a href="controller?cmd=gasto-listado">Gastos</a>
          <a href="controller?cmd=perfil-listado">Perfiles</a>
          <a href="controller?cmd=partida-listado">Partidas</a>
          <a href="controller?cmd=permiso-listado">Permisos</a>
          <a href="controller?cmd=proyecto-listado">Proyectos</a>
          <a href="controller?cmd=usuario-listado">Usuarios</a>
        </nav>
      </main>
      
      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/main/main-admin.js"></script>
  </body>
</html>
