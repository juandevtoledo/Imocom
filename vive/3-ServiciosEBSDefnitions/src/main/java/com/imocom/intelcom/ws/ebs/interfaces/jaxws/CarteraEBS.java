/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

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
@XmlRootElement(name = "CarteraEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "CarteraEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class CarteraEBS implements Serializable {
    
    private String nitCliente;
    private String idAsesor;

    /**
     * 
     * @return 
     */
    public String getNitCliente() {
        return nitCliente;
    }

    /**
     * 
     * @param nitCliente 
     */
    @XmlElement
    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    /**
     * 
     * @return 
     */
    public String getIdAsesor() {
        return idAsesor;
    }
    
    /**
     * 
     * @param idAsesor 
     */
    @XmlElement
    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }
    
    
}
