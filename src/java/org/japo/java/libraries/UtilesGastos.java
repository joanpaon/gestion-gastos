package org.japo.java.libraries;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.japo.java.entities.EntityAbono;
import org.japo.java.entities.EntityCuota;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.EntityPartida;
import org.japo.java.entities.EntityPermiso;
import org.japo.java.entities.EntityProceso;
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public class UtilesGastos {

    public static final String obtenerNombrePerfil(List<EntityPerfil> lista, int id) {
        // Nombre
        String nombre = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityPerfil p = lista.get(i);
            if (p.getId() == id) {
                nombre = p.getNombre();
                i = lista.size();
            }
        }

        // Retorno
        return nombre;
    }

    public static final String obtenerNombreUsuario(List<EntityUsuario> lista, int id) {
        // Nombre
        String nombre = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityUsuario u = lista.get(i);
            if (u.getId() == id) {
                nombre = u.getUser();
                i = lista.size();
            }
        }

        // Retorno
        return nombre;
    }

    public static final String obtenerNombreProyecto(List<EntityProyecto> lista, int id) {
        // Nombre
        String nombre = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityProyecto p = lista.get(i);
            if (p.getId() == id) {
                nombre = p.getNombre();
                i = lista.size();
            }
        }

        // Retorno
        return nombre;
    }

    public static final String obtenerNombreCuota(List<EntityCuota> lista, int id) {
        // Nombre
        String nombre = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityCuota c = lista.get(i);
            if (c.getId() == id) {
                nombre = c.getNombre();
                i = lista.size();
            }
        }

        // Retorno
        return nombre;
    }

    public static final String obtenerNombrePartida(List<EntityPartida> lista, int id) {
        // Nombre
        String nombre = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityPartida p = lista.get(i);
            if (p.getId() == id) {
                nombre = p.getNombre();
                i = lista.size();
            }
        }

        // Retorno
        return nombre;
    }

    public static final String obtenerInfoAbono(List<EntityAbono> lista, int id) {
        // Nombre
        String info = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityAbono a = lista.get(i);
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
        boolean checkOK;

        try {
            if (sesion == null) {
                checkOK = false;
            } else {
                // Sesion > EntityUsuario
                EntityUsuario u = (EntityUsuario) sesion.getAttribute("usuario");

                // Valida EntityUsuario
                if (u == null) {
                    sesion.invalidate();
                    checkOK = false;
                } else {
                    // Prueba de Validez Ortopédica :S
                    // Llegados aquí ...
                    // Cuando la sesión NO es válida
                    // ( por alguna razón desconocida )
                    // el siguiente proceso lanzará una excepción
                    sesion.setAttribute("test", "delete it!");
                    sesion.removeAttribute("test");
                    checkOK = true;
                }
            }
        } catch (Exception e) {
            if (sesion != null) {
                sesion.invalidate();
            }
            checkOK = false;
        }

        // Retorno Semáforo
        return checkOK;
    }

    public static final String obtenerInfoProceso(List<EntityProceso> lista, int id) {
        // Info
        String info = "";

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityProceso p = lista.get(i);
            if (p.getId() == id) {
                info = p.getInfo();
                i = lista.size();
            }
        }

        // Retorno
        return info;
    }

    public static int buscarProcesoLista(EntityProceso proceso, List<EntityPermiso> lista) {
        // Posicion
        int posicion = -1;

        // Bucle Búsqueda
        for (int i = 0; i < lista.size(); i++) {
            EntityPermiso pg = lista.get(i);
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

        // EntityUsuario > Parametros Listado
        p.setUser((EntityUsuario) sesion.getAttribute("usuario"));

        // Tabla > Parametros Listado
        p.setTable(tabla);

        // Retorno: Parámetros Listado
        return p;
    }

    public static final void definirFiltradoListado(ParametrosListado p,
            HttpServletRequest request) {
        // Constantes
        final String REQUEST_FILTER_FIELD = "filter-fld";
        final String REQUEST_FILTER_EXPRESSION = "filter-exp";
        final String SESSION_FILTER_FIELD = p.getTable() + "-filter-fld";
        final String SESSION_FILTER_EXPRESSION = p.getTable() + "-filter-exp";

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
        p.setFilterField(filterFld);
        p.setFilterValue(filterExp);
    }

    public static final void definirOrdenacionListado(ParametrosListado p,
            HttpServletRequest request) {
        // Constantes
        final String REQUEST_SORT_FIELD = "sort-fld";
        final String REQUEST_SORT_DIRECTION = "sort-dir";
        final String SESSION_SORT_FIELD = p.getTable() + "-sort-fld";
        final String SESSION_SORT_DIRECTION = p.getTable() + "-sort-dir";

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
        p.setOrderField(sortFld);
        p.setOrderProgress(sortDir);
    }

    public static final void definirNavegacionListado(ParametrosListado p,
            HttpServletRequest request) {
        // Constantes
        final long FILAS_PAGINA = 10;
        final String REQUEST_ROWS_PAGE = "rows-page";
        final String SESSION_ROWS_PAGE = p.getTable() + "-rows-page";
        final String SESSION_ROW_INDEX = p.getTable() + "-row-index";

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
                rowIndex = rowIndex + rowsPage < p.getRowCount() ? rowIndex + rowsPage : rowIndex;
                break;
            case "end":
                // Parámetros Paginación - FIN
                rowIndex = p.getRowCount() / rowsPage * rowsPage;
                rowIndex = rowIndex >= rowsPage && p.getRowCount() % rowsPage == 0 ? rowIndex - rowsPage : rowIndex;
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
        p.setRowsPage(rowsPage);
        p.setRowIndex(rowIndex);
    }
}
