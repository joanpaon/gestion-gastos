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
import org.japo.java.entities.AtributosLista;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;
import org.japo.java.libraries.UtilesGastos;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class ProyectoDAL extends AbstractDAL {

    // Constantes
    private final String TABLA = "proyectos";

    // Parámetros de Listado
    private final ParametrosListado PL;

    // Sesión
    private final HttpSession sesion;

    public ProyectoDAL(HttpSession sesion) {
        this.sesion = sesion;

        // Sesión > Usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // BD + TABLA + usuario > Parámetros de Listado
        PL = new ParametrosListado(BD, TABLA, usuario);
    }

    public List<Proyecto> obtenerProyectos() {
        return obtenerProyectos(PL);
    }

    public Proyecto obtenerProyecto(int id) {
        // Parámetros de Listado
        PL.setFilterFields(new ArrayList<>(Arrays.asList("proyectos.id")));
        PL.setFilterValue(id + "");
        PL.setFilterStrict(true);

        // Lista de Proyectos
        List<Proyecto> proyectos = obtenerProyectos(PL);

        // Referencia de Entidad
        return proyectos.isEmpty() ? null : proyectos.get(0);
    }

    public boolean insertarProyecto(Proyecto proyecto) {
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
                parametrizarInsert(ps, proyecto);

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

    public boolean modificarProyecto(Proyecto proyecto) {
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
                parametrizarUpdate(ps, proyecto);

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

    public void contarFilas(AtributosLista la) {
        // Número de Filas
        long filas = 0;

        // SQL
        String sql = generarSQLComputo(la);

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

        // Actualizar Computo Filas
        la.getPagina().setFilasTotal(filas);
    }

    public List<Proyecto> obtenerProyectos(ParametrosListado pl) {
        // Número de Usuarios > Parámetro Listado
        pl.setRowCount(contarProyectos(pl));

        // Navegación > Parametros listado
        UtilesGastos.definirIndiceListado(pl);

        // SQL
        String sql = generarSQLListado(pl);

        // Lista Vacía
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                proyectos = exportarListaProyectos(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return proyectos;
    }

    public List<Proyecto> obtenerProyectos(AtributosLista al) {
        // SQL
        String sql = generarSQLListado(al);

        // Lista Vacía
        List<Proyecto> proyectos = new ArrayList<>();

        try {
            // Contexto Inicial > DataSource
            DataSource ds = obtenerDataSource(PL);

            try (
                    Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                proyectos = exportarListaProyectos(ps);
            }
        } catch (NamingException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Retorno Lista
        return proyectos;
    }

    private List<Proyecto> exportarListaProyectos(PreparedStatement ps) throws SQLException {
        // Lista 
        List<Proyecto> proyectos = new ArrayList<>();

        // BD > Lista de Entidades
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Campos > Entidad
                Proyecto proyecto = exportarProyecto(rs);

                // Entidad > Lista
                proyectos.add(proyecto);
            }
        }

        // Retorno: Lista de Proyectos
        return proyectos;
    }

    private Proyecto exportarProyecto(ResultSet rs) throws SQLException {
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
        return new Proyecto(id, nombre, info,
                icono, propietarioID, propietarioInfo, cuotaID, cuotaInfo,
                status, data, createdAt, updatedAt);
    }

    public String generarSQLSelect() {
        return ""
                + "SELECT "
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
    }

    public String generarSQLSelectComputo() {
        return ""
                + "SELECT "
                + "COUNT(*) "
                + "FROM "
                + "proyectos "
                + "INNER JOIN "
                + "usuarios ON usuarios.id = proyectos.propietario "
                + "INNER JOIN "
                + "cuotas ON cuotas.id = proyectos.cuota";
    }

    public String generarSQLInsert() {
        return ""
                + "INSERT INTO "
                + "proyectos "
                + "("
                + "nombre, info, icono, propietario, cuota, "
                + "status, data, created_at, updated_at"
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String generarSQLUpdate() {
        return ""
                + "UPDATE "
                + "proyectos "
                + "SET "
                + "nombre=?, info=?, icono=?, propietario=?, cuota=? "
                + "status=?, data=?, created_at=?, updated_at=?"
                + "WHERE id=?";
    }

    public String generarSQLDelete() {
        return ""
                + "DELETE FROM "
                + "proyectos "
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

    private String generarSQLListado(AtributosLista al) {
        // SQL Parciales
        String select = generarSQLSelect();
        String where = generarSQLWhere(al);
        String order = generarSQLOrder(al);
        String limit = generarSQLLimit(al);

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

    protected String generarSQLComputo(AtributosLista la) {
        // SQL Parciales
        String select = generarSQLSelectComputo();
        String where = generarSQLWhere(la);

        // SQL Completo: SELECT + WHERE
        return String.format("%s%s", select, where);
    }

    private void parametrizarInsert(PreparedStatement ps, Proyecto proyecto) throws SQLException {
        ps.setString(1, proyecto.getNombre());
        ps.setString(2, proyecto.getInfo());
        ps.setString(3, proyecto.getIcono());
        ps.setInt(4, proyecto.getPropietarioID());
        ps.setInt(5, proyecto.getCuotaID());
        ps.setInt(6, proyecto.getStatus());
        ps.setString(7, proyecto.getData());
        ps.setDate(8, new java.sql.Date(proyecto.getCreatedAt().getTime()));
        ps.setDate(9, new java.sql.Date(proyecto.getUpdatedAt().getTime()));
    }

    private void parametrizarUpdate(PreparedStatement ps, Proyecto proyecto) throws SQLException {
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
    }

    @Override
    protected String generarSQLWhere(ParametrosListado pl) {
        // SQL
        String sql;

        // SQL Perfil ( Abonos )
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
                + "proyectos.id IN "
                + "("
                + "SELECT abonos.proyecto "
                + "FROM abonos "
                + "WHERE abonos.usuario=" + pl.getUser().getId()
                + ")";
    }
}
