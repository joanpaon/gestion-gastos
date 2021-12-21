<%@page import="org.japo.java.entities.Permiso"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Datos Inyectados
  Permiso permiso = (Permiso) request.getAttribute("permiso");
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
    <link rel="stylesheet" href="public/css/permisos/permiso-consulta.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Consulta de Permisos de Perfil</h2>
          <a class="btn btn-listar" href="controller?cmd=permiso-listado">Listado</a>
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
                <td><%= permiso.getId()%></td>
              </tr>
              <tr>
                <td>Proceso</td>
                <td><%= permiso.getProcesoInfo()%></td>
              </tr>
              <tr>
                <td>Perfil</td>
                <td><%= permiso.getPerfilInfo()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= permiso.getInfo()%></td>
              </tr>
            </tbody>
          </table>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=permiso-borrado&id=<%= permiso.getId()%>">Borrar</a>
          <a class="btn btn-modificar" href="controller?cmd=permiso-modificacion&id=<%= permiso.getId()%>&op=captura">Modificar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/permisos/permiso-consulta.js"></script>
  </body>
</html>
