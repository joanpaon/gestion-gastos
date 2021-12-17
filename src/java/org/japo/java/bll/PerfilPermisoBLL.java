package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.PerfilPermisoDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.PerfilPermiso;
import org.japo.java.entities.PerfilPermisoLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilPermisoBLL {

    // Capa de Datos
    private final PerfilPermisoDAL dal = new PerfilPermisoDAL();

    public PerfilPermiso obtenerPerfilPermiso(int id) {
        return dal.obtenerPerfilPermiso(id);
    }

    public boolean insertarPerfilPermiso(PerfilPermiso pp) {
        return dal.insertarPerfilPermiso(pp);
    }

    public boolean borrarPerfilPermiso(int id) {
        return dal.borrarPerfilPermiso(id);
    }

    public boolean modificarPerfilPermiso(PerfilPermiso pp) {
        return dal.modificarPerfilPermiso(pp);
    }

    public boolean cambiarPerfilPermiso(PerfilPermiso pg, boolean estado) {
        boolean procesoOK;

        if (estado) {
            // Entidad Permisos Perfil > BD
            procesoOK = dal.insertarPerfilPermiso(pg);
        } else {
            procesoOK = dal.borrarPerfilPermiso(pg);
        }

        return procesoOK;
    }

    public Long contarPerfilPermisos(ParametrosListado p) {
        return dal.contarPerfilPermisos(p);
    }

    public List<PerfilPermisoLista> obtenerPerfilPermisos(ParametrosListado p) {
        return dal.obtenerPerfilPermisos(p);
    }
}
