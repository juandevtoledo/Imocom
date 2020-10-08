/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.servlet;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ContextListener implements ServletContextListener {

    /**
     * Initialize log4j when the application is being started
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        System.out.println("ContextListener->contextInitialized->INIT");
        // initialize log4j here
        ServletContext context = event.getServletContext();
        //String log4jConfigFile = context.getInitParameter("log4j-config-location");
        //String fullPath = context.getResource("/WEB-INF/log4j.properties");//alPath("") + File.separator + log4jConfigFile;
        try {
            PropertyConfigurator.configure( context.getResource("/WEB-INF/log4j.properties") );
        } catch (MalformedURLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("ContextListener->contextInitialized-FIN");

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }
}
