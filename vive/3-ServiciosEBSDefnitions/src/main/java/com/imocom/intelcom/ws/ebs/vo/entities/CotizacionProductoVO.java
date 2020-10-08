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
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Guzman
 */
@XmlRootElement(name = "CotizacionProductoVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "CotizacionProductoVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"idCotizacionProducto", "idProducto", "cantidad", "valorUnitario","nombreProducto","marca","modelo","tipoProducto","centroCostos","bodega","unidad"})
public class CotizacionProductoVO implements Serializable{
    String idCotizacionProducto;
    String idProducto;
    String cantidad;
    String valorUnitario;
    String nombreProducto;
    String marca;
    String modelo;
    String tipoProducto;
    String centroCostos;
    String bodega;
    String unidad;
    private List<CotizacionProductoVO> cotizacionProducto;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    

    public String getIdCotizacionProducto() {
        return idCotizacionProducto;
    }

    public void setIdCotizacionProducto(String idCotizacionProducto) {
        this.idCotizacionProducto = idCotizacionProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    
}
