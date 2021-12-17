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
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.ProyectoLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProyectoDAL {

    public List<Proyecto> obtenerProyectos() {
        // SQL
        final String SQL = "SELECT * FROM proyectos";

        // Lista Vacía
        List<Proyecto> lista = new ArrayList<>();

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
                        // Fila Actual > Campos 
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String info = rs.getString("info");
                        String icono = rs.getString("icono");
                        int propietario = rs.getInt("propietario");
                        int cuota = rs.getInt("cuota");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Proyecto p = new Proyecto(id, nombre, info,
                                icono, propietario, cuota,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(p);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public Proyecto obtenerProyecto(int _id) {
        // SQL
        final String SQL = "SELECT * FROM proyectos WHERE id=?";

        // Referencia de Entidad
        Proyecto p = null;

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
                        int propietario = rs.getInt("propietario");
                        int cuota = rs.getInt("cuota");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Datos Entidad > Instancia Entidad
                        p = new Proyecto(id, nombre, info,
                                icono, propietario, cuota,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return p;
    }

    public boolean insertarProyecto(Proyecto p) {
        // SQL
        final String SQL = "INSERT INTO proyectos "
                + "(nombre, info, icono, propietario, cuota) "
                + "VALUES (?, ?, ?, ?, ?)";

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
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getInfo());
                ps.setString(3, p.getIcono());
                ps.setInt(4, p.getPropietario());
                ps.setInt(5, p.getCuota());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean modificarProyecto(Proyecto p) {
        // SQL
        final String SQL = "UPDATE proyectos "
                + "SET nombre=?, info=?, icono=?, propietario=?, cuota=? "
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
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getInfo());
                ps.setString(3, p.getIcono());
                ps.setInt(4, p.getPropietario());
                ps.setInt(5, p.getCuota());
                ps.setInt(6, p.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarProyecto(int _id) {
        // SQL
        final String SQL = "DELETE FROM proyectos WHERE id=?";

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

    public Long contarProyectos(ParametrosListado pl) {
        String sqlSelec
                = "SELECT COUNT(*) "
                + "FROM "
                + "proyectos "
                + "INNER JOIN "
                + "usuarios ON proyectos.propietario = usuarios.id "
                + "INNER JOIN "
                + "cuotas ON proyectos.cuota = cuotas.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("proyectos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("cuotas.nombre LIKE '%%%s%%'", pl.getFilterExp());
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

    public List<ProyectoLista> obtenerProyectos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "proyectos.id AS id, "
                + "proyectos.nombre AS nombre, "
                + "usuarios.user AS propietario, "
                + "cuotas.nombre AS cuota "
                + "FROM "
                + "proyectos "
                + "INNER JOIN "
                + "usuarios ON proyectos.propietario = usuarios.id "
                + "INNER JOIN "
                + "cuotas ON proyectos.cuota = cuotas.id";

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlWhere;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = " WHERE "
                        + String.format("proyectos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("cuotas.nombre LIKE '%%%s%%'", pl.getFilterExp());
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
        List<ProyectoLista> lista = new ArrayList<>();

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
                        String propietario = rs.getString("propietario");
                        String cuota = rs.getString("cuota");

                        // Campos > Entidad
                        ProyectoLista p = new ProyectoLista(id, nombre, propietario, cuota);

                        // Entidad > Lista
                        lista.add(p);
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
