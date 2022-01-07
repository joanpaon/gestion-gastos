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
import org.japo.java.entities.Gasto;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class GastoDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "gastos";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Campos
    private final HttpSession sesion;

    public GastoDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, usuario);
    }

    public List<Gasto> obtenerGastos() {
        return obtenerGastos(PL);
    }

    public Gasto obtenerGasto(int id) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("gastos.id")));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);

        // Lista de Usuarios
        List<Gasto> gastos = obtenerGastos(PL);

        // Referencia de Entidad
        return gastos.isEmpty() ? null : gastos.get(0);
    }

    public boolean insertarGasto(Gasto gasto) {
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

    public boolean modificarGasto(Gasto gasto) {
        // SQL
        final String SQL = generarSQLUpdate();

        // Número de Registros Afectados
        int numReg = 0;

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

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
        String sql = generarSQLComputo(pl);

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

    public List<Gasto> obtenerGastos(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarGastos(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Gasto> gastos = new ArrayList<>();

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

    private List<Gasto> exportarListaGastos(PreparedStatement ps) throws SQLException {
        // Lista 
        List<Gasto> lista = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Gasto data = exportarGasto(rs);

                // Entidad > Lista
                lista.add(data);
            }
        }

        // Retorno: Lista de Gastos
        return lista;
    }

    private Gasto exportarGasto(ResultSet rs) throws SQLException {
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
        return new Gasto(id, abonoID, abonoInfo, importe,
                info, partidaID, partidaInfo, recibo,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
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

        // SQL Completo: SELECT + WHERE + ORDER + LIMIT
        return String.format("%s%s", select, where);
    }

    private void parametrizarInsert(PreparedStatement ps, Gasto gasto)
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

    private void parametrizarUpdate(PreparedStatement ps, Gasto gasto)
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
                sqlPrf = generarSQLPerfil(pl);
                break;
            default:
                sqlPrf = generarSQLPerfil(pl);
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

    private String generarSQLPerfil(ParametrosListado pl) {
        // Retorno: SQL
        return ""
                + "gastos.abono IN "
                + "("
                + "SELECT abonos.id "
                + "FROM abonos "
                + "WHERE abonos.usuario=" + pl.getUser().getId()
                + ")";
    }
}
