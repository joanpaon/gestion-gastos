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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Filtro {

  // Campos
  private List<String> campos = new ArrayList<>();
  private String patron = "";
  private boolean exacto;

  public Filtro() {
  }

  public Filtro(List<String> campos, String patron, boolean exacto) {
    if (campos != null) {
      this.campos = campos;
    }

    if (patron != null) {
      this.patron = patron;
    }

    this.exacto = exacto;
  }

  public List<String> getCampos() {
    return campos;
  }

  public void setCampos(List<String> campos) {
    if (campos != null) {
      this.campos = campos;
    }
  }

  public String getPatron() {
    return patron;
  }

  public void setPatron(String patron) {
    if (patron != null) {
      this.patron = patron;
    }
  }

  public boolean isExacto() {
    return exacto;
  }

  public void setExacto(boolean exacto) {
    this.exacto = exacto;
  }
}
