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
import org.japo.java.entities.UsuarioPermiso;
import org.japo.java.entities.UsuarioPermisoLista;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioPermisoDAL {

    public List<UsuarioPermiso> obtenerUsuarioPermisos() {
        // SQL
        final String SQL = "SELECT * FROM usuario_permisos ORDER BY usuario";

        // Lista Vacía
        List<UsuarioPermiso> lista = new ArrayList<>();

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
                        int proceso = rs.getInt("proceso");
                        int usuario = rs.getInt("usuario");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Datos Entidad > Instancia Entidad
                        UsuarioPermiso pu = new UsuarioPermiso(id, proceso, usuario, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(pu);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public UsuarioPermiso obtenerUsuarioPermiso(int _id) {
        // SQL
        final String SQL = "SELECT * FROM usuario_permisos WHERE id=?";

        // Referencia de Entidad
        UsuarioPermiso pu = null;

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
                        int proceso = rs.getInt("proceso");
                        int usuario = rs.getInt("usuario");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Datos Entidad > Instancia Entidad
                        pu = new UsuarioPermiso(id, proceso, usuario, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return pu;
    }

    public boolean insertarUsuarioPermiso(UsuarioPermiso up) {
        // SQL
        final String SQL = "INSERT INTO usuario_permisos "
                + "(proceso, usuario, info) "
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
                ps.setInt(1, up.getProceso());
                ps.setInt(2, up.getUsuario());
                ps.setString(3, up.getInfo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarUsuarioPermiso(int _id) {
        // SQL
        final String SQL = "DELETE FROM usuario_permisos WHERE id=?";

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

    public boolean modificarUsuarioPermiso(UsuarioPermiso up) {
        // SQL
        final String SQL = "UPDATE usuario_permisos "
                + "SET proceso=?, usuario=?, info=? "
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
                ps.setInt(1, up.getProceso());
                ps.setInt(2, up.getUsuario());
                ps.setString(3, up.getInfo());
                ps.setInt(4, up.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }


    public Long contarPermisosUsuario(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "usuario_permisos "
                + "INNER JOIN "
                + "procesos ON usuario_permisos.proceso = procesos.id "
                + "INNER JOIN "
                + "usuarios ON usuario_permisos.usuario = usuarios.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Usuario > Perfil: ID
        String sqlUser;
        if (pl.getUser() == null) {
            sqlUser = "";
        } else if (pl.getUser().getPerfil() == Perfil.DEV || pl.getUser().getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "usuario_permisos.usuario=" + pl.getUser().getId();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("usuario_permisos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("usuarios.user LIKE '%%%s%%' ", pl.getFilterExp());
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

    public List<UsuarioPermisoLista> obtenerUsuarioPermisos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "usuario_permisos.id AS id, "
                + "procesos.nombre AS proceso, "
                + "usuarios.user AS usuario "
                + "FROM "
                + "usuario_permisos "
                + "INNER JOIN "
                + "procesos ON usuario_permisos.proceso = procesos.id "
                + "INNER JOIN "
                + "usuarios ON usuario_permisos.usuario = usuarios.id";

        // WHERE - Usuario: ID
        String sqlUser;
        Usuario user = pl.getUser();
        if (user == null) {
            sqlUser = "";
        } else if (user.getPerfil() == Perfil.DEV || user.getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "usuario_permisos.usuario=" + user.getId();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("usuario_permisos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
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

        // ORDER BY %NOMBRE_CAMPO% ASC | DESC
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
        String sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlSort, sqlLimit);

        // Lista Vacía
        List<UsuarioPermisoLista> lista = new ArrayList<>();

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
                        String proceso = rs.getString("proceso");
                        String usuario = rs.getString("usuario");

                        // Campos > Entidad
                        UsuarioPermisoLista pul = new UsuarioPermisoLista(id, proceso, usuario);

                        // Entidad > Lista
                        lista.add(pul);
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
