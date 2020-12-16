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
 * @author juan.toledo
 */
@XmlRootElement(name = "oportunidad", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "OportunidadMasivoVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"idOportunidad", "nombreOportunidad", "descripcion", "etapaOportunidad", "nitCliente",
            "nombreCliente", "idAsesor", "tipoOportunidad", "incoterm", "moneda", "monto", "fechaCierre", "probabilidadEjecucion",
            "probabilidadExito", "canalEntrada",  "estadoOportunidad", "fechaCierreMostrar",
            "marca","modelo","producto"})
public class OportunidadMasivoVO {
    
    private String idOportunidad;
    private String nombreOportunidad;
    private String descripcion;
    private String etapaOportunidad;
    private String nitCliente;
    private String nombreCliente;
    private String idAsesor;
    private String tipoOportunidad;
    private String incoterm;
    private String moneda;
    private String monto;
    private String fechaCierre;
    private String probabilidadEjecucion;
    private String probabilidadExito;
    private String canalEntrada;
    private String estadoOportunidad;
    private String fechaCierreMostrar;
    private String marca;
    private String modelo;
    private String producto;
    
    public String getIdOportunidad() {
        return idOportunidad;
    }
    
    @XmlElement
    public void setIdOportunidad(String idOportunidad) {
        this.idOportunidad = idOportunidad;
    }

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }
    
    @XmlElement
    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    @XmlElement
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtapaOportunidad() {
        return etapaOportunidad;
    }
    
    @XmlElement
    public void setEtapaOportunidad(String etapaOportunidad) {
        this.etapaOportunidad = etapaOportunidad;
    }

    public String getNitCliente() {
        return nitCliente;
    }
    
    @XmlElement
    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    
    @XmlElement
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdAsesor() {
        return idAsesor;
    }
    
    @XmlElement
    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getTipoOportunidad() {
        return tipoOportunidad;
    }
    
    @XmlElement
    public void setTipoOportunidad(String tipoOportunidad) {
        this.tipoOportunidad = tipoOportunidad;
    }

    public String getIncoterm() {
        return incoterm;
    }
    
    @XmlElement
    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getMoneda() {
        return moneda;
    }
    
    @XmlElement
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMonto() {
        return monto;
    }
    
    @XmlElement
    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }
    
    @XmlElement
    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getProbabilidadEjecucion() {
        return probabilidadEjecucion;
    }
    
    @XmlElement
    public void setProbabilidadEjecucion(String probabilidadEjecucion) {
        this.probabilidadEjecucion = probabilidadEjecucion;
    }

    public String getProbabilidadExito() {
        return probabilidadExito;
    }
    
    @XmlElement
    public void setProbabilidadExito(String probabilidadExito) {
        this.probabilidadExito = probabilidadExito;
    }

    public String getCanalEntrada() {
        return canalEntrada;
    }
    
    @XmlElement
    public void setCanalEntrada(String canalEntrada) {
        this.canalEntrada = canalEntrada;
    }

    public String getEstadoOportunidad() {
        return estadoOportunidad;
    }
    
    @XmlElement
    public void setEstadoOportunidad(String estadoOportunidad) {
        this.estadoOportunidad = estadoOportunidad;
    }

    public String getFechaCierreMostrar() {
        return fechaCierreMostrar;
    }
    
    @XmlElement
    public void setFechaCierreMostrar(String fechaCierreMostrar) {
        this.fechaCierreMostrar = fechaCierreMostrar;
    }

    public String getMarca() {
        return marca;
    }
    
    @XmlElement
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    
    @XmlElement
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProducto() {
        return producto;
    }
    
    @XmlElement
    public void setProducto(String producto) {
        this.producto = producto;
    }

    
}
