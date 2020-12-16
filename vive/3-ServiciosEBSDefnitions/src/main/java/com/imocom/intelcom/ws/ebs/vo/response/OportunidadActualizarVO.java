/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author juan.toledo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oportunidadActualizarVO", propOrder = {
    "resultadoOperacion"
})
public class OportunidadActualizarVO {

    protected String resultadoOperacion;

    /**
     * Gets the value of the resultadoOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultadoOperacion() {
        return resultadoOperacion;
    }

    /**
     * Sets the value of the resultadoOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultadoOperacion(String value) {
        this.resultadoOperacion = value;
    }

}
