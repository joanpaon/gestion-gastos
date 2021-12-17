document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let txfNombre = document.getElementById("nombre");
    let txfInfo = document.getElementById("info");

    // Proyecto - Selección
    txfNombre.addEventListener("input", () => {
        // Actualizar Campo Info
        txfInfo.value = txfNombre.value + " - info";
    });
});
