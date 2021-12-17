package org.japo.java.entities;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class AbonoLista {

    private int id;
    private String proyecto;
    private String usuario;

    public AbonoLista(int id, String proyecto, String usuario) {
        this.id = id;
        this.proyecto = proyecto;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
