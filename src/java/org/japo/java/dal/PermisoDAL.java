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
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.EntityPermiso;
import org.japo.java.entities.EntityUsuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PermisoDAL {

  public List<EntityPermiso> obtenerPermisos() {
    return obtenerPermisos(new ParametrosListado());
  }

  public EntityPermiso obtenerPermiso(int id) {
    // Parámetros de Listado - POR DEFECTO
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Permisos
    List<EntityPermiso> permisos = obtenerPermisos(pl);

    // Referencia de Entidad
    return permisos.isEmpty() ? null : permisos.get(0);
  }

  public List<EntityPermiso> obtenerPermisos(int perfil) {
    // Parámetros de Listado
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("perfil");
    pl.setFilterValue(perfil + "");
    pl.setFilterStrict(true);

    // Retorno: Lista de Permisos
    return obtenerPermisos(pl);
  }

  public boolean insertarPermiso(EntityPermiso permiso) {
    // SQL
    final String SQL
            = "INSERT INTO "
            + "permisos "
            + "("
            + "proceso, perfil, info, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
        ps.setInt(1, permiso.getProcesoId());
        ps.setInt(2, permiso.getPerfilId());
        ps.setString(3, permiso.getInfo());
        ps.setInt(4, permiso.getStatus());
        ps.setString(5, permiso.getData());
        ps.setDate(6, new java.sql.Date(permiso.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(permiso.getUpdatedAt().getTime()));

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
    final String SQL = "DELETE FROM permisos WHERE id=?";

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

  public boolean borrarPermiso(EntityPermiso permiso) {
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
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(SQL)) {
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

  public boolean modificarPermiso(EntityPermiso permiso) {
    // SQL
    final String SQL
            = "UPDATE "
            + "permisos "
            + "SET "
            + "proceso=?, perfil=?, info=?, "
            + "status=?, data=?, created_at=?, updated_at=?"
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
        ps.setInt(1, permiso.getProcesoId());
        ps.setInt(2, permiso.getPerfilId());
        ps.setString(3, permiso.getInfo());
        ps.setInt(4, permiso.getStatus());
        ps.setString(5, permiso.getData());
        ps.setDate(6, new java.sql.Date(permiso.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(permiso.getUpdatedAt().getTime()));
        ps.setInt(8, permiso.getId());

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
    // SELECT
    String sqlSelect
            = "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "permisos "
            + "INNER JOIN "
            + "procesos ON procesos.id = permisos.proceso "
            + "INNER JOIN "
            + "perfiles ON perfiles.id = permisos.perfil";

    // Número de Filas
    long filas = 0;

    // WHERE - USER
    String sqlUser;
    EntityUsuario usuario = pl.getUser();
    if (usuario == null) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.DEVEL) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "permisos.perfil != " + EntityPerfil.DEVEL;
    } else {
      sqlUser = "permisos.perfil = " + usuario.getPerfilID();
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("permisos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("perfiles.nombre LIKE '%%%s%%' ", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("permisos.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("permisos.%s LIKE '%%%s%%'",
                pl.getFilterField(), pl.getFilterValue());
      }
    }

    // WHERE - USER + FILTER
    String sqlWhere;
    if (sqlUser.isBlank()) {
      if (sqlFilter.isBlank()) {
        sqlWhere = "";
      } else {
        sqlWhere = String.format(" WHERE %s", sqlFilter);
      }
    } else {
      if (sqlFilter.isBlank()) {
        sqlWhere = String.format(" WHERE %s", sqlUser);
      } else {
        sqlWhere = String.format(" WHERE %s AND %s", sqlUser, sqlFilter);
      }
    }

    // SQL Completo: SELECT + WHERE
    String sql = String.format("%s%s", sqlSelect, sqlWhere);

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

  public List<EntityPermiso> obtenerPermisos(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
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

    // WHERE - USER
    String sqlUser;
    EntityUsuario usuario = pl.getUser();
    if (usuario == null) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.DEVEL) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "permisos.perfil != " + EntityPerfil.DEVEL;
    } else {
      sqlUser = "permisos.perfil = " + usuario.getPerfilID();
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("permisos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("procesos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("perfiles.nombre LIKE '%%%s%%' ", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("permisos.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("permisos.%s LIKE '%%%s%%'",
                pl.getFilterField(), pl.getFilterValue());
      }
    }

    // WHERE - USER + FILTER
    String sqlWhere;
    if (sqlUser.isBlank()) {
      if (sqlFilter.isBlank()) {
        sqlWhere = "";
      } else {
        sqlWhere = String.format(" WHERE %s", sqlFilter);
      }
    } else {
      if (sqlFilter.isBlank()) {
        sqlWhere = String.format(" WHERE %s", sqlUser);
      } else {
        sqlWhere = String.format(" WHERE %s AND %s", sqlUser, sqlFilter);
      }
    }

    // ORDER BY
    String sqlOrder;
    if (pl.getOrderProgress() == null || pl.getOrderProgress().isEmpty()) {
      sqlOrder = "";
    } else if (pl.getOrderField() == null || pl.getOrderField().isEmpty()) {
      sqlOrder = "";
    } else if (pl.getOrderProgress().equalsIgnoreCase("asc") || pl.getOrderProgress().equalsIgnoreCase("desc")) {
      sqlOrder = String.format(" ORDER BY %s %s", pl.getOrderField(), pl.getOrderProgress());
    } else {
      sqlOrder = "";
    }

    // LIMIT A,B
    String sqlLimit;
    if (pl.getRowIndex() == null || pl.getRowsPage() == null) {
      sqlLimit = "";
    } else {
      sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());
    }

    // SELECT + WHERE + ORDER + LIMIT
    String sql = String.format("%s%s%s%s", sqlSelect, sqlWhere, sqlOrder, sqlLimit);

    // Lista Vacía
    List<EntityPermiso> permisos = new ArrayList<>();

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
            EntityPermiso permiso = new EntityPermiso(id, 
                    procesoID, procesoInfo, perfilID, perfilInfo, info, 
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            permisos.add(permiso);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return permisos;
  }
}
