package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Permiso {

  private int id;
  private int procesoId;
  private String procesoInfo;
  private int perfilId;
  private String perfilInfo;
  private String info;
  private int status;
  private String data;
  private Date createdAt;
  private Date updatedAt;

  public Permiso(int id, int procesoId, String procesoInfo,
          int perfilId, String perfilInfo, String info,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.procesoId = procesoId;
    this.procesoInfo = procesoInfo;
    this.perfilId = perfilId;
    this.perfilInfo = perfilInfo;
    this.info = info;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Permiso(int id, int procesoId, int perfilId, String info,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.procesoId = procesoId;
    this.perfilId = perfilId;
    this.info = info;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Permiso(int procesoId, int perfilId, String info) {
    this.procesoId = procesoId;
    this.perfilId = perfilId;
    this.info = info;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProcesoId() {
    return procesoId;
  }

  public void setProcesoId(int procesoId) {
    this.procesoId = procesoId;
  }

  public String getProcesoInfo() {
    return procesoInfo;
  }

  public void setProcesoInfo(String procesoInfo) {
    this.procesoInfo = procesoInfo;
  }

  public int getPerfilId() {
    return perfilId;
  }

  public void setPerfilId(int perfilId) {
    this.perfilId = perfilId;
  }

  public String getPerfilInfo() {
    return perfilInfo;
  }

  public void setPerfilInfo(String perfilInfo) {
    this.perfilInfo = perfilInfo;
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
