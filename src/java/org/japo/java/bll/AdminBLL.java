package org.japo.java.bll;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.japo.java.dal.PermisoDAL;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.EntityPermiso;
import org.japo.java.entities.EntityProceso;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AdminBLL {

    // Capas de Datos
    private final PermisoDAL perfilPermisoDAL = new PermisoDAL();
    private final ProcesoDAL procesoDAL = new ProcesoDAL();

    public boolean validarAccesoComando(HttpSession sesion, String comando) {
        // Semáforo
        boolean checkOK;

        try {
            // Sesion > Usuario
            EntityUsuario usuario = (EntityUsuario) sesion.getAttribute("usuario");

            // EntityUsuario > Perfil
            int perfil = usuario.getPerfilID();

            // Validar Perfil Desarrollador
            if (perfil == EntityPerfil.DEVEL) {
              checkOK = true;
            } else {
              // Perfil + BD > Lista de Comandos
              List<EntityPermiso> permisos = perfilPermisoDAL.obtenerPermisos(perfil);

              // Nombre Comando > Entidad Comando
              EntityProceso proceso = procesoDAL.obtenerProceso(comando);

              // Valida Acceso Comando
              int posicion = UtilesGastos.buscarProcesoLista(proceso, permisos);

              // Semaforo: true | false
              checkOK = posicion > -1;
            }
        } catch (Exception e) {
            checkOK = false;
        }

        // Retorno: true | false
        return checkOK;
    }

    public boolean validarAccesoServicio(HttpSession session, String servicio) {
        return true;
    }
}
