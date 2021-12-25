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
import org.japo.java.entities.Abono;
import org.japo.java.entities.AtributosLista;
import org.japo.java.entities.Cuota;
import org.japo.java.entities.Filtro;
import org.japo.java.entities.ParametrosListado;
import org.japo.java.entities.Perfil;
import org.japo.java.entities.Proyecto;
import org.japo.java.entities.Usuario;

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
    String sqlUsr = generarSQLUser(pl);
    String sqlFtr = generarSQLFilter(pl);

    // Fusionar SQL Parciales
    if (sqlUsr.isBlank()) {
      if (sqlFtr.isBlank()) {
        sql = "";
      } else {
        sql = String.format(" WHERE %s", sqlFtr);
      }
    } else {
      if (sqlFtr.isBlank()) {
        sql = String.format(" WHERE %s", sqlUsr);
      } else {
        sql = String.format(" WHERE %s AND (%s)", sqlUsr, sqlFtr);
      }
    }

    // Retorno: SQL
    return sql;
  }

  protected String generarSQLWhere(AtributosLista la) {
    // SQL
    String sql;

    // SQL Parciales
    String sqlUsr = generarSQLEntity(la);
    String sqlFtr = generarSQLFilter(la);

    // Fusionar SQL Parciales
    if (sqlUsr.isBlank()) {
      if (sqlFtr.isBlank()) {
        sql = "";
      } else {
        sql = String.format(" WHERE %s", sqlFtr);
      }
    } else {
      if (sqlFtr.isBlank()) {
        sql = String.format(" WHERE %s", sqlUsr);
      } else {
        sql = String.format(" WHERE %s AND (%s)", sqlUsr, sqlFtr);
      }
    }

    // Retorno: SQL
    return sql;
  }

  private String generarSQLUser(ParametrosListado pl) {
    // SQL
    String sql;

    // Parámetros Usuario
    Usuario usuario = pl.getUser();

    // Discriminar Usuario
    if (usuario == null) {
      // Cuando NO se especifica usuario en los parámetros 
      // de listado, se seleccionan TODOS los abonos
      sql = "";
    } else {
      // Usuario > Pefil
      int perfilID = usuario.getPerfilID();
      String tabla = pl.getTable();
      String campo = pl.getUserField();

      if (perfilID == Perfil.DEVEL) {
        // Cuando se especifica un usuario DESARROLLADOR en los 
        // parámetros de listado, se seleccionan TODOS los registros
        sql = "";
      } else if (perfilID == Perfil.ADMIN) {
        // Cuando se especifica un usuario ADMINISTRADOR en los 
        // parámetros de listado, se seleccionan TODOS los registros
        sql = "";
      } else if (campo == null) {
        // Cuando NO se especifica un campo de usuario 
        // se seleccionan TODOS los registros
        sql = "";
      } else {
        // Cuando se especifica un usuario BASICO en los parámetros 
        // de listado, se seleccionan SÓLO los registros de ese usuario
        sql = String.format("%s.%s=%d", tabla, campo, usuario.getId());
      }
    }

    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntity(AtributosLista al) {
    // SQL
    String sql;

    // Discriminación de Tabla
    switch (al.getTable()) {
      case "abonos":
        if (al.getObjetivo() instanceof Usuario) {
          sql = generarSQLEntityAbonosUsuario(al);
        } else if (al.getObjetivo() instanceof Proyecto) {
          sql = generarSQLEntityAbonosProyecto(al);
        } else {
          sql = "";
        }
        break;
      case "cierres":
        if (al.getObjetivo() instanceof Proyecto) {
          sql = generarSQLEntityCierres(al);
        } else {
          sql = "";
        }
        break;
      case "cuotas":
        sql = "";
        break;
      case "gastos":
        if (al.getObjetivo() instanceof Abono) {
          sql = generarSQLEntityCuotasAbono(al);
        } else if (al.getObjetivo() instanceof Proyecto) {
          sql = generarSQLEntityCuotasPartida(al);
        } else {
          sql = "";
        }
        break;
      case "partidas":
        if (al.getObjetivo() instanceof Proyecto) {
          sql = generarSQLEntityPartidas(al);
        } else {
          sql = "";
        }
        break;
      case "perfiles":
        sql = "";
        break;
      case "permisos":
        if (al.getObjetivo() instanceof Abono) {
          sql = generarSQLEntityPermisosProceso(al);
        } else if (al.getObjetivo() instanceof Proyecto) {
          sql = generarSQLEntityPermisosPerfil(al);
        } else {
          sql = "";
        }
        break;
      case "procesos":
        sql = "";
        break;
      case "proyectos":
        if (al.getObjetivo() instanceof Usuario) {
          sql = generarSQLEntityProyectosPropietario(al);
        } else if (al.getObjetivo() instanceof Cuota) {
          sql = generarSQLEntityProyectosCuota(al);
        } else {
          sql = "";
        }
        break;
      default:
        sql = "";
    }

    // Retorno: SQL
    return sql;
  }

  // El SQL de Filtro supone establecer la selección de aquellos 
  // registros en los que el/los valor/es de UNO o VARIOS CAMPOS 
  // ( Los que se consideren signficativos ) contenga/n de forma 
  // ESTRICTA ( Valor Exacto ) o RELAJADA ( Valor contenido ), el 
  // valor indicado en la expresión
  private String generarSQLFilter(ParametrosListado pl) {
    // Bombres de Campos a Filtrar
    List<String> campos = pl.getFilterFields();

    // Concatenador
    StringBuilder buffer = new StringBuilder();

    // Genera + Concatena Expresiones de Filtro por Campo
    if (pl.isFilterStrict()) {
      for (int i = 0; i < campos.size(); i++) {
        buffer.append(String.format("%s.%s='%s'",
                pl.getTable(), campos.get(i), pl.getFilterValue()));
        if (i < campos.size() - 1) {
          buffer.append(" AND ");
        }
      }
    } else {
      for (int i = 0; i < campos.size(); i++) {
        buffer.append(String.format("%s.%s LIKE '%%%s%%'",
                pl.getTable(), campos.get(i), pl.getFilterValue()));
        if (i < campos.size() - 1) {
          buffer.append(" OR ");
        }
      }
    }

    // Retorno: SQL
    return campos.isEmpty() ? "" : buffer.toString().trim();
  }

  // El SQL de Filtro supone establecer la selección de aquellos 
  // registros en los que el/los valor/es de UNO o VARIOS CAMPOS 
  // ( Los que se consideren signficativos ) contenga/n de forma 
  // ESTRICTA ( Valor Exacto ) o RELAJADA ( Valor contenido ), el 
  // valor indicado en la expresión
  private String generarSQLFilter(AtributosLista la) {
    // AtributosLista > Filtro
    Filtro filtro = la.getFiltro();

    // Bombres de Campos a Filtrar
    List<String> campos = filtro.getCampos();

    // Concatenador
    StringBuilder buffer = new StringBuilder();

    // Genera + Concatena Expresiones de Filtro por Campo
    if (filtro.isExacto()) {
      for (int i = 0; i < campos.size(); i++) {
        buffer.append(String.format("%s.%s='%s'",
                la.getTable(), campos.get(i), filtro.getPatron()));
        if (i < campos.size() - 1) {
          buffer.append(" OR ");
        }
      }
    } else {
      for (int i = 0; i < campos.size(); i++) {
        buffer.append(String.format("%s.%s LIKE '%%%s%%'",
                la.getTable(), campos.get(i), filtro.getPatron()));
        if (i < campos.size() - 1) {
          buffer.append(" OR ");
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
    String sentido = pl.getOrderProgress();

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

  protected String generarSQLOrder(AtributosLista al) {
    // SQL
    String sql;

    // Parámetros Ordenación
    String campo = al.getOrden().getCampo();
    String avance = al.getOrden().getAvance();

    if (avance == null || avance.isBlank()) {
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
    } else if (avance.equalsIgnoreCase("asc")) {
      // SI se ha especificado SENTIDO > ASCENDENTE
      // SI se ha especificado CAMPO de ordenación
      // Los datos se ordenan de MENOR a MAYOR por los
      // valores del campo de ordenación
      sql = String.format(" ORDER BY %s ASC", campo);
    } else if (avance.equalsIgnoreCase("desc")) {
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

  protected String generarSQLLimit(AtributosLista al) {
    // SQL
    String sql;

    // Parámetros Paginación
    long indice = al.getPagina().getIndice();
    long filasPagina = al.getPagina().getFilasPagina();

    // Discriminación
    if (filasPagina <= 0) {
      // Tamaño de Página NULO
      // Paginación Desactivada
      sql = "";
    } else if (indice < 0) {
      // Índice de Página NULO
      // Paginación Desactivada
      sql = "";
    } else {
      // Se entregan filas desde el índice de página
      // en una cantidad indicada por el tamaño de página
      sql = String.format(" LIMIT %d,%d", indice, filasPagina);
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


  /*
  Los ABONOS están relacionados con los USUARIOS de manera que se 
  define un FILTRO para limitar la LISTA DE ABONOS únicamente a los que 
  correspondientes a un USUARIO OBJETIVO, que NO ES EL USUARIO ACTUAL.
  Si NO se establece USUARIO OBJETIVO entonces NO se define FILTRO.
   */
  private String generarSQLEntityAbonosUsuario(AtributosLista al) {
    // SQL
    String sql;

    // Atributos Lista > Objetivo
    Object entidad = al.getObjetivo();

    // Discrimina Entidad
    if (entidad instanceof Usuario) {
      // Entidad > Usuario
      Usuario usuario = (Usuario) entidad;

      // Discrimina Usuario
      sql = "abonos.usuario='" + usuario.getId() + "'";
    } else {
      sql = "";
    }

    // Retorno: SQL
    return sql;
  }

  /*
  Los ABONOS están relacionados con los PROYECTOS de manera que se 
  define un FILTRO para limitar la LISTA DE ABONOS únicamente a los que 
  correspondientes a un PROYECTO OBJETIVO.
  Si NO se establece PROYECTO OBJETIVO entonces NO se define FILTRO.
   */
  private String generarSQLEntityAbonosProyecto(AtributosLista al) {
    // SQL
    String sql;

    // Atributos Lista > Objetivo
    Object entidad = al.getObjetivo();

    // Discrimina Entidad
    if (entidad instanceof Proyecto) {
      // Entidad > Proyecto
      Proyecto proyecto = (Proyecto) entidad;

      // Discrimina Usuario
      sql = "abonos.proyecto='" + proyecto.getId() + "'";
    } else {
      sql = "";
    }

    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityCierres(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityCuotasAbono(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityCuotasPartida(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityPartidas(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityPermisosProceso(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityPermisosPerfil(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityProyectosPropietario(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }

  private String generarSQLEntityProyectosCuota(AtributosLista al) {
    // SQL
    String sql = "";
    
    // Retorno: SQL
    return sql;
  }
}
