/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Moneda;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.IMonedaServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.FILTRO_OPORTUNIDADES_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MANTENER_FILTRO_OPORTUNIDADES_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CREAR_OPORTUNIDAD_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EXITO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EJECUCION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ETAPA_OPORTUNIDAD;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_SEGUIMIENTO_KEY;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_MENU_OP_REQUEST_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.TIPO_OPORTUNIDAD_ESTADO_FILTRO;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_CONSULTA_x_FILTROS;

import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.util.dtos.ClaveValorDTO;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_ACTUALIZAR;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
import com.imocom.intelcom.view.vo.Oportunidad;

import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ViewScoped
public class OportunidadesClientesFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidadesClientesFacesBean.class);
    private static final long serialVersionUID = 1L;

    private List<Tipo> listaProbabilidadExito;
    private List<Tipo> listaProbabilidadEjecucion;
    private List<Tipo> listaEtapaOportunidad;

    private String probabilidadExito = "";
    private String probabilidadEjecucion = "";
    private String etapaOportunidad = "";
    private String estadoOportunidad = "";

    private List<OportunidadVO> oportunidades;
    private List<Tipo> listaEstadosOportunidad;

    private OportunidadVO oportunidadSeleccionada;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;
    private MiddlewareServiceRequestVO requestActualizarOportunidad;
    private int numeroParametrosActualizarOportunidadWS = 0;
    private List<Moneda> listaMonedas;

    private ClienteVO clienteSeleccionado;

    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;
    @EJB
    private IMonedaServiceLocal imonedaServiceLocal;

    @Override
    protected void build() {
        try {
            //Consulta de tipos de Probabilidad Exito
            listaProbabilidadExito = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EXITO);

            //Consulta de tipos de Probabilidad Ejecucion
            listaProbabilidadEjecucion = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);

            //Consulta de tipos de Etapas de la oportunidad
            listaEtapaOportunidad = iTipoService.findByTipoNombre(TIPO_ETAPA_OPORTUNIDAD);

            //Consulta de tipos de Estado de la oportunidad
            listaEstadosOportunidad = iTipoService.findByTipoNombre(TIPO_OPORTUNIDAD_ESTADO_FILTRO);
            //Monedas
            listaMonedas = imonedaServiceLocal.buscarMonedas();

            clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
            if (clienteSeleccionado == null || (getRequest().getParameter(SPECIFIC_MENU_OP_REQUEST_ID_PARAM) != null)) {
                clienteSeleccionado = new ClienteVO();
            } else {
                logger.info("El cliente seleccionado es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccioando"));
            }
            this.estadoOportunidad = "";
            probabilidadEjecucion = "";
            probabilidadExito = "";
            etapaOportunidad = "";

            //Armamos el objeto request
            armarRequestWS();

            //Actualizado por gfandino el 05/08/2016
            String[] paramsService = new String[numeroParametrosWS];
            paramsService = (String[]) getSessionMap().get(FILTRO_OPORTUNIDADES_CLIENTE_PARAM);
            Boolean mantenerFiltro = (Boolean) getSessionMap().get(MANTENER_FILTRO_OPORTUNIDADES_CLIENTE_PARAM);
            if ((mantenerFiltro != null && mantenerFiltro) && (paramsService != null && paramsService.length > 0)) {
                for (String tmp : paramsService) {
                    logger.info("Valor parametro:  " + tmp);
                }
                logger.info("");
                oportunidades = new ArrayList<OportunidadVO>();
                List<String> idEstado = new ArrayList<String>();
                if (paramsService != null && paramsService.length > 0 && paramsService[5] != null && paramsService[5].length() > 0) {
                    idEstado.add(paramsService[5]);
                } else {
                    idEstado.add(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
                    idEstado.add(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
                }

                for (String idEstadoTmp : idEstado) {
                    String[] paramsServiceTmp = paramsService.clone();
                    paramsServiceTmp[5] = idEstadoTmp;
                    request.setParams(paramsServiceTmp);
                    String responseStr = userSession.getClientWs().consumeService(request);
                    System.out.println("Oportunidades Clientes Filtro: " + responseStr);
                    OportunidadResponseVO oportunidadResponseVO = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                    if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                        List<OportunidadVO> oportunidadesTmp = oportunidadResponseVO.getOportunidades();
                        for (OportunidadVO oportunidadTmp : oportunidadesTmp) {
                            oportunidades.add(oportunidadTmp);
                        }
                    }

                }

                Collections.sort(oportunidades, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        OportunidadVO op1 = (OportunidadVO) o1;
                        OportunidadVO op2 = (OportunidadVO) o2;
                        return Integer.valueOf(op2.getIdOportunidad()).compareTo(Integer.valueOf((op1.getIdOportunidad())));
                    }
                });

                probabilidadEjecucion = paramsService[2];
                probabilidadExito = paramsService[3];
                etapaOportunidad = paramsService[4];
                estadoOportunidad = paramsService[5];
                getSessionMap().put(MANTENER_FILTRO_OPORTUNIDADES_CLIENTE_PARAM, Boolean.FALSE);
            }

            //Armamos los parametros
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
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

            /* Request para Actualizar Oportunidad */
            requestActualizarOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioBPMActualizarOportunidad = iServiciosESB.findByUniqueName(WS_BPM_OPORTUNIDAD_ACTUALIZAR);
            requestActualizarOportunidad.setEndpoint(servicioBPMActualizarOportunidad.getInterfazEndpoint());
            requestActualizarOportunidad.setMethod(servicioBPMActualizarOportunidad.getMetodo());
            requestActualizarOportunidad.setNamespacePort(servicioBPMActualizarOportunidad.getNamespace());
            requestActualizarOportunidad.setServiceName(servicioBPMActualizarOportunidad.getQnameServicio());
            requestActualizarOportunidad.setWsdlUrl(servicioBPMActualizarOportunidad.getUrlWsdl());
            requestActualizarOportunidad.setInterfaceType(servicioBPMActualizarOportunidad.getTipoInterfaz());
            //Cargamos el nÃºmero de parametros
            numeroParametrosActualizarOportunidadWS = servicioBPMActualizarOportunidad.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void buscarAction(ActionEvent actionEvent) {

        getSessionMap().remove(FILTRO_OPORTUNIDADES_CLIENTE_PARAM);
        getSessionMap().put(MANTENER_FILTRO_OPORTUNIDADES_CLIENTE_PARAM, Boolean.FALSE);

        List<String> idEstado = new ArrayList<String>();
        if (estadoOportunidad != null && estadoOportunidad.length() > 0) {
            idEstado.add(estadoOportunidad);
        } else {
            idEstado.add(ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP);
            idEstado.add(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
        }

        try {
            oportunidades = new ArrayList<OportunidadVO>();

            for (String idEstadoTmp : idEstado) {

                String[] paramsService = new String[numeroParametrosWS];

                if (isUsuarioTieneRolGerente()) {
                    // paramsService[0] = "%";
                    paramsService[0] = userSession.getUserLogin();
                } else {
                    paramsService[0] = userSession.getUserLogin();
                }
                paramsService[1] = clienteSeleccionado.getNitCliente() != null ? clienteSeleccionado.getNitCliente() : "";
                paramsService[2] = probabilidadEjecucion != null ? probabilidadEjecucion : "";
                paramsService[3] = probabilidadExito != null ? probabilidadExito : "";
                paramsService[4] = etapaOportunidad != null ? etapaOportunidad : "";
                //paramsService[5] = estadoOportunidad != null ? estadoOportunidad : "";
                paramsService[5] = idEstadoTmp;

                request.setParams(paramsService);

                String responseStr = userSession.getClientWs().consumeService(request);
                System.out.println("Oportunidades Clientes Filtro: " + responseStr);
                OportunidadResponseVO oportunidadResponseVO = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
                if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                    List<OportunidadVO> oportunidadesTmp = oportunidadResponseVO.getOportunidades();
                    for (OportunidadVO oportunidadTmp : oportunidadesTmp) {
                        oportunidades.add(oportunidadTmp);
                    }

                }
            }
            Collections.sort(oportunidades, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {

                    OportunidadVO op1 = (OportunidadVO) o1;
                    OportunidadVO op2 = (OportunidadVO) o2;
                    return Integer.valueOf(op2.getIdOportunidad()).compareTo(Integer.valueOf((op1.getIdOportunidad())));
                }
            });
        } catch (IntelcomMiddlewareException ex) {
            logger.error("Mdw error " + ex.getMessage(),ex);
        } catch (UtilException ex) {
            logger.error("Mdw error " + ex.getMessage(),ex);
        }

    }

    public String redirectCrear() {
        try {
            String outcome = getViewRedirect(PAGE_EVENTOS_CREAR_OPORTUNIDAD_KEY);
            redirect(navigationFaces.navigation.get(outcome));
            getSessionMap().remove(EVENT_ID_PARAM);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(OportunidadesClientesFacesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String redirectDetalle() {
        redirect(PAGE_OPORTUNIDADES_DETALLE_KEY, "redirectDetalle");
        return null;
    }

    public String redirectSeguimiento() {
        redirect(PAGE_OPORTUNIDADES_SEGUIMIENTO_KEY, "redirectSeguimiento");
        return null;
    }

    private void redirect(String pageKey, String nombreMetodo) {
        String outcome = getViewRedirect(pageKey);
        try {
            logger.info("La oportunidad seleccionada en " + nombreMetodo + " es --> " + ((oportunidadSeleccionada != null) ? oportunidadSeleccionada.getNombreOportunidad() : "No seleccionado"));

            if (oportunidadSeleccionada != null) {
                getSessionMap().put(OPORTUNIDAD_ID_PARAM, oportunidadSeleccionada);
                if (getSessionMap().get(FILTRO_OPORTUNIDADES_CLIENTE_PARAM) == null) {
                    String[] paramsService = new String[numeroParametrosWS];
                    paramsService[0] = userSession.getUserLogin();
                    paramsService[1] = clienteSeleccionado.getNitCliente() != null ? clienteSeleccionado.getNitCliente() : "";
                    paramsService[2] = probabilidadEjecucion != null ? probabilidadEjecucion : "";
                    paramsService[3] = probabilidadExito != null ? probabilidadExito : "";
                    paramsService[4] = etapaOportunidad != null ? etapaOportunidad : "";
                    paramsService[5] = estadoOportunidad != null ? estadoOportunidad : "";
                    getSessionMap().put(FILTRO_OPORTUNIDADES_CLIENTE_PARAM, paramsService);
                }

                getSessionMap().put(MANTENER_FILTRO_OPORTUNIDADES_CLIENTE_PARAM, Boolean.TRUE);

                redirect(navigationFaces.navigation.get(outcome));
            }
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        this.oportunidadSeleccionada = ((OportunidadVO) event.getObject());
    }

    /**
     * Metodo que actualiza las probabilidades de una oportunidad
     *
     */
    public void actualizarProbabilidadOportunidad() {
        logger.info("********* Metodo actualizarProbabilidadOportunidad sobre la oportunidad: " + this.oportunidadSeleccionada.getNombreOportunidad());

        logger.info("Actualizando la propabilidad de la oportunidad con ID = " + this.oportunidadSeleccionada.getIdOportunidad());

        logger.info("this.oportunidadSeleccionada.getProbabilidadEjecucion() = " + this.oportunidadSeleccionada.getProbabilidadEjecucion());

        //logger.info("this.oportunidadSeleccionada.getEstadoOportunidad() = " + this.oportunidadSeleccionada.getEstadoOportunidad());
        
        //Fix - Remover el valor de" Probabilidad" que acompaña cada keyword de probabilidad que viene de la EBS        
        String probEjecucion = this.oportunidadSeleccionada.getProbabilidadEjecucion();
        String probExito = this.oportunidadSeleccionada.getProbabilidadExito();
        if (probEjecucion.contains(" Probabilidad")) {
            this.oportunidadSeleccionada.setProbabilidadEjecucion(probEjecucion.replace(" Probabilidad", ""));
        }
        if (probExito.contains(" Probabilidad")) {
            this.oportunidadSeleccionada.setProbabilidadExito(probExito.replace(" Probabilidad", ""));
        }

        try {

            //Convertir los label de probabilidad a su correspondiente ID de la lista de seleccion
            for (Tipo tipoListaProbEje : this.listaProbabilidadEjecucion) {
                if (tipoListaProbEje.getTipoEtiqueta().equals(this.oportunidadSeleccionada.getProbabilidadEjecucion())) {
                    this.oportunidadSeleccionada.setProbabilidadEjecucion(tipoListaProbEje.getTipoValor());
                }
            }

            for (Tipo tipoListaProbExi : this.listaProbabilidadExito) {
                if (tipoListaProbExi.getTipoEtiqueta().equals(this.oportunidadSeleccionada.getProbabilidadExito())) {
                    this.oportunidadSeleccionada.setProbabilidadExito(tipoListaProbExi.getTipoValor());
                }
            }

            Oportunidad oportunidadFromVO = new Oportunidad(this.oportunidadSeleccionada);
            //Se crea el objeto a enviar al proceso de BPM
            OportunidadBPMVO oportunidadBPMVO = new OportunidadBPMVO();

            oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
            oportunidadBPMVO.setIdOportunidad(oportunidadFromVO.getIdOportunidad());

            //oportunidadBPMVO.setNombreOportunidad(convertirCadena(oportunidadFromVO.getNombreOportunidad()));
            //oportunidadBPMVO.setIdEstadoOportunidad(oportunidadFromVO.getIdEstadoOportunidad());
            oportunidadBPMVO.setProbabilidadProyecto(oportunidadFromVO.getIdProbabilidadEjecucion());
            oportunidadBPMVO.setProbabilidadImocom(oportunidadFromVO.getIdProbabilidadExito());

            //gfandino 23/09/2016 Se adiciona la asignación para que mantenga el tipo moneda
            //Debido a que el SP de actualizar pone USD si este va nulo
            //oportunidadBPMVO.setIdTipoMoneda(oportunidadFromVO.getIdMoneda());
            String strRequest = Utils.marshal(oportunidadBPMVO);

            logger.info("Actualizando probabilidades de la oportunidad con parametros: " + strRequest);

            //Se pasan los parametros al WS y se invoca
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_OPORTUNIDAD_CREACION;
            requestActualizarOportunidad.setParams(paramsService);
            userSession.getClientWs().consumeService(requestActualizarOportunidad);

            performInfoMessages("La Oportunidad: " + oportunidadFromVO.getNombreOportunidad() + " fué actualizada correctamente.");

        } catch (JAXBException ex) {
            logger.error(ex.getMessage());
            performErrorMessages("Ha ocurrido un error: " + ex.getMessage());
            return;
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
            performErrorMessages("Ha ocurrido un error: " + ex.getMessage());
            return;
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            logger.error(ex.getMessage());
            performErrorMessages("Ha ocurrido un error: " + ex.getMessage());
            return;
        } catch (java.lang.NullPointerException ex) {
            logger.error(ex.getMessage());
            performErrorMessages("Ha ocurrido un error: " + ex.getMessage());
            return;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            performErrorMessages("Ha ocurrido un error: " + ex.getMessage());
            return;
        }

    }

    private String convertirCadena(String cadena) throws UnsupportedEncodingException {
        return cadena == null ? "" : new String(cadena.getBytes("iso-8859-1"), "UTF-8");
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

    public List<OportunidadVO> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<OportunidadVO> oportunidades) {
        this.oportunidades = oportunidades;
    }

    public OportunidadVO getOportunidadSeleccionada() {
        return oportunidadSeleccionada;
    }

    public void setOportunidadSeleccionada(OportunidadVO oportunidadSeleccionada) {
        this.oportunidadSeleccionada = oportunidadSeleccionada;
    }

    public ClienteVO getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(ClienteVO clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getProbabilidadExito() {
        return probabilidadExito;
    }

    public void setProbabilidadExito(String probabilidadExito) {
        this.probabilidadExito = probabilidadExito;
    }

    public String getProbabilidadEjecucion() {
        return probabilidadEjecucion;
    }

    public void setProbabilidadEjecucion(String probabilidadEjecucion) {
        this.probabilidadEjecucion = probabilidadEjecucion;
    }

    public String getEtapaOportunidad() {
        return etapaOportunidad;
    }

    public void setEtapaOportunidad(String etapaOportunidad) {
        this.etapaOportunidad = etapaOportunidad;
    }

    public String getEstadoOportunidad() {
        return estadoOportunidad;
    }

    public void setEstadoOportunidad(String estadoOportunidad) {
        this.estadoOportunidad = estadoOportunidad;
    }

    public List<Tipo> getListaEstadosOportunidad() {
        return listaEstadosOportunidad;
    }

    public void setListaEstadosOportunidad(List<Tipo> listaEstadosOportunidad) {
        this.listaEstadosOportunidad = listaEstadosOportunidad;
    }

    public List<Moneda> getListaMonedas() {
        return listaMonedas;
    }

    public void setListaMonedas(List<Moneda> listaMonedas) {
        this.listaMonedas = listaMonedas;
    }
    
    

}
