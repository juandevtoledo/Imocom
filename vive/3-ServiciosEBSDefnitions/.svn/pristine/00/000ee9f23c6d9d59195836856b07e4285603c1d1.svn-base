/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "OportunidadResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadResponseVO {
    
    private String resultadoOperacion;
    private List<OportunidadVO> oportunidades;

    
    /**
     * 
     * @return 
     */
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

    /**
     * 
     * @return 
     */
    public List<OportunidadVO> getOportunidades() {
        return oportunidades;
    }

    /**
     * 
     * @param oportunidades 
     */
    @XmlElement(name = "oportunidades")
    public void setOportunidades(List<OportunidadVO> oportunidades) {
        this.oportunidades = oportunidades;
    }
}
