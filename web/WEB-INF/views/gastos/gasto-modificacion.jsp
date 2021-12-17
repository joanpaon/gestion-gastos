<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.japo.java.entities.Gasto"%>
<%@page import="org.japo.java.entities.Abono"%>
<%@page import="org.japo.java.entities.Partida"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

  <%
      // Datos Inyectados
      Gasto g = (Gasto) request.getAttribute("gasto");
      List<Abono> listaAbo = (ArrayList<Abono>) request.getAttribute("lista-abonos");
      List<Partida> listaPda = (ArrayList<Partida>) request.getAttribute("lista-partidas");
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
    <link rel="stylesheet" href="public/css/gastos/gasto-modificacion.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Modificación de Gastos</h2>
          <a class="btn btn-listar" href="controller?cmd=gasto-listado">Listado</a>
        </header> 

        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=gasto-modificacion&id=<%= g.getId()%>&op=proceso">
          <div class="fieldset">
            <label for="abono">Abono</label>
            <select id="abono" name="abono">
              <% for (Abono a : listaAbo) {%>
              <% if (a.getId() == g.getAbono()) {%>
              <option value="<%= a.getId()%>" selected><%= a.getInfo()%></option>
              <% } else {%>
              <option value="<%= a.getId()%>"><%= a.getInfo()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="partida">Partida</label>
            <select id="partida" name="partida">
              <% for (Partida pda : listaPda) {%>
              <% if (pda.getId() == g.getPartida()) {%>
              <option value="<%= pda.getId()%>" selected><%= pda.getNombre()%></option>
              <% } else {%>
              <option value="<%= pda.getId()%>"><%= pda.getNombre()%></option>
              <% }%>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="importe">Importe</label>
            <input id="importe" type="number" step="0.01" min="0.0" name="importe" 
                   value="<%= String.format(Locale.ENGLISH, "%.2f", g.getImporte())%>" />
          </div>
          <div class="fieldset">
            <label for="fecha">Fecha</label>
            <input id="fecha" type="date" name="fecha" required 
                   value="<%= new SimpleDateFormat("yyyy-MM-dd").format(g.getCreatedAt())%>" />
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" value="<%= g.getInfo()%>" />
          </div>
          <div class="fieldset">
            <label for="recibo">Recibo</label>
            <textarea id="recibo" name="recibo" rows="6"><%= g.getRecibo()%></textarea>
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <jsp:include page="../../partials/footer.jsp" />
    </div>
          
    <!-- Scripts -->
    <script src="public/js/gastos/gasto-modificacion.js"></script>
  </body>
</html>
