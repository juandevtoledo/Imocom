/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.view.auth;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Logger;
import weblogic.security.auth.login.UsernamePasswordLoginModule;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 8, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class JAASLoginModule extends UsernamePasswordLoginModule{
    
    private static final Logger logger = Logger.getLogger(JAASLoginModule.class);
    
    // configurable option
    private boolean debug = false;
    ;
    
    public JAASLoginModule() {
        super();
    }
    
    /**
     *
     * @param subject
     * @param callbackHandler
     * @param sharedState
     * @param options
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        super.initialize(subject, callbackHandler, options, options);
        logger.info("Inicializando JAAS LoginModule...");
        
        debug = "true".equalsIgnoreCase((String)options.get("debug"));
    }
    
    /**
     *
     * @return
     * @throws LoginException
     */
    @Override
    public boolean login() throws LoginException {
        logger.info("JAAS LoginModule login()...");
        return super.login();
    }
    
    /**
     *
     * @return
     * @throws LoginException
     */
    @Override
    public boolean abort() throws LoginException {
        return super.abort();
    }
    
    /**
     *
     * @return
     * @throws LoginException
     */
    @Override
    public boolean logout() throws LoginException {
        logger.info("JAAS LoginModule logout()...");
        return super.logout();
    }
}


