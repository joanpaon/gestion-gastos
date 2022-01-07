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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Usuario;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public class UtilesGastos {

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

    public static void definirFiltroListado(ParametrosListado pl, HttpServletRequest request) {
        // Constantes
        final String FILTRO_CAMPO = "filter-fld";
        final String FILTRO_PATRON = "filter-exp";
        final String FILTRO_ESTRICTO = "filter-strict";

        // Request > Filtro
        String[] filtroCamposArray = request.getParameterValues(FILTRO_CAMPO);
        String filtroPatron = request.getParameter(FILTRO_PATRON);
        String filtroEstricto = request.getParameter(FILTRO_ESTRICTO);

        // Adaptar Filtro
        List<String> filtroCampos;
        if (filtroCamposArray == null) {
            filtroCampos = pl.getFilterFields();
        } else {
            filtroCampos = new ArrayList<>(Arrays.asList(filtroCamposArray));
        }
        filtroPatron = filtroPatron == null ? pl.getFilterValue() : filtroPatron.trim();
        filtroEstricto = filtroEstricto == null ? pl.isFilterStrict() + "" : filtroEstricto;
        filtroEstricto = filtroCampos.size() != 1 ? false + "" : filtroEstricto;

        // Filtro > Parámetros Listado
        pl.setFilterFields(filtroCampos);
        pl.setFilterValue(filtroPatron);
        pl.setFilterStrict(filtroEstricto.equals("true"));
    }

    public static void definirOrdenListado(ParametrosListado pl, HttpServletRequest request) {
        // Constantes
        final String ORDEN_CAMPO = "sort-fld";
        final String ORDEN_AVANCE = "sort-dir";

        // Request > Sort Parameters
        String ordenCampo = request.getParameter(ORDEN_CAMPO);
        String ordenAvance = request.getParameter(ORDEN_AVANCE);

        // Adaptar Campo de Ordenación
        ordenCampo = ordenCampo == null ? pl.getOrderField() : ordenCampo.trim();

        // Adaptar Avance de Ordenación
        if (ordenAvance == null) {
            ordenAvance = pl.getOrderAdvance();
        } else if (ordenAvance.isBlank()) {
            ordenAvance = "";
        } else if (ordenAvance.trim().equalsIgnoreCase("ASC")) {
            ordenAvance = "ASC";
        } else if (ordenAvance.trim().equalsIgnoreCase("DESC")) {
            ordenAvance = "DESC";
        } else {
            ordenAvance = "";
        }

        // Parámetros Ordenación > Parámetros Listado
        pl.setOrderField(ordenCampo);
        pl.setOrderAdvance(ordenAvance);
    }

    // Accion + Filas por Página + Indice de Fila ( Previo ) + 
    // Filas Totales + Número de Página > Indice de Fila ( Actualizado )
    public static void definirIndiceListado(ParametrosListado pl) {
        // Parámetros Pagina
        String accion = pl.getAction();
        long filasPagina = pl.getRowsPage();
        long indiceFila = pl.getRowIndex();
        long filasTotal = pl.getRowCount();
        int numPagina = pl.getPage();

        // Discriminar Operación Actual
        switch (accion) {
            case "prv":
                // Parámetros Paginación - PREVIO
                indiceFila = indiceFila - filasPagina > 0 ? indiceFila - filasPagina : 0;
                break;
            case "nxt":
                // Parámetros Paginación - SIGUIENTE
                indiceFila = indiceFila + filasPagina < filasTotal ? indiceFila + filasPagina : indiceFila;
                break;
            case "end":
                // Parámetros Paginación - FIN
                indiceFila = filasTotal / filasPagina * filasPagina;
                indiceFila = indiceFila >= filasPagina && filasTotal % filasPagina == 0 ? indiceFila - filasPagina : indiceFila;
                break;
            case "num":
                // Parámetros Paginación - Nº PAGINA
                indiceFila = numPagina * filasPagina;
                break;
            case "ini":
            default:
                // Parámetros Paginación - INICIO
                indiceFila = 0L;
        }

        // Navegación > Parámetros Listado
        pl.setRowIndex(indiceFila);
    }

    public static void definirNavegacionListado(ParametrosListado pl, HttpServletRequest request) {
        // Constantes
        final String PAGINA_ACTUAL = "page";
        final String ACCION_PAGINA = "op";
        final String FILAS_PAGINA = "rows-page";

        // Acción Actual
        String accion = "ini";
        if (request.getParameter(ACCION_PAGINA) == null) {
            accion = "ini";
        } else if (request.getParameter(ACCION_PAGINA).equals("prv")
                || request.getParameter(ACCION_PAGINA).equals("nxt")
                || request.getParameter(ACCION_PAGINA).equals("end")
                || request.getParameter(ACCION_PAGINA).equals("num")) {
            accion = request.getParameter(ACCION_PAGINA);
        }

        // Página Actual
        int pagina;
        try {
            pagina = Integer.parseInt(request.getParameter(PAGINA_ACTUAL));
        } catch (NumberFormatException e) {
            // En caso que la obtención de la página de paginado desde 
            // la petición no sea posible se utilizará el valor actual
            // de los parámetros de listado
            pagina = pl.getPage();
        }

        // Filas por Página
        long filasPagina;
        try {
            filasPagina = Long.parseLong(request.getParameter(FILAS_PAGINA));
        } catch (NumberFormatException e) {
            // En caso que la obtención de las lineas por página desde 
            // la petición no sea posible se utilizará el valor actual
            // de los parámetros de listado
            filasPagina = pl.getRowsPage();
        }

        // Navegación > Parámetros Listado
        pl.setAction(accion);
        pl.setPage(pagina);
        pl.setRowsPage(filasPagina);
    }
}
