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

package com.imocom.intelcom.util.exceptions;

import java.io.Serializable;
import org.apache.log4j.Level;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Generic exception wrapper to application.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public abstract class GenericException extends Exception implements Serializable {
    
    private final Level level;
    
    /**
     * 
     * @param exception
     * @param level 
     */
    public GenericException(Exception exception, Level level) {
        super(exception);
        this.level = level;
    }
    
    /**
     * 
     * @param message
     * @param level 
     */
    public GenericException(String message, Level level) {
        super(message);
        this.level = level;
    }

    /**
     * 
     * @return 
     */
    public Level getLevel() {
        return level;
    }    
}
