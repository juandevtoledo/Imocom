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
import com.imocom.intelcom.persistence.entities.Moneda;
import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.TipoProducto;
import com.imocom.intelcom.services.interfaces.IMarcaServiceLocal;
import com.imocom.intelcom.services.interfaces.IMonedaServiceLocal;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ITipoProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import static com.imocom.intelcom.util.utility.Constants.PAGE_PRODUCTO_CONSULTAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_INCOTERM;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import static com.imocom.intelcom.view.vo.EstadoCotizacion.ACTIVO;
import static com.imocom.intelcom.view.vo.EstadoCotizacion.INACTIVO;
import static com.imocom.intelcom.view.vo.PerteneceValores.NO;
import static com.imocom.intelcom.view.vo.PerteneceValores.SI;
import com.imocom.intelcom.view.vo.ProductoVO;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@ManagedBean
@ViewScoped
public class CrearProductosFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CrearProductosFacesBean.class);
    private Long tipoProducto;
    private String codigo;
    private String descripcionProducto;
    private Long idMarca;
    private Long idModelo;
    private Long idMoneda;
    private List<Marca> marcas;
    private List<Modelo> modelos;
    private List<Moneda> monedas;
    private BigDecimal precio;
    private String cotizable;
    private String catalogoProducto;
    private List<String> pertenceValues;
    private List<String> estadoValues;
    private List<TipoProducto> tipoProductosList;
    private String tipoCreacion;
    private Long idMarcaCreacion;
    private String modeloCreacionTexto;
    private String marcaCreacionTexto;
    private String buttonName;
    private String incoterm;
    private List<Tipo> listaIncoterm;

    @EJB
    private IProductoServiceLocal iProductoServiceLocal;

    @EJB
    private IMarcaServiceLocal iMarcaServiceLocal;

    @EJB
    private ImodeloServiceLocal imodeloServiceLocal;

    @EJB
    private IMonedaServiceLocal iMonedaServiceLocal;

    @EJB
    private ITipoProductoServiceLocal iTipoProductoServiceLocal;

    @EJB
    private ITipoServiceLocal iTipoService;

    @Override
    protected void build() {

        productoSeleccionado();
        cargarMarcas();
        cargarMonedas();
        cargarCotizablesValues();
        cargarEstadosValues();
        cargarTipoProductos();
        cargarIncoterm();
    }
    private void cargarIncoterm() {
        try {
            listaIncoterm = iTipoService.findByTipoNombre(TIPO_INCOTERM);
        } catch (ServiceException ex) {
           logger.error("Error consultando incoterm " + ex.getMessage(), ex);
           listaIncoterm = new ArrayList<Tipo>();
        }
    }
    private void productoSeleccionado() {
        if (getSessionMap().containsKey("PRODUCTO_SELECCIONADO")) {
            buttonName = "editar";
            Producto producto = (Producto) getSessionMap().get("PRODUCTO_SELECCIONADO");
            codigo = producto.getCodigo();
            descripcionProducto = producto.getDescripcion();
            precio = producto.getPrecio();
            tipoProducto = producto.getIdTipo().getIdTipo();
            idMarca = producto.getIdModelo().getIdMarca().getIdMarca();
            idModelo = producto.getIdModelo().getIdModelo();
            idMoneda = producto.getIdMoneda().getIdMoneda().longValue();
            incoterm = producto.getIncoterm();
            cotizable = "NO";
            if (producto.getCotizable().intValue() == 1) {
                cotizable = "SI";
            }
            catalogoProducto = "NO";
            if (producto.getCatalogo().intValue() == 1) {
                catalogoProducto = "SI";
            }
            cargarModelos();
        } else {
            buttonName = "crear";
        }
    }

    public void enviarAction() {

        if (procesarCreacion()) {
            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_PRODUCTO_CONSULTAR_KEY);
            try {
                redirect(navigationFaces.navigation.get(outcome));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        } else {
            performErrorMessages("Ha ocurrido un problema la solicitud de creación de productos, no ha podido ser enviada ");
        }

    }

    private boolean procesarCreacion() {
        logger.info(".... Enviando Solicitud de creacion de productos ...  ");
        try {

            ProductoVO.Builder productoVOBuilder = new ProductoVO.Builder()
                    .tipoProducto(1l)
                    .descripcion(this.descripcionProducto)
                    .idModelo(this.idModelo)
                    .idMoneda(this.idMoneda)
                    .descripcion(this.descripcionProducto)
                    .precio(this.precio)
                    .cotizable(this.cotizable)
                    .estado("ACTIVO")
                    .catalogo(this.catalogoProducto)
                    .incoterm(this.incoterm);

            if (buttonName.equals("crear")) {
                ProductoVO productoVO = productoVOBuilder.build();
                Producto productoCreado = iProductoServiceLocal.crear(productoVO.toProducto());
                logger.info("Producto Creado : " + productoCreado.getIdProducto());
                performInfoMessages("Producto Creado : " + productoCreado.getIdProducto());
                return true;
            } else {
                Producto productoSelecct = (Producto) getSessionMap().get("PRODUCTO_SELECCIONADO");
                productoVOBuilder.idProducto(productoSelecct.getIdProducto());
                ProductoVO productoVO = productoVOBuilder.build();
                Producto productoEditado = iProductoServiceLocal.editar(productoVO.toProducto());
                logger.info("Editar Producto : " + productoEditado.getIdProducto());
                performInfoMessages("Producto Editar : " + productoEditado.getIdProducto());
                return true;
            }

        } catch (ServiceException ex) {
            logger.error("Error creando producto " + ex.getMessage(), ex);
            performErrorMessages("Ha creando producto en base de datos: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            logger.error("Error creando producto " + ex.getMessage(), ex);
            performErrorMessages("Error creando producto : " + ex.getMessage());
            return false;
        }
    }

    public void selectCreacionMarcasModelos() {
        tipoCreacion = "marca";
    }

    public void creacionMarcas() {

        if (tipoCreacion.equals("marca")) {
            try {
                logger.info("...  Creacion marcas marca ... -> " + this.marcaCreacionTexto);
                Marca marca = new Marca();
                marca.setLinea(userSession.getUsuario().getLinea());
                marca.setNombre(this.marcaCreacionTexto);
                Marca marcaCreacion = iMarcaServiceLocal.crearMarca(marca);
                logger.info("...  Creacion marcas creacion   " + marcaCreacion.getIdMarca());
            } catch (ServiceException ex) {
                logger.error("error creacion de marcas :  " + ex.getMessage(), ex);
            }

        } else if (tipoCreacion.equals("modelo")) {
            logger.info("...  Creacion marcas modelo ... ");
            try {
                logger.info("...  Creacion marcas marca ... ");
                Modelo modelo = new Modelo();
                modelo.setIdMarca(new Marca(this.idMarcaCreacion));
                modelo.setNombre(this.modeloCreacionTexto);
                Modelo modeloCreacion = imodeloServiceLocal.crearModelo(modelo);
                logger.info("...  Creacion modelo creacion   " + modeloCreacion.getIdModelo());
            } catch (ServiceException ex) {
                logger.error("error creacion de modelos :  " + ex.getMessage(), ex);
            }
        }
        cargarMarcas();
        cargarModelos();
    }

    private void cargarMonedas() {
        try {
            monedas = iMonedaServiceLocal.buscarMonedas();
        } catch (ServiceException ex) {
            logger.error("Error cargando marcas msg : " + ex.getMessage(), ex);
            marcas = new ArrayList<Marca>();
        }
    }

    private void cargarMarcas() {
        try {
            marcas = iMarcaServiceLocal.buscarMarcasPorLinea(userSession.getUsuario().getLinea());
        } catch (ServiceException ex) {
            logger.error("Error cargando marcas msg : " + ex.getMessage(), ex);
            marcas = new ArrayList<Marca>();
        }
    }

    public void cargarModelos() {
        try {
            modelos = imodeloServiceLocal.buscarModeloPorMarca(idMarca);
        } catch (ServiceException ex) {
            logger.error("Error cargando modelo msg : " + ex.getMessage(), ex);
            modelos = new ArrayList<Modelo>();
        }
    }

    public void cargarCotizablesValues() {
        pertenceValues = new ArrayList<String>();
        pertenceValues.add(SI.name());
        pertenceValues.add(NO.name());
    }

    public void cargarEstadosValues() {
        estadoValues = new ArrayList<String>();
        estadoValues.add(ACTIVO.name());
        estadoValues.add(INACTIVO.name());
    }

    public void cargarTipoProductos() {
        try {
            tipoProductosList = iTipoProductoServiceLocal.buscarTipoProducto();
        } catch (ServiceException ex) {
            logger.error("Error cargando tipo producto msg : " + ex.getMessage(), ex);
            tipoProductosList = new ArrayList<TipoProducto>();
        }
    }

    public Boolean getIsMarca() {
        if (tipoCreacion != null) {
            return this.tipoCreacion.equals("marca");
        }
        return true;
    }

    public Boolean getIsModelo() {
        if (tipoCreacion != null) {
            return this.tipoCreacion.equals("modelo");
        }
        return false;
    }

    public Long getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(Long tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
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

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public List<Moneda> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Moneda> monedas) {
        this.monedas = monedas;
    }

    public Long getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Long idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getCotizable() {
        return cotizable;
    }

    public void setCotizable(String cotizable) {
        this.cotizable = cotizable;
    }

    public String getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(String catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public List<String> getPertenceValues() {
        return pertenceValues;
    }

    public void setPertenceValues(List<String> pertenceValues) {
        this.pertenceValues = pertenceValues;
    }

    public List<String> getEstadoValues() {
        return estadoValues;
    }

    public void setEstadoValues(List<String> estadoValues) {
        this.estadoValues = estadoValues;
    }

    public List<TipoProducto> getTipoProductosList() {
        return tipoProductosList;
    }

    public void setTipoProductosList(List<TipoProducto> tipoProductosList) {
        this.tipoProductosList = tipoProductosList;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoCreacion() {
        return tipoCreacion;
    }

    public void setTipoCreacion(String tipoCreacion) {
        this.tipoCreacion = tipoCreacion;
    }

    public Long getIdMarcaCreacion() {
        return idMarcaCreacion;
    }

    public void setIdMarcaCreacion(Long idMarcaCreacion) {
        this.idMarcaCreacion = idMarcaCreacion;
    }

    public String getModeloCreacionTexto() {
        return modeloCreacionTexto;
    }

    public void setModeloCreacionTexto(String modeloCreacionTexto) {
        this.modeloCreacionTexto = modeloCreacionTexto;
    }

    public String getMarcaCreacionTexto() {
        return marcaCreacionTexto;
    }

    public void setMarcaCreacionTexto(String marcaCreacionTexto) {
        this.marcaCreacionTexto = marcaCreacionTexto;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public List<Tipo> getListaIncoterm() {
        return listaIncoterm;
    }

    public void setListaIncoterm(List<Tipo> listaIncoterm) {
        this.listaIncoterm = listaIncoterm;
    }

}
