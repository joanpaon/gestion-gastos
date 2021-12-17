/* global fetch */

document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let frmMain = document.querySelector("main > form");
    let txfUser = document.getElementById("user");
    let txfPass = document.getElementById("pass");
    let fbkUser = document.querySelector(".feedback.user");

    // Semáforos
    let userMissing = true;

    // Formulario Desactivar Submit
    frmMain.addEventListener("submit", (event) => {
        // Desactiva Submit Predeterminado
        event.preventDefault();

        // Comprueba Validez Formulario
        if (txfUser.checkValidity() && txfPass.checkValidity() && !userMissing) {
            // Envía formulario
            frmMain.submit();
        }
    });

    // User - Foco Ganado
    txfUser.addEventListener("focusin", () => {
        // Reset User Feedback 
        fbkUser.innerText = "";
        fbkUser.style.display = "none";
    });

    // User - Foco Perdido
    txfUser.addEventListener("focusout", () => {
        // Input > Nombre de Usuario
        let user = txfUser.value;

        // Consulta Existencia Nombre de Usuario
        fetch("controller?svc=usuario-existencia&user=" + user)
                .then(res => res.json())
                .then(json => {
                    if (json.ok) {
                        console.log(json);
                        fbkUser.innerText = "";
                        fbkUser.style.display = "none";
                        userMissing = false;
                    } else {
                        console.log(json);
                        fbkUser.innerText = "Nombre de Usuario NO existe";
                        fbkUser.style.display = "block";
                        userMissing = true;
                    }
                });
    });
});


