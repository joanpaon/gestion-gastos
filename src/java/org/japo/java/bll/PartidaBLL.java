package org.japo.java.bll;

import java.util.List;
import org.japo.java.dal.PartidaDAL;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Partida;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PartidaBLL {

    // Capa de Datos
    private final PartidaDAL dal = new PartidaDAL();

    public List<Partida> obtenerPartidas() {
        return dal.obtenerPartidas();
    }

    public Partida obtenerPartida(int id) {
        return dal.obtenerPartida(id);
    }

    public boolean insertarPartida(Partida p) {
        return dal.insertarPartida(p);
    }

    public boolean modificarPartida(Partida p) {
        return dal.modificarPartida(p);
    }

    public boolean borrarPartida(int id) {
        return dal.borrarPartida(id);
    }

    public Long contarPartidas(ParametrosListado p) {
        return dal.contarPartidas(p);
    }

    public List<Partida> obtenerPartidas(ParametrosListado p) {
        return dal.obtenerPartidas(p);
    }
}
