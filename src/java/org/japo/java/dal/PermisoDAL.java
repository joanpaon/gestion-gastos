package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Permiso;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PermisoDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "permisos";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Campos
    private final HttpSession sesion;

    public PermisoDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, TABLA, usuario);
    }

    public List<Permiso> obtenerPermisos() {
        return obtenerPermisos(PL);
    }

    public Permiso obtenerPermiso(int id) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("permisos.id")));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);
        PL.setRowsPage(Long.MAX_VALUE);

        // Lista de Permisos
        List<Permiso> permisos = obtenerPermisos(PL);

        // Referencia de Entidad
        return permisos.isEmpty() ? null : permisos.get(0);
    }

    public List<Permiso> obtenerPermisos(int perfil) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("permisos.perfil")));
        PL.setFilterValue(perfil + "");
        PL.setFilterStrict(true);
        PL.setRowsPage(Long.MAX_VALUE);

        // Retorno: Lista de Permisos
        return obtenerPermisos(PL);
    }

    public boolean insertarPermiso(Permiso permiso) {
        // SQL
        final String SQL = generarSQLInsert();

        // Número de registros afectados
        int numReg = 0;

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarInsert(ps, permiso);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPermiso(int id) {
        // SQL
        final String SQL = generarSQLDelete();

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setInt(1, id);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPermiso(Permiso permiso) {
        // SQL
        final String SQL
                = "DELETE FROM "
                + "permisos "
                + "WHERE "
                + "proceso=? AND perfil=?";

        // Número de Registros Afectados
        int numReg = 0;

        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setInt(1, permiso.getProcesoId());
                ps.setInt(2, permiso.getPerfilId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean modificarPermiso(Permiso permiso) {
        // SQL
        final String SQL = generarSQLUpdate();

        // Número de Registros Afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarUpdate(ps, permiso);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Long contarPermisos(ParametrosListado pl) {
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(pl);

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        filas = rs.getLong(1);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: Filas Contadas
        return filas;
    }

    public List<Permiso> obtenerPermisos(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarPermisos(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Permiso> permisos = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                permisos = exportarListaPermisos(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return permisos;
    }

    private List<Permiso> exportarListaPermisos(PreparedStatement ps) throws SQLException {
        // Lista 
        List<Permiso> permisos = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Permiso permiso = exportarPermiso(rs);

                // Entidad > Lista
                permisos.add(permiso);
            }
        }

        // Retorno: Lista de Permisos
        return permisos;
    }

    private Permiso exportarPermiso(ResultSet rs) throws SQLException {
        // Fila Actual > Campos 
        int id = rs.getInt("id");
        int procesoID = rs.getInt("proceso_id");
        String procesoInfo = rs.getString("proceso_info");
        int perfilID = rs.getInt("perfil_id");
        String perfilInfo = rs.getString("perfil_info");
        String info = rs.getString("info");
        int status = rs.getInt("status");
        String data = rs.getString("data");
        Date createdAt = rs.getDate("created_at");
        Date updatedAt = rs.getDate("updated_at");

        // Campos > Entidad
        return new Permiso(id,
                procesoID, procesoInfo, perfilID, perfilInfo, info,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
                + "permisos.id AS id, "
                + "permisos.proceso AS proceso_id, "
                + "procesos.nombre AS proceso_info, "
                + "permisos.perfil AS perfil_id, "
                + "perfiles.nombre AS perfil_info, "
                + "permisos.info AS info, "
                + "permisos.status AS status, "
                + "permisos.data AS data, "
                + "permisos.created_at AS created_at, "
                + "permisos.updated_at AS updated_at "
                + "FROM "
                + "permisos "
                + "INNER JOIN "
                + "procesos ON procesos.id = permisos.proceso "
                + "INNER JOIN "
                + "perfiles ON perfiles.id = permisos.perfil";
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "permisos "
                + "INNER JOIN "
                + "procesos ON procesos.id = permisos.proceso "
                + "INNER JOIN "
                + "perfiles ON perfiles.id = permisos.perfil";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "permisos "
                + "("
                + "proceso, perfil, info, "
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "permisos "
                + "SET "
                + "proceso=?, perfil=?, info=?, "
                + "status=?, data=?, created_at=?, updated_at=?"
                + "WHERE id=?";
    }

    public String generarSQLDelete() {
        return ""
                + "DELETE FROM "
                + "permisos "
                + "WHERE id=?";
    }

    private String generarSQLListado(ParametrosListado pl) {
        // SQL Parciales
        String select = generarSQLSelect();
        String where = generarSQLWhere(pl);
        String order = generarSQLOrder(pl);
        String limit = generarSQLLimit(pl);

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        return String.format("%s%s%s%s", select, where, order, limit);
    }

    protected String generarSQLComputo(ParametrosListado pl) {
        // SQL Parciales
        String select = generarSQLSelectComputo();
        String where = generarSQLWhere(pl);

        // SQL Completo: SELECT + WHERE
        return String.format("%s%s", select, where);
    }

    private void parametrizarInsert(PreparedStatement ps, Permiso permiso) throws SQLException {
        ps.setInt(1, permiso.getProcesoId());
        ps.setInt(2, permiso.getPerfilId());
        ps.setString(3, permiso.getInfo());
        ps.setInt(4, permiso.getStatus());
        ps.setString(5, permiso.getData());
        ps.setDate(6, new java.sql.Date(permiso.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(permiso.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Permiso permiso) throws SQLException {
        ps.setInt(1, permiso.getProcesoId());
        ps.setInt(2, permiso.getPerfilId());
        ps.setString(3, permiso.getInfo());
        ps.setInt(4, permiso.getStatus());
        ps.setString(5, permiso.getData());
        ps.setDate(6, new java.sql.Date(permiso.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(permiso.getUpdatedAt().getTime()));
        ps.setInt(8, permiso.getId());
    }

    @Override
    protected String generarSQLWhere(ParametrosListado pl) {
        // SQL
        String sql;

        // SQL Perfil
        String sqlPrf;
        switch (pl.getUser().getPerfilID()) {
            case Perfil.DEVEL:
                sqlPrf = "";
                break;
            case Perfil.ADMIN:
                sqlPrf = "";
                break;
            case Perfil.BASIC:
                sqlPrf = "permisos.perfil=" + pl.getUser().getPerfilID();
                break;
            default:
                sqlPrf = "permisos.perfil=" + pl.getUser().getPerfilID();
        }

        // SQL Filtro
        String sqlFtr = generarSQLFilter(pl);

        // Procesar SQL
        if (sqlPrf.isBlank()) {
            sql = sqlFtr.isBlank() ? "" : " WHERE " + sqlFtr;
        } else if (sqlFtr.isBlank()) {
            sql = " WHERE " + sqlPrf;
        } else {
            sql = " WHERE " + sqlPrf + " AND (" + sqlFtr + ")";
        }

        // Retorno: SQL
        return sql;
    }
}
