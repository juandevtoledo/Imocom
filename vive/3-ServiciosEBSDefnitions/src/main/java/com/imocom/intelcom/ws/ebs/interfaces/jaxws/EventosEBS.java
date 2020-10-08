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
@XmlRootElement(name = "EventosEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "EventosEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class EventosEBS implements Serializable {
    
    private String correoOrigen;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String correosInvitados;
    private String fechaHoraInicio;
    private String fechaHoraFinalizacion;

    /**
     * 
     * @return 
     */
    public String getCorreoOrigen() {
        return correoOrigen;
    }

    /**
     * 
     * @param correoOrigen 
     */
    @XmlElement
    public void setCorreoOrigen(String correoOrigen) {
        this.correoOrigen = correoOrigen;
    }

    /**
     * 
     * @return 
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * 
     * @param titulo 
     */
    @XmlElement
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * 
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion 
     */
    @XmlElement
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return 
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * 
     * @param ubicacion 
     */
    @XmlElement
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * 
     * @return 
     */
    public String getCorreosInvitados() {
        return correosInvitados;
    }

    /**
     * 
     * @param correosInvitados 
     */
    @XmlElement
    public void setCorreosInvitados(String correosInvitados) {
        this.correosInvitados = correosInvitados;
    }

    /**
     * 
     * @return 
     */
    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * 
     * @param fechaHoraInicio 
     */
    @XmlElement
    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * 
     * @return 
     */
    public String getFechaHoraFinalizacion() {
        return fechaHoraFinalizacion;
    }

    /**
     * 
     * @param fechaHoraFinalizacion 
     */
    @XmlElement
    public void setFechaHoraFinalizacion(String fechaHoraFinalizacion) {
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
    }   
   
}
