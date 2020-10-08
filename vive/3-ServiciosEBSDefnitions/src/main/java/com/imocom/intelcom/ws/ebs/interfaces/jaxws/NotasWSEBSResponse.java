/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.response.NotasResponseVO;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "NotasWSEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "NotasWSEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class NotasWSEBSResponse implements Serializable{
   
    private NotasResponseVO notasResponse;

    public NotasResponseVO getNotasResponse() {
        return notasResponse;
    }
    
    @XmlElement(name = "return")
    public void setNotasResponse(NotasResponseVO notasResponse) {
        this.notasResponse = notasResponse;
    }
    
    
}
