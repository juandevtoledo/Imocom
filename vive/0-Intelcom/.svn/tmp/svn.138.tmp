/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CREAR_X_OPORTUNIDAD_PROBABILIDAD_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ETAPA_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EJECUCION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EXITO;
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
import java.util.List;
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

public class OportunidadesProbabilidadFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidadesProbabilidadFacesBean.class);
    private static final long serialVersionUID = 1L;

    private String nit;
    private String nombreCliente;
    private String nombreOportunidad;
    private String idTipoOportunidad;
    private String idEtapa;
    private String idProbabilidadEjecución;
    private String idProbabilidadExito;
    private String estadoOportunidad;
    
    private List<Tipo> listaTiposOportunidad;
    private List<Tipo> listaEtapasOportunidad;
    private List<Tipo> listaProbabilidadEjecucion;
    private List<Tipo> listaProbabilidadExito;
    
    private OportunidadVO oportunidadSeleccionada; 
    private List<OportunidadVO> oportunidades;
    
    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;
    
    
    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;


    @Override
    protected void build() {
        try {
            //Borramos la información en sesión
            getSessionMap().remove(CLIENT_ID_PARAM);
            getSessionMap().remove(COTIZACION_ID_PARAM);

            //Consulta de tipos de Oportunidad
            listaTiposOportunidad = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD);

            //Consulta de tipos de etapa de la oportunidad
            listaEtapasOportunidad = iTipoService.findByTipoNombre(TIPO_ETAPA_OPORTUNIDAD);

            //Consulta de tipos de probabilidad de la ejecución
            listaProbabilidadEjecucion = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);

            //Consulta de tipos de probabilidad de exito
            listaProbabilidadExito = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EXITO);
            
            //Armamos el objeto request
            armarRequestWS();

            //Armamos los parametros
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequestWS() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDAD_CONSULTA_x_FILTROS);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());

            //Cargamos el número de parametros
            numeroParametrosWS = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }
    
    
    public void buscarAction(ActionEvent actionEvent) {
        try {
            
                    
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = userSession.getUserLogin();
            paramsService[1] = nit != null ? nit : "";
            paramsService[2] = idProbabilidadEjecución != null ? idProbabilidadEjecución : "";
            paramsService[3] = idProbabilidadExito != null ? idProbabilidadExito : "";
            paramsService[4] = idEtapa != null ? idEtapa : "";
            paramsService[5] = estadoOportunidad != null ? estadoOportunidad : "";

            request.setParams(paramsService);

            String responseStr = userSession.getClientWs().consumeService(request);
            OportunidadResponseVO oportunidadResponseVO = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                oportunidades = oportunidadResponseVO.getOportunidades();
                /*for(OportunidadVO oportunidadVO:oportunidadResponseVO.getOportunidades()){
                   oportunidades.add(new Oportunidad(oportunidadVO));
                }*/
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        }
        
    }

    public String redirectEventos() {
        redirect(PAGE_EVENTOS_CREAR_X_OPORTUNIDAD_PROBABILIDAD_KEY, "redirectEventos");
        return null;
    }

    public String redirectDetalle() {
        redirect(PAGE_OPORTUNIDADES_DETALLE_KEY, "redirectDetalle");
        return null;
    }

    
    private void redirect(String pageKey, String nombreMetodo) {
        String outcome = getViewRedirect(pageKey);
        try {
            logger.info("La oportunidad seleccionada en " + nombreMetodo + " es --> " + ((oportunidadSeleccionada != null) ? oportunidadSeleccionada.getNombreOportunidad() : "No seleccionado"));

            if (oportunidadSeleccionada != null) {
                getSessionMap().put(OPORTUNIDAD_ID_PARAM, oportunidadSeleccionada);
                redirect(navigationFaces.navigation.get(outcome));
            }
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
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

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }

    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }

    public List<Tipo> getListaTiposOportunidad() {
        return listaTiposOportunidad;
    }

    public void setListaTiposOportunidad(List<Tipo> listaTiposOportunidad) {
        this.listaTiposOportunidad = listaTiposOportunidad;
    }

    public List<Tipo> getListaEtapasOportunidad() {
        return listaEtapasOportunidad;
    }

    public void setListaEtapasOportunidad(List<Tipo> listaEtapasOportunidad) {
        this.listaEtapasOportunidad = listaEtapasOportunidad;
    }

    public List<Tipo> getListaProbabilidadEjecucion() {
        return listaProbabilidadEjecucion;
    }

    public void setListaProbabilidadEjecucion(List<Tipo> listaProbabilidadEjecucion) {
        this.listaProbabilidadEjecucion = listaProbabilidadEjecucion;
    }

    public List<Tipo> getListaProbabilidadExito() {
        return listaProbabilidadExito;
    }

    public void setListaProbabilidadExito(List<Tipo> listaProbabilidadExito) {
        this.listaProbabilidadExito = listaProbabilidadExito;
    }

    public List<OportunidadVO> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<OportunidadVO> oportunidades) {
        this.oportunidades = oportunidades;
    }

    public String getIdTipoOportunidad() {
        return idTipoOportunidad;
    }

    public void setIdTipoOportunidad(String idTipoOportunidad) {
        this.idTipoOportunidad = idTipoOportunidad;
    }

    public String getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(String idEtapa) {
        this.idEtapa = idEtapa;
    }

    public String getIdProbabilidadEjecución() {
        return idProbabilidadEjecución;
    }

    public void setIdProbabilidadEjecución(String idProbabilidadEjecución) {
        this.idProbabilidadEjecución = idProbabilidadEjecución;
    }

    public String getIdProbabilidadExito() {
        return idProbabilidadExito;
    }

    public void setIdProbabilidadExito(String idProbabilidadExito) {
        this.idProbabilidadExito = idProbabilidadExito;
    }

    public OportunidadVO getOportunidadSeleccionada() {
        return oportunidadSeleccionada;
    }

    public void setOportunidadSeleccionada(OportunidadVO oportunidadSeleccionada) {
        this.oportunidadSeleccionada = oportunidadSeleccionada;
    }

    public String getEstadoOportunidad() {
        return estadoOportunidad;
    }

    public void setEstadoOportunidad(String estadoOportunidad) {
        this.estadoOportunidad = estadoOportunidad;
    }
    
    
    
    
}
