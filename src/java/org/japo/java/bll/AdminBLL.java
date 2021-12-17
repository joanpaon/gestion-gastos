package org.japo.java.bll;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.dal.PerfilPermisoDAL;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.PerfilPermiso;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AdminBLL {

    // Capas de Datos
    private final PerfilPermisoDAL perfilPermisoDAL = new PerfilPermisoDAL();
    private final ProcesoDAL procesoDAL = new ProcesoDAL();

    public boolean validarAccesoComando(HttpSession sesion, String comando) {
        // Semáforo
        boolean validacionOK;

        try {
            // Sesion > Usuario
            Usuario user = (Usuario) sesion.getAttribute("usuario");

            // Usuario > Perfil
            int perfil = user.getPerfil();

            // Perfil + BD > Lista de Comandos
            List<PerfilPermiso> lista = perfilPermisoDAL.obtenerPerfilPermisos(perfil);

            // Nombre Comando > Entidad Comando
            Proceso proceso = procesoDAL.obtenerProceso(comando);

            // Valida Acceso Comando
            int posicion = UtilesGastos.buscarProcesoLista(proceso, lista);

            // Semaforo: true | false
            validacionOK = posicion > -1;
        } catch (Exception e) {
            validacionOK = false;
        }

        // Retorno: true | false
        return validacionOK;
    }

    public boolean validarAccesoServicio(HttpSession session, String servicio) {
        return true;
    }
}
