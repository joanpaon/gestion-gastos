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
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PerfilDAL {

  public List<EntityPerfil> obtenerPerfiles() {
    return obtenerPerfiles(new ParametrosListado());
  }

  public EntityPerfil obtenerPerfil(int id) {
    // Parámetros de Listado
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Proyectos
    List<EntityPerfil> perfiles = obtenerPerfiles(pl);

    // Referencia de Entidad
    return perfiles.isEmpty() ? null : perfiles.get(0);
  }

  public boolean insertarPerfil(EntityPerfil perfil) {
    // SQL
    final String SQL
            = "INSERT INTO "
            + "perfiles "
            + "("
            + "nombre, info, icono"
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
        ps.setString(1, perfil.getNombre());
        ps.setString(2, perfil.getInfo());
        ps.setString(3, perfil.getIcono());
        ps.setInt(6, perfil.getStatus());
        ps.setString(7, perfil.getData());
        ps.setDate(8, new java.sql.Date(perfil.getCreatedAt().getTime()));
        ps.setDate(9, new java.sql.Date(perfil.getUpdatedAt().getTime()));

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

  public boolean modificarPerfil(EntityPerfil perfil) {
    // SQL
    final String SQL
            = "UPDATE "
            + "perfiles "
            + "SET "
            + "nombre=?, info=?, icono=? "
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
        ps.setString(1, perfil.getNombre());
        ps.setString(2, perfil.getInfo());
        ps.setString(3, perfil.getIcono());
        ps.setInt(4, perfil.getStatus());
        ps.setString(5, perfil.getData());
        ps.setDate(6, new java.sql.Date(perfil.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(perfil.getUpdatedAt().getTime()));
        ps.setInt(8, perfil.getId());

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
    String sqlSelect
            = "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "perfiles";

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
      sqlUser = "perfiles.id != " + EntityPerfil.DEVEL;
    } else {
      sqlUser = "perfiles.id = " + EntityPerfil.BASIC;
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("info LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("perfiles.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("perfiles.%s LIKE '%%%s%%'",
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

  public List<EntityPerfil> obtenerPerfiles(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
            + "* "
            + "FROM "
            + "perfiles";

    // WHERE - USER
    String sqlUser;
    EntityUsuario usuario = pl.getUser();
    if (usuario == null) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.DEVEL) {
      sqlUser = "";
    } else if (usuario.getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "perfiles.id != " + EntityPerfil.DEVEL;
    } else {
      sqlUser = "perfiles.id = " + EntityPerfil.BASIC;
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("info LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("perfiles.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("perfiles.%s LIKE '%%%s%%'",
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

    // SQL Completo: SELECT + WHERE + ORDER + LIMIT
    String sql = String.format("%s%s%s%s", sqlSelect, sqlWhere, sqlOrder, sqlLimit);

    // Lista Vacía
    List<EntityPerfil> perfiles = new ArrayList<>();

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
            EntityPerfil perfil = new EntityPerfil(id, nombre, info, icono,
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            perfiles.add(perfil);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return perfiles;
  }
}
