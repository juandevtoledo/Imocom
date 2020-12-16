/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author juan.toledo
 */
@XmlRootElement(name = "oportunidadesActualizarAsesorResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "oportunidadesActualizarAsesorResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadesActualizarAsesorResponseVO {

    private String resultadoOperacion;

    public String getResultadoOperacion() {
        return resultadoOperacion;
    }

    @XmlElement(name = "resultadoOperacion")
    public void setResultadoOperacion(String resultadoOperacion) {
        this.resultadoOperacion = resultadoOperacion;
    }
}
