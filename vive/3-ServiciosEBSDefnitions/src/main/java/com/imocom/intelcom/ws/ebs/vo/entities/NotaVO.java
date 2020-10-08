/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "nota", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "notaVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"idNota", "descripcion", "fechaCreacion","autor","tipoNota","nombreOportunidad"})
public class NotaVO {
    private String idNota;
    private String descripcion;
    private String fechaCreacion;
    private String autor;
    private String tipoNota;
    private String nombreOportunidad;

    public String getIdNota() {
        return idNota;
    }
    
    @XmlElement
    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public String getDescripcion() {
        return descripcion;
    }
    @XmlElement
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }
    @XmlElement
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAutor() {
        return autor;
    }
    @XmlElement
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTipoNota() {
        return tipoNota;
    }
    @XmlElement
    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }

    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }
    
    
}
