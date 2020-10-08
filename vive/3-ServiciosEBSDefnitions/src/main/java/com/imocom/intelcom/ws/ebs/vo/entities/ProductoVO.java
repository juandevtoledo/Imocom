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
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "producto", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ProductoVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"codigo", "nombre","division","linea","tecnologia","familia","marca","cantidad","precioLista",
                    "moneda","descripcion","modelo","tipoProducto","valorUnitario","bodega","unidad",
                    "precioListaSinFormato","linkCaracteristicas","linkAccesorios","linkVideo","linkImg","organizationId",
                    "fecha","asesor"})
public class ProductoVO implements Serializable {
    
    private String codigo;
    private String nombre;
    private String division;
    private String linea;
    private String tecnologia;
    private String familia;
    private String marca;
    private String cantidad;
    private String precioLista;
    private String moneda;
    private String descripcion;
    private String modelo;
    private String tipoProducto;
    private String valorUnitario;
    private String bodega;
    private String unidad;
    private String precioListaSinFormato;
    private String linkCaracteristicas;
    private String linkAccesorios;
    private String linkVideo;
    private String linkImg;
    private String organizationId;
    private String fecha;
    private String asesor;
    
    public String getDivision() {
        return division;
    }
    @XmlElement
    public void setDivision(String division) {
        this.division = division;
    }

    public String getLinea() {
        return linea;
    }
    @XmlElement
    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getTecnologia() {
        return tecnologia;
    }
    @XmlElement
    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getFamilia() {
        return familia;
    }
    @XmlElement
    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getCantidad() {
        return cantidad;
    }
    @XmlElement
    public void setCantidad(String cantidad) {
          this.cantidad = cantidad;
    }

    public String getPrecioLista() {
        return precioLista;
    }
    @XmlElement
    public void setPrecioLista(String precioLista) {
        this.precioLista = precioLista;
    }

    public String getMoneda() {
        return moneda;
    }
    @XmlElement
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    
    
    /**
     * 
     * @return 
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * 
     * @param codigo 
     */
    @XmlElement
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre 
     */
    @XmlElement
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return 
     */
    public String getMarca() {
        return marca;
    }

    /**
     * 
     * @param marca 
     */
    @XmlElement
    public void setMarca(String marca) {
        this.marca = marca;
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

    public String getModelo() {
        return modelo;
    }
    @XmlElement
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }
    @XmlElement
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }
    @XmlElement
    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getBodega() {
        return bodega;
    }
    @XmlElement
    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getUnidad() {
        return unidad;
    }
    @XmlElement
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPrecioListaSinFormato() {
        return precioListaSinFormato;
    }
    
    @XmlElement
    public void setPrecioListaSinFormato(String precioListaSinFormato) {
        this.precioListaSinFormato = precioListaSinFormato;
    }

    public String getLinkCaracteristicas() {
        return linkCaracteristicas;
    }

    public void setLinkCaracteristicas(String linkCaracteristicas) {
        this.linkCaracteristicas = linkCaracteristicas;
    }

    public String getLinkAccesorios() {
        return linkAccesorios;
    }

    public void setLinkAccesorios(String linkAccesorios) {
        this.linkAccesorios = linkAccesorios;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }
}
