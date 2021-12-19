<%@page import="org.japo.java.libraries.UtilesGastos"%>
<%@page import="org.japo.java.entities.EntityUsuario"%> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  EntityUsuario usuario = session == null ? null : (EntityUsuario) (session.getAttribute("usuario"));
%>

<header>
  <div class="menu"><a href="controller?cmd=landing">&#9776;</a></div>
  <a class="logo" href="controller?cmd=landing">
    <span>G</span>
    <span>de</span>
    <span>G</span>
  </a>
  <a class="slogan" href="controller?cmd=landing">
    <span>Gestión</span>
    <span>de</span>
    <span>Gastos</span>
  </a>
  <a class="lookup" href="#">
    <img src="public/img/lupa.png" alt="Carrito" />
  </a>
  <a class="cart" href="#">
    <span>0</span>
    <img src="public/img/cart.png" alt="Carrito" />
  </a>
  <% if (usuario instanceof EntityUsuario) {%>
  <a class="user" href="controller?cmd=profile&op=captura&id=<%= usuario.getId()%>" >
    <% if (UtilesGastos.validarImagenBase64(usuario.getIcono())) {%>
    <img src="<%= usuario.getIcono()%>" alt="User" />
    <% } else { %>
    <img src="public/img/user.png" alt="User" />
    <% }%>
    <span><%= usuario.getUser()%></span>
  </a>
  <a class="exit" href="controller?cmd=logout">
    <img src="public/img/exit.png" alt="Salida" />
  </a>
  <% } else { %>
  <a class="user" href="controller?cmd=login">
    <img src="public/img/user.png" alt="User" />
    <span>login</span>
  </a>
  <% }%>
</header>

<style>
  #container > header {
    width: 100%;

    padding: 0 1rem;

    font-size: 4rem;

    background-color: cornsilk;
    background-color: rgb(255, 233, 161);
    border-bottom: solid 0.2rem lightgray;

    display: flex;
    justify-content: space-between;
    align-items: center;
    /*flex: none;*/
  }

  /* --- */

  #container > header .menu {
    display: none;
  }

  #container > header .menu a {
    padding: 0.2rem 0.2rem;

    color: brown;
    text-decoration: none;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.8);

    display: flex;
    justify-content: center;
    align-items: center;
  }

  #container > header .menu a:hover {
    color: coral;
    color: burlywood;
  }

  /* --- */

  #container > header .logo {
    margin: 0 auto 0 0.5rem;

    font-weight: 900;
    text-decoration: none;
    text-shadow: 0.2rem 0.2rem 0.4rem rgba(0, 0, 0, 0.8);

    display: none;
    justify-content: center;
    align-items: baseline;
  }

  #container > header .logo > span:first-child {
    color: lightblue;
    font-size: 4rem;
  }

  #container > header .logo > span:nth-child(2) {
    color: lightyellow;
    font-family: cursive;
    font-size: 2.4rem;
  }

  #container > header .logo > span:last-child {
    color: lightpink;
    font-size: 4rem;
  }

  /* --- */

  #container > header .slogan {
    margin: 0 auto 0 0.5rem;

    font-weight: 900;
    text-decoration: none;
    text-shadow: 0.2rem 0.2rem 0.4rem rgba(0, 0, 0, 0.8);

    display: flex;
    justify-content: center;
    align-items: baseline;
  }

  #container > header .slogan > span:first-child {
    color: lightblue;
    font-size: 4rem;
  }

  #container > header .slogan > span:nth-child(2) {
    color: lightyellow;
    font-family: cursive;
    font-size: 2.4rem;
  }

  #container > header .slogan > span:last-child {
    color: lightpink;
    font-size: 4rem;
  }

  /* --- */

  #container > header .lookup {
    margin-left: 2.5rem;
    margin-right: 1.5rem;

    text-decoration: none;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  #container > header .lookup img {
    height: 4rem;
    width: 4rem;
  }

  /* --- */

  #container > header .cart {
    margin-right: 2rem;

    text-decoration: none;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  #container > header .cart span {
    width: 3rem;
    margin-bottom: -2rem;
    margin-left: 3rem;

    color: white;
    font-family: sans-serif;
    font-size: 1.2rem;
    font-weight: 900;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.8);
    text-align: center;

    background-color: red;
    border-radius: 50%;
    z-index: 1;
  }

  #container > header .cart img {
    height: 5rem;
    width: 5rem;

    font-size: 0;
  }

  /* --- */

  #container > header .user {
    margin-right: 1rem;

    text-decoration: none;

    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
  }

  #container > header .user img {
    height: 3.4rem;
    width: 4rem;

    margin-bottom: -0.5rem;
  }

  #container > header .user span {
    margin-top: .2rem;

    color: brown;
    font-size: 1.6rem;
    font-size: 1.2rem;
    text-shadow: 0.05rem 0.05rem 0.1rem rgba(0, 0, 0, 0.9);
  }

  /* --- */

  #container > header .exit {
    margin-left: 2rem;
    margin-right: 0.5rem;

    text-decoration: none;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  #container > header .exit img {
    height: 3rem;
    width: 3rem;
  }

  @media (max-width: 1170px) {
    #container > header .logo {
      display: flex;
    }
    #container > header .slogan {
      display: none;
    }
  }

  @media (max-width: 700px) {
    #container > header .menu {
      display: block;
    }
    #container > header .logo {
      margin-left: auto;
      margin-right: 1rem;
    }
    #container > header .lookup {
      display: none;
    }
    #container > header .cart {
      display: none;
    }
    #container > header .user {
      display: none;
    }
    #container > header .exit {
      display: none;
    }
  }
</style>