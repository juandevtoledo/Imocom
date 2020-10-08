/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "OportunidadesBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadesBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadesBPM implements Serializable{
    
    private OportunidadBPMVO _request;

    public OportunidadBPMVO getRequest() {
        return _request;
    }
    
    @XmlElement(name = "request")
    public void setRequest(OportunidadBPMVO _request) {
        this._request = _request;
    }
    
}
