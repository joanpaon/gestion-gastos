package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Cuota {

  private int id;
  private String nombre;
  private String info;
  private int status = 0;
  private String data = "{}";
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public Cuota(int id, String nombre, String info,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.nombre = nombre;
    this.info = info;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Cuota(String nombre, String info) {
    this.nombre = nombre;
    this.info = info;
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
