<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Gasto"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.japo.java.libraries.UtilesGastos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Session > Usuario
    Usuario usuario = (Usuario) (session.getAttribute("usuario"));

    // Datos Inyectados
    List<Gasto> gastos = (ArrayList<Gasto>) request.getAttribute("gastos");
    String filterExp = (String) request.getAttribute("filter-exp");
    String sortFld = (String) request.getAttribute("sort-fld");
    String sortDir = (String) request.getAttribute("sort-dir");
    Long rowCount = (Long) request.getAttribute("row-count");
    Long rowIndex = (Long) request.getAttribute("row-index");
    Long rowsPage = (Long) request.getAttribute("rows-page");
%>

<!DOCTYPE html>
<html lang="es">

  <head>
    <!-- Head de la Página - Listado -->
    <% request.setAttribute("tabla", "gastos");%>
    <%@include file="../../partials/crud/listado-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>

        <!-- Cabecera de la Página -->
        <% request.setAttribute("titulo-listado", "Listado de Gastos");%>
        <%@include file="../../partials/crud/listado-header.jspf"%>

        <% if (gastos.size() <= 1) { %>
        <nav class="paginacion" style="display: none;">
          <% } else { %>
          <nav class="paginacion">
            <% }%>
            <a href="controller?cmd=gasto-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=gasto-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=gasto-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=gasto-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
            |
            <div class="rows-page">
              <label for="rows-page">Filas</label>
              <select id="rows-page">
                <% if (rowsPage == 10) { %>
                <option value="10" selected>10</option>
                <option value="20">20</option>
                <option value="40">40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 20) { %>
                <option value="10">10</option>
                <option value="20" selected>20</option>
                <option value="40">40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 40) { %>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="40" selected>40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 80) { %>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="40">40</option>
                <option value="80" selected>80</option>
                <% }%>
              </select>
            </div>
            |
            <div class="filter">
              <input type="text" value="<%=filterExp%>" autofocus />
              <a href="#" class="btn btn-filter">⚡</a>
            </div>
          </nav>

          <% if (gastos.isEmpty()) { %>

          <h2>No hay gastos disponibles</h2>

          <% } else { %>

          <table>
            <thead>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equals("asc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=id&sort-dir=desc">ID</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equals("desc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-dir=">ID</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=info&sort-dir=asc">Información</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("info") && sortDir.equals("asc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=info&sort-dir=desc">Información</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("info") && sortDir.equals("desc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-dir=none">Información</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=info&sort-dir=asc">Proyecto</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=created_at&sort-dir=asc">Fecha</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("created_at") && sortDir.equals("asc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=created_at&sort-dir=desc">Fecha</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("created_at") && sortDir.equals("desc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-dir=none">Fecha</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=created_at&sort-dir=asc">Fecha</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=importe&sort-dir=asc">Importe</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("importe") && sortDir.equals("asc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=importe&sort-dir=desc">Importe</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("importe") && sortDir.equals("desc")) { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-dir=none">Importe</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=gasto-listado&sort-fld=importe&sort-dir=asc">Importe</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>Acciones</th>
            </thead>
            <tbody>
              <% for (Gasto gasto : gastos) {%>

              <tr>
                <td><%= gasto.getId()%></td>
                <td><%= gasto.getInfo()%></td>
                <td><%= new SimpleDateFormat("dd/MM/yyyy").format(gasto.getCreatedAt())%></td>
                <td><%= String.format(Locale.ENGLISH, "%.2f", gasto.getImporte())%></td>
                <td>
                  <a class="btn btn-consultar" href="controller?cmd=gasto-consulta&id=<%= gasto.getId()%>" title="Consulta">C</a>
                  <a class="btn btn-modificar" href="controller?cmd=gasto-modificacion&id=<%= gasto.getId()%>" title="Modificación">M</a>
                  <a class="btn btn-borrar" href="controller?cmd=gasto-borrado&id=<%= gasto.getId()%>" title="Eliminación">B</a>
                </td>
              </tr>

              <% } %>

            </tbody>
          </table>

          <% }%>

          <nav class="paginacion">
            <a href="controller?cmd=gasto-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=gasto-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=gasto-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=gasto-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
            |
            <div class="rows-page">
              <label for="rows-page">Filas</label>
              <select id="rows-page">
                <% if (rowsPage == 10) { %>
                <option value="10" selected>10</option>
                <option value="20">20</option>
                <option value="40">40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 20) { %>
                <option value="10">10</option>
                <option value="20" selected>20</option>
                <option value="40">40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 40) { %>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="40" selected>40</option>
                <option value="80">80</option>
                <% } else if (rowsPage == 80) { %>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="40">40</option>
                <option value="80" selected>80</option>
                <% }%>
              </select>
            </div>
            |
            <div class="filter">
              <input type="text" value="<%=filterExp%>"/>
              <a href="#" class="btn btn-filter">⚡</a>
            </div>
          </nav>

      </main>

      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <script src="public/js/gastos/gasto-listado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
