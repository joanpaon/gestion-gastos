<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="permisos-grupo-usuarios">
  <header>
    <label>Usuarios</label>
  </header>
  <div class="selectores">
    <div class="selector insercion">
      <input type="checkbox"/>
      <label>Inserción</label>
    </div>
    <div class="selector borrado">
      <input type="checkbox"/>
      <label>Borrado</label>
    </div>
    <div class="selector modificacion">
      <input type="checkbox"/>
      <label>Modificación</label>
    </div>
    <div class="selector consulta">
      <input type="checkbox"/>
      <label>Consulta</label>
    </div>
    <div class="selector listado">
      <input type="checkbox"/>
      <label>Listado</label>
    </div>
  </div>
</div>

<!-- -->

<style>
  .permisos-grupo-usuarios {
    width: 18rem;

    /* padding: 1rem; */
    /*margin: 1rem;*/

    background-color: cadetblue;
    background-color: tan;
    border: solid 0.1rem black;
    border-radius: 0.1rem;
    box-shadow: 0.2rem 0.2rem 0.4rem rgba(0, 0, 0, 0.5);

    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  /* --- */

  .permisos-grupo-usuarios header {
    background-color: darksalmon;
    border-bottom: solid .1rem #777;

    display: flex;
    justify-content: space-between;
    align-items: center
  }

  .permisos-grupo-usuarios header label {
    padding: 0.4rem 1rem;

    font-size: 1.2rem;
    font-weight: 700;
    text-align: center;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.3);
  }

  /* --- */

  .permisos-grupo-usuarios .selectores {
    padding: 0.5rem 0 0.5rem 5.5rem;

    display: flex;
    flex-direction: column;
    justify-content: center;
    /* align-items: center; */
    gap: 0.2rem;
  }

  /* --- */

  .permisos-grupo-usuarios .selectores .selector {
    display: flex;
    /* justify-content: center; */
    align-items: center;
    gap: 0.4rem;
  }

  /* --- */

  .permisos-grupo-usuarios .selectores input {
    height: 1rem;
    width: 1rem;

    /* box-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.5); */
  }

  /* --- */

  .permisos-grupo-usuarios .selectores label {
    font-size: 1rem;
    font-weight: 700;
    /* text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.3); */
  }
</style>

<!-- -->

<script>
    // Referencias
    let cbxUsuarioInsercion = document.querySelector(".permisos-grupo-usuarios .insercion");
    let cbxUsuarioBorrado = document.querySelector(".permisos-grupo-usuarios .borrado");
    let cbxUsuarioModificacion = document.querySelector(".permisos-grupo-usuarios .modificacion");
    let cbxUsuarioConsulta = document.querySelector(".permisos-grupo-usuarios .consulta");
    let cbxUsuarioListado = document.querySelector(".permisos-grupo-usuarios .listado");

    // Nombre del Servicio
    let svc = "cambiar-permisos-grupo";

    // Id del Grupo Actual - Obtenido de la lista del JSP Principal
    let grupo = document.querySelector(".selector-grupo").value + 0;

    // Evento Change > usuario-insercion
    cbxUsuarioInsercion.addEventListener("change", () => {
        // Estado CheckBox: true | false
        let state = cbxUsuarioInsercion.checked ? true : false;

        // Acción a la que se va a cambiar el permiso de acceso del usuario
        let action = "command-usuario-insercion";

        // Servicio > Cambiar Estado Permiso
        fetch("controller?svc=" + svc +
                "&group=" + grupo +
                "&action=" + action +
                "&state=" + state)
                .then(res => res.json())
                .then(json => {
                    if (!json.ok) {
                        cbxUsuarioInsercion.checked = false;
                    }
                });
    });

    // Evento Change > usuario-borrado
    cbxUsuarioBorrado.addEventListener("change", () => {
        // Estado CheckBox: true | false
        let state = cbxUsuarioBorrado.checked ? true : false;

        // Acción a la que se va a cambiar el permiso de acceso del usuario
        let action = "command-usuario-borrado";

        // Servicio > Cambiar Estado Permiso
        fetch("controller?svc=" + svc +
                "&group=" + grupo +
                "&action=" + action +
                "&state=" + state)
                .then(res => res.json())
                .then(json => {
                    if (!json.ok) {
                        cbxUsuarioBorrado.checked = false;
                    }
                });
    });

    // Evento Change > usuario-modificacion
    cbxUsuarioModificacion.addEventListener("change", () => {
        // Estado CheckBox: true | false
        let state = cbxUsuarioModificacion.checked ? true : false;

        // Acción a la que se va a cambiar el permiso de acceso del usuario
        let action = "command-usuario-modificacion";

        // Servicio > Cambiar Estado Permiso
        fetch("controller?svc=" + svc +
                "&group=" + grupo +
                "&action=" + action +
                "&state=" + state)
                .then(res => res.json())
                .then(json => {
                    if (!json.ok) {
                        cbxUsuarioModificacion.checked = false;
                    }
                });
    });

    // Evento Change > usuario-consulta
    cbxUsuarioConsulta.addEventListener("change", () => {
        // Estado CheckBox: true | false
        let state = cbxUsuarioConsulta.checked ? true : false;

        // Acción a la que se va a cambiar el permiso de acceso del usuario
        let action = "command-usuario-consulta";

        // Servicio > Cambiar Estado Permiso
        fetch("controller?svc=" + svc +
                "&group=" + grupo +
                "&action=" + action +
                "&state=" + state)
                .then(res => res.json())
                .then(json => {
                    if (!json.ok) {
                        cbxUsuarioConsulta.checked = false;
                    }
                });
    });

    // Evento Change > usuario-listado
    cbxUsuarioListado.addEventListener("change", () => {
        // Estado CheckBox: true | false
        let state = cbxUsuarioListado.checked ? true : false;

        // Acción a la que se va a cambiar el permiso de acceso del usuario
        let action = "command-usuario-listado";

        // Servicio > Cambiar Estado Permiso
        fetch("controller?svc=" + svc +
                "&group=" + grupo +
                "&action=" + action +
                "&state=" + state)
                .then(res => res.json())
                .then(json => {
                    if (!json.ok) {
                        cbxUsuarioListado.checked = false;
                    }
                });
    });
</script>
