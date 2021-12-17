package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class ProyectoLista implements Serializable {

    private int id;
    private String nombre;
    private String propietario;
    private String cuota;

    public ProyectoLista() {
    }

    public ProyectoLista(int id, String nombre, String propietario, String cuota) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.cuota = cuota;
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

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }
}
