package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.GastoDAL;
import org.japo.java.entities.Gasto;
import org.japo.java.entities.GastoLista;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class GastoBLL {

    // Capa de Datos
    private final GastoDAL dal = new GastoDAL();

    public List<Gasto> obtenerGastos() {
        return dal.obtenerGastos();
    }

    public Gasto obtenerGasto(int id) {
        return dal.obtenerGasto(id);
    }

    public boolean insertarGasto(Gasto g) {
        return dal.insertarGasto(g);
    }

    public boolean modificarGasto(Gasto g) {
        return dal.modificarGasto(g);
    }

    public boolean borrarGasto(int id) {
        return dal.borrarGasto(id);
    }

    public Long contarGastos(ParametrosListado p) {
        return dal.contarGastos(p);
    }

    public List<GastoLista> obtenerGastos(ParametrosListado p) {
        return dal.obtenerGastos(p);
    }
}
