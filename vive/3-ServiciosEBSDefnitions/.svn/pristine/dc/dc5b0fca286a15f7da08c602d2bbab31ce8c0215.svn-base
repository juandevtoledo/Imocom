/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.entities.CotizacionesVO;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "CotizacionesBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "CotizacionesBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class CotizacionesBPM implements Serializable {
    
    private CotizacionesVO _request;

    public CotizacionesVO getRequest() {
        return _request;
    }
    
    @XmlElement(name = "request")
    public void setRequest(CotizacionesVO _request) {
        this._request = _request;
    }
    
}
