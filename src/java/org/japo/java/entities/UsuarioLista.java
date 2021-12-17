package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class UsuarioLista implements Serializable {

    // Campos
    private int id;
    private String nombre;
    private String perfil;

    public UsuarioLista() {
    }

    public UsuarioLista(int id, String nombre, String perfil) {
        this.id = id;
        this.nombre = nombre;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
