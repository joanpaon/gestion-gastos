/* global fetch */

document.addEventListener("DOMContentLoaded", () => {

    // Referencias
    let frmMain = document.querySelector("main > form");
    let txfUser = document.getElementById("user");
    let txfPass = document.getElementById("pass");
    let txfConf = document.getElementById("conf");
    let txaIcon = document.getElementById("icono");
    let fbkUser = document.querySelector(".fieldset .user");
    let fbkPass = document.querySelector(".fieldset .pass");
    let fbkConf = document.querySelector(".fieldset .conf");
    let fbkAvat = document.querySelector(".fieldset .icono");
    let btnSmit = document.querySelector("[type='submit']");

    // Semáforos
    let userExists = true;
    let passConfirmed = false;

    // Formulario Desactivar Submit
    frmMain.addEventListener("submit", (event) => {
        // Desactiva Submit Predeterminado
        event.preventDefault();

        // Comprueba Validez Formulario
        if (txfUser.checkValidity() && txfPass.checkValidity() && !userExists && passConfirmed) {
            // Envía formulario
            frmMain.submit();
        } else {
            console.log("user: " + txfUser.checkValidity());
            console.log("pass: " + txfPass.checkValidity());
            console.log("Exists: " + userExists);
            console.log("Confirm: " + passConfirmed);
        }
    });

    // User - Foco Ganado
    txfUser.addEventListener("focusin", () => {
        fbkUser.style.display = "none";
    });

    // User - Foco Perdido
//    txfUser.addEventListener("focusout", () => {
    txfUser.addEventListener("input", () => {
        // Nombre de usuario
        let user = txfUser.value;
//        console.log("Usurario: " + txfUser.value);

        // Consulta Existencia Nombre de Usuario
        fetch("controller?svc=usuario-existencia&user=" + user)
                .then(res => res.json())
                .then(json => {
//                    console.log(json);
                    if (json.ok) {
                        fbkUser.style.display = "block";
                        userExists = true;
                    } else {
                        fbkUser.style.display = "none";
                        userExists = false;
                    }
                });
    });

    // Pass - Foco Perdido
    txfPass.addEventListener("focusout", () => {
        // Password Primario
        let pass = txfPass.value;

        // Password Confirmación
        let conf = txfConf.value;
        
        // Comparación
        if (pass === conf) {
            fbkPass.style.display = "none";
            fbkConf.style.display = "none";
            passConfirmed = true;
        } else {
            fbkPass.style.display = "block";
            fbkConf.style.display = "block";
            passConfirmed = false;
        }
    });

    // Conf - Foco Perdido
    txfConf.addEventListener("focusout", () => {
        // Password Primario
        let pass = txfPass.value;

        // Password Confirmación
        let conf = txfConf.value;
        
        // Comparación
        if (pass === conf) {
            fbkPass.style.display = "none";
            fbkConf.style.display = "none";
            passConfirmed = true;
        } else {
            fbkPass.style.display = "block";
            fbkConf.style.display = "block";
            passConfirmed = false;
        }
    });
});
