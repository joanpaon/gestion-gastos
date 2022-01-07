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
import org.japo.java.entities.Abono;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class AbonoDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "abonos";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Campos
    private final HttpSession sesion;

    public AbonoDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, usuario);
    }

    public List<Abono> obtenerAbonos() {
        return obtenerAbonos(PL);
    }

    public List<Abono> obtenerAbonos(int usuarioID) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("abonos.usuario")));
        PL.setFilterValue(usuarioID + "");
        PL.setFilterStrict(true);
        PL.setRowsPage(Long.MAX_VALUE);

        // Retorno: Lista de Abonos
        return obtenerAbonos(PL);
    }

    public Abono obtenerAbono(int id) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("abonos.id")));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);
        PL.setRowsPage(Long.MAX_VALUE);

        // Lista de Abonos
        List<Abono> abonos = obtenerAbonos(PL);

        // Referencia de Entidad
        return abonos.isEmpty() ? null : abonos.get(0);
    }

    public boolean insertarAbono(Abono abono) {
        // SQL
        final String SQL = generarSQLInsert();

        // Número de registros afectados
        int numReg = 0;

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarInsert(ps, abono);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public boolean borrarAbono(int id) {
        // SQL
        final String SQL = generarSQLDelete();

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

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

    public boolean modificarAbono(Abono abono) {
        // SQL
        final String SQL = generarSQLUpdate();

        // Número de registros afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(SQL)) {
                // Parametrizar Sentencia
                parametrizarUpdate(ps, abono);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Long contarAbonos(ParametrosListado pl) {
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(pl);

        // Obtención del Contexto
        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

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

    public List<Abono> obtenerAbonos(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarAbonos(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Abono> abonos = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                abonos = exportarListaAbonos(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return abonos;
    }

    private List<Abono> exportarListaAbonos(PreparedStatement ps) throws SQLException {
        // Lista 
        List<Abono> lista = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Abono data = exportarAbono(rs);

                // Entidad > Lista
                lista.add(data);
            }
        }

        // Retorno: Lista de Abonos
        return lista;
    }

    private Abono exportarAbono(ResultSet rs) throws SQLException {
        // Fila Actual > Campos 
        int id = rs.getInt("id");
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
        return new Abono(id,
                proyectoID, proyectoInfo,
                usuarioID, usuarioInfo, info,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
                + "abonos.id AS id, "
                + "abonos.proyecto AS proyecto_id, "
                + "proyectos.nombre AS proyecto_info, "
                + "abonos.usuario AS usuario_id, "
                + "usuarios.user AS usuario_info, "
                + "abonos.info AS info, "
                + "abonos.status AS status, "
                + "abonos.data AS data, "
                + "abonos.created_at AS created_at, "
                + "abonos.updated_at AS updated_at "
                + "FROM "
                + "abonos "
                + "INNER JOIN "
                + "proyectos ON abonos.proyecto = proyectos.id "
                + "INNER JOIN "
                + "usuarios ON abonos.usuario = usuarios.id";
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "abonos "
                + "INNER JOIN "
                + "proyectos ON abonos.proyecto = proyectos.id "
                + "INNER JOIN "
                + "usuarios ON abonos.usuario = usuarios.id";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "abonos "
                + "("
                + "proyecto, usuario, info, "
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "abonos "
                + "SET "
                + "proyecto=?, usuario=?, info=?, "
                + "status=?, data=?, created_at=?, updated_at=? "
                + "WHERE id=?";
    }

    public String generarSQLDelete() {
        return ""
                + "DELETE FROM "
                + "abonos "
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

    private void parametrizarInsert(PreparedStatement ps, Abono abono) throws SQLException {
        ps.setInt(1, abono.getProyectoID());
        ps.setInt(2, abono.getUsuarioID());
        ps.setString(3, abono.getInfo());
        ps.setInt(7, abono.getStatus());
        ps.setString(8, abono.getData());
        ps.setDate(9, new java.sql.Date(abono.getCreatedAt().getTime()));
        ps.setDate(10, new java.sql.Date(abono.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Abono abono) throws SQLException {
        ps.setInt(1, abono.getProyectoID());
        ps.setInt(2, abono.getUsuarioID());
        ps.setString(3, abono.getInfo());
        ps.setInt(4, abono.getStatus());
        ps.setString(5, abono.getData());
        ps.setDate(6, new java.sql.Date(abono.getCreatedAt().getTime()));
        ps.setDate(7, new java.sql.Date(abono.getUpdatedAt().getTime()));
        ps.setInt(8, abono.getId());
    }

    @Override
    protected String generarSQLWhere(ParametrosListado pl) {
        // SQL
        String sql;

        // SQL Perfil
        String sqlPrf;
        switch (pl.getUser().getPerfilID()) {
            case Perfil.DEVEL:
                sqlPrf = "";
                break;
            case Perfil.ADMIN:
                sqlPrf = "";
                break;
            case Perfil.BASIC:
                sqlPrf = "abonos.usuario=" + pl.getUser().getId();
                break;
            default:
                sqlPrf = "abonos.usuario=" + pl.getUser().getId();
        }

        // SQL Filtro
        String sqlFtr = generarSQLFilter(pl);

        // Procesar SQL
        if (sqlPrf.isBlank()) {
            sql = sqlFtr.isBlank() ? "" : " WHERE " + sqlFtr;
        } else if (sqlFtr.isBlank()) {
            sql = " WHERE " + sqlPrf;
        } else {
            sql = " WHERE " + sqlPrf + " AND (" + sqlFtr + ")";
        }

        // Retorno: SQL
        return sql;
    }
}
