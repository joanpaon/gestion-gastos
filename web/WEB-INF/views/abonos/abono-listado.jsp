<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Abono"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Session > Usuario
    Usuario usuario = (Usuario) (session.getAttribute("usuario"));

    // Datos Inyectados
    List<Abono> abonos = (ArrayList<Abono>) request.getAttribute("abonos");
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
    <% request.setAttribute("tabla", "abonos");%>
    <%@include file="../../partials/crud/listado-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>

        <!-- Cabecera de la Página -->
        <% request.setAttribute("titulo-listado", "Listado de Abonos");%>
        <%@include file="../../partials/crud/listado-header.jspf"%>

        <% if (abonos.size() <= 1) { %>
        <nav class="paginacion" style="display: none;">
          <% } else { %>
          <nav class="paginacion">
            <% }%>
            <a href="controller?cmd=abono-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=abono-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=abono-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=abono-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

          <% if (abonos.isEmpty()) { %>

          <h2>No hay Abonos disponibles</h2>

          <% } else {%>

          <table>
            <thead>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=id&sort-dir=desc">ID</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-dir=">ID</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=proyecto&sort-dir=asc">Proyecto</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("proyecto") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=proyecto&sort-dir=desc">Proyecto</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("proyecto") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-dir=">Proyecto</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=proyecto&sort-dir=asc">Proyecto</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=usuario&sort-dir=asc">Usuario</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("usuario") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=usuario&sort-dir=desc">Usuario</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("usuario") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-dir=">Usuario</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=abono-listado&sort-fld=usuario&sort-dir=asc">Usuario</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>Acciones</th>
            </thead>
            <tbody>
              <% for (Abono abono : abonos) {%>

              <tr>
                <td><%= abono.getId()%></td>
                <td><%= abono.getProyectoInfo()%></td>
                <td><%= abono.getUsuarioInfo()%></td>
                <td>
                  <a class="btn btn-consultar" href="controller?cmd=abono-consulta&id=<%= abono.getId()%>" title="Consulta">C</a>
                  <a class="btn btn-modificar" href="controller?cmd=abono-modificacion&id=<%= abono.getId()%>" title="Modificación">M</a>
                  <a class="btn btn-borrar" href="controller?cmd=abono-borrado&id=<%= abono.getId()%>" title="Eliminación">B</a>
                </td>
              </tr>

              <% }%>

            </tbody>
          </table>

          <% }%>

          <nav class="paginacion">
            <a href="controller?cmd=abono-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=abono-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=abono-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=abono-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

    <!-- Scripts Página -->
    <script src="public/js/abonos/abono-listado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
