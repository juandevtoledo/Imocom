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
@XmlRootElement(name = "ProductosEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ProductosEBS", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ProductosEBS implements Serializable {
    
    private String _idAsesor, _tipoProducto, _marca, _modelo, _descripcion, _idOportunidad, _idProducto;

    /**
     * 
     * @return 
     */
    public String getIdAsesor() {
        return _idAsesor;
    }

    /**
     * 
     * @param _idAsesor 
     */
    @XmlElement(name = "idAsesor", namespace = "")
    public void setIdAsesor(String _idAsesor) {
        this._idAsesor = _idAsesor;
    }

    /**
     * 
     * @return 
     */
    public String getTipoProducto() {
        return _tipoProducto;
    }

    /**
     * 
     * @param _tipoProducto 
     */
    @XmlElement(name = "tipoProducto", namespace = "")
    public void setTipoProducto(String _tipoProducto) {
        this._tipoProducto = _tipoProducto;
    }

    /**
     * 
     * @return 
     */
    public String getMarca() {
        return _marca;
    }

    /**
     * 
     * @param _marca
     */
    @XmlElement(name = "marca", namespace = "")
    public void setMarcaProducto(String _marca) {
        this._marca = _marca;
    }

    /**
     * 
     * @return 
     */
    public String getModelo() {
        return _modelo;
    }

    /**
     * 
     * @param _modelo 
     */
    @XmlElement(name = "modelo", namespace = "")
    public void setModeloProducto(String _modelo) {
        this._modelo = _modelo;
    }

    /**
     * 
     * @return 
     */
    public String getDescripcion() {
        return _descripcion;
    }

    /**
     * 
     * @param _descripcion
     */
    @XmlElement(name = "descripcion", namespace = "")
    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    /**
     * 
     * @return 
     */
    public String getIdOportunidad() {
        return _idOportunidad;
    }

    /**
     * 
     * @param _idOportunidad 
     */
    @XmlElement(name = "idOportunidad", namespace = "")
    public void setIdOportunidad(String _idOportunidad) {
        this._idOportunidad = _idOportunidad;
    }

    /**
     * 
     * @return 
     */
    public String getIdProducto() {
        return _idProducto;
    }

    /**
     * 
     * @param _idProducto 
     */
    @XmlElement(name = "idProducto", namespace = "")
    public void setIdProducto(String _idProducto) {
        this._idProducto = _idProducto;
    }
}
