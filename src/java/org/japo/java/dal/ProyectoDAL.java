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
import org.japo.java.entities.EntityProyecto;
import org.japo.java.entities.EntityUsuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProyectoDAL {

  public List<EntityProyecto> obtenerProyectos() {
    return obtenerProyectos(new ParametrosListado());
  }

  public EntityProyecto obtenerProyecto(int id) {
    // Parámetros de Listado
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Proyectos
    List<EntityProyecto> proyectos = obtenerProyectos(pl);

    // Referencia de Entidad
    return proyectos.isEmpty() ? null : proyectos.get(0);
  }

  public boolean insertarProyecto(EntityProyecto proyecto) {
    // SQL
    final String SQL
            = "INSERT INTO "
            + "proyectos "
            + "("
            + "nombre, info, icono, propietario, cuota, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        ps.setString(1, proyecto.getNombre());
        ps.setString(2, proyecto.getInfo());
        ps.setString(3, proyecto.getIcono());
        ps.setInt(4, proyecto.getPropietarioID());
        ps.setInt(5, proyecto.getCuotaID());
        ps.setInt(6, proyecto.getStatus());
        ps.setString(7, proyecto.getData());
        ps.setDate(8, new java.sql.Date(proyecto.getCreatedAt().getTime()));
        ps.setDate(9, new java.sql.Date(proyecto.getUpdatedAt().getTime()));

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public boolean borrarProyecto(int id) {
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

  public boolean modificarProyecto(EntityProyecto proyecto) {
    // SQL
    final String SQL
            = "UPDATE "
            + "proyectos "
            + "SET "
            + "nombre=?, info=?, icono=?, propietario=?, cuota=? "
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
        ps.setString(1, proyecto.getNombre());
        ps.setString(2, proyecto.getInfo());
        ps.setString(3, proyecto.getIcono());
        ps.setInt(4, proyecto.getPropietarioID());
        ps.setInt(5, proyecto.getCuotaID());
        ps.setInt(6, proyecto.getStatus());
        ps.setString(7, proyecto.getData());
        ps.setDate(8, new java.sql.Date(proyecto.getCreatedAt().getTime()));
        ps.setDate(9, new java.sql.Date(proyecto.getUpdatedAt().getTime()));
        ps.setInt(10, proyecto.getId());

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
    String sqlSelect
            = "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "proyectos "
            + "INNER JOIN "
            + "usuarios ON usuarios.id = proyectos.propietario "
            + "INNER JOIN "
            + "cuotas ON cuotas.id = proyectos.cuota";

    // Número de Filas
    long filas = 0;

    // WHERE - USER
    String sqlUser;
    EntityUsuario usuario = pl.getUser();
    if (usuario == null) {
      sqlUser = "";
    } else {
      sqlUser
              = "proyectos.id IN ("
              + "SELECT "
              + "abonos.proyecto "
              + "FROM "
              + "abonos "
              + "WHERE "
              + "abonos.usuario = " + usuario.getId()
              + ")";
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("proyectos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("cuotas.nombre LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("proyectos.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("proyectos.%s LIKE '%%%s%%'",
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

  public List<EntityProyecto> obtenerProyectos(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
            + "proyectos.id AS id, "
            + "proyectos.nombre AS nombre, "
            + "proyectos.info AS info, "
            + "proyectos.icono AS icono, "
            + "proyectos.propietario AS propietario_id, "
            + "usuarios.user AS propietario_info, "
            + "proyectos.cuota AS cuota_id, "
            + "cuotas.nombre AS cuota_info, "
            + "proyectos.status AS status, "
            + "proyectos.data AS data, "
            + "proyectos.created_at AS created_at, "
            + "proyectos.updated_at AS updated_at "
            + "FROM "
            + "proyectos "
            + "INNER JOIN "
            + "usuarios ON usuarios.id = proyectos.propietario "
            + "INNER JOIN "
            + "cuotas ON cuotas.id = proyectos.cuota";

    // WHERE - USER
    String sqlUser;
    if (pl.getUser() == null) {
      sqlUser = "";
    } else {
      sqlUser
              = "proyectos.id IN ("
              + "SELECT "
              + "abonos.proyecto "
              + "FROM "
              + "abonos "
              + "WHERE "
              + "abonos.usuario = " + pl.getUser().getId()
              + ")";
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("proyectos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("cuotas.nombre LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("proyectos.%s = '%s'", pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("proyectos.%s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
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
    List<EntityProyecto> proyectos = new ArrayList<>();

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
            int propietarioID = rs.getInt("propietario_id");
            String propietarioInfo = rs.getString("propietario_info");
            int cuotaID = rs.getInt("cuota_id");
            String cuotaInfo = rs.getString("cuota_info");
            int status = rs.getInt("status");
            String data = rs.getString("data");
            Date createdAt = rs.getDate("created_at");
            Date updatedAt = rs.getDate("updated_at");

            // Campos > Entidad
            EntityProyecto proyecto = new EntityProyecto(id, nombre, info,
                    icono, propietarioID, propietarioInfo, cuotaID, cuotaInfo,
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            proyectos.add(proyecto);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return proyectos;
  }
}
