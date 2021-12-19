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
package org.japo.java.libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.japo.java.bll.command.Command;
import org.japo.java.bll.command.admin.CommandUnknown;
import org.japo.java.bll.service.IService;
import org.japo.java.bll.service.Service;
import org.japo.java.bll.service.admin.ServiceUnknown;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
public final class UtilesServlet {

    // Ruta Base de Recursos Estáticos
    private static final String BASE_ESTATICA = "/WEB-INF/static";

    // Tamaño Maximo de Recurso - Ajusta en Producción
    private static final int LONGITUD_MAX = Integer.MAX_VALUE;

    // Localización del Fichero de Propiedades
    public static final String DEF_RECURSO_PRP = "org/japo/java/config/app.properties";

    public static File obtenerRecurso(HttpServletRequest request) throws IOException {
        // Referencia al Fichero
        File f = null;

        // Obtener Fichero
        try {
            // URL Peticion > Ruta Peticion
            String rutaPeticion = request.getPathTranslated().replace('\\', '/');

            // URL Peticion > Recurso Peticion
            String recursoPeticion = request.getPathInfo().replace('\\', '/');

            // Recurso Peticion + BASE > Recurso Servicio
            String recursoServicio = BASE_ESTATICA + recursoPeticion;

            // Ruta Peticion - Recurso Peticion : Recurso Servicio > Ruta Servicio
            String rutaServicio = rutaPeticion.replace(recursoPeticion, recursoServicio);

            // Ruta Servicio > Objeto File
            f = new File(rutaServicio);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        // Retorno Fichero
        return f;
    }

    public static void servirRecurso(File f, HttpServletResponse response)
            throws FileNotFoundException, IOException {
        // Validar Existencia Recurso
        if (!f.exists()) {
            // No hacer nada - Ignorar Petición
        } else if (f.length() > LONGITUD_MAX) {
            // No hacer nada - Ignorar Petición
        } else {
            try (
                    FileInputStream in = new FileInputStream(f);
                    ServletOutputStream out = response.getOutputStream()) {
                // Tipo MIME > Configuración Response
                response.setContentType(UtilesServlet.obtenerTipoMime(f.getName()));

                // Almacén de Bytes
                byte[] buffer = new byte[(int) f.length()];

                // Fichero Servidor > Buffer
                in.read(buffer);

                // Buffer > Secuencia Byte
                out.write(buffer);

                // Completar Transferencia
                out.flush();
            }
        }
    }

    public static final String obtenerTipoMime(String archivo) {
        // Extensiones de archivo
        String[] extensiones = {
            "txt", // Texto sin formato
            "csv", // Texto sin formato
            "pdf", // Archivo PDF 
            // ---           
            "htm", // Hipertexto (HTML)
            "html", // Hipertexto (HTML) 
            // ---           
            "jpg", // Imagen JPEG
            "png", // Imagen PNG
            "gif", // Imagen GIF
            "ico", // Imagen ICO
            "svg", // Imagen SVG
            // ---
            "css", // Hojas de estilo (CSS)
            "js", // JavaScript (ECMAScript)
            // ---
            "json", // Archivo JSON
            "xml", // Archivo XML
            // ---
            "ttf", // Fuente TrueType
            "otf", // Fuente OpenType
            "woff", // Formato de fuente abierta web (WOFF)
            "woff2" // Formato de fuente abierta web (WOFF)
        };

        // Tipos Mime
        String[] tiposMime = {
            "text/plain", // Texto sin formato
            "text/csv", // Valores separados por coma (CSV)
            "application/pdf", // Archivo PDF
            // ---
            "text/html", // Hipertexto (HTML)
            "text/html", // Hipertexto (HTML)
            // ---
            "image/jpg", // Imagen JPEG
            "image/png", // Imagen PNG
            "image/gif", // Imagen GIF
            "image/x-icon", // Imagen ICO
            "image/svg+xml", // Imagen SVG
            // ---
            "text/css", // Hojas de estilo (CSS)
            "application/js", // JavaScript (ECMAScript)
            // ---
            "application/json", // Archivo JSON
            "application/xml", // Archivo XML
            // ---
            "font/ttf", // Fuente TrueType
            "font/opentype", // Fuente OpenType
            "font/woff", // Formato de fuente abierta web (WOFF)
            "font/woff2" // Formato de fuente abierta web (WOFF)
        };

        // Tipo MIME por defecto
        String tipoMime = null;

        try {
            // Fichero > Extensión
            String extension = archivo.substring(archivo.lastIndexOf('.') + 1);

            // Extensión > Tipo Mime
            for (int i = 0; i < extensiones.length; i++) {
                if (extensiones[i].equalsIgnoreCase(extension)) {
                    tipoMime = tiposMime[i];
                    i = extensiones.length;
                }
            }

            // Tipo Mime NO Encontrado
            if (tipoMime == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            tipoMime = tiposMime[0];
        }

        // Devuelve Tipo Mime
        return tipoMime;
    }

    // Nombre de Comando (UCC) > Entidad Comando
    public static final Command obtenerComando(String cmdName) {
        // Paquete donde están los comandos
        final String PKG = "org.japo.java.bll.command";

        // Referencia del Comando
        Command cmd;

        try {
            // Nombre de Comando > Nombre Subpaquete
            String sub;
            if (false
                    || cmdName.equals("landing") || cmdName.equals("login")
                    || cmdName.equals("profile")
                    || cmdName.equals("signup") || cmdName.equals("logout")) {
                sub = "admin";
            } else if (cmdName.contains("-")) {
                sub = cmdName.substring(0, cmdName.lastIndexOf("-")).replaceAll("-", "") + "s";
                sub = sub.replaceAll("perfils", "perfiles");
                sub = sub.replaceAll("mains", "main");
            } else {
                sub = cmdName;
            }

            // Cambiar: Kebab Case > CamelCase
            cmdName = cambiarKebab2Camel(cmdName);

            // Nombre Comando + Nombre Paquete > Nombre Cualificado Clase de Comando
            String className = String.format("%s.%s.Command%s", PKG, sub, cmdName);

            // Nombre Cualificado de Clase de Comando > Clase Comando
            Class type = Class.forName(className).asSubclass(Command.class);

            // Clase Comando > Objeto Comando
            cmd = (Command) type.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | IllegalAccessException
                | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | SecurityException
                | InvocationTargetException | NullPointerException e) {
            cmd = new CommandUnknown();
        }

        // Retorna el Comando
        return cmd;
    }

    // Recurso Propiedades > Objeto Propiedades
    public static final Properties importarPropiedadesServlet(String recurso) {
        // Objeto de Propiedades Vacio
        Properties prp = new Properties();

        // Cargador Principal de Clases que tiene acceso a todos los recursos
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 
        try (InputStream is = classLoader.getResourceAsStream(recurso)) {
            prp.load(is);
//            System.out.println("Propiedades " + recurso + " Cargadas correctamente");
        } catch (Exception e) {
            System.out.println("ERROR: Acceso al recurso de propiedades " + recurso);
        }

        // Devolver Propiedades
        return prp;
    }

    // Recurso Propiedades ( Predefinido ) > Objeto Propiedades
    public static final Properties importarPropiedadesServlet() {
        return importarPropiedadesServlet(DEF_RECURSO_PRP);
    }

    public static final String cambiarKebab2Camel(String nombre) {
        // Nombre ( Kebab Case ) > Items
        String[] items = nombre.split("-");

        // Concatenador
        StringBuilder sb = new StringBuilder();

        // Items > Camel Case
        for (String i : items) {
            sb.append(Character.toUpperCase(i.charAt(0)));
            sb.append(i.substring(1).toLowerCase());
        }

        // Retorno: Camel Case
        return sb.toString();
    }

    public static IService obtenerServicio(String svcName) {
        // Paquete donde están los servicios
        final String PKG = "org.japo.java.bll.service";

        // Referencia del Servicio
        Service svc;

        try {
            // Nombre de Servicio > Nombre Subpaquete
            String sub;
            if (false
                    || svcName.equals("landing") || svcName.equals("login")
                    || svcName.equals("signup") || svcName.equals("logout")) {
                sub = "admin";
            } else if (!svcName.contains("-")) {
                sub = svcName;
            } else {
                sub = svcName.substring(0, svcName.lastIndexOf("-")).replaceAll("-", "") + "s";
            }

            // Cambiar: Kebab Case > CamelCase
            svcName = cambiarKebab2Camel(svcName);

            // Nombre Servicio + Nombre Paquete > Nombre Cualificado Clase de Servicio
            String className = String.format("%s.%s.Service%s", PKG, sub, svcName);

            // Nombre Cualificado de Clase de Servicio > Clase Servicio
            Class type = Class.forName(className).asSubclass(Service.class);

            // Clase Servicio > Objeto Servicio
            svc = (Service) type.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | IllegalAccessException
                | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | SecurityException
                | InvocationTargetException | NullPointerException e) {
            svc = new ServiceUnknown();
        }

        // Retorna el Servicio
        return svc;
    }
}
