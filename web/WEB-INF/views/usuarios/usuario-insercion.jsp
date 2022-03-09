<%@page import="org.japo.java.entities.Perfil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Datos Inyectados
    // Con Perfil SI Usuario es el mismo de la sesión
    // Con Perfil NO Usuario es cualquier usuario
    List<Perfil> perfiles = (ArrayList<Perfil>) request.getAttribute("perfiles");
%>

<!DOCTYPE html>
<html lang="es">

  <head>
    <% request.setAttribute("tabla", "usuarios");%>
    <%@include file="../../partials/crud/insercion-head.jspf"%>
  </head>

  <body>
    <div id="container">
      <%@include file="../../partials/general/header.jspf"%>

      <main>
        <header>
          <h2>Incorporación de Usuarios</h2>
          <a class="btn btn-listar" accept-charset="Windows-1252"
             href="controller?cmd=usuario-listado">Listado</a>
        </header> 
        <form method="post" accept-charset="Windows-1252"
              action="controller?cmd=usuario-insercion&op=proceso">
          <div class="fieldset">
            <label for="user">Usuario</label>
            <input id="user" type="text" name="user" 
                   pattern="\w{3,20}" required />
          </div>
          <div class="fieldset">
            <label for="pass">Contraseña</label>
            <input id="pass" type="text" name="pass" 
                   pattern="\w{3,20}" required />
          </div>
          <div class="fieldset">
            <label for="email">Correo Electrónico</label>
            <input id="email" type="email" name="email" required />
          </div>
          <div class="fieldset">
            <label for="icono">Avatar</label>
            <textarea id="icono" name="icono" rows="6"></textarea>
          </div>
          <div class="fieldset">
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil">
              <option disabled selected value></option>
              <% for (Perfil perfil : perfiles) {%>
              <option value="<%= perfil.getId()%>"><%= perfil.getNombre()%></option>
              <% }%>
            </select>
          </div>
          <div class="fieldset">
            <label for="info">Información</label>
            <input id="info" type="text" name="info" />
          </div>
          <div class="botones">
            <button class="btn btn-submit" type="submit">Enviar</button>
            <button class="btn btn-reset" type="reset">Reiniciar</button>
          </div>
        </form>
      </main>

      <%@include file="../../partials/general/footer.jspf"%>
    </div>

    <script src="public/js/usuarios/usuario-insercion.js"></script>
    <script src="public/js/partials/partial-header.js"></script>
    <script src="public/js/partials/partial-footer.js"></script>
  </body>
</html>
