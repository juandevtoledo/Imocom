/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "OportunidadNotaVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadNotaVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"idOportunida", "notaOportunidad"})
public class OportunidadNotaVO implements Serializable{
    private String idOportunida;
    private String notaOportunidad;

    public String getIdOportunida() {
        return idOportunida;
    }

    public void setIdOportunida(String idOportunida) {
        this.idOportunida = idOportunida;
    }

    public String getNotaOportunidad() {
        return notaOportunidad;
    }

    public void setNotaOportunidad(String notaOportunidad) {
        this.notaOportunidad = notaOportunidad;
    }
    
    
}
