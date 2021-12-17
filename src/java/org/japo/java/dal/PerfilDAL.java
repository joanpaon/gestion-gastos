package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilDAL {

    public List<Perfil> obtenerPerfiles() {
        // SQL
        final String SQL = "SELECT * FROM perfiles";

        // Lista Vacía
        List<Perfil> lista = new ArrayList<>();

        // Obtención del Contexto
        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Registro Actual > Campos
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String info = rs.getString("info");
                        String icono = rs.getString("icono");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Perfil u = new Perfil(id, nombre, info, icono,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(u);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public Perfil obtenerPerfil(int _id) {
        // SQL
        final String SQL = "SELECT * FROM perfiles WHERE id=?";

        // Referencia de Entidad
        Perfil g = null;

        // Contexto
        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setInt(1, _id);

                // BD > Entidad
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Registro Actual > Campos
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String info = rs.getString("info");
                        String icono = rs.getString("icono");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        g = new Perfil(id, nombre, info, icono,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return g;
    }

    public boolean insertarPerfil(Perfil g) {
        // SQL
        final String SQL = "INSERT INTO perfiles "
                + "(nombre, info, icono) "
                + "VALUES (?, ?, ?)";

        // Número de registros afectados
        int numReg = 0;

        // Obtención del Contexto
        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setString(1, g.getNombre());
                ps.setString(2, g.getInfo());
                ps.setString(3, g.getIcono());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPerfil(int _id) {
        // SQL
        final String SQL = "DELETE FROM perfiles WHERE id=?";

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setInt(1, _id);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean modificarPerfil(Perfil g) {
        // SQL
        final String SQL = "UPDATE perfiles "
                + "SET nombre=?, info=?, icono=? "
                + "WHERE id=?";

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
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                ps.setString(1, g.getNombre());
                ps.setString(2, g.getInfo());
                ps.setString(3, g.getIcono());
                ps.setInt(4, g.getId());

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
        // SELECT
        String sqlSelec = "SELECT COUNT(*) FROM perfiles";

        // Número de Filas
        long filas = 0;

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("info LIKE '%%%s%%'", pl.getFilterExp());
            }
        } else {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("%s LIKE '%%%s%%'", pl.getFilterFld(), pl.getFilterExp());
            }
        }

        // SQL Completo: SELECT + WHERE
        String sql = String.format("%s%s", sqlSelec, sqlWhere);

        // Obtención del Contexto
        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Número de Filas
                        filas = rs.getLong(1);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Filas
        return filas;
    }

    public List<Perfil> obtenerPerfiles(ParametrosListado pl) {
        // SELECT
        String sqlSelec = "SELECT * FROM perfiles";

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("info LIKE '%%%s%%'", pl.getFilterExp());
            }
        } else {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("%s LIKE '%%%s%%'", pl.getFilterFld(), pl.getFilterExp());
            }
        }

        // ORDER BY
        String sqlSort;
        if (pl.getSortDir() == null || pl.getSortDir().isEmpty()) {
            sqlSort = "";
        } else if (pl.getSortFld() == null || pl.getSortFld().isEmpty()) {
            sqlSort = "";
        } else if (pl.getSortDir().equalsIgnoreCase("asc") || pl.getSortDir().equalsIgnoreCase("desc")) {
            sqlSort = String.format(" ORDER BY %s %s", pl.getSortFld(), pl.getSortDir());
        } else {
            sqlSort = "";
        }

        // LIMIT A,B
        String sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlSort, sqlLimit);

        // Lista Vacía
        List<Perfil> lista = new ArrayList<>();

        // Obtención del Contexto
        try {
            // Contexto Inicial Nombrado JNDI
            Context iniCtx = new InitialContext();

            // Situar Contexto Inicial
            Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

            // Contexto Inicial > DataSource
            DataSource ds = (DataSource) envCtx.lookup("jdbc/gestion_gastos");

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
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
                        Perfil g = new Perfil(id, nombre, info, icono,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(g);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }
}
