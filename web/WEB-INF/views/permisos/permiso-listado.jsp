<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="org.japo.java.entities.Usuario"%>
<%@page import="org.japo.java.entities.Permiso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Datos Inyectados
    List<Permiso> permisos = (ArrayList<Permiso>) request.getAttribute("permisos");
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
    <link rel="stylesheet" href="public/css/permisos/permiso-listado.css" /> 
  </head>

  <body>
    <div id="container">
      <jsp:include page="../../partials/header.jsp" />

      <main>
        <header>
          <h2>Listado de Permisos</h2>
          <% Usuario usuario = (Usuario) session.getAttribute("usuario"); %>
          <% if (usuario.getPerfilID() == Perfil.DEVEL) { %>
          <a class="btn btn-principal" href="controller?cmd=main-devel" title="Principal">P</a>
          <% } else if (usuario.getPerfilID() == Perfil.ADMIN) { %>
          <a class="btn btn-principal" href="controller?cmd=main-admin" title="Principal">P</a>
          <% } else { %>
          <a class="btn btn-principal" href="controller?cmd=main-basic" title="Principal">P</a>
          <% }%>
          <a class="btn btn-insertar" href="controller?cmd=permiso-insercion&op=captura" title="Nuevo">N</a>
        </header>

        <% if (permisos.size() <= 1) { %>
        <nav class="paginacion" style="display: none;">
          <% } else { %>
          <nav class="paginacion">
            <% }%>
            <a href="controller?cmd=permiso-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
            <a href="controller?cmd=permiso-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
            <div class="slider">
              <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
              <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
            </div>
            <a href="controller?cmd=permiso-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
            <a href="controller?cmd=permiso-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

          <% if (permisos.isEmpty()) { %>

          <h2>No hay Permisos de Perfil disponibles</h2>

          <% } else {%>


          <table>
            <thead>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=id&sort-dir=desc">ID</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("id") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-dir=">ID</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=id&sort-dir=asc">ID</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=proceso&sort-dir=asc">Proceso</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("proceso") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=proceso&sort-dir=desc">Proceso</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("proceso") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-dir=">Proceso</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=proceso&sort-dir=asc">Proceso</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>
              <% if (sortFld.isEmpty() || sortDir.isEmpty()) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=perfil&sort-dir=asc">Perfil</a>
                <span></span>
              </div>
              <% } else if (sortFld.equals("perfil") && sortDir.equalsIgnoreCase("asc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=perfil&sort-dir=desc">Perfil</a>
                <span>&#9650;</span>
              </div>
              <% } else if (sortFld.equals("perfil") && sortDir.equalsIgnoreCase("desc")) { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-dir=">Perfil</a>
                <span>&#9660;</span>
              </div>
              <% } else { %>
              <div>
                <a href="controller?cmd=permiso-listado&sort-fld=perfil&sort-dir=asc">Perfil</a>
                <span></span>
              </div>
              <% } %>
            </th>
            <th>Acciones</th>
            </thead>
            <tbody>
              <% for (Permiso permiso : permisos) {%>

              <tr>
                <td><%= permiso.getId()%></td>
                <td><%= permiso.getProcesoInfo()%></td>
                <td><%= permiso.getPerfilInfo()%></td>
                <td>
                  <a class="btn btn-consultar" href="controller?cmd=permiso-consulta&id=<%= permiso.getId()%>" title="Consulta">C</a>
                  <a class="btn btn-modificar" href="controller?cmd=permiso-modificacion&id=<%= permiso.getId()%>" title="Modificación">M</a>
                  <a class="btn btn-borrar" href="controller?cmd=permiso-borrado&id=<%= permiso.getId()%>" title="Eliminación">B</a>
                </td>
              </tr>

              <% }%>

            </tbody>
          </table>

          <% }%>

          <% if (permisos.size() <= 1) { %>
          <nav class="paginacion" style="display: none;">
            <% } else { %>
            <nav class="paginacion">
              <% }%>
              <a href="controller?cmd=permiso-listado&op=ini" class="btn btn-ini" title="Principio">&lt;&lt;</a>
              <a href="controller?cmd=permiso-listado&op=prv" class="btn btn-prv" title="Anterior">&lt;</a>
              <div class="slider">
                <input type="range" min="0" max="<%= rowsPage > 0 ? rowCount / rowsPage : 0%>" value="<%= rowsPage > 0 ? rowIndex / rowsPage : 0%>">
                <a href="#" class="btn btn-num" title="Nº de Página"><%= rowsPage > 0 ? rowIndex / rowsPage : 0%></a>
              </div>
              <a href="controller?cmd=permiso-listado&op=nxt" class="btn btn-nxt" title="Siguiente">&gt;</a>
              <a href="controller?cmd=permiso-listado&op=end" class="btn btn-end" title="Final">&gt;&gt;</a>
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

            <jsp:include page="../../partials/footer.jsp" />
            </div>

            <script src="public/js/permisos/permiso-listado.js"></script>
            </body>
            </html>
