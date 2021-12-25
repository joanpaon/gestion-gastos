/* 
 * Copyright 2021 José A. Pacheco Ondoño - japolabs@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class AtributosLista implements Serializable {

  private String dbName = "gestion_gastos";
  private String table;
  private Usuario user;
  private Object objetivo;
  private Filtro filtro;
  private Orden orden;
  private Pagina pagina;

  public AtributosLista() {
  }

  public AtributosLista(String dbName, String table) {
    this.dbName = dbName;
    this.table = table;
  }

  public AtributosLista(String dbName, String table, Usuario user) {
    this.dbName = dbName;
    this.table = table;
    this.user = user;
  }

  public AtributosLista(String dbName, String table, Usuario user, Object objetivo) {
    this.dbName = dbName;
    this.table = table;
    this.user = user;
    this.objetivo = objetivo;
  }

  public AtributosLista(String table, Usuario user, Object objetivo,
          Filtro filtro, Orden orden, Pagina pagina) {
    this.table = table;
    this.user = user;
    this.objetivo = objetivo;
    this.filtro = filtro;
    this.orden = orden;
    this.pagina = pagina;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public Usuario getUser() {
    return user;
  }

  public void setUser(Usuario user) {
    this.user = user;
  }

  public Object getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(Object objetivo) {
    this.objetivo = objetivo;
  }

  public Filtro getFiltro() {
    return filtro;
  }

  public void setFiltro(Filtro filtro) {
    this.filtro = filtro;
  }

  public Orden getOrden() {
    return orden;
  }

  public void setOrden(Orden orden) {
    this.orden = orden;
  }

  public Pagina getPagina() {
    return pagina;
  }

  public void setPagina(Pagina pagina) {
    this.pagina = pagina;
  }
}
