package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class PerfilPermisoLista implements Serializable {

    private int id;
    private String proceso;
    private String perfil;

    public PerfilPermisoLista() {
    }

    public PerfilPermisoLista(int id, String proceso, String perfil) {
        this.id = id;
        this.proceso = proceso;
        this.perfil = perfil;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
