<%@page import="org.japo.java.entities.PermisosUsuario"%>
<%@page import="org.japo.java.entities.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Entidades
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
    <link rel="stylesheet" href="public/css/permisos-grupo.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../partials/header.jsp" />

      <main>
        <header>
          <h2>Permisos de Grupo</h2>
          <a class="btn btn-principal" href="controller?cmd=main">Principal</a>
        </header>
        <div class="permisos-container">
          <jsp:include page="../partials/permisos-grupo-usuarios.jsp" />
        </div> 
      </main>
        
      <jsp:include page="../partials/footer.jsp" />
    </div>
  </body>
</html>
