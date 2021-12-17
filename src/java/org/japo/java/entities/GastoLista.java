package org.japo.java.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class GastoLista implements Serializable {

    private int id;
    private String info;
    private Date fecha;
    private double importe;

    public GastoLista() {
    }

    public GastoLista(int id, String info, Date fecha, double importe) {
        this.id = id;
        this.info = info;
        this.fecha = fecha;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

}
