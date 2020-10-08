/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.entities.TipoProducto;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@ManagedBean
@ViewScoped
public class ConsultaProductoFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ConsultaProductoFacesBean.class);

    private String descripcion;
    private String tipo;
    private String marca;
    private String modelo;
    private Integer totalProductos;
    private List<Producto> productos;

    @EJB
    private IProductoServiceLocal iProductoServiceLocal;

    @Override
    protected void build() {
    }

    public void buscarAction(ActionEvent actionEvent) {
        productos = new ArrayList<Producto>();
        try {
            logger.info("... Buscar Producto : " + descripcion);
            productos.addAll(iProductoServiceLocal.buscarProductos(descripcion));
        } catch (ServiceException ex) {
            logger.error("Error buscando producto :  " + ex.getMessage(), ex);
        }
        totalProductos = productos.size();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(Integer totalProductos) {
        this.totalProductos = totalProductos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

}
