<%@page import="org.japo.java.entities.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Datos Inyectados
    // Con Perfil SI Usuario es el mismo de la sesión
    // Con Perfil NO Usuario es cualquier usuario
    Usuario usuario = (Usuario) request.getAttribute("usuario");
%>

<!DOCTYPE html>
<html lang="es">

  <head>
    <% request.setAttribute("tabla", "usuarios");%>
    <%@include file="../../partials/crud/borrado-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>
        <header>
          <h2>Borrado de Usuarios - Confirmación</h2>
          <a class="btn btn-listar" href="controller?cmd=usuario-listado">Listado</a>
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
                <td><%= usuario.getId()%></td>
              </tr>
              <tr>
                <td>Nombre</td>
                <td><%= usuario.getUser()%></td>
              </tr>
              <tr>
                <td>EMail</td>
                <td><%= usuario.getEmail()%></td>
              </tr>
              <tr>
                <td>Perfil</td>
                <td><%= usuario.getPerfilInfo()%></td>
              </tr>
              <tr>
                <td>Información</td>
                <td><%= usuario.getInfo()%></td>
              </tr>
            </tbody>
          </table>
          <div class="imagen">
            <div class="imagen-margen">
              <img src="<%= usuario.getIcono()%>" alt="<%= usuario.getUser()%>"/> 
            </div>
          </div>
        </div>
        <nav class="botones">
          <a class="btn btn-borrar" href="controller?cmd=usuario-borrado&id=<%= usuario.getId()%>&op=proceso">Borrar</a>
          <a class="btn btn-cancelar" href="controller?cmd=usuario-consulta&id=<%= usuario.getId()%>&op=captura">Cancelar</a>
        </nav>
      </main>

      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <!-- Application Scripts -->
    <script src="public/js/usuarios/usuario-borrado.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
