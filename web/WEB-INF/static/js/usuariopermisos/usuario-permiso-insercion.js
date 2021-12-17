document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let lstProc = document.getElementById("proceso");
    let lstUser = document.getElementById("usuario");
    let txfInfo = document.getElementById("info");

    // User - Foco Perdido
    lstProc.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProc.options[lstProc.selectedIndex].text
                + " - "
                + lstUser.options[lstUser.selectedIndex].text;
    });

    // User - Foco Ganado
    lstUser.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProc.options[lstProc.selectedIndex].text
                + " - "
                + lstUser.options[lstUser.selectedIndex].text;
    });

});


