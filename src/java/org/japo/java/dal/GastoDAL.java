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
import org.japo.java.entities.Gasto;
import org.japo.java.entities.GastoLista;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class GastoDAL {

    public List<Gasto> obtenerGastos() {
        // SQL
        final String SQL = "SELECT * FROM gastos ORDER BY created_at";

        // Lista Vacía
        List<Gasto> lista = new ArrayList<>();

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
                        int abono = rs.getInt("abono");
                        double importe = rs.getDouble("importe");
                        String info = rs.getString("info");
                        int partida = rs.getInt("partida");
                        String recibo = rs.getString("recibo");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Gasto p = new Gasto(id, abono, importe, info,
                                partida, recibo,
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

    public Gasto obtenerGasto(int _id) {
        // SQL
        final String SQL = "SELECT * FROM gastos WHERE id=?";

        // Referencia de Entidad
        Gasto p = null;

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
                ps.setInt(1, _id);

                // BD > Entidad
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Registro Actual > Campos
                        int id = rs.getInt("id");
                        int abono = rs.getInt("abono");
                        double importe = rs.getDouble("importe");
                        String info = rs.getString("info");
                        int partida = rs.getInt("partida");
                        String recibo = rs.getString("recibo");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        p = new Gasto(id, abono, importe, info, partida, recibo,
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

    public boolean insertarGasto(Gasto p) {
        // SQL
        final String SQL = "INSERT INTO gastos "
                + "(abono, importe, info, partida, recibo) "
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
                ps.setInt(1, p.getAbono());
                ps.setDouble(2, p.getImporte());
                ps.setString(3, p.getInfo());
                ps.setInt(4, p.getPartida());
                ps.setString(5, p.getRecibo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean modificarGasto(Gasto p) {
        // SQL
        final String SQL = "UPDATE gastos "
                + "SET abono=?, importe=?, info=?, partida=?, recibo=? "
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
                ps.setInt(1, p.getAbono());
                ps.setDouble(2, p.getImporte());
                ps.setString(3, p.getInfo());
                ps.setInt(4, p.getPartida());
                ps.setString(5, p.getRecibo());
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

    public boolean borrarGasto(int _id) {
        // SQL
        final String SQL = "DELETE FROM gastos WHERE id=?";

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

    public List<Gasto> obtenerPagos(int _idUser) {
        // SQL
        final String SQL = "SELECT * FROM gastos WHERE abono IN "
                + "(SELECT id FROM abonos WHERE usuario=?)";

        // Lista Vacía
        List<Gasto> lista = new ArrayList<>();

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
                ps.setInt(1, _idUser);

                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Fila Actual > Campos 
                        int id = rs.getInt("id");
                        int abono = rs.getInt("abono");
                        double importe = rs.getDouble("importe");
                        String info = rs.getString("info");
                        int partida = rs.getInt("partida");
                        String recibo = rs.getString("recibo");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Gasto p = new Gasto(id, abono, importe, info,
                                partida, recibo,
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

    public Long contarGastos(ParametrosListado pl) {
        String sqlSelec
                = "SELECT COUNT(*) "
                + "FROM "
                + "gastos "
                + "INNER JOIN "
                + "abonos ON gastos.abono = abonos.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Usuario: ID
        String sqlUser;
        Usuario user = pl.getUser();
        if (user == null) {
            sqlUser = "";
        } else if (user.getPerfil() == Perfil.DEV || user.getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "abonos.usuario=" + user.getId();
        }

        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("gastos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.info LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.created_at LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.importe LIKE '%%%s%%'", pl.getFilterExp());
            }
        } else {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = String.format(" %s LIKE '%%%s%%'", pl.getFilterFld(), pl.getFilterExp());
            }
        }

        // WHERE - Completo: Usuario + Filtro
        String sqlWhere;
        if (sqlUser.isEmpty()) {
            if (sqlFiltr.isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = String.format(" WHERE %s", sqlFiltr);
            }
        } else {
            if (sqlFiltr.isEmpty()) {
                sqlWhere = String.format(" WHERE %s", sqlUser);
            } else {
                sqlWhere = String.format(" WHERE %s AND (%s)", sqlUser, sqlFiltr);
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

    public List<GastoLista> obtenerGastos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "gastos.id AS id, "
                + "gastos.info AS info, "
                + "gastos.created_at AS fecha, "
                + "gastos.importe AS importe "
                + "FROM "
                + "gastos "
                + "INNER JOIN "
                + "abonos ON gastos.abono = abonos.id";

        // WHERE - Usuario: ID
        String sqlUser;
        Usuario user = pl.getUser();
        if (user == null) {
            sqlUser = "";
        } else if (user.getPerfil() == Perfil.DEV || user.getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "abonos.usuario=" + user.getId();
        }

        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("gastos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.info LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.created_at LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("gastos.importe LIKE '%%%s%%'", pl.getFilterExp());
            }
        } else {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = String.format(" %s LIKE '%%%s%%'", pl.getFilterFld(), pl.getFilterExp());
            }
        }

        // WHERE - Completo: Usuario + Filtro
        String sqlWhere;
        if (sqlUser.isEmpty()) {
            if (sqlFiltr.isEmpty()) {
                sqlWhere = "";
            } else {
                sqlWhere = String.format(" WHERE %s", sqlFiltr);
            }
        } else {
            if (sqlFiltr.isEmpty()) {
                sqlWhere = String.format(" WHERE %s", sqlUser);
            } else {
                sqlWhere = String.format(" WHERE %s AND (%s)", sqlUser, sqlFiltr);
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
        List<GastoLista> lista = new ArrayList<>();

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
                        String info = rs.getString("info");
                        Date fecha = rs.getDate("fecha");
                        double importe = rs.getDouble("importe");

                        // Campos > Entidad
                        GastoLista p = new GastoLista(id, info, fecha, importe);

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
