/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ETAPA_OP;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_CONSULTAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ACEPTACION_COTIZACION;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_ACTUALIZAR;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped

public class CotizacionesActualizarFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CarteraConsultarFacesBean.class);
    private static final long serialVersionUID = 1L;
    private Cotizacion cotizacionSelect;
    private String aceptacion;
    private String Observacion;
    private List<Tipo> tiposAceptacion;
    @EJB
    private ICotizacionServiceLocal iCotizacionService;
    @EJB
    private ITipoServiceLocal iTipoServiceLocal;
    @EJB
    private IServiciosEBSLocal iServiciosESB;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;

    @Override
    protected void build() {
        cotizacionSelect = (Cotizacion) getSessionMap().get(COTIZACION_ID_PARAM);
        logger.info("la  cotizacion seleccionada es --> " + ((cotizacionSelect != null) ? cotizacionSelect.getIdCotizacion() : "No seleccioando"));
        cotizacionSelect.setNombreCliente("Exito");
        armarRequestWSActualizacionOportunidad();
        try {
            tiposAceptacion = iTipoServiceLocal.findByTipoNombre(TIPO_ACEPTACION_COTIZACION);
        } catch (ServiceException ex) {
            logger.error(ex);
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
            //Cargamos el número de parametros
            numeroParametrosWS = servicioBPM.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Cotizacion getCotizacionSelect() {
        return cotizacionSelect;
    }

    public void setCotizacionSelect(Cotizacion cotizacionSelect) {
        this.cotizacionSelect = cotizacionSelect;
    }

    public String getAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(String aceptacion) {
        this.aceptacion = aceptacion;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public void actualizacionOportunidad() {
        try {
            OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();
            oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
            oportunidadBPMVO.setIdOportunidad(Long.toString(cotizacionSelect.getIdOportunidad()));
            oportunidadBPMVO.setIdEstadoOportunidad(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
            oportunidadBPMVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_ACTUALIZACION_ETAPA_OP);
            String strRequest = Utils.marshal(oportunidadBPMVO);

            //se pasan los unicos 2 parámetros
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
            request.setParams(paramsService);
            userSession.getClientWs().consumeService(request);
        } catch (JAXBException ex) {
            logger.error(ex.getMessage());

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());

        }
    }

    public void enviarAction() {
        logger.info("Actualizando Cotización: " + this.aceptacion + " obs " + this.Observacion);
        try {
            iCotizacionService.actualizarCotizacion(cotizacionSelect.getIdCotizacion(), aceptacion, convertirCadena(Observacion));
            if(aceptacion.equals("S")){
               actualizacionOportunidad(); 
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        String outcome = getViewRedirect(PAGE_COTIZACIONES_CONSULTAR_KEY);
        try {
            redirect(navigationFaces.navigation.get(outcome));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private String convertirCadena(String cadena) throws UnsupportedEncodingException{
        return cadena == null ? "" : new String (cadena.getBytes ("iso-8859-1"), "UTF-8");
    }

    public List<Tipo> getTiposAceptacion() {
        return tiposAceptacion;
    }

    public void setTiposAceptacion(List<Tipo> tiposAceptacion) {
        this.tiposAceptacion = tiposAceptacion;
    }

}
