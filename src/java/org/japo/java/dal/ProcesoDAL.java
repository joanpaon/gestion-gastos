package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Proceso;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProcesoDAL extends AbstractDAL {

  // Constantes
  private final String TABLA = "procesos";

  // Parámetros de Listado
  private final ParametrosListado PL;

  // Campos
  private final HttpSession sesion;

  public ProcesoDAL(HttpSession sesion) {
    this.sesion = sesion;

    // Sesión > Usuario
    Usuario usuario = (Usuario) sesion.getAttribute("usuario");

    // BD + TABLA + usuario > Parámetros de Listado
    PL = new ParametrosListado(BD, TABLA, usuario);
  }

  public List<Proceso> obtenerProcesos() {
    return obtenerProcesos(PL);
  }

  public Proceso obtenerProceso(int id) {
    // Parámetros de Listado
    PL.setFilterFields(Arrays.asList("id"));
    PL.setFilterValue(id + "");
    PL.setFilterStrict(true);

    // Lista de Proyectos
    List<Proceso> procesos = obtenerProcesos(PL);

    // Referencia de Entidad
    return procesos.isEmpty() ? null : procesos.get(0);
  }

  public Proceso obtenerProceso(String nombre) {
    // Parámetros de Listado
    PL.setFilterFields(Arrays.asList("nombre"));
    PL.setFilterValue(nombre);
    PL.setFilterStrict(true);

    // Lista de Proyectos
    List<Proceso> procesos = obtenerProcesos(PL);

    // Referencia de Entidad
    return procesos.isEmpty() ? null : procesos.get(0);
  }

  public boolean insertarProceso(Proceso proceso) {
    // SQL
    final String SQL = generarSQLInsert();

    // Número de registros afectados
    int numReg = 0;

    // Obtención del Contexto
    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
               Connection conn = ds.getConnection();  PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarInsert(ps, proceso);

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public boolean borrarProceso(int id) {
    // SQL
    final String SQL = generarSQLDelete();

    // Número de registros afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
               Connection conn = ds.getConnection();  PreparedStatement ps = conn.prepareStatement(SQL)) {
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

  public boolean modificarProceso(Proceso proceso) {
    // SQL
    final String SQL = generarSQLUpdate();

    // Número de Registros Afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
               Connection conn = ds.getConnection();  PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarUpdate(ps, proceso);

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public Long contarProcesos(ParametrosListado pl) {
    // Número de Filas
    long filas = 0;

    // SQL
    String sql = generarSQLComputo(pl);

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
               Connection conn = ds.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
        try ( ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
            filas = rs.getLong(1);
          }
        }
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: Filas Contadas
    return filas;
  }

  public List<Proceso> obtenerProcesos(ParametrosListado pl) {
    // SQL
    String sql = generarSQLListado(pl);

    // Lista Vacía
    List<Proceso> procesos = new ArrayList<>();

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
               Connection conn = ds.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
        procesos = exportarListaProcesos(ps);
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return procesos;
  }

  private List<Proceso> exportarListaProcesos(PreparedStatement ps) throws SQLException {
    // Lista 
    List<Proceso> procesos = new ArrayList<>();

    // BD > Lista de Entidades
    try ( ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        // Campos > Entidad
        Proceso proceso = exportarProceso(rs);

        // Entidad > Lista
        procesos.add(proceso);
      }
    }

    // Retorno: Lista de Procesos
    return procesos;
  }

  private Proceso exportarProceso(ResultSet rs) throws SQLException {
    // Fila Actual > Campos 
    int id = rs.getInt("id");
    String nombre = rs.getString("nombre");
    String info = rs.getString("info");
    int status = rs.getInt("status");
    String data = rs.getString("data");
    Date createdAt = rs.getDate("created_at");
    Date updatedAt = rs.getDate("updated_at");

    // Campos > Entidad
    return new Proceso(id, nombre, info,
            status, data, createdAt, updatedAt);
  }

  public String generarSQLSelect() {
    return ""
            + "SELECT "
            + "* "
            + "FROM procesos";
  }

  public String generarSQLSelectComputo() {
    return ""
            + "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "procesos";
  }

  public String generarSQLInsert() {
    return ""
            + "INSERT INTO "
            + "procesos "
            + "("
            + "nombre, info, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?)";
  }

  public String generarSQLUpdate() {
    return ""
            + "UPDATE "
            + "procesos "
            + "SET "
            + "nombre=?, info=? "
            + "status=?, data=?, created_at=?, updated_at=?"
            + "WHERE id=?";
  }

  public String generarSQLDelete() {
    return ""
            + "DELETE FROM "
            + "procesos "
            + "WHERE id=?";
  }

  private String generarSQLListado(ParametrosListado pl) {
    // SQL Parciales
    String select = generarSQLSelect();
    String where = generarSQLWhere(pl);
    String order = generarSQLOrder(pl);
    String limit = generarSQLLimit(pl);

    // SQL Completo: SELECT + WHERE + ORDER + LIMIT
    return String.format("%s%s%s%s", select, where, order, limit);
  }

  protected String generarSQLComputo(ParametrosListado pl) {
    // SQL Parciales
    String select = generarSQLSelectComputo();
    String where = generarSQLWhere(pl);

    // SQL Completo: SELECT + WHERE
    return String.format("%s%s", select, where);
  }

  private void parametrizarInsert(PreparedStatement ps, Proceso proceso) throws SQLException {
    ps.setString(1, proceso.getNombre());
    ps.setString(2, proceso.getInfo());
    ps.setInt(3, proceso.getStatus());
    ps.setString(4, proceso.getData());
    ps.setDate(5, new java.sql.Date(proceso.getCreatedAt().getTime()));
    ps.setDate(6, new java.sql.Date(proceso.getUpdatedAt().getTime()));
  }

  private void parametrizarUpdate(PreparedStatement ps, Proceso proceso) throws SQLException {
    ps.setString(1, proceso.getNombre());
    ps.setString(2, proceso.getInfo());
    ps.setInt(3, proceso.getStatus());
    ps.setString(4, proceso.getData());
    ps.setDate(5, new java.sql.Date(proceso.getCreatedAt().getTime()));
    ps.setDate(6, new java.sql.Date(proceso.getUpdatedAt().getTime()));
    ps.setInt(7, proceso.getId());
  }
}
