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
import com.imocom.intelcom.persistence.util.PersistenceException;
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
import static com.imocom.intelcom.util.utility.Constants.TIPO_ESTADO_EVENTO;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_PROCESS_INVOCATION;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_RESULTADO_VISITA;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadNotaVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import com.imocom.intelcom.ws.ebs.vo.response.EventoResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class EventoCrearConResultadosFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoCrearConResultadosFacesBean.class);
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

    private MiddlewareServiceRequestVO requestResultadoOportunidad;
    private int numeroParametrosWS_ResultadoOportunidad = 0;

    private String idTipoEstado;
    private List<Tipo> listaEstados;

    private List<OportunidadVisita> listaOportunidadesVisita;

    private boolean generaOportunidad = false;

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

            //Consulta de estados de los eventos
            listaEstados = iTipoService.findByTipoNombre(TIPO_ESTADO_EVENTO);

            //se arma el request para la consulta de oportunidades
            armarRequestWS_ConsultaOportunidades();
            //se arma el request para la creación de eventos en Exchange
            armarRequestWS_CrearEventoExchange();

            //Se arma el request de resultado oportunidad porque tambien hay registro de resultado de visita en la EBS como nota al cliente.
            armarRequestWSResultadoOportunidad();

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

    public void crearConResultado(ActionEvent actionEvent) {
        logger.info("****** Metodo -> crearActionConResultado *********");
        logger.info("Creando Visita con Resultados para el cliente: " + nit);

        logger.info("evento.getAccionesGeneradas:" + evento.getAccionesGeneradas());
        logger.info("evento.getResultadovisita:" + evento.getResultadovisita());
        //logger.info("evento.getIdEstado:" + evento.getIdEstado());
        logger.info("evento.getIdTipovisita:" + evento.getIdTipovisita());

        //Validaciones
        //TODO la validacion de hora de visita esta invertida
        if (!DateUtil.validarHorasVisita(horaIni, horaFin)) {
            performWarningMessages("La hora incial de la visita (" + horaIni + ")no debe ser mayor que la hora final(" + horaFin + ")");
            return;
        }

        if (nit == null || nit.trim().length() < 0) {
            performWarningMessages("Debe seleccionar un cliente.");
            return;
        }

        //Si se esta creando una nueva oportunidad, se debe validar que tenga productos
        if (generaOportunidad) {
            if (!existenProductosOportunidad()) {
                return;
            }
        }

        try {

            // Se complementan los valores del objeto VISITA que sera persistido
            evento.setIdasesor(userSession.getUsuario());
            evento.setIdcliente(Long.valueOf(nit));
            Date inicialDate = DateUtil.armarFechaEvento(evento.getFechainicio(), Integer.parseInt(horaIni.substring(0, 2)), Integer.parseInt(horaIni.substring(3, 5)));
            Date finaldate = DateUtil.armarFechaEvento(evento.getFechainicio(), Integer.parseInt(horaFin.substring(0, 2)), Integer.parseInt(horaFin.substring(3, 5)));
            evento.setFechainicio(inicialDate);
            evento.setFechafinal(finaldate);
            evento.setFechacreacion(new Date());
            evento.setFechamodificacion(new Date());
            evento.setFecharegistroresultados(new Date());

            //El estado de la visita debe ser Ejecutada
            //El estado se obtiene del selectionList en pantalla
            Tipo estadoEvento = new Tipo();
            estadoEvento.setIdTipo(Long.valueOf(idTipoEstado));
            evento.setIdEstado(estadoEvento);
            //Se ejecuta la persistencia del evento en BD
            logger.info("Persistiendo la visita del cliente: " + evento.getIdcliente());
            evento = iEventoService.save(evento);

            logger.info("EVENTO PERSISTIDO EN BD");
            logger.info("evento.toString() = " + evento.toString());

            // Se insertan las oportunidades asociadas
            if (listaOportunidadesVisita != null && listaOportunidadesVisita.size() > 0) {

                List<OportunidadVisita> opVisitaListPersisted = new ArrayList<OportunidadVisita>();

                for (OportunidadVisita unaOportunidadVisita : listaOportunidadesVisita) {
                    //Persistir unicamente las oportunidades Visita que sean seleccionadas
                    if (unaOportunidadVisita.isSelected()) {
                        unaOportunidadVisita.setIdVisita(evento);
                        logger.info("Persistiendo OportunidadVisita para la oportunidad " + unaOportunidadVisita.getNombreOportunidad());
                        iServicioGenerico.saveEntity(OportunidadVisita.class, unaOportunidadVisita);
                        opVisitaListPersisted.add(unaOportunidadVisita);
                    }
                }

                //Se debe actualizar tambien el set de OportunidadVisitaSet del evento
                //con las que fueron seleccionadas y persistidas                
                if (!opVisitaListPersisted.isEmpty()) {
                    Set<OportunidadVisita> oportunidadesSet = new HashSet<OportunidadVisita>(opVisitaListPersisted);
                    evento.setOportunidadVisitaSet(oportunidadesSet);
                }

            }

            //Invocar servicio BPM para resultados de Visita
            logger.info("Invocar servicio BPM para resultados de Visita -> " + evento.toString());
            actualizarResultadosPorOportunidad();

            //Se verifica si se crea una nueva oportunidad
            if (generaOportunidad) {
                crearNuevaOportunidad();
            }

            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_EVENTOS_CALENDARIO);
            if (existeCliente) {
                outcome = getViewRedirect(PAGE_EVENTOS_CONSULTA_KEY);
            }
            if (existeOportunidadProbabilidad) {
                outcome = getViewRedirect(PAGE_OPORTUNIDADES_PROBABILIDAD_KEY);
            }

            redirect(navigationFaces.navigation.get(outcome));

        } catch (PersistenceException ex) {
            performErrorMessages("Ha ocurrido un problema persistiendo evento. ->" + ex.getMessage());
            logger.error(ex.getMessage());
        } catch (NumberFormatException ex) {
            performErrorMessages("Ha ocurrido un problema valores relacionados al evento. ->" + ex.getMessage());
            logger.error(ex.getMessage());
        } catch (ServiceException ex) {
            performErrorMessages("Ha ocurrido un problema con uel servicio. ->" + ex.getMessage());
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            performWarningMessages("Ha ocurrido un problema con la navegación. ->" + ex.getMessage());
            logger.error(ex.getMessage());
        }
        /*catch (Exception ex) {
            performErrorMessages("Ha ocurrido un problema. ->" + ex.getMessage());
            logger.error(ex.getMessage());
        }*/

    }

    public void actualizarResultadosPorOportunidad() throws ServiceException {
        logger.info("***** Metodo actualizarResultadosPorOportunidad *****");

        ResultadoVisitaVO resultadoVisitaVO = new ResultadoVisitaVO();
        resultadoVisitaVO.setNombreUsuario(userSession.getUserLogin());
        resultadoVisitaVO.setCreacionProspecto("FALSE");
        resultadoVisitaVO.setIdClienteVisita("" + evento.getIdcliente());

        //Inicializar Tipos
        Tipo eventoVisitaByIdTipo = iTipoService.findByTipoId(evento.getIdTipovisita().getIdTipo());
        Tipo eventoEstadoByIdTipo = iTipoService.findByTipoId(evento.getIdEstado().getIdTipo());
                
        resultadoVisitaVO.setTipoEventoVisita(eventoVisitaByIdTipo.getTipoValor());
        resultadoVisitaVO.setEstadoEventoVisita(eventoEstadoByIdTipo.getTipoValor());

        resultadoVisitaVO.setIdEventoGeneradorVisita("" + evento.getIdvisita());
        resultadoVisitaVO.setAsuntoEventoVisita(evento.getAsuntovisita());
        resultadoVisitaVO.setResultadoEventoVisita(evento.getResultadovisita());
        resultadoVisitaVO.setUbicacionVisita(evento.getUbicacion());
        resultadoVisitaVO.setFechaCreacionVisita(DateUtil.formatExchangeTime(evento.getFechacreacion()));
        resultadoVisitaVO.setFechaInicioVisita(DateUtil.formatExchangeTime(evento.getFechainicio()));
        resultadoVisitaVO.setFechaFinalVisita(DateUtil.formatExchangeTime(evento.getFechafinal()));
        resultadoVisitaVO.setFechaRegistroResultadosVisita(DateUtil.formatBPMTime(new Date()));

        if (evento.getOportunidadVisitaSet() != null && !evento.getOportunidadVisitaSet().isEmpty()) {
            List<OportunidadNotaVO> oportunidadesNota = new ArrayList<OportunidadNotaVO>();
            for (OportunidadVisita oportunidadVisita : evento.getOportunidadVisitaSet()) {
                if (oportunidadVisita.getResultadoVisita() != null && oportunidadVisita.getResultadoVisita().trim().length() > 0) {
                    OportunidadNotaVO oportunidadNotaVO = new OportunidadNotaVO();
                    oportunidadNotaVO.setIdOportunida("" + oportunidadVisita.getIdOportunidad());
                    oportunidadNotaVO.setNotaOportunidad(oportunidadVisita.getResultadoVisita());
                    oportunidadesNota.add(oportunidadNotaVO);
                }
            }
            resultadoVisitaVO.setOportunidadNota(oportunidadesNota);
        }

        logger.info("Enviando objeto ResultadoVisitaVO -> " + resultadoVisitaVO.toString());

        try {

            /**/
            //se convierte el objeto a String
            String strRequest = Utils.marshal(resultadoVisitaVO);

            //se pasan los unicos 2 parámetros
            String[] paramsService = new String[numeroParametrosWS_ResultadoOportunidad];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_RESULTADO_VISITA;
            requestResultadoOportunidad.setParams(paramsService);

            userSession.getClientWs().consumeService(requestResultadoOportunidad);

        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        } catch (JAXBException ex) {
            logger.error(ex.getMessage());
        }

    }

    private boolean existenProductosOportunidad() {
        ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{oportunidadesCrearFacesBean}", OportunidadesCrearFacesBean.class);
        OportunidadesCrearFacesBean oportunidadesCrearFacesBean = (OportunidadesCrearFacesBean) ve.getValue(getELContext());

        if (oportunidadesCrearFacesBean != null && oportunidadesCrearFacesBean.getListaProductosSeleccionados() != null && !oportunidadesCrearFacesBean.getListaProductosSeleccionados().isEmpty()) {
            String cantidad = oportunidadesCrearFacesBean.getListaProductosSeleccionados().get(0).getCantidad();
            if (!Utils.isNumeric(cantidad)) {
                performErrorMessages("Cantidad del Producto de la Oportunidad No es númerica o Contiene espacios.");
                return false;
            }
            return true;
        } else {
            performWarningMessages("En Formulario para creación de Oportunidades se debe seleccionar al menos un producto.");
            return false;
        }

    }

    public void crearNuevaOportunidad() {
        try {
            ValueExpression ve = getExpressionFactory().createValueExpression(getELContext(), "#{oportunidadesCrearFacesBean}", OportunidadesCrearFacesBean.class);
            OportunidadesCrearFacesBean oportunidadesCrearFacesBean = (OportunidadesCrearFacesBean) ve.getValue(getELContext());

            Oportunidad oportunidad = oportunidadesCrearFacesBean.getOportunidad();
            System.out.println("Tipo de Valor tipo Oportunidad: " + oportunidad.getIdTipoOportunidad());
            Tipo _probalidadExito = iTipoService.findByTipoId(Long.parseLong(oportunidad.getIdProbabilidadExito()));
            Tipo _probalidadEjecucion = iTipoService.findByTipoId(Long.parseLong(oportunidad.getIdProbabilidadEjecucion()));
            // Se Setea a 2; 
            String canalOportunidad = oportunidad.getIdCanalEntrada().toUpperCase();
            oportunidad.setIdCanalEntrada(canalOportunidad);
            oportunidad.setIdTipoOportunidad("2");
            oportunidad.setIdProbabilidadEjecucion(_probalidadEjecucion.getTipoValor());
            oportunidad.setIdProbabilidadExito(_probalidadExito.getTipoValor());

            //Se setean los valores faltantes para el metodo enviarSolicitud
            oportunidadesCrearFacesBean.setNit(nit);
            oportunidadesCrearFacesBean.setNombreCliente(nombreCliente);

            oportunidadesCrearFacesBean.setOportunidad(oportunidad);

            //envio de la oportunidad a BPM
            oportunidadesCrearFacesBean.enviarSolicitud();

        } catch (Exception ex) {
            performErrorMessages("Ha ocurrido un problema con la creación de la oportunidad. ->" + ex.getMessage());
            logger.error(ex.getMessage());
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

    private void armarRequestWSResultadoOportunidad() {
        requestResultadoOportunidad = new MiddlewareServiceRequestVO();
        numeroParametrosWS_ResultadoOportunidad = cargarInfoRequest(requestResultadoOportunidad, WS_BPM_PROCESS_INVOCATION);
    }

    /**
     * Metodo que carga los datos de un request y devuelve el número de
     * parametros asociado a dicho request
     *
     * @param middlewareServiceRequestVO objeto request que se quiere cargar con
     * los datos del servicio EBS
     * @param nombreServicioEBS nombre del servicio EBS que se quiere consultar
     */
    /**
     * Metodo que carga los datos de un request y devuelve el número de
     * parametros asociado a dicho request
     *
     * @param middlewareServiceRequestVO objeto request que se quiere cargar con
     * los datos del servicio EBS
     * @param nombreServicioEBS nombre del servicio EBS que se quiere consultar
     * @return número de parámetros del servicio
     */
    private int cargarInfoRequest(MiddlewareServiceRequestVO middlewareServiceRequestVO, String nombreServicioEBS) {
        try {
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(nombreServicioEBS);
            middlewareServiceRequestVO.setEndpoint(servicio.getInterfazEndpoint());
            middlewareServiceRequestVO.setMethod(servicio.getMetodo());
            middlewareServiceRequestVO.setNamespacePort(servicio.getNamespace());
            middlewareServiceRequestVO.setServiceName(servicio.getQnameServicio());
            middlewareServiceRequestVO.setWsdlUrl(servicio.getUrlWsdl());
            middlewareServiceRequestVO.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            return servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error("Ha ocurrido un error en el metodo cargarInfoRequest con  nombreServicioEBS:" + nombreServicioEBS);
            logger.error(ex.getMessage());
        }
        return 0;

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

    private void cargarOPortunidadesVisita(String nitCliente) {

        try {
            if (listaOportunidadesVisita == null) {
                listaOportunidadesVisita = new ArrayList<OportunidadVisita>();
            }
            String[] paramsService = new String[numeroParametrosWS_consultaOportunidades];
            paramsService[0] = userSession.getUserLogin();
            paramsService[1] = nitCliente;

            requestConsultaOportunidades.setParams(paramsService);

            String responseStr = userSession.getClientWs().consumeService(requestConsultaOportunidades);
            OportunidadResponseVO oportunidadResponseVO = (OportunidadResponseVO) Utils.unmarshal(OportunidadResponseVO.class, responseStr);
            if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                listaOportunidadesVisita = new ArrayList<OportunidadVisita>();
                for (OportunidadVO oportunidadVO : oportunidadResponseVO.getOportunidades()) {
                    //Se crea una oportunidadVisita a partir de los resultados de oportunidades
                    OportunidadVisita unaOportunidadVisita = new OportunidadVisita();
                    unaOportunidadVisita.setIdOportunidad(Long.parseLong(oportunidadVO.getIdOportunidad()));
                    unaOportunidadVisita.setNombreOportunidad(oportunidadVO.getNombreOportunidad());
                    listaOportunidadesVisita.add(unaOportunidadVisita);
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

    public String cargarOPortunidadesVisitaPopup() {
        listaOportunidadesVisita = null;
        cargarOPortunidadesVisita(nit);
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

    public String getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(String idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public List<Tipo> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Tipo> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<OportunidadVisita> getListaOportunidadesVisita() {
        return listaOportunidadesVisita;
    }

    public void setListaOportunidadesVisita(List<OportunidadVisita> listaOportunidadesVisita) {
        this.listaOportunidadesVisita = listaOportunidadesVisita;
    }

    public boolean isGeneraOportunidad() {
        return generaOportunidad;
    }

    public void setGeneraOportunidad(boolean generaOportunidad) {
        this.generaOportunidad = generaOportunidad;
    }

}
