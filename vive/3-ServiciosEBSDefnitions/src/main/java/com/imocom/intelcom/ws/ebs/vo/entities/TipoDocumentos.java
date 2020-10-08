/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "tipoDocumentos", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "tipoDocumentos",namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", propOrder = {
    "idContentDocumento",
    "tituloDocumento"
})
public class TipoDocumentos {
     private String idContentDocumento;
     private String tituloDocumento;

    public String getIdContentDocumento() {
        return idContentDocumento;
    }

    public void setIdContentDocumento(String idContentDocumento) {
        this.idContentDocumento = idContentDocumento;
    }

    public String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }
     
     
}
