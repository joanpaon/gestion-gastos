package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class UsuarioPermisoLista implements Serializable {

    private int id;
    private String proceso;
    private String usuario;

    public UsuarioPermisoLista() {
    }

    public UsuarioPermisoLista(int id, String proceso, String usuario) {
        this.id = id;
        this.proceso = proceso;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
