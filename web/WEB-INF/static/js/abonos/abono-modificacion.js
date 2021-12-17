document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let lstProy = document.getElementById("proyecto");
    let lstUser = document.getElementById("usuario");
    let txfInfo = document.getElementById("info");

    // User - Foco Perdido
    lstProy.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProy.options[lstProy.selectedIndex].text
                + " - "
                + lstUser.options[lstUser.selectedIndex].text;
    });

    // User - Foco Ganado
    lstUser.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProy.options[lstProy.selectedIndex].text
                + " - "
                + lstUser.options[lstUser.selectedIndex].text;
    });
});





