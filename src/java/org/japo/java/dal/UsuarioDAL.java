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
import org.japo.java.entities.EntityUsuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioDAL {

  public List<EntityUsuario> obtenerUsuarios() {
    return obtenerUsuarios(new ParametrosListado());
  }

  public EntityUsuario obtenerUsuario(int id) {
    // Parámetros de Listado - POR DEFECTO
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Usuarios
    List<EntityUsuario> usuarios = obtenerUsuarios(pl);

    // Referencia de Entidad
    return usuarios.isEmpty() ? null : usuarios.get(0);
  }

  public boolean insertarUsuario(EntityUsuario usuario) {
    // SQL
    final String SQL
            = "INSERT INTO "
            + "usuarios "
            + "("
            + "user, pass, email, icono, perfil, info, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        ps.setString(1, usuario.getUser());
        ps.setString(2, usuario.getPass());
        ps.setString(3, usuario.getEmail());
        ps.setString(4, usuario.getIcono());
        ps.setInt(5, usuario.getPerfilID());
        ps.setString(6, usuario.getInfo());
        ps.setInt(7, usuario.getStatus());
        ps.setString(8, usuario.getData());
        ps.setDate(9, new java.sql.Date(usuario.getCreatedAt().getTime()));
        ps.setDate(10, new java.sql.Date(usuario.getUpdatedAt().getTime()));

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public boolean borrarUsuario(int id) {
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

  public boolean modificarUsuario(EntityUsuario usuario) {
    // SQL
    final String SQL
            = "UPDATE "
            + "usuarios "
            + "SET "
            + "user=?, pass=?, email=?, icono=?, perfil=?, info=?, "
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
        ps.setString(1, usuario.getUser());
        ps.setString(2, usuario.getPass());
        ps.setString(3, usuario.getEmail());
        ps.setString(4, usuario.getIcono());
        ps.setInt(5, usuario.getPerfilID());
        ps.setString(6, usuario.getInfo());
        ps.setInt(7, usuario.getStatus());
        ps.setString(8, usuario.getData());
        ps.setDate(9, new java.sql.Date(usuario.getCreatedAt().getTime()));
        ps.setDate(10, new java.sql.Date(usuario.getUpdatedAt().getTime()));
        ps.setInt(11, usuario.getId());

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public EntityUsuario obtenerUsuario(String user) {
    // Parámetros de Listado - POR DEFECTO
    ParametrosListado pl = new ParametrosListado();
    pl.setFilterField("user");
    pl.setFilterValue(user);
    pl.setFilterStrict(true);

    // Lista de Usuarios
    List<EntityUsuario> usuarios = obtenerUsuarios(pl);

    // Referencia de Entidad
    return usuarios.isEmpty() ? null : usuarios.get(0);
  }

  public Long contarUsuarios(ParametrosListado pl) {
    // SELECT
    String sqlSelect
            = "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "usuarios "
            + "INNER JOIN "
            + "perfiles ON perfiles.id = usuarios.perfil";

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
                = String.format("usuarios.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("perfiles.nombre LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("usuarios.%s = '%s'",
                pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("usuarios.%s LIKE '%%%s%%'",
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

  public List<EntityUsuario> obtenerUsuarios(ParametrosListado pl) {
    String sqlSelec
            = "SELECT "
            + "usuarios.id AS id, "
            + "usuarios.user AS user, "
            + "usuarios.pass AS pass, "
            + "usuarios.email AS email, "
            + "usuarios.icono AS icono, "
            + "usuarios.perfil AS perfil_id, "
            + "perfiles.nombre AS perfil_info, "
            + "usuarios.info AS info, "
            + "usuarios.status AS status, "
            + "usuarios.data AS data, "
            + "usuarios.created_at AS created_at, "
            + "usuarios.updated_at AS updated_at "
            + "FROM "
            + "usuarios "
            + "INNER JOIN "
            + "perfiles ON perfiles.id = usuarios.perfil";

    // WHERE - USER
    String sqlUser;
    EntityUsuario u = pl.getUser();
    if (u == null) {
      sqlUser = "";
    } else if (u.getPerfilID() == EntityPerfil.DEVEL) {
      sqlUser = "";
    } else if (u.getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "usuarios.perfil != " + EntityPerfil.DEVEL;
    } else {
      sqlUser = "usuarios.id = " + u.getPerfilID();
    }

    // WHERE - FILTER
    String sqlFilter;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else {
        sqlFilter
                = String.format("usuarios.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("perfiles.nombre LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFilter = "";
      } else if (pl.isFilterStrict()) {
        sqlFilter = String.format("usuarios.%s = '%s'", pl.getFilterField(), pl.getFilterValue());
      } else {
        sqlFilter = String.format("usuarios.%s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
      }
    }

    // WHERE - USER + FILTER
    String sqlWhere;
    if (sqlUser.isEmpty()) {
      if (sqlFilter.isEmpty()) {
        sqlWhere = "";
      } else {
        sqlWhere = String.format(" WHERE %s", sqlFilter);
      }
    } else {
      if (sqlFilter.isEmpty()) {
        sqlWhere = String.format(" WHERE %s", sqlUser);
      } else {
        sqlWhere = String.format(" WHERE %s AND (%s)", sqlUser, sqlFilter);
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
    String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlOrder, sqlLimit);

    // Lista Vacía
    List<EntityUsuario> usuarios = new ArrayList<>();

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
            String user = rs.getString("user");
            String pass = rs.getString("pass");
            String email = rs.getString("email");
            String icono = rs.getString("icono");
            int perfilId = rs.getInt("perfil_id");
            String perfilInfo = rs.getString("perfil_info");
            String info = rs.getString("info");
            int status = rs.getInt("status");
            String data = rs.getString("data");
            Date createdAt = rs.getDate("created_at");
            Date updatedAt = rs.getDate("updated_at");

            // Campos > Entidad
            EntityUsuario usuario = new EntityUsuario(id, user, pass,
                    email, icono, perfilId, perfilInfo, info,
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            usuarios.add(usuario);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return usuarios;
  }
}
