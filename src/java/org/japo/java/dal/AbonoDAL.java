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
import org.japo.java.entities.Abono;
import org.japo.java.entities.AbonoLista;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AbonoDAL {

    public List<Abono> obtenerAbonos() {
        // SQL
        final String SQL = "SELECT * FROM abonos";

        // Lista Vacía
        List<Abono> lista = new ArrayList<>();

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
                        int proyecto = rs.getInt("proyecto");
                        int usuario = rs.getInt("usuario");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Abono a = new Abono(id, proyecto, usuario, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(a);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public boolean insertarAbono(Abono a) {
        // SQL
        final String SQL = "INSERT INTO abonos "
                + "(proyecto, usuario, info) "
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
                ps.setInt(1, a.getProyecto());
                ps.setInt(2, a.getUsuario());
                ps.setString(3, a.getInfo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Abono obtenerAbono(int _id) {
        // SQL
        final String SQL = "SELECT * FROM abonos WHERE id=?";

        // Referencia de Entidad
        Abono a = null;

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
                        int proyecto = rs.getInt("proyecto");
                        int usuario = rs.getInt("usuario");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Datos Entidad > Instancia Entidad
                        a = new Abono(id, proyecto, usuario, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return a;
    }

    public boolean borrarAbono(int _id) {
        // SQL
        final String SQL = "DELETE FROM abonos WHERE id=?";

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

    public boolean modificarAbono(Abono a) {
        // SQL
        final String SQL = "UPDATE abonos "
                + "SET proyecto=?, usuario=?, info=? "
                + "WHERE id=?";

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
                ps.setInt(1, a.getProyecto());
                ps.setInt(2, a.getUsuario());
                ps.setString(3, a.getInfo());
                ps.setInt(4, a.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public List<Abono> obtenerAbonos(int _idUser) {
        // SQL
        final String SQL = "SELECT * FROM abonos WHERE usuario=?";

        // Lista Vacía
        List<Abono> lista = new ArrayList<>();

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
                        int proyecto = rs.getInt("proyecto");
                        int usuario = rs.getInt("usuario");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Abono a = new Abono(id, proyecto, usuario, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(a);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public Long contarAbonos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT COUNT(*) "
                + "FROM "
                + "abonos "
                + "INNER JOIN "
                + "proyectos ON abonos.proyecto = proyectos.id "
                + "INNER JOIN "
                + "usuarios ON abonos.usuario = usuarios.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Usuario: ID
        String sqlUser;
        if (pl.getUser() == null) {
            sqlUser = "";
        } else if (pl.getUser().getPerfil() == Perfil.DEV || pl.getUser().getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "abonos.usuario=" + pl.getUser().getId();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("abonos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%'", pl.getFilterExp());
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

    public List<AbonoLista> obtenerAbonos(ParametrosListado pl) {

        // Producto Cartesiano
        // -------------------
        // SELECT abonos.id AS id, proyectos.nombre AS proyecto, usuarios.user AS usuario 
        // FROM abonos, proyectos, usuarios 
        // WHERE abonos.proyecto = proyectos.id AND abonos.usuario = usuarios.id;
        // ---
        // INNER JOIN
        // ----------
        // SELECT
        // abonos.id AS id,
        // proyectos.nombre AS proyecto
        // usuarios.user AS usuario
        // FROM 
        // abonos 
        // INNER JOIN proyectos ON abonos.proyecto = proyectos.id
        // INNER JOIN usuarios ON abonos.usuario = usuarios.id
        // SELECT
        String sqlSelec
                = "SELECT "
                + "abonos.id AS id, "
                + "proyectos.nombre AS proyecto, "
                + "usuarios.user AS usuario "
                + "FROM "
                + "abonos "
                + "INNER JOIN "
                + "proyectos ON abonos.proyecto = proyectos.id "
                + "INNER JOIN "
                + "usuarios ON abonos.usuario = usuarios.id";

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

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("abonos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%'", pl.getFilterExp());
            }
        } else {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = String.format(" %s LIKE '%%%s%%'", pl.getFilterFld(), pl.getFilterExp());
            }
        }

        // WHERE 
        // abonos.usuario=%USER% 
        // AND
        // abonos.id LIKE '%FILTRO_EXP%'
        // OR 
        // proyectos.nombre LIKE '%FILTRO_EXP%'
        // OR
        // usuarios.user LIKE '%FILTRO_EXP%'
        // ---
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

        // ORDER BY %NOMBRE_CAMPO% ASC | DESC
        // ---
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

        // LIMIT %INDICE_TABLA%,%FILAS_PAGINA%
        // LIMIT A,B
        String sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlSort, sqlLimit);

        // Lista Vacía
        List<AbonoLista> lista = new ArrayList<>();

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
                        String proyecto = rs.getString("proyecto");
                        String usuario = rs.getString("usuario");

                        // Campos > Entidad
                        AbonoLista al = new AbonoLista(id, proyecto, usuario);

                        // Entidad > Lista
                        lista.add(al);
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
