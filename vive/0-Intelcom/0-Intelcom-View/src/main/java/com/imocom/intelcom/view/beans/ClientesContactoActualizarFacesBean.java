/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.HabeasDataContacto;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.IHabeasDataContactoEBSLocal;
import com.imocom.intelcom.services.interfaces.IParametrosServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import static com.imocom.intelcom.util.utility.Constants.CONTACTO_CLIENTE_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.DETALLE_CLIENTE_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_CLIENTES_ACTUALIZACION_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_CARGO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_CIUDAD;
import static com.imocom.intelcom.util.utility.Constants.TIPO_DEPARTAMENTO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PAIS;
import static com.imocom.intelcom.util.utility.Constants.TIPO_VALOR_PAIS_COLOMBIA;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_CLIENTES_ACTUALIZACION;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_CLIENTE_ACTUALIZACION;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.util.dtos.ClaveValorDTO;
import com.imocom.intelcom.util.utility.Constants;
import com.imocom.intelcom.view.util.GerenerNombreUnicos;
import com.imocom.intelcom.view.vo.ContactoCliente;
import com.imocom.intelcom.view.vo.DetalleCliente;
import com.imocom.intelcom.view.vo.DocumentosClienteDTO;
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped

public class ClientesContactoActualizarFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ClientesContactoActualizarFacesBean.class);
    private static final long serialVersionUID = 1L;

    private DetalleCliente detalleCliente;
    private ContactoCliente contactoSeleccionado;

    private List<Tipo> listaCargos;

    private boolean editando = true;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private ITipoServiceLocal iTipoService;
    @EJB
    private IParametrosServiceLocal iParametrosService;

    private UploadedFile fileDocumentoSoporteHabeasData;
    private UploadedFile fileDocumentoDatosSensible;
    private List<DocumentosClienteDTO> listaDocumentosClienteHabeasDataDTO;

    private List<ClaveValorDTO> listaClaveValorSiNoDTO;

    private RidcConnectionServiceBean iRidcConnection;

    private ContactoCliente contactoClienteConsultadoTEMPORAL;
    private DetalleCliente detalleClienteConsultadoTEMPORAL;
    private String idDocHD;
    @EJB
    private IHabeasDataContactoEBSLocal iHabeasDataContactoEBSLocal;

    @Override
    protected void build() {
        try {
            detalleClienteConsultadoTEMPORAL = (DetalleCliente) getSessionMap().get(DETALLE_CLIENTE_ID_PARAM);
            detalleCliente = new DetalleCliente(detalleClienteConsultadoTEMPORAL);

            //Se obtiene el contacto de cliente del session map
            contactoClienteConsultadoTEMPORAL = (ContactoCliente) getSessionMap().get(CONTACTO_CLIENTE_ID_PARAM);
            
            //si el cliente es un cliente nuevo, se debe construir un ContactoCliente nuevo
            contactoSeleccionado = new ContactoCliente(contactoClienteConsultadoTEMPORAL);

            editando = contactoClienteConsultadoTEMPORAL.isEditando();

            listaClaveValorSiNoDTO = new ArrayList<ClaveValorDTO>();
            listaClaveValorSiNoDTO.add(new ClaveValorDTO("S", "Si"));
            listaClaveValorSiNoDTO.add(new ClaveValorDTO("N", "No"));

            iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());

            if (contactoClienteConsultadoTEMPORAL != null && contactoClienteConsultadoTEMPORAL.getNombre() != null && contactoClienteConsultadoTEMPORAL.getApellido() != null) {

                String nombre = GerenerNombreUnicos.getNombre(contactoClienteConsultadoTEMPORAL.getNombre());
                cargarListaDocumentoHabeasDataContactor(nombre);
            }

            try {
                Tipo tipoCargo = iTipoService.findByEtiqueta(contactoSeleccionado.getCargo());
                contactoSeleccionado.setCargo(tipoCargo.getTipoValor());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
            listaCargos = iTipoService.findByTipoNombre(TIPO_CARGO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public String inactivarContactoAction() {
        try {
            armarRequest();

            ResultadoVisitaVO resultadoVisitaVO = new ResultadoVisitaVO();
            resultadoVisitaVO.setNombreUsuario(userSession.getUserLogin());
            resultadoVisitaVO.setNitClienteProspecto(detalleClienteConsultadoTEMPORAL.getNit());
            resultadoVisitaVO.setNombreClienteProspecto(detalleClienteConsultadoTEMPORAL.getNombre());

            resultadoVisitaVO.setDireccionDireccionProspecto(detalleClienteConsultadoTEMPORAL.getDireccion());
            resultadoVisitaVO.setTelefonoPrincipalDireccionProspecto(detalleClienteConsultadoTEMPORAL.getTelefono());

            Tipo tipoPais = iTipoService.findByTipoNombreValor(TIPO_PAIS, detalleClienteConsultadoTEMPORAL.getPais());
            boolean esColombia = TIPO_VALOR_PAIS_COLOMBIA.equals(tipoPais.getTipoValor());
            resultadoVisitaVO.setIdPaisDireccionProspecto("" + tipoPais.getIdTipo());
            resultadoVisitaVO.setNombrePaisDireccionProspecto(detalleClienteConsultadoTEMPORAL.getPais());
            resultadoVisitaVO.setNombreDepartamentoDireccionProspecto(detalleClienteConsultadoTEMPORAL.getDepartamento());
            resultadoVisitaVO.setCiudadDireccionProspecto(detalleClienteConsultadoTEMPORAL.getCiudad());

            if (esColombia) {
                Tipo tipoDepartamento = iTipoService.findByTipoNombreValorTipopadre(TIPO_DEPARTAMENTO, detalleClienteConsultadoTEMPORAL.getDepartamento(), tipoPais.getIdTipo());
                Tipo tipoCiudad = iTipoService.findByTipoNombreValorTipopadre(TIPO_CIUDAD, detalleClienteConsultadoTEMPORAL.getCiudad(), tipoDepartamento.getIdTipo());
                resultadoVisitaVO.setIdDepartamentoDireccionProspecto("" + tipoDepartamento.getIdTipo());
                resultadoVisitaVO.setIdCiudadDireccionProspecto("" + tipoCiudad.getIdTipo());
                //
                Tipo indicativoCliente = iTipoService.findByTipoNombreEtiqueta("INDICATIVO", tipoDepartamento.getTipoEtiqueta());
                resultadoVisitaVO.setIndicativo(indicativoCliente.getTipoValor());
                resultadoVisitaVO.setIndicativoContacto(indicativoCliente.getTipoValor());
            }

            //Datos del contacto
            resultadoVisitaVO.setIdContactoProspecto(contactoClienteConsultadoTEMPORAL.getId());
            resultadoVisitaVO.setNombreContactoProspecto(contactoClienteConsultadoTEMPORAL.getNombre());
            resultadoVisitaVO.setApellidoCliente(contactoClienteConsultadoTEMPORAL.getApellido());
            resultadoVisitaVO.setCargoContactoProspecto(contactoClienteConsultadoTEMPORAL.getCargo());
            resultadoVisitaVO.setTelefonoContactoProspecto(contactoClienteConsultadoTEMPORAL.getTelefono());
            resultadoVisitaVO.setCelularContactoProspecto(contactoClienteConsultadoTEMPORAL.getCelular());
            resultadoVisitaVO.setCorreoContactoProspecto(contactoClienteConsultadoTEMPORAL.getCorreo());

            resultadoVisitaVO.setAccionActualizacion("INACTIVANDO");

            //Tipo cargoTipo = obtenerTipoPorEtiqueta(contactoSeleccionado.getCargo(), listaCargos);
            Tipo cargoTipo = iTipoService.findByTipoNombreValor(TIPO_CARGO, (contactoClienteConsultadoTEMPORAL.getCargo()));
            if (cargoTipo != null) {
                resultadoVisitaVO.setIdCargoContactoProspecto("" + cargoTipo.getIdTipo());
                resultadoVisitaVO.setCargoContactoProspecto(cargoTipo.getTipoValor());
            }

            //se convierte el objeto a String
            String strRequest = Utils.marshal(resultadoVisitaVO);
            logger.error(strRequest);

            //se pasan los unicos 2 parámetros
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_CLIENTE_ACTUALIZACION;
            request.setParams(paramsService);

            userSession.getClientWs().consumeService(request);

            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_CLIENTES_ACTUALIZACION_KEY);

            redirect(navigationFaces.navigation.get(outcome));

        } catch (Exception ex) {
            performErrorMessages("Ha ocurrido un problema al enviar la solicitud de inactivar de información del contacto.");
            logger.error(ex.getMessage());
        }

        return null;
    }

    public void actualizarAction() {
        try {
            armarRequest();

            ResultadoVisitaVO resultadoVisitaVO = new ResultadoVisitaVO();
            resultadoVisitaVO.setNombreUsuario(userSession.getUserLogin());
            resultadoVisitaVO.setNitClienteProspecto(detalleCliente.getNit());
            resultadoVisitaVO.setNombreClienteProspecto(detalleCliente.getNombre());

            resultadoVisitaVO.setDireccionDireccionProspecto(detalleCliente.getDireccion());
            resultadoVisitaVO.setTelefonoPrincipalDireccionProspecto(detalleCliente.getTelefono());

            Tipo tipoPais = iTipoService.findByTipoNombreValor(TIPO_PAIS, detalleCliente.getPais());
            boolean esColombia = TIPO_VALOR_PAIS_COLOMBIA.equals(tipoPais.getTipoValor());
            resultadoVisitaVO.setIdPaisDireccionProspecto("" + tipoPais.getIdTipo());
            resultadoVisitaVO.setNombrePaisDireccionProspecto(detalleCliente.getPais());
            resultadoVisitaVO.setNombreDepartamentoDireccionProspecto(detalleCliente.getDepartamento());
            resultadoVisitaVO.setCiudadDireccionProspecto(detalleCliente.getCiudad());

            if (esColombia) {
                Tipo tipoDepartamento = iTipoService.findByTipoNombreValorTipopadre(TIPO_DEPARTAMENTO, detalleCliente.getDepartamento(), tipoPais.getIdTipo());
                Tipo tipoCiudad = iTipoService.findByTipoNombreValorTipopadre(TIPO_CIUDAD, detalleCliente.getCiudad(), tipoDepartamento.getIdTipo());
                resultadoVisitaVO.setIdDepartamentoDireccionProspecto("" + tipoDepartamento.getIdTipo());
                resultadoVisitaVO.setIdCiudadDireccionProspecto("" + tipoCiudad.getIdTipo());
                //
                Tipo indicativoCliente = iTipoService.findByTipoNombreEtiqueta("INDICATIVO", tipoDepartamento.getTipoEtiqueta());
                resultadoVisitaVO.setIndicativo(indicativoCliente.getTipoValor());
                resultadoVisitaVO.setIndicativoContacto(indicativoCliente.getTipoValor());
            }

            //Datos del contacto
            resultadoVisitaVO.setCedulaContactoProspecto(contactoSeleccionado.getCedulaContactoProspecto());
            resultadoVisitaVO.setAutorizaSensibles("N");
            resultadoVisitaVO.setAutorizaPersonales("N");
            resultadoVisitaVO.setIdContactoProspecto(contactoSeleccionado.getId());
            resultadoVisitaVO.setNombreContactoProspecto(contactoSeleccionado.getNombre());
            resultadoVisitaVO.setApellidoCliente(contactoSeleccionado.getApellido());
            resultadoVisitaVO.setCargoContactoProspecto(contactoSeleccionado.getCargo());
            resultadoVisitaVO.setTelefonoContactoProspecto(contactoSeleccionado.getTelefono());
            resultadoVisitaVO.setCelularContactoProspecto(contactoSeleccionado.getCelular());
            resultadoVisitaVO.setCorreoContactoProspecto(contactoSeleccionado.getCorreo());

            resultadoVisitaVO.setAccionActualizacion(editando ? "ACTUALIZANDO" : "CREANDO");

            if (!editando) {
                contactoClienteConsultadoTEMPORAL.setNombre(contactoSeleccionado.getNombre());
                contactoClienteConsultadoTEMPORAL.setApellido(contactoSeleccionado.getApellido());
            }

            //Tipo cargoTipo = obtenerTipoPorEtiqueta(contactoSeleccionado.getCargo(), listaCargos);
            Tipo cargoTipo = iTipoService.findByTipoNombreValor(TIPO_CARGO, (contactoSeleccionado.getCargo()));
            resultadoVisitaVO.setIdCargoContactoProspecto("" + cargoTipo.getIdTipo());
            resultadoVisitaVO.setCargoContactoProspecto(cargoTipo.getTipoValor());

            String idDocumentoContentHabeasData = null;
            String idDocNameHabeasData = null;

            String idDocumentoContentSensible = null;
            String idDocNameSensible = null;

            if (fileDocumentoSoporteHabeasData != null) {

                logger.info(" *********** Subiendo Documento Soporte habeas data cliente *********************** ");

                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataCliente(
                        fileDocumentoSoporteHabeasData.getInputstream(),
                        System.currentTimeMillis() + "",
                        "Autorizacion datos personales " + contactoSeleccionado.getNombre() + " " + contactoSeleccionado.getApellido(),
                        fileDocumentoSoporteHabeasData.getContentType(),
                        detalleCliente.getNit(),
                        userSession.getUsuario().getUsuario(),
                        detalleCliente.getNombre() + " - " + contactoSeleccionado.getNombre() + " " + contactoSeleccionado.getApellido(),
                        contactoSeleccionado.getCedulaContactoProspecto());

                resultadoVisitaVO.setIdContentPersonales(mapaRespuesta.get("dDocName"));
                resultadoVisitaVO.setAutorizaPersonales("S");

                idDocumentoContentHabeasData = mapaRespuesta.get("id");
                idDocNameHabeasData = mapaRespuesta.get("dDocName");

            }
            fileDocumentoSoporteHabeasData = null;

            if (fileDocumentoDatosSensible != null) {

                logger.info(" *********** Subiendo Documento DatosSensible data cliente *********************** ");

                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataCliente(
                        fileDocumentoDatosSensible.getInputstream(),
                        System.currentTimeMillis() + "",
                        "Autorizacion datos sensibles " + contactoSeleccionado.getNombre() + " " + contactoSeleccionado.getApellido(),
                        fileDocumentoDatosSensible.getContentType(),
                        detalleCliente.getNit(),
                        userSession.getUsuario().getUsuario(),
                        detalleCliente.getNombre() + " - " + contactoSeleccionado.getNombre() + " " + contactoSeleccionado.getApellido(),
                        contactoSeleccionado.getCedulaContactoProspecto());

                resultadoVisitaVO.setIdContentSensibles(mapaRespuesta.get("dDocName"));
                resultadoVisitaVO.setAutorizaSensibles("S");

                idDocumentoContentSensible = mapaRespuesta.get("id");
                idDocNameSensible = mapaRespuesta.get("dDocName");

            }
            fileDocumentoDatosSensible = null;

            //se convierte el objeto a String
            String strRequest = Utils.marshal(resultadoVisitaVO);

            //se pasan los unicos 2 parámetros
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_CLIENTE_ACTUALIZACION;
            request.setParams(paramsService);

            userSession.getClientWs().consumeService(request);
            String nombre = GerenerNombreUnicos.getNombre(contactoSeleccionado.getNombre());

            if (nombre != null && idDocumentoContentHabeasData != null && idDocNameHabeasData != null) {

                HabeasDataContacto habeasDataContacto = new HabeasDataContacto();
                habeasDataContacto.setIdContent(idDocumentoContentHabeasData);
                habeasDataContacto.setIdentificacion(nombre);
                habeasDataContacto.setIdDocName(idDocNameHabeasData);
                habeasDataContacto.setNit(detalleCliente.getNit());
                habeasDataContacto.setOrigen(Constants.ORIGEN_DOC_CLIENTE_CONTACTO_ACTUALIZAR_HABEASDATA);

                iHabeasDataContactoEBSLocal.save(habeasDataContacto);

            }

            if (nombre != null && idDocumentoContentSensible != null && idDocNameSensible != null) {

                HabeasDataContacto habeasDataContacto = new HabeasDataContacto();
                habeasDataContacto.setIdContent(idDocumentoContentSensible);
                habeasDataContacto.setIdentificacion(nombre);
                habeasDataContacto.setIdDocName(idDocNameSensible);
                habeasDataContacto.setNit(detalleCliente.getNit());
                habeasDataContacto.setOrigen(Constants.ORIGEN_DOC_CLIENTE_CONTACTO_ACTUALIZAR_SENSIBLES);

                iHabeasDataContactoEBSLocal.save(habeasDataContacto);

            }

            cargarListaDocumentoHabeasDataContactor(nombre);

            // Se redirecciona a la pagina de consulta
            //String outcome = getViewRedirect(PAGE_CLIENTES_ACTUALIZACION_KEY);
            //return navigationFaces.navigation.get(outcome);
            performInfoMessages("Información actualizada correctamente..");

        } catch (Exception ex) {
            performErrorMessages("Ha ocurrido un problema al enviar la solicitud de actualización de información del contacto.");
            logger.error(ex.getMessage());
        }

    }

    private void armarRequest() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_BPM_CLIENTES_ACTUALIZACION);
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

    public Tipo obtenerTipoPorEtiqueta(String etiqueta, List<Tipo> listaValores) {
        for (Tipo item : listaValores) {
            if (item.getTipoEtiqueta().equals(etiqueta)) {
                return item;
            }
        }
        return null;
    }

    public DetalleCliente getDetalleCliente() {
        return detalleCliente;
    }

    public void setDetalleCliente(DetalleCliente detalleCliente) {
        this.detalleCliente = detalleCliente;
    }

    public ContactoCliente getContactoSeleccionado() {
        return contactoSeleccionado;
    }

    public void setContactoSeleccionado(ContactoCliente contactoSeleccionado) {
        this.contactoSeleccionado = contactoSeleccionado;
    }

    public List<Tipo> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Tipo> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public UploadedFile getFileDocumentoSoporteHabeasData() {
        return fileDocumentoSoporteHabeasData;
    }

    public void setFileDocumentoSoporteHabeasData(UploadedFile fileDocumentoSoporteHabeasData) {
        this.fileDocumentoSoporteHabeasData = fileDocumentoSoporteHabeasData;
    }

    public UploadedFile getFileDocumentoDatosSensible() {
        return fileDocumentoDatosSensible;
    }

    public void setFileDocumentoDatosSensible(UploadedFile fileDocumentoDatosSensible) {
        this.fileDocumentoDatosSensible = fileDocumentoDatosSensible;
    }

    public List<DocumentosClienteDTO> getListaDocumentosClienteHabeasDataDTO() {
        return listaDocumentosClienteHabeasDataDTO;
    }

    public void setListaDocumentosClienteHabeasDataDTO(List<DocumentosClienteDTO> listaDocumentosClienteHabeasDataDTO) {
        this.listaDocumentosClienteHabeasDataDTO = listaDocumentosClienteHabeasDataDTO;
    }

    public List<ClaveValorDTO> getListaClaveValorSiNoDTO() {
        return listaClaveValorSiNoDTO;
    }

    public void setListaClaveValorSiNoDTO(List<ClaveValorDTO> listaClaveValorSiNoDTO) {
        this.listaClaveValorSiNoDTO = listaClaveValorSiNoDTO;
    }

    public String getIdDocHD() {
        return idDocHD;
    }

    public void setIdDocHD(String idDocHD) {
        this.idDocHD = idDocHD;
    }

    private void cargarListaDocumentoHabeasDataContactor(String nombreContacto) throws Exception {

        listaDocumentosClienteHabeasDataDTO = new ArrayList<DocumentosClienteDTO>();

        if (nombreContacto == null) {
            return;
        }
        String urlUCM =iParametrosService.findByTipoId(Constants.PARAMETRO_ID_UCM).getValor();
        logger.info("url UCM "+Constants.PARAMETRO_ID_UCM);
        List<DocumentosClienteDTO> listaTMP = iRidcConnection.searchDocsClienteHabeasData(detalleCliente.getNit(),urlUCM);

        for (DocumentosClienteDTO unDocumentosClienteDTO : listaTMP) {

            List<HabeasDataContacto> listaDocumentosHabeasNit = iHabeasDataContactoEBSLocal.findAllByNitYdocNameYnombre(detalleCliente.getNit(), unDocumentosClienteDTO.getIdDocName(), nombreContacto);

            if (!listaDocumentosHabeasNit.isEmpty()) {
                listaDocumentosClienteHabeasDataDTO.add(unDocumentosClienteDTO);
            }
        }
    }

   /* public void downloadRenditionRidc() {
        logger.info("Downlodas documents habeas data clientes contacto actualizar " + this.idDocHD);
        FacesContext fc = FacesContext.getCurrentInstance();
        byte[] bytes;
        HttpServletResponse response
                = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        try {
            if (this.idDocHD != null) {
                String id = this.idDocHD;
                logger.info("Archivo Search Id:  " + id + " : " + userSession.getRidcUrl() + " : " + userSession.getRidcUser() + " : " + userSession.getRidcPassword());
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());

                HashMap<String, String> docInfo = iRidcConnection.searchDocs(id, "", "", "");

                bytes = iRidcConnection.getBytesFile(id);
                logger.info("Archivo Search Id:  " + bytes.length);
                response.setContentType(docInfo.get("dFormat"));
                response.setContentType(docInfo.get("dOriginalName"));
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + docInfo.get("dOriginalName")
                        + "\"");
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                FacesContext.getCurrentInstance().responseComplete();
            }

        } catch (FileNotFoundException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        } catch (ServiceException ex) {
            logger.error("Error documento: " + ex.getMessage(), ex);
        }
    }*/

}
