/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.services.ejb3.OracleCanalesEbsImpl;
import com.imocom.intelcom.services.interfaces.IOracleConsultas;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.dtos.ClaveValorDTO;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_CREACION_EXCEL;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_MONEDA;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EXITO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EJECUCION;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOMBRE_ESTAPA_ESTADO;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOMBRE_ESTAPA_ESTADO_CLIENTE;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_DETALLE;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;

import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.view.util.ReadExcel;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadesBPMResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ViewScoped
public class OportunidadesCrearFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidadesCrearFacesBean.class);
    private static final long serialVersionUID = 1L;

    private List<Tipo> listaTipoOportunidad;
    private List<Tipo> listaMoneda;
    //private List<Tipo> listaIncoterm;
    private List<Tipo> listaProbabilidadExito;
    private List<Tipo> listaProbabilidadEjecucion;
    private List<ClaveValorDTO> listaCanalEntrada;
    private List<ProductoVO> listaProductosSeleccionados;

    private Oportunidad oportunidad;
    private ProductoVO productoBorrar;
    private ProductoVO productoAgregar;

    private String nit;
    private String nombreCliente;
    private Date fechaCierre;
    private Date fechaOrdenCompra;
    private Date fechaPedido;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;
    private Boolean agregarProductos;
    //Archivo de carga de Excel
    private UploadedFile fileExcel;
    private MiddlewareServiceRequestVO requestExcel;
    List<OportunidadBPMVO> listOporErrorExcel;
    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestProducto;
    /**
     * NÃƒÂºmero de paramatos para request de CreaciÃƒÂ³n de CotizaciÃƒÂ³n
     */
    private int numeroParamatrosWSProducto = 0;
    
    /**
     * Parametros para invocar MDW para Buscar una oportunidad por etapa estado y NIT del cliente
     */
    private MiddlewareServiceRequestVO requestOportunidadesEstapaEstadoCliente;
    
    /**
     * Numero de parametros para request de busqueda de una oportunidad por Etapa Estado y Nit de cliente
     */
    private int numeroParamatrosWSOportunidadEtapaEstadoCliente = 0;
    
    private String linea;

    /*
     private MiddlewareServiceRequestVO requestProductos;
     private int numeroParametrosWS_produtos = 0;
     */
    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    private IOracleConsultas iOracleConsultas;

    @EJB
    private IServicioGenericoLocal<Long, Parametros> iServicioGenerico;

    @Override
    protected void build() {
        try {
            /**
             * Se carga la linea del asesor
             */

            this.linea = this.userSession.getUsuario().getLinea();
            this._initLineaInventarioConsulta(linea);
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class);
            InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
            inventarioConsultaFacesBean.listaMarcaPorLinea();
            //Inicializamos el objeto Oportunidad y las listas de productos
            oportunidad = new Oportunidad();
            //gfandino Se adiciona temporalmente para que por defecto se muestre FERIA
            oportunidad.setIdCanalEntrada(Constants.TEMP_FERIA);
            listaProductosSeleccionados = new ArrayList<ProductoVO>();

            //Consulta de tipos de Oportunidad
            listaTipoOportunidad = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD);
            //Consulta de tipos de Moneda
            listaMoneda = iTipoService.findByTipoNombre(TIPO_MONEDA);

            //Consulta de tipos de Probabilidad Exito
            listaProbabilidadExito = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EXITO);

            //Consulta de tipos de Probabilidad Ejecucion
            listaProbabilidadEjecucion = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);

            //Consulta de tipos de Etapas de la oportunidad            
            //listaCanalEntrada = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD_CANAL);
            try {
                iOracleConsultas = new OracleCanalesEbsImpl();

                //List<Tipo> lista = iTipoService.findByTipoNombre( Constants.TIPO_LEAD_CANAL );            
                listaCanalEntrada = new ArrayList<ClaveValorDTO>();
                List<Tipo> listaCanales = iOracleConsultas.getCanalesEBS(iServicioGenerico);
                for (Tipo kv : listaCanales) {
                    listaCanalEntrada.add(new ClaveValorDTO(kv.getTipoNombre(), kv.getTipoValor()));
                }

            } catch (Exception e) {
                logger.error("ERROR AL CARGAR CANALES: " + e.getMessage(), e);
            }

            listOporErrorExcel = new ArrayList<OportunidadBPMVO>();

            //Verificamos si el detalle es para un cliente especifico en sesión
            if (getSessionMap().get(CLIENT_ID_PARAM) != null) {
                ClienteVO clienteSeleccionado;
                clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
                logger.info("El cliente seleccionado es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccioando"));

                nit = clienteSeleccionado.getNitCliente();
                nombreCliente = clienteSeleccionado.getNombreCliente();

            } else {
                //verificamos si es una creación de Oportunidad desde resultado de la  visita
                if (getSessionMap().get(EVENT_ID_PARAM) != null) {
                    Visita eventoPadre = (Visita) getSessionMap().get(EVENT_ID_PARAM);
                    nit = "" + eventoPadre.getIdcliente();
                    nombreCliente = eventoPadre.getNombreCliente();
                }
            }

            //Armamos el objeto request
            armarRequestWS();

            //Armamos el objeto request para consulta de productos
            //armarRequestWS_Productos();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Inicializa objeto en el back bean de inventario consultas_ Linea
     */
    private void _initLineaInventarioConsulta(String _linea) {
        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class);
        InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
        inventarioConsultaFacesBean.setLinea(_linea);
    }

    private void armarRequestWS() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_CREACION);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWS = servicio.getNumeroParametros();

            requestExcel = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioExcel = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_CREACION_EXCEL);
            requestExcel.setEndpoint(servicioExcel.getInterfazEndpoint());
            requestExcel.setMethod(servicioExcel.getMetodo());
            requestExcel.setNamespacePort(servicioExcel.getNamespace());
            requestExcel.setServiceName(servicioExcel.getQnameServicio());
            requestExcel.setWsdlUrl(servicioExcel.getUrlWsdl());
            requestExcel.setInterfaceType(servicioExcel.getTipoInterfaz());

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
            //Cargamos el nÃƒÂºmero de parametros
            numeroParamatrosWSProducto = servicio.getNumeroParametros();

            
            /**
             * Se arma Request para buscar oportunidad por nombreEtapaEstadoCliente
             */
            requestOportunidadesEstapaEstadoCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioOpEtapaEstadoCliente = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_NOMBRE_ESTAPA_ESTADO_CLIENTE);
            requestOportunidadesEstapaEstadoCliente.setEndpoint(servicioOpEtapaEstadoCliente.getInterfazEndpoint());
            requestOportunidadesEstapaEstadoCliente.setMethod(servicioOpEtapaEstadoCliente.getMetodo());
            requestOportunidadesEstapaEstadoCliente.setNamespacePort(servicioOpEtapaEstadoCliente.getNamespace());
            requestOportunidadesEstapaEstadoCliente.setServiceName(servicioOpEtapaEstadoCliente.getQnameServicio());
            requestOportunidadesEstapaEstadoCliente.setWsdlUrl(servicioOpEtapaEstadoCliente.getUrlWsdl());
            requestOportunidadesEstapaEstadoCliente.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el numero de parametros
            numeroParamatrosWSOportunidadEtapaEstadoCliente = servicioOpEtapaEstadoCliente.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

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

    private boolean ejecutarCrearAction() throws Exception {

        if (listaProductosSeleccionados == null || listaProductosSeleccionados.isEmpty()) {
            performErrorMessages("Para la creación de la oportunidad es necesario seleccionar al menos un producto");
            return false;
        }

        //VALIDAMOS TODOS LOS PRODUCTOS SELECCIONADOS
        for (ProductoVO producto : listaProductosSeleccionados) {
            //Se validad que la cantidad del producto sea númerica
            String cantidadProducto = producto.getCantidad();
            if (!Utils.isNumeric(cantidadProducto)) {
                performErrorMessages("Cantidad del Producto No es númerica o Contiene espacios.");
                return false;
            }
            listaCodigoProductosAcotizacion.add(producto);
        }

        //se guarda de nuevo el producto con eliminaciòn de espacios.
        //producto.setCantidad(cantidadProducto);
        //listaProductosSeleccionados.add(producto);
        if (nit == null || nit.equals("")) {
            performErrorMessages("Para la creación de la oportunidad es necesario un cliente");
            return false;
        }

        return true;
    }

    /**
     * Proceso de creacion de Oportunidades con Solicitud Requiere que sea
     * inicializada la Oportunidad Creada por el WS de BPM para obtener el
     * idOportunidad que sera enviado a la pantalla de Cotizacion
     */
    
    //Objeto requerido para buscar la oportunidad recientemente creada
    //Se inicializa durante el envio de la solicitud
    private OportunidadVO oportunidadACotizacionCreada;

    /*public OportunidadVO getOportunidadACotizacionVO() {
        return oportunidadACotizacionVO;
    }
    
    public void setOportunidadACotizacionVO(OportunidadVO oportunidadACotizacionVO) {
        this.oportunidadACotizacionVO = oportunidadACotizacionVO;
    }*/
    private List<ProductoVO> listaCodigoProductosAcotizacion;

    public void crearActionConSolicitud(ActionEvent actionEvent) {
        logger.info("******** Metodo crearActionConSolicitud");

        try {

            //idOportunidadAcotizacion = null;
            listaCodigoProductosAcotizacion = new ArrayList<ProductoVO>();

            if (!ejecutarCrearAction()) {
                return;
            }

            if (enviarSolicitud()) {
                logger.info("******** Metodo crearActionConSolicitud -> EnviarSolicitud");
                                
                //Se debe poner un Sleep de thread para esperar que la solicitud halla sido creada
                logger.info("Esperando que se cree la oportunidad.");
                Thread.sleep(8000);

                //Despues que se envia la solicitud, ya debe quedar en etapa 1
                // se debe buscar la ultima oportunidad por nombre, usuario y etapa
                List<OportunidadVO> listOPorNombreEtapaEstadoCliente = new ArrayList<OportunidadVO>();
                try {
                    logger.info("Buscando la oportunidad recientemente creada.");
                    String[] paramsService = new String[numeroParamatrosWSOportunidadEtapaEstadoCliente];
                    paramsService[0] = userSession.getUserLogin();
                    paramsService[1] = oportunidad.getNombreOportunidad() != null ? oportunidad.getNombreOportunidad() : "";
                    paramsService[2] = "11";
                    paramsService[3] = "%";
                    paramsService[4] = nit;
                    requestOportunidadesEstapaEstadoCliente.setParams(paramsService);
                    String responseStr;
                    responseStr = userSession.getClientWs().consumeService(requestOportunidadesEstapaEstadoCliente);
                    OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                    if (response != null && response.getOportunidades() != null) {
                        listOPorNombreEtapaEstadoCliente = response.getOportunidades();
                    }
                } catch (IntelcomMiddlewareException ex) {
                    logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
                    performWarningMessages("Ha ocurrido un problema durante la redireccion a la vista de cotización. Sin embargo, La oportunidad fue creada exitosamente, por favor, dirijase a las Consultas de Oportunidades.");
                } catch (UtilException ex) {
                    logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
                    performWarningMessages("Ha ocurrido un problema durante la redireccion a la vista de cotización. Sin embargo, La oportunidad fue creada exitosamente, por favor, dirijase a las Consultas de Oportunidades.");
                }
                
                if(listOPorNombreEtapaEstadoCliente.isEmpty()){
                    performInfoMessages("La oportunidad fue creada exitosamente, pero ocurrio un problema al direccionarlo a la siguiente página, por favor, dirijase a las Consultas de Oportunidades.");
                    build();
                    return;
                }
                
                //Se obtiene el objeto de Oportunidad del primer elemento de la lista
                if(!listOPorNombreEtapaEstadoCliente.isEmpty() && listOPorNombreEtapaEstadoCliente.size() > 0){
                    oportunidadACotizacionCreada = listOPorNombreEtapaEstadoCliente.get(0);  
                    
                    //Se limpian los parametros de la sesion utilizados en la vista de cotizacion
                    getSessionMap().remove("OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD");
                    getSessionMap().remove("OPORTUNIDAD_A_COTIZACION_LISTA_PRODUCTOS");
                    
                    //Se inicializa la Oportunidad a visualizar en la pantalla de cotizacion
                    getSessionMap().put(OPORTUNIDAD_ID_PARAM, oportunidadACotizacionCreada);
                    
                    //se inicializa el ID de la oportunidad
                    getSessionMap().put("OPORTUNIDAD_A_COTIZACION_ID_OPORTUNIDAD", oportunidadACotizacionCreada.getIdOportunidad());
                    
                    //Se inicializan los productos para la pantalla de cotizacion
                    getSessionMap().put("OPORTUNIDAD_A_COTIZACION_LISTA_PRODUCTOS", listaCodigoProductosAcotizacion);
                    
                    logger.info("Oportunidad Inicializada para cotizacion = " + oportunidadACotizacionCreada.getIdOportunidad());
                }

                
                // Se redirecciona a la pagina de consulta enviando en el session map el objeto OPORTUNIDAD_ID_PARAM
                //recientemente creado
                
                String outcome = getViewRedirect(Constants.PAGE_COTIZACIONES_CREAR_KEY);

                try {
                    redirect(navigationFaces.navigation.get(outcome));
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }

            } else {
                performErrorMessages("Ha ocurrido un problema: la solicitud de creación de oportunidad no ha podido ser enviada ");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            performErrorMessages("Error:: Ha ocurrido un problema :la solicitud de creación de oportunidad no ha podido ser enviada ");
        }

    }
    

    public void crearAction(ActionEvent actionEvent) {

        try {

            listaCodigoProductosAcotizacion = new ArrayList<ProductoVO>();

            if (!ejecutarCrearAction()) {
                return;
            }

            if (enviarSolicitud()) {

                // Se redirecciona a la pagina de consulta
                String outcome = getViewRedirect(PAGE_OPORTUNIDADES_CONSULTA_KEY);

                try {
                    redirect(navigationFaces.navigation.get(outcome));
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }

            } else {
                performErrorMessages("Ha ocurrido un problema la solicitud de creación de oportunidad no ha podido ser enviada ");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            performErrorMessages("Error:: Ha ocurrido un problema la solicitud de creación de oportunidad no ha podido ser enviada ");
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public boolean enviarSolicitud() throws Exception {

        int contadorProducto = 0;
        for (ProductoVO productoVO : listaProductosSeleccionados) {

            try {

                contadorProducto += 1;

                List<ProductoVO> listaProductosSeleccionadosTMP = new ArrayList<ProductoVO>();
                listaProductosSeleccionadosTMP.add(productoVO);

                OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();
                oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
                if (listaProductosSeleccionados.size() == 1) {
                    oportunidadBPMVO.setNombreOportunidad(oportunidad.getNombreOportunidad());
                } else {
                    oportunidadBPMVO.setNombreOportunidad(oportunidad.getNombreOportunidad() + " - " + contadorProducto);
                }
                //idOportunidadAcotizacion = oportunidad.getNombreOportunidad();

                oportunidadBPMVO.setLinea(listaProductosSeleccionadosTMP.get(0).getLinea());

                oportunidadBPMVO.setIdCliente(nit);
                oportunidadBPMVO.setNombreCliente(nombreCliente);
                oportunidadBPMVO.setIdTipoOportunidad(oportunidad.getIdTipoOportunidad());
                oportunidadBPMVO.setIdIncoterm(oportunidad.getIdIncoterm());

                oportunidadBPMVO.setIdCanalEntrada(oportunidad.getIdCanalEntrada());
                oportunidadBPMVO.setProbabilidadProyecto(oportunidad.getIdProbabilidadEjecucion());
                oportunidadBPMVO.setProbabilidadImocom(oportunidad.getIdProbabilidadExito());
                oportunidadBPMVO.setObservacion(oportunidad.getObservacionPedido());

                oportunidadBPMVO.setFechaEstimadaCierre(DateUtil.formatBPMTime(fechaCierre));
                //////oportunidadBPMVO.setFechaOrdenCompra(DateUtil.formatBPMTime(fechaOrdenCompra));
                //////oportunidadBPMVO.setFechaPedido(DateUtil.formatBPMTime(fechaPedido));
                oportunidadBPMVO.setFechaCreacion(DateUtil.formatBPMTime(new Date()));
                oportunidadBPMVO.setFechaModificacion(DateUtil.formatBPMTime(new Date()));

                oportunidadBPMVO.setIdEstadoOportunidad(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
                oportunidadBPMVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
                oportunidadBPMVO.setOportunidadProducto(listaProductosSeleccionadosTMP);

                oportunidadBPMVO.setLinea(listaProductosSeleccionadosTMP.get(0).getLinea());
                if (listaProductosSeleccionadosTMP.get(0).getPrecioLista() == null || listaProductosSeleccionadosTMP.get(0).getPrecioLista().equals("")) {
                    oportunidadBPMVO.setMonto("0");
                } else {
                    oportunidadBPMVO.setMonto(listaProductosSeleccionadosTMP.get(0).getPrecioLista());
                }

                if (listaProductosSeleccionadosTMP.get(0).getMoneda() == null || listaProductosSeleccionadosTMP.get(0).getMoneda().equals("")) {
                    oportunidadBPMVO.setIdTipoMoneda("USD");
                } else {
                    oportunidadBPMVO.setIdTipoMoneda(listaProductosSeleccionadosTMP.get(0).getMoneda());
                }
                String strRequest = Utils.marshal(oportunidadBPMVO);

                logger.info(":OPORTUNIDAD_CREAR,USER:" + this.userSession.getUserLogin() + ":" + strRequest);
                //se pasan los unicos 2 parámetros
                String[] paramsService = new String[numeroParametrosWS];
                paramsService[0] = strRequest;
                paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
                request.setParams(paramsService);

                userSession.getClientWs().consumeService(request);

            } catch (JAXBException ex) {
                logger.error(ex.getMessage(), ex);
                return false;
            } catch (IntelcomMiddlewareException ex) {
                logger.error(ex.getMessage(), ex);
                return false;
            }
        }
        return true;
    }

    public String agregarProducto() {

        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class);
        InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
        if (inventarioConsultaFacesBean != null && inventarioConsultaFacesBean.getListaProductos() != null) {
            inventarioConsultaFacesBean.getListaProductos().remove(productoAgregar);
        }
        productoAgregar.setCantidad("");
        if (!listaProductosSeleccionados.contains(productoAgregar)) {
            String lineaSeleccionada = productoAgregar.getLinea();
            lineaSeleccionada = Utils.remove1(lineaSeleccionada);
            productoAgregar.setLinea(lineaSeleccionada);
            productoAgregar.setCantidad("1");
            listaProductosSeleccionados.add(productoAgregar);
        }
        linea = null;
        return null;
    }

    /**
     * Carga de oportunidades desde un archivo excel
     */
    public void cargarOportunidadesExcel() {

        try {
            logger.info("Cargando Documento Excel :  " + fileExcel.getFileName());
            listOporErrorExcel.clear();
            ReadExcel read = new ReadExcel();
            List<OportunidadBPMVO> oportunidadLista = read.do_proccess(fileExcel.getInputstream());
            if (oportunidadLista != null) {
                for (OportunidadBPMVO opr : oportunidadLista) {
                    if (opr.getRespuestaCreacion().equals("")) {
                        String responseStr = "";
                        Boolean upload = false;
                        String strRequest = "";
                        String mensajeError = "";
                        try {
                            //Se busca producto
                            ProductoVO productoVO = this.getProductoDetalle(opr.getOportunidadProducto().get(0).getCodigo());
                            if (productoVO != null) {
                                if (productoVO.getPrecioLista() == null || productoVO.getPrecioLista().equals("")) {
                                    opr.setMonto("0");
                                    productoVO.setValorUnitario("0");
                                    productoVO.setPrecioLista("0");
                                    productoVO.setPrecioListaSinFormato("0");
                                } else {
                                    opr.setMonto(productoVO.getPrecioLista());
                                    productoVO.setValorUnitario(productoVO.getPrecioLista());
                                    productoVO.setPrecioLista(productoVO.getPrecioLista());
                                    productoVO.setPrecioListaSinFormato(productoVO.getPrecioLista());
                                }
                                if (productoVO.getMoneda() == null || productoVO.getMoneda().equals("")) {
                                    opr.setIdTipoMoneda("USD");
                                } else {
                                    opr.setIdTipoMoneda(productoVO.getMoneda());
                                }
                                if (productoVO.getUnidad() == null || productoVO.getUnidad().equals("")) {
                                    productoVO.setUnidad("UND");
                                }
                                productoVO.setCantidad(opr.getOportunidadProducto().get(0).getCantidad());
                                opr.setLinea(productoVO.getLinea());
                                List<ProductoVO> productList = new ArrayList<ProductoVO>();
                                productList.add(productoVO);
                                opr.setOportunidadProducto(productList);
                                strRequest = Utils.marshal(opr);

                                //se pasan los unicos 2 parámetros
                                String[] paramsService = new String[numeroParametrosWS];
                                paramsService[0] = strRequest;
                                paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;

                                requestExcel.setParams(paramsService);

                                responseStr = userSession.getClientWs().consumeService(requestExcel);
                                if (responseStr != null) {
                                    OportunidadesBPMResponseVO response = (OportunidadesBPMResponseVO) Utils.unmarshal(OportunidadesBPMResponseVO.class, responseStr);
                                    if (response != null && response.getResultadoOperacion().equals("")) {
                                        request.setParams(paramsService);
                                        userSession.getClientWs().consumeService(request);
                                        Thread.sleep(2000);
                                        responseStr = userSession.getClientWs().consumeService(requestExcel);
                                        if (responseStr != null) {
                                            OportunidadesBPMResponseVO responseSalida = (OportunidadesBPMResponseVO) Utils.unmarshal(OportunidadesBPMResponseVO.class, responseStr);
                                            if (responseSalida != null && responseSalida.getResultadoOperacion().contains("creada")) {
                                                upload = true;
                                            } else {
                                                mensajeError = "La oportunidad no se ha cargado, subir linea nuevamente";
                                            }
                                        }

                                    } else {
                                        if (response != null) {
                                            mensajeError = response.getResultadoOperacion();
                                        } else {
                                            mensajeError = "Error Creando oportunidad";
                                        }
                                    }
                                }
                            } else {
                                mensajeError = "Producto no encontrado";
                            }

                        } catch (JAXBException ex) {
                            mensajeError = "Error Cargando Oportunidad : " + ex.getMessage();
                            logger.error(ex.getMessage());
                        } catch (IntelcomMiddlewareException ex) {
                            mensajeError = "Error Cargando Oportunidad : " + ex.getMessage();
                            logger.error(ex.getMessage());
                        } catch (UtilException ex) {
                            mensajeError = "Error Cargando Oportunidad : " + ex.getMessage();
                            logger.error(ex.getMessage());
                        } catch (Exception ex) {
                            mensajeError = "Error Cargando Oportunidad : " + ex.getMessage();
                            logger.error(ex.getMessage());
                        }
                        if (!upload) {
                            opr.setRespuestaCreacion(mensajeError);
                            listOporErrorExcel.add(opr);
                            logger.error("Error Cargando Oportunidad " + strRequest);
                        }
                    }

                }
            }
        } catch (IOException ex) {
            logger.error("Error Leyendo archivo");
        }
    }

    public String borrarProducto() {
        listaProductosSeleccionados.remove(productoBorrar);
        return null;
    }

    public List<Tipo> getListaTipoOportunidad() {
        return listaTipoOportunidad;
    }

    public void setListaTipoOportunidad(List<Tipo> listaTipoOportunidad) {
        this.listaTipoOportunidad = listaTipoOportunidad;
    }

    public List<Tipo> getListaMoneda() {
        return listaMoneda;
    }

    public void setListaMoneda(List<Tipo> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

    public List<Tipo> getListaProbabilidadExito() {
        return listaProbabilidadExito;
    }

    public void setListaProbabilidadExito(List<Tipo> listaProbabilidadExito) {
        this.listaProbabilidadExito = listaProbabilidadExito;
    }

    public List<Tipo> getListaProbabilidadEjecucion() {
        return listaProbabilidadEjecucion;
    }

    public void setListaProbabilidadEjecucion(List<Tipo> listaProbabilidadEjecucion) {
        this.listaProbabilidadEjecucion = listaProbabilidadEjecucion;
    }

    public List<ProductoVO> getListaProductosSeleccionados() {
        return listaProductosSeleccionados;
    }

    public boolean isMasDeUnProductoSeleccionado() {
        return listaProductosSeleccionados != null && listaProductosSeleccionados.size() > 1;
    }

    public void setListaProductosSeleccionados(List<ProductoVO> listaProductosSeleccionados) {
        this.listaProductosSeleccionados = listaProductosSeleccionados;
    }

    public Oportunidad getOportunidad() {
        return oportunidad;
    }

    public void setOportunidad(Oportunidad oportunidad) {
        this.oportunidad = oportunidad;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ClaveValorDTO> getListaCanalEntrada() {
        return listaCanalEntrada;
    }

    public void setListaCanalEntrada(List<ClaveValorDTO> listaCanalEntrada) {
        this.listaCanalEntrada = listaCanalEntrada;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public ProductoVO getProductoBorrar() {
        return productoBorrar;
    }

    public void setProductoBorrar(ProductoVO productoBorrar) {
        this.productoBorrar = productoBorrar;
    }

    public ProductoVO getProductoAgregar() {
        return productoAgregar;
    }

    public void setProductoAgregar(ProductoVO productoAgregar) {
        this.productoAgregar = productoAgregar;
    }

    public Boolean getAgregarProductos() {
        //Si lista esta vacia se puede agregar
        if (this.listaProductosSeleccionados == null) {
            return true;
        }
        //Si lista no contiene elemento se puede agregar
        if (this.listaProductosSeleccionados.size() == 0) {
            return true;
        }
        return false;
    }

    public void setAgregarProductos(Boolean agregarProductos) {
        this.agregarProductos = agregarProductos;
    }

    public UploadedFile getFileExcel() {
        return fileExcel;
    }

    public void setFileExcel(UploadedFile fileExcel) {
        this.fileExcel = fileExcel;
    }

    public List<OportunidadBPMVO> getListOporErrorExcel() {
        return listOporErrorExcel;
    }

    public void setListOporErrorExcel(List<OportunidadBPMVO> listOporErrorExcel) {
        this.listOporErrorExcel = listOporErrorExcel;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
        this._initLineaInventarioConsulta(linea);
    }

    public Date getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(Date fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

}
