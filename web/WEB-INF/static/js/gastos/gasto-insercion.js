document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let lstAbon = document.getElementById("abono");
    let lstPart = document.getElementById("partida");
    let txfInfo = document.getElementById("info");

    // User - Foco Perdido
    lstAbon.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstAbon.options[lstAbon.selectedIndex].text
                + " - "
                + lstPart.options[lstPart.selectedIndex].text;
    });

    // User - Foco Ganado
    lstPart.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstAbon.options[lstAbon.selectedIndex].text
                + " - "
                + lstPart.options[lstPart.selectedIndex].text;
    });
});
