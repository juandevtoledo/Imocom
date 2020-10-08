/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.CotizacionProducto;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.ICotizacionProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ACEPTACION_COTIZACION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ETAPA_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_MONEDA;
import static com.imocom.intelcom.util.utility.Constants.TIPO_MOTIVO_CIERRE;
import static com.imocom.intelcom.util.utility.Constants.TIPO_MOTIVO_CIERRE_GANADO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_MOTIVO_CIERRE_PERDIDO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD_CANAL;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD_ESTADO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EJECUCION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EXITO;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_ACTUALIZAR;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_ACTUALIZAR_PRODUCTO;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_PROCESS_INVOCATION;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOMBRE_ESTAPA;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_RESULTADO_VISITA;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_ASOCIADO_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_DETALLE;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadNotaVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
//import com.imocom.intelcom.view.vo.Producto;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class OportunidadesSeguimientoFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidadesSeguimientoFacesBean.class);
    private static final long serialVersionUID = 1L;

    private Oportunidad oportunidad;

    private List<Tipo> listaTipoOportunidad;
    private List<Tipo> listaMoneda;
    private List<Tipo> listaIncoterm;
    private List<Tipo> listaProbabilidadExito;
    private List<Tipo> listaProbabilidadEjecucion;
    private List<Tipo> listaEtapaOportunidad;
    private List<Tipo> listaCanalEntrada;
    private List<Tipo> listaEstadosOportunidad;
    private List<Tipo> listaMotivosCierre;
    private Tipo tipoOfertaPrincipal;
    private Tipo tipoOfertaAlternativa;
    private List<ProductoVO> listaProductosConsultados;
    private ProductoVO productoBorrar;
    private ProductoVO productoAgregar;
    private String idArchivoOrdenCompra;
    private String nombreEtapaOportunidad;
    private List<Tipo> tiposAceptacion;
    /**
     * Estado incial de la oportunidad que no se modifica en el ciclo de la
     * pantalla
     */
    private String estadoInicialOportunidad = "";

    /**
     * Lista de cotizaciones asocidasa
     */
    private List<Cotizacion> cotizaciones;
    /**
     * Cotizacion principal
     */
    private Cotizacion cotizacionPrincipal;
    /**
     * Cotizacion principal inicial. Esta se guarda para no crearle una
     * oportunidad sole se actualiza su estado en caso de ser cambiada
     */
    private Cotizacion cotizacionPrincipalInicial;

    private MiddlewareServiceRequestVO requestOportunidad;
    private int numeroParametrosWS_Oportunidad = 0;
    /**
     * Parametros para invocar MDW para Buscar Producto para una oportunidad
     */
    private MiddlewareServiceRequestVO requestProductosOportunidad;
    /**
     * NÃƒÂºmero de parametros para request de busqueda de productos por
     * oportunidad
     */
    private int numeroParamatrosWSProductoOportunidad = 0;

    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;
    @EJB
    private ICotizacionProductoServiceLocal iCotizacionProductoEJB;
    /**
     * Archivo de Cargue de Documento para subir al Content Server
     */
    private UploadedFile file;
    /**
     * Archivo de Cargue de Documento para subir al Content Server
     */
    private UploadedFile filePedido;
    /**
     * ConexiÃƒÂ³n al UCM para guardar documento
     */
    private RidcConnectionServiceBean iRidcConnection;
    //Request para el proceso de actualizacion de Oportunidad
    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;
    //Request para actualizar producto de la oportunidad
    private MiddlewareServiceRequestVO requestActualizarProducto;
    private int numeroParametrosActualizarProductoWS = 0;
    /**
     * Fecha de Cierre
     */
    private Date fechaCierre;
    /**
     * Nota de una oportunidad
     */
    private String notaOportunidad;
    /**
     * Proceso de registrar resultado
     */
    private MiddlewareServiceRequestVO requestResultadoOportunidad;
    private int numeroParametrosWS_ResultadoOportunidad = 0;
    /**
     * Proceso de crear Oportunidad
     */
    private MiddlewareServiceRequestVO requestCrearOportunidad;
    private int numeroParametrosWSCrearOportunidad = 0;
    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestProducto;
    /**
     * NÃƒÂºmero de paramatos para request de CreaciÃƒÂ³n de CotizaciÃƒÂ³n
     */
    private int numeroParamatrosWSProducto = 0;
    @EJB
    private ICotizacionServiceLocal iCotizacionService;

    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestProductoCotizacion;
    /**
     * NÃƒÂºmero de paramatos para request de CreaciÃƒÂ³n de CotizaciÃƒÂ³n
     */
    private int numeroParamatrosWSProductoCotizacion = 0;

    private String stateDefaul = "";

    private Boolean visibleEstado;

    /**
     * Parametros para invocar MDW para Buscar una oportunidad del asesor
     */
    private MiddlewareServiceRequestVO requestOportunidadNombreEtapa;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParametrosWSOportunidadNombreEtapa = 0;

    @Override
    protected void build() {
        if (getSessionMap().get(OPORTUNIDAD_ID_PARAM) instanceof OportunidadVO) {
            oportunidad = new Oportunidad((OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM));
            logger.info("Recuperando Oportunidad Pe" + oportunidad.getIdProbabilidadEjecucion() + " - " + oportunidad.getIdProbabilidadExito());
        } else {
            oportunidad = (Oportunidad) getSessionMap().get(OPORTUNIDAD_ID_PARAM);
        }
        idArchivoOrdenCompra = oportunidad.getIdArchivoOrdenCompra();
        //se arma el request para la consulta de oportunidades
        armarRequestWSActualizacionOportunidad();
        //Se  carga detalle de la oportunidad
        cargarDetalleOportunidad(oportunidad.getIdOportunidad());

        try {
            //Consulta de tipos de Oportunidad
            listaTipoOportunidad = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD);
            //Consulta de tipos de Moneda
            listaMoneda = iTipoService.findByTipoNombre(TIPO_MONEDA);
            //Consulta de tipos de Probabilidad Exito
            listaProbabilidadExito = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EXITO);
            //Consulta de tipos de Probabilidad Ejecucion
            listaProbabilidadEjecucion = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);
            //Consulta de tipos de Etapas de la oportunidad
            listaEtapaOportunidad = iTipoService.findByTipoNombre(TIPO_ETAPA_OPORTUNIDAD);
            //Consulta de tipos de Estado de la oportunidad
            listaEstadosOportunidad = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD_ESTADO);
            //Consulta de tipos de motivos de cierre de la oportunidad
            listaMotivosCierre = iTipoService.findByTipoNombre(TIPO_MOTIVO_CIERRE);
            //Consulta de tipos de Etapas de la oportunidad
            listaCanalEntrada = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD_CANAL);
            //Se obtiene el tipo de oferta principal
            tipoOfertaPrincipal = iTipoService.findByTipoNombreEtiqueta("TIPO_OFERTA", "Principal");
            //Se obtiene el tipo de oferta alternativa
            tipoOfertaAlternativa = iTipoService.findByTipoNombreEtiqueta("TIPO_OFERTA", "Alternativa");
            //Tipo Aceptacion
            tiposAceptacion = iTipoService.findByTipoNombre(TIPO_ACEPTACION_COTIZACION);
            //se inicializa lista de productos consultados
            cargarProductos();
            //Se cargan cotizaciones
            cargarCotizaciones();
            estadosEtapaOportunidad(oportunidad.getNombreEtapa());

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void cargarMotivosCierreConsulta(String estado) throws ServiceException {
        if (estado.equals("Ganado")) {
            listaMotivosCierre = iTipoService.findByTipoNombre(TIPO_MOTIVO_CIERRE_GANADO);
        } else if (estado.equals("Perdido")) {
            listaMotivosCierre = iTipoService.findByTipoNombre(TIPO_MOTIVO_CIERRE_PERDIDO);
        } else if (estado.equals("Cancelado")) {
            listaMotivosCierre = iTipoService.findByTipoNombre(Constants.TIPO_MOTIVO_CIERRE_CANCELADO);
        }  else if (estado.equals("Aplazado")) {
            listaMotivosCierre = iTipoService.findByTipoNombre(Constants.TIPO_MOTIVO_CIERRE_APLAZADO);
        }       
        else {
            listaMotivosCierre = new ArrayList<Tipo>();
        }
    }

    public void cargarMotivosCierre(ValueChangeEvent evento) throws ServiceException {
        String estado = evento.getNewValue().toString();
        cargarMotivosCierreConsulta(estado);
        //listaDepto = iTipoService.findByTipoNombrePadre(Long.parseLong(evento.getNewValue().toString(), 10));
        //listaMotivosCierre = iTipoService.findByTipoNombre(TIPO_MOTIVO_CIERRE);
    }

    public void estadosEtapaOportunidad(String etapa) {

        try {
            System.out.println("Etapa:  " + etapa + " Estado: " + oportunidad.getIdEstadoOportunidad());
            if (!etapa.contains("Etapa 4")) {
                List<Tipo> tipoEstados = new ArrayList<Tipo>();
                tipoEstados.addAll(listaEstadosOportunidad);
                for (Tipo Op : tipoEstados) {
                    if (Op.getTipoEtiqueta().equals("Ganado")) {
                        listaEstadosOportunidad.remove(Op);
                    }
                }

            }
            cargarMotivosCierreConsulta(oportunidad.getIdEstadoOportunidad());
        } catch (ServiceException ex) {
            logger.error("Error cargando motivos de cierre, " + ex.getMessage());
        }
    }

    private void armarRequestWSActualizacionOportunidad() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioBPM = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_ACTUALIZAR);
            request.setEndpoint(servicioBPM.getInterfazEndpoint());
            request.setMethod(servicioBPM.getMetodo());
            request.setNamespacePort(servicioBPM.getNamespace());
            request.setServiceName(servicioBPM.getQnameServicio());
            request.setWsdlUrl(servicioBPM.getUrlWsdl());
            request.setInterfaceType(servicioBPM.getTipoInterfaz());
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS = servicioBPM.getNumeroParametros();

            //Actualizar producto de oportunidad
            requestActualizarProducto = new MiddlewareServiceRequestVO();
            servicioBPM = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_ACTUALIZAR_PRODUCTO);
            requestActualizarProducto.setEndpoint(servicioBPM.getInterfazEndpoint());
            requestActualizarProducto.setMethod(servicioBPM.getMetodo());
            requestActualizarProducto.setNamespacePort(servicioBPM.getNamespace());
            requestActualizarProducto.setServiceName(servicioBPM.getQnameServicio());
            requestActualizarProducto.setWsdlUrl(servicioBPM.getUrlWsdl());
            requestActualizarProducto.setInterfaceType(servicioBPM.getTipoInterfaz());
            //Cargamos el nÃºmero de parametros
            numeroParametrosActualizarProductoWS = servicioBPM.getNumeroParametros();
            //Producto Detalle
            requestOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_DETALLE);
            requestOportunidad.setEndpoint(servicio.getInterfazEndpoint());
            requestOportunidad.setMethod(servicio.getMetodo());
            requestOportunidad.setNamespacePort(servicio.getNamespace());
            requestOportunidad.setServiceName(servicio.getQnameServicio());
            requestOportunidad.setWsdlUrl(servicio.getUrlWsdl());
            requestOportunidad.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS_Oportunidad = servicio.getNumeroParametros();
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
            //Cargamos el nÃƒÂºmero de parametros
            numeroParamatrosWSProductoOportunidad = servicioPP.getNumeroParametros();
            numeroParametrosWS_Oportunidad = servicio.getNumeroParametros();
            /**
             * Se arma Request para registrar Resultado
             */
            requestResultadoOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioRp = iServiciosESB.findByUniqueName(WS_BPM_PROCESS_INVOCATION);
            requestResultadoOportunidad.setEndpoint(servicioRp.getInterfazEndpoint());
            requestResultadoOportunidad.setMethod(servicioRp.getMetodo());
            requestResultadoOportunidad.setNamespacePort(servicioRp.getNamespace());
            requestResultadoOportunidad.setServiceName(servicioRp.getQnameServicio());
            requestResultadoOportunidad.setWsdlUrl(servicioRp.getUrlWsdl());
            requestResultadoOportunidad.setInterfaceType(servicioRp.getTipoInterfaz());
            //Cargamos el nÃƒÂºmero de parametros
            numeroParametrosWS_ResultadoOportunidad = servicioRp.getNumeroParametros();
            /**
             * Se arma Request de crear oportunidad
             */
            requestCrearOportunidad = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_CREACION);
            requestCrearOportunidad.setEndpoint(servicio.getInterfazEndpoint());
            requestCrearOportunidad.setMethod(servicio.getMetodo());
            requestCrearOportunidad.setNamespacePort(servicio.getNamespace());
            requestCrearOportunidad.setServiceName(servicio.getQnameServicio());
            requestCrearOportunidad.setWsdlUrl(servicio.getUrlWsdl());
            requestCrearOportunidad.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el nÃºmero de parametros
            numeroParametrosWSCrearOportunidad = servicio.getNumeroParametros();
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
             * Se arma Request para buscar informacion del producto
             */
            requestProductoCotizacion = new MiddlewareServiceRequestVO();
            servicio = iServiciosESB.findByUniqueName(WS_PRODUCTO_DETALLE);
            requestProductoCotizacion.setEndpoint(servicio.getInterfazEndpoint());
            requestProductoCotizacion.setMethod(servicio.getMetodo());
            requestProductoCotizacion.setNamespacePort(servicio.getNamespace());
            requestProductoCotizacion.setServiceName(servicio.getQnameServicio());
            requestProductoCotizacion.setWsdlUrl(servicio.getUrlWsdl());
            requestProductoCotizacion.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃƒÂºmero de parametros
            numeroParamatrosWSProductoCotizacion = servicio.getNumeroParametros();
            /**
             * Se arma Request para buscar una oportunidad
             */
            requestOportunidadNombreEtapa = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioP = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_NOMBRE_ESTAPA);
            requestOportunidadNombreEtapa.setEndpoint(servicioP.getInterfazEndpoint());
            requestOportunidadNombreEtapa.setMethod(servicioP.getMetodo());
            requestOportunidadNombreEtapa.setNamespacePort(servicioP.getNamespace());
            requestOportunidadNombreEtapa.setServiceName(servicioP.getQnameServicio());
            requestOportunidadNombreEtapa.setWsdlUrl(servicioP.getUrlWsdl());
            requestOportunidadNombreEtapa.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParametrosWSOportunidadNombreEtapa = servicioP.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Método que verifica si oportunidad ya existe , este se utliza en la
     * autogeneración de oportunidad
     *
     * @param nombre
     * @return
     */
    private Boolean existOportunidad(String nombre) {
        try {
            String[] paramsService = new String[numeroParametrosWSOportunidadNombreEtapa];
            paramsService[0] = "";
            paramsService[1] = nombre;
            paramsService[2] = "";
            requestOportunidadNombreEtapa.setParams(paramsService);
            String responseStr;
            responseStr = userSession.getClientWs().consumeService(requestOportunidadNombreEtapa);
            logger.info("BUSQUEDA_NOMBRE_ETAPA_CA: " + responseStr);
            OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (response != null && response.getOportunidades() != null) {
                if (!response.getOportunidades().isEmpty()) {
                    return true;
                }
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
        }
        return false;
    }

    private void cargarDetalleOportunidad(String idOportunidad) {
        try {

            String[] paramsService = new String[numeroParametrosWS_Oportunidad];
            paramsService[0] = idOportunidad;
            requestOportunidad.setParams(paramsService);
            String responseStr;
            responseStr = userSession.getClientWs().consumeService(requestOportunidad);
            logger.info("DETALLE OPORTUNIDAD: " + responseStr);
            OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (response != null) {
                OportunidadVO oportunidadDetalle = response.getOportunidades().get(0);
                oportunidad.setIdCanalEntrada(oportunidadDetalle.getCanalEntrada());
                oportunidad.setIdTipoOportunidad(oportunidadDetalle.getTipoOportunidad());
                oportunidad.setMonto(oportunidadDetalle.getMonto());
                logger.info("oportunidad.setIdMoneda(oportunidadDetalle.getMoneda())" + oportunidadDetalle.getMoneda());
                oportunidad.setIdMoneda(oportunidadDetalle.getMoneda());
                oportunidad.setIdIncoterm(oportunidadDetalle.getIncoterm());
                oportunidad.setIdEstadoOportunidad(oportunidadDetalle.getEstadoOportunidad());
                estadoInicialOportunidad = oportunidadDetalle.getEstadoOportunidad();
                stateDefaul = oportunidadDetalle.getEstadoOportunidad();
                oportunidad.setNombreEtapa(oportunidadDetalle.getEtapaOportunidad());
                oportunidad.setIdMotivoCierre(oportunidadDetalle.getIdMotivoCierre());
                //portunidad.setEtapaOportunidad(oportunidadDetalle.getEtapaOportunidad());
                this.fechaCierre = DateUtil.getStringEBSTIME(oportunidadDetalle.getFechaCierre());
                String ejecucion = oportunidadDetalle.getProbabilidadEjecucion().replace("Probabilidad", "").trim();
                String exito = oportunidadDetalle.getProbabilidadExito().replace("Probabilidad", "").trim();

                System.out.println("Buscando probalidad: " + TIPO_PROBABILIDAD_EJECUCION + " : " + ejecucion);

                System.out.println("Buscando probalidad: " + TIPO_PROBABILIDAD_EXITO + " : " + exito);
                if (ejecucion != null && !ejecucion.equals("")) {
                    oportunidad.setIdProbabilidadEjecucion(iTipoService.findByTipoNombreEtiqueta(TIPO_PROBABILIDAD_EJECUCION, ejecucion).getTipoValor());
                }
                if (exito != null && !exito.equals("")) {
                    oportunidad.setIdProbabilidadExito(iTipoService.findByTipoNombreEtiqueta(TIPO_PROBABILIDAD_EXITO, exito).getTipoValor());
                }
                System.out.println("IdProbalidad de Ejecucion: " + oportunidad.getIdProbabilidadEjecucion());
                System.out.println("IdProbalidad de Exito: " + oportunidad.getIdProbabilidadExito());
                //oportunidad.setFechaCierreMostrar(DateUtil.formatShortTime(DateUtil.getStringBMPToDate(oportunidadDetalle.getFechaCierre())));
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        } catch (UtilException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        } catch (ServiceException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        }

    }

    /**
     * Metodo que busca productos en la EBS
     */
    public void cargarProductos() {

        String[] paramsService = new String[numeroParamatrosWSProductoOportunidad];
        paramsService[0] = this.oportunidad.getIdOportunidad();
        paramsService[1] = "";
        requestProductosOportunidad.setParams(paramsService);
        String responseStr;
        listaProductosConsultados=new ArrayList<ProductoVO>();
        try {
            responseStr = userSession.getClientWs().consumeService(requestProductosOportunidad);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            if(response!=null&&response.getProductos()!=null){
                ProductoVO _pr =  response.getProductos().get(0);
                if(_pr.getMoneda()==null||_pr.getMoneda().trim().equals("")){
                    logger.info("pr.setMoneda(\"USD\")");
                    _pr.setMoneda("USD");
                }
                listaProductosConsultados.add(_pr);
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        }
    }

    public String agregarProducto() {

        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{inventarioConsultaFacesBean}", InventarioConsultaFacesBean.class);
        InventarioConsultaFacesBean inventarioConsultaFacesBean = (InventarioConsultaFacesBean) ve.getValue(getELContext());
        if (inventarioConsultaFacesBean != null && inventarioConsultaFacesBean.getListaProductos() != null) {
            inventarioConsultaFacesBean.getListaProductos().remove(productoAgregar);
        }
        if (!listaProductosConsultados.contains(productoAgregar)) {
            listaProductosConsultados.add(productoAgregar);
        }
        return null;
    }

    /**
     * se selecciona una cotizacion y se cambia el tipo de las cotizaciones este
     * metodo es llamado desde la pagina y setea la variable
     * cotizacionPrincipal, (La cual siempre contiene cual cotizacion es
     * "Principal"). En este metodo tambien se recorre las demas cotizaciones y
     * se poner como alternativa
     *
     * @return
     */
    public String seleccionarCotizacionPrincipal() {
        List<Cotizacion> cotUpdate = new ArrayList<Cotizacion>();
        //cotizaciones.clear();
        for (Cotizacion cot : cotizaciones) {

            if (cot.getCodigo().equals(cotizacionPrincipal.getCodigo())) {
                cot.setIdtipooferta(tipoOfertaPrincipal);
            } else {
                cot.setIdtipooferta(tipoOfertaAlternativa);
            }
            cotUpdate.add(cot);
        }
        cotizaciones.clear();
        cotizaciones = cotUpdate;
        return null;
    }
    /**
     * Carga las oportunidades autogeneradas
     * @param producto
     * @param nombreOportunidad 
     */
    private void crearOportunidadCotizacion(ProductoVO producto, String nombreOportunidad) {
        try {
            if (!existOportunidad(nombreOportunidad)) {
                OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();
                oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
                oportunidadBPMVO.setNombreOportunidad(nombreOportunidad);
                oportunidadBPMVO.setIdCliente(oportunidad.getNit());
                oportunidadBPMVO.setNombreCliente(oportunidad.getNombreCliente());
                oportunidadBPMVO.setIdTipoOportunidad(oportunidad.getIdTipoOportunidad());
                oportunidadBPMVO.setIdIncoterm(oportunidad.getIdIncoterm());
                oportunidadBPMVO.setIdCanalEntrada(oportunidad.getIdCanalEntrada());
                oportunidadBPMVO.setProbabilidadProyecto(oportunidad.getIdProbabilidadEjecucion());
                oportunidadBPMVO.setProbabilidadImocom(oportunidad.getIdProbabilidadExito());
                oportunidadBPMVO.setObservacion(oportunidad.getObservacionPedido());

                oportunidadBPMVO.setFechaEstimadaCierre(DateUtil.formatBPMTime(fechaCierre));
                oportunidadBPMVO.setFechaCreacion(DateUtil.formatBPMTime(new Date()));
                oportunidadBPMVO.setFechaModificacion(DateUtil.formatBPMTime(new Date()));

                oportunidadBPMVO.setIdEstadoOportunidad(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
                oportunidadBPMVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_ACTUALIZACION_ETAPA_OP);
                List<ProductoVO> productoList = new ArrayList<ProductoVO>();
                productoList.add(producto);
                oportunidadBPMVO.setOportunidadProducto(productoList);
                oportunidadBPMVO.setLinea(producto.getLinea());
                oportunidadBPMVO.setMonto(producto.getPrecioLista());
                logger.info("oportunidadBPMVO.setIdTipoMoneda(producto.getMoneda()) " + producto.getMoneda());
                oportunidadBPMVO.setIdTipoMoneda(producto.getMoneda());
                String strRequest = Utils.marshal(oportunidadBPMVO);
                logger.info(":OPORTUNIDAD_CREAR,USER:" + this.userSession.getUserLogin() + ":" + strRequest);
                String[] paramsService = new String[numeroParametrosWS];
                paramsService[0] = strRequest;
                paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
                requestCrearOportunidad.setParams(paramsService);
                userSession.getClientWs().consumeService(requestCrearOportunidad);
            }
        } catch (JAXBException ex) {
            logger.error(ex.getMessage());

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());

        } catch (java.lang.NullPointerException ex) {
            logger.error(ex.getMessage());
        }
    }

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

    public Boolean verficarCotizacionesOportunidad() {

        for (Cotizacion cot : cotizaciones) {
            if (cot.getAceptacioncliente().equals("S")) {
                if (cot.getIdtipooferta().getTipoEtiqueta().equals(tipoOfertaPrincipal.getTipoEtiqueta())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo que crear o actualiza las oportunidades de una cotizacion
     */
    public Cotizacion ActualizarCotizaciones() {
        Cotizacion cotPrincipal = new Cotizacion();
        if (file != null && idArchivoOrdenCompra.equals("")) {
            //  if (idArchivoOrdenCompra.equals("")) {
            if (verficarCotizacionesOportunidad()) {
                for (Cotizacion cot : cotizaciones) {
                    iCotizacionService.actualizarCotizacion(cot.getIdCotizacion(), cot.getIdtipooferta());
                    iCotizacionService.actualizarCotizacion(cot.getIdCotizacion(), cot.getAceptacioncliente(), "");

                    try {
                        String idProducto = "";
                        String cantidad = "1";
                        for (CotizacionProducto cp : iCotizacionProductoEJB.buscarCotizacionProductoPorCot(cot.getIdCotizacion())) {
                            idProducto = Long.toString(cp.getIdproducto());
                            cantidad = Integer.toString(cp.getCantidad());
                        }
                        ProductoVO producto = getProductoDetalle(idProducto);
                        if (producto != null) {
                            producto.setCantidad(cantidad);
                            //Verifica que la cotizacion este aceptada, para realizar proceso
                            //de creacion o actualizacion
                            if (cot.getAceptacioncliente().equals("S")) {
                                //Si la cotizacion es alternativa Se inicia proceso de creación de oportunidad 
                                if (!cot.getIdtipooferta().getTipoEtiqueta().equals(tipoOfertaPrincipal.getTipoEtiqueta())) {
                                    logger.debug("Creando Oportunidad................");
                                    String nombre = "AUTOGENERADA OPORTUNIDAD No " + oportunidad.getIdOportunidad() + "-" + producto.getNombre();
                                    //Si la cotizacion es principal no se carga cotizacion
                                    if (!cot.getIdCotizacion().equals(cotizacionPrincipalInicial.getIdCotizacion())) {
                                        crearOportunidadCotizacion(producto, nombre);
                                    }
                                } else {
                                    actualizar_producto_portunidad(producto);
                                    cotPrincipal=cot;
                                    logger.debug("Actualizando Oportunidad Cotizacion " + cot.getIdCotizacion());
                                }
                            }
                        } else {
                            logger.debug("No existe Produtcto " + idProducto + " Cotizacion: " + cot.getIdCotizacion());
                        }
                    } catch (ServiceException ex) {
                        logger.error("No existe Produtcto  Cotizacion: " + cot.getIdCotizacion());
                    }

                }
            } else {
                performErrorMessages("No existe una CotizaciÃ³n Principal Aceptada por el cliente..");
            }
        } else {
            logger.debug("No se actualizan Cotizaciones, No se Adjunto Orden de compra");
        }
        return cotPrincipal;
    }

    public String borrarProducto() {
        listaProductosConsultados.remove(productoBorrar);
        return null;
    }

    public void enviarAction() {

        if (enviarSolicitud()) {
            agregarNotaOportunidad();
            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_OPORTUNIDADES_CONSULTA_KEY);
            try {
                redirect(navigationFaces.navigation.get(outcome));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }

        } else {
            performErrorMessages("Ha ocurrido un problema la solicitud de actualizacion de Oportunidad ");
        }
    }

    public HashMap<String, String> upload() {
        if (file != null) {
            try {
                logger.info(" *********** Subiendo Documento UCM ORDEN DE COMPRA*********************** ");
                String name = "" + System.currentTimeMillis();
                String title = "ORDENCOMPRA_" + userSession.getUserLogin() + "_" + oportunidad.getNombreOportunidad();
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                return iRidcConnection.checkingDocsOportunidad(file.getInputstream(), file.getFileName(), name, title, file.getContentType(),this.oportunidad.getIdOportunidad(),this.oportunidad.getNit(),this.userSession.getUserLogin(),"OrdenDeCompra");
            } catch (IOException ex) {
                logger.error("Error Subiendo el documento de la oportunidad: " + ex.getMessage());
                return null;
            } catch (ServiceException ex) {
                logger.error("Error Subiendo el documento de la oportunidad: " + ex.getMessage());
                return null;
            }
        } else {
            logger.error("No se ha cargado un documento en oportunidad: ");

        }
        return null;
    }
    
    public HashMap<String, String> uploadPedido() {
        if (filePedido != null) {
            try {
                logger.info(" *********** Subiendo Documento UCM CONFIRMACION PEDIDO*********************** ");
                String name = "" + System.currentTimeMillis();
                String title = "FINALIZACIONPEDIDO_" + userSession.getUserLogin() + "_" + oportunidad.getNombreOportunidad();
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                return iRidcConnection.checkingDocsOportunidad(filePedido.getInputstream(), filePedido.getFileName(), name, title, filePedido.getContentType(),this.oportunidad.getIdOportunidad(),this.oportunidad.getNit(),this.userSession.getUserLogin(),"OrdenDePedido");
            } catch (IOException ex) {
                logger.error("Error Subiendo el documento finalizacion de pedido de la oportunidad: " + ex.getMessage());
                return null;
            } catch (ServiceException ex) {
                logger.error("Error Subiendo el documento finalizacion de pedido de la oportunidad: " + ex.getMessage());
                return null;
            }
        } else {
            logger.error("No se ha cargado un documento en oportunidad: ");

        }
        return null;
    }

    /**
     * Invoque el servicio web que actualiza el producto de la oportunidad
     *
     * @param newProducto
     */
    public void actualizar_producto_portunidad(ProductoVO newProducto) {
        try {
            //Se arma objeto de actualizacion de oportunidad de producto.
            OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();
            oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
            oportunidadBPMVO.setIdOportunidad(oportunidad.getIdOportunidad());
            //ProductoVO _producto = new ProductoVO();
            /*_producto.setCodigo(newProducto);*/
            List<ProductoVO> _listProducto = new ArrayList<ProductoVO>();
            //listaProductosConsultados.get(0).getCodigo();
            newProducto.setNombre(listaProductosConsultados.get(0).getCodigo());
            _listProducto.add(newProducto);
            oportunidadBPMVO.setOportunidadProducto(_listProducto);
            String strRequest = Utils.marshal(oportunidadBPMVO);
            logger.info(":UPDATE_CREAR,USER:" + this.userSession.getUserLogin() + ":" + strRequest);
            //se pasan los unicos 2 parÃ¡metros
            String[] paramsService = new String[numeroParametrosActualizarProductoWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
            requestActualizarProducto.setParams(paramsService);
            userSession.getClientWs().consumeService(requestActualizarProducto);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Metodo que envia un proceso y registra una nota a la oportunidad
     *
     * @return
     */
    public void agregarNotaOportunidad() {
        try {
            if (notaOportunidad != null && !notaOportunidad.isEmpty() && !notaOportunidad.equals("")) {

                ResultadoVisitaVO resultadoVisitaVO = new ResultadoVisitaVO();
                resultadoVisitaVO.setNombreUsuario(userSession.getUserLogin());
                resultadoVisitaVO.setCreacionProspecto("FALSE");
                resultadoVisitaVO.setAsuntoEventoVisita(notaOportunidad);
                OportunidadNotaVO oportunidadNotaVO = new OportunidadNotaVO();
                oportunidadNotaVO.setIdOportunida("" + oportunidad.getIdOportunidad());
                oportunidadNotaVO.setNotaOportunidad(this.notaOportunidad);
                List<OportunidadNotaVO> oportunidadesNota = new ArrayList<OportunidadNotaVO>();
                oportunidadesNota.add(oportunidadNotaVO);
                resultadoVisitaVO.setOportunidadNota(oportunidadesNota);

                /**/
                //se convierte el objeto a String
                String strRequest = Utils.marshal(resultadoVisitaVO);

                //se pasan los unicos 2 parÃ¡metros
                String[] paramsService = new String[numeroParametrosWS_ResultadoOportunidad];
                paramsService[0] = strRequest;
                paramsService[1] = WS_PROCESS_ENTITY_RESULTADO_VISITA;
                requestResultadoOportunidad.setParams(paramsService);

                userSession.getClientWs().consumeService(requestResultadoOportunidad);
            }
        } catch (JAXBException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (java.lang.NullPointerException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * MÃ©todo que carga una una lista de cotizaciones elaboradas para ser
     * modificadas
     */
    public void cargarCotizaciones() {
        try {
            //Se verifica si se ha cargado la lista de oportunidad se carga la lista.
            Long idOportunidad = (this.oportunidad != null && this.oportunidad.getIdOportunidad() != null) ? Long.parseLong(this.oportunidad.getIdOportunidad()) : null;

            logger.info("Buscando Ctz X Oportunidad Elaborada:  idOportunidad: " + oportunidad.getIdOportunidad());
            cotizaciones = iCotizacionService.buscarCotizacionOpElaborada(idOportunidad);
            for (Cotizacion cot : cotizaciones) {
                if (cot.getIdtipooferta().equals(tipoOfertaPrincipal)) {
                    cotizacionPrincipalInicial = cot;
                }
                if (cot.getAceptacioncliente() == null || cot.getAceptacioncliente().equals("")) {
                    cot.setAceptacioncliente("N");
                }
                String idProducto = "";
                for (CotizacionProducto cp : cot.getCotizacionProductoSet()) {
                    idProducto = Long.toString(cp.getIdproducto());                    
                }
                ProductoVO producto = getProductoDetalle(idProducto);
                
                cot.setNombreProducto(producto.getNombre());
            }
            if (cotizacionPrincipalInicial == null) {
                cotizacionPrincipalInicial = new Cotizacion();
            }
            logger.info("Cotizacion Consultadas " + cotizaciones.size());

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Creacion de Un Proceso de Actualizacion de Oportunidad
     *
     * @return
     */
    public Boolean enviarSolicitud() {

        try {

            logger.info(" *********** Enviando Solicitud de Registro de Seguimiento *********************** ");
            logger.info(" Id Ususario: " + userSession.getUserLogin());
            logger.info(" Id Oportunidad: " + oportunidad.getIdOportunidad());
            logger.info(" Id Nombre: " + oportunidad.getNombreOportunidad());
            logger.info(" Id Etapa : " + oportunidad.getNombreEtapa());
            logger.info(" Id Estado Oportunidad: " + oportunidad.getIdEstadoOportunidad());
            logger.info(" Id Fecha Cierre: " + oportunidad.getFechaCierre());
            logger.info(" Id Motivo Cierre: " + oportunidad.getIdMotivoCierre());
            logger.info(" Id Probalidad Ejecucion: " + oportunidad.getIdProbabilidadEjecucion());
            logger.info(" Id Probalidad Exito: " + oportunidad.getIdProbabilidadExito());
            logger.info(" Archivo Cargue: " + this.file);
            logger.info(" Archivo Cargue Pedido: " + this.filePedido);
            //Si la oportunidad ya esta cancelada o Cerrada no se hace ningun proceso
            if (getVisibleEstado()) {
                getSessionMap().remove(CLIENT_ID_PARAM);
                return true;
            }

            OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();
            oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
            oportunidadBPMVO.setIdOportunidad(oportunidad.getIdOportunidad());
            oportunidadBPMVO.setNombreOportunidad(convertirCadena(oportunidad.getNombreOportunidad()));
            //oportunidadBPMVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
            if (!oportunidad.getIdEstadoOportunidad().equals("-")) {
                oportunidadBPMVO.setIdEstadoOportunidad(oportunidad.getIdEstadoOportunidad());
            } else {
                // oportunidadBPMVO.setIdEstadoOportunidad(stateDefaul);                
            }
            oportunidadBPMVO.setFechaEstimadaCierre(DateUtil.formatSOAtime(fechaCierre));
            oportunidadBPMVO.setMotivoCierre(oportunidad.getIdMotivoCierre());
            oportunidadBPMVO.setProbabilidadProyecto(oportunidad.getIdProbabilidadEjecucion());
            oportunidadBPMVO.setProbabilidadImocom(oportunidad.getIdProbabilidadExito());
            oportunidadBPMVO.setOportunidadProducto(this.listaProductosConsultados);
            //gfandino 23/09/2016 Se adiciona la asignación para que mantenga el tipo moneda
            //Debido a que el SP de actualizar pone USD si este va nulo
            oportunidadBPMVO.setIdTipoMoneda(oportunidad.getIdMoneda());
            HashMap<String, String> rFile = upload();
            if (this.file != null && rFile != null && !rFile.isEmpty()) {
                int idDocumentoContent = Integer.parseInt(rFile.get("id"));
                String webLocation = rFile.get("webLocation");
                oportunidadBPMVO.setIdArchivo(Integer.toString(idDocumentoContent));
                oportunidadBPMVO.setNombreArchivo(file.getFileName());
            }
            
            HashMap<String, String> rFilePedido = uploadPedido();
            if (this.filePedido != null && rFilePedido != null && !rFilePedido.isEmpty()) {
                int idDocumentoContent = Integer.parseInt(rFilePedido.get("id"));
                String webLocation = rFilePedido.get("webLocation");
                oportunidadBPMVO.setIdArchivoPedido(Integer.toString(idDocumentoContent));
                oportunidadBPMVO.setNombreArchivoPedido(this.filePedido.getFileName());
            }

            String strRequest = Utils.marshal(oportunidadBPMVO);

            //se pasan los unicos 2 parÃ¡metros
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
            request.setParams(paramsService);
            userSession.getClientWs().consumeService(request);
            //TEST AGREGAR DESPUES DE ACTUALIZAR OPORTUNIDADES-----------------
            //Se actualiza las cotizaciones de la oportunidad si estan en etapa cotizacion
            //Boolean _correctActOp = true;
            if (this.oportunidad.getNombreEtapa().contains("Etapa 2")) {
                Cotizacion cot =this.ActualizarCotizaciones();
                if(cot!=null&&cot.getIdCotizacion()!=null){
                    oportunidadBPMVO.setMonto(cot.getValortotal().toString());
                    logger.info("oportunidadBPMVO.setIdTipoMoneda(cot.getIdTipomoneda().getTipoEtiqueta()) " + cot.getIdTipomoneda().getTipoEtiqueta());
                    oportunidadBPMVO.setIdTipoMoneda(cot.getIdTipomoneda().getTipoEtiqueta());
                    strRequest = Utils.marshal(oportunidadBPMVO);
                    paramsService = new String[numeroParametrosWS];
                    paramsService[0] = strRequest;
                    paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
                    request.setParams(paramsService);
                    userSession.getClientWs().consumeService(request);
                }
            }
            getSessionMap().remove(CLIENT_ID_PARAM);
            return true;
        } catch (JAXBException ex) {
            logger.error(ex.getMessage());
            return false;
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
            return false;
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            logger.error(ex.getMessage());
            return false;
        } catch (java.lang.NullPointerException ex) {
            logger.error(ex.getMessage());
            return false;
        }catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
    
    private String convertirCadena(String cadena) throws UnsupportedEncodingException{
        return cadena == null ? "" : new String (cadena.getBytes ("iso-8859-1"), "UTF-8");
    }

    public Oportunidad getOportunidad() {
        return oportunidad;
    }

    public void setOportunidad(Oportunidad oportunidad) {
        this.oportunidad = oportunidad;
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

    public List<Tipo> getListaIncoterm() {
        return listaIncoterm;
    }

    public void setListaIncoterm(List<Tipo> listaIncoterm) {
        this.listaIncoterm = listaIncoterm;
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

    public List<Tipo> getListaEtapaOportunidad() {
        return listaEtapaOportunidad;
    }

    public void setListaEtapaOportunidad(List<Tipo> listaEtapaOportunidad) {
        this.listaEtapaOportunidad = listaEtapaOportunidad;
    }

    public List<Tipo> getListaCanalEntrada() {
        return listaCanalEntrada;
    }

    public void setListaCanalEntrada(List<Tipo> listaCanalEntrada) {
        this.listaCanalEntrada = listaCanalEntrada;
    }

    public List<ProductoVO> getListaProductosConsultados() {
        return listaProductosConsultados;
    }

    public void setListaProductosConsultados(List<ProductoVO> listaProductosConsultados) {
        this.listaProductosConsultados = listaProductosConsultados;
    }

    public List<Tipo> getListaEstadosOportunidad() {
        return listaEstadosOportunidad;
    }

    public void setListaEstadosOportunidad(List<Tipo> listaEstadosOportunidad) {
        this.listaEstadosOportunidad = listaEstadosOportunidad;
    }

    public List<Tipo> getListaMotivosCierre() {
        return listaMotivosCierre;
    }

    public void setListaMotivosCierre(List<Tipo> listaMotivosCierre) {
        this.listaMotivosCierre = listaMotivosCierre;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        System.out.println("Set File.. " + file);
        this.file = file;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getNotaOportunidad() {
        return notaOportunidad;
    }

    public void setNotaOportunidad(String notaOportunidad) {
        this.notaOportunidad = notaOportunidad;
    }

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public Cotizacion getCotizacionPrincipal() {
        return cotizacionPrincipal;
    }

    public void setCotizacionPrincipal(Cotizacion cotizacionPrincipal) {
        this.cotizacionPrincipal = cotizacionPrincipal;
    }

    public List<Tipo> getTiposAceptacion() {
        return tiposAceptacion;
    }

    public void setTiposAceptacion(List<Tipo> tiposAceptacion) {
        this.tiposAceptacion = tiposAceptacion;
    }

    /**
     * Especifica si la oportunidad no esta cancelada o perdida
     *
     * @return
     */
    public Boolean getVisibleEstado() {
        if (this.oportunidad != null) {
            if (estadoInicialOportunidad.equals("Cancelado") || estadoInicialOportunidad.equals("Perdido")) {
                return true;
            }
        }
        return false;
    }

    public void setVisibleEstado(Boolean visibleEstado) {
        this.visibleEstado = visibleEstado;
    }

    public UploadedFile getFilePedido() {
        return filePedido;
    }

    public void setFilePedido(UploadedFile filePedido) {
        this.filePedido = filePedido;
    }
    
    

}
