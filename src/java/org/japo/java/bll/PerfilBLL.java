package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.PerfilDAL;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilBLL {

    // Capa de Datos
    private final PerfilDAL dal = new PerfilDAL();

    public List<Perfil> obtenerPerfiles() {
        return dal.obtenerPerfiles();
    }

    public Perfil obtenerPerfil(int id) {
        return dal.obtenerPerfil(id);
    }

    public boolean insertarPerfil(Perfil p) {
        return dal.insertarPerfil(p);
    }

    public boolean borrarPerfil(int id) {
        return dal.borrarPerfil(id);
    }

    public boolean modificarPerfil(Perfil p) {
        return dal.modificarPerfil(p);
    }

    public Long contarPerfiles(ParametrosListado pl) {
        return dal.contarPerfiles(pl);
    }

    public List<Perfil> obtenerPerfiles(ParametrosListado pl) {
        return dal.obtenerPerfiles(pl);
    }
}
