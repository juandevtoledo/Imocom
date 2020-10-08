/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.view.security;

import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.view.util.ViewException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Component to control application access (Authentication and Authorization).
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@ManagedBean
@RequestScoped
public class LoginFacesBean extends AbstractFacesBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(LoginFacesBean.class);
    
    private String username;
    private String password;
    
    /**
     * Creates a new instance of LoginFacesBean
     */
    public LoginFacesBean() {
    }
    
    /**
     *
     * @return
     */
    public String login() {        
        
        try {
            logger.info("Iniciando Proceso DE Autenticación Usuarios!!!!!s...");
            boolean requiredValidation = true;
            
            if (username == null || "".equals(username)) {
                logger.info("El campo 'Nombre de usuario' es requerido");
                performErrorMessages("El campo 'Nombre de usuario' es requerido");
                requiredValidation = false;
            }
            
            if (password == null || "".equals(password)) {
                logger.info("El campo 'Contraseña' es requerido");
                performErrorMessages("El campo 'Contraseña' es requerido");
                requiredValidation = false;
            }
            
            if (!requiredValidation)
                return null;
                
            logger.info("Tomando módulo JAAS para autenticación");          
            userSession.validate(username.toUpperCase(), password);
            return super.redirectTo("outcome:index");
            
        } catch (ViewException ex) {
            logger.error("Nombre de usuario ó Contraseña inválidos, detalles " + ex.getMessage());
            performErrorMessages("Nombre de usuario ó Contraseña inválidos");
        }catch (java.lang.LinkageError ex){
            logger.error("Nombre de usuario ó Contraseña inválidos LoginFacesBean, detalles " + ex.getMessage());
            performErrorMessages("Nombre de usuario ó Contraseña inválidos");
        }
        catch (Exception ex) {
            logger.error("Nombre de usuario ó Contraseña inválidos, detalles " + ex.getMessage());
            performErrorMessages("Nombre de usuario ó Contraseña inválidos");
        }
        
        return null;
    }
    
    public void build() {}
    
    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }
    
    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }
    
    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
