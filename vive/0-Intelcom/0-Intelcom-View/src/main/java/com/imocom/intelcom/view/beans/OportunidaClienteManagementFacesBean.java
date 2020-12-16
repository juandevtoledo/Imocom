/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Marca;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.service.ClientesServices;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.IUsuarioServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.service.OportunidadService;
import com.imocom.intelcom.services.interfaces.IMarcaServiceLocal;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EJECUCION;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PROBABILIDAD_EXITO;
import com.imocom.intelcom.view.vo.AsesorVO;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.ebs.interfaces.IProcesosBPM;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@ManagedBean
@ViewScoped
public class OportunidaClienteManagementFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(OportunidaClienteManagementFacesBean.class);
    private List<AsesorVO> asesores;
    private String asesorSelectedVO;
    private String asesorSelectedVOAsig;
    private String tipoBusqueda;
    private String tableNombre;
    private List<Oportunidad> oportunidades;
    private List<ClienteVO> clientes;
    private static final long ROL_ASESOR = 1L;
    private Oportunidad oportunidadSelect;
    private List<Tipo> listaProbabilidadExito;
    private List<Tipo> listaProbabilidadEjecucion;
    private List<Tipo> listaProbabilidadNegocio;
    private List<ClienteVO> selectedClients;
    @EJB
    private IUsuarioServiceLocal usuarioServiceLocal;
    @EJB
    private IServiciosEBSLocal iServiciosESB;
    @EJB
    private ITipoServiceLocal iTipoService;
    @EJB
    private IMarcaServiceLocal iMarcaServiceLocal;
    @EJB
    private ImodeloServiceLocal imodeloServiceLocal;
    @EJB
    private IProductoServiceLocal iProductoServiceLocal;
    private OportunidadService oportunidadService;
    private ClientesServices clientesServices;
    private List<Marca> marcas;
    private List<Oportunidad> oportunidadesSelect;

    @Override
    protected void build() {
        tipoBusqueda = "cliente";
        cargarAsesores();
        oportunidadService = new OportunidadService(iServiciosESB, userSession.getClientWs(), imodeloServiceLocal, iProductoServiceLocal);
        clientesServices = new ClientesServices(iServiciosESB, userSession.getClientWs());
        tiposProbabilidad();
        cargarMarcas();
    }

    private void tiposProbabilidad() {
        //Consulta de tipos de Probabilidad Exito
        try {
            listaProbabilidadExito = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EXITO);
            //Consulta de tipos de Probabilidad Ejecucion
            listaProbabilidadEjecucion = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);
            //Consulta de tipos de Probabilidad negocio
            listaProbabilidadNegocio = iTipoService.findByTipoNombre(TIPO_PROBABILIDAD_EJECUCION);
        } catch (ServiceException ex) {
            logger.error("Error cargando tipos : " + ex.getMessage(), ex);
        }
    }

    public void cargaModelos() {
        System.out.println("Cargar Modelos ...");
    }

    private void cargarMarcas() {
        try {
            marcas = iMarcaServiceLocal.buscarMarcasPorLinea(userSession.getUsuario().getLinea());
        } catch (ServiceException ex) {
            logger.error("Error cargando marcas msg : " + ex.getMessage(), ex);
            marcas = new ArrayList<Marca>();
        }
    }

    public void cargarAsesores() {
        asesores = new ArrayList<AsesorVO>();
        try {
            logger.info("Asesor Linea "+userSession.getUsuario().getLinea());
            logger.info("ROL_ASESOR "+ROL_ASESOR);
            List<Usuario> usuarios = usuarioServiceLocal.findByLineaAndRole(userSession.getUsuario().getLinea(), ROL_ASESOR);
            for (Usuario user : usuarios) {
                asesores.add(AsesorVO.toAsesorVO(user));
            }
        } catch (ServiceException ex) {
            logger.error("Error cargando Asesores : " + ex.getMessage(), ex);
        }
    }

    public void buscarAction(ActionEvent actionEvent) {
        if (this.tipoBusqueda.equals("oportunidad")) {
            this.oportunidades = new ArrayList<Oportunidad>();
            this.oportunidades.addAll(oportunidadService.buscar(asesorSelectedVO));
        }
        if (this.tipoBusqueda.equals("cliente")) {
            this.clientes = new ArrayList<ClienteVO>();
            this.clientes.addAll(clientesServices.buscarAction(asesorSelectedVO));
        }
    }

    public void asignarAsesor(ActionEvent actionEvent) {
        
        if (tipoBusqueda.equals("oportunidad")) {
            logger.info("Asignado asesor a Oportunidad .... ... ..." + this.oportunidadesSelect.size());
            for (Oportunidad oportunidad : oportunidadesSelect) {
                oportunidadService.asignar(this.asesorSelectedVOAsig, oportunidad.getIdOportunidad());
                logger.info("Asignado asesor a aportunidad ... " + oportunidad.getIdOportunidad());
            }
            clientes.clear();
            performInfoMessages("Oportunidad asignados al Asesor : " + this.asesorSelectedVOAsig);
        }
        if (tipoBusqueda.equals("cliente")) {
            logger.info("Asignado asesor .... ... ..." + selectedClients.size());
            for (ClienteVO clients : selectedClients) {
                
                clientesServices.asignar(asesorSelectedVO,this.asesorSelectedVOAsig, clients.getNitCliente());
                logger.info("Asignado asesor a cliente ... " + clients.getNitCliente());
            }
            clientes.clear();
            performInfoMessages("Clientes asignados al Asesor : " + this.asesorSelectedVOAsig);
        }

    }
    
    public void actualizarProbabilidadOportunidad() {
        logger.info("Actualizado oportunida .... -> "+oportunidadSelect.getIdOportunidad());
        oportunidadService.actualizar(userSession.getUsuario().getUsuario(), oportunidadSelect);
    }

    public void updateAsesor() {
        this.oportunidades = new ArrayList<Oportunidad>();
        this.clientes = new ArrayList<ClienteVO>();
    }

    public List<AsesorVO> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<AsesorVO> asesores) {
        this.asesores = asesores;
    }

    public String getAsesorSelectedVO() {
        return asesorSelectedVO;
    }

    public void setAsesorSelectedVO(String asesorSelectedVO) {
        this.asesorSelectedVO = asesorSelectedVO;
    }

    public List<Oportunidad> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<Oportunidad> oportunidades) {
        this.oportunidades = oportunidades;
    }

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public List<ClienteVO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteVO> clientes) {
        this.clientes = clientes;
    }

    public Oportunidad getOportunidadSelect() {
        return oportunidadSelect;
    }

    public void setOportunidadSelect(Oportunidad oportunidadSelect) {
        this.oportunidadSelect = oportunidadSelect;
    }

    public List<Tipo> getListaProbabilidadExito() {
        return listaProbabilidadExito;
    }

    public void setListaProbabilidadExito(List<Tipo> listaProbabilidadExito) {
        this.listaProbabilidadExito = listaProbabilidadExito;
    }

    public List<ClienteVO> getSelectedClients() {
        return selectedClients;
    }

    public void setSelectedClients(List<ClienteVO> selectedClients) {
        this.selectedClients = selectedClients;
    }

    public String getAsesorSelectedVOAsig() {
        return asesorSelectedVOAsig;
    }

    public void setAsesorSelectedVOAsig(String asesorSelectedVOAsig) {
        this.asesorSelectedVOAsig = asesorSelectedVOAsig;
    }

    public List<Tipo> getListaProbabilidadNegocio() {
        return listaProbabilidadNegocio;
    }

    public void setListaProbabilidadNegocio(List<Tipo> listaProbabilidadNegocio) {
        this.listaProbabilidadNegocio = listaProbabilidadNegocio;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public List<Oportunidad> getOportunidadesSelect() {
        return oportunidadesSelect;
    }

    public void setOportunidadesSelect(List<Oportunidad> oportunidadesSelect) {
        this.oportunidadesSelect = oportunidadesSelect;
    }

}
