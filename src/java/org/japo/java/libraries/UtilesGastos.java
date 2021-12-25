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
package org.japo.java.libraries;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.japo.java.entities.Abono;
import org.japo.java.entities.AtributosLista;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.Filtro;
import org.japo.java.entities.Orden;
import org.japo.java.entities.Pagina;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Partida;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public class UtilesGastos {

  public static final String obtenerNombrePerfil(List<Perfil> lista, int id) {
    // Nombre
    String nombre = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Perfil p = lista.get(i);
      if (p.getId() == id) {
        nombre = p.getNombre();
        i = lista.size();
      }
    }

    // Retorno
    return nombre;
  }

  public static final String obtenerNombreUsuario(List<Usuario> lista, int id) {
    // Nombre
    String nombre = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Usuario u = lista.get(i);
      if (u.getId() == id) {
        nombre = u.getUser();
        i = lista.size();
      }
    }

    // Retorno
    return nombre;
  }

  public static final String obtenerNombreProyecto(List<Proyecto> lista, int id) {
    // Nombre
    String nombre = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Proyecto p = lista.get(i);
      if (p.getId() == id) {
        nombre = p.getNombre();
        i = lista.size();
      }
    }

    // Retorno
    return nombre;
  }

  public static final String obtenerNombreCuota(List<Cuota> lista, int id) {
    // Nombre
    String nombre = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Cuota c = lista.get(i);
      if (c.getId() == id) {
        nombre = c.getNombre();
        i = lista.size();
      }
    }

    // Retorno
    return nombre;
  }

  public static final String obtenerNombrePartida(List<Partida> lista, int id) {
    // Nombre
    String nombre = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Partida p = lista.get(i);
      if (p.getId() == id) {
        nombre = p.getNombre();
        i = lista.size();
      }
    }

    // Retorno
    return nombre;
  }

  public static final String obtenerInfoAbono(List<Abono> lista, int id) {
    // Nombre
    String info = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Abono a = lista.get(i);
      if (a.getId() == id) {
        info = a.getInfo();
        i = lista.size();
      }
    }

    // Retorno
    return info;
  }

  public static final boolean validarImagenBase64(String img) {
    boolean checkOK;
    try {
      // Elimina Prefijo
      String strImg = img.substring(img.indexOf(',') + 1);

      // Base64 > Binario
      byte[] binImg = Base64.getDecoder().decode(strImg);

      // Binario > Cauce Lectura
      InputStream is = new ByteArrayInputStream(binImg);

      // Cauce Lectura > Tipo MIME
      String mimeType = URLConnection.guessContentTypeFromStream(is);

      // Tipo MIME > Semáforo
      checkOK = false
              || mimeType.equals("image/jpeg")
              || mimeType.equals("image/png")
              || mimeType.equals("image/gif");
    } catch (IOException | NullPointerException e) {
      checkOK = false;
    }

    // Retorno: true | false
    return checkOK;
  }

  public static final boolean validarSesion(HttpSession sesion) {
    // Semáforo
    boolean checkOK = false;

    try {
      if (sesion == null) {
        // Semáforo
        checkOK = false;
      } else {
        // Sesion > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // Valida Usuario
        if (usuario == null) {
          // Invalida Sesión
          sesion.invalidate();
        } else {
          // Prueba de Validez Ortopédica :S
          // Llegados aquí ...
          // Cuando la sesión NO es válida
          // ( por alguna razón desconocida )
          // el siguiente proceso lanzará una excepción
          sesion.setAttribute("test", "delete it!");
          sesion.removeAttribute("test");

          // Semáforo
          checkOK = true;
        }
      }
    } catch (Exception e) {
      if (sesion != null) {
        // Invalida Sesión
        sesion.invalidate();
      }

      // Semáforo
      checkOK = false;
    }

    // Retorno Semáforo
    return checkOK;
  }

  public static final String obtenerInfoProceso(List<Proceso> lista, int id) {
    // Info
    String info = "";

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Proceso p = lista.get(i);
      if (p.getId() == id) {
        info = p.getInfo();
        i = lista.size();
      }
    }

    // Retorno
    return info;
  }

  public static int buscarProcesoLista(Proceso proceso, List<Permiso> lista) {
    // Posicion
    int posicion = -1;

    // Bucle Búsqueda
    for (int i = 0; i < lista.size(); i++) {
      Permiso pg = lista.get(i);
      if (pg.getProcesoId() == proceso.getId()) {
        posicion = i;
        i = lista.size();
      }
    }

    // Retorno
    return posicion;
  }

  public static final ParametrosListado iniciarParametrosListado(
          String tabla, HttpServletRequest request) {
    // Parámetros Listado
    ParametrosListado p = new ParametrosListado();

    // Request > Sesion
    HttpSession sesion = request.getSession(false);

    // Usuario > Parametros Listado
    p.setUser((Usuario) sesion.getAttribute("usuario"));

    // Tabla > Parametros Listado
    p.setTable(tabla);

    // Retorno: Parámetros Listado
    return p;
  }

  public static final void definirListaFiltro(ParametrosListado pl,
          HttpServletRequest request) {
    // Constantes
    final String REQUEST_FILTER_FIELD = "filter-fld";
    final String REQUEST_FILTER_EXPRESSION = "filter-exp";
    final String SESSION_FILTER_FIELD = pl.getTable() + "-filter-fld";
    final String SESSION_FILTER_EXPRESSION = pl.getTable() + "-filter-exp";

    // Request > Sesion
    HttpSession sesion = request.getSession(false);

    // Request > Filtro
    String filterFld = request.getParameter(REQUEST_FILTER_FIELD);
    String filterExp = request.getParameter(REQUEST_FILTER_EXPRESSION);

    // Adaptar Filtro
    filterFld = filterFld == null ? "" : filterFld.trim();

    // Validar Filtro - Request
    if (filterExp == null) {
      // Sesión > Filtro - Expresión
      Object oFilterExp = sesion.getAttribute(SESSION_FILTER_EXPRESSION);

      // Adaptar Filtro - Expresión
      filterExp = oFilterExp == null ? "" : oFilterExp.toString().trim();

      // Validar Filtro - Session
      if (filterExp.isEmpty()) {
        // Inicializa Filtro
        filterFld = "";
      } else if (filterFld.isEmpty()) {
        // Sesión > Filtro - Campo
        Object oFilterFld = sesion.getAttribute(SESSION_FILTER_FIELD);

        // Adaptar Filtro - Campo
        filterFld = oFilterFld == null ? "" : oFilterFld.toString().trim();
      }
    } else if (filterExp.isBlank()) {
      // Inicializa Filtro
      filterFld = "";
      filterExp = "";
    } else if (filterFld.isEmpty()) {
      // Sesión > Filtro - Campo
      Object oFilterFld = sesion.getAttribute(SESSION_FILTER_FIELD);

      // Adaptar Filtro - Campo
      filterFld = oFilterFld == null ? "" : oFilterFld.toString().trim();
    }

    // Filtro > Sesión
    sesion.setAttribute(SESSION_FILTER_FIELD, filterFld);
    sesion.setAttribute(SESSION_FILTER_EXPRESSION, filterExp);

    // Filtro > Entidad
    pl.setFilterField(filterFld);
    pl.setFilterValue(filterExp);
  }

  // Actualiza los parámetros de filtro en la entidad de atributos de lista
  // desde los parametros de la petición y si no existen desde los valores 
  // anteriores de la entidad de atributos de lista 
  public static final void definirListaFiltro(AtributosLista la,
          HttpServletRequest request) {
    // Request > Parámetros Filtro
    List<String> campos;
    String[] camposPeticion = request.getParameterValues("filtro-campos");
    String patron = request.getParameter("filtro-patron");
    boolean exacto = request.getParameter("filtro-exacto") != null;

    // Entidad Atributos de Lista > Entidad Filtro
    Filtro filtro = la.getFiltro();

    // Parámetros Filtro - Campos
    if (camposPeticion == null) {
      campos = filtro.getCampos();
    } else {
      campos = Arrays.asList(camposPeticion);
    }

    // Parámetros Filtro - Patron
    if (patron == null) {
      patron = filtro.getPatron();
    } else {
      patron = patron.trim();
    }

    // Validar Parámetros Filtro
    if (campos.isEmpty()) {
      filtro = new Filtro();
    } else {
      filtro = new Filtro(campos, patron, exacto);
    }

    // Parámetros Filtro > Entidad Filtro > Entidad Atributos de Lista
    la.setFiltro(filtro);
  }

  public static final void definirListaOrden(ParametrosListado pl,
          HttpServletRequest request) {
    // Constantes
    final String REQUEST_SORT_FIELD = "sort-fld";
    final String REQUEST_SORT_DIRECTION = "sort-dir";
    final String SESSION_SORT_FIELD = pl.getTable() + "-sort-fld";
    final String SESSION_SORT_DIRECTION = pl.getTable() + "-sort-dir";

    // Request > Sesion
    HttpSession sesion = request.getSession(false);

    // Request > Sort Parameters
    String sortFld;
    String sortDir = request.getParameter(REQUEST_SORT_DIRECTION);

    // Validar Sort
    if (sortDir == null) {
      // Sesion > Objeto Sort Direction
      Object oSortDir = sesion.getAttribute(SESSION_SORT_DIRECTION);

      // Valida Objeto Sort Direction
      if (oSortDir == null) {
        // Desactiva Ordenación
        sortFld = null;
      } else {
        // Objeto Sort Direction > Sort Direction
        sortDir = (String) oSortDir;

        // Valida Sort Direction
        if (sortDir.equalsIgnoreCase("asc") || sortDir.equalsIgnoreCase("desc")) {
          // Request > Sort Field
          sortFld = request.getParameter(REQUEST_SORT_FIELD);

          // Valida Sort Field
          if (sortFld == null) {
            // Sesion > Objeto Sort Field
            Object oSortFld = sesion.getAttribute(SESSION_SORT_FIELD);

            // Valida Objeto Sort Direction
            if (oSortFld == null) {
              // Desactiva Ordenación
              sortDir = null;

              // Elimina Parametros Ordenación de Session
              sesion.removeAttribute(SESSION_SORT_FIELD);
              sesion.removeAttribute(SESSION_SORT_DIRECTION);
            } else {
              // Objeto Sort Field > Sort Direction
              sortFld = (String) oSortFld;

              // Parámetros Ordenación > Session
              sesion.setAttribute(SESSION_SORT_FIELD, sortFld);
              sesion.setAttribute(SESSION_SORT_DIRECTION, sortDir);
            }
          } else {
            // Parámetros Ordenación > Session
            sesion.setAttribute(SESSION_SORT_FIELD, sortFld);
            sesion.setAttribute(SESSION_SORT_DIRECTION, sortDir);
          }
        } else {
          // Desactiva Ordenación
          sortFld = null;
          sortDir = null;

          // Elimina Parametros Ordenación de Session
          sesion.removeAttribute(SESSION_SORT_FIELD);
          sesion.removeAttribute(SESSION_SORT_DIRECTION);
        }
      }
    } else if (sortDir.isBlank()) {
      // Desactiva Ordenación
      sortFld = null;
      sortDir = null;

      // Elimina Parametros Ordenación de Session
      sesion.removeAttribute(SESSION_SORT_FIELD);
      sesion.removeAttribute(SESSION_SORT_DIRECTION);
    } else if (sortDir.equalsIgnoreCase("asc") || sortDir.equalsIgnoreCase("desc")) {
      // Request > Sort Field
      sortFld = request.getParameter(REQUEST_SORT_FIELD);

      // Valida Sort Field
      if (sortFld == null) {
        // Sesion > Objeto Sort Field
        Object oSortFld = sesion.getAttribute(SESSION_SORT_FIELD);

        // Valida Objeto Sort Direction
        if (oSortFld == null) {
          // Desactiva Ordenación
          sortDir = null;

          // Elimina Parametros Ordenación de Session
          sesion.removeAttribute(SESSION_SORT_FIELD);
          sesion.removeAttribute(SESSION_SORT_DIRECTION);
        } else {
          // Objeto Sort Field > Sort Direction
          sortFld = (String) oSortFld;

          // Parámetros Ordenación > Session
          sesion.setAttribute(SESSION_SORT_FIELD, sortFld);
          sesion.setAttribute(SESSION_SORT_DIRECTION, sortDir);
        }
      } else {
        // Parámetros Ordenación > Session
        sesion.setAttribute(SESSION_SORT_FIELD, sortFld);
        sesion.setAttribute(SESSION_SORT_DIRECTION, sortDir);
      }
    } else {
      // Desactiva Ordenación
      sortFld = null;
      sortDir = null;

      // Elimina Parametros Ordenación de Session
      sesion.removeAttribute(SESSION_SORT_FIELD);
      sesion.removeAttribute(SESSION_SORT_DIRECTION);
    }

    // Parámetros Ordenación > Parámetros Listado
    pl.setOrderField(sortFld);
    pl.setOrderProgress(sortDir);
  }

  public static final void definirListaOrden(AtributosLista la,
          HttpServletRequest request) {
    // Atributos de Lista > Orden + Validación
    Orden orden = la.getOrden();

    // Request > Campo de Ordenación
    String campo = request.getParameter("orden-campo");
    if (campo == null) {
      campo = orden.getCampo();
    } else if (campo.isBlank()) {
      campo = "";
    }

    // Request > Avance de Ordenación
    String avance = request.getParameter("orden-avance");
    if (avance == null) {
      avance = orden.getAvance();
    } else if (avance.equalsIgnoreCase("ASC")) {
      avance = "ASC";
    } else if (avance.equalsIgnoreCase("DESC")) {
      avance = "DESC";
    } else {
      avance = "";
    }

    // Orden > Parámetros Listado
    la.setOrden(new Orden(campo, avance));
  }

  public static final void definirListaPagina(ParametrosListado pl,
          HttpServletRequest request) {
    // Constantes
    final long FILAS_PAGINA = 10;
    final String REQUEST_ROWS_PAGE = "rows-page";
    final String SESSION_ROWS_PAGE = pl.getTable() + "-rows-page";
    final String SESSION_ROW_INDEX = pl.getTable() + "-row-index";

    // Request > Sesion
    HttpSession sesion = request.getSession(false);

    // Operación Actual
    String op = "ini";
    if (request.getParameter("op") == null) {
      op = "ini";
    } else if (request.getParameter("op").equals("prv")
            || request.getParameter("op").equals("nxt")
            || request.getParameter("op").equals("end")
            || request.getParameter("op").equals("num")) {
      op = request.getParameter("op");
    }

    // Filas por Página
    Long rowsPage;
    if (request.getParameter(REQUEST_ROWS_PAGE) == null) {
      if (sesion.getAttribute(SESSION_ROWS_PAGE) == null) {
        // Filas Por Página por defecto: 10
        rowsPage = FILAS_PAGINA;

        // Filas por Página > Sesión
        sesion.setAttribute(SESSION_ROWS_PAGE, rowsPage);
      } else {
        // Sesión > Filas por Página
        rowsPage = (Long) sesion.getAttribute(SESSION_ROWS_PAGE);
      }
    } else {
      // request > Filas por Página
      rowsPage = Long.valueOf(request.getParameter(REQUEST_ROWS_PAGE));

      // Filas por Página > Sesión
      sesion.setAttribute(SESSION_ROWS_PAGE, rowsPage);
    }

    // Posición de Página
    Long rowIndex;
    try {
      rowIndex = (Long) sesion.getAttribute(SESSION_ROW_INDEX);
    } catch (NumberFormatException | NullPointerException e) {
      rowIndex = 0L;
    }

    // Discriminar Operación Actual
    switch (op) {
      case "prv":
        // Parámetros Paginación - PREVIO
        rowIndex = rowIndex - rowsPage > 0 ? rowIndex - rowsPage : 0;
        break;
      case "nxt":
        // Parámetros Paginación - SIGUIENTE
        rowIndex = rowIndex + rowsPage < pl.getRowCount() ? rowIndex + rowsPage : rowIndex;
        break;
      case "end":
        // Parámetros Paginación - FIN
        rowIndex = pl.getRowCount() / rowsPage * rowsPage;
        rowIndex = rowIndex >= rowsPage && pl.getRowCount() % rowsPage == 0 ? rowIndex - rowsPage : rowIndex;
        break;
      case "num":
        // Página Enésima
        int pagina = Integer.parseInt(request.getParameter("page"));

        // Parámetros Paginación - Nº PAGINA
        rowIndex = pagina * rowsPage;
        break;
      case "ini":
      default:
        // Parámetros Paginación - INICIO
        rowIndex = 0L;
    }

    // Posición de Página > Sesión
    sesion.setAttribute(SESSION_ROW_INDEX, rowIndex);

    // Navegación > Parámetros Listado
    pl.setRowsPage(rowsPage);
    pl.setRowIndex(rowIndex);
  }

  public static final void definirListaPagina(AtributosLista la,
          HttpServletRequest request) {
    // Sesión > Atributos Página
    Pagina pagina = la.getPagina();

    // Atributos de Lista > Total de Filas
    long filasTotal = pagina.getFilasTotal();

    // Request > Índice de Pagina + Validación
    long indice = 0L;
    if (request.getParameter("indice") != null) {
      indice = Long.parseLong(request.getParameter("indice"));
      if (indice < 0) {
        indice = pagina.getIndice();
      }
    }

    // Request > Filas por Pagina + Validación
    long filasPagina = filasTotal;
    if (request.getParameter("filas-pagina") != null) {
      filasPagina = Long.parseLong(request.getParameter("filas-pagina"));
      if (filasPagina < 0) {
        filasPagina = filasTotal;
      }
    }

    // Request > Navegación + Validación
    String nav = request.getParameter("nav");
    if (nav == null) {
      nav = "ini";
    } else if (!nav.equals("ini") && !nav.equals("end")
            && !nav.equals("prv") && !nav.equals("nxt")
            && !nav.equals("num")) {
      nav = "ini";
    }

    // Navegación > Indice
    switch (nav) {
      case "prv":
        // Parámetros Paginación - PREVIO
        indice = indice - filasPagina > 0 ? indice - filasPagina : 0;
        break;
      case "nxt":
        // Parámetros Paginación - SIGUIENTE
        indice = indice + filasPagina < filasTotal ? indice + filasPagina : indice;
        break;
      case "end":
        // Parámetros Paginación - FIN
        indice = filasTotal / filasPagina * filasPagina;
        indice = indice >= filasPagina && filasTotal % filasPagina == 0 ? indice - filasPagina : indice;
        break;
      case "num":
        // Se recibe el indice de la página a la que se desea ir
        break;
      case "ini":
      default:
        // Parámetros Paginación - INICIO
        indice = 0L;
    }

    // Atributos Pagina > Pagina
    pagina = new Pagina(indice, filasPagina, filasTotal);

    // Atributos Página > Parámetros Listado
    la.setPagina(pagina);
  }
}
