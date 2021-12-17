package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.UsuarioPermisoDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.UsuarioPermiso;
import org.japo.java.entities.UsuarioPermisoLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioPermisoBLL {

    // Capa de Datos
    private final UsuarioPermisoDAL dal = new UsuarioPermisoDAL();

    public List<UsuarioPermiso> obtenerUsuarioPermisos() {
        return dal.obtenerUsuarioPermisos();
    }

    public UsuarioPermiso obtenerUsuarioPermiso(int id) {
        return dal.obtenerUsuarioPermiso(id);
    }

    public boolean insertarUsuarioPermiso(UsuarioPermiso pu) {
        return dal.insertarUsuarioPermiso(pu);
    }

    public boolean borrarUsuarioPermiso(int id) {
        return dal.borrarUsuarioPermiso(id);
    }

    public boolean modificarUsuarioPermiso(UsuarioPermiso pu) {
        return dal.modificarUsuarioPermiso(pu);
    }

    public Long contarPermisosUsuario(ParametrosListado p) {
        return dal.contarPermisosUsuario(p);
    }

    public List<UsuarioPermisoLista> obtenerPermisosUsuario(ParametrosListado p) {
        return dal.obtenerUsuarioPermisos(p);
    }
}
