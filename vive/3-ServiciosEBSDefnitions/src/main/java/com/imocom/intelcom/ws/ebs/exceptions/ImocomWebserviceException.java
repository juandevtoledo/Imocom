/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.ebs.exceptions;


import com.imocom.intelcom.ws.ebs.support.ImocomWebFault;
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
@WebFault(name = "ImocomWebFault", targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ImocomWebserviceException extends Exception {

    private ImocomWebFault fault;


    /**
     * @param fault
     */
    protected ImocomWebserviceException(ImocomWebFault fault) {
        super(fault.getFaultString());
        this.fault = fault;
    }

    /**
     * @param message 
     * @param faultInfo
     */
    public ImocomWebserviceException(String message, ImocomWebFault faultInfo) {
        super(message);
        this.fault = faultInfo;
    }

    /**
     * @param message 
     * @param faultInfo 
     * @param cause
     */
    public ImocomWebserviceException(String message, ImocomWebFault faultInfo, Throwable cause) {
        super(message, cause);
        this.fault = faultInfo;
    }

    /**
     * 
     * @return
     */
    public ImocomWebFault getFaultInfo() {
        return fault;
    }

    /**
     * @param message
     * 
     */
    public ImocomWebserviceException(String message) {
        super(message); 
    }
}