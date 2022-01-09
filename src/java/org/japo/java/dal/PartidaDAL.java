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
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Partida;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class PartidaDAL extends AbstractDAL {

    // Usuario
    private final Usuario usuario;

    public PartidaDAL(HttpSession sesion) {
        usuario = (Usuario) sesion.getAttribute("usuario");
    }

    public List<Partida> obtenerPartidas() {
        // SQL
        String sql = generarSQLSelect();

        // Lista Vacía
        List<Partida> partidas = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                partidas = exportarListaPartidas(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return partidas;
    }

    public Partida obtenerPartida(int id) {
        // SQL
        String sqlSelect = generarSQLSelect();
        String sqlWhere = " WHERE partidas.id=" + id;
        String sql = sqlSelect + sqlWhere;

        // Lista Vacía
        List<Partida> partidas = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                partidas = exportarListaPartidas(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return partidas.isEmpty() ? null : partidas.get(0);
    }

    public boolean insertarPartida(Partida partida) {
        // SQL
        final String SQL = generarSQLInsert();

        // Número de registros afectados
        int numReg = 0;

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarInsert(ps, partida);

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
        final String SQL = generarSQLDelete();

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

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

    public boolean modificarPartida(Partida partida) {
        // SQL
        final String SQL = generarSQLUpdate();

        // Número de Registros Afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarUpdate(ps, partida);

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
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(pl);

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

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

        // Retorno: Filas Contadas
        return filas;
    }

    public List<Partida> obtenerPartidas(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarPartidas(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Partida> partidas = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(BD);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                partidas = exportarListaPartidas(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return partidas;
    }

    private List<Partida> exportarListaPartidas(PreparedStatement ps)
            throws SQLException {
        // Lista 
        List<Partida> lista = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Partida data = exportarPartida(rs);

                // Entidad > Lista
                lista.add(data);
            }
        }

        // Retorno: Lista de Partidas
        return lista;
    }

    private Partida exportarPartida(ResultSet rs)
            throws SQLException {
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
        return new Partida(id, nombre, info,
                icono, proyectoID, proyectoInfo,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
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
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "partidas "
                + "INNER JOIN "
                + "proyectos ON proyectos.id = partidas.proyecto";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "partidas "
                + "("
                + "nombre, info, icono,"
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "partidas "
                + "SET "
                + "nombre=?, info=?, icono=?, "
                + "status=?, data=?, created_at=?, updated_at=?"
                + "WHERE id=?";
    }

    public String generarSQLDelete() {
        return ""
                + "DELETE FROM "
                + "partidas "
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

    private void parametrizarInsert(PreparedStatement ps, Partida partida)
            throws SQLException {
        ps.setString(1, partida.getNombre());
        ps.setString(2, partida.getInfo());
        ps.setString(3, partida.getIcono());
        ps.setInt(4, partida.getStatus());
        ps.setString(5, partida.getData());
        ps.setDate(6, new java.sql.Date(partida.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(partida.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Partida partida)
            throws SQLException {
        ps.setString(1, partida.getNombre());
        ps.setString(2, partida.getInfo());
        ps.setString(3, partida.getIcono());
        ps.setInt(4, partida.getStatus());
        ps.setString(5, partida.getData());
        ps.setDate(6, new java.sql.Date(partida.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(partida.getUpdatedAt().getTime()));
        ps.setInt(8, partida.getId());
    }
}
