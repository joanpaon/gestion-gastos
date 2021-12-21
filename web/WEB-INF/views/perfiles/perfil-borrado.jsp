<%@page import="org.japo.java.entities.Perfil"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Perfil perfil = (Perfil) request.getAttribute("perfil");
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
    <link rel="stylesheet" href="public/css/perfiles/perfil-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Perfiles - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=perfil-listado">Listado</a>
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
                <td><%= perfil.getId()%></td>
              </tr>
              <tr>
                <td>Nombre</td>
                <td><%= perfil.getNombre()%></td>
              </tr>
              <tr>
                <td>Info</td>
                <td><%= perfil.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= perfil.getIcono()%>" alt="<%= perfil.getNombre()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=perfil-borrado&id=<%= perfil.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=perfil-consulta&id=<%= perfil.getId()%>">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/perfiles/perfil-borrado.js"></script>
  </body>
</html>
