/* 
 * Copyright 2021 José A. Pacheco Ondoño - japolabs@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Usuario;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UsuarioDAL extends AbstractDAL {

  // Constantes
  private final String TABLA = "usuarios";

  // Parámetros de Listado
  private final ParametrosListado PL;

  // Sesión
  private final HttpSession sesion;

  public UsuarioDAL(HttpSession sesion) {
    this.sesion = sesion;

    // Sesión > Usuario
    Usuario usuario = (Usuario) sesion.getAttribute("usuario");

    // BD + TABLA + usuario > Parámetros de Listado
    PL = new ParametrosListado(BD, TABLA, usuario);
  }

  public List<Usuario> obtenerUsuarios() {
    return obtenerUsuarios(PL);
  }

  public Usuario obtenerUsuario(int id) {
    // Parámetros de Listado
    PL.setFilterFields(Arrays.asList("id"));
    PL.setFilterValue(id + "");
    PL.setFilterStrict(true);

    // Lista de Usuarios
    List<Usuario> usuarios = obtenerUsuarios(PL);

    // Referencia de Entidad
    return usuarios.isEmpty() ? null : usuarios.get(0);
  }

  public Usuario obtenerUsuario(String user) {
    PL.setFilterFields(Arrays.asList("user"));
    PL.setFilterValue(user);
    PL.setFilterStrict(true);

    // Lista de Usuarios
    List<Usuario> usuarios = obtenerUsuarios(PL);

    // Referencia de Entidad
    return usuarios.isEmpty() ? null : usuarios.get(0);
  }

  public boolean insertarUsuario(Usuario usuario) {
    // SQL
    final String SQL = generarSQLInsert();

    // Número de registros afectados
    int numReg = 0;

    // Obtención del Contexto
    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
              Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarInsert(ps, usuario);

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
    final String SQL = generarSQLDelete();

    // Número de registros afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
              Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
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

  public boolean modificarUsuario(Usuario usuario) {
    // SQL
    final String SQL = generarSQLUpdate();

    // Número de Registros Afectados
    int numReg = 0;

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
              Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL)) {
        // Parametrizar Sentencia
        parametrizarUpdate(ps, usuario);

        // Ejecutar Sentencia
        numReg = ps.executeUpdate();
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno: true | false
    return numReg == 1;
  }

  public Long contarUsuarios(ParametrosListado pl) {
    // Número de Filas
    long filas = 0;

    // SQL
    String sql = generarSQLComputo(pl);

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
              Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
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

  public List<Usuario> obtenerUsuarios(ParametrosListado pl) {
    // SQL
    String sql = generarSQLListado(pl);

    // Lista Vacía
    List<Usuario> usuarios = new ArrayList<>();

    try {
      // Contexto Inicial > DataSource
      DataSource ds = obtenerDataSource(PL);

      try (
              Connection conn = ds.getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {
        usuarios = exportarListaUsuarios(ps);
      }
    } catch (NamingException | SQLException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }

    // Retorno Lista
    return usuarios;
  }

  private List<Usuario> exportarListaUsuarios(PreparedStatement ps) throws SQLException {
    // Lista 
    List<Usuario> usuarios = new ArrayList<>();

    // BD > Lista de Entidades
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        // Campos > Entidad
        Usuario usuario = exportarUsuario(rs);

        // Entidad > Lista
        usuarios.add(usuario);
      }
    }

    // Retorno: Lista de Usuarios
    return usuarios;
  }

  private Usuario exportarUsuario(ResultSet rs) throws SQLException {
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
    return new Usuario(id, user, pass,
            email, icono, perfilId, perfilInfo, info,
            status, data, createdAt, updatedAt);
  }

  public String generarSQLSelect() {
    return ""
            + "SELECT "
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
  }

  public String generarSQLSelectComputo() {
    return ""
            + "SELECT "
            + "COUNT(*) "
            + "FROM "
            + "usuarios "
            + "INNER JOIN "
            + "perfiles ON perfiles.id = usuarios.perfil";
  }

  public String generarSQLInsert() {
    return ""
            + "INSERT INTO "
            + "usuarios "
            + "("
            + "user, pass, email, icono, perfil, info, "
            + "status, data, created_at, updated_at"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  }

  public String generarSQLUpdate() {
    return ""
            + "UPDATE "
            + "usuarios "
            + "SET "
            + "user=?, pass=?, email=?, icono=?, perfil=?, info=?, "
            + "status=?, data=?, created_at=?, updated_at=? "
            + "WHERE id=?";
  }

  public String generarSQLDelete() {
    return ""
            + "DELETE FROM "
            + "usuarios "
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

  private void parametrizarInsert(PreparedStatement ps, Usuario usuario) throws SQLException {
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
  }

  private void parametrizarUpdate(PreparedStatement ps, Usuario usuario) throws SQLException {
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
  }
}
