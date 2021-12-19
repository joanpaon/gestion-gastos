document.addEventListener("DOMContentLoaded", () => {
    // Referencias
    let sldPageUp = document.querySelector("main .paginacion:first-of-type input[type='range']");
    let btnPageUp = document.querySelector("main .paginacion:first-of-type .btn-num");
    let sldPageDn = document.querySelector("main .paginacion:last-of-type input[type='range']");
    let btnPageDn = document.querySelector("main .paginacion:last-of-type .btn-num");
    let lstRowsPageUp = document.querySelector("main .paginacion:first-of-type select");
    let lstRowsPageDn = document.querySelector("main .paginacion:last-of-type select");
    let txfFilterUp = document.querySelector("main .paginacion:first-of-type [type='text']");
    let txfFilterDn = document.querySelector("main .paginacion:last-of-type [type='text']");
    let btnFilterUp = document.querySelector("main .paginacion:first-of-type .btn-filter");
    let btnFilterDn = document.querySelector("main .paginacion:last-of-type .btn-filter");

    // Slider UP - Evento input
    sldPageUp.addEventListener("input", () => {
        // Slider > Valor
        let page = sldPageUp.value;

        // Valor > Slider
        sldPageDn.value = page;

        // Valor > Botón
        btnPageUp.innerText = page;
        btnPageDn.innerText = page;

        // Valor > Enlace
        btnPageUp.setAttribute("href", "controller?cmd=perfil-permiso-listado&op=num&page=" + page);
        btnPageDn.setAttribute("href", "controller?cmd=perfil-permiso-listado&op=num&page=" + page);
    });

    // Slider DOWN - Evento input
    sldPageDn.addEventListener("input", () => {
        // Slider > Valor
        let page = sldPageDn.value;

        // Valor > Slider
        sldPageUp.value = page;
        
        // Valor > Botón
        btnPageUp.innerText = page;
        btnPageDn.innerText = page;

        // Valor > Enlace
        btnPageUp.setAttribute("href", "controller?cmd=perfil-permiso-listado&op=num&page=" + page);
        btnPageDn.setAttribute("href", "controller?cmd=perfil-permiso-listado&op=num&page=" + page);
    });

    // Select UP - Evento Input
    lstRowsPageUp.addEventListener("input", () => {
        window.location.href = "controller?cmd=perfil-permiso-listado&rows-page=" + lstRowsPageUp.value;
    });

    // Select DOWN - Evento Input
    lstRowsPageDn.addEventListener("input", () => {
        window.location.href = "controller?cmd=perfil-permiso-listado&rows-page=" + lstRowsPageDn.value;
    });

    // Texto Filtro UP - Evento Input
    txfFilterUp.addEventListener("input", () => {
        txfFilterDn.value = txfFilterUp.value;
    });

    // Texto Filtro DOWN - Evento Input
    txfFilterDn.addEventListener("input", () => {
        txfFilterUp.value = txfFilterDn.value;
    });
    
    // Botón Filtro UP - Evento Click
    btnFilterUp.addEventListener("click", () => {
        window.location.href = "controller?cmd=perfil-permiso-listado&filter-exp=" + txfFilterUp.value;
    });    
    
    // Botón Filtro DOWN - Evento Click
    btnFilterDn.addEventListener("click", () => {
        window.location.href = "controller?cmd=perfil-permiso-listado&filter-exp=" + txfFilterDn.value;
    });    
});
