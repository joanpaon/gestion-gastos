package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "perfiles";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Campos
    private final HttpSession sesion;

    public PerfilDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, TABLA, usuario);
    }

    public List<Perfil> obtenerPerfiles() {
        return obtenerPerfiles(PL);
    }

    public Perfil obtenerPerfil(int id) {
        // Parámetros de Listado
        PL.setFilterFields(Arrays.asList("id"));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);

        // Lista de Proyectos
        List<Perfil> perfiles = obtenerPerfiles(PL);

        // Referencia de Entidad
        return perfiles.isEmpty() ? null : perfiles.get(0);
    }

    public boolean insertarPerfil(Perfil perfil) {
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
                parametrizarInsert(ps, perfil);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPerfil(int id) {
        // SQL
        final String SQL = "DELETE FROM perfiles WHERE id=?";

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
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

    public boolean modificarPerfil(Perfil perfil) {
        // SQL
        final String SQL = generarSQLUpdate();

        // Número de Registros Afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarUpdate(ps, perfil);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Long contarPerfiles(ParametrosListado pl) {
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(pl);

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                // BD > Lista de Entidades
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

    public List<Perfil> obtenerPerfiles(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarPerfiles(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Perfil> perfiles = new ArrayList<>();

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(new ParametrosListado("gestion_gastos", "perfiles"));

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        perfiles = exportarListaPerfiles(ps);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return perfiles;
    }

    private List<Perfil> exportarListaPerfiles(PreparedStatement ps) throws SQLException {
        // Lista 
        List<Perfil> lista = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Perfil data = exportarPerfil(rs);

                // Entidad > Lista
                lista.add(data);
            }
        }

        // Retorno: Lista de Perfiles
        return lista;
    }

    private Perfil exportarPerfil(ResultSet rs) throws SQLException {
        // Fila Actual > Campos 
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String info = rs.getString("info");
        String icono = rs.getString("icono");
        int status = rs.getInt("status");
        String data = rs.getString("data");
        Date createdAt = rs.getDate("created_at");
        Date updatedAt = rs.getDate("updated_at");

        // Campos > Entidad
        return new Perfil(id, nombre, info, icono,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
                + "* "
                + "FROM "
                + "perfiles";
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "perfiles";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "perfiles "
                + "("
                + "nombre, info, icono, "
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "perfiles "
                + "SET "
                + "nombre=?, info=?, icono=?, "
                + "status=?, data=?, created_at=?, updated_at=? "
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

    private void parametrizarInsert(PreparedStatement ps, Perfil perfil)
            throws SQLException {
        ps.setString(1, perfil.getNombre());
        ps.setString(2, perfil.getInfo());
        ps.setString(3, perfil.getIcono());
        ps.setInt(4, perfil.getStatus());
        ps.setString(5, perfil.getData());
        ps.setDate(6, new java.sql.Date(perfil.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(perfil.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Perfil perfil)
            throws SQLException {
        ps.setString(1, perfil.getNombre());
        ps.setString(2, perfil.getInfo());
        ps.setString(3, perfil.getIcono());
        ps.setInt(4, perfil.getStatus());
        ps.setString(5, perfil.getData());
        ps.setDate(6, new java.sql.Date(perfil.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(perfil.getUpdatedAt().getTime()));
        ps.setInt(8, perfil.getId());
    }
}
