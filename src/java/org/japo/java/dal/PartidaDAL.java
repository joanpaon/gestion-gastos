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
import org.japo.java.entities.EntityPartida;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PartidaDAL {

  public List<EntityPartida> obtenerPartidas() {
    return obtenerPartidas(new ParametrosListado());
  }

  public EntityPartida obtenerPartida(int id) {
    // Parámetros de Listado
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Partidas
    List<EntityPartida> partidas = obtenerPartidas(pl);

    // Referencia de Entidad
    return partidas.isEmpty() ? null : partidas.get(0);
  }

  public boolean insertarPartida(EntityPartida partida) {
    // SQL
    final String SQL
            = "INSERT INTO "
            + "partidas "
            + "("
            + "nombre, info, icono,"
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
        ps.setString(1, partida.getNombre());
        ps.setString(2, partida.getInfo());
        ps.setString(3, partida.getIcono());
        ps.setInt(4, partida.getStatus());
        ps.setString(5, partida.getData());
        ps.setDate(6, new java.sql.Date(partida.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(partida.getUpdatedAt().getTime()));

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public boolean borrarPartida(int id) {
    // SQL
    final String SQL = "DELETE FROM partidas WHERE id=?";

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

  public boolean modificarPartida(EntityPartida partida) {
    // SQL
    final String SQL
            = "UPDATE "
            + "partidas "
            + "SET "
            + "nombre=?, info=?, icono=?, "
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
        ps.setString(1, partida.getNombre());
        ps.setString(2, partida.getInfo());
        ps.setString(3, partida.getIcono());
        ps.setInt(4, partida.getStatus());
        ps.setString(5, partida.getData());
        ps.setDate(6, new java.sql.Date(partida.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(partida.getUpdatedAt().getTime()));
        ps.setInt(8, partida.getId());

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public Long contarPartidas(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "partidas "
            + "INNER JOIN "
            + "proyectos ON proyectos.id = partidas.iproyecto";

    // Número de Filas
    long filas = 0;

    // WHERE - USER
    String sqlUser = "";

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
        sqlFilter = String.format("%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("%s LIKE '%%%s%%'",
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

  public List<EntityPartida> obtenerPartidas(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
            + "partidas.id AS id, "
            + "partidas.nombre AS nombre, "
            + "partidas.info AS info, "
            + "partidas.icono AS icono, "
            + "partidas.proyecto AS proyecto_id, "
            + "proyectos.nombre AS proyecto_info, "
            + "partidas.status AS status, "
            + "partidas.data AS data, "
            + "partidas.created_at AS created_at, "
            + "partidas.updated_at AS updated_at "
            + "FROM "
            + "partidas "
            + "INNER JOIN "
            + "proyectos ON proyectos.id = partidas.proyecto";

    // WHERE - USER
    String sqlUser = "";

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
        sqlFilter = String.format("partidas.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("partidas.%s LIKE '%%%s%%'",
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
    List<EntityPartida> partidas = new ArrayList<>();

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
            int proyectoID = rs.getInt("proyecto_id");
            String proyectoInfo = rs.getString("proyecto_info");
            int status = rs.getInt("status");
            String data = rs.getString("data");
            Date createdAt = rs.getDate("created_at");
            Date updatedAt = rs.getDate("updated_at");

            // Campos > Entidad
            EntityPartida partida = new EntityPartida(id, nombre, info, icono,
                    proyectoID, proyectoInfo,
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            partidas.add(partida);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return partidas;
  }
}
