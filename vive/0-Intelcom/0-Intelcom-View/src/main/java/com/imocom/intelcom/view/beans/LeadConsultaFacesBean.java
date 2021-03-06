/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Lead;
import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.ejb3.OracleCanalesEbsImpl;
import com.imocom.intelcom.services.interfaces.ILeadServiceLocal;
import com.imocom.intelcom.services.interfaces.IOracleConsultas;
import com.imocom.intelcom.services.interfaces.IRolesServiceLocal;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ETAPA_OP;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.util.dtos.ClaveValorDTO;
import com.imocom.intelcom.ws.ebs.vo.entities.AsignacionCanalesVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@ManagedBean
@ViewScoped
public class LeadConsultaFacesBean  extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(LeadConsultaFacesBean.class);
    private static final long serialVersionUID = 1L;

    
    @EJB
    private ILeadServiceLocal iLeadServiceLocal;
    
    private List<Lead> listaLead;
    private Lead leadBusqueda;
    private Lead leadSeleccionado;
    private String observacion;
    private String asesor;
    
    private boolean esPerfilAsesor = false;
    private boolean esPerfilMercadeo = false;
    
    private List<ClaveValorDTO> listaAsesorDTO;
    private List<ClaveValorDTO> listaCanalDTO;
    
    @EJB
    private ITipoServiceLocal iTipoService;
    
    @EJB
    private IRolesServiceLocal iRolesServiceLocal;
    
    @EJB
    private IServiciosEBSLocal iServiciosESB;
    
    private IOracleConsultas iOracleConsultas;
    
    @EJB
    private IServicioGenericoLocal<Long, Parametros> iServicioGenerico;
    
    @Override
    protected void build() {
        
        try {
            
            listaLead = new ArrayList<Lead>();
            leadBusqueda = new Lead();
            
            listaAsesorDTO = new ArrayList<ClaveValorDTO>();
            
            Set<String> rolUsers =  userSession.getUserSessionRoles();
            
            for(String rol : rolUsers){
                if( "asesor".equalsIgnoreCase( rol ) ){
                    esPerfilAsesor = true;
                    listaAsesorDTO.add( new ClaveValorDTO( userSession.getUserLogin(), userSession.getUserLogin()));
                }
                if( "mercadeo".equalsIgnoreCase( rol ) ){
                    esPerfilMercadeo = true;
                }
            } 
            
            if( esPerfilMercadeo ){
                
                listaAsesorDTO = new ArrayList<ClaveValorDTO>();
                
                List lista = iRolesServiceLocal.listaDeUsuariosRolUno();
                for (Object tipo : lista) {
                    Object[] valores = (Object[])tipo;
                    listaAsesorDTO.add( new ClaveValorDTO(  valores[0] , valores[0] + " - " + valores[1]  ));
                }
            }
            
            try {
                iOracleConsultas = new OracleCanalesEbsImpl();
            
                //List<Tipo> lista = iTipoService.findByTipoNombre( Constants.TIPO_LEAD_CANAL );            

                listaCanalDTO = new ArrayList<ClaveValorDTO>();   
                List<Tipo> listaCanales = iOracleConsultas.getCanalesEBS( iServicioGenerico );
                for (Tipo kv : listaCanales) {
                    listaCanalDTO.add( new ClaveValorDTO( kv.getTipoNombre(), kv.getTipoValor() ));
                }

            } catch (Exception e) {
                logger.error("ERROR AL CARGAR CANALES: "+e.getMessage(), e);
            }
            
            servicioClienteDetalle = iServiciosESB.findByUniqueName(Constants.WS_CLIENTE_DETALLE);
            servicioOportunidadCreacion = iServiciosESB.findByUniqueName(Constants.WS_LEAD_ASIGNACIONCANALES);
            
            requestDetalleCliente = new MiddlewareServiceRequestVO();            
            requestDetalleCliente.setEndpoint(servicioClienteDetalle.getInterfazEndpoint());
            requestDetalleCliente.setMethod(servicioClienteDetalle.getMetodo());
            requestDetalleCliente.setNamespacePort(servicioClienteDetalle.getNamespace());
            requestDetalleCliente.setServiceName(servicioClienteDetalle.getQnameServicio());
            requestDetalleCliente.setWsdlUrl(servicioClienteDetalle.getUrlWsdl());
            numeroParametrosWS_Cliente = servicioClienteDetalle.getNumeroParametros();
            
            requestOportunidadCreacion = new MiddlewareServiceRequestVO();
            requestOportunidadCreacion.setEndpoint(servicioOportunidadCreacion.getInterfazEndpoint());
            requestOportunidadCreacion.setMethod(servicioOportunidadCreacion.getMetodo());
            requestOportunidadCreacion.setNamespacePort(servicioOportunidadCreacion.getNamespace());
            requestOportunidadCreacion.setServiceName(servicioOportunidadCreacion.getQnameServicio());
            requestOportunidadCreacion.setWsdlUrl(servicioOportunidadCreacion.getUrlWsdl());
            requestOportunidadCreacion.setInterfaceType(servicioOportunidadCreacion.getTipoInterfaz());
            numeroParametrosOportunidadCreacion = servicioOportunidadCreacion.getNumeroParametros();
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    private MiddlewareServiceRequestVO requestDetalleCliente;
    private MiddlewareServiceRequestVO requestOportunidadCreacion;
    private int numeroParametrosWS_Cliente = 0, numeroParametrosOportunidadCreacion = 0;


    private ServiciosEbs servicioClienteDetalle;
    private ServiciosEbs servicioOportunidadCreacion;
       
    
    /**
     * 
     * @return 
     */
    public String crearClienteProspecto() {
        try {
            
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_CORREOCONTACTO");
            
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT", leadSeleccionado.getNit() );
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMNRECLIENTE", leadSeleccionado.getEmpresa() );
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMBRECONTACTO", leadSeleccionado.getNombreContacto() );
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_APELLIDOCONTACTO", leadSeleccionado.getApellidoContacto() );
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_TELEFONOCONTACTO", leadSeleccionado.getTelefonoContacto() );
            getSessionMap().put("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_CORREOCONTACTO", leadSeleccionado.getCorreoContacto() );
            
            String outcome = getViewRedirect(Constants.PAGE_CLIENTES_PROSPECTO_CREAR);
            redirect(navigationFaces.navigation.get(outcome));
            
        }catch(Exception e ){
            performErrorMessages("Error al crear cliente prospecto");
            logger.error(e);
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public String crearOportunidad() {
        try {
            
            //VALIDAMOS QUE EL NIT ASOCIADO AL LEAD, SEA VALIDO
            //HACIENDO UNA CONSULTA AL WEBSERVICE DEL NIT ACTUAL
            //SI TRAE DETALLE, EL NIT EXISTE, DE LO CONTRARIO NO
            String[] paramsService = new String[numeroParametrosWS_Cliente];
            paramsService[0] = leadSeleccionado.getNit().trim();
            
            requestDetalleCliente.setParams(paramsService);
            String responseStr = userSession.getClientWs().consumeService(requestDetalleCliente);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            logger.info("responseStr: " + responseStr);
            if (response == null 
                    || response.getClientes() == null 
                        || response.getClientes().isEmpty() 
                            || response.getClientes().get(0).getNitCliente() == null 
                                || response.getClientes().get(0).getNitCliente().trim().length() == 0) {               
               //CLIENTE NO EXISTE
               performErrorMessages("No es posiable crear la oportunidad debido a que el nit: "+leadSeleccionado.getNit().trim()+" no exíste. ");
               return null;
            }
            
            //ENVIAMOS LA OPOTUNIDAD
            AsignacionCanalesVO asignacionCanalesVO = new AsignacionCanalesVO();
            //oportunidadBPMVO.setNombreUsuario(userSession.getUserLogin());
            asignacionCanalesVO.setNombreUsuario("");
            asignacionCanalesVO.setNombreOportunidad( leadSeleccionado.getNombreProducto() );
            asignacionCanalesVO.setIdCliente( leadSeleccionado.getNit().trim() );
            asignacionCanalesVO.setNombreCliente( leadSeleccionado.getEmpresa() );
            asignacionCanalesVO.setIdUsuarioModificador("");            
            asignacionCanalesVO.setIdTipoOportunidad("2");
            asignacionCanalesVO.setIdAsesor( leadSeleccionado.getAsesor() );
            asignacionCanalesVO.setIdCorreoAsesor("");
            asignacionCanalesVO.setIdGerenteLinea("");
            asignacionCanalesVO.setIdCorreoGerenteLinea("");
            asignacionCanalesVO.setNombreTipoOportunidad("");
            asignacionCanalesVO.setIdIncoterm("");
            asignacionCanalesVO.setNombreIncoterm("");
            asignacionCanalesVO.setIdTipoMoneda("USD");
            asignacionCanalesVO.setIdCanalEntrada( leadSeleccionado.getCanal().trim() );
            asignacionCanalesVO.setProbabilidadProyecto("1");
            asignacionCanalesVO.setProbabilidadImocom("1");
            asignacionCanalesVO.setObservacion( leadSeleccionado.getObservacion() == null ?  "" : leadSeleccionado.getObservacion().trim());
            
            ProductoVO productoVO = new ProductoVO();
            productoVO.setCodigo( leadSeleccionado.getCodigoProducto() );
            productoVO.setNombre( leadSeleccionado.getNombreProducto() );
            productoVO.setDivision("");
            productoVO.setLinea( leadSeleccionado.getLineaProducto() );
            productoVO.setTecnologia("");
            productoVO.setFamilia("");
            productoVO.setMarca( leadSeleccionado.getMarcaProducto() );
            productoVO.setCantidad( leadSeleccionado.getCantidadProducto() == null ? "" : leadSeleccionado.getCantidadProducto().toString()  );
            productoVO.setPrecioLista("");
            productoVO.setMoneda("USD");
            productoVO.setDescripcion("");
            productoVO.setModelo( leadSeleccionado.getModeloProducto() );
            productoVO.setTipoProducto("1");
            productoVO.setValorUnitario("0");
            productoVO.setBodega("IMOCOM MAESTRA INVENTARIO");
            productoVO.setUnidad("UND");
            productoVO.setPrecioListaSinFormato("");
            productoVO.setLinkCaracteristicas("");
            productoVO.setLinkAccesorios("");
            productoVO.setLinkVideo("");
            productoVO.setLinkImg("");
            productoVO.setOrganizationId("");
            productoVO.setFecha("");
            productoVO.setAsesor( leadSeleccionado.getAsesor() );
            
            List<ProductoVO> listaProductoVOs = new ArrayList<ProductoVO>();
            listaProductoVOs.add(productoVO);
            
            asignacionCanalesVO.setOportunidadProducto( listaProductoVOs );
            
            asignacionCanalesVO.setMonto("0");
            Date hoy = new Date();            
            asignacionCanalesVO.setFechaCreacion( DateUtil.formatBPMTime( hoy ));
            
            Calendar hoyMesTresMese = Calendar.getInstance();
            hoyMesTresMese.setTime(hoy);
            hoyMesTresMese.add( Calendar.MONTH, 3);
            
            asignacionCanalesVO.setFechaEstimadaCierre( DateUtil.formatBPMTime( hoyMesTresMese.getTime() ));
            asignacionCanalesVO.setFechaModificacion( DateUtil.formatBPMTime( hoy ));
            asignacionCanalesVO.setIdEstadoOportunidad(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
            asignacionCanalesVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
            asignacionCanalesVO.setIdArchivo("");
            asignacionCanalesVO.setNombreArchivo("");
            asignacionCanalesVO.setMotivoCierre("");
            asignacionCanalesVO.setIdOportunidad("");
            asignacionCanalesVO.setFisrtProducto( productoVO );  
            asignacionCanalesVO.setRespuestaCreacion("");
            asignacionCanalesVO.setLinea( leadSeleccionado.getLineaProducto() );
            asignacionCanalesVO.setIdArchivoPedido("");
            asignacionCanalesVO.setNombreArchivoPedido("");
            
            String strRequest = Utils.marshal(asignacionCanalesVO);
            logger.info("responseStr: " + strRequest);
            
            String[] paramsServiceOpor = new String[numeroParametrosOportunidadCreacion];
            paramsServiceOpor[0] = strRequest;
            paramsServiceOpor[1] = Constants.WS_PROCESS_ENTITY_ASIGNACION_CANALES;
            requestOportunidadCreacion.setParams(paramsServiceOpor);
            
            userSession.getClientWs().consumeService(requestOportunidadCreacion);
            
            leadSeleccionado.setFechaUltimaActualizacion( new Date() );
            leadSeleccionado.setEstado( Constants.LEAD_ESTADO_CREACION_OPORTUNIDAD );
            leadSeleccionado.setObservacionCancela("Se crea oportunidad");
            
            iLeadServiceLocal.update( leadSeleccionado );
            
            reiniciarDatos();
            
            buscarAction( null );
            
            performInfoMessages("La oportunidad ha sido enviada para su creación.");
            
        }catch(Exception e ){
            performErrorMessages("Error al crear la oportunidad lead");
            logger.error(e);
        }
        return null;
    }
    
    
    /**
     * Se reinicia tabla de listado de oportunidades
     */
    public void reiniciarDatos() {
        
        asesor = null;
        observacion = null;
        
    }
    
    public void cancelarAction(ActionEvent actionEvent) {
        try {
            
            leadSeleccionado.setFechaUltimaActualizacion( new Date() );
            leadSeleccionado.setFechaCancela(new Date() );
            leadSeleccionado.setEstado( Constants.LEAD_ESTADO_CANCELADO );
            leadSeleccionado.setObservacionCancela(observacion);
            leadSeleccionado.setUsuarioCancela( userSession.getUserLogin() );
            
            iLeadServiceLocal.update( leadSeleccionado );
            
            reiniciarDatos();
            
            String outcome = getViewRedirect(Constants.PAGE_LEAD_CONSULTAR_KEY);
            redirect(navigationFaces.navigation.get(outcome));
            
        }catch(Exception e ){
            performErrorMessages("Error al cancelar lead");
            logger.error(e);
        }
    }
    
    public String editarLead(){
        
        getSessionMap().remove("LEAD_SELECCIONADO_EDITAR");
        if( leadSeleccionado != null ){
            
            getSessionMap().put("LEAD_SELECCIONADO_EDITAR", leadSeleccionado);
            
            String outcome = getViewRedirect(Constants.PAGE_LEAD_EDITAR_KEY);
            return navigationFaces.navigation.get(outcome);
            
        }
        return null;
    }
    
    public void resignaAction(ActionEvent actionEvent) {
        try {
            
            leadSeleccionado.setFechaUltimaActualizacion( new Date() );
            leadSeleccionado.setFechaReasigna( new Date() );
            leadSeleccionado.setEstado( Constants.LEAD_ESTADO_NUEVO );
            leadSeleccionado.setObservacionReasigna( observacion);            
            String usuarioActual = leadSeleccionado.getAsesor();
            leadSeleccionado.setUsuarioAnterior( usuarioActual );
            leadSeleccionado.setAsesor( asesor );
            leadSeleccionado.setUsuarioReasigna( userSession.getUserLogin() );
            
            iLeadServiceLocal.update( leadSeleccionado );
            
            reiniciarDatos();
            
            String outcome = getViewRedirect(Constants.PAGE_LEAD_CONSULTAR_KEY);
            redirect(navigationFaces.navigation.get(outcome));
            
        }catch(Exception e ){
            performErrorMessages("Error al cancelar lead");
            logger.error(e);
        }
    }
    
    public void buscarAction(ActionEvent actionEvent) {
        
        try {
            
            listaLead = iLeadServiceLocal.findListByParm( leadBusqueda );
            
            for (Lead lead : listaLead) {
                
                String[] paramsService = new String[numeroParametrosWS_Cliente];
                paramsService[0] = lead.getNit().trim();

                requestDetalleCliente.setParams(paramsService);
                String responseStr = userSession.getClientWs().consumeService(requestDetalleCliente);
                ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
                logger.info("responseStr: " + responseStr);
                
                lead.setClienteInvalido( 
                        response == null 
                            || response.getClientes() == null 
                                || response.getClientes().isEmpty() 
                                    || response.getClientes().get(0).getNitCliente() == null 
                                        || response.getClientes().get(0).getNitCliente().trim().length() == 0);
                
            }
            
        } catch (Exception ex) {
            
            performErrorMessages("Error al consultar lead");
            logger.error(ex);
        }

    }

    public List<Lead> getListaLead() {
        return listaLead;
    }

    public void setListaLead(List<Lead> listaLead) {
        this.listaLead = listaLead;
    }

    public Lead getLeadBusqueda() {
        return leadBusqueda;
    }

    public void setLeadBusqueda(Lead leadBusqueda) {
        this.leadBusqueda = leadBusqueda;
    }

    public Lead getLeadSeleccionado() {
        return leadSeleccionado;
    }

    public void setLeadSeleccionado(Lead leadSeleccionado) {
        this.leadSeleccionado = leadSeleccionado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }    

    public List<ClaveValorDTO> getListaAsesorDTO() {
        return listaAsesorDTO;
    }

    public void setListaAsesorDTO(List<ClaveValorDTO> listaAsesorDTO) {
        this.listaAsesorDTO = listaAsesorDTO;
    }

    public List<ClaveValorDTO> getListaCanalDTO() {
        return listaCanalDTO;
    }

    public void setListaCanalDTO(List<ClaveValorDTO> listaCanalDTO) {
        this.listaCanalDTO = listaCanalDTO;
    }

    public boolean isEsPerfilAsesor() {
        return esPerfilAsesor;
    }

    public void setEsPerfilAsesor(boolean esPerfilAsesor) {
        this.esPerfilAsesor = esPerfilAsesor;
    }

    public boolean isEsPerfilMercadeo() {
        return esPerfilMercadeo;
    }

    public void setEsPerfilMercadeo(boolean esPerfilMercadeo) {
        this.esPerfilMercadeo = esPerfilMercadeo;
    }

    
}
