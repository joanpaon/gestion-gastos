package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.japo.java.entities.EntityGasto;
import org.japo.java.entities.EntityUsuario;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class GastoDAL extends AbstractDAL {

  public GastoDAL() {
  }

  public GastoDAL(EntityUsuario usuario) {
    super(usuario);
  }

  public List<EntityGasto> obtenerGastos() {
    return obtenerGastos(new ParametrosListado("gestion_gastos", "gastos"));
  }

  public EntityGasto obtenerGasto(int id) {
    // Parámetros de Listado - POR DEFECTO
    ParametrosListado pl = new ParametrosListado("gestion_gastos", "gastos");
    pl.setFilterField("id");
    pl.setFilterValue(id + "");
    pl.setFilterStrict(true);

    // Lista de Usuarios
    List<EntityGasto> gastos = obtenerGastos(pl);

    // Referencia de Entidad
    return gastos.isEmpty() ? null : gastos.get(0);
  }

  public boolean insertarGasto(EntityGasto gasto) {
    // SQL
    final String SQL = generarSQLInsert();

    // Número de registros afectados
    int numReg = 0;

    // Obtención del Contexto
    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(new ParametrosListado("gestion_gastos", "gastos"));
      
      try (
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarInsert(ps, gasto);

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public boolean borrarGasto(int id) {
    // SQL
    final String SQL = "DELETE FROM gastos WHERE id=?";

    // Número de registros afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(new ParametrosListado("gestion_gastos", "gastos"));

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

  public boolean modificarGasto(EntityGasto gasto) {
    // SQL
    final String SQL = generarSQLUpdate();

    // Número de Registros Afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(new ParametrosListado("gestion_gastos", "gastos"));

      try (
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarUpdate(ps, gasto);

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public Long contarGastos(ParametrosListado pl) {
    // Número de Filas
    long filas = 0;

    // SQL
    String sql = generarSQLGastosComputo(pl);

    // Obtención del Contexto
    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(pl);

      try (
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
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

  public List<EntityGasto> obtenerGastos(ParametrosListado pl) {
    // SQL
    String sql = generarSQLGastosListado(pl);

    // Lista Vacía
    List<EntityGasto> gastos = new ArrayList<>();

    // Obtención del Contexto
    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(pl);

      try (
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {
        gastos = exportarListaGastos(ps);
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return gastos;
  }

  private List<EntityGasto> exportarListaGastos(PreparedStatement ps) throws SQLException {
    // Lista 
    List<EntityGasto> lista = new ArrayList<>();

    // BD > Lista de Entidades
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        // Campos > Entidad
        EntityGasto data = exportarGasto(rs);

        // Entidad > Lista
        lista.add(data);
      }
    }

    // Retorno: Lista de Gastos
    return lista;
  }

  private EntityGasto exportarGasto(ResultSet rs) throws SQLException {
    // Registro Actual > Campos
    int id = rs.getInt("id");
    int abonoID = rs.getInt("abono_id");
    String abonoInfo = rs.getString("abono_info");
    double importe = rs.getDouble("importe");
    String info = rs.getString("info");
    int partidaID = rs.getInt("partida_id");
    String partidaInfo = rs.getString("partida_info");
    String recibo = rs.getString("recibo");
    int status = rs.getInt("status");
    String data = rs.getString("data");
    Date createdAt = rs.getDate("created_at");
    Date updatedAt = rs.getDate("updated_at");

    // Retorno: Campos > Entidad
    return new EntityGasto(id, abonoID, abonoInfo, importe,
            info, partidaID, partidaInfo, recibo,
            status, data, createdAt, updatedAt);
  }

  public String generarSQLSelectListado() {
    return ""
            + "SELECT "
            + "gastos.id AS id, "
            + "gastos.abono AS abono_id, "
            + "abonos.info AS abono_info, "
            + "gastos.importe AS importe, "
            + "gastos.info AS info, "
            + "gastos.partida AS partida_id, "
            + "partidas.nombre AS partida_info, "
            + "gastos.recibo AS recibo, "
            + "gastos.status AS status, "
            + "gastos.data AS data, "
            + "gastos.created_at AS created_at, "
            + "gastos.updated_at AS updated_at "
            + "FROM "
            + "gastos "
            + "INNER JOIN "
            + "abonos ON abonos.id = gastos.abono "
            + "INNER JOIN "
            + "partidas ON partidas.id = gastos.partida";
  }

  public String generarSQLSelectComputo() {
    return ""
            + "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "gastos "
            + "INNER JOIN "
            + "abonos ON gastos.abono = abonos.id";
  }

  public String generarSQLInsert() {
    return ""
            + "INSERT INTO "
            + "gastos "
            + "("
            + "abono, importe, info, partida, recibo, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  }

  public String generarSQLUpdate() {
    return ""
            + "UPDATE "
            + "gastos "
            + "SET "
            + "abono=?, importe=?, info=?, partida=?, recibo=?, "
            + "status=?, data=?, created_at=?, updated_at=? "
            + "WHERE id=?";
  }

  private String generarSQLGastosListado(ParametrosListado pl) {
    // SQL Parciales
    String select = generarSQLSelectListado();
    String where = generarSQLWhere(pl);
    String order = generarSQLOrder(pl);
    String limit = generarSQLLimit(pl);

    // SQL Completo: SELECT + WHERE + ORDER + LIMIT
    return String.format("%s%s%s%s", select, where, order, limit);
  }

  protected String generarSQLGastosComputo(ParametrosListado pl) {
    // SQL Parciales
    String select = generarSQLSelectComputo();
    String where = generarSQLWhere(pl);

    // SQL Completo: SELECT + WHERE + ORDER + LIMIT
    return String.format("%s%s", select, where);
  }

  private void parametrizarInsert(PreparedStatement ps, EntityGasto gasto)
          throws SQLException {
    ps.setInt(1, gasto.getAbonoID());
    ps.setDouble(2, gasto.getImporte());
    ps.setString(3, gasto.getInfo());
    ps.setInt(4, gasto.getPartidaID());
    ps.setString(5, gasto.getRecibo());
    ps.setInt(6, gasto.getStatus());
    ps.setString(7, gasto.getData());
    ps.setDate(8, new java.sql.Date(gasto.getCreatedAt().getTime()));
    ps.setDate(9, new java.sql.Date(gasto.getUpdatedAt().getTime()));
  }

  private void parametrizarUpdate(PreparedStatement ps, EntityGasto gasto) 
          throws SQLException {
    ps.setInt(1, gasto.getAbonoID());
    ps.setDouble(2, gasto.getImporte());
    ps.setString(3, gasto.getInfo());
    ps.setInt(4, gasto.getPartidaID());
    ps.setString(5, gasto.getRecibo());
    ps.setInt(6, gasto.getStatus());
    ps.setString(7, gasto.getData());
    ps.setDate(8, new java.sql.Date(gasto.getCreatedAt().getTime()));
    ps.setDate(9, new java.sql.Date(gasto.getUpdatedAt().getTime()));
    ps.setInt(10, gasto.getId());
  }
}
