<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Session > Usuario
    Usuario usuario = (Usuario) (session.getAttribute("usuario"));

    // Datos Inyectados
    List<Perfil> perfiles = (ArrayList<Perfil>) request.getAttribute("perfiles");
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
    <% request.setAttribute("tabla", "perfiles");%>
    <%@include file="../../partials/crud/listado-head.jspf"%> 
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>

        <!-- Cabecera de la Página -->
        <% request.setAttribute("titulo-listado", "Listado de Perfiles");%>
        <%@include file="../../partials/crud/listado-header.jspf"%>

        <% if (perfiles.size() <= 1) { %>
        <nav class="paginacion" style="display: none;">
          <% } else { %>
          <nav class="paginacion">
            <% }%>
            <a href="controller?cmd=perfil-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=perfil-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=perfil-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=perfil-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

          <% if (perfiles.isEmpty()) { %>

          <h2>No hay perfiles disponibles</h2>

          <% } else { %>

          <table>
            <thead>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=id&sort-dir=desc">ID</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-dir=">ID</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=nombre&sort-dir=asc">Nombre</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("nombre") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=nombre&sort-dir=desc">Nombre</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("nombre") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-dir=">Nombre</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=nombre&sort-dir=asc">Nombre</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=info&sort-dir=asc">Info</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("info") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=info&sort-dir=desc">Info</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("info") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-dir=">Info</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=perfil-listado&sort-fld=info&sort-dir=asc">Info</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>Acciones</th>
            </thead>

            <tbody>
              <% for (Perfil perfil : perfiles) {%>

              <tr>
                <td><%= perfil.getId()%></td>
                <td><%= perfil.getNombre()%></td>
                <td><%= perfil.getInfo()%></td>
                <td>
                  <a class="btn btn-consultar" href="controller?cmd=perfil-consulta&id=<%= perfil.getId()%>" title="Consulta">C</a>
                  <a class="btn btn-modificar" href="controller?cmd=perfil-modificacion&id=<%= perfil.getId()%>" title="Modificación">M</a>
                  <a class="btn btn-borrar" href="controller?cmd=perfil-borrado&id=<%= perfil.getId()%>" title="Eliminación">B</a>
                </td>
              </tr>

              <% } %>

            </tbody>
          </table>

          <% }%>

          <% if (usuario.getPerfilID() == Perfil.BASIC) { %>
          <nav class="paginacion" style="display: none;">
            <% } else { %>
            <nav class="paginacion">
              <% }%>
              <a href="controller?cmd=perfil-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
              <a href="controller?cmd=perfil-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
              <div class="slider">
                <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
                <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
              </div>
              <a href="controller?cmd=perfil-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
              <a href="controller?cmd=perfil-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

            </main>

            <jsp:include page="../../partials/general/footer.jspf" />
            </div>

            <script src="public/js/perfiles/perfil-listado.js"></script>
            <script src="public/js/partials/partial-header.js"></script>
            <script src="public/js/partials/partial-footer.js"></script>
            </body>
            </html>
