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
@XmlRootElement(name = "OportunidadesEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadesEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class OportunidadesEBS implements Serializable {
    
    private String idAsesor;
    private String nitCliente;
    private String probabilidadEjecucion;
    private String probabilidadExito;
    private String idEtapaOportunidad;

    public String getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getProbabilidadEjecucion() {
        return probabilidadEjecucion;
    }

    public void setProbabilidadEjecucion(String probabilidadEjecucion) {
        this.probabilidadEjecucion = probabilidadEjecucion;
    }

    public String getProbabilidadExito() {
        return probabilidadExito;
    }

    public void setProbabilidadExito(String probabilidadExito) {
        this.probabilidadExito = probabilidadExito;
    }

    public String getIdEtapaOportunidad() {
        return idEtapaOportunidad;
    }

    public void setIdEtapaOportunidad(String idEtapaOportunidad) {
        this.idEtapaOportunidad = idEtapaOportunidad;
    }
    
    
    
    
}
