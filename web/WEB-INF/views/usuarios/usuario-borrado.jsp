<%@page import="org.japo.java.entities.EntityUsuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
    // Datos Inyectados
    EntityUsuario usuario = (EntityUsuario) request.getAttribute("usuario");
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
    <link rel="stylesheet" href="public/css/usuarios/usuario-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Usuarios - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=usuario-listado">Listado</a>
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
                <td><%= usuario.getId()%></td>
              </tr>
              <tr>
                <td>Nombre</td>
                <td><%= usuario.getUser()%></td>
              </tr>
              <tr>
                <td>EMail</td>
                <td><%= usuario.getEmail()%></td>
              </tr>
              <tr>
                <td>Perfil</td>
                <td><%= usuario.getPerfilInfo()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= usuario.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= usuario.getIcono()%>" alt="<%= usuario.getUser()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=usuario-borrado&id=<%= usuario.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=usuario-consulta&id=<%= usuario.getId()%>&op=captura">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/usuarios/usuario-borrado.js"></script>
  </body>
</html>
