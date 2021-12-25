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

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Pagina {

  // Constantes
  public static final long FILAS_PAGINA = 10;

  private long indice = 0L;
  private long filasPagina = Long.MAX_VALUE;
  private long filasTotal = 0L;

  ;

  public Pagina() {
  }

  public Pagina(long indice, long filasPagina, long filasTotal) {
    if (indice >= 0) {
      this.indice = indice;
    }

    if (filasTotal >= 0) {
      this.filasTotal = filasTotal;
    }

    if (filasPagina >= 0 && filasPagina <= filasTotal) {
      this.filasPagina = filasPagina;
    }
  }

  public long getIndice() {
    return indice;
  }

  public void setIndice(long indice) {
    if (indice >= 0) {
      this.indice = indice;
    }
  }

  public long getFilasPagina() {
    return filasPagina;
  }

  public void setFilasPagina(long filasPagina) {
    if (filasPagina >= 0 && filasPagina <= filasTotal) {
      this.filasPagina = filasPagina;
    }
  }

  public long getFilasTotal() {
    return filasTotal;
  }

  public void setFilasTotal(long filasTotal) {
    if (filasTotal >= 0) {
      this.filasTotal = filasTotal;
    }
  }

}
