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

package com.imocom.intelcom.view.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.Flash;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : JSF Utilities.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class JSFUtils implements Serializable {
    
    private static final Logger logger = Logger.getLogger(JSFUtils.class);
    private static final Properties props = new Properties();
    
    static {
        
        InputStream in;
        
        try {
            in = JSFUtils.class.getClassLoader().getResourceAsStream("redirect-view.properties");
            props.load(in);
            in.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    /**
     *
     * @param viewKey
     * @return
     */
    public static String getViewRedirect(String viewKey) {
        return props.getProperty(viewKey);
    }
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     *
     * @return {@link FacesContext}
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    /**
     *
     * @param msg
     *            {@link String}
     */
    public void performErrorMessages(String msg) {
        
        FacesContext facesContext = getFacesContext();
        if (facesContext == null) {
            return;
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", msg));
    }
    
    /**
     *
     * @param msg
     *            {@link String}
     */
    public void performWarningMessages(String msg) {
        FacesContext facesContext = getFacesContext();
        if (facesContext == null) {
            return;
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: ", msg));
    }
    
    /**
     *
     * @param msg
     *            {@link String}
     */
    public void performInfoMessages(String msg) {
        FacesContext facesContext = getFacesContext();
        if (facesContext == null) {
            return;
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: ", msg));
    }
    
    /**
     *
     * @return {@link Map}
     */
    public Map<String, String> getParameterMap() {
        return getFacesContext().getExternalContext().getRequestParameterMap();
    }
    
    /**
     *
     * @return {@link ELContext}
     */
    public ELContext getELContext() {
        return getFacesContext().getELContext();
    }
    
    /**
     *
     * @return {@link ExpressionFactory}
     */
    public ExpressionFactory getExpressionFactory() {
        Application application = getFacesContext().getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        
        return expressionFactory;
    }
    
    /**
     *
     * @return {@link Map}
     */
    public Map<String, Object> getSessionMap() {
        return getFacesContext().getExternalContext().getSessionMap();
    }
    
    public Object getObjectFromSessionMap(String nombreObjeto) {
         Map<String, Object> mapa = getSessionMap();
        return getSessionMap().get(nombreObjeto);
    }

    /**
     *
     * @return {@link HttpSession}
     */
    public HttpSession getHttpSession() {
        HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        return request.getSession();
    }
    
    /**
     *
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        return request;
    }
    
    /**
     *
     * @param page
     *            {@link String}
     * @throws IOException
     */
    public void redirect(String page) throws IOException {
        HttpServletRequest request = getRequest();
        getFacesContext().getExternalContext().redirect(request.getContextPath().concat(page));
    }
    
    /**
     *
     * @return {@link HttpServletRequest}
     */
    public Flash getFlash() {
        return getFacesContext().getExternalContext().getFlash();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return 
     */
    public static FacesContext getFacesContextFromServlet(HttpServletRequest request, HttpServletResponse response) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
 
            FacesContextFactory contextFactory  = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
 
            facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);
 
            // Set using our inner class
            InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);
 
            // set a new viewRoot, otherwise context.getViewRoot returns null
            UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
            facesContext.setViewRoot(view);               
        }
        return facesContext;
    }
    
    /**
     * 
     */
    private abstract static class InnerFacesContext extends FacesContext {
        
        /**
         * 
         * @param facesContext 
         */
        protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
    
}
