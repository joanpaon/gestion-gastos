package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.CuotaDAL;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CuotaBLL {

    // Capa de Datos
    private final CuotaDAL dal = new CuotaDAL();;

    public List<Cuota> obtenerCuotas() {
        return dal.obtenerCuotas();
    }

    public Cuota obtenerCuota(int id) {
        return dal.obtenerCuota(id);
    }

    public boolean insertarCuota(Cuota c) {
        return dal.insertarCuota(c);
    }

    public boolean borrarCuota(int id) {
        return dal.borrarCuota(id);
    }

    public boolean modificarCuota(Cuota c) {
        return dal.modificarCuota(c);
    }

    public Long contarCuotas(ParametrosListado p) {
        return dal.contarCuotas(p);
    }

    public List<Cuota> obtenerCuotas(ParametrosListado p) {
        return dal.obtenerCuotas(p);
    }
}
