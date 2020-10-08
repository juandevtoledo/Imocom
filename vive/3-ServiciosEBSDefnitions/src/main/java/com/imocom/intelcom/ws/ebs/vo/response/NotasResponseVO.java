/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.NotaVO;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "NotasResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "NotasResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class NotasResponseVO {
    
    private List<NotaVO> notas; 

    public List<NotaVO> getNotas() {
        return notas;
    }
    
    @XmlElement
    public void setNotas(List<NotaVO> notas) {
        this.notas = notas;
    }
    
    
}
