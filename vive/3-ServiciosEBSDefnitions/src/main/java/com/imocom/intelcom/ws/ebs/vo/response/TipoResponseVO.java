/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.TipoVO;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "TipoResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "TipoResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class TipoResponseVO {
    private List<TipoVO> tipos;

    public List<TipoVO> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoVO> tipos) {
        this.tipos = tipos;
    }    
    
}
