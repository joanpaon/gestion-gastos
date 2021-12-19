package org.japo.java.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class EntityGasto implements Serializable {

  private int id;
  private int abonoID;
  private String abonoInfo;
  private double importe;
  private String info;
  private int partidaID;
  private String partidaInfo;
  private String recibo;
  private int status;
  private String data;
  private Date createdAt;
  private Date updatedAt;

  public EntityGasto(int id, int abonoID, String abonoInfo, double importe,
          String info, int partidaID, String partidaInfo, String recibo,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.abonoID = abonoID;
    this.abonoInfo = abonoInfo;
    this.importe = importe;
    this.info = info;
    this.partidaID = partidaID;
    this.partidaInfo = partidaInfo;
    this.recibo = recibo;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityGasto(int id, int abono, double importe, String info,
          int partida, String recibo,
          int status, String data, Date createdAt, Date updatedAt) {
    this.id = id;
    this.abonoID = abono;
    this.importe = importe;
    this.info = info;
    this.partidaID = partida;
    this.recibo = recibo;
    this.status = status;
    this.data = data;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public EntityGasto(double importe, String info, Date fecha) {
    this.importe = importe;
    this.info = info;
    this.updatedAt = fecha;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAbonoID() {
    return abonoID;
  }

  public void setAbonoID(int abonoID) {
    this.abonoID = abonoID;
  }

  public String getAbonoInfo() {
    return abonoInfo;
  }

  public void setAbonoInfo(String abonoInfo) {
    this.abonoInfo = abonoInfo;
  }

  public double getImporte() {
    return importe;
  }

  public void setImporte(double importe) {
    this.importe = importe;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public int getPartidaID() {
    return partidaID;
  }

  public void setPartidaID(int partidaID) {
    this.partidaID = partidaID;
  }

  public String getPartidaInfo() {
    return partidaInfo;
  }

  public void setPartidaInfo(String partidaInfo) {
    this.partidaInfo = partidaInfo;
  }

  public String getRecibo() {
    return recibo;
  }

  public void setRecibo(String recibo) {
    this.recibo = recibo;
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
