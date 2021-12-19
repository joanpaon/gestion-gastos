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
import org.japo.java.entities.EntityCuota;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CuotaDAL {

    public List<EntityCuota> obtenerCuotas() {
        // SQL
        final String SQL = "SELECT * FROM cuotas";

        // Lista Vacía
        List<EntityCuota> lista = new ArrayList<>();

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
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        EntityCuota u = new EntityCuota(id, nombre, info,
                                status, data, createdAt, updatedAt);

                        // Producto > Lista
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

    public EntityCuota obtenerCuota(int _id) {
        // SQL
        final String SQL = "SELECT * FROM cuotas WHERE id=?";

        // Referencia de Entidad
        EntityCuota c = null;

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
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        c = new EntityCuota(id, nombre, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return c;
    }

    public boolean insertarCuota(EntityCuota c) {
        // SQL
        final String SQL = "INSERT INTO cuotas "
                + "(nombre, info) "
                + "VALUES (?, ?)";

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
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getInfo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarCuota(int _id) {
        // SQL
        final String SQL = "DELETE FROM cuotas WHERE id=?";

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

    public boolean modificarCuota(EntityCuota c) {
        // SQL
        final String SQL = "UPDATE cuotas "
                + "SET nombre=?, info=? "
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
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getInfo());
                ps.setInt(3, c.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Long contarCuotas(ParametrosListado pl) {
        // SELECT
        String sqlSelec = "SELECT COUNT(*) FROM cuotas";

        // Número de Filas
        long filas = 0;

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
            if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("id LIKE '%%%s%%' OR ", pl.getFilterValue())
                        + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                        + String.format("info LIKE '%%%s%%'", pl.getFilterValue());
            }
        } else {
            if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("%s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
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

    public List<EntityCuota> obtenerCuotas(ParametrosListado pl) {
        // SELECT
        String sqlSelec = "SELECT * FROM cuotas";

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
            if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("id LIKE '%%%s%%' OR ", pl.getFilterValue())
                        + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                        + String.format("info LIKE '%%%s%%'", pl.getFilterValue());
            }
        } else {
            if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("%s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
            }
        }

        // ORDER BY
        String sqlSort;
        if (pl.getOrderProgress() == null || pl.getOrderProgress().isEmpty()) {
            sqlSort = "";
        } else if (pl.getOrderField() == null || pl.getOrderField().isEmpty()) {
            sqlSort = "";
        } else if (pl.getOrderProgress().equalsIgnoreCase("asc") || pl.getOrderProgress().equalsIgnoreCase("desc")) {
            sqlSort = String.format(" ORDER BY %s %s", pl.getOrderField(), pl.getOrderProgress());
        } else {
            sqlSort = "";
        }

        // LIMIT A,B
        String sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlSort, sqlLimit);

        // Lista Vacía
        List<EntityCuota> lista = new ArrayList<>();

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
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        EntityCuota c = new EntityCuota(id, nombre, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(c);
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
