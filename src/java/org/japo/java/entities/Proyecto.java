package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Proyecto {

    private int id;
    private String nombre;
    private String info;
    private String icono;
    private int propietario;
    private int cuota;
    private int status;
    private String data;
    private Date createdAt;
    private Date updatedAt;

    public Proyecto(int id, String nombre, String info,
            String icono, int propietario, int cuota,
            int status, String data, Date createdAt, Date updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.info = info;
        this.icono = icono;
        this.propietario = propietario;
        this.cuota = cuota;
        this.status = status;
        this.data = data;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
