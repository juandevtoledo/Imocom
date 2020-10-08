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
 * <strong>AplicaciÃ³n</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Dec 5, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "factura", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "FacturaVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"idFactura", "fechaVencimiento", "nroDiasVencimiento", "valorFactura", "moneda",
            "valorVencido", "centroCosto","valorPorVencer","asesor"})
public class FacturaVO implements Serializable {
    
    private String idFactura;
    private String fechaVencimiento;
    private String nroDiasVencimiento;
    private String valorFactura;
    private String moneda;
    private String valorVencido;
    private String centroCosto;
    private String valorPorVencer;
    private String asesor;

    /**
     * 
     * @return 
     */
    public String getIdFactura() {
        return idFactura;
    }

    /**
     * 
     * @param idFactura 
     */
    @XmlElement
    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * 
     * @return 
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * 
     * @param fechaVencimiento 
     */
    @XmlElement
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * 
     * @return 
     */
    public String getNroDiasVencimiento() {
        return nroDiasVencimiento;
    }

    /**
     * 
     * @param nroDiasVencimiento 
     */
    @XmlElement
    public void setNroDiasVencimiento(String nroDiasVencimiento) {
        this.nroDiasVencimiento = nroDiasVencimiento;
    }

    /**
     * 
     * @return 
     */
    public String getValorFactura() {
        return valorFactura;
    }

    /**
     * 
     * @param valorFactura 
     */
    @XmlElement
    public void setValorFactura(String valorFactura) {
        this.valorFactura = valorFactura;
    }

    /**
     * 
     * @return 
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * 
     * @param moneda 
     */
    @XmlElement
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /**
     * 
     * @return 
     */
    public String getValorVencido() {
        return valorVencido;
    }

    /**
     * 
     * @param valorVencido 
     */
    @XmlElement
    public void setValorVencido(String valorVencido) {
        this.valorVencido = valorVencido;
    }

    /**
     * 
     * @return 
     */
    public String getCentroCosto() {
        return centroCosto;
    }

    /**
     * 
     * @param centroCosto 
     */
    @XmlElement
    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }   

    public String getValorPorVencer() {
        return valorPorVencer;
    }

    public void setValorPorVencer(String valorPorVencer) {
        this.valorPorVencer = valorPorVencer;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }
    
    
}
