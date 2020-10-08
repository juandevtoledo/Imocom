/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.view.auth;


import com.imocom.intelcom.view.util.ViewException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 10, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class JAASHelper {
    
    LoginContext loginContext = null;
    private static final Logger logger = Logger.getLogger(JAASHelper.class);    
    
    public JAASHelper() {
    }
    
    /**
     * 
     * @param userid
     * @param encryptPasswd
     * @return
     * @throws ViewException 
     */
    public boolean authenticate(String userid, String encryptPasswd) throws ViewException {
        
        try {          
            logger.info("Tomando contexto JAAS (intelcom-jass)...");
            loginContext = new LoginContext("intelcom-jaas", new JAASLoginCallback(userid, encryptPasswd));
            
            logger.info("Ejecutando JAAS Login...");
            loginContext.login();
            return true;
        } catch (LoginException e) {
            throw new ViewException(e, Level.WARN);
        }catch (java.lang.LinkageError ex){
            logger.error("Error en proceso de Autenticacion: "+ex.getMessage());
            throw new ViewException("Se ha presentado un error durante la validación del usuario: " + userid, Level.WARN);
        }
    }
    
    /**
     *
     * @return
     */
    public Subject getSubject () {
        Subject result = null;
        if (null != loginContext) {
            logger.info("Tomando subject");
            result = loginContext.getSubject();
        }
        return result;
    }
    
    /**
     *
     * @return
     */
    public void logout() throws ViewException {
        if (null != loginContext) {
            try {
                logger.info("Ejecutando JAAS Logout...");
                loginContext.logout();
            } catch (LoginException ex) {
                logger.warn("Problemas al cerrar la sesión, detalles: " + ex.getMessage());
                throw new ViewException(ex, Level.WARN);
            }
        }
    }
}