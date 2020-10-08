/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import java.io.Serializable;
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
@XmlRootElement(name = "ProcesosBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ProcesosBPM", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ProcesosBPM implements Serializable {
    
    private ResultadoVisitaVO _request;

    /**
     * 
     * @return 
     */
    public ResultadoVisitaVO getRequest() {
        return _request;
    }

    /**
     * 
     * @param _request 
     */
    @XmlElement(name = "request")
    public void setRequest(ResultadoVisitaVO _request) {
        this._request = _request;
    }
}
