<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.japo.java.entities.Partida"%>
<%@page import="org.japo.java.entities.Abono"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  // Obtener Datos
  List<Abono> abonos = (ArrayList<Abono>) request.getAttribute("abonos");
  List<Partida> partidas = (ArrayList<Partida>) request.getAttribute("partidas");
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
    <link rel="stylesheet" href="public/css/gastos/gasto-insercion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Incorporación de Gastos</h2>
          <a class="btn btn-listar" href="controller?cmd=gasto-listado">Listado</a>
        </header> 
        <form method="post" accept-charset="Windows-1252" 
              action="controller?cmd=gasto-insercion&op=proceso">
          <div class="fieldset">
            <label for="abono">Abono</label>
            <select id="abono" name="abono" min="1">
              <option disabled selected value></option>
              <% for (Abono abono : abonos) {%>
              <option value="<%= abono.getId()%>"><%= abono.getInfo()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="partida">Partida</label>
            <select id="partida" name="partida">
              <option disabled selected value></option>
              <% for (Partida partida : partidas) {%>
              <option value="<%= partida.getId()%>"><%= partida.getNombre()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="importe">Importe</label>
            <input id="importe" type="number" step="0.01" min="0.0" name="importe" required />
          </div>
          <div class="fieldset">
            <label for="fecha">Fecha</label>
            <input id="fecha" type="date" name="fecha" required
                   value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>" />
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" required />
          </div>
          <div class="fieldset">
            <label for="recibo">Recibo</label>
            <textarea id="recibo" name="recibo" rows="6"></textarea>
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
    <script src="public/js/gastos/gasto-insercion.js"></script>
  </body>
</html>
