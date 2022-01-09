document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let divCookies = document.querySelector("#container > .cookies");
    let msgCookies = document.querySelector("#container > .cookies .msg");
    let btnCookies = document.querySelector("#container > .cookies input");

    // Evento Click > Botón
    btnCookies.addEventListener("click", () => {
        // Memoriza Aceptación en LocalStorage
        localStorage.setItem("cookiesAceptadas", true);

        // Oculta Banner
        divCookies.style.opacity = 0;

        // Quita Banner
        setTimeout(() => {
            divCookies.style.display = "none";
        }, 1000);
    });

    // Comprueba Aceptación en LocalStorage
    let cookiesOK = localStorage.getItem("cookiesAceptadas");

    // Validación Aceptación > false: Visualiza Banner
    if (!(cookiesOK || false)) {
        // Materializa el Banner
        divCookies.style.display = "flex";

        // Visualiza Banner
        divCookies.style.opacity = 1;
    }
});


