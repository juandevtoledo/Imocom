/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.interfaces.IEventosServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CREAR_X_OPORTUNIDAD_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_DETALLE_CLIENTE_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_NOTAS;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_ASOCIADO_OPORTUNIDAD;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.view.vo.SeleccionObjectoDTO;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.NotaVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.NotasResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class OportunidadesDetalleFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidadesDetalleFacesBean.class);
    private static final long serialVersionUID = 1L;

    private OportunidadVO oportunidad;

    private MiddlewareServiceRequestVO requestOportunidad;
    private int numeroParametrosWS_Oportunidad = 0;
    /**
     * Parametros para invocar MDW para Buscar Producto para una oportunidad
     */
    private MiddlewareServiceRequestVO requestProductosOportunidad;
    /**
     * NÃºmero de parametros para request de busqueda de productos por
     * oportunidad
     */
    private int numeroParamatrosWSProductoOportunidad = 0;
    /**
     * Parametros para invocar MDW para Buscar Producto para las notas de una
     * oportunidad
     */
    private MiddlewareServiceRequestVO requestNotas;
    private int numeroParametrosWS_Notas = 0;
    private List<ProductoVO> productos;
    private List<SeleccionObjectoDTO> listaSeleccionObjectoDTOProductos;
    private boolean seleccionTodosProductos;

    @EJB
    private IServiciosEBSLocal iServiciosESB;
    @EJB
    private ICotizacionServiceLocal iCotizacionService;
    /**
     * Lista de cotizaciones asocidas a una oportunidad
     */
    private List<Cotizacion> cotizaciones;
    /**
     * Notas de una oportunidad
     */
    private List<NotaVO> notas;
    /**
     * Visitas de una oportunidad
     */
    private List<Visita> listaVisitas;
    @EJB
    private IEventosServiceLocal iEventosServiceLocal;
    /**
     * Cotizacion seleccionad para mostrar el detalle
     */
    private Cotizacion cotizacionSelect;
    /**
     * Cotizacion seleccionad para mostrar el detalle
     */
    private Visita visitaSelect;

    private MiddlewareServiceRequestVO requestConsultaCliente;
    private int numeroParametrosWSConsultaCliente = 0;
    /**
     * Conexión al UCM
     */
    private RidcConnectionServiceBean iRidcConnection;
    
    private ProductoVO productoVOSeleccionado;
    
    @EJB
    private ITipoServiceLocal iTiposServiceLocal;

    @Override
    protected void build() {
        oportunidad = (OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM);

        //se arma el request para la consulta de oportunidades
        armarRequestWSConsultaOportunidad();
        cargarDetalleOportunidad(oportunidad.getIdOportunidad());
        cargarProductos();
        cargarCotizaciones();
        cargarNotas();
        cargarEventos();

    }

    private void armarRequestWSConsultaOportunidad() {
        try {
            requestOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_DETALLE);
            requestOportunidad.setEndpoint(servicio.getInterfazEndpoint());
            requestOportunidad.setMethod(servicio.getMetodo());
            requestOportunidad.setNamespacePort(servicio.getNamespace());
            requestOportunidad.setServiceName(servicio.getQnameServicio());
            requestOportunidad.setWsdlUrl(servicio.getUrlWsdl());
            //Cargamos el número de parametros
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
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSProductoOportunidad = servicioPP.getNumeroParametros();

            /**
             * Se arma Request para buscar productos de una notas
             */
            requestNotas = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioNota = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_NOTAS);
            requestNotas.setEndpoint(servicioNota.getInterfazEndpoint());
            requestNotas.setMethod(servicioNota.getMetodo());
            requestNotas.setNamespacePort(servicioNota.getNamespace());
            requestNotas.setServiceName(servicioNota.getQnameServicio());
            requestNotas.setWsdlUrl(servicioNota.getUrlWsdl());
            requestNotas.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS_Notas = servicioNota.getNumeroParametros();

            requestConsultaCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs serviciocliente = iServiciosESB.findByUniqueName(WS_CLIENTE_DETALLE);
            requestConsultaCliente.setEndpoint(serviciocliente.getInterfazEndpoint());
            requestConsultaCliente.setMethod(serviciocliente.getMetodo());
            requestConsultaCliente.setNamespacePort(serviciocliente.getNamespace());
            requestConsultaCliente.setServiceName(serviciocliente.getQnameServicio());
            requestConsultaCliente.setWsdlUrl(serviciocliente.getUrlWsdl());
            requestConsultaCliente.setInterfaceType(serviciocliente.getTipoInterfaz());
            numeroParametrosWSConsultaCliente = serviciocliente.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
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
        try {
            responseStr = userSession.getClientWs().consumeService(requestProductosOportunidad);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            listaSeleccionObjectoDTOProductos = new ArrayList<SeleccionObjectoDTO>();
            for( ProductoVO productoVO : response.getProductos() ){
                listaSeleccionObjectoDTOProductos.add( new SeleccionObjectoDTO(false, productoVO) );
            }            
            productos = response.getProductos();

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        }
    }

    /**
     * Metodo que busca productos en la EBS
     */
    public void cargarNotas() {

        String[] paramsService = new String[numeroParametrosWS_Notas];
        paramsService[0] = this.oportunidad.getIdOportunidad();
        requestNotas.setParams(paramsService);
        String responseStr;
        try {
            responseStr = userSession.getClientWs().consumeService(requestNotas);
            NotasResponseVO response = (NotasResponseVO) Utils.unmarshal(NotasResponseVO.class, responseStr);
            notas = new ArrayList<NotaVO>();
            if (response != null && response.getNotas() != null) {
                for (NotaVO n : response.getNotas()) {
                    String fechaMostrar = DateUtil.formatShortTime(DateUtil.getStringBMPToDate(n.getFechaCreacion()));
                    n.setFechaCreacion(fechaMostrar);
                    notas.add(n);
                }
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar notas de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar notas de una oportunidad, " + ex.getMessage());
        }
    }

    private void cargarDetalleOportunidad(String idOportunidad) {
        try {
            String[] paramsService = new String[numeroParametrosWS_Oportunidad];
            paramsService[0] = idOportunidad;
            requestOportunidad.setParams(paramsService);
            String responseStr;
            responseStr = userSession.getClientWs().consumeService(requestOportunidad);
            OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (response != null) {
                logger.info("Response: " + responseStr);
                OportunidadVO oportunidadDetalle = response.getOportunidades().get(0);
                oportunidad.setCanalEntrada(oportunidadDetalle.getCanalEntrada());
                oportunidad.setMonto(oportunidadDetalle.getMonto());
                if (!Utils.isNumeric(oportunidadDetalle.getMoneda())){
                    oportunidad.setMoneda(oportunidadDetalle.getMoneda());
                }else{
                    com.imocom.intelcom.persistence.entities.Tipo tipoMoneda=  iTiposServiceLocal.findByTipoId(Long.parseLong(oportunidadDetalle.getMoneda()));
                    oportunidad.setMoneda(tipoMoneda.getTipoValor());
                }
                 if (!Utils.isNumeric(oportunidadDetalle.getMonto())){
                    oportunidad.setMonto(oportunidadDetalle.getMonto());
                }else{
                    //com.imocom.intelcom.persistence.entities.Tipo tipoMoneda=  iTiposServiceLocal.findByTipoId(Long.parseLong(oportunidadDetalle.getMoneda()));
                    oportunidad.setMonto(Utils.formatNumber(Long.parseLong(oportunidadDetalle.getMonto())));
                }   
                
                
                
                oportunidad.setIncoterm(oportunidadDetalle.getIncoterm());
                oportunidad.setEstadoOportunidad(oportunidadDetalle.getEstadoOportunidad());
                oportunidad.setEtapaOportunidad(oportunidadDetalle.getEtapaOportunidad());
                oportunidad.setFechaCierre(oportunidadDetalle.getFechaCierre());
                oportunidad.setFechaCierreMostrar(oportunidadDetalle.getFechaCierre());
                oportunidad.setIdArchivoOrdenCompra(oportunidadDetalle.getIdArchivoOrdenCompra());
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        } catch (UtilException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        } catch (ServiceException ex) {
            logger.error("Error cargando detalle de la oportunidad" + ex.getMessage(), ex);
        }

    }

    public void cargarEventos() {
        try {
            Long idOportunidad = (this.oportunidad != null && this.oportunidad.getIdOportunidad() != null) ? Long.parseLong(this.oportunidad.getIdOportunidad()) : null;
            logger.info("oportunidad Search: Idoportunidad: Usuario : " + userSession.getUsuario() + " Cliente: " + Long.parseLong(this.oportunidad.getNitCliente()) + " Tipo: " + null + " idOportunidad: " + idOportunidad);
            listaVisitas = iEventosServiceLocal.findInitialResultsByClienteAsesorTipoFechaOportunidad(userSession.getUsuario(), Long.parseLong(this.oportunidad.getNitCliente()), null, null, null, idOportunidad);
            logger.info("Cotizacion Consultadas " + listaVisitas.size());
        } catch (ServiceException ex) {
            logger.error("Error recuperando eventos" + ex.getMessage(), ex);
        }
    }

    /**
     * Método que carga una una lista de cotizaciones
     */
    public void cargarCotizaciones() {
        try {
            //Se verifica si se ha cargado la lista de oportunidad se carga la lista.
            Long idOportunidad = (this.oportunidad != null && this.oportunidad.getIdOportunidad() != null) ? Long.parseLong(this.oportunidad.getIdOportunidad()) : null;

            logger.info("Buscando Ctz: " + null + " idOportunidad: " + oportunidad.getIdOportunidad() + " Vencimiento: " + null + " tipoConsulta:" + null);
            cotizaciones = iCotizacionService.buscarCotizacionCliOporVenc(null, idOportunidad, null, null, null);
            logger.info("Cotizacion Consultadas " + cotizaciones.size());

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public String redirectCotizacion() {
        if (cotizacionSelect != null) {
            getSessionMap().put(COTIZACION_ID_PARAM, cotizacionSelect);
        }
        redirect(PAGE_COTIZACIONES_DETALLE_KEY, "redirectCotizacion");

        return null;
    }
    
    public void downloadRenditionRidc() {
        FacesContext fc = FacesContext.getCurrentInstance();
        byte[] bytes;
        HttpServletResponse response
                = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        try {
            if (this.oportunidad.getIdArchivoOrdenCompra() != null) {
                String id = this.oportunidad.getIdArchivoOrdenCompra();
                logger.info("Archivo Search Id:  " + id + " : " + userSession.getRidcUrl() + " : " + userSession.getRidcUser() + " : " + userSession.getRidcPassword());
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());

                HashMap<String, String> docInfo = iRidcConnection.searchDocs(id, "", "", "");

                bytes = iRidcConnection.getBytesFile(id);
                logger.info("Archivo Search Id:  " + bytes.length);
                response.setContentType(docInfo.get("dFormat"));
                response.setContentType(docInfo.get("dOriginalName"));
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + docInfo.get("dOriginalName")
                        + "\"");
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                FacesContext.getCurrentInstance().responseComplete();
            }else{
                logger.info("Oportunidad "+this.oportunidad.getIdOportunidad()+" No tiene documento de orden de compra!");
            }

        } catch (FileNotFoundException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        } catch (ServiceException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        }
    }
    
   public void redirectVisitaDetalle() {
        ClienteVO clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
            //if (clienteSeleccionado == null) {
                try {
                    String[] paramsService = new String[numeroParametrosWSConsultaCliente];
                    paramsService[0] = oportunidad.getNitCliente();
                    requestConsultaCliente.setParams(paramsService);
                    String responseStr;
                    responseStr = userSession.getClientWs().consumeService(requestConsultaCliente);
                    ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
                    if(response!=null){
                        clienteSeleccionado =  response.getClientes().get(0);
                        getSessionMap().put(CLIENT_ID_PARAM, clienteSeleccionado);
                    }
                } catch (IntelcomMiddlewareException ex) {
                    logger.error("Error obteniento cliente ");
                    ex.printStackTrace();
                } catch (UtilException ex) {
                    logger.error("Error obteniento cliente ");
                    ex.printStackTrace();
                }
           // }
              redirect(PAGE_EVENTOS_CREAR_X_OPORTUNIDAD_KEY, "redirectVisita");
   }

    public String redirectVisita() {
        if (visitaSelect != null) {
            getSessionMap().put(EVENT_ID_PARAM, visitaSelect);
            ClienteVO clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
            if (clienteSeleccionado == null) {
                try {
                    String[] paramsService = new String[numeroParametrosWSConsultaCliente];
                    paramsService[0] = oportunidad.getNitCliente();
                    requestConsultaCliente.setParams(paramsService);
                    String responseStr;
                    responseStr = userSession.getClientWs().consumeService(requestConsultaCliente);
                    ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
                    if(response!=null){
                        clienteSeleccionado =  response.getClientes().get(0);
                        getSessionMap().put(CLIENT_ID_PARAM, clienteSeleccionado);
                    }
                } catch (IntelcomMiddlewareException ex) {
                    logger.error("Error obteniento cliente ");
                    ex.printStackTrace();
                } catch (UtilException ex) {
                    logger.error("Error obteniento cliente ");
                    ex.printStackTrace();
                }
            }

            logger.info("El cliente seleccionado es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccioando"));

        }
        redirect(PAGE_EVENTOS_DETALLE_CLIENTE_KEY, "redirectVisita");

        return null;
    }

    private void redirect(String pageKey, String nombreMetodo) {
        try {
            String outcome = getViewRedirect(pageKey);
            redirect(navigationFaces.navigation.get(outcome));
        } catch (IOException ex) {
            logger.error("Error Redireccionando Detalle: " + nombreMetodo, ex);
        }
    }

    public String redirectCotizarProducto() {
        
        /*if( productoVOSeleccionado == null ){
            return null;
        }*/
        
        getSessionMap().put("PRODUCTO_OPORTUNIDAD_VO_SELECIONADO", listaSeleccionObjectoDTOProductos);
        
        //getSessionMap().put("PRODUCTO_OPORTUNIDAD_VO_SELECIONADO", productoVOSeleccionado);
        
        String outcome = getViewRedirect("redirect.view.page.cotizaciones.crear");
        return navigationFaces.navigation.get(outcome);
    }
            
    public String redirectVersion() {
        
        getSessionMap().remove("COTIZACION_DETALLE_VERSION");
        getSessionMap().put("COTIZACION_DETALLE_VERSION",cotizacionSelect);
        redirect(Constants.PAGE_COTIZACIONES_VERSION_KEY, "redirectDetalle");
        return null;
    }
    
    public OportunidadVO getOportunidad() {
        return oportunidad;
    }

    public void setOportunidad(OportunidadVO oportunidad) {
        this.oportunidad = oportunidad;
    }

    public List<ProductoVO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVO> productos) {
        this.productos = productos;
    }

    public List<SeleccionObjectoDTO> getListaSeleccionObjectoDTOProductos() {
        return listaSeleccionObjectoDTOProductos;
    }

    public void setListaSeleccionObjectoDTOProductos(List<SeleccionObjectoDTO> listaSeleccionObjectoDTOProductos) {
        this.listaSeleccionObjectoDTOProductos = listaSeleccionObjectoDTOProductos;
    }    

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public List<NotaVO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaVO> notas) {
        this.notas = notas;
    }

    public List<Visita> getListaVisitas() {
        return listaVisitas;
    }

    public void setListaVisitas(List<Visita> listaVisitas) {
        this.listaVisitas = listaVisitas;
    }

    public Cotizacion getCotizacionSelect() {
        return cotizacionSelect;
    }

    public void setCotizacionSelect(Cotizacion cotizacionSelect) {
        this.cotizacionSelect = cotizacionSelect;
    }

    public Visita getVisitaSelect() {
        return visitaSelect;
    }

    public void setVisitaSelect(Visita visitaSelect) {
        this.visitaSelect = visitaSelect;
    }

    public ProductoVO getProductoVOSeleccionado() {
        return productoVOSeleccionado;
    }

    public void setProductoVOSeleccionado(ProductoVO productoVOSeleccionado) {
        this.productoVOSeleccionado = productoVOSeleccionado;
    }

    public boolean isSeleccionTodosProductos() {
        return seleccionTodosProductos;
    }

    public void setSeleccionTodosProductos(boolean seleccionTodosProductos) {
        this.seleccionTodosProductos = seleccionTodosProductos;
    }
    
    public void seleccionarTodosProductos() {
        if( listaSeleccionObjectoDTOProductos != null ){
            for (SeleccionObjectoDTO seleccionObjectoDTO : listaSeleccionObjectoDTOProductos) {
                seleccionObjectoDTO.setSeleccionado(seleccionTodosProductos);
            }
        }
    }

    public void guardarInformacionCompetenciaAction(ActionEvent actionEvent) {
        try {
            
            iCotizacionService.update( cotizacionSelect );
            
            performInfoMessages("Competencia actualizada correctamente");
            
        }catch(Exception e ){
            performErrorMessages("Error al actualizar la competencia");
            logger.error(e);
        }
    }    
}
