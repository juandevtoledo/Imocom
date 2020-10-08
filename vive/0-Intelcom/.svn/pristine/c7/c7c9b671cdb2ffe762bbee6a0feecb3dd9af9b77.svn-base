/*
* Copyright (c) 2014 FONADE. All Rights Reserved.
*
* This software is the confidential and proprietary information of FONADE.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of FONADE.
*/

package com.imocom.intelcom.view.servlet;

import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.REDIRECT_PARAM;
import com.imocom.intelcom.view.security.NavigationFaces;
import com.imocom.intelcom.view.util.JSFUtils;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cguzman
 */
public class GeoEnlacesSevlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String outcome = request.getParameter(REDIRECT_PARAM);
        String idCliente = request.getParameter(CLIENT_ID_PARAM);
        
        if (idCliente == null) {           
            FacesContext facesContext = JSFUtils.getFacesContextFromServlet(request, response);
            NavigationFaces navigation = (NavigationFaces) facesContext.getExternalContext().getApplicationMap().get("navigationFaces");
            String page = navigation.navigation.get(outcome);
            response.sendRedirect(request.getContextPath().concat(page));
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
