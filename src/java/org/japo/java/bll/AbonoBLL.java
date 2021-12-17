package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.AbonoDAL;
import org.japo.java.entities.Abono;
import org.japo.java.entities.AbonoLista;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AbonoBLL {

    // Capa de Datos
    private final AbonoDAL dal = new AbonoDAL();

    public List<Abono> obtenerAbonos() {
        return dal.obtenerAbonos();
    }

    public boolean insertarAbono(Abono a) {
        return dal.insertarAbono(a);
    }

    public Abono obtenerAbono(int id) {
        return dal.obtenerAbono(id);
    }

    public boolean borrarAbono(int id) {
        return dal.borrarAbono(id);
    }

    public boolean modificarAbono(Abono a) {
        return dal.modificarAbono(a);
    }

    public List<Abono> obtenerAbonos(int idUser) {
        return dal.obtenerAbonos(idUser);
    }

    public List<AbonoLista> obtenerAbonos(ParametrosListado p) {
        return dal.obtenerAbonos(p);
    }

    public Long contarAbonos(ParametrosListado p) {
        return dal.contarAbonos(p);
    }
}
