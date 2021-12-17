package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.ProcesoDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Proceso;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProcesoBLL {

    // Capa de Datos
    private final ProcesoDAL dal = new ProcesoDAL();

    public List<Proceso> obtenerProcesos() {
        return dal.obtenerProcesos();
    }

    public Proceso obtenerProceso(int id) {
        return dal.obtenerProceso(id);
    }

    public boolean insertarProceso(Proceso p) {
        return dal.insertarProceso(p);
    }

    public boolean modificarProceso(Proceso p) {
        return dal.modificarProceso(p);
    }

    public boolean borrarProceso(int id) {
        return dal.borrarProceso(id);
    }

    public Long contarProcesos(ParametrosListado p) {
        return dal.contarProcesos(p);
    }

    public List<Proceso> obtenerProcesos(ParametrosListado p) {
        return dal.obtenerProcesos(p);
    }
}
