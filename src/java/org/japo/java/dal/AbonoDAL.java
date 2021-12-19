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
import org.japo.java.entities.EntityAbono;
import org.japo.java.entities.EntityPerfil;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.EntityUsuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AbonoDAL {

  public List<EntityAbono> obtenerAbonos() {
    // SQL
    final String SQL = "SELECT * FROM abonos";

    // Lista Vacía
    List<EntityAbono> abonos = new ArrayList<>();

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
            EntityAbono abono = new EntityAbono(id, proyecto, usuario, info,
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            abonos.add(abono);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return abonos;
  }

  public boolean insertarAbono(EntityAbono abono) {
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
        ps.setInt(1, abono.getProyectoID());
        ps.setInt(2, abono.getUsuarioID());
        ps.setString(3, abono.getInfo());

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public EntityAbono obtenerAbono(int id) {
    // SQL
    final String SQL
            = "SELECT "
            + "abonos.id AS id, "
            + "abonos.proyecto AS proyecto_id, proyectos.nombre AS proyecto_info, "
            + "abonos.usuario AS usuario_id, usuarios.user AS usuario_info, "
            + "abonos.info AS info, "
            + "abonos.status AS status, abonos.data AS data, "
            + "abonos.created_at AS created_at, abonos.updated_at AS updated_at "
            + "FROM abonos "
            + "INNER JOIN proyectos "
            + "ON proyectos.id = abonos.proyecto "
            + "INNER JOIN usuarios "
            + "ON usuarios.id = abonos.usuario "
            + "WHERE abonos.id=?";

    // Referencia de Entidad
    EntityAbono abono = null;

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
        ps.setInt(1, id);

        // BD > Entidad
        try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
            // Registro Actual > Datos Entidad
            int proyectoID = rs.getInt("proyecto_id");
            String proyectoInfo = rs.getString("proyecto_info");
            int usuarioID = rs.getInt("usuario_id");
            String usuarioInfo = rs.getString("usuario_info");
            String info = rs.getString("info");
            int status = rs.getInt("status");
            String data = rs.getString("data");
            Date createdAt = rs.getDate("created_at");
            Date updatedAt = rs.getDate("updated_at");

            // Datos Entidad > Instancia Entidad
            abono = new EntityAbono(id,
                    proyectoID, proyectoInfo,
                    usuarioID, usuarioInfo, info,
                    status, data, createdAt, updatedAt);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Entidad
    return abono;
  }

  public boolean borrarAbono(int id) {
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

  public boolean modificarAbono(EntityAbono abono) {
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
        ps.setInt(1, abono.getProyectoID());
        ps.setInt(2, abono.getUsuarioID());
        ps.setString(3, abono.getInfo());
        ps.setInt(4, abono.getId());

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public List<EntityAbono> obtenerAbonos(int usuarioID) {
    // SQL
    final String SQL
            = "SELECT "
            + "abonos.id AS id, "
            + "abonos.proyecto AS proyecto_id, proyectos.nombre AS proyecto_info, "
            + "abonos.usuario AS usuario_id, usuarios.user AS usuario_info, "
            + "abonos.info AS info, "
            + "abonos.status AS status, abonos.data AS data, "
            + "abonos.created_at AS created_at, abonos.updated_at AS updated_at "
            + "FROM abonos "
            + "INNER JOIN proyectos "
            + "ON proyectos.id = abonos.proyecto "
            + "INNER JOIN usuarios "
            + "ON usuarios.id = abonos.usuario "
            + "WHERE abonos.usuario=?";
    // Lista Vacía
    List<EntityAbono> abonos = new ArrayList<>();

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
        ps.setInt(1, usuarioID);

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            // Fila Actual > Campos 
            // Registro Actual > Datos Entidad
            int id = rs.getInt("id");
            int proyectoID = rs.getInt("proyecto_id");
            String proyectoInfo = rs.getString("proyecto_info");
            String usuarioInfo = rs.getString("usuario_info");
            String info = rs.getString("info");
            int status = rs.getInt("status");
            String data = rs.getString("data");
            Date createdAt = rs.getDate("created_at");
            Date updatedAt = rs.getDate("updated_at");


            // Campos > Entidad
            EntityAbono abono = new EntityAbono(id, proyectoID, proyectoInfo, 
                    usuarioID, usuarioInfo, info, 
                    status, data, createdAt, updatedAt);

            // Entidad > Lista
            abonos.add(abono);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return abonos;
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

    // WHERE - EntityUsuario: ID
    String sqlUser;
    if (pl.getUser() == null) {
      sqlUser = "";
    } else if (pl.getUser().getPerfilID() == EntityPerfil.DEVEL || pl.getUser().getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "";
    } else {
      sqlUser = "abonos.usuario=" + pl.getUser().getId();
    }

    // WHERE - Filtro: campo LIKE %expresion%
    String sqlFiltr;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFiltr = "";
      } else {
        sqlFiltr = " "
                + String.format("abonos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFiltr = "";
      } else {
        sqlFiltr = String.format(" %s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
      }
    }

    // WHERE - Completo: EntityUsuario + Filtro
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

  public List<EntityAbono> obtenerAbonos(ParametrosListado pl) {

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

    // WHERE - EntityUsuario: ID
    String sqlUser;
    EntityUsuario user = pl.getUser();
    if (user == null) {
      sqlUser = "";
    } else if (user.getPerfilID() == EntityPerfil.DEVEL || user.getPerfilID() == EntityPerfil.ADMIN) {
      sqlUser = "";
    } else {
      sqlUser = "abonos.usuario=" + user.getId();
    }

    // WHERE - Filtro: campo LIKE %expresion%
    String sqlFiltr;
    if (pl.getFilterField() == null || pl.getFilterField().isEmpty()) {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFiltr = "";
      } else {
        sqlFiltr = " "
                + String.format("abonos.id LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("proyectos.nombre LIKE '%%%s%%' OR ", pl.getFilterValue())
                + String.format("usuarios.user LIKE '%%%s%%'", pl.getFilterValue());
      }
    } else {
      if (pl.getFilterValue() == null || pl.getFilterValue().isEmpty()) {
        sqlFiltr = "";
      } else {
        sqlFiltr = String.format(" %s LIKE '%%%s%%'", pl.getFilterField(), pl.getFilterValue());
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
    // WHERE - Completo: EntityUsuario + Filtro
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
    if (pl.getOrderProgress() == null || pl.getOrderProgress().isEmpty()) {
      sqlSort = "";
    } else if (pl.getOrderField() == null || pl.getOrderField().isEmpty()) {
      sqlSort = "";
    } else if (pl.getOrderProgress().equalsIgnoreCase("asc") || pl.getOrderProgress().equalsIgnoreCase("desc")) {
      sqlSort = String.format(" ORDER BY %s %s", pl.getOrderField(), pl.getOrderProgress());
    } else {
      sqlSort = "";
    }

    // LIMIT %INDICE_TABLA%,%FILAS_PAGINA%
    // LIMIT A,B
    String sqlLimit = String.format(" LIMIT %d,%d", pl.getRowIndex(), pl.getRowsPage());

    // SQL Completo: SELECT + WHERE + ORDER + LIMIT
    String sql = String.format("%s%s%s%s", sqlSelec, sqlWhere, sqlSort, sqlLimit);

    // Lista Vacía
    List<EntityAbono> abonos = new ArrayList<>();

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
            EntityAbono abono = new EntityAbono(id, proyecto, usuario);

            // Entidad > Lista
            abonos.add(abono);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return abonos;
  }
}
