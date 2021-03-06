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
import com.imocom.intelcom.services.interfaces.ICotizacionProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.FILTRO_COTIZACIONES_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MANTENER_FILTRO_COTIZACIONES_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_ACTUALIZAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_CREAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ACEPTACION_COTIZACION;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_CONSULTA_x_FILTROS;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class CotizacionesConsultarBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoDetalleFacesBean.class);
    private static final long serialVersionUID = 1L;
    /**
     * Identificadot del cliente a consultar la cotización
     */
    private String idCliente;
    /**
     * Identificador de la oportunidad
     */
    private String idOportunidad;
    /**
     * Fecha vencimiento de la cotización
     */
    private String vencimiento;
    /**
     * Tipo de consulta de la cotización
     */
    private String tipoConsulta;
    /**
     * Nombre del cliente
     */
    private String nombreCliente;

    private List<Cotizacion> cotizaciones;

    private List<CotizacionProducto> cotizacionesProducto;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private ICotizacionServiceLocal iCotizacionService;

    @EJB
    private ICotizacionProductoServiceLocal iCotizacionProductoService;
    /**
     * Lista de oportunidad que se van a consultar
     */
    private List<OportunidadVO> oportunidades;
    /**
     * Oportunidad seleccionada
     */
    private OportunidadVO oportunidadSelect;

    /**
     * Cotización selecciona para hacer las operaciones de Detalle y
     * Actualización
     */
    private Cotizacion cotizacionSelect;
    /**
     * Parametros para invocar MDW para Buscar una oportunidad del asesor
     */
    private MiddlewareServiceRequestVO requestOportunidad;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParamatrosWSOportunidad = 0;
    @EJB
    private ITipoServiceLocal iTipoServiceLocal;
    @EJB
    private ICotizacionProductoServiceLocal iCotizacionProductoEJB;

    @Override
    protected void build() {
        armarRequest();
        String[] paramsService = new String[4];
        paramsService = (String[]) getSessionMap().get(FILTRO_COTIZACIONES_PARAM);
        Boolean mantenerFiltro = (Boolean) getSessionMap().get(MANTENER_FILTRO_COTIZACIONES_PARAM);
        if((mantenerFiltro!=null && mantenerFiltro) && (paramsService!=null && paramsService.length==4)){
            try{
                if (this.oportunidades == null || this.oportunidades.isEmpty()) {
                    loadOportunidad();
                }
                Long idOportunidad = (this.oportunidadSelect != null && this.oportunidadSelect.getIdOportunidad() != null) ? Long.parseLong(this.oportunidadSelect.getIdOportunidad()) : null;
                idCliente = paramsService[0];
                idOportunidad  = paramsService[1]==null?null:Long.valueOf(paramsService[1]).longValue();
                vencimiento =paramsService[2] ;
                tipoConsulta = paramsService[3] ;
                getSessionMap().put(MANTENER_FILTRO_COTIZACIONES_PARAM,Boolean.TRUE);
                logger.info("Buscando Ctz: " + idCliente + " idOportunidad: " + this.getOportunidadSelect().getIdOportunidad() + " Vencimiento: " + vencimiento + " tipoConsulta:" + tipoConsulta);
                List<Cotizacion> cotizacionNew = iCotizacionService.buscarCotizacionCliOporVenc(idCliente, idOportunidad, vencimiento, tipoConsulta, userSession.getUserLogin());
                cotizaciones = new ArrayList<Cotizacion>();
                List<Tipo> tiposAceptacion = iTipoServiceLocal.findByTipoNombre(TIPO_ACEPTACION_COTIZACION);
                for (Cotizacion ct : cotizacionNew) {
                    Cotizacion act = buscarDatosOportunidadXCot(Long.toString(ct.getIdOportunidad()));
                    ct.setNombreOportunidad(act.getNombreOportunidad());
                    ct.setNombreCliente(act.getNombreCliente());
                    ct.setAceptacioncliente(getTipoAceptacion(tiposAceptacion, ct.getAceptacioncliente()));

                    for (CotizacionProducto cp : iCotizacionProductoEJB.buscarCotizacionProductoPorCot(ct.getIdCotizacion())) {
                         ct.setNombreProducto(cp.getNombreProducto());
                    }
                    cotizaciones.add(ct);

                }
            }catch (ServiceException ex) {
                logger.error(ex.getMessage());
            }
        }
        
        getSessionMap().put(MANTENER_FILTRO_COTIZACIONES_PARAM, Boolean.FALSE);
    }

    private void armarRequest() {
        try {

            /**
             * Se arma Request para buscar una oportunidad
             */
            requestOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioP = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_CONSULTA_x_FILTROS);
            requestOportunidad.setEndpoint(servicioP.getInterfazEndpoint());
            requestOportunidad.setMethod(servicioP.getMetodo());
            requestOportunidad.setNamespacePort(servicioP.getNamespace());
            requestOportunidad.setServiceName(servicioP.getQnameServicio());
            requestOportunidad.setWsdlUrl(servicioP.getUrlWsdl());
            requestOportunidad.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSOportunidad = servicioP.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * MÃ©todo que carga una lista de oportunidades del asesor
     */
    public void loadOportunidad() {
        //logger.info("Nombre oportunidad a buscar: "+this.nombreOportunidadSearch+" "+numeroParamatrosWSOportunidad);
        List<String> idEstado =  new ArrayList<String>();
        idEstado.add(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
        idEstado.add(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
        
        oportunidades = new ArrayList<OportunidadVO>();
        oportunidadSelect = new OportunidadVO();
        
        for(String idEstadoTmp :  idEstado){
            String[] paramsService = new String[numeroParamatrosWSOportunidad];
            paramsService[0] = userSession.getUserLogin();
            paramsService[1] = this.idCliente != null ? this.idCliente : "";
            paramsService[2] = "";
            paramsService[3] = "";
            paramsService[4] = "";
            paramsService[5] = idEstadoTmp;

            requestOportunidad.setParams(paramsService);

            String responseStr;
            try {
                responseStr = userSession.getClientWs().consumeService(requestOportunidad);
                OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                if (response != null && response.getOportunidades() != null) {
                    oportunidades.addAll(response.getOportunidades())  ;
                }

            } catch (IntelcomMiddlewareException ex) {
                logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
            } catch (UtilException ex) {
                logger.error("Error al buscar  una oportunidad, " + ex.getMessage());
            }
        }
        
        Collections.sort(oportunidades, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                     OportunidadVO op1= (OportunidadVO) o1;
                     OportunidadVO op2= (OportunidadVO) o2;
                    return op1.getNombreOportunidad().compareTo(op2.getNombreOportunidad());
            }
            });
        
    }

    public String redirectDetalle() {
        redirect(PAGE_COTIZACIONES_DETALLE_KEY, "redirectDetalle");
        return null;
    }
    
    public String redirectVersion() {
        
        getSessionMap().remove("COTIZACION_DETALLE_VERSION");
        getSessionMap().put("COTIZACION_DETALLE_VERSION",cotizacionSelect);
        redirect(Constants.PAGE_COTIZACIONES_VERSION_KEY, "redirectDetalle");
        return null;
    }

    public String redirectActualizar() {
        redirect(PAGE_COTIZACIONES_ACTUALIZAR_KEY, "redirectActualizar");
        return null;
    }
     /**
     * Metodo que redirecciona a la pagina de creación
     * @return 
     */
    public String redirectCrear() {
        try {
            String outcome = getViewRedirect(PAGE_COTIZACIONES_CREAR_KEY);
            getSessionMap().remove(OPORTUNIDAD_ID_PARAM);
            getSessionMap().remove(CLIENT_ID_PARAM);
            redirect(navigationFaces.navigation.get(outcome));
           
        } catch (IOException ex) {
            logger.error("Error redireccionado cotizaciones crear");
        }
         return null;
    }

    /**
     * Método que retorna el nombre de la oportunidad, obtenido de la lista de
     * oportunidad ya consultada en el filtro de oportunidades
     *
     * @return
     */
    public Cotizacion buscarDatosOportunidadXCot(String idOportunidad) {
        Cotizacion cot = new Cotizacion();
        if (oportunidades != null) {
            for (OportunidadVO opr : oportunidades) {
                if (opr.getIdOportunidad().equals(idOportunidad)) {
                    cot.setNombreOportunidad(opr.getDescripcion());
                    cot.setNombreCliente(opr.getNombreCliente());
                    return cot;
                }
            }
        }
        cot.setNombreOportunidad(idOportunidad);
        return cot;
    }

    /**
     * Retorna el nombre de una etiqueta asociada a un tipo
     *
     * @param tiposAceptacion
     * @param tv
     * @return
     */
    private String getTipoAceptacion(List<Tipo> tiposAceptacion, String tv) {
        for (Tipo tipo : tiposAceptacion) {
            if (tipo.getTipoValor().equals(tv)) {
                return tipo.getTipoEtiqueta();
            }
        }
        return tv;
    }

    /**
     * Método para buscar una oportunidad
     *
     * @param actionEvent
     */
    public void buscarAction(ActionEvent actionEvent) {
         getSessionMap().remove(FILTRO_COTIZACIONES_PARAM);
        getSessionMap().put(MANTENER_FILTRO_COTIZACIONES_PARAM,Boolean.FALSE);
        try {
            //Se verifica si se ha cargado la lista de oportunidad se carga la lista.
            if (this.oportunidades == null || this.oportunidades.isEmpty()) {
                loadOportunidad();
            }
            Long idOportunidad = (this.oportunidadSelect != null && this.oportunidadSelect.getIdOportunidad() != null) ? Long.parseLong(this.oportunidadSelect.getIdOportunidad()) : null;

            logger.info("Buscando Ctz: " + idCliente + " idOportunidad: " + this.getOportunidadSelect().getIdOportunidad() + " Vencimiento: " + vencimiento + " tipoConsulta:" + tipoConsulta);
            List<Cotizacion> cotizacionNew = iCotizacionService.buscarCotizacionCliOporVenc(idCliente, idOportunidad, vencimiento, tipoConsulta, userSession.getUserLogin());
            cotizaciones = new ArrayList<Cotizacion>();
            List<Tipo> tiposAceptacion = iTipoServiceLocal.findByTipoNombre(TIPO_ACEPTACION_COTIZACION);
            for (Cotizacion ct : cotizacionNew) {
                Cotizacion act = buscarDatosOportunidadXCot(Long.toString(ct.getIdOportunidad()));
                ct.setNombreOportunidad(act.getNombreOportunidad());
                ct.setNombreCliente(act.getNombreCliente());
                ct.setAceptacioncliente(getTipoAceptacion(tiposAceptacion, ct.getAceptacioncliente()));
                
                for (CotizacionProducto cp : iCotizacionProductoEJB.buscarCotizacionProductoPorCot(ct.getIdCotizacion())) {
                     ct.setNombreProducto(cp.getNombreProducto());
                }
                cotizaciones.add(ct);

            }
            cotizacionNew.clear();
            String[] filtroCotizacion = new String[4];
            filtroCotizacion[0] = idCliente;
            filtroCotizacion[1] = idOportunidad==null?null:idOportunidad.toString();
            filtroCotizacion[2] = vencimiento;
            filtroCotizacion[3] = tipoConsulta;
            getSessionMap().put(FILTRO_COTIZACIONES_PARAM, filtroCotizacion);
            getSessionMap().put(MANTENER_FILTRO_COTIZACIONES_PARAM,Boolean.TRUE);
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }
   
    private void redirect(String pageKey, String nombreMetodo) {
        String outcome = getViewRedirect(pageKey);
        try {
            logger.info("La cotización seleccionada en " + nombreMetodo + " es --> " + ((cotizacionSelect != null) ? cotizacionSelect.getIdCotizacion() : "No seleccionado") + " outcome: " + outcome + " dev: " + navigationFaces.navigation.get(outcome));

            if (cotizacionSelect != null) {
                getSessionMap().put(COTIZACION_ID_PARAM, cotizacionSelect);
                if(getSessionMap().get(FILTRO_COTIZACIONES_PARAM)==null){
                    String[] filtroCotizacion = new String[4];
                    filtroCotizacion[0] = idCliente;
                    filtroCotizacion[1] = idOportunidad == null ? null : idOportunidad;
                    filtroCotizacion[2] = vencimiento;
                    filtroCotizacion[3] = tipoConsulta;
                    getSessionMap().put(FILTRO_COTIZACIONES_PARAM, filtroCotizacion);
                }
                getSessionMap().put(MANTENER_FILTRO_COTIZACIONES_PARAM, Boolean.TRUE);
                redirect(navigationFaces.navigation.get(outcome));
            }
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<OportunidadVO> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<OportunidadVO> oportunidades) {
        this.oportunidades = oportunidades;
    }

    public OportunidadVO getOportunidadSelect() {
        if (this.oportunidadSelect == null) {
            return new OportunidadVO();
        }
        return oportunidadSelect;
    }

    public void setOportunidadSelect(OportunidadVO oportunidadSelect) {
        this.oportunidadSelect = oportunidadSelect;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdOportunidad() {
        return idOportunidad;
    }

    public void setIdOportunidad(String idOportunidad) {
        this.idOportunidad = idOportunidad;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public List<CotizacionProducto> getCotizacionesProducto() {
        return cotizacionesProducto;
    }

    public void setCotizacionesProducto(List<CotizacionProducto> cotizacionesProducto) {
        this.cotizacionesProducto = cotizacionesProducto;
    }

    public Cotizacion getCotizacionSelect() {
        return cotizacionSelect;
    }

    public void setCotizacionSelect(Cotizacion cotizacionSelect) {
        logger.info("Cotizacion a Consulta Detalle: " + cotizacionSelect.getIdCotizacion());
        this.cotizacionSelect = cotizacionSelect;
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
