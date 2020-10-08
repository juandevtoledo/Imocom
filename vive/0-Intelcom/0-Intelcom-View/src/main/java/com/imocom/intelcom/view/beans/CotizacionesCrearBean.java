/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_PROCESS;
import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.ContadorCotLinea;
import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.CotizacionProducto;
import com.imocom.intelcom.persistence.entities.HabeasDataContacto;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.IContadorCotLineaServiceLocal;
import com.imocom.intelcom.services.interfaces.ICotizacionProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.interfaces.IHabeasDataContactoEBSLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_COTIZACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_CONSULTAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_INCOTERM;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_COTIZACION_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOMBRE_ESTAPA;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOMBRE_ESTAPA_ESTADO;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_RESULTADO_COTIZACION;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_ASOCIADO_OPORTUNIDAD;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.view.util.CreateDocx;
import com.imocom.intelcom.util.dtos.ClaveValorDTO;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTES_CONTACTOS_CONSULTA;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_CONSULTA;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_COTIZABLE_AUTOMATICAMENTE;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_DETALLE;
import com.imocom.intelcom.view.util.GerenerNombreUnicos;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.view.vo.ContactoCliente;
import com.imocom.intelcom.view.vo.SeleccionObjectoDTO;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ContactoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.CotizacionProductoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.CotizacionesVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class CotizacionesCrearBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoDetalleFacesBean.class);
    private static final long serialVersionUID = 1L;

    private List<Tipo> listaIncoterm;
    private List<ClaveValorDTO> porcentajeDescuentos;

    /**
     * Parametros para invocar MDW para crear Cotizacion
     */
    private MiddlewareServiceRequestVO request;
    /**
     * Parametros para invocar MDW para Buscar Producto para una oportunidad
     */
    private MiddlewareServiceRequestVO requestProductosOportunidad;
    /**
     * Parametros para invocar MDW para Buscar una oportunidad del asesor
     */
    private MiddlewareServiceRequestVO requestOportunidad;
    /**
     * Parametros para invocar MDW para Buscar una oportunidad por etapa estado
     */
    private MiddlewareServiceRequestVO requestOportunidadesEstapaEstado;
    /**
     * Parametros para invocar MDW para Buscar una oportunidad por etapa estado
     */
    private MiddlewareServiceRequestVO requestProductoCotizableAutomatico;
    /**
     * NÃºmero de paramatos para request de Creación de Cotización
     */
    private int numeroParametrosWS = 0;
    /**
     * NÃºmero de parametros para request de busqueda de productos por
     * oportunidad
     */
    private int numeroParamatrosWSProductoOportunidad = 0;
    /**
     * NÃºmero de parametros para request de busqueda de una oportunidad
     */
    private int numeroParamatrosWSOportunidad = 0;
    /**
     * NÃºmero de parametros para request de busqueda de una oportunidad
     */
    private int numeroParamatrosWSOportunidadEtapaEstado = 0;
    /**
     * NÃºmero de parametros para consulta de producto cotizable
     */
    private int numeroParamatrosWSProductoCotizableAutomatico = 0;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private ICotizacionServiceLocal iCotizacionService;

    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IContadorCotLineaServiceLocal IContadorCot;

    @EJB
    private ICotizacionProductoServiceLocal iCotizacionProductoServiceLocal;

    /**
     * Conexión al UCM para guardar documento
     */
    private RidcConnectionServiceBean iRidcConnection;

    private Cotizacion cotizacion;
    /**
     * listados de productos que se buscan al inventarios
     */
    private List<ProductoVO> productoCotizacion;
    /**
     * Producto que se selecciona para agregar a la cotizacion, ya se a del
     * inventario o de la oportunidad
     */
    private List<ProductoVO> selectProductoCotizacion;

    private ProductoVO productoAgregar;

    private ProductoVO productoBorrar;
    /**
     * ***************************************************************
     */
    /**
     * Lista oportunidades
     */
    private List<OportunidadVO> oportunidades;
    /**
     * Oportunidad Seleccionada para hacer la cotizacion
     */
    private OportunidadVO oportunidadSelect;
    /**
     * Producto de asociada a la oportunidad
     */
    private ProductoVO oportunidadProductoSelect;
    /**
     * Tipo de Oferta de la cotización
     */
    private String tipo;
    /**
     * Archivo de Cargue de Documento para subir al Content Server
     */
    private UploadedFile file;

    private Boolean isTipoPrincipal;
    /**
     * Nombre de las oportunidad a buscar
     */
    private String nombreOportunidadSearch;

    private Boolean agregarProductos;
    /**
     * Booelando que determina si la cotización es automatica o normal
     */
    private Boolean requeiereAprobacion = true;

    private Boolean mostrarLinea;
    private Boolean productoEsCotizableAutomatico;

    /**
     * Linea de la cotización: Si el usuario pertenece a linea: VENDEDORES
     * SUCURSALES y VENDEDORES DIVISION METALMECANICA se envia la liena
     * seleccionada, si es diferente se toma la linea de listado de la pantalla
     */
    private String linea;

    private String incoterm;

    private Long incotermLong;

    private List<ClaveValorDTO> listaRazonCompetenciaDTO;

    public CotizacionesCrearBean() {

    }

    @Override
    protected void build() {
        try {
            logger.info("********* Construyendo Vista -> cotizaciones-crear.xhtml");
            armarRequest();
            /**
             * Se verifica si en la session se tiene un objeto oportunidad, Lo
             * que quiere decir que fue invocada la apagina desde el detalle de
             * un oportunidad para solicitar una cotizacion
             */
            if (getSessionMap().get(OPORTUNIDAD_ID_PARAM) != null) {
                oportunidadSelect = (OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM);
                //loadProductosOportunidad();
                logger.info("oportunidadSelect.getIdOportunidad = " + oportunidadSelect.getIdOportunidad());

            }

            clearProductosList();

            if (getSessionMap().get("PRODUCTO_OPORTUNIDAD_VO_SELECIONADO") != null) {

                List<SeleccionObjectoDTO> listaPA = (List) getSessionMap().get("PRODUCTO_OPORTUNIDAD_VO_SELECIONADO");
                if (listaPA != null) {
                    for (SeleccionObjectoDTO seleccionObjectoDTO : listaPA) {
                        if (seleccionObjectoDTO.isSeleccionado()) {
                            productoAgregar = seleccionObjectoDTO.getProductoVO();
                            agregarProducto();
                        }
                    }
                }
                getSessionMap().remove("PRODUCTO_OPORTUNIDAD_VO_SELECIONADO");
            }
            listaIncoterm = iTipoService.findByTipoNombre(TIPO_INCOTERM);

            porcentajeDescuentos = new ArrayList<ClaveValorDTO>();

            for (int i = 1; i <= 10; i++) {
                porcentajeDescuentos.add(new ClaveValorDTO(i, i + "%"));
            }

            listaRazonCompetenciaDTO = new ArrayList<ClaveValorDTO>();
            List<Tipo> listaTipo = iTipoService.findByTipoNombre(Constants.TIPO_RAZON_COMPETENCIA_OPORTUNIDAD);
            for (Tipo unTipo : listaTipo) {
                listaRazonCompetenciaDTO.add(new ClaveValorDTO(unTipo.getTipoValor(), unTipo.getTipoEtiqueta()));
            }

            cotizacion = new Cotizacion();

            if (getSessionMap().get("COTIZACION_DETALLE_VERSION") != null) {

                Cotizacion cotizacionSelectTMP = (Cotizacion) getSessionMap().get("COTIZACION_DETALLE_VERSION");

                String codigoCotAntiguo = cotizacionSelectTMP.getCodigo();

                oportunidadSelect = cargarDetalleOportunidad(cotizacionSelectTMP.getIdOportunidad().toString());

                cotizacion = iCotizacionService.findById(cotizacionSelectTMP.getIdCotizacion());
                cotizacion.setIdCotizacion(null);
                cotizacion.setCodigoVersion(codigoCotAntiguo);

                clearProductosList();

                List<CotizacionProducto> cotizacionProductosTMP = iCotizacionProductoServiceLocal.buscarCotizacionProductoPorCot(cotizacionSelectTMP.getIdCotizacion());
                for (CotizacionProducto cotizacionProducto : cotizacionProductosTMP) {
                    ProductoVO productoVO = getProductoDetalle(cotizacionProducto.getIdproducto().toString());
                    if (productoVO != null) {
                        productoVO.setCantidad("1");
                        selectProductoCotizacion.add(productoVO);
                    }
                }
                incotermLong = cotizacionSelectTMP.getIdIncoterm().getIdTipo();
                tipo = "Version";

                getSessionMap().remove("COTIZACION_DETALLE_VERSION");
            }

            //se carga la pantalla de una cotizacion cuando proviene de una oportunidad creada con solicitud
            if (getSessionMap().get("OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD") != null) {

                nombreOportunidadSearch = (String) getSessionMap().get("OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD");

                logger.info("Obteniendo datos a partir del SessionMap -> OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD = " + nombreOportunidadSearch);

                List<ProductoVO> listaProductoVOProductosAcotizacion = (List<ProductoVO>) getSessionMap().get("OPORTUNIDAD_A_COTIZACION_LISTA_PRODUCTOS");

                //Buscar la oportunidad es innecesario, se obtiene del sessionMap: OPORTUNIDAD_ID_PARAM
                /*
                List<String> idEtapas = new ArrayList<String>();
                idEtapas.add(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
                idEtapas.add(ATR_OPORTUNIDAD_COTIZACION_ETAPA_OP);

                List<String> idEstado = new ArrayList<String>();
                idEstado.add(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
                idEstado.add(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);

                List<OportunidadVO> listaO = this.load_oportunidad_etapa_estado(idEtapas, idEstado);
                if (listaO != null && !listaO.isEmpty()) {
                    oportunidadSelect = listaO.get(0);
                }
                 */
                nombreOportunidadSearch = null;

                //buscar los productos asociados a la oportunidad en pantalla
                clearProductosList();

                for (ProductoVO productoVO : listaProductoVOProductosAcotizacion) {
                    //productoVO.setCantidad("1");
                    selectProductoCotizacion.add(productoVO);

                }

                getSessionMap().remove("OPORTUNIDAD_A_COTIZACION_LISTA_PRODUCTOS");
                getSessionMap().remove("OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD");
            }

            contactosClienteDirigidoA = new ArrayList<ContactoCliente>();

            if (oportunidadSelect != null && oportunidadSelect.getNitCliente() != null) {
                //CARGAMOS LOS CONSTACTOS DEL CLIENTE
                cargarContactos(oportunidadSelect.getNitCliente());
            }

            //Validar que el producto es cotizable automaticamente al cargar la pagina
            //this.productoEsCotizableAutomatico = validarProductoEsCotizableAutomatico();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param idOportunidad
     * @return
     */
    private OportunidadVO cargarDetalleOportunidad(String idOportunidad) {
        try {
            String[] paramsService = new String[numeroParametrosWS_Oportunidad];
            paramsService[0] = idOportunidad;
            requestOportunidadDetalle.setParams(paramsService);

            String responseStr = userSession.getClientWs().consumeService(requestOportunidadDetalle);

            OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);

            if (response != null) {
                logger.info("Response: " + responseStr);
                OportunidadVO oportunidadDetalle = response.getOportunidades().get(0);

                return oportunidadDetalle;
            }

        } catch (Exception ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * Detalle de producto
     *
     * @param idProducto identificador del producto
     * @return
     */
    private ProductoVO getProductoDetalle(String idProducto) {
        String[] paramsService = new String[numeroParamatrosWSProducto];
        paramsService[0] = idProducto;
        requestProducto.setParams(paramsService);
        String responseStr;
        try {
            responseStr = userSession.getClientWs().consumeService(requestProducto);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            if (response.getProductos() != null) {
                return response.getProductos().get(0);
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        }
        return null;
    }

    public String agregarProducto() {

        if (productoAgregar != null) {
            productoCotizacion.remove(productoAgregar);
            if (!selectProductoCotizacion.contains(productoAgregar)) {
                selectProductoCotizacion.add(productoAgregar);
            }
            productoAgregar = null;
        }
        return null;
    }

    public String borrarProducto() {
        selectProductoCotizacion.remove(productoBorrar);
        if (!productoCotizacion.contains(productoBorrar)) {
            productoCotizacion.add(productoBorrar);
        }
        return null;
    }

    /**
     * Metodo que retorn los tipos de Oferta para una cotizacion
     *
     * @return
     */
    public List<Tipo> getTipoOfertaCotizacion() {
        List<Tipo> tipoOferta = null;
        try {
            tipoOferta = iTipoService.findByTipoNombre("TIPO_OFERTA");
        } catch (ServiceException ex) {
            logger.error("Error Capturando los Tipos de Ofertas para una cotizacion " + ex.getMessage());
        }
        return tipoOferta;
    }

    /**
     * Metodo que retorn los tipos de Oferta para una cotizacion
     *
     * @return
     */
    public List<ContadorCotLinea> getContadorCotLinea() {
        List<ContadorCotLinea> contadorCotLinea;
        contadorCotLinea = IContadorCot.buscarLienas();
        return contadorCotLinea;
    }

    private MiddlewareServiceRequestVO requestProducto;
    private int numeroParamatrosWSProducto = 0;
    private MiddlewareServiceRequestVO requestOportunidadDetalle;
    private int numeroParametrosWS_Oportunidad = 0;

    //Rquest de detalle cliente por nit.
    private MiddlewareServiceRequestVO requestDetalleCliente;
    private int numeroParametrosWS_detalleCliente = 0;

    private void armarRequest() {
        try {
            /**
             * Se arma Request para crear cotización en la EBS a travez del MDW
             */
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_BPM_COTIZACION_CREACION);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_PROCESS);
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS = servicio.getNumeroParametros();

            /**
             * Se arma Request para buscar productos de una oportunidad
             */
            requestProductosOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioPP = iServiciosESB.findByUniqueName(WS_PRODUCTO_ASOCIADO_OPORTUNIDAD);
            requestProductosOportunidad.setEndpoint(servicioPP.getInterfazEndpoint());
            requestProductosOportunidad.setMethod(servicioPP.getMetodo());
            requestProductosOportunidad.setNamespacePort(servicioPP.getNamespace());
            requestProductosOportunidad.setServiceName(servicioPP.getQnameServicio());
            requestProductosOportunidad.setWsdlUrl(servicioPP.getUrlWsdl());
            requestProductosOportunidad.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSProductoOportunidad = servicioPP.getNumeroParametros();

            /**
             * Se arma Request para buscar una oportunidad
             */
            requestOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioP = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_NOMBRE_ESTAPA);
            requestOportunidad.setEndpoint(servicioP.getInterfazEndpoint());
            requestOportunidad.setMethod(servicioP.getMetodo());
            requestOportunidad.setNamespacePort(servicioP.getNamespace());
            requestOportunidad.setServiceName(servicioP.getQnameServicio());
            requestOportunidad.setWsdlUrl(servicioP.getUrlWsdl());
            requestOportunidad.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSOportunidad = servicioP.getNumeroParametros();

            requestOportunidadesEstapaEstado = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioOpEtapaEstado = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_NOMBRE_ESTAPA_ESTADO);
            requestOportunidadesEstapaEstado.setEndpoint(servicioOpEtapaEstado.getInterfazEndpoint());
            requestOportunidadesEstapaEstado.setMethod(servicioOpEtapaEstado.getMetodo());
            requestOportunidadesEstapaEstado.setNamespacePort(servicioOpEtapaEstado.getNamespace());
            requestOportunidadesEstapaEstado.setServiceName(servicioOpEtapaEstado.getQnameServicio());
            requestOportunidadesEstapaEstado.setWsdlUrl(servicioOpEtapaEstado.getUrlWsdl());
            requestOportunidadesEstapaEstado.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSOportunidadEtapaEstado = servicioOpEtapaEstado.getNumeroParametros();

            /**
             * Se arma Request para buscar informacion del producto
             */
            requestProducto = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_PRODUCTO_DETALLE);
            requestProducto.setEndpoint(servicio.getInterfazEndpoint());
            requestProducto.setMethod(servicio.getMetodo());
            requestProducto.setNamespacePort(servicio.getNamespace());
            requestProducto.setServiceName(servicio.getQnameServicio());
            requestProducto.setWsdlUrl(servicio.getUrlWsdl());
            requestProducto.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            numeroParamatrosWSProducto = servicio.getNumeroParametros();

            requestOportunidadDetalle = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_DETALLE);
            requestOportunidadDetalle.setEndpoint(servicio.getInterfazEndpoint());
            requestOportunidadDetalle.setMethod(servicio.getMetodo());
            requestOportunidadDetalle.setNamespacePort(servicio.getNamespace());
            requestOportunidadDetalle.setServiceName(servicio.getQnameServicio());
            requestOportunidadDetalle.setWsdlUrl(servicio.getUrlWsdl());
            //Cargamos el número de parametros
            numeroParametrosWS_Oportunidad = servicio.getNumeroParametros();

            requestContactosClientes = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_CLIENTES_CONTACTOS_CONSULTA);
            requestContactosClientes.setEndpoint(servicio.getInterfazEndpoint());
            requestContactosClientes.setMethod(servicio.getMetodo());
            requestContactosClientes.setNamespacePort(servicio.getNamespace());
            requestContactosClientes.setServiceName(servicio.getQnameServicio());
            requestContactosClientes.setWsdlUrl(servicio.getUrlWsdl());
            requestContactosClientes.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosContactosClientesWS = servicio.getNumeroParametros();

            //request de dettale de cliente x nit
            requestDetalleCliente = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_CLIENTE_CONSULTA);
            requestDetalleCliente.setEndpoint(servicio.getInterfazEndpoint());
            requestDetalleCliente.setMethod(servicio.getMetodo());
            requestDetalleCliente.setNamespacePort(servicio.getNamespace());
            requestDetalleCliente.setServiceName(servicio.getQnameServicio());
            requestDetalleCliente.setWsdlUrl(servicio.getUrlWsdl());
            requestDetalleCliente.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            logger.info("Parámetros asignados a numeroParametrosWS:" + servicio.getNumeroParametros());
            numeroParametrosWS_detalleCliente = servicio.getNumeroParametros();
            //request de producto Cotizable Automatico
            requestProductoCotizableAutomatico = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_PRODUCTO_COTIZABLE_AUTOMATICAMENTE);
            requestProductoCotizableAutomatico.setEndpoint(servicio.getInterfazEndpoint());
            requestProductoCotizableAutomatico.setMethod(servicio.getMetodo());
            requestProductoCotizableAutomatico.setNamespacePort(servicio.getNamespace());
            requestProductoCotizableAutomatico.setServiceName(servicio.getQnameServicio());
            requestProductoCotizableAutomatico.setWsdlUrl(servicio.getUrlWsdl());
            requestProductoCotizableAutomatico.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            logger.info("Parámetros asignados a numeroParametrosWS:" + servicio.getNumeroParametros());
            numeroParamatrosWSProductoCotizableAutomatico = servicio.getNumeroParametros();
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Método que verifica si el tipo null :Se ingresa a la pagina y el tipo por
     * defecto es prinicipal
     *
     * @return
     */
    private String getChangeTipoOferta() {

        if (this.tipo == null || this.tipo.equals("")) {
            return "Principal";
        }
        return tipo;

    }

    /**
     * Retorna una lista de oportunidades por etapa
     *
     * @param idEtapa
     */
    private List<OportunidadVO> load_oportunidad_etapa(String idEtapa) {
        List<OportunidadVO> _listOEtapa = new ArrayList<OportunidadVO>();
        try {
            String[] paramsService = new String[numeroParamatrosWSOportunidadEtapaEstado];
            paramsService[0] = userSession.getUserLogin();
            paramsService[1] = this.nombreOportunidadSearch != null ? this.nombreOportunidadSearch : "";
            paramsService[2] = idEtapa;
            paramsService[3] = "11";
            requestOportunidadesEstapaEstado.setParams(paramsService);
            String responseStr;
            responseStr = userSession.getClientWs().consumeService(requestOportunidadesEstapaEstado);
            OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (response != null && response.getOportunidades() != null) {
                _listOEtapa = response.getOportunidades();
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        }
        return _listOEtapa;
    }

    /**
     * Retorna una lista de oportunidades por etapa
     *
     * @param idEtapa
     */
    private List<OportunidadVO> load_oportunidad_etapa_estado(List<String> idsEtapa, List<String> idsEstado) {
        List<OportunidadVO> _listOEtapaEstado = new ArrayList<OportunidadVO>();
        try {

            for (String idEtapa : idsEtapa) {
                for (String idEstado : idsEstado) {
                    String[] paramsService = new String[numeroParamatrosWSOportunidadEtapaEstado];
                    paramsService[0] = userSession.getUserLogin();
                    paramsService[1] = this.nombreOportunidadSearch != null ? this.nombreOportunidadSearch : "";
                    paramsService[2] = idEtapa;
                    paramsService[3] = idEstado;
                    requestOportunidadesEstapaEstado.setParams(paramsService);
                    String responseStr;
                    responseStr = userSession.getClientWs().consumeService(requestOportunidadesEstapaEstado);
                    OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                    if (response != null && response.getOportunidades() != null) {
                        // _listOEtapaEstado = response.getOportunidades();
                        _listOEtapaEstado.addAll(response.getOportunidades());
                    }

                }

            }

            Collections.sort(_listOEtapaEstado, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    OportunidadVO op1 = (OportunidadVO) o1;
                    OportunidadVO op2 = (OportunidadVO) o2;
                    return op1.getNombreOportunidad().compareTo(op2.getNombreOportunidad());
                }
            });

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        }
        return _listOEtapaEstado;
    }

    /**
     * MÃ©todo que carga una lista de oportunidades del asesor
     */
    public void loadOportunidad() {
        //logger.info("Nombre oportunidad a buscar: "+this.nombreOportunidadSearch+" "+numeroParamatrosWSOportunidad);
        /*  oportunidades = new ArrayList<OportunidadVO>();
         String[] paramsService = new String[numeroParamatrosWSOportunidad];
         paramsService[0] = userSession.getUserLogin();
         paramsService[1] = this.nombreOportunidadSearch != null ? this.nombreOportunidadSearch : "";
         paramsService[2] = "";

         requestOportunidad.setParams(paramsService);

         String responseStr;
         try {
         responseStr = userSession.getClientWs().consumeService(requestOportunidad);
         OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
         oportunidades = response.getOportunidades();

         } catch (IntelcomMiddlewareException ex) {
         logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
         } catch (UtilException ex) {
         logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
         }*/

 /*
         gfandino: Se crearon las listas idEtapas y idEstado
         para invocar el nuevo método load_oportunidad_etapa_estado
         */
        List<String> idEtapas = new ArrayList<String>();
        idEtapas.add(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
        idEtapas.add(ATR_OPORTUNIDAD_COTIZACION_ETAPA_OP);

        List<String> idEstado = new ArrayList<String>();
        idEstado.add(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
        idEstado.add(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);

        oportunidades = new ArrayList<OportunidadVO>();
        oportunidades.addAll(this.load_oportunidad_etapa_estado(idEtapas, idEstado));

        //oportunidades.addAll(this.load_oportunidad_etapa(ATR_OPORTUNIDAD_CREACION_ETAPA_OP));
        //oportunidades.addAll(this.load_oportunidad_etapa(ATR_OPORTUNIDAD_COTIZACION_ETAPA_OP));
    }

    public void clearProductosList() {
        selectProductoCotizacion = new ArrayList<ProductoVO>();
        productoCotizacion = new ArrayList<ProductoVO>();

    }

    public void clearInventario() {
        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class
        );
        InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
        if (inventarioConsultaFacesBean != null && inventarioConsultaFacesBean.getListaProductos() != null) {
            inventarioConsultaFacesBean.getListaProductos().clear();
        }
    }

    public void reqApprValueChangeListener(ValueChangeEvent event) {
        logger.info("********** Metodo reqApprValueChangeListener **********");

        UIInput input = (UIInput) event.getComponent();
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        RequestContext context = RequestContext.getCurrentInstance();

        //validar si el producto es cotizable automaticamente cuando el usuario
        //quita el check a requiere aprovacion
        this.productoEsCotizableAutomatico = validarProductoEsCotizableAutomatico();

        //si no es cotizable automaticamente, Salta un mensaje informando al usuario
        //Tambien se modifica el valor del chek de requiere approbacion para forzar
        //que si el producto en lista NO es cotizable automaticamente, se solicite aprobacion
        if (!this.productoEsCotizableAutomatico && this.selectProductoCotizacion.size() > 0) {

            context.execute("PF('productoAutomaticoDialogVar').show();");

            this.requeiereAprobacion = true;
            input.setValue(oldValue);
        }

        logger.info("*Fin de reqApprValueChangeListener **********");
        logger.info("this.requeiereAprobacion = " + this.requeiereAprobacion);
        logger.info("input.getValue() = " + input.getValue());
    }

    public void consultaProdDialogCloseListener(CloseEvent cEvent) {

        logger.info("********** Metodo consultaProdDialogCloseListener **********");
        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class
        );
        InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());

        inventarioConsultaFacesBean.setModelo("");
        inventarioConsultaFacesBean.setMarca("");
        inventarioConsultaFacesBean.setTipoProducto("");

    }

    /**
     * Metodo que que busca un producto asociada por una oportunidad, en este
     * metodo se selecciona la linea asociada a un producto
     */
    public void loadProductosOportunidad() {

        if (selectProductoCotizacion != null) {
            selectProductoCotizacion.clear();

        } else {
            selectProductoCotizacion = new ArrayList<ProductoVO>();
        }
        if (productoCotizacion != null) {
            productoCotizacion.clear();
            clearInventario();
        }
        //Toca buscar la etiqueta para ese tipo Long a traves dle ejeb
        productoCotizacion = new ArrayList<ProductoVO>();

        //Se busca el producto asociado a la oportunidad para 
        //Se la cotizacion es principal se toma el producto y si es alternativa se toma la linea
        String[] paramsService = new String[numeroParamatrosWSProductoOportunidad];
        System.out.println("this.oportunidadSelect.getIdOportunidad() " + this.oportunidadSelect.getIdOportunidad());
        System.out.println("this.oportunidadSelect.getNombreOportunidad() " + this.oportunidadSelect.getNombreOportunidad());
        paramsService[0] = this.oportunidadSelect.getIdOportunidad();
        String cotizable = "";
        if (!this.requeiereAprobacion) {
            cotizable = "1";
        }
        paramsService[1] = cotizable;
        requestProductosOportunidad.setParams(paramsService);
        String responseStr;
        try {
            responseStr = userSession.getClientWs().consumeService(requestProductosOportunidad);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            List<ProductoVO> productoOportunidad = response.getProductos();
            if (productoOportunidad != null && !productoOportunidad.isEmpty()) {
                this.linea = productoOportunidad.get(0).getLinea();
                ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class);
                InventarioConsultaFacesBean inventarioConsulta = (InventarioConsultaFacesBean) ve.getValue(getELContext());
                inventarioConsulta.listaMarcaPorLineaCot();

                this.mostrarLinea = true;
                oportunidadProductoSelect = productoOportunidad.get(0);
            }

            //CARGAMOS LOS CONSTACTOS DEL CLIENTE
            cargarContactos(oportunidadSelect.getNitCliente());

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        }
    }

    @EJB
    private IHabeasDataContactoEBSLocal iHabeasDataContactoEBSLocal;

    private List<ContactoCliente> contactosClienteDirigidoA;
    private MiddlewareServiceRequestVO requestContactosClientes;
    private int numeroParametrosContactosClientesWS = 1;

    private void cargarContactos(String nitCliente) {
        try {

            String[] paramsService = new String[numeroParametrosContactosClientesWS];
            paramsService[0] = nitCliente;

            requestContactosClientes.setParams(paramsService);

            contactosClienteDirigidoA = new ArrayList<ContactoCliente>();

            String responseStr;
            if (requestContactosClientes != null) {

                responseStr = userSession.getClientWs().consumeService(requestContactosClientes);
                ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);

                if (response != null) {
                    List<ContactoVO> contactosResponse = response.getContactos();
                    if (contactosResponse != null && !contactosResponse.isEmpty()) {

                        for (ContactoVO contactoItem : contactosResponse) {
                            String nombre = GerenerNombreUnicos.getNombre(contactoItem.getNombre());

                            if (nombre == null) {
                                continue;
                            }

                            List<HabeasDataContacto> listaHabeas = iHabeasDataContactoEBSLocal.findAllByIdentificacionYNit(nombre, oportunidadSelect.getNitCliente());
                            if (!listaHabeas.isEmpty()) {
                                contactosClienteDirigidoA.add(new ContactoCliente(contactoItem));
                            }
                        }

                    }
                }

            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * Se selecciona el producto asociada en una oportunidad, cuando es una
     * cotizacion principal
     */
    public void loadProductos() {
        if ((OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM) != null) {
            oportunidadSelect = (OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM);
            loadProductosOportunidad();

        }
        String tipoOferta = getChangeTipoOferta();
        if (tipoOferta.equals("Principal")) {
            productoCotizacion.add(oportunidadProductoSelect);
        }
    }

    /**
     * Evento de Creación llamado desde la Pagina
     */
    public void enviarAction() {

        if (enviarSolicitud()) {
            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_COTIZACIONES_CONSULTAR_KEY);
            try {
                redirect(navigationFaces.navigation.get(outcome));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }

        } else {
            performErrorMessages("Ha ocurrido un problema la solicitud de creación de cotizaciones, no ha podido ser enviada ");
        }
        //logger.info("Productos Seleccionados: " + this.selectProductoCotizacion.size());
        /*for (ProductoVO pr : this.selectProductoCotizacion) {
         logger.info("Productos Seleccionados: " + this.selectProductoCotizacion);
         }*/

    }

    /**
     * Se carga el Archivo en el Content Servers
     *
     * @param is
     * @param filename
     * @param contentType
     * @param tipo
     * @return
     */
    public HashMap<String, String> upload(InputStream is, String filename, String contentType, String tipo) {
        if (is != null) {
            try {
                logger.info(" *********** Subiendo Documento UCM *********************** ");
                String name = "" + System.currentTimeMillis();
                String title = "COT_" + userSession.getUserLogin() + "_" + oportunidadSelect.getNombreOportunidad();
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                return iRidcConnection.checkingDocsOportunidad(is, filename, name, title, contentType, tipo, this.oportunidadSelect.getIdOportunidad(), this.oportunidadSelect.getNitCliente(), this.userSession.getUserLogin());
            } catch (ServiceException ex) {
                logger.error("Error Subiendo el documento de la cotización al UCM bean: " + ex.getMessage());
                return null;
            }
        } else {
            logger.error("No se ha cargado un documento en la cotización: ");
        }
        return new HashMap<String, String>();
    }

    /**
     * Metodo que valida que si la cotizacion a crear es valida, por la
     * siguientes condiciones: Oferta Principal: No debe tener cotizaciones la
     * oportunidad Oferta Alternatica: Debe tener una cotizacion principal
     *
     * @return
     */
    public Boolean cotizacion_valida_oferta() {
        try {
            Boolean resp = false;
            List<Cotizacion> cotizacionOportunidad = iCotizacionService.buscarCotizacionCliOporVenc(null, Long.parseLong(oportunidadSelect.getIdOportunidad()), null, null, null);
            if (tipo.equals("Principal")) {
                if (cotizacionOportunidad.isEmpty()) {
                    resp = true;
                } else {
                    performErrorMessages("Ya existe un oferta principal para la oportunidad seleccionada, Debe crear una Alternatica");
                }
            } else {
                resp = true;
            }
            /* else {
             if (!cotizacionOportunidad.isEmpty()) {
             for (Cotizacion co : cotizacionOportunidad) {
             if (co.getIdtipooferta().getTipoEtiqueta().equals("Principal")) {
             resp= true;
             }
             }                    
             }
             if(!resp){
             performErrorMessages("No existe un oferta principal, por favor seleccione una");
             }
             }*/
            return resp;

        } catch (ServiceException ex) {
            logger.error("Error Creando una cotización: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Envia una solicitud de crear una cotizacion
     *
     * @return
     */
    public Boolean enviarSolicitud() {

        logger.info("************** Solicitud De Creacion de Cotización ****************************");
        //logger.info("Productos Seleccionados: " + this.selectProductoCotizacion.);
        try {
            if (selectProductoCotizacion == null || selectProductoCotizacion.isEmpty()) {
                performErrorMessages("Debe seleccionar un Producto");
                return false;
            }
            String cantidadProducto = selectProductoCotizacion.get(0).getCantidad();
            if (!Utils.isNumeric(cantidadProducto) && cantidadProducto == "0") {
                performErrorMessages("Cantidad del Producto No es númerica o Contiene espacios. Ó es Cero");
                return false;
            }
            if (this.oportunidadSelect == null) {
                performErrorMessages("Debe seleccionar un Oportunidad");
                return false;
            }
            if (this.cotizacion.getCorreo() != null && !this.cotizacion.getCorreo().equals("")) {
                Pattern pat = Pattern.compile("[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]");
                Matcher mat = pat.matcher(this.cotizacion.getCorreo());
                if (!mat.matches()) {
                    performErrorMessages("Formato de Correo no es valido.");
                    return false;
                }
            }

            //Validar que no se este evadiendo el check de requiere aprobacion
            if (!this.requeiereAprobacion) {
                logger.info("Verificando si el producto es cotizable automaticamente antes de enviar la cotizacion.");
                RequestContext context = RequestContext.getCurrentInstance();
                boolean productoCotizableAutomaticamente = validarProductoEsCotizableAutomatico();

                if (!productoCotizableAutomaticamente && this.selectProductoCotizacion.size() > 0) {
                    context.execute("PF('productoAutomaticoDialogVar').show();");
                    this.requeiereAprobacion = true;
                    return false;
                }
            }

            if (this.cotizacion_valida_oferta()) {
                String nameDocument = "";
                int idDocumentoContent = 0;
                String webLocation = "";
                String codigoCotizacion = "";
                if (this.requeiereAprobacion) {
                    logger.info("Creando Archivo [Requiere Aprobacion]");
                    if (file != null) {
                        nameDocument = file.getFileName();
                        HashMap<String, String> rFile = upload(file.getInputstream(), file.getFileName(), file.getContentType(), "ListaChequeo");
                        if (rFile == null) {
                            idDocumentoContent = -1;
                        } else {
                            if (!rFile.isEmpty()) {
                                idDocumentoContent = Integer.parseInt(rFile.get("id"));
                                webLocation = rFile.get("webLocation");
                            }
                        }
                    }
                }
                if (idDocumentoContent >= 0) {
                    CotizacionesVO cotizacionVO = new CotizacionesVO();
                    cotizacionVO.setNombreUsuario(userSession.getUserLogin());
                    cotizacionVO.setRequiereAprobacion("TRUE");
                    cotizacionVO.setIdOportunidad(this.oportunidadSelect.getIdOportunidad());//HARDCODE ...
                    cotizacionVO.setNombreOportunidad(this.convertirCadena(this.oportunidadSelect.getNombreOportunidad()));//HARDCODE Viene de la oportunidad
                    //cotizacionVO.setNombreOportunidad(this.oportunidadSelect.getNombreOportunidad());//HARDCODE Viene de la oportunidad
                    cotizacionVO.setIdCliente(this.oportunidadSelect.getNitCliente());//HARDCODE Viene de la oportunidad
                    cotizacionVO.setNombreCliente(this.oportunidadSelect.getNombreCliente());//HARDCODE Viene de la oportunidad
                    //cotizacionVO.setNombreCliente(this.oportunidadSelect.getNombreCliente());//HARDCODE Viene de la oportunidad
                    cotizacionVO.setFechLimiteGeneracion(DateUtil.formatBPMTime(this.cotizacion.getFechavencimientosolicitud()));
                    cotizacionVO.setFechaEstimadaEntrega(DateUtil.formatBPMTime(this.cotizacion.getFechaEstimadaEntrega()));
                    cotizacionVO.setFechaEstimadaFacturacion(DateUtil.formatBPMTime(this.cotizacion.getFechaEstimadaFacturacion()));
                    cotizacionVO.setFechaEstimadaPedidoVenta(DateUtil.formatBPMTime(this.cotizacion.getFechaEstimadaPedidoVenta()));
                    if (!requeiereAprobacion) {
                        cotizacionVO.setTieneDescuento(this.cotizacion.getTieneDescuento());
                        cotizacionVO.setPorcentajeDescuento(this.cotizacion.getPorcentajeDescuento().toString());
                    } else {
                        cotizacionVO.setTieneDescuento("NO");
                        cotizacionVO.setPorcentajeDescuento("0");
                    }

                    cotizacionVO.setInteligenciaCompetencia(this.cotizacion.getInteligenciaCompetencia());
                    cotizacionVO.setInteligenciaObservaciones(this.cotizacion.getInteligenciaObservaciones());
                    cotizacionVO.setInteligenciaRazon(this.cotizacion.getInteligenciaRazon());

                    String lineaInput = selectProductoCotizacion.get(0).getLinea();
                    Long tipoId = 0L;
                    try {
                        tipoId = iTipoService.findByEtiqueta(tipo).getIdTipo();
                        logger.debug("Obteniendo el Valor de Tipo" + Long.toString(tipoId));
                        logger.info("Obteniendo el Valor de Tipo" + Long.toString(tipoId));
                    } catch (ServiceException ex) {
                        logger.error("Error capturando tipo de Oferta", ex);
                    }
                    Long tipoEstadoCot = 0L;
                    try {
                        tipoEstadoCot = iTipoService.findByEtiqueta("Pendiente").getIdTipo();
                    } catch (ServiceException ex) {
                        logger.error("Error capturando tipo de Estado cot", ex);
                    }
                    if (this.incotermLong == null) {
                        cotizacionVO.setIdInconterm("0");
                    } else {
                        cotizacionVO.setIdInconterm("" + this.incotermLong);
                    }

                    if (this.cotizacion.getCodigoVersion() == null) {
                        cotizacionVO.setTipoOferta(Long.toString(tipoId));
                        logger.info("No es version" + Long.toString(tipoId));
                    } else {
                        logger.info("Cotizacion Versionada " + Long.toString(tipoId) + '*' + this.cotizacion.getCodigoVersion());
                        cotizacionVO.setTipoOferta(Long.toString(tipoId) + '*' + this.cotizacion.getCodigoVersion());
                    }
                    cotizacionVO.setIdEstadoCotizacion(Long.toString(tipoEstadoCot));//Estado Pendiente
                    cotizacionVO.setObservAsesor(this.convertirCadena(this.cotizacion.getObservacionasesor()));
                    //cotizacionVO.setObservAsesor(this.cotizacion.getObservacionasesor());
                    cotizacionVO.setDirigida(this.convertirCadena(this.cotizacion.getDirigida()));
                    // cotizacionVO.setDirigida(this.convertirCadena(this.dirigida.getNombre()+" "+this.dirigida.getApellido()));
                    //cotizacionVO.setDirigida(this.cotizacion.getDirigida());
                    cotizacionVO.setTelefono(this.cotizacion.getTelefono());
                    if (this.cotizacion.getTelefono() == null) {
                        cotizacionVO.setTelefono("0000");
                    } else {
                        cotizacionVO.setTelefono(this.cotizacion.getTelefono());
                    }

                    if (this.cotizacion.getDireccion() == null) {
                        cotizacionVO.setDireccion("N/A");
                    } else {
                        cotizacionVO.setDireccion(this.cotizacion.getDireccion());
                    }
                    if (this.cotizacion.getCorreo() == null) {
                        cotizacionVO.setCorreo("N/A");
                    } else {
                        cotizacionVO.setCorreo(this.cotizacion.getCorreo());
                    }
                    //cotizacionVO.setDireccion(this.cotizacion.getDireccion());
                    //cotizacionVO.setCorreo(this.cotizacion.getCorreo());
                    //Verificacion Cotizacion Automatica
                    if (!this.requeiereAprobacion) {
                        cotizacionVO.setRequiereAprobacion("FALSE");
                        logger.info("Creando Archivo Cot Automatica [No Requiere Aprobacion]");
                        String path = "/tmp/";
                        nameDocument = "Cotizacion_" + userSession.getUserLogin() + "_" + System.currentTimeMillis() + ".docx";
                        String letraTipo = "V";
                        if ("Principal".equalsIgnoreCase(tipo.trim())) {
                            letraTipo = "P";
                        } else if ("Alternativa".equalsIgnoreCase(tipo.trim())) {
                            letraTipo = "A";
                        }
                        logger.info("Linea " + this.userSession.getUsuario().getLinea() + " " + letraTipo);

                        //Capturar el error en caso que no se logre establecer conexion del Entity Manager
                        codigoCotizacion = iCotizacionService.procCoodigoCotizacion(userSession.getUsuario().getLinea(), letraTipo);

                        //Se crear HASHMAP, se debe llenar con datos del EBS
                        HashMap<String, String> codReplace = new HashMap<String, String>();
                        codReplace.put("L001CIUDAD", userSession.getUsuario().getCiudad());
                        codReplace.put("L002FECHA", DateUtil.formatShortDateTime(new Date()));
                        codReplace.put("L003NOMBRECLIENTE", oportunidadSelect.getNombreCliente());
                        codReplace.put("L004LINEA", userSession.getUsuario().getLinea());
                        codReplace.put("L0041LINEA", userSession.getUsuario().getLinea());
                        codReplace.put("L005CODCOT", codigoCotizacion);
                        codReplace.put("L006PRODUCTO", selectProductoCotizacion.get(0).getNombre());
                        codReplace.put("L007MARCA", selectProductoCotizacion.get(0).getMarca());
                        codReplace.put("L008GERENTELINEA", "Ing. Gerente Linea");
                        codReplace.put("L009ASESOR", "Ing. " + userSession.getUsuario().getNombre());
                        codReplace.put("L0010LINKIMAGENMAQUINA", selectProductoCotizacion.get(0).getLinkImg());
                        codReplace.put("L0011LINKTEC", selectProductoCotizacion.get(0).getLinkCaracteristicas());
                        codReplace.put("L0012LINKACCESORIOS", selectProductoCotizacion.get(0).getLinkAccesorios());
                        codReplace.put("L0013LINKVIDEO", selectProductoCotizacion.get(0).getLinkVideo());
                        codReplace.put("L0014DIRIGIDA", cotizacion.getDirigida());
                        codReplace.put("L0015DIRECCION", (cotizacion.getDireccion() == null ? "" : cotizacion.getDireccion()));
                        codReplace.put("L0016TELEFONO", (cotizacion.getTelefono() == null ? "" : cotizacion.getTelefono()));
                        codReplace.put("L0017EMAIL", cotizacion.getCorreo());
                        //

                        cotizacionVO.setCodigo(codigoCotizacion);
                        logger.info("Codigo Cotizacion: " + codigoCotizacion);
                        CreateDocx ct = new CreateDocx(codReplace, nameDocument);
                        InputStream is = ct.replace();
                        HashMap<String, String> rFile = upload(is, nameDocument, "application/docx", "CotizacionAutomatica");
                        idDocumentoContent = Integer.parseInt(rFile.get("id"));
                        webLocation = rFile.get("webLocation");
                        logger.info("Documento Cargado:  " + idDocumentoContent);
                    } else {
                        cotizacionVO.setRequiereAprobacion("TRUE");
                    }
                    if (idDocumentoContent > 0) {

                        cotizacionVO.setIdArchivoAdjSolicitud(Integer.toString(idDocumentoContent));//UCM
                        cotizacionVO.setNombreArchivoAdjSolicitud(nameDocument);//UCM
                        cotizacionVO.setUrlContentArchivoAdjSolicitud(webLocation); //-agregar cotizacionesVO;      

                    }

                    /*if (getMostrarLinea()) {
                     lineaInput = this.linea;
                     }*/
                    cotizacionVO.setLinea(lineaInput);
                    cotizacionVO.setFechaSolicitud(DateUtil.formatBPMTime(new Date()));//Fecha Actual
                    List<CotizacionProductoVO> listaCotizacionProducto = new ArrayList<CotizacionProductoVO>();

                    float vValor_total = 0;

                    for (ProductoVO prd : this.selectProductoCotizacion) {
                        CotizacionProductoVO producto = new CotizacionProductoVO();
                        logger.info("Producto Cantidad: " + prd.getCantidad());
                        logger.info("Valor Unitario: " + prd.getValorUnitario());
                        logger.info("Precion Lista Set Unitario: " + prd.getPrecioListaSinFormato());
                        logger.info("Precion Lista: " + prd.getPrecioLista());
                        logger.info("Moneda: " + prd.getMoneda());
                        producto.setIdProducto(prd.getCodigo());
                        if (prd.getCantidad() == null) {
                            producto.setCantidad("1");
                        } else if (prd.getCantidad().equalsIgnoreCase("0")) {
                            producto.setCantidad("1");
                        } else {
                            producto.setCantidad(prd.getCantidad());
                        }
                        producto.setModelo(prd.getModelo());
                        producto.setMarca(prd.getMarca());
                        producto.setValorUnitario((prd.getPrecioListaSinFormato() == null || prd.getPrecioListaSinFormato().equals("")) ? "0" : prd.getPrecioListaSinFormato());
                        producto.setNombreProducto(prd.getNombre());
                        if (prd.getMoneda() != null) {
                            cotizacionVO.setIdTipoMoneda(prd.getMoneda());
                        } else {
                            cotizacionVO.setIdTipoMoneda("");
                        }
                        listaCotizacionProducto.add(producto);
                        vValor_total = vValor_total + ((Float.valueOf(producto.getValorUnitario()).floatValue() * Float.valueOf(producto.getCantidad()).floatValue()));
                    }

                    cotizacionVO.setCotizacionProducto(listaCotizacionProducto);

                    logger.info("Valor total : " + vValor_total);

                    // Validar el descuento si aplica a la cotizacion automatica
                    logger.info("TIENE DESCUENTO ? : " + this.cotizacion.getTieneDescuento());

                    if (!requeiereAprobacion) {

                        if (this.cotizacion.getTieneDescuento().equalsIgnoreCase("SI")) {
                            int porcentaje = this.cotizacion.getPorcentajeDescuento();
                            logger.info("Entro con descuento al : " + porcentaje);
                            vValor_total = vValor_total - ((vValor_total * porcentaje) / 100);
                            logger.info("Valor con descuento ? : " + vValor_total);
                        }
                    }

                    cotizacionVO.setValorTotal(String.valueOf(vValor_total));
                    //se convierte el objeto a String
                    logger.info("Cotizacion Utils.marshal(cotizacionVO)");
                    String strRequest = Utils.marshal(cotizacionVO);
                    logger.info("Cotizacion Request Linea: " + strRequest);

                    //se pasan los unicos 2 parÃ¡metros
                    String[] paramsService = new String[numeroParametrosWS];
                    paramsService[0] = strRequest;
                    paramsService[1] = WS_PROCESS_ENTITY_RESULTADO_COTIZACION;
                    request.setParams(paramsService);

                    userSession.getClientWs().consumeService(request);
                    return true;
                }
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error Creando una cotización: " + ex.getMessage(), ex);
            return false;

        } catch (JAXBException ex) {
            logger.error("Error Creando una cotización: " + ex.getMessage(), ex);
            return false;
        } catch (IOException ex) {
            logger.error("Error Creando una cotización: " + ex.getMessage(), ex);
            return false;
        }
        return false;
    }

    /**
     * Busca las productos que se consultan en el inventario, esto aplica cuando
     * es alternativa
     *
     *
     * @return
     */
    public List<ProductoVO> getProductoCotizacion() {
        if (this.getChangeTipoOferta().equals("Alternativa")) {
            if (productoCotizacion != null) {
                productoCotizacion.clear();

            }
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class
            );
            InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
            if (inventarioConsultaFacesBean
                    != null && inventarioConsultaFacesBean.getListaProductos()
                    != null) {
                productoCotizacion.addAll(inventarioConsultaFacesBean.getListaProductos());
            }
        }
        return productoCotizacion;
    }

    private String convertirCadena(String cadena) throws UnsupportedEncodingException {
        return cadena == null ? "" : new String(cadena.getBytes("iso-8859-1"), "UTF-8");
    }

    public void setProductoCotizacion(List<ProductoVO> productoCotizacion) {
        this.productoCotizacion = productoCotizacion;
    }

    public ProductoVO getProductoAgregar() {
        return productoAgregar;
    }

    public void setProductoAgregar(ProductoVO productoAgregar) {
        this.productoAgregar = productoAgregar;
    }

    public ProductoVO getProductoBorrar() {
        return productoBorrar;
    }

    public void setProductoBorrar(ProductoVO productoBorrar) {
        this.productoBorrar = productoBorrar;
    }

    public List<ProductoVO> getSelectProductoCotizacion() {
        return selectProductoCotizacion;
    }

    public void setSelectProductoCotizacion(List<ProductoVO> selectProductoCotizacion) {
        this.selectProductoCotizacion = selectProductoCotizacion;
    }

    public List<OportunidadVO> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<OportunidadVO> oportunidades) {
        this.oportunidades = oportunidades;
    }

    public OportunidadVO getOportunidadSelect() {
        return oportunidadSelect;
    }

    public void setOportunidadSelect(OportunidadVO oportunidadSelect) {
        this.oportunidadSelect = oportunidadSelect;
    }

    public Boolean getIsTipoPrincipal() {
        Boolean pr = this.getChangeTipoOferta().equals("Principal");
        return pr;
        //return isTipoPrincipal;
    }

    public void setIsTipoPrincipal(Boolean isTipoPrincipal) {
        this.isTipoPrincipal = isTipoPrincipal;
    }

    public String getNombreOportunidadSearch() {
        return nombreOportunidadSearch;
    }

    public void setNombreOportunidadSearch(String nombreOportunidadSearch) {
        this.nombreOportunidadSearch = nombreOportunidadSearch;
    }

    public Boolean getAgregarProductos() {
        //Si lista esta vacia se puede agregar
        if (this.selectProductoCotizacion == null) {
            return true;
        }
        //Si lista no contiene elemento se puede agregar
        if (this.selectProductoCotizacion.isEmpty()) {
            return true;
        }
        return false;
    }

    public void handleContactoChange() {
        logger.info("Cotizaciones select contacto...");
        if (this.cotizacion != null && this.cotizacion.getDirigida() != null) {
            for (ContactoCliente cc : this.contactosClienteDirigidoA) {
                String vfind = cc.getNombre() + " " + cc.getApellido();
                logger.info("dirigidoa : " + vfind + " - " + this.cotizacion.getDirigida());

                if (vfind.equals(this.cotizacion.getDirigida())) {
                    this.cotizacion.setTelefono(cc.getTelefono());
                    this.cotizacion.setCorreo(cc.getCorreo());
                }
            }
        }
    }

    public void setAgregarProductos(Boolean agregarProductos) {
        this.agregarProductos = agregarProductos;
    }

    public Boolean getRequeiereAprobacion() {
        return requeiereAprobacion;
    }

    public void setRequeiereAprobacion(Boolean requeiereAprobacion) {
        this.requeiereAprobacion = requeiereAprobacion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Boolean getMostrarLinea() {
        if (this.userSession.getUsuario().getLinea().equals("VENDEDORES SUCURSALES") || (this.userSession.getUsuario().getLinea().equals("VENDEDORES DIVISION METALMECANICA"))) {
            return true;
        }
        return false;
    }

    public void setMostrarLinea(Boolean mostrarLinea) {
        this.mostrarLinea = mostrarLinea;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Tipo> getListaIncoterm() {
        return listaIncoterm;
    }

    public void setListaIncoterm(List<Tipo> listaIncoterm) {
        this.listaIncoterm = listaIncoterm;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public Long getIncotermLong() {
        return incotermLong;
    }

    public void setIncotermLong(Long incotermLong) {
        this.incotermLong = incotermLong;
    }

    public List<ClaveValorDTO> getPorcentajeDescuentos() {
        return porcentajeDescuentos;
    }

    public void setPorcentajeDescuentos(List<ClaveValorDTO> porcentajeDescuentos) {
        this.porcentajeDescuentos = porcentajeDescuentos;
    }

    public List<ClaveValorDTO> getListaRazonCompetenciaDTO() {
        return listaRazonCompetenciaDTO;
    }

    public void setListaRazonCompetenciaDTO(List<ClaveValorDTO> listaRazonCompetenciaDTO) {
        this.listaRazonCompetenciaDTO = listaRazonCompetenciaDTO;
    }

    private ClienteVO detalleClienteNit(String nit) {
        try {
            String[] paramsService = new String[numeroParametrosWS_detalleCliente];
            paramsService[0] = nit != null ? nit : "";
            paramsService[1] = "";
            paramsService[2] = "%";
            paramsService[3] = "";
            paramsService[4] = "";
            paramsService[5] = "0";
            this.requestDetalleCliente.setParams(paramsService);
            String responseStr = userSession.getClientWs().consumeService(requestDetalleCliente);
            System.out.println("Response Clientes: " + responseStr);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response != null && response.getClientes() != null) {
                return response.getClientes().get(0);
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error buscando cliente " + nit + " " + ex.getMessage(), ex);
        } catch (UtilException ex) {
            logger.error("Error buscando cliente " + nit + " " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("Error buscando cliente " + nit + " " + ex.getMessage(), ex);
        }
        return null;
    }

    
    //TODO Separar la inicializacion de la lista de ContactosClienteDirigidoA del getter
    public List<ContactoCliente> getContactosClienteDirigidoA() {
        //se valida si las lista de contacto esta vacia
        if (contactosClienteDirigidoA.isEmpty()) {
            if (this.oportunidadSelect != null) {
                //invocar ws de detalle de cliente por nit.                        
                ClienteVO _cliente = this.detalleClienteNit(this.oportunidadSelect.getNitCliente());

                ContactoVO empresaContacto = new ContactoVO();
                empresaContacto.setId("1");
                empresaContacto.setNombre(this.oportunidadSelect.getNombreCliente());
                empresaContacto.setApellido("");
                empresaContacto.setCargo("");

                if (_cliente != null) {
                    empresaContacto.setTelefono(_cliente.getTelefonoPpal());
                    empresaContacto.setCorreo(_cliente.getCorreoCliente());
                }
                contactosClienteDirigidoA.add(new ContactoCliente(empresaContacto));
            }
        }
        return contactosClienteDirigidoA;
    }

    public void setContactosClienteDirigidoA(List<ContactoCliente> contactosClienteDirigidoA) {
        this.contactosClienteDirigidoA = contactosClienteDirigidoA;
    }

    private Boolean productoCotizable(String producto) {

        String responseStr;

        try {
            logger.info("*********** buscando producto es cotizable " + producto);
            String[] paramsService = new String[numeroParamatrosWSProductoCotizableAutomatico];
            paramsService[0] = producto;
            this.requestProductoCotizableAutomatico.setParams(paramsService);
            responseStr = userSession.getClientWs().consumeService(requestProductoCotizableAutomatico);

            logger.info("*********** Response productoCotizable: " + responseStr + "***********");

            //Si la respuesta es NULL retornar False, porque indica que el producto NO es cotizable automaticamente
            if (responseStr == null || responseStr.equals("")) {
                logger.info("*********** Response productoCotizable es NULL ****** " + responseStr);
                return false;
            }

            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            if (response != null && response.getProductos() != null) {
                logger.info("*********** Response producto " + response.getProductos().get(0).getOrganizationId());
                ProductoVO productoResponse = response.getProductos().get(0);
                return productoResponse.getOrganizationId().equals("Y");
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("error buscando producto cotizable " + ex.getMessage(), ex);
        } catch (UtilException ex) {
            logger.error("error buscando producto cotizable " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("error buscando producto cotizable " + ex.getMessage(), ex);
        }
        return false;
    }

    public CotizacionesCrearBean(Boolean productoEsCotizableAutomatico) {
        this.productoEsCotizableAutomatico = productoEsCotizableAutomatico;
    }

    public Boolean getProductoEsCotizableAutomatico() {
        return productoEsCotizableAutomatico;
    }

    /**
     * determina si un producto sirve para una cotización automaticca
     *
     * @return
     */
    public Boolean validarProductoEsCotizableAutomatico() {
        logger.info("Metodo validarProductoEsCotizableAutomatico *********");
        /* if (this.selectProductoCotizacion!=null&&!this.selectProductoCotizacion.isEmpty()){
            return this.selectProductoCotizacion.get(0).get
        }*/
        if (this.selectProductoCotizacion != null && !this.selectProductoCotizacion.isEmpty()) {
            boolean esProductoCotizableAut = productoCotizable(this.selectProductoCotizacion.get(0).getCodigo());
            logger.info("esProductoCotizableAut ? ->" + esProductoCotizableAut);

            //FacesContext.getCurrentInstance().addMessage("template:cotizacion-crear-form:tablaProductosSeleccionados", new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Producto No es cotizable automatico"));
            return esProductoCotizableAut;
        }
        return false;
    }

}
