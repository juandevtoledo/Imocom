/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.OportunidadVisita;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.interfaces.IEventosServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CALENDARIO;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_DETALLE;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class EventoDetalleFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoDetalleFacesBean.class);
    private static final long serialVersionUID = 1L;

    private Visita evento;

    private MiddlewareServiceRequestVO requestOportunidad;
    private MiddlewareServiceRequestVO requestCliente;
    private int numeroParametrosWS_Oportunidad = 0;
    private int numeroParametrosWS_Cliente = 0;

    @EJB
    private IEventosServiceLocal iEventoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @Override
    protected void build() {
        //Recuperamos el evento de la sesiÃ³n
        evento = (Visita) getSessionMap().get(EVENT_ID_PARAM);
        if(evento!=null){
            try {
                evento=iEventoService.findById(evento.getIdvisita());
            } catch (PersistenceException ex) {
                logger.error( "[ "+userSession.getUserLogin() +" ] Error buscando visita, "+ex.getMessage(),ex);
            }
        }
        //Se verifica si ya hay un cliente especifico
        if (getRequest().getParameter(SPECIFIC_CLIENT_ID_PARAM) != null) {
            //Recupera de la sesiÃ³n la informaciÃ³n del cliente 
            ClienteVO clienteSeleccionado;
            clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
            logger.info("El cliente seleccionado es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccionado"));
            evento.setNombreCliente(clienteSeleccionado.getNombreCliente());
            
        } else {
            armarRequestWSConsultaCliente();
            evento.setNombreCliente(consultaNombreCliente(Long.toString(evento.getIdcliente())));
        }
        armarRequestWSConsultaOportunidad();
        //Consultamos la informacion de los nombres de oportunidades
        if(evento.getOportunidadVisitaSet()==null || evento.getOportunidadVisitaSet().isEmpty()){
             logger.info("Buscando Oportunidades de una vista");
            try {
                List<OportunidadVisita> ovList =iEventoService.finOportunidadesXVisita(evento.getIdvisita());
                if(ovList!=null){
                    logger.info("Encontro "+ovList.size()+" Oportunidades a la visita "+evento.getIdvisita());
                    evento.setOportunidadVisitaSet(new HashSet<OportunidadVisita>(ovList));
                }
            } catch (ServiceException ex) {
                logger.error("error buscando Oportunidades de una vista");
            }
        }
        String oportunidades = consultarDetalleOportunidades(evento.getOportunidadVisitaSet());
        evento.setNombreOportunidad(oportunidades);
        
        
        
        //Consultamos la informaciÃ³n del cliente
        //se arma el request para la consulta de oportunidades
        ////armarRequestWSConsultaOportunidad();
        //Consultamos la informacion de los nombres de oportunidades
        ////Set<OportunidadVisita> oportunidades = consultarDetalleOportunidades(evento.getOportunidadVisitaSet());
        ////evento.setOportunidadVisitaSet(oportunidades);
    }

    private String consultarDetalleOportunidades(Set<OportunidadVisita> oportunidades) {
        logger.info("Consultando Oportunidades: "+oportunidades.isEmpty());
       String retorno = "";
        
        if (oportunidades != null) {
            for (OportunidadVisita oportunidad : oportunidades) {
                String nombreOportunidad = consultaNombreOportunidad("" + oportunidad.getIdOportunidad());
                retorno += nombreOportunidad+"-";               
            }
        }  
        return  retorno;
    }

    private void armarRequestWSConsultaCliente() {
        try {
            requestCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CLIENTE_DETALLE);
            requestCliente.setEndpoint(servicio.getInterfazEndpoint());
            requestCliente.setMethod(servicio.getMetodo());
            requestCliente.setNamespacePort(servicio.getNamespace());
            requestCliente.setServiceName(servicio.getQnameServicio());
            requestCliente.setWsdlUrl(servicio.getUrlWsdl());
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS_Cliente = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
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
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS_Oportunidad = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private String consultaNombreOportunidad(String idOportunidad) {
        String[] paramsService = new String[numeroParametrosWS_Oportunidad];
        paramsService[0] = idOportunidad;

        requestOportunidad.setParams(paramsService);

        try {
            String responseStr = userSession.getClientWs().consumeService(requestOportunidad);
            try {
                OportunidadResponseVO response = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                if (response != null && response.getOportunidades() != null) {
                    return response.getOportunidades().get(0).getNombreOportunidad();
                }
            } catch (UtilException ex) {
                logger.error(ex.getMessage());
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
        return null;

    }

    private String consultaNombreCliente(String idCliente) {
        String[] paramsService = new String[numeroParametrosWS_Cliente];
        paramsService[0] = idCliente;

        requestCliente.setParams(paramsService);
        try {
            String responseStr = userSession.getClientWs().consumeService(requestCliente);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response != null && response.getClientes() != null) {
                logger.info("responseStr: " + responseStr);
               return response.getClientes().get(0).getNombreCliente();
            }
            
            
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (UtilException ex) {
             logger.error(ex.getMessage());
        }

        return null;

    }

    public void eliminarAction(ActionEvent actionEvent) {

        try {
            iEventoService.removeVisita(evento.getIdvisita());
            //iEventoService.remove(evento.getIdvisita());
            String outcome = getViewRedirect(PAGE_EVENTOS_CALENDARIO);
            getSessionMap().remove(CLIENT_ID_PARAM);
            getSessionMap().remove(SPECIFIC_CLIENT_ID_PARAM);
            getSessionMap().remove(EVENT_ID_PARAM);
            
            redirect(navigationFaces.navigation.get(outcome));
          
        } catch (Exception ex) {
            performErrorMessages("Ha ocurrido un problema al eliminar el evento");
            logger.error(ex.getMessage());
        }
    }

    public Visita getEvento() {
        return evento;
    }

    public void setEvento(Visita evento) {
        this.evento = evento;
    }
}
