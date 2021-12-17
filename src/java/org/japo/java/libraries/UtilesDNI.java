/* 
 * Copyright 2017 José A. Pacheco Ondoño - joanpaon@gmail.com.
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

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class UtilesDNI {

    // Secuencia letras DNI
    public static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

    // Expresión Regular FORMATO DNI ESPAÑOL SIN VALIDACION
    public static final String ER_DNI_ESP = "[0-9]{8}[" + LETRAS + "]";

    // Expresión Regular FORMATO DNI EXTRANJERO SIN VALIDACION
    public static final String ER_DNI_EXT = "[XYZ][0-9]{7}[" + LETRAS + "]";

    // Expresión Regular FORMATO DNI (ESPAÑOL + EXTRANJERO) SIN VALIDACION
    public static final String ER_DNI = ER_DNI_ESP + "|" + ER_DNI_EXT;

    // Calcula letra a partir del número de DNI
    public static char calcularLetraDNI(int dni) {
        return LETRAS.charAt(dni % LETRAS.length());
    }

    // Extraer número del DNI
    public static int extraerNumeroDNI(String dni) throws Exception {
        // Almacen del DNI extraido
        int numero;

        // Validar Formato DNI
        if (!UtilesValidacion.validarDato(dni, ER_DNI)) {
            throw new Exception("Formato erróneo de DNI");
        }

        // Extraer Prefijo Numérico
        String prefijo = "";
        if (dni.charAt(0) == 'X') {
            prefijo += '0' + dni.substring(1, dni.length() - 1);
        } else if (dni.charAt(0) == 'Y') {
            prefijo += '1' + dni.substring(1, dni.length() - 1);
        } else if (dni.charAt(0) == 'Z') {
            prefijo += '2' + dni.substring(1, dni.length() - 1);
        } else {
            prefijo += dni.substring(0, dni.length() - 1);
        }

        // Convierte el texto a entero
        numero = Integer.parseInt(prefijo);

        // Devuelve el DNI obtenido
        return numero;
    }

// Extraer letra del DNI
    public static char extraerLetraDNI(String dni) throws Exception {

        // Validar Formato DNI
        if (!UtilesValidacion.validarDato(dni, ER_DNI)) {
            throw new Exception("Formato erróneo de DNI");
        }

        // Devuelve Letra
        return dni.charAt(dni.length() - 1);
    }

    // Valida DNI - Formato texto
    public static boolean validarDNI(String dni) {
        // Semáforo de validación
        boolean dniOK = false;

        // Validar DNI
        try {
            // Extraer DNI
            int numero = extraerNumeroDNI(dni);

            // Extraer LETRA
            char letra = extraerLetraDNI(dni);

            // Análisis Concordancia
            dniOK = calcularLetraDNI(numero) == letra;
        } catch (Exception e) {
            System.out.println("ERROR: Formato DNI incorrecto");
        }
        
        // Resultado del análisis
        return dniOK;
    }
}
