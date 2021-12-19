package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class EntityPartida {

  private int id;
  private String nombre;
  private String info;
  private String icono;
  private int proyectoID;
  private String proyectoInfo;
  private int status = 0;
  private String data = "{}";
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public EntityPartida(int id, String nombre, String info, String icono,
          int proyectoID, String proyectoInfo,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.proyectoID = proyectoID;
    this.proyectoInfo = proyectoInfo;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityPartida(int id, String nombre, String info, String icono,
          int proyectoID,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.proyectoID = proyectoID;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityPartida(String nombre, String info, String icono, int proyectoID) {
    this.nombre = nombre;
    this.info = info;
    this.icono = icono;
    this.proyectoID = proyectoID;
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

  public int getProyectoID() {
    return proyectoID;
  }

  public void setProyectoID(int proyectoID) {
    this.proyectoID = proyectoID;
  }

  public String getProyectoInfo() {
    return proyectoInfo;
  }

  public void setProyectoInfo(String proyectoInfo) {
    this.proyectoInfo = proyectoInfo;
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
