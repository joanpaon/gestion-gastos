document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let txfNombre = document.getElementById("nombre");
    let lstCuotas = document.getElementById("cuota");
    let txfInfo = document.getElementById("info");

    // Proyecto - Selección
    txfNombre.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = txfNombre.value +
                " - " + lstCuotas.options[lstCuotas.selectedIndex].text;
    });

    // Proyecto - Selección
    lstCuotas.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = txfNombre.value +
                " - " + lstCuotas.options[lstCuotas.selectedIndex].text;
    });
});
