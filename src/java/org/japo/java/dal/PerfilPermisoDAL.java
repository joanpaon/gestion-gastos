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
import org.japo.java.entities.PerfilPermiso;
import org.japo.java.entities.PerfilPermisoLista;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilPermisoDAL {

    public List<PerfilPermiso> obtenerPerfilPermisos() {
        // SQL
        final String SQL = "SELECT * FROM perfil_permisos ORDER BY info ASC";

        // Lista Vacía
        List<PerfilPermiso> lista = new ArrayList<>();

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
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Campos > Entidad
                        PerfilPermiso pg = new PerfilPermiso(id, proceso, perfil, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(pg);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public List<PerfilPermiso> obtenerPerfilPermisos(int _perfil) {
        // SQL
        final String SQL = "SELECT * FROM perfil_permisos WHERE perfil=?";

        // Lista Vacía
        List<PerfilPermiso> lista = new ArrayList<>();

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
                ps.setInt(1, _perfil);

                // BD > Lista de Entidades
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Campos > Variables
                        int id = rs.getInt("id");
                        int proceso = rs.getInt("proceso");
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Variables > Entidad
                        PerfilPermiso pg = new PerfilPermiso(id, proceso, perfil, info,
                                status, data, createdAt, updatedAt);

                        // Entidad > Lista
                        lista.add(pg);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return lista;
    }

    public PerfilPermiso obtenerPerfilPermiso(int _id) {
        // SQL
        final String SQL = "SELECT * FROM perfil_permisos WHERE id=?";

        // Referencia de Entidad
        PerfilPermiso pg = null;

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
                        int perfil = rs.getInt("perfil");
                        String info = rs.getString("info");
                        int status = rs.getInt("status");
                        String data = rs.getString("data");
                        Date createdAt = rs.getDate("created_at");
                        Date updatedAt = rs.getDate("updated_at");

                        // Datos Entidad > Instancia Entidad
                        pg = new PerfilPermiso(id, proceso, perfil, info,
                                status, data, createdAt, updatedAt);
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Entidad
        return pg;
    }

    public boolean insertarPerfilPermiso(PerfilPermiso pg) {
        // SQL
        final String SQL = "INSERT INTO perfil_permisos "
                + "(proceso, perfil, info) "
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
                ps.setInt(1, pg.getProceso());
                ps.setInt(2, pg.getPerfil());
                ps.setString(3, pg.getInfo());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPerfilPermiso(int _id) {
        // SQL
        final String SQL = "DELETE FROM perfil_permisos WHERE id=?";

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

    public boolean modificarPerfilPermiso(PerfilPermiso gp) {
        // SQL
        final String SQL = "UPDATE perfil_permisos "
                + "SET proceso=?, perfil=?, info=? "
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
                ps.setInt(1, gp.getProceso());
                ps.setInt(2, gp.getPerfil());
                ps.setString(3, gp.getInfo());
                ps.setInt(4, gp.getId());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarPerfilPermiso(PerfilPermiso pg) {
        // SQL
        final String SQL = "DELETE FROM perfil_permisos WHERE proceso=? AND perfil=?";

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
                ps.setInt(1, pg.getProceso());
                ps.setInt(2, pg.getPerfil());

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }



    public Long contarPerfilPermisos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT COUNT(*) "
                + "FROM "
                + "perfil_permisos "
                + "INNER JOIN "
                + "procesos ON perfil_permisos.proceso = procesos.id "
                + "INNER JOIN "
                + "perfiles ON perfil_permisos.perfil = perfiles.id";

        // Número de Filas
        long filas = 0;

        // WHERE - Usuario > Perfil: ID
        String sqlUser;
        if (pl.getUser() == null) {
            sqlUser = "";
        } else if (pl.getUser().getPerfil() == Perfil.DEV || pl.getUser().getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "perfil_permisos.perfil=" + pl.getUser().getPerfil();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("perfil_permisos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("perfiles.nombre LIKE '%%%s%%' ", pl.getFilterExp());
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

    public List<PerfilPermisoLista> obtenerPerfilPermisos(ParametrosListado pl) {
        // SELECT
        String sqlSelec
                = "SELECT "
                + "perfil_permisos.id AS id, "
                + "procesos.nombre AS proceso, "
                + "perfiles.nombre AS perfil "
                + "FROM "
                + "perfil_permisos "
                + "INNER JOIN "
                + "procesos ON perfil_permisos.proceso = procesos.id "
                + "INNER JOIN "
                + "perfiles ON perfil_permisos.perfil = perfiles.id";

        // WHERE - Usuario: ID
        String sqlUser;
        Usuario user = pl.getUser();
        if (user == null) {
            sqlUser = "";
        } else if (user.getPerfil() == Perfil.DEV || user.getPerfil() == Perfil.ADMIN) {
            sqlUser = "";
        } else {
            sqlUser = "perfil_permisos.perfil=" + user.getPerfil();
        }

        // WHERE - Filtro: campo LIKE %expresion%
        String sqlFiltr;
        if (pl.getFilterFld() == null || pl.getFilterFld().isEmpty()) {
            if (pl.getFilterExp() == null || pl.getFilterExp().isEmpty()) {
                sqlFiltr = "";
            } else {
                sqlFiltr = " "
                        + String.format("perfil_permisos.id LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterExp())
                        + String.format("perfiles.nombre LIKE '%%%s%%' ", pl.getFilterExp());
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
        List<PerfilPermisoLista> lista = new ArrayList<>();

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
                        String perfil = rs.getString("perfil");

                        // Campos > Entidad
                        PerfilPermisoLista pgl = new PerfilPermisoLista(id, proceso, perfil);

                        // Entidad > Lista
                        lista.add(pgl);
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
