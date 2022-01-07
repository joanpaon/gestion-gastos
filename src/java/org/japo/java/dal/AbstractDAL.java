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

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.japo.java.entities.ParametrosListado;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public abstract class AbstractDAL {

    // Constantes
    protected final String BD = "gestion_gastos";

    public AbstractDAL() {
    }

    protected String generarSQLWhere(ParametrosListado pl) {
        // SQL
        String sql;

        // SQL Parciales
        String sqlFtr = generarSQLFilter(pl);

        // Procesar SQL
        sql = sqlFtr.isBlank() ? "" : " WHERE " + sqlFtr;

        // Retorno: SQL
        return sql;
    }

    // El SQL de Filtro supone establecer la selección de aquellos 
    // registros en los que el/los valor/es de UNO o VARIOS CAMPOS 
    // ( Los que se consideren signficativos ) contenga/n de forma 
    // ESTRICTA ( Valor Exacto ) o RELAJADA ( Valor contenido ), el 
    // valor indicado en la expresión
    protected String generarSQLFilter(ParametrosListado pl) {
        // Bombres de Campos a Filtrar
        List<String> campos = pl.getFilterFields();

        // Concatenador
        StringBuilder buffer = new StringBuilder();

        // Genera + Concatena Expresiones de Filtro por Campo
        if (!pl.getFilterValue().isBlank()) {
            if (pl.isFilterStrict()) {
                for (int i = 0; i < campos.size(); i++) {
                    buffer.append(String.format("%s='%s'",
                            campos.get(i), pl.getFilterValue()));
                    if (i < campos.size() - 1) {
                        buffer.append(" AND ");
                    }
                }
            } else {
                for (int i = 0; i < campos.size(); i++) {
                    buffer.append(String.format("%s LIKE '%%%s%%'",
                            campos.get(i), pl.getFilterValue()));
                    if (i < campos.size() - 1) {
                        buffer.append(" OR ");
                    }
                }
            }
        }

        // Retorno: SQL
        return campos.isEmpty() ? "" : buffer.toString().trim();
    }

    protected String generarSQLOrder(ParametrosListado pl) {
        // SQL
        String sql;

        // Parámetros Ordenación
        String campo = pl.getOrderField();
        String sentido = pl.getOrderAdvance();

        if (sentido == null || sentido.isBlank()) {
            // NO se ha especificado SENTIDO de ordenación
            // Los datos se muestran en la secuencia en que 
            // se recuperan de la base de datos
            sql = "";
        } else if (campo == null || campo.isBlank()) {
            // SI se ha especificado SENTIDO de ordenación
            // NO se ha especificado CAMPO de ordenación
            // Los datos se muestran en la secuencia en que 
            // se recuperan de la base de datos
            sql = "";
        } else if (sentido.equalsIgnoreCase("asc")) {
            // SI se ha especificado SENTIDO > ASCENDENTE
            // SI se ha especificado CAMPO de ordenación
            // Los datos se ordenan de MENOR a MAYOR por los
            // valores del campo de ordenación
            sql = String.format(" ORDER BY %s ASC", campo);
        } else if (sentido.equalsIgnoreCase("desc")) {
            // SI se ha especificado SENTIDO > DESCENDENTE
            // SI se ha especificado CAMPO de ordenación
            // Los datos se ordenan de MAYOR a MENOR por los
            // valores del campo de ordenación
            sql = String.format(" ORDER BY %s DESC", campo);
        } else {
            // Valor incorrecto del SENTIDO de ordenación
            // Los datos se muestran en la secuencia en que 
            // se recuperan de la base de datos
            sql = "";
        }

        // Retorno: SQL
        return sql;
    }

    protected String generarSQLLimit(ParametrosListado pl) {
        // SQL
        String sql;

        // Parámetros Paginación
        Long indice = pl.getRowIndex();
        Long tamany = pl.getRowsPage();

        // Discriminación
        if (tamany == null || tamany <= 0) {
            // Tamaño de Página NULO
            // Paginación Desactivada
            sql = "";
        } else if (indice == null || indice < 0) {
            // Índice de Página NULO
            // Paginación Desactivada
            sql = "";
        } else {
            // Se entregan filas desde el índice de página
            // en una cantidad indicada por el tamaño de página
            sql = String.format(" LIMIT %d,%d", indice, tamany);
        }

        // Retorno: SQL
        return sql;
    }

    protected DataSource obtenerDataSource(ParametrosListado pl) throws NamingException {
        // Contexto Inicial Nombrado JNDI
        Context iniCtx = new InitialContext();

        // Situar Contexto Inicial
        Context envCtx = (Context) iniCtx.lookup("java:/comp/env");

        // Contexto Inicial > DataSource
        return (DataSource) envCtx.lookup("jdbc/" + pl.getDbName());
    }
}
