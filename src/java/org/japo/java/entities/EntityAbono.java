package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class EntityAbono {

  private int id;
  private int proyectoID;
  private String proyectoInfo;
  private int usuarioID;
  private String usuarioInfo;
  private String info;
  private int status;
  private String data;
  private Date createdAt;
  private Date updatedAt;

  public EntityAbono(int id,
          int proyectoID, String proyectoInfo,
          int usuarioID, String usuarioInfo,
          String info,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.proyectoID = proyectoID;
    this.proyectoInfo = proyectoInfo;
    this.usuarioID = usuarioID;
    this.usuarioInfo = usuarioInfo;
    this.info = info;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityAbono(int id, int proyectoID, int usuarioID, String info,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.proyectoID = proyectoID;
    this.usuarioID = usuarioID;
    this.info = info;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityAbono(int id, String proyecto, String usuario) {
    this.id = id;
    this.proyectoInfo = proyecto;
    this.usuarioInfo = usuario;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getUsuarioID() {
    return usuarioID;
  }

  public void setUsuarioID(int usuarioID) {
    this.usuarioID = usuarioID;
  }

  public String getUsuarioInfo() {
    return usuarioInfo;
  }

  public void setUsuarioInfo(String usuarioInfo) {
    this.usuarioInfo = usuarioInfo;
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
