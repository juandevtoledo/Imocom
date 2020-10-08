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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>AplicaciÃ³n</strong> : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Dec 5, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "cartera", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "CarteraVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"nitCliente", "nombreCliente", "cupoCredito", "tipoCliente", "cupoCreditoDisponible",
            "totalCartera","totalCarteraAsesor"})
public class CarteraVO implements Serializable {

    private String nitCliente;
    private String nombreCliente;
    private String cupoCredito;
    private String tipoCliente;
    private String cupoCreditoDisponible;
    private String totalCartera;
    private String totalCarteraAsesor;

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
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     *
     * @param nombreCliente
     */
    @XmlElement
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     *
     * @return
     */
    public String getCupoCredito() {
        return cupoCredito;
    }

    /**
     *
     * @param cupoCredito
     */
    @XmlElement
    public void setCupoCredito(String cupoCredito) {
        this.cupoCredito = cupoCredito;
    }

    /**
     *
     * @return
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     *
     * @param tipoCliente
     */
    @XmlElement
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     *
     * @return
     */
    public String getCupoCreditoDisponible() {
        return cupoCreditoDisponible;
    }

    /**
     *
     * @param cupoCreditoDisponible
     */
    @XmlElement
    public void setCupoCreditoDisponible(String cupoCreditoDisponible) {
        this.cupoCreditoDisponible = cupoCreditoDisponible;
    }

    /**
     *
     * @return
     */
    public String getTotalCartera() {
        return totalCartera;
    }

    /**
     *
     * @param totalCartera
     */
    @XmlElement
    public void setTotalCartera(String totalCartera) {
        this.totalCartera = totalCartera;
    }

    public String getTotalCarteraAsesor() {
        return totalCarteraAsesor;
    }
    @XmlElement
    public void setTotalCarteraAsesor(String totalCarteraAsesor) {
        this.totalCarteraAsesor = totalCarteraAsesor;
    }
    
    
}
