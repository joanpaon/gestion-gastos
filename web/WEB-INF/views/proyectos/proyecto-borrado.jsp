<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Proyecto"%>
<%@page import="org.japo.java.entities.Cuota"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Proyecto proyecto = (Proyecto) request.getAttribute("proyecto");
      Usuario propietario = (Usuario) request.getAttribute("propietario");
      Cuota cuota = (Cuota) request.getAttribute("cuota");
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
    <link rel="stylesheet" href="public/css/proyectos/proyecto-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Proyectos - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=proyecto-listado">Listado</a>
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
                <td><%= proyecto.getId()%></td>
              </tr>
              <tr>
                <td>Nombre</td>
                <td><%= proyecto.getNombre()%></td>
              </tr>
              <tr>
                <td>Propietario</td>
                <td><%= propietario.getUser()%></td>
              </tr>
              <tr>
                <td>Cuota</td>
                <td><%= cuota.getNombre()%></td>
              </tr>
              <tr>
                <td>Info</td>
                <td><%= proyecto.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= proyecto.getIcono()%>" alt="<%= proyecto.getNombre()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=proyecto-borrado&id=<%= proyecto.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=proyecto-consulta&id=<%= proyecto.getId()%>">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/proyectos/proyecto-borrado.js"></script>
  </body>
</html>
