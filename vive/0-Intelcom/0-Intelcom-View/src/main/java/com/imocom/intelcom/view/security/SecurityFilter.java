/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*
*
*/

package com.imocom.intelcom.view.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Security component to handle access at application resources in order to user authorizations.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class SecurityFilter implements Filter {
    
    private static final Logger logger = Logger.getLogger(SecurityFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpServletResponse res = ((HttpServletResponse) response);
        
        try {
            
            String urlToRequest = ((HttpServletRequest)request).getRequestURI();
            logger.info("URL Soliticada.. mdf* : " + urlToRequest);
            
            UserSession userSession = (UserSession)((HttpServletRequest)request).getSession().getAttribute("userSession");
            
            if (userSession != null && userSession.isLoggedIn()) {
                
                if (SecurityConfig.getLoginPage().equals(urlToRequest)) {
                    sendRedirect(req, res, "/index_m.jsf?faces-redirect=true");
                    return;
                }
                
                logger.info("Solicitando autorización para URL solicitada... ");
                if (!userSession.getUserResourcesAllow().contains(urlToRequest) && !SecurityConfig.getFilterExcludedList().contains(urlToRequest)) {
                    String page = "/acceso-denegado.jsf";
                    sendRedirect(((HttpServletRequest)request), ((HttpServletResponse)response), page);
                }
                
                logger.info("Autorización para URL solicitada concedida ");
                chain.doFilter(request, response);
                
            } else {
                sendRedirect(req, res, "/authenticate/login.jsf?faces-redirect=true");
            }
            
        } catch (IOException t) {
            
            String messageError = "Se ha presentado un error indeterminado, detalles: " + t.getMessage();
            logger.error(messageError);
            
        } catch (Exception e) {
            
            String messageError = "Se ha presentado un error indeterminado, detalles: " + e.getMessage();
            logger.error(messageError);
            e.printStackTrace();
        }
        
    }
    
    /**
     *
     * @param request
     * @param response
     * @param urlToRequest
     * @throws IOException
     */
    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, String urlToRequest) throws IOException {
        String contextPath =  request.getContextPath();
        System.out.println("Context Path: "+contextPath);
        response.sendRedirect(contextPath + urlToRequest);
    }
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    
}
