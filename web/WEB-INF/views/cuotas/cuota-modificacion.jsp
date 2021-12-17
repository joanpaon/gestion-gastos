<%@page import="org.japo.java.entities.Cuota"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Cuota c = (Cuota) request.getAttribute("cuota");
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
    <link rel="stylesheet" href="public/css/cuotas/cuota-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Cuotas</h2>
          <a class="btn btn-listar" href="controller?cmd=cuota-listado">Listado</a>
        </header> 

        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=cuota-modificacion&id=<%= c.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="nombre">Nombre</label>
            <input id="nombre" type="text" name="nombre" value="<%= c.getNombre()%>"/>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" value="<%= c.getInfo()%>"/>
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/cuotas/cuota-modificacion.js"></script>
  </body>
</html>
