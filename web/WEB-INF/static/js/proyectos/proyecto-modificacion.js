document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let txfProy = document.getElementById("nombre");
    let lstCota = document.getElementById("cuota");
    let txfInfo = document.getElementById("info");

    // User - Foco Perdido
    txfProy.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + txfProy.value
                + " - "
                + lstCota.options[lstCota.selectedIndex].text;
    });

    // User - Foco Ganado
    lstCota.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + txfProy.value
                + " - "
                + lstCota.options[lstCota.selectedIndex].text;
    });
});





