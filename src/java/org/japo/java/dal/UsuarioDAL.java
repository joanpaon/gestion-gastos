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
import org.japo.java.entities.Usuario;
import org.japo.java.entities.UsuarioLista;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioDAL {

    public List<Usuario> obtenerUsuarios() {
        // SQL
        final String SQL = "SELECT * FROM usuarios";

        // Lista Vacía
        List<Usuario> lista = new ArrayList<>();

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
                        String user = rs.getString("user");
                        String pass = rs.getString("pass");
                        String email = rs.getString("email");
                        String icono = rs.getString("icono");
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        Usuario u = new Usuario(id, user, pass, email,
                                icono, perfil, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(u);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public Usuario obtenerUsuario(int _id) {
        // SQL
        final String SQL = "SELECT * FROM usuarios WHERE id=?";

        // Referencia de Entidad
        Usuario u = null;

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
                        String user = rs.getString("user");
                        String pass = rs.getString("pass");
                        String email = rs.getString("email");
                        String icono = rs.getString("icono");
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        u = new Usuario(id, user, pass, email, icono,
                                perfil, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return u;
    }

    public boolean insertarUsuario(Usuario u) {
        // SQL
        final String SQL = "INSERT INTO usuarios "
                + "(user, pass, email, icono, perfil, info) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

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
                ps.setString(1, u.getUser());
                ps.setString(2, u.getPass());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getIcono());
                ps.setInt(5, u.getPerfil());
                ps.setString(6, u.getInfo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarUsuario(int _id) {
        // SQL
        final String SQL = "DELETE FROM usuarios WHERE id=?";

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

    public boolean modificarUsuario(Usuario u) {
        // SQL
        final String SQL = "UPDATE usuarios "
                + "SET user=?, pass=?, email=?, icono=?, perfil=?, info=?, "
                + "status=?, data=?, created_at=?, updated_at=? "
                + "WHERE id=?";

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
                ps.setString(1, u.getUser());
                ps.setString(2, u.getPass());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getIcono());
                ps.setInt(5, u.getPerfil());
                ps.setString(6, u.getInfo());
                ps.setInt(7, u.getStatus());
                ps.setString(8, u.getData());
                ps.setDate(9, new java.sql.Date(u.getCreatedAt().getTime()));
                ps.setDate(10, new java.sql.Date(u.getUpdatedAt().getTime()));
                ps.setInt(11, u.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Usuario obtenerUsuario(String _user) {
        // SQL
        final String SQL = "SELECT * FROM usuarios WHERE user=?";

        // Referencia de Entidad
        Usuario u = null;

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
                ps.setString(1, _user);

                // BD > Entidad
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Registro Actual > Campos
                        int id = rs.getInt("id");
                        String user = rs.getString("user");
                        String pass = rs.getString("pass");
                        String email = rs.getString("email");
                        String icono = rs.getString("icono");
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        u = new Usuario(id, user, pass, email, icono,
                                perfil, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return u;
    }


    public Long contarUsuarios(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "usuarios "
                + "INNER JOIN "
                + "perfiles ON usuarios.perfil = perfiles.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Usuario > Perfil: ID
        String sqlUser;
        if (pl.getUser() == null) {
            sqlUser = "";
        } else if (pl.getUser().getPerfil() == Perfil.DEV || pl.getUser().getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "usuarios.id=" + pl.getUser().getId();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("usuarios.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("perfiles.nombre LIKE '%%%s%%'", pl.getFilterExp());
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

    public List<UsuarioLista> obtenerUsuarios(ParametrosListado pl) {
        String sqlSelec
                = "SELECT "
                + "usuarios.id AS id, "
                + "usuarios.user AS nombre, "
                + "perfiles.nombre AS perfil "
                + "FROM "
                + "usuarios "
                + "INNER JOIN "
                + "perfiles ON usuarios.perfil = perfiles.id";

        // WHERE - Usuario: ID
        String sqlUser;
        if (pl.getUser() == null) {
            sqlUser = "";
        } else if (pl.getUser().getPerfil() == Perfil.DEV || pl.getUser().getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "usuarios.id=" + pl.getUser().getId();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("usuarios.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("perfiles.nombre LIKE '%%%s%%'", pl.getFilterExp());
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
        List<UsuarioLista> lista = new ArrayList<>();

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
                        String perfil = rs.getString("perfil");

                        // Campos > Entidad
                        UsuarioLista al = new UsuarioLista(id, nombre, perfil);

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
