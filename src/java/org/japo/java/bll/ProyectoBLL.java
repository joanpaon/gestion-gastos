package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.ProyectoDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.ProyectoLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProyectoBLL {

    // Capa de Datos
    private final ProyectoDAL dal = new ProyectoDAL();

    public List<Proyecto> obtenerProyectos() {
        return dal.obtenerProyectos();
    }

    public Proyecto obtenerProyecto(int id) {
        return dal.obtenerProyecto(id);
    }

    public boolean insertarProyecto(Proyecto p) {
        return dal.insertarProyecto(p);
    }

    public boolean modificarProyecto(Proyecto p) {
        return dal.modificarProyecto(p);
    }

    public boolean borrarProyecto(int id) {
        return dal.borrarProyecto(id);
    }

    public Long contarProyectos(ParametrosListado p) {
        return dal.contarProyectos(p);
    }

    public List<ProyectoLista> obtenerProyectos(ParametrosListado p) {
        return dal.obtenerProyectos(p);
    }
}
