/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.response.OportunidadesBPMResponseVO;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "OportunidadesBPMResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadesBPMResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadesBPMResponse implements Serializable{
    
    private OportunidadesBPMResponseVO response;

    public OportunidadesBPMResponseVO getResponse() {
        return response;
    }
    
    @XmlElement(name = "return")
    public void setResponse(OportunidadesBPMResponseVO response) {
        this.response = response;
    }
    
}
