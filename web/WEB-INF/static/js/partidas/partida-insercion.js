document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let lstAbono = document.getElementById("abono");
    let lstPartida = document.getElementById("partida");
    let txfInfo = document.getElementById("info");

    // Proyecto - Selección
    lstAbono.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = lstAbono.options[lstAbono.selectedIndex].text;
    });

    // Proyecto - Selección
    lstPartida.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = lstAbono.options[lstAbono.selectedIndex].text +
                " - " + lstPartida.options[lstPartida.selectedIndex].text;
    });
});
