/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.exception;

import com.imocom.intelcom.ws.fault.IntelcomMiddlewareFault;
import javax.xml.ws.WebFault;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Dec 1, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@WebFault(name = "IntelcomMiddlewareFault", targetNamespace = "http://com.imocom.intelcom.ws.interfaces/")
public class IntelcomMiddlewareException extends Exception {

    private IntelcomMiddlewareFault fault;


    /**
     * @param fault
     */
    protected IntelcomMiddlewareException(IntelcomMiddlewareFault fault) {
        super(fault.getFaultInfo());
        this.fault = fault;
    }

    /**
     * @param message 
     * @param faultInfo
     */
    public IntelcomMiddlewareException(String message, IntelcomMiddlewareFault faultInfo) {
        super(message);
        this.fault = faultInfo;
    }

    /**
     * @param message 
     * @param faultInfo 
     * @param cause
     */
    public IntelcomMiddlewareException(String message, IntelcomMiddlewareFault faultInfo, Throwable cause) {
        super(message, cause);
        this.fault = faultInfo;
    }

    /**
     * 
     * @return
     */
    public IntelcomMiddlewareFault getFaultInfo() {
        return fault;
    }

    /**
     * @param message
     * 
     */
    public IntelcomMiddlewareException(String message) {
        super(message); 
    }
}