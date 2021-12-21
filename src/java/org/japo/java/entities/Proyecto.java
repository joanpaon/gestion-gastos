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
  private int propietarioID;
  private String propietarioInfo;
  private int cuotaID;
  private String cuotaInfo;
  private int status;
  private String data = "{}";
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public Proyecto(int id, String nombre, String info, String icono,
          int propietarioID, String propietarioInfo,
          int cuotaID, String cuotaInfo,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.propietarioID = propietarioID;
    this.propietarioInfo = propietarioInfo;
    this.cuotaID = cuotaID;
    this.cuotaInfo = cuotaInfo;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Proyecto(String nombre, String info, String icono,
          int propietarioID, int cuotaID,
          int status, String data, Date createdAt, Date updatedAt) {
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.propietarioID = propietarioID;
    this.cuotaID = cuotaID;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Proyecto(String nombre, String info, String icono,
          int propietarioID, int cuotaID) {
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.propietarioID = propietarioID;
    this.cuotaID = cuotaID;
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

  public int getPropietarioID() {
    return propietarioID;
  }

  public void setPropietarioID(int propietarioID) {
    this.propietarioID = propietarioID;
  }

  public String getPropietarioInfo() {
    return propietarioInfo;
  }

  public void setPropietarioInfo(String propietarioInfo) {
    this.propietarioInfo = propietarioInfo;
  }

  public int getCuotaID() {
    return cuotaID;
  }

  public void setCuotaID(int cuotaID) {
    this.cuotaID = cuotaID;
  }

  public String getCuotaInfo() {
    return cuotaInfo;
  }

  public void setCuotaInfo(String cuotaInfo) {
    this.cuotaInfo = cuotaInfo;
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
