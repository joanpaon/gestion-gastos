package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.UsuarioDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.UsuarioLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioBLL {

    // Capa de Datos
    private final UsuarioDAL dal = new UsuarioDAL();

    public List<Usuario> obtenerUsuarios() {
        return dal.obtenerUsuarios();
    }

    public Usuario obtenerUsuario(int id) {
        return dal.obtenerUsuario(id);
    }

    public boolean insertarUsuario(Usuario u) {
        return dal.insertarUsuario(u);
    }

    public boolean borrarUsuario(int id) {
        return dal.borrarUsuario(id);
    }

    public boolean modificarUsuario(Usuario u) {
        return dal.modificarUsuario(u);
    }

    public Usuario obtenerUsuario(String user) {
        return dal.obtenerUsuario(user);
    }

    public Long contarUsuarios(ParametrosListado p) {
        return dal.contarUsuarios(p);
    }

    public List<UsuarioLista> obtenerUsuarios(ParametrosListado p) {
        return dal.obtenerUsuarios(p);
    }
}
