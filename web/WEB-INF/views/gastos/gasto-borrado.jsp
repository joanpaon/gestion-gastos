<%@page import="java.util.Locale"%>
<%@page import="org.japo.java.entities.Partida"%>
<%@page import="org.japo.java.entities.Abono"%>
<%@page import="org.japo.java.entities.Gasto"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Gasto g = (Gasto) request.getAttribute("gasto");
      Abono a = (Abono) request.getAttribute("abono");
      Partida pda = (Partida) request.getAttribute("partida");
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
    <link rel="stylesheet" href="public/css/gastos/gasto-borrado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Borrado de Gastos - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=gasto-listado">Listado</a>
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
                <td><%= g.getId()%></td>
              </tr>
              <tr>
                <td>Abono</td>
                <td><%= a.getInfo()%></td>
              </tr>
              <tr>
                <td>Partida</td>
                <td><%= pda.getNombre()%></td>
              </tr>
              <tr>
                <td>Importe</td>
                <td><%= String.format(Locale.ENGLISH, "%.2f", g.getImporte())%> €</td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= g.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= g.getRecibo()%>" alt="Recibo del Gasto"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=gasto-borrado&id=<%= g.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=gasto-consulta&id=<%= g.getId()%>">Cancelar</a>
        </nav>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <!-- Application Scripts -->
    <script src="public/js/gastos/gasto-borrado.js"></script>
  </body>
</html>
