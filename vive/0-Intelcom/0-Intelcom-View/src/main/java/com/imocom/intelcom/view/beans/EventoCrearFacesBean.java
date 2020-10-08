/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.InvitadoVisita;
import com.imocom.intelcom.persistence.entities.OportunidadVisita;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.services.interfaces.IEventosServiceLocal;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.interfaces.IUsuarioServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.WS_EVENTOS_CREAR;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.EVENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.TIPO_EVENTO;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_OPORTUNITY_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_OPORTUNITY_PROBABILITY_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CALENDARIO;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_PROBABILIDAD_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDADES_VISITA;
import static com.imocom.intelcom.util.utility.Constants.OPORTUNIDAD_ID_PARAM;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.EventoResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class EventoCrearFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoCrearFacesBean.class);
    private static final long serialVersionUID = 1L;

    private Visita evento;

    private List<Tipo> listaTiposEvento;
    private List<String> valoresHoras;

    private List<Oportunidad> listaOportunidades;
    private String[] listaOportunidadesSeleccionadas;

    private List<Usuario> listaUsuariosConsultados;
    private List<Usuario> listaUsuariosSeleccionados;

    private Usuario usuarioBorrar;
    private Usuario usuarioAgregar;

    private String nit;
    private String nombreCliente;
    private boolean existeCliente;
    private String nombreBuscar;

    private String horaIni = "08:00";
    private String horaFin = "08:30";

    private boolean existeOportunidad;
    private boolean existeOportunidadProbabilidad;

    private MiddlewareServiceRequestVO requestConsultaOportunidades;
    private int numeroParametrosWS_consultaOportunidades = 0;

    private MiddlewareServiceRequestVO requestCrearEventoExchange;
    private int numeroParametrosWS_crearEventoExchange = 0;

    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IEventosServiceLocal iEventoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private IServicioGenericoLocal iServicioGenerico;

    @EJB
    private IUsuarioServiceLocal iServicioUsuario;

    @Override
    protected void build() {

        try {
            //Se inicializa el objeto Evento
            evento = new Visita();
            Tipo tipo = new Tipo();
            evento.setIdTipovisita(tipo);

            // listaUsuariosConsultados= new  ArrayList<Usuario>();
            listaUsuariosSeleccionados = new ArrayList<Usuario>();

            //Se cargan los combos cpon los valores para horas de los eventos            
            valoresHoras = DateUtil.valoresHoraCombo();

            //Consulta de tipos de evento
            listaTiposEvento = iTipoService.findByTipoNombre(TIPO_EVENTO);

            //se arma el request para la consulta de oportunidades
            armarRequestWS_ConsultaOportunidades();
            //se arma el request para la creación de eventos en Exchange
            armarRequestWS_CrearEventoExchange();

            //Se verifica si ya hay un cliente especifico
            existeCliente = getRequest().getParameter(SPECIFIC_CLIENT_ID_PARAM) != null;

            if (existeCliente) {
                //Recupera de la sesión la información del cliente 
                ClienteVO clienteSeleccionado;
                clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
                logger.info("El cliente seleccionado es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccionado"));
                nit = clienteSeleccionado.getNitCliente();
                nombreCliente = clienteSeleccionado.getNombreCliente();
                //se consultan las oportunidades para ese cliente
                cargarOPortunidades(nit);
                //Se verifica si ya hay una oportunidad especifica
                existeOportunidad = getRequest().getParameter(SPECIFIC_OPORTUNITY_ID_PARAM) != null;
                if (existeOportunidad) {
                    listaOportunidades = new ArrayList<Oportunidad>();
                    Oportunidad oportunidad = null;
                    if (getSessionMap().get(OPORTUNIDAD_ID_PARAM) instanceof OportunidadVO) {
                        oportunidad = new Oportunidad((OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM));
                    } else {
                        oportunidad = (Oportunidad) getSessionMap().get(OPORTUNIDAD_ID_PARAM);
                    }
                    listaOportunidades.add(oportunidad);
                    listaOportunidadesSeleccionadas = new String[1];
                    listaOportunidadesSeleccionadas[0] = oportunidad.getIdOportunidad();
                }
            } else {
                //verificamos si es una creación de visita desde otra visita
                if (getSessionMap().get(EVENT_ID_PARAM) != null) {
                    Visita eventoPadre = (Visita) getSessionMap().get(EVENT_ID_PARAM);
                    nit = "" + eventoPadre.getIdcliente();
                    cargarOPortunidades(nit);
                }
            }

            //Se verifica si ya viene de una oportunidad por probabilidad
            existeOportunidadProbabilidad = getRequest().getParameter(SPECIFIC_OPORTUNITY_PROBABILITY_ID_PARAM) != null;
            if (existeOportunidadProbabilidad) {
                OportunidadVO oportunidadVO = (OportunidadVO) getSessionMap().get(OPORTUNIDAD_ID_PARAM);
                nit = oportunidadVO.getNitCliente();
                nombreCliente = oportunidadVO.getNombreCliente();
                listaOportunidades = new ArrayList<Oportunidad>();
                Oportunidad oportunidad = new Oportunidad(oportunidadVO);
                listaOportunidades.add(oportunidad);
            }

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void crearAction(ActionEvent actionEvent) {
        if (DateUtil.validarHorasVisita(horaIni, horaFin)) {
            if (nit != null && nit.trim().length() > 0) {
                String errorExchange = "";
                String resultadoExchange = "";
                try {
                    evento.setIdasesor(userSession.getUsuario());
                    evento.setIdcliente(Long.valueOf(nit));
                    Date inicialDate = DateUtil.armarFechaEvento(evento.getFechainicio(), Integer.parseInt(horaIni.substring(0, 2)), Integer.parseInt(horaIni.substring(3, 5)));
                    Date finaldate = DateUtil.armarFechaEvento(evento.getFechainicio(), Integer.parseInt(horaFin.substring(0, 2)), Integer.parseInt(horaFin.substring(3, 5)));
                    evento.setFechainicio(inicialDate);
                    evento.setFechafinal(finaldate);
                    evento.setFechacreacion(new Date());
                    evento.setFechamodificacion(new Date());
                    Tipo estadoInicialEvento = new Tipo();
                    estadoInicialEvento.setIdTipo(userSession.getTipoEvento_Programado().getIdTipo());
                    evento.setIdEstado(estadoInicialEvento);

                    evento = iEventoService.save(evento);
                    /* Aqui se hace el agendamiento en exchange */
                    try {
                        resultadoExchange = crearEventoExchange(evento);
                        if (!resultadoExchange.equals("00")) {
                            logger.info("El resultado de la creación de evento en Exchange es:" + resultadoExchange);
                            errorExchange = resultadoExchange;
                            performWarningMessages("La visita se ha creado, Pero se han generado problemas con su agendamiento: " + errorExchange);
                        }
                    } catch (Exception ex) {
                        logger.error("Error agendando visita: ", ex);
                        errorExchange = "La visita se ha creado, Pero se han generado problemas con su agendamiento" + ex.getMessage();
                        performWarningMessages("La visita se ha creado, Pero se han generado problemas con su agendamiento");
                    }

                    // Se insertan las oportunidades asociadas
                    if (listaOportunidadesSeleccionadas != null && listaOportunidadesSeleccionadas.length > 0) {
                        for (String listaOportunidadesSeleccionada : listaOportunidadesSeleccionadas) {
                            OportunidadVisita oportunidadVisita = new OportunidadVisita();
                            oportunidadVisita.setIdVisita(evento);
                            oportunidadVisita.setIdOportunidad(Long.valueOf(listaOportunidadesSeleccionada));
                            iServicioGenerico.saveEntity(OportunidadVisita.class, oportunidadVisita);
                        }

                    }
                    // Se insertan los usuarios invitados
                    if (listaUsuariosSeleccionados != null && listaUsuariosSeleccionados.isEmpty()) {
                        for (Usuario usuario : listaUsuariosSeleccionados) {
                            InvitadoVisita invitadoVisita = new InvitadoVisita();
                            invitadoVisita.setIdVisita(evento);
                            invitadoVisita.setIdUsuario(usuario);
                            iServicioGenerico.saveEntity(InvitadoVisita.class, invitadoVisita);
                        }
                    }
                    // Se redirecciona a la pagina de consulta
                    String outcome = getViewRedirect(PAGE_EVENTOS_CALENDARIO);
                    if (existeCliente) {
                        outcome = getViewRedirect(PAGE_EVENTOS_CONSULTA_KEY);
                    }
                    if (existeOportunidadProbabilidad) {
                        outcome = getViewRedirect(PAGE_OPORTUNIDADES_PROBABILIDAD_KEY);
                    }
                    if (errorExchange.equals("")) {
                        redirect(navigationFaces.navigation.get(outcome));
                    }

                } catch (Exception ex) {
                    performErrorMessages("Ha ocurrido un problema con la creación del evento");
                    logger.error(ex.getMessage());
                }

            } else {
                performWarningMessages("Debe seleccionar un cliente.");
            }

        } else {
            performWarningMessages("La hora incial de la visita no debe ser mayor que la hora final.");

        }

    }

    private void armarRequestWS_CrearEventoExchange() {
        try {
            requestCrearEventoExchange = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_EVENTOS_CREAR);
            requestCrearEventoExchange.setEndpoint(servicio.getInterfazEndpoint());
            requestCrearEventoExchange.setMethod(servicio.getMetodo());
            requestCrearEventoExchange.setNamespacePort(servicio.getNamespace());
            requestCrearEventoExchange.setServiceName(servicio.getQnameServicio());
            requestCrearEventoExchange.setWsdlUrl(servicio.getUrlWsdl());
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS_crearEventoExchange = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    private void armarRequestWS_ConsultaOportunidades() {
        try {
            requestConsultaOportunidades = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDADES_VISITA);
            requestConsultaOportunidades.setEndpoint(servicio.getInterfazEndpoint());
            requestConsultaOportunidades.setMethod(servicio.getMetodo());
            requestConsultaOportunidades.setNamespacePort(servicio.getNamespace());
            requestConsultaOportunidades.setServiceName(servicio.getQnameServicio());
            requestConsultaOportunidades.setWsdlUrl(servicio.getUrlWsdl());
            requestConsultaOportunidades.setInterfaceType(servicio.getTipoInterfaz());

            //Cargamos el número de parametros
            numeroParametrosWS_consultaOportunidades = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    private void cargarOPortunidades(String nitCliente) {

        try {
            if (listaOportunidades == null) {
                listaOportunidades = new ArrayList<Oportunidad>();
            }
            String[] paramsService = new String[numeroParametrosWS_consultaOportunidades];
            paramsService[0] = userSession.getUserLogin();
            paramsService[1] = nitCliente;

            requestConsultaOportunidades.setParams(paramsService);

            String responseStr = userSession.getClientWs().consumeService(requestConsultaOportunidades);
            OportunidadResponseVO oportunidadResponseVO = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                listaOportunidades = new ArrayList<Oportunidad>();
                for (OportunidadVO oportunidadVO : oportunidadResponseVO.getOportunidades()) {
                    listaOportunidades.add(new Oportunidad(oportunidadVO));
                }

            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (NullPointerException ex) {
            logger.error(ex.getMessage());
        }

    }

    public String cargarOPortunidadesPopup() {
        listaOportunidades = null;
        cargarOPortunidades(nit);
        return null;
    }

    private String obtenerCorreosInvitados() {
        String retorno = "";
        int pos = 0;
        if (getListaUsuariosSeleccionados() != null && !getListaUsuariosSeleccionados().isEmpty()) {
            for (Usuario usuario : getListaUsuariosSeleccionados()) {
                if (pos > 0) {
                    retorno = retorno + ",";
                }
                retorno = retorno + usuario.getCorreo();
                pos++;
            }
        }
        return retorno;
    }

    private String crearEventoExchange(Visita visita) {

        String[] paramsService = new String[numeroParametrosWS_crearEventoExchange];
        paramsService[0] = userSession.getUsuario().getCorreo();
        paramsService[1] = nombreCliente;
        paramsService[2] = visita.getAsuntovisita();
        //modelo != null ? modelo : "";
        paramsService[3] = visita.getUbicacion() != null ? visita.getUbicacion() : "";
        paramsService[4] = obtenerCorreosInvitados();
        paramsService[5] = DateUtil.formatExchangeTime(visita.getFechainicio());
        paramsService[6] = DateUtil.formatExchangeTime(visita.getFechafinal());

        requestCrearEventoExchange.setParams(paramsService);

        try {
            String responseStr = userSession.getClientWs().consumeService(requestCrearEventoExchange);

            EventoResponseVO response = (EventoResponseVO) Utils.unmarshal(EventoResponseVO.class, responseStr);
            if (response != null) {
                if (response.getResultadoOperacion() != null && response.getResultadoOperacion().trim().length() > 0) {
                    return response.getResultadoOperacion();
                }
            }

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
            return "Error generando Agendamiento: " + ex.getMessage();

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
            return "Error generando Agendamiento: " + ex.getMessage();

        }
        return "No se pudo hacer el Agendamiento";
    }

    public String borrarInvitado() {
        listaUsuariosSeleccionados.remove(usuarioBorrar);
        return null;
    }

    public String agregarInvitado() {
        listaUsuariosConsultados.remove(usuarioAgregar);
        if (!listaUsuariosSeleccionados.contains(usuarioAgregar)) {
            listaUsuariosSeleccionados.add(usuarioAgregar);
        }

        return null;
    }

    public void buscarAction(ActionEvent actionEvent) {
        try {
            listaUsuariosConsultados = iServicioUsuario.findByNombreParcialUsuario(nombreBuscar);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public Visita getEvento() {
        return evento;
    }

    public void setEvento(Visita evento) {
        this.evento = evento;
    }

    public List<Tipo> getListaTiposEvento() {
        return listaTiposEvento;
    }

    public void setListaTiposEvento(List<Tipo> listaTiposEvento) {
        this.listaTiposEvento = listaTiposEvento;
    }

    public List<String> getValoresHoras() {
        return valoresHoras;
    }

    public void setValoresHoras(List<String> valoresHoras) {
        this.valoresHoras = valoresHoras;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

    public boolean isExisteCliente() {
        return existeCliente;
    }

    public List<Oportunidad> getListaOportunidades() {
        return listaOportunidades;
    }

    public void setListaOportunidades(List<Oportunidad> listaOportunidades) {
        this.listaOportunidades = listaOportunidades;
    }

    public String[] getListaOportunidadesSeleccionadas() {
        return listaOportunidadesSeleccionadas;
    }

    public void setListaOportunidadesSeleccionadas(String[] listaOportunidadesSeleccionadas) {
        this.listaOportunidadesSeleccionadas = listaOportunidadesSeleccionadas;
    }

    public List<Usuario> getListaUsuariosConsultados() {
        return listaUsuariosConsultados;
    }

    public void setListaUsuariosConsultados(List<Usuario> listaUsuariosConsultados) {
        this.listaUsuariosConsultados = listaUsuariosConsultados;
    }

    public List<Usuario> getListaUsuariosSeleccionados() {
        return listaUsuariosSeleccionados;
    }

    public void setListaUsuariosSeleccionados(List<Usuario> listaUsuariosSeleccionados) {
        this.listaUsuariosSeleccionados = listaUsuariosSeleccionados;
    }

    public Usuario getUsuarioBorrar() {
        return usuarioBorrar;
    }

    public void setUsuarioBorrar(Usuario usuarioBorrar) {
        this.usuarioBorrar = usuarioBorrar;
    }

    public Usuario getUsuarioAgregar() {
        return usuarioAgregar;
    }

    public void setUsuarioAgregar(Usuario usuarioAgregar) {
        this.usuarioAgregar = usuarioAgregar;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }

}
