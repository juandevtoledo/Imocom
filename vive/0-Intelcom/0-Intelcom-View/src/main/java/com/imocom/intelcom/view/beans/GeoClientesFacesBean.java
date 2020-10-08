/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_GEOCLIENTES;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MARKER_GREEN;
import static com.imocom.intelcom.util.utility.Constants.MARKER_RED;
import static com.imocom.intelcom.util.utility.Constants.MARKER_YELLOW;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_NAMESPACE_ID;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_SERVICE_NAME_ID;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_WSDL_ID;
import static com.imocom.intelcom.util.utility.Constants.PAGE_CLIENTES_DETALLE_KEY;

import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.client.IntelcomWSClient;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;

/**
 * <strong>Aplicación</strong>     : FONADE Interoperabilidad GEL-XML.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 23, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@ManagedBean
@ViewScoped
public class GeoClientesFacesBean extends AbstractFacesBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ClientesNombreNitFacesBean.class);
    
    private MapModel mapModel;
    
    private Marker marcaSeleccionada;
    
    private List<ClienteVO> listDatosCliente;
    private ClienteVO clienteSeleccionado;
    
    private Map<String, Object> infoWindowData;
    
    @EJB
    private IServiciosEBSLocal iServiciosESB;
    
    @EJB
    private IServicioGenericoLocal<Long, Parametros> iServicioGenerico;
    
    @Override
    protected void build() {
        
        try {
            
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CLIENTE_GEOCLIENTES);
            String wsdlUrl = iServicioGenerico.findEntityById(MIDDLEWARE_WSDL_ID, Parametros.class).getValor();
            String namespace = iServicioGenerico.findEntityById(MIDDLEWARE_NAMESPACE_ID, Parametros.class).getValor();
            String serviceName = iServicioGenerico.findEntityById(MIDDLEWARE_SERVICE_NAME_ID, Parametros.class).getValor();
            
            
            IntelcomWSClient clientWs = new IntelcomWSClient(wsdlUrl, namespace, serviceName);
            
            MiddlewareServiceRequestVO request = new MiddlewareServiceRequestVO();
            
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            
            String[] paramsService = new String[servicio.getNumeroParametros()];
            paramsService[0] = userSession.getUserLogin();
            
            request.setParams(paramsService);
            
            String responseStr = clientWs.consumeService(request);
            
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response != null) {
                listDatosCliente = response.getClientes();
            }
           
            colocarMarcas();       
            
            
        } catch (ServiceException ex) {
            logger.error(ex.getLocalizedMessage());
            performErrorMessages("Error cargando los clientes del asesor");
        } catch (UtilException ex) {
            logger.error(ex.getLocalizedMessage());
            performErrorMessages("Error cargando los clientes del asesor");
        } catch (IntelcomMiddlewareException ex) {
            performErrorMessages("Error cargando los clientes del asesor");
        }
        
    }   
    
   private String tipoMarca(ClienteVO _cliente){
       if(_cliente.getSemaforo().toUpperCase().equals("ROJO")){
            return MARKER_RED;
        }
        if(_cliente.getSemaforo().toUpperCase().equals("AMARILLO")){
            return MARKER_YELLOW;
        }
        if(_cliente.getSemaforo().toUpperCase().equals("VERDE")){
            return MARKER_GREEN;
        }
         return MARKER_RED;
   }
    
    /**
     *
     */
    public void colocarMarcas() {
        
        mapModel = new DefaultMapModel();
        
    
        
        if (listDatosCliente != null && !listDatosCliente.isEmpty()) {
            
            for (ClienteVO data : listDatosCliente) {
                if(data!=null && 
                        data.getCoordClienteLat()!= null && 
                        data.getCoordClienteLong()!= null && 
                        data.getCoordClienteLat().length()>0 && 
                        data.getCoordClienteLong().length()>0){
                    LatLng coordenada = new LatLng(Double.parseDouble(data.getCoordClienteLat()), Double.parseDouble(data.getCoordClienteLong()));
                
                infoWindowData = new HashMap<String, Object>();
                
                infoWindowData.put("mail", data.getCorreoContactoPpal());
                infoWindowData.put("address", data.getDireccionPpal());
                infoWindowData.put("phone", data.getTelefonoPpal());
                infoWindowData.put("nit", data.getNitCliente());
                infoWindowData.put("client-data", data);
                
                //String tipoMarca = (data.getDiasSinVisita() <= 60) ? MARKER_GREEN : (data.getDiasSinVisita() > 60 && data.getDiasSinVisita() <= 90) ? MARKER_YELLOW : MARKER_RED;
                String tipoMarca=this.tipoMarca(data);
                Marker marca = new Marker(coordenada, data.getNombreCliente(), infoWindowData, tipoMarca);
                mapModel.addOverlay(marca);
                }
                
            }
        }else{
            logger.info("....................Listado de Clientes Vacios.....................");
        }
        
        
        //Agregar las localidades al Mapa
        /*Polygon polygon = new Polygon();
        polygon.getPaths().add(new LatLng(4.59335334662506, -74.1307544625703));
        polygon.getPaths().add(new LatLng(4.59335141474458, -74.13074817211511));
        polygon.getPaths().add(new LatLng(4.59306380179575, -74.12989316475969));
        polygon.getPaths().add(new LatLng(4.59294041163806, -74.1294694443381));
        polygon.getPaths().add(new LatLng(4.59280405611952, -74.1289091802355));
        polygon.getPaths().add(new LatLng(4.59271112961793, -74.12853009727159));
        polygon.getPaths().add(new LatLng(4.59265318755665, -74.12829044658299));
        mapModel.addOverlay(polygon);*/
        
    }
    
    /**
     * 
     * @return 
     */
    public String redirectDetalle() {
        String outcome = getViewRedirect(PAGE_CLIENTES_DETALLE_KEY);
        try {              
            logger.info("El cliente seleccionado en redirectDetalle es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccionado"));
            
            if (clienteSeleccionado != null) {           
                getSessionMap().put(CLIENT_ID_PARAM, clienteSeleccionado);
                redirect(navigationFaces.navigation.get(outcome));
            }
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));
            
            performErrorMessages(mensaje);           
        }
        return null;
    }
    
    /**
     *
     * @return
     */
    public List<ClienteVO> getListDatosCliente() {
        return listDatosCliente;
    }
    
    /**
     *
     * @param listDatosCliente
     */
    public void setListDatosCliente(List<ClienteVO> listDatosCliente) {
        this.listDatosCliente = listDatosCliente;
    }
    
    /**
     *
     * @return
     */
    public Marker getMarcaSeleccionada() {
        return marcaSeleccionada;
    }
    
    /**
     *
     * @param marcaSeleccionada
     */
    public void setMarcaSeleccionada(Marker marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }
    
    /**
     * 
     * @param event 
     */
    public void onMarkerSelect(OverlaySelectEvent event) {
        marcaSeleccionada = (Marker) event.getOverlay();
    }

    /**
     * 
     * @return 
     */
    public MapModel getMapModel() {
        return mapModel;
    }

    /**
     * 
     * @param mapModel 
     */
    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    /**
     * 
     * @return 
     */
    public Map<String, Object> getInfoWindowData() {
        return infoWindowData;
    }

    /**
     * 
     * @param infoWindowData 
     */
    public void setInfoWindowData(Map<String, Object> infoWindowData) {
        this.infoWindowData = infoWindowData;
    }

    /**
     * 
     * @return 
     */
    public ClienteVO getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    /**
     * 
     * @param clienteSeleccionado 
     */
    public void setClienteSeleccionado(ClienteVO clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
    
    
}
