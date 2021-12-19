<%@page import="org.japo.java.entities.Permiso"%>
<%@page import="org.japo.java.entities.Abono"%>
<%@page import="org.japo.java.entities.Proceso"%>
<%@page import="org.japo.java.entities.Perfil"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
    // Datos Inyectados
    Permiso permiso = (Permiso) request.getAttribute("permiso");
    Proceso proceso = (Proceso) request.getAttribute("proceso");
    Perfil perfil = (Perfil) request.getAttribute("perfil");
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
    <link rel="stylesheet" href="public/css/permisos/permiso-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Permiso de Perfil - Confirmación</h2>
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
                <td><%= proceso.getNombre()%></td>
              </tr>
              <tr>
                <td>Perfil</td>
                <td><%= perfil.getNombre()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= permiso.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= perfil.getIcono()%>" alt="<%= perfil.getInfo()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=permiso-borrado&id=<%= permiso.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=permiso-consulta&id=<%= permiso.getId()%>&op=captura">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/permisos/permiso-borrado.js"></script>
  </body>

</html>
