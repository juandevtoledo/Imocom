package com.imocom.intelcom.ws.ebs.support;
/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.l
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Dec 1, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImocomWebFault", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", propOrder = {
    "faultCode", "faultString"
})
public class ImocomWebFault {
    
    /**
     * * Fault Code
     */
    private String faultCode;
    /**
     * * Fault String
     */
    private String faultString;
    
    /**
     * * @return the faultCode
     */
    public String getFaultCode() {
        return faultCode;
    }
    
    /**
     * * @param faultCode the faultCode to set
     */
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
    
    /**
     * * @return the faultString
     */
    public String getFaultString() {
        return faultString;
    }
    
    /**
     * * @param faultString the faultString to set
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
