/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Marca;
import com.imocom.intelcom.persistence.entities.Modelo;
import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.services.interfaces.IMarcaServiceLocal;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.FILTRO_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MANTENER_FILTRO_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_PRODUCTO_CREAR_KEY;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private Long tipo;
    private Long idMarca;
    private Long idModelo;
    private Integer totalProductos;
    private List<Producto> productos;
    private List<Marca> marcas;
    private List<Modelo> modelos;
    private Producto productoSeleccionado;

    @EJB
    private IProductoServiceLocal iProductoServiceLocal;

    @EJB
    private IMarcaServiceLocal iMarcaServiceLocal;

    @EJB
    private ImodeloServiceLocal imodeloServiceLocal;

    @Override
    protected void build() {
        cargarMarcas();
    }

    private void cargarMarcas() {
        try {
            marcas = iMarcaServiceLocal.buscarMarcasPorLinea(userSession.getUsuario().getLinea());
        } catch (ServiceException ex) {
            logger.error("Error cargando marcas msg : " + ex.getMessage(), ex);
            marcas = new ArrayList<Marca>();
        }
    }

    public void cargaModelos() {
        try {
            modelos = imodeloServiceLocal.buscarModeloPorMarca(idMarca);
        } catch (ServiceException ex) {
            logger.error("Error cargando modelo msg : " + ex.getMessage(), ex);
            modelos = new ArrayList<Modelo>();
        }
    }

    public void buscarAction(ActionEvent actionEvent) {
        productos = new ArrayList<Producto>();
        try {
            logger.info("... Buscar Producto : descripcion :  " + descripcion + " tipo : " + tipo + " idMarca : " + idMarca + " idModelo " + idModelo);
            productos.addAll(iProductoServiceLocal.buscarProductos(userSession.getUsuario().getLinea(), descripcion, tipo, idMarca, idModelo));
        } catch (ServiceException ex) {
            logger.error("Error buscando producto :  " + ex.getMessage(), ex);
        }
        totalProductos = productos.size();
    }

    public String redirectEditar() {
        redirect(PAGE_PRODUCTO_CREAR_KEY, "redirectCrearProducto");
        return null;
    }

    public String redirectCrear() {
        productoSeleccionado=null;
        redirect(PAGE_PRODUCTO_CREAR_KEY, "redirectCrearProducto");
        return null;
    }

    private void redirect(String pageKey, String nombreMetodo) {
        String outcome = getViewRedirect(pageKey);
        try {
            logger.info("El producto seleccionado en " + nombreMetodo + " es --> " + ((productoSeleccionado != null) ? productoSeleccionado.getIdProducto() : "No seleccionado"));

            if (productoSeleccionado != null) {
                getSessionMap().put("PRODUCTO_SELECCIONADO", productoSeleccionado);
            } else {
                getSessionMap().remove("PRODUCTO_SELECCIONADO");
            }
            redirect(navigationFaces.navigation.get(outcome));
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
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

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

}
