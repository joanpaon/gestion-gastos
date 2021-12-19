document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let lstProc = document.getElementById("proceso");
    let lstPerf = document.getElementById("perfil");
    let txfInfo = document.getElementById("info");

    // User - Foco Perdido
    lstProc.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProc.options[lstProc.selectedIndex].text
                + " - "
                + lstPerf.options[lstPerf.selectedIndex].text;
    });

    // User - Foco Ganado
    lstPerf.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = ""
                + lstProc.options[lstProc.selectedIndex].text
                + " - "
                + lstPerf.options[lstPerf.selectedIndex].text;
    });

});


