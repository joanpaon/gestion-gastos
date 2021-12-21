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
  // Datos Inyectados
  List<Gasto> gastos = (ArrayList<Gasto>) request.getAttribute("gastos");
  String filterExp = request.getAttribute("filter-exp") == null ? "" : request.getAttribute("filter-exp").toString();
  String sortFld = request.getAttribute("sort-fld") == null ? "" : request.getAttribute("sort-fld").toString();
  String sortDir = request.getAttribute("sort-dir") == null ? "" : request.getAttribute("sort-dir").toString();
  Long rowCount = (Long) request.getAttribute("row-count");
  Long rowIndex = (Long) request.getAttribute("row-index");
  Long rowsPage = (Long) request.getAttribute("rows-page");
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
    <link rel="stylesheet" href="public/css/general.css" /> 
    <link rel="stylesheet" href="public/css/crud/listado.css" /> 
    <link rel="stylesheet" href="public/css/gastos/gasto-listado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Listado de Gastos</h2>
          <% Usuario usuario = (Usuario) session.getAttribute("usuario"); %>
          <% if (usuario.getPerfilID() == Perfil.DEVEL) { %>
          <a class="btn btn-principal" href="controller?cmd=main-devel" title="Principal">P</a>
          <% } else if (usuario.getPerfilID() == Perfil.ADMIN) { %>
          <a class="btn btn-principal" href="controller?cmd=main-admin" title="Principal">P</a>
          <% } else { %>
          <a class="btn btn-principal" href="controller?cmd=main-basic" title="Principal">P</a>
          <% }%>
          <a class="btn btn-insertar" href="controller?cmd=gasto-insercion&op=captura" title="Nuevo">N</a>
        </header>

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

      <jsp:include page="../../partials/footer.jsp" />
    </div>

    <script src="public/js/gastos/gasto-listado.js"></script>
  </body>
</html>
