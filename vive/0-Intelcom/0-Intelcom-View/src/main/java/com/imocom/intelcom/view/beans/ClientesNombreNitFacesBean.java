/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.FILTRO_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.MANTENER_FILTRO_CLIENTE_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_CLIENTES_DETALLE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_CARTERA_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_EVENTOS_CREAR_X_CLIENTE_KEY;
import static com.imocom.intelcom.util.utility.Constants.PAGE_OPORTUNIDADES_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_CONSULTA;
import static com.imocom.intelcom.util.utility.Constants.TIPO_CLIENTE;
import static com.imocom.intelcom.util.utility.Constants.TIPO_NIVEL_ATENCION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_SEMAFORO;

import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Oct 10, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class ClientesNombreNitFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ClientesNombreNitFacesBean.class);
    private static final long serialVersionUID = 1L;

    private List<Tipo> listaTiposCliente;
    private List<Tipo> listaNivelAtencion;
    private List<Tipo> listaSemaforo;
    private List<ClienteVO> listDatosCliente;
    private ClienteVO clienteSeleccionado;
    private String nombre;
    private String nit;
    private String tipoCliente;
    private String diasSinVisita;
    private int totalClientes;

    private String idClienteSeleccionado;
    private boolean soloClientesAsignados = true;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;

    @EJB
    private ITipoServiceLocal iTipoService;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @Override
    protected void build() {
        try {
            //Consulta de tipos de Cliente
            listaTiposCliente = iTipoService.findByTipoNombre(TIPO_CLIENTE);

            //Consulta de tipos de Niveles de Atención
            listaNivelAtencion = iTipoService.findByTipoNombre(TIPO_NIVEL_ATENCION);

            //Consulta de tipos de Cliente
            listaSemaforo = iTipoService.findByTipoNombre(TIPO_SEMAFORO);

            //Armamos el objeto request
            armarRequestWS();
            
            //Actualizado por gfandino el 05/08/2016
            String[] paramsService = new String[numeroParametrosWS];
            paramsService = (String[]) getSessionMap().get(FILTRO_CLIENTE_PARAM);
            Boolean mantenerFiltro = (Boolean) getSessionMap().get(MANTENER_FILTRO_CLIENTE_PARAM);
            if((mantenerFiltro!=null && mantenerFiltro) && (paramsService!=null && paramsService.length>0)){
                for(String tmp:paramsService){
                    logger.info("Valor parametro:  " + tmp);
                }
                logger.info("");
                listDatosCliente = new ArrayList<ClienteVO>();                    
                request.setParams(paramsService);
                String responseStr = userSession.getClientWs().consumeService(request);
                System.out.println("Clientes Filtro: "+responseStr);
                ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
                if (response != null &&response.getClientes()!=null) {                       
                    List<ClienteVO>listadoResponse = response.getClientes();
                    totalClientes=listadoResponse.size();
                    for(ClienteVO cliente : listadoResponse){
                        System.out.println("Clientes : "+cliente.getDiasSinVisita());
                        if(cliente.getDiasSinVisita()==-1){
                            cliente.setDiasSinVisita(-1);
                            if( isUsuarioTieneRolGerente() ){
                                cliente.setPerteneceAsesor(true);
                            }else{
                                cliente.setPerteneceAsesor(false);
                            }
                        }else if(cliente.getDiasSinVisita()==-2){
                            cliente.setDiasSinVisita(-1);
                            cliente.setPerteneceAsesor(true);
                        }else{
                            cliente.setPerteneceAsesor(true);
                        }
                        listDatosCliente.add(cliente);
                    }

                }             
                
                nit = paramsService[0]!=null?paramsService[0]:""; 
                nombre = paramsService[1] !=null?paramsService[1]:"";
                tipoCliente = paramsService[3] !=null?paramsService[3]:"";
                diasSinVisita = paramsService[4] !=null?paramsService[4]:"";
                soloClientesAsignados = paramsService[5].equals("1");
                
                
               
                              
                getSessionMap().put(MANTENER_FILTRO_CLIENTE_PARAM, Boolean.FALSE);
            }

            //Armamos los parametros
        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequestWS() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CLIENTE_CONSULTA);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            logger.info("Parámetros asignados a numeroParametrosWS:" + servicio.getNumeroParametros());
            numeroParametrosWS = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void buscarAction(ActionEvent actionEvent) {
        getSessionMap().remove(FILTRO_CLIENTE_PARAM);
        getSessionMap().put(MANTENER_FILTRO_CLIENTE_PARAM,Boolean.FALSE);
        try {
            logger.info("Prueba logger Consultando cliente con nit:" + nit + " y nombre:" + nombre);
            if (!soloClientesAsignados && (nombre == null || nombre.trim().length() < 5)) {
                performWarningMessages("Debe digitar un nombre de cliente con al menos 5 letras.");

            } else {
                if (nit != null && nit.trim().length() < 6 && nit.trim().length() > 0) {
                    performWarningMessages("Debe digitar un nit mayor a 6 digitos o tambien puede no digitar ningun NIT");

                } else {
                    totalClientes=0;
                    String[] paramsService = new String[numeroParametrosWS];
                    paramsService[0] = nit!=null?nit:"";
                    paramsService[1] = nombre!=null?nombre:"";                                                            
                    paramsService[3] = tipoCliente!=null?tipoCliente:"";
                    paramsService[4] = diasSinVisita!=null?diasSinVisita:"";
                    
                    if( isUsuarioTieneRolGerente() ){
                        paramsService[2] = userSession.getUserLogin();
                        // paramsService[2] = "%";
                        //  paramsService[5] = "0";
                        paramsService[5] = soloClientesAsignados?"1":"0";
                    }else{
                        paramsService[2] = userSession.getUserLogin();
                        paramsService[5] = soloClientesAsignados?"1":"0";
                    }

                    request.setParams(paramsService);

                    String responseStr = userSession.getClientWs().consumeService(request);
                    System.out.println("Response Clientes: "+responseStr);
                    ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
                     listDatosCliente=new ArrayList<ClienteVO>();
                    if (response != null &&response.getClientes()!=null) {                       
                        List<ClienteVO>listadoResponse = response.getClientes();
                        totalClientes=listadoResponse.size();
                        for(ClienteVO cliente : listadoResponse){
                            System.out.println("Clientes : "+cliente.getDiasSinVisita());
                            if(cliente.getDiasSinVisita()==-1){
                                cliente.setDiasSinVisita(-1);
                                if( isUsuarioTieneRolGerente() ){
                                    cliente.setPerteneceAsesor(true);
                                }else{
                                    cliente.setPerteneceAsesor(false);
                                }                                
                            }else if(cliente.getDiasSinVisita()==-2){
                                cliente.setDiasSinVisita(-1);
                                cliente.setPerteneceAsesor(true);
                            }else{
                                cliente.setPerteneceAsesor(true);
                            }
                            listDatosCliente.add(cliente);
                        }
                        
                    }

                }
            }

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }

    }

    public Boolean getPerteneceClienteAsesor() {
       /* if(clienteSeleccionado!=null){
            System.out.print("Cliente Seleccionado : "+clienteSeleccionado.getDiasSinVisita());
            return clienteSeleccionado.getDiasSinVisita()>0;
        }else{
            System.out.print("Cliente Seleccionado Null: ");
        }*/
        return true;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Tipo> getListaNivelAtencion() {
        return listaNivelAtencion;
    }

    public void setListaNivelAtencion(List<Tipo> listaNivelAtencion) {
        this.listaNivelAtencion = listaNivelAtencion;
    }

    public List<Tipo> getListaSemaforo() {
        return listaSemaforo;
    }

    public void setListaSemaforo(List<Tipo> listaSemaforo) {
        this.listaSemaforo = listaSemaforo;
    }

    public List<Tipo> getListaTiposCliente() {
        return listaTiposCliente;
    }

    public void setListaTiposCliente(List<Tipo> listaTiposCliente) {
        this.listaTiposCliente = listaTiposCliente;
    }

    public List<ClienteVO> getListDatosCliente() {
        return listDatosCliente;
    }

    public void setListDatosCliente(List<ClienteVO> listDatosCliente) {
        this.listDatosCliente = listDatosCliente;
    }

    public String getIdClienteSeleccionado() {
        return idClienteSeleccionado;
    }

    public void setIdClienteSeleccionado(String idClienteSeleccionado) {
        this.idClienteSeleccionado = idClienteSeleccionado;
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
        System.out.print("Seteando Cliente Seleccionado..");
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String redirectEventos() {
        redirect(PAGE_EVENTOS_CONSULTA_KEY, "redirectEventos");
        return null;
    }
    
    public String redirectCrearEventos() {
        redirect(PAGE_EVENTOS_CREAR_X_CLIENTE_KEY, "redirectEventos");
        return null;
    }

    public String redirectOportunidades() {
        redirect(PAGE_OPORTUNIDADES_CONSULTA_KEY, "redirectOportunidades");
        return null;
    }

    public String redirectCartera() {
        redirect(PAGE_CARTERA_CONSULTA_KEY, "redirectOportunidades");
        return null;
    }

    public String redirectDetalle() {
        redirect(PAGE_CLIENTES_DETALLE_KEY, "redirectDetalle");
        return null;
    }

    private void redirect(String pageKey, String nombreMetodo) {
        String outcome = getViewRedirect(pageKey);
        try {
            logger.info("El cliente seleccionado en " + nombreMetodo + " es --> " + ((clienteSeleccionado != null) ? clienteSeleccionado.getNombreCliente() : "No seleccionado"));

            if (clienteSeleccionado != null) {
                getSessionMap().put(CLIENT_ID_PARAM, clienteSeleccionado);
                if(getSessionMap().get(FILTRO_CLIENTE_PARAM)==null){
                    String[] paramsService = new String[numeroParametrosWS];
                    paramsService[0] = nit!=null?nit:"";
                    paramsService[1] = nombre!=null?nombre:"";
                    paramsService[2] = userSession.getUserLogin();
                    paramsService[3] = tipoCliente!=null?tipoCliente:"";
                    paramsService[4] = diasSinVisita!=null?diasSinVisita:"";
                    paramsService[5] = soloClientesAsignados?"1":"0";
                    getSessionMap().put(FILTRO_CLIENTE_PARAM, paramsService);
                    
                }
                getSessionMap().put(MANTENER_FILTRO_CLIENTE_PARAM, Boolean.TRUE);
                redirect(navigationFaces.navigation.get(outcome));
            }
        } catch (IOException ex) {
            String mensaje = "Recurso no encontrado (".concat(outcome).concat(")");
            logger.error(mensaje.concat(", detalles: ").concat(ex.getLocalizedMessage()));

            performErrorMessages(mensaje);
        }
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDiasSinVisita() {
        return diasSinVisita;
    }

    public void setDiasSinVisita(String diasSinVisita) {
        this.diasSinVisita = diasSinVisita;
    }
    
    public boolean _desahabilitarBoton(){
        return true;
    }

    public boolean isSoloClientesAsignados() {
        return soloClientesAsignados;
    }

    public void setSoloClientesAsignados(boolean soloClientesAsignados) {
        this.soloClientesAsignados = soloClientesAsignados;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }
    

}
