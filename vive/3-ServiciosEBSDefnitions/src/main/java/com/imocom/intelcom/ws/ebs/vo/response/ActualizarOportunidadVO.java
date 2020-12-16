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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for actualizarOportunidadVO complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="actualizarOportunidadVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultadoOperacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "actualizarOportunidadVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "actualizarOportunidadVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ActualizarOportunidadVO {

     private String resultadoOperacion;

    public String getResultadoOperacion() {
        return resultadoOperacion;
    }

    @XmlElement(name = "resultadoOperacion")
    public void setResultadoOperacion(String resultadoOperacion) {
        this.resultadoOperacion = resultadoOperacion;
    }

}
