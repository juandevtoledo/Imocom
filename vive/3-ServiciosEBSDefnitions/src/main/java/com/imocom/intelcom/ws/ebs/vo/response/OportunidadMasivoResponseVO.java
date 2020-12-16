/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadMasivoVO;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author juan.toledo
 */
@XmlRootElement(name = "OportunidadMasivoResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadMasivoResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadMasivoResponseVO {
    private String resultadoOperacion;
    private List<OportunidadMasivoVO> oportunidades;

    public String getResultadoOperacion() {
        return resultadoOperacion;
    }
    
    /**
     * 
     * @param resultadoOperacion 
     */
    @XmlElement(name = "resultadoOperacion")
    public void setResultadoOperacion(String resultadoOperacion) {
        this.resultadoOperacion = resultadoOperacion;
    }

    public List<OportunidadMasivoVO> getOportunidades() {
        return oportunidades;
    }
    
     /**
     * 
     * @param oportunidades 
     */
    @XmlElement(name = "oportunidades")
    public void setOportunidades(List<OportunidadMasivoVO> oportunidades) {
        this.oportunidades = oportunidades;
    }
    
    
}
