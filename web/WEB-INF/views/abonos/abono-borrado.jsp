<%@page import="org.japo.java.entities.Abono"%>
<%@page import="org.japo.java.entities.Proyecto"%>
<%@page import="org.japo.java.entities.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Abono abono = (Abono) request.getAttribute("abono");
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
    <link rel="stylesheet" href="public/css/abonos/abono-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Abonos - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=abono-listado">Listado</a>
        </header>
        <div class="content">
          <table>
            <thead>
            <th>Dato</th>
            <th>Valor</th>
            </thead>
            <tbody>
              <tr>
                <td>ID</td>
                <td><%= abono.getId()%></td>
              </tr>
              <tr>
                <td>Usuario</td>
                <td><%= abono.getUsuarioInfo()%></td>
              </tr>
              <tr>
                <td>Proyecto</td>
                <td><%= abono.getProyectoInfo()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= abono.getInfo()%></td>
              </tr>
            </tbody>
          </table>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=abono-borrado&id=<%= abono.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=abono-consulta&id=<%= abono.getId()%>">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/abonos/abono-borrado.js"></script>
  </body>
</html>
