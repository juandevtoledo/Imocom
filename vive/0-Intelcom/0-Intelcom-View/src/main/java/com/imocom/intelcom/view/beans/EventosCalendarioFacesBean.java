/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.services.interfaces.IEventosServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.FILTRO_VISITA_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MANTENER_FILTRO_VISITA_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_MODIFICAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_CONSULTA_x_FILTROS;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class EventosCalendarioFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventosCalendarioFacesBean.class);
    private static final long serialVersionUID = 1L;

    private Date fechaSeleccion;
    private List<Visita> listaVisitas;
    private Visita eventoSeleccionado;
    /**
     * Parametros para invocar MDW para Buscar el detalle de un cliente
     */
    private MiddlewareServiceRequestVO requestCliente;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParamatrosWSCliente = 0;

    private final String nombreVariableVisitasEnSesion = "visitasEnSession";

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private IEventosServiceLocal iEventosServiceLocal;

    @Override
    protected void build() {
        System.out.println("build() en Eventos Calendario");
        try {

            //Se inicializa comunicacion con el MiddelWare para buscar informacion de cliente en listado de visitas
            requestCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioP = iServiciosESB.findByUniqueName(WS_CLIENTE_DETALLE);
            requestCliente.setEndpoint(servicioP.getInterfazEndpoint());
            requestCliente.setMethod(servicioP.getMetodo());
            requestCliente.setNamespacePort(servicioP.getNamespace());
            requestCliente.setServiceName(servicioP.getQnameServicio());
            requestCliente.setWsdlUrl(servicioP.getUrlWsdl());
            requestCliente.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSCliente = servicioP.getNumeroParametros();
            //Borramos la información en sesión
            getSessionMap().remove(CLIENT_ID_PARAM);
            getSessionMap().remove(OPORTUNIDAD_ID_PARAM);
            getSessionMap().remove(COTIZACION_ID_PARAM);

            if (getSessionMap().get(nombreVariableVisitasEnSesion) != null) {
                System.out.println(nombreVariableVisitasEnSesion + " diferente nulo");
                List<Visita> listaVisitasNew = (List<Visita>) getSessionMap().get(nombreVariableVisitasEnSesion);
                addNombreCliente(listaVisitasNew);
                //listaVisitas = (List<Visita>) getSessionMap().get(nombreVariableVisitasEnSesion);
                getSessionMap().remove(nombreVariableVisitasEnSesion);
            } else {
                System.out.println(nombreVariableVisitasEnSesion + " ES nulo");
                System.out.println("DateUtil.fechaActualIni():" + DateUtil.fechaActualIni());
                System.out.println("DateUtil.fechaActualFin():" + DateUtil.fechaActualFin());
                if(getSessionMap().get(FILTRO_VISITA_PARAM)!=null){
                    System.out.println("build:getSessionMap().get(FILTRO_VISITA_PARAM)");
                    fechaSeleccion = (Date) getSessionMap().get(FILTRO_VISITA_PARAM);
                }else{
                    System.out.println("build:DateUtil.fechaActualIni()");
                    fechaSeleccion = DateUtil.fechaActualIni();
                }
                Date finaldate = DateUtil.armarFechaEvento(fechaSeleccion, 23, 59);
                listaVisitas = iEventosServiceLocal.buscarEventoFechaAsesor(fechaSeleccion, finaldate, userSession.getUsuario());
                addNombreCliente(listaVisitas);
                if (listaVisitas != null) {
                    System.out.println(" listaVisitas tam :" + listaVisitas.size());
                }
                getSessionMap().put(MANTENER_FILTRO_VISITA_PARAM, Boolean.FALSE);
            }
            
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    private String getDetalleCliente(String nit) {
        try {
            String[] paramsService = new String[numeroParamatrosWSCliente];
            paramsService[0] = nit;
            requestCliente.setParams(paramsService);
            String responseStr = userSession.getClientWs().consumeService(requestCliente);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response != null) {
                return response.getClientes().get(0).getNombreCliente();
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error buscando detalle de cliente"+ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error buscando detalle de cliente"+ex.getMessage());
        } catch(Exception ex){
           logger.error("Error buscando detalle de cliente"+ex.getMessage());
        }
        return nit;

    }

    /**
     * Método que modifica el listado de visita y adiciona el nombre del cliente
     *
     * @return
     */
    private void addNombreCliente(List<Visita> listaVisitasNew) {
        //Se inicializa la lista de visitas
        listaVisitas = new ArrayList<Visita>();
        /*  if(listaVisitas != null){
         listaVisitas.clear();
         }else{
         listaVisitas = new ArrayList<Visita>();
         }
         for (Visita v : listaVisitasNew) {
         listaVisitas.add(v);
         }
         listaVisitasNew.clear();*/
        for (Visita v : listaVisitasNew) {
                
            v.setNombreCliente(getDetalleCliente(""+v.getIdcliente()));
            listaVisitas.add(v);
        }
        listaVisitasNew.clear();
    }

    public void buscarAction(SelectEvent selectEvent) {
        System.out.println(" buscarAction en Eventos Calendario");
         getSessionMap().remove(FILTRO_VISITA_PARAM);
        getSessionMap().put(MANTENER_FILTRO_VISITA_PARAM,Boolean.FALSE);
        try {

            if (fechaSeleccion != null) {
                System.out.println("buscarAction:fechaSeleccion");
                Date finaldate = DateUtil.armarFechaEvento(fechaSeleccion, 23, 59);
                System.out.println("fechaSeleccion:" + fechaSeleccion);
                System.out.println("finaldate:" + finaldate);

                //listaVisitas = iEventosServiceLocal.buscarEventoFechaAsesor(fechaSeleccion, finaldate, userSession.getUsuario());
                List<Visita> listaVisitasNew = iEventosServiceLocal.buscarEventoFechaAsesor(fechaSeleccion, finaldate, userSession.getUsuario());
                addNombreCliente(listaVisitasNew);
                getSessionMap().put(FILTRO_VISITA_PARAM, fechaSeleccion);
            } else {
                System.out.println("buscarAction:null");
                listaVisitas = null;
            }
            getSessionMap().put(nombreVariableVisitasEnSesion, listaVisitas);

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Date getFechaSeleccion() {
        return fechaSeleccion;
    }

    public void setFechaSeleccion(Date fechaSeleccion) {
        this.fechaSeleccion = fechaSeleccion;
    }

    public List<Visita> getListaVisitas() {
        return listaVisitas;
    }

    public void setListaVisitas(List<Visita> listaVisitas) {
        this.listaVisitas = listaVisitas;
    }

    public String redirectDetalle() {
        String outcome = getViewRedirect(PAGE_EVENTOS_DETALLE_KEY);
        try {
            logger.info("El evento seleccionado en redirectDetalle es --> " + ((eventoSeleccionado != null) ? eventoSeleccionado.getIdvisita() : "No seleccionado"));

            if (eventoSeleccionado != null) {
                System.out.println("redirectDetalle:eventoSeleccionado");
                getSessionMap().put(EVENT_ID_PARAM, eventoSeleccionado);
                redirect(navigationFaces.navigation.get(outcome));
            }
        }  catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
        return null;
    }
    
    public String redirectModificar() {
        String outcome = getViewRedirect(PAGE_EVENTOS_MODIFICAR_KEY);
        try {
            logger.info("El evento seleccionado en redirectDetalle es --> " + ((eventoSeleccionado != null) ? eventoSeleccionado.getIdvisita() : "No seleccionado"));

            if (eventoSeleccionado != null) {
                System.out.println("redirectDetalle:eventoSeleccionado");
                getSessionMap().put(EVENT_ID_PARAM, eventoSeleccionado);
                redirect(navigationFaces.navigation.get(outcome));
            }
        }  catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
        return null;
    }

    public Visita getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(Visita eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

}
