/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PRODUCTO;
import static com.imocom.intelcom.util.utility.Constants.WS_EBS_WS_MARCA_X_LINEA;
import static com.imocom.intelcom.util.utility.Constants.WS_EBS_WS_MODEL_X_MARCA;
import static com.imocom.intelcom.util.utility.Constants.WS_INVENTARIO_CONSULTA;
import static com.imocom.intelcom.util.utility.Constants.WS_INVENTARIO_CONSULTA_SIN_BODEGA;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_COTAUTOMATICA;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_COTIZABLE_AUTOMATICAMENTE;
import static com.imocom.intelcom.util.utility.Constants.WS_EBS_MODEL_X_MARCA_INVENTARIO;
import static com.imocom.intelcom.util.utility.Constants.WS_EBS_CONSULTA_PRODUCTOS_INVENTARIO;

import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.TipoVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.TipoResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class InventarioConsultaFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ClientesNombreNitFacesBean.class);
    private static final long serialVersionUID = 1L;

    private String tipoProducto;
    private String marca;
    private String modelo;
    private String descripcion;
    private String linea;
    private List<Tipo> listaTiposProducto;
    private List<TipoVO> listaMarcas;
    private List<TipoVO> listaModelos;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;

    private MiddlewareServiceRequestVO requestMarcas;
    private int numeroParametrosWSMarcas = 0;

    private MiddlewareServiceRequestVO requestConsultaInventario;
    private int numeroParametrosConsultaInventarioWS = 0;

    private MiddlewareServiceRequestVO ConsultaInventarioAutomatica;
    private int numeroParametrosConsultaInventarioAutomaticaWS = 0;

    private MiddlewareServiceRequestVO ProductoCotizableAutomaticamente;
    private int numeroParametrosProductoCotizableAutomatiamenteWS = 0;

    private MiddlewareServiceRequestVO requestModelos;
    private int numeroParametrosWSModelos = 0;
    
    private MiddlewareServiceRequestVO requestModelosPorMarcaInventario;
    private int numeroParametrosWSModelosPorMarcaInventario = 0;
    
    private MiddlewareServiceRequestVO requestConsultaProductosInventarioEBS;
    private int numeroParametrosconsultaProductosInventarioEBSWS = 0;

    private List<ProductoVO> listaProductos;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private ITipoServiceLocal iTipoService;

    @Override
    protected void build() {
        try {
            //Consulta de tipos de Producto
            listaTiposProducto = iTipoService.findByTipoNombre(TIPO_PRODUCTO);
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

        //Armamos el objeto request
        armarRequestWS();
    }

    private void armarRequestWS() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_INVENTARIO_CONSULTA);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWS = servicio.getNumeroParametros();

            requestConsultaInventario = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_INVENTARIO_CONSULTA_SIN_BODEGA);
            requestConsultaInventario.setEndpoint(servicio.getInterfazEndpoint());
            requestConsultaInventario.setMethod(servicio.getMetodo());
            requestConsultaInventario.setNamespacePort(servicio.getNamespace());
            requestConsultaInventario.setServiceName(servicio.getQnameServicio());
            requestConsultaInventario.setWsdlUrl(servicio.getUrlWsdl());
            requestConsultaInventario.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosConsultaInventarioWS = servicio.getNumeroParametros();

            requestMarcas = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioMarca = iServiciosESB.findByUniqueName(WS_EBS_WS_MARCA_X_LINEA);
            requestMarcas.setEndpoint(servicioMarca.getInterfazEndpoint());
            requestMarcas.setMethod(servicioMarca.getMetodo());
            requestMarcas.setNamespacePort(servicioMarca.getNamespace());
            requestMarcas.setServiceName(servicioMarca.getQnameServicio());
            requestMarcas.setWsdlUrl(servicioMarca.getUrlWsdl());
            requestMarcas.setInterfaceType(servicioMarca.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWSMarcas = servicioMarca.getNumeroParametros();

            requestModelos = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioModelo = iServiciosESB.findByUniqueName(WS_EBS_WS_MODEL_X_MARCA);
            requestModelos.setEndpoint(servicioModelo.getInterfazEndpoint());
            requestModelos.setMethod(servicioModelo.getMetodo());
            requestModelos.setNamespacePort(servicioModelo.getNamespace());
            requestModelos.setServiceName(servicioModelo.getQnameServicio());
            requestModelos.setWsdlUrl(servicioModelo.getUrlWsdl());
            requestModelos.setInterfaceType(servicioModelo.getTipoInterfaz());
            //Cargamos el número de ServiciosEbs
            numeroParametrosWSModelos = servicioModelo.getNumeroParametros();

            ConsultaInventarioAutomatica = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioProdAuto = iServiciosESB.findByUniqueName(WS_PRODUCTO_COTAUTOMATICA);
            ConsultaInventarioAutomatica.setEndpoint(servicioProdAuto.getInterfazEndpoint());
            ConsultaInventarioAutomatica.setMethod(servicioProdAuto.getMetodo());
            ConsultaInventarioAutomatica.setNamespacePort(servicioProdAuto.getNamespace());
            ConsultaInventarioAutomatica.setServiceName(servicioProdAuto.getQnameServicio());
            ConsultaInventarioAutomatica.setWsdlUrl(servicioProdAuto.getUrlWsdl());
            ConsultaInventarioAutomatica.setInterfaceType(servicioProdAuto.getTipoInterfaz());
            //Cargamos el número de ServiciosEbs
            numeroParametrosConsultaInventarioAutomaticaWS = servicioProdAuto.getNumeroParametros();

            ProductoCotizableAutomaticamente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioProdCotAuto = iServiciosESB.findByUniqueName(WS_PRODUCTO_COTIZABLE_AUTOMATICAMENTE);
            ProductoCotizableAutomaticamente.setEndpoint(servicioProdCotAuto.getInterfazEndpoint());
            ProductoCotizableAutomaticamente.setMethod(servicioProdCotAuto.getMetodo());
            ProductoCotizableAutomaticamente.setNamespacePort(servicioProdCotAuto.getNamespace());
            ProductoCotizableAutomaticamente.setServiceName(servicioProdCotAuto.getQnameServicio());
            ProductoCotizableAutomaticamente.setWsdlUrl(servicioProdCotAuto.getUrlWsdl());
            ProductoCotizableAutomaticamente.setInterfaceType(servicioProdCotAuto.getTipoInterfaz());
            //Cargamos el número de ServiciosEbs
            numeroParametrosProductoCotizableAutomatiamenteWS = servicioProdCotAuto.getNumeroParametros();
            
            
            requestModelosPorMarcaInventario = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioModeloPorMarcaInventario = iServiciosESB.findByUniqueName(WS_EBS_MODEL_X_MARCA_INVENTARIO);
            requestModelosPorMarcaInventario.setEndpoint(servicioModeloPorMarcaInventario.getInterfazEndpoint());
            requestModelosPorMarcaInventario.setMethod(servicioModeloPorMarcaInventario.getMetodo());
            requestModelosPorMarcaInventario.setNamespacePort(servicioModeloPorMarcaInventario.getNamespace());
            requestModelosPorMarcaInventario.setServiceName(servicioModeloPorMarcaInventario.getQnameServicio());
            requestModelosPorMarcaInventario.setWsdlUrl(servicioModeloPorMarcaInventario.getUrlWsdl());
            requestModelosPorMarcaInventario.setInterfaceType(servicioModeloPorMarcaInventario.getTipoInterfaz());
            //Cargamos el número de ServiciosEbs
            numeroParametrosWSModelosPorMarcaInventario = servicioModeloPorMarcaInventario.getNumeroParametros();   
            
            
            requestConsultaProductosInventarioEBS = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioConsultaProductosInventarioEBS = iServiciosESB.findByUniqueName(WS_EBS_CONSULTA_PRODUCTOS_INVENTARIO);
            requestConsultaProductosInventarioEBS.setEndpoint(servicioConsultaProductosInventarioEBS.getInterfazEndpoint());
            requestConsultaProductosInventarioEBS.setMethod(servicioConsultaProductosInventarioEBS.getMetodo());
            requestConsultaProductosInventarioEBS.setNamespacePort(servicioConsultaProductosInventarioEBS.getNamespace());
            requestConsultaProductosInventarioEBS.setServiceName(servicioConsultaProductosInventarioEBS.getQnameServicio());
            requestConsultaProductosInventarioEBS.setWsdlUrl(servicioConsultaProductosInventarioEBS.getUrlWsdl());
            requestConsultaProductosInventarioEBS.setInterfaceType(servicioConsultaProductosInventarioEBS.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosconsultaProductosInventarioEBSWS = servicioConsultaProductosInventarioEBS.getNumeroParametros();
            

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * Metodo que carga las modelos por marcas
     */
    public void listaModeloXMarcas() {
        try {
            if (linea == null) {
                ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{cotizacionesCrearBean}", CotizacionesCrearBean.class);
                CotizacionesCrearBean cotizacionesCrearBean = (CotizacionesCrearBean) ve.getValue(getELContext());
                linea = cotizacionesCrearBean.getContadorCotLinea().get(0).getIdlinea();
            }
            String[] paramsService = new String[numeroParametrosWSModelos];
            paramsService[0] = this.marca;
            paramsService[1] = this.linea;
            paramsService[2] = (this.tipoProducto == null || "".equals(this.tipoProducto)) ? "%" : this.tipoProducto;
            requestModelos.setParams(paramsService);
            String responseStr = userSession.getClientWs().consumeService(requestModelos);
            logger.info("responseStr: " + responseStr);
            TipoResponseVO response = (TipoResponseVO) Utils.unmarshal(TipoResponseVO.class, responseStr);
            if (response != null) {
                listaModelos = response.getTipos();
            }
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    /**
     * Metodo que carga las modelos por marcas de la vista de inventario
     */
    /*public void listaModeloXMarcasInventario() {
        try {
            
            String[] paramsService = new String[numeroParametrosWSModelosPorMarcaInventario];
            paramsService[0] = this.marca;
            paramsService[1] = this.linea;
            paramsService[2] = (this.tipoProducto == null || "".equals(this.tipoProducto)) ? "%" : this.tipoProducto;
            requestModelosPorMarcaInventario.setParams(paramsService);
            String responseStr = userSession.getClientWs().consumeService(requestModelosPorMarcaInventario);
            logger.info("responseStr: " + responseStr);
            TipoResponseVO response = (TipoResponseVO) Utils.unmarshal(TipoResponseVO.class, responseStr);
            if (response != null) {
                listaModelos = response.getTipos();
            }
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }*/

    /**
     * Se reinicia tabla de listado de oportunidades
     */
    public void reiniciar_tabla() {
        this.marca = null;
        this.modelo = null;
        this.descripcion = null;
        this.listaProductos = null;
    }

    /**
     * Metodo que carga las marcas por lineas
     */
    public void listaMarcaPorLinea() {
        try {
            if (linea == null) {
                ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{cotizacionesCrearBean}", CotizacionesCrearBean.class);
                CotizacionesCrearBean cotizacionesCrearBean = (CotizacionesCrearBean) ve.getValue(getELContext());
                linea = cotizacionesCrearBean.getContadorCotLinea().get(0).getIdlinea();
            }
            String[] paramsService = new String[numeroParametrosWSMarcas];
            paramsService[0] = this.linea;
            requestMarcas.setParams(paramsService);
            System.out.println("Requested Marcas .... -> "+requestMarcas.getEndpoint()+" , "+requestMarcas.getMethod()+", "+requestMarcas.getServiceName()+" , "+requestMarcas.getWsdlUrl());
            String responseStr = userSession.getClientWs().consumeService(requestMarcas);
            logger.info("responseStr: " + responseStr);
            TipoResponseVO response = (TipoResponseVO) Utils.unmarshal(TipoResponseVO.class, responseStr);
            if (response != null) {
                listaMarcas = response.getTipos();
            }
            this.listaModelos = null;
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Metodo que carga las marcas por lineas
     */
    public void listaMarcaPorLineaCot() {
        try {
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{cotizacionesCrearBean}", CotizacionesCrearBean.class);
            CotizacionesCrearBean cotizacionesCrearBean = (CotizacionesCrearBean) ve.getValue(getELContext());
            linea = cotizacionesCrearBean.getLinea();
            if (linea != null) {

                String[] paramsService = new String[numeroParametrosWSMarcas];
                paramsService[0] = this.linea;
                requestMarcas.setParams(paramsService);
                String responseStr = userSession.getClientWs().consumeService(requestMarcas);
                logger.info("responseStr: " + responseStr);
                TipoResponseVO response = (TipoResponseVO) Utils.unmarshal(TipoResponseVO.class, responseStr);
                if (response != null) {
                    listaMarcas = response.getTipos();
                }
            }
            this.listaModelos = null;
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void buscarAction(ActionEvent actionEvent) {
        logger.info("**** IntventarioConsultaFAcesBean -> buscarAction");
        try {
            String value = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("hidden1");
            //Variable que determine que el producto a buscar es cotizable.
            //Esto aplica si los productos son consultados desde la vista Cotizaciones
            String cotizable = "";
            String organizationId = "";
            //String subinventario = "A.REALIZAB";
            String subinventario = "";
            String lineaConsulta = "";
            String catalago = "";
            Boolean consultaInevtario = true;
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{cotizacionesCrearBean}", CotizacionesCrearBean.class);
            CotizacionesCrearBean cotizacionesCrearBean = (CotizacionesCrearBean) ve.getValue(getELContext());
            logger.info("Hidden cotizable crear-cotizacion: " + value);
            if (value != null && value.equals("crear-cotizacion")) {
                logger.info("Requerire Aprobacion: " + cotizacionesCrearBean.getRequeiereAprobacion());
                if (!cotizacionesCrearBean.getRequeiereAprobacion()) {
                    cotizable = "Y";
                }
                lineaConsulta = cotizacionesCrearBean.getLinea();
                catalago = "Y";
            } else if (value != null && value.equals("crear-oportunidad")) {
                lineaConsulta = this.linea;
                catalago = "Y";
            } else {
                consultaInevtario = false;
            }
            if (!consultaInevtario) {
                logger.info("**** IntventarioConsultaFAcesBean -> buscarAction -> FALSE consultaInevtario");
                String[] paramsService = new String[numeroParametrosConsultaInventarioWS];
                //paramsService[0] = tipoProducto;
                paramsService[0] = tipoProducto != null ? tipoProducto : "";
                paramsService[1] = marca != null ? marca : "";
                paramsService[2] = modelo != null ? modelo : "";
                paramsService[3] = descripcion != null ? descripcion : "";
                
                paramsService[4] = cotizable != null ? cotizable : "";
                requestConsultaInventario.setParams(paramsService);

                String responseStr = userSession.getClientWs().consumeService(requestConsultaInventario);
                logger.info("responseStr: " + responseStr);
                ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);

                if (response != null) {
                    if (Boolean.parseBoolean(response.getStatus())) {
                        listaProductos = response.getProductos();
                    } else {
                        listaProductos = null;
                        performWarningMessages("La consulta contiene demasiados registros, por favor realice una nueva consulta con un filtro más especifico.");
                        logger.info("Consulta contiene demasiados registros");
                    }

                }
            } else {
                logger.info("**** IntventarioConsultaFAcesBean -> buscarAction -> TRUE consultaInevtario");
                if (lineaConsulta != null && consultaInevtario) {
                    String[] paramsService = new String[numeroParametrosWS];
                    //paramsService[0] = tipoProducto;
                    paramsService[0] = tipoProducto != null ? tipoProducto : "";
                    paramsService[1] = marca != null ? marca : "";
                    paramsService[2] = modelo != null ? modelo : "";
                    paramsService[3] = descripcion != null ? descripcion : "";
                    paramsService[4] = cotizable;
                    paramsService[5] = organizationId;
                    paramsService[6] = subinventario;
                    paramsService[7] = lineaConsulta;
                    paramsService[8] = catalago;

                    request.setParams(paramsService);

                    String responseStr = userSession.getClientWs().consumeService(request);
                    logger.info("responseStr: " + responseStr);
                    ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);

                    if (response != null) {
                        if (Boolean.parseBoolean(response.getStatus())) {
                            listaProductos = response.getProductos();
                        } else {
                            listaProductos = null;
                            performWarningMessages("La consulta contiene demasiados registros, por favor realice una nueva consulta con un filtro más especifico.");
                            logger.info("Consulta contiene demasiados registros");
                        }

                    }
                } else {
                    performWarningMessages("No existe un Línea asociada a la oportunidad");
                }
            }
            logger.info("Consulta de inventario terminada");

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void buscarActionInventario(ActionEvent actionEvent) {
        logger.info("**** IntventarioConsultaFacesBean -> buscarActionInventario");
        try {
            //Validaciones
            if (this.linea == null || "".equals(this.linea)) {
                performErrorMessages("Linea es requerida");
                return;
            }

            //Inicializacion de parametros requeridos por el WS
            String cotizable = "";
            String organizationId = "";
            String subinventario = "";
            //String lineaConsulta = "";
            String catalago = "";

            //Inicializacion del request al WS
            String[] paramsService = new String[numeroParametrosconsultaProductosInventarioEBSWS];
            paramsService[0] = tipoProducto != null ? tipoProducto : "";
            paramsService[1] = marca != null ? marca : "";
            paramsService[2] = modelo != null ? modelo : "";
            paramsService[3] = descripcion != null ? descripcion : "";
            paramsService[4] = cotizable;
            paramsService[5] = organizationId;
            paramsService[6] = subinventario;
            paramsService[7] = linea;
            paramsService[8] = catalago;

            requestConsultaProductosInventarioEBS.setParams(paramsService);

            String responseStr = userSession.getClientWs().consumeService(requestConsultaProductosInventarioEBS);
            logger.info("responseStr: " + responseStr);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);

            if (response != null) {
                if (Boolean.parseBoolean(response.getStatus())) {
                    listaProductos = response.getProductos();
                } else {
                    listaProductos = null;
                    performWarningMessages("La consulta contiene demasiados registros, por favor realice una nueva consulta con un filtro más especifico.");
                    logger.info("Consulta contiene demasiados registros");
                }
            }

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void buscarActionAutomatica(ActionEvent actionEvent) {
        try {
            String value = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("hidden1");
            //Variable que determine que el producto a buscar es cotizable.
            //Esto aplica si los productos son consultados desde la vista Cotizaciones
            String cotizable = "";
            String organizationId = "";
            String subinventario = "";
            String linea = "";
            String catalago = "";
            Boolean consultaInevtario = true;
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{cotizacionesCrearBean}", CotizacionesCrearBean.class);
            CotizacionesCrearBean cotizacionesCrearBean = (CotizacionesCrearBean) ve.getValue(getELContext());
            logger.info("Hidden cotizable crear-cotizacion: " + value);
            if (value != null && value.equals("crear-cotizacion")) {
                logger.info("Requiere Aprobacion? : " + cotizacionesCrearBean.getRequeiereAprobacion());
                if (!cotizacionesCrearBean.getRequeiereAprobacion()) {
                    cotizable = "Y";
                }
                linea = cotizacionesCrearBean.getLinea();
                catalago = "Y";
                consultaInevtario = false;
            } else if (value != null && value.equals("crear-oportunidad")) {
                linea = this.linea;
                catalago = "Y";
            } else {
                consultaInevtario = false;
            }
            if (!consultaInevtario) {
                logger.info("Entro en consulta Inventario Automatica");
                String[] paramsService = new String[numeroParametrosConsultaInventarioAutomaticaWS];
                logger.info("Numero de Parametros: " + numeroParametrosConsultaInventarioAutomaticaWS);
                logger.info("Numero de WSDL : " + ConsultaInventarioAutomatica.getWsdlUrl());
                //paramsService[0] = tipoProducto;
                paramsService[0] = tipoProducto != null ? tipoProducto : "";
                paramsService[1] = marca != null ? marca : "";
                paramsService[2] = modelo != null ? modelo : "";
                paramsService[3] = descripcion != null ? descripcion : "";
                paramsService[4] = cotizable;
                paramsService[5] = organizationId;
                paramsService[6] = subinventario;
                paramsService[7] = linea;
                paramsService[8] = catalago;
                ConsultaInventarioAutomatica.setParams(paramsService);
                logger.info("Envio de Parametros de Servicios Cotizacion Automatica ");
                String responseStr = userSession.getClientWs().consumeService(ConsultaInventarioAutomatica);
                logger.info("responseStr: " + responseStr);
                ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);

                if (response != null) {
                    if (Boolean.parseBoolean(response.getStatus())) {
                        listaProductos = response.getProductos();
                    } else {
                        listaProductos = null;
                        performWarningMessages("La consulta contiene demasiados registros, por favor realice una nueva consulta con un filtro más especifico.");
                        logger.info("Consulta contiene demasiados registros");
                    }

                }
            } else {
                if (linea != null && consultaInevtario) {
                    logger.info("No ingreso en consulta de inventario");
                    String[] paramsService = new String[numeroParametrosWS];
                    //paramsService[0] = tipoProducto;
                    paramsService[0] = tipoProducto != null ? tipoProducto : "";
                    paramsService[1] = marca != null ? marca : "";
                    paramsService[2] = modelo != null ? modelo : "";
                    paramsService[3] = descripcion != null ? descripcion : "";
                    paramsService[4] = cotizable;
                    paramsService[5] = organizationId;
                    paramsService[6] = subinventario;
                    paramsService[7] = linea;
                    paramsService[8] = catalago;

                    request.setParams(paramsService);

                    String responseStr = userSession.getClientWs().consumeService(request);
                    logger.info("responseStr: " + responseStr);
                    ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);

                    if (response != null) {
                        if (Boolean.parseBoolean(response.getStatus())) {
                            listaProductos = response.getProductos();
                        } else {
                            listaProductos = null;
                            performWarningMessages("La consulta contiene demasiados registros, por favor realice una nueva consulta con un filtro más especifico.");
                            logger.info("Consulta contiene demasiados registros");
                        }

                    }
                } else {
                    performWarningMessages("No existe un Línea asociada a la oportunidad");
                }
            }
            logger.info("Consulta de inventario terminada");

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }

    }

    public List<ProductoVO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoVO> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Tipo> getListaTiposProducto() {
        return listaTiposProducto;
    }

    public void setListaTiposProducto(List<Tipo> listaTiposProducto) {
        this.listaTiposProducto = listaTiposProducto;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public List<TipoVO> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<TipoVO> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List<TipoVO> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<TipoVO> listaModelos) {
        this.listaModelos = listaModelos;
    }

}
