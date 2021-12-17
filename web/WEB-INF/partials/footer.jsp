<%@page contentType="text/html" pageEncoding="UTF-8"%>

<footer>
  <a
    class="social"
    href="https://www.facebook.com/"
    title="Facebook"
    target="_blank"
    ><img src="public/img/facebook.png" alt="Facebook"
        /></a>
  <a
    class="social"
    href="https://twitter.com/?lang=es"
    title="Twitter"
    target="_blank"
    ><img src="public/img//twitter.png" alt="Twitter"
        /></a>
  <a
    class="social"
    href="https://www.youtube.com/"
    title="Youtube"
    target="_blank"
    ><img src="public/img//youtube.png" alt="Youtube"
        /></a>
  <a
    class="social"
    href="https://www.instagram.com/"
    title="Instagram"
    target="_blank"
    ><img src="public/img//instagram.png" alt="Instagram"
        /></a>
  <a
    class="social"
    href="https://web.whatsapp.com/"
    title="Whatsapp"
    target="_blank"
    ><img src="public/img//whatsapp.png" alt="Whatsapp"
        /></a>
  <a class="social" href="controller?cmd=mensajes&type=recibidos&op=listar&base=1" title="Centro de Mensajes">
    <span>0</span>
    <img src="public/img//mail.png" alt="Contact" />
  </a>
</footer>

<div class="cookies">
  <div class="msg">
    Utilizamos cookies propias y de terceros para obtener datos estadí­sticos 
    de la navegación de nuestros usuarios y mejorar nuestros servicios. 
  </div>
  <input type="button" value="Aceptar" />
</div>

<!-- -->

<style>
  footer {
    width: 100%;

    padding: 0 1rem;
    /*margin-top: auto;*/

    background-color: rgb(57, 70, 83);
    border-top: solid 0.2rem yellowgreen;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  /* --- */

  footer .social {
    margin: 0.5rem;
  }

  footer .social img {
    height: 3rem;
    width: 3rem;
  }

  footer .social:last-of-type {
    height: 3.2rem;
    width: 3.2rem;

    margin-left: auto;

    background-color: lightgray;
    background-color: orchid;
    background-color: plum;
    background-color: rgb(228, 210, 220);
    border-radius: 50%;

    display: flex;
    justify-content: center;
    align-items: center;

    position: relative;
  }

  footer .social:last-of-type span {
    width: 3rem;

    color: white;
    font-family: sans-serif;
    font-size: 1.2rem;
    font-weight: 900;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.8);
    text-align: center;

    background-color: red;
    border-radius: 50%;

    position: absolute;
    left: -2rem;
    top: 0;
    z-index: 1;
  }

  footer .social:last-of-type img {
    height: 2rem;
    width: 2rem;
  }

  /* --- */

  .cookies {
    width: 40rem;

    padding: 1rem 2rem;

    text-align: center;

    background-color: blue;
    background-color: #ccc;
    background-color: rgba(240, 240, 240, .9);
    border: solid blue .05rem;
    border-radius: .5rem;
    box-shadow: .4rem .4rem .4rem rgba(0, 0, 0, .5);

    opacity: 0;

    position: fixed;
    top: calc(50% - 10rem);
    left: 50%;
    z-index: 1;

    transform: translateX(-50%);

    transition: opacity 1s;

    display: flex;
    display: none;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }

  .cookies .msg {
    margin-bottom: 3rem;

    /*color: yellow;*/
    font-size: 1.2rem;
  }

  .cookies input {
    padding: .3rem 2rem;
    font-size: 1.2rem;

  }
</style>

<!-- -->

<script>
    // Referencias
    let divCookies = document.querySelector("#container .cookies");
    let msgCookies = document.querySelector("#container .cookies .msg");
    let btnCookies = document.querySelector("#container .cookies input");

    // Evento Click > BotÃ³n
    btnCookies.addEventListener("click", () => {
        // Memoriza AceptaciÃ³n en LocalStorage
        localStorage.setItem("cookiesAceptadas", true);

        // Oculta Banner
        divCookies.style.opacity = 0;

        // Quita Banner
        setTimeout(() => {
            divCookies.style.display = "none";
        }, 1000);
    });

    // Comprueba AceptaciÃ³n en LocalStorage
    let cookiesOK = localStorage.getItem("cookiesAceptadas");

    // ValidaciÃ³n AceptaciÃ³n > false: Visualiza Banner
    if (!(cookiesOK || false)) {
        // Materializa el Banner
        divCookies.style.display = "flex";

        // Visualiza Banner
        divCookies.style.opacity = 1;
    }
</script>
