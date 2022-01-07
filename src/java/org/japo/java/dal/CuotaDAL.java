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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.Usuario;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class CuotaDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "cuotas";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Campos
    private final HttpSession sesion;

    public CuotaDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, usuario);
    }

    public List<Cuota> obtenerCuotas() {
        return obtenerCuotas(PL);
    }

    public Cuota obtenerCuota(int id) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("id")));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);

        // Lista de Cuotas
        List<Cuota> cuotas = obtenerCuotas(PL);

        // Referencia de Entidad
        return cuotas.isEmpty() ? null : cuotas.get(0);
    }

    public boolean insertarCuota(Cuota usuario) {
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

    public boolean borrarCuota(int id) {
        // SQL
        final String SQL = "DELETE FROM cuotas WHERE id=?";

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

    public boolean modificarCuota(Cuota cuota) {
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
                parametrizarUpdate(ps, cuota);

                // Ejecutar Sentencia
                numReg = ps.executeUpdate();
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno: true | false
        return numReg == 1;
    }

    public Long contarCuotas(ParametrosListado pl) {
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(pl);

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                // BD > Lista de Entidades
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

    public List<Cuota> obtenerCuotas(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarCuotas(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Cuota> cuotas = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                cuotas = exportarListaCuotas(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return cuotas;
    }

    private List<Cuota> exportarListaCuotas(PreparedStatement ps)
            throws SQLException {
        // Lista 
        List<Cuota> lista = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Cuota data = exportarCuota(rs);

                // Entidad > Lista
                lista.add(data);
            }
        }

        // Retorno: Lista de Cuotas
        return lista;
    }

    private Cuota exportarCuota(ResultSet rs)
            throws SQLException {
        // Fila Actual > Campos 
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String info = rs.getString("info");
        int status = rs.getInt("status");
        String data = rs.getString("data");
        Date createdAt = rs.getDate("created_at");
        Date updatedAt = rs.getDate("updated_at");

        // Campos > Entidad
        return new Cuota(id, nombre, info,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
                + "* "
                + "FROM "
                + "cuotas";
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "cuotas";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "cuotas "
                + "("
                + "nombre, info, "
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "cuotas "
                + "SET nombre=?, info=?, "
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

        // SQL Completo: SELECT + WHERE
        return String.format("%s%s", select, where);
    }

    private void parametrizarInsert(PreparedStatement ps, Cuota cuota)
            throws SQLException {
        ps.setString(1, cuota.getNombre());
        ps.setString(2, cuota.getInfo());
        ps.setInt(3, cuota.getStatus());
        ps.setString(4, cuota.getData());
        ps.setDate(5, new java.sql.Date(cuota.getCreatedAt().getTime()));
        ps.setDate(6, new java.sql.Date(cuota.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Cuota cuota)
            throws SQLException {
        ps.setString(1, cuota.getNombre());
        ps.setString(2, cuota.getInfo());
        ps.setInt(3, cuota.getStatus());
        ps.setString(4, cuota.getData());
        ps.setDate(5, new java.sql.Date(cuota.getCreatedAt().getTime()));
        ps.setDate(6, new java.sql.Date(cuota.getUpdatedAt().getTime()));
        ps.setInt(7, cuota.getId());
    }
}
