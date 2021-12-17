<%@page import="org.japo.java.entities.UsuarioPermiso"%>
<%@page import="org.japo.java.entities.Proceso"%>
<%@page import="org.japo.java.entities.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      UsuarioPermiso up = (UsuarioPermiso) request.getAttribute("usuario-permiso");
      Proceso p = (Proceso) request.getAttribute("proceso");
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
    <link rel="stylesheet" href="public/css/usuariopermisos/usuario-permiso-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Permiso de Usuario - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=usuario-permiso-listado">Listado</a>
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
                <td><%= up.getId()%></td>
              </tr>
              <tr>
                <td>Proceso</td>
                <td><%= p.getNombre()%></td>
              </tr>
              <tr>
                <td>Usuario</td>
                <td><%= u.getUser()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= up.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= u.getIcono()%>" alt="<%= u.getInfo()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=usuario-permiso-borrado&id=<%= up.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=usuario-permiso-consulta&id=<%= up.getId()%>&op=captura">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/usuariopermisos/usuario-permiso-borrado.js"></script>
  </body>

</html>
