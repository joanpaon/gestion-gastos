package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Perfil {
    // Constantes
    public static final int VISITANTE = 1;
    public static final int BASICO = 2;
    public static final int AVANZADO = 3;
    public static final int ADMIN = 4;
    public static final int DEV = 5;
    
    // Campos
    private int id;
    private String nombre;
    private String info;
    private String icono;
    private int status;
    private String data;
    private Date createdAt;
    private Date updatedAt;

    public Perfil(int id, String nombre, String info, String icono,
            int status, String data, Date createdAt, Date updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.info = info;
        this.icono = icono;
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
