/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.interfaces.jaxws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 15, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "doProcess", namespace = "http://com.imocom.intelcom.ws.interfaces/")
@XmlType(name = "MiddlewareService", namespace = "http://com.imocom.intelcom.ws.interfaces/")
public class MiddlewareService implements Serializable {

        private MiddlewareServiceRequestVO _request;
    
    /**
     *
     * @return
     */
    public MiddlewareServiceRequestVO getRequest() {
        return _request;
    }
    
    /**
     *
     * @param _request
     */
    @XmlElement(name = "request", namespace = "")
    public void setRequest(MiddlewareServiceRequestVO _request) {
        this._request = _request;
    } 
    
}
