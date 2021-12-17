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
package org.japo.java.pll.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.japo.java.libraries.UtilesServlet;
import org.japo.java.pll.command.ICommand;
import org.japo.java.pll.service.IService;

/**
 *
 * @author José A. Pacheco Ondoño - japolabs@gmail.com
 */
@WebServlet(name = "ControllerFront", urlPatterns = {"/controller"})
public final class ControllerFront extends HttpServlet {

    // Semáforo Debug
    private static final boolean CHECK_CODE_500 = false;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        if (CHECK_CODE_500) {
            throw new ServletException();
//        throw new IOException();
//        throw new NullPointerException();
        }
        
        // Petición > Nombre de Servicio
        String svcName = request.getParameter("svc");

        // Validación de Nombre de Servicio
        if (svcName != null) {
            // Nombre de Servicio > Servicio ( Interfaz )
            IService svc = UtilesServlet.obtenerServicio(svcName);

            // ServletContext + Petición + Resuesta > Inicializar Servicio
            svc.init(getServletContext(), request, response);

            // Procesa Servicio
            svc.process();

        } else {
            // Petición > Nombre de Comando (Kebab Case)
            String cmdName = request.getParameter("cmd");

            // Nombre de Comando > Comando ( Interfaz )
            ICommand cmd = UtilesServlet.obtenerComando(cmdName);

            // ServletContext + Peticion + Resuesta > Inicializar Comando
            cmd.init(getServletContext(), request, response);

            // Procesa Comando
            cmd.process();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
