/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.HabeasDataContacto;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.IHabeasDataContactoEBSLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.PAGE_CLIENTES_CONSULTA_KEY;
import static com.imocom.intelcom.util.utility.Constants.TIPO_CARGO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_SECTOR_INDUSTRIAL;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_PROCESS_INVOCATION;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_RESULTADO_VISITA;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PAIS;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_CONSULTA_EXISTE;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_DETALLE;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.view.util.GerenerNombreUnicos;
import static com.imocom.intelcom.view.util.JSFUtils.getViewRedirect;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import com.imocom.intelcom.ws.ebs.vo.entities.TipoDocumentos;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class ClientesProspectoCrearFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CarteraConsultarFacesBean.class);
    private static final long serialVersionUID = 1L;

    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;

    private MiddlewareServiceRequestVO requestDetalleCliente;
    private int numeroParametrosWS_DetalleCliente = 0;

    private MiddlewareServiceRequestVO requestConsultaExistenciaCliente;
    private int numeroParametrosWS_consultaExistenciaCliente = 0;

    private String nit;
    private String empresa;
    private String apellidoempresa;
    private String pais;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String linea;
    private String sector;
    private String observacion;

    private String contacto;
    private String telefonoContacto;
    private String cargo;
    private String celular;
    private String correo;

    private String nombreRepresentantelegal;
    private String apellidoRepresentantelegal;
    private String cedulaRepresentantelegal;
    private String nombreGerentelegal;
    private String apellidoGerentelegal;
    private String cedulaGerentelegal;

    private boolean esPaisColombia;

    private List<Tipo> listaSectores;
    private List<Tipo> listaPaises;
    private List<Tipo> listaCiudad;
    private List<Tipo> listaDepto;
    private List<Tipo> listaCargos;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @EJB
    private ITipoServiceLocal iTipoService;

    private UploadedFile file;
    private UploadedFile fileRUT;
    private UploadedFile fileCamaraComercio;
    private UploadedFile fileRepresentanteLegal;
    private UploadedFile fileGerencia;
    private UploadedFile fileContacto;
    /**
     * Conexión al UCM para guardar documento
     */
    private RidcConnectionServiceBean iRidcConnection;

    @Override
    protected void build() {
        try {
            //Consulta de tipos de Cliente
            listaSectores = iTipoService.findByTipoNombre(TIPO_SECTOR_INDUSTRIAL);
            listaCargos = iTipoService.findByTipoNombre(TIPO_CARGO);
            listaPaises = iTipoService.findByTipoNombre(TIPO_PAIS);

            //Se inicializan los valores para Colombia
            pais = "" + userSession.getTipoPais_Colombia().getIdTipo();
            listaDepto = iTipoService.findByTipoNombrePadre(userSession.getTipoPais_Colombia().getIdTipo());
            esPaisColombia = true;
            listaCiudad = null;

            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT")) {
                nit = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NIT");
            }
            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMNRECLIENTE")) {
                empresa = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMNRECLIENTE");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMNRECLIENTE");
            }
            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMBRECONTACTO")) {
                contacto = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMBRECONTACTO");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_NOMBRECONTACTO");
            }
            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_APELLIDOCONTACTO")) {
                apellidoempresa = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_APELLIDOCONTACTO");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_APELLIDOCONTACTO");
            }
            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_TELEFONOCONTACTO")) {
                telefonoContacto = (String) getSessionMap().get("");
                celular = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_TELEFONOCONTACTO");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_TELEFONOCONTACTO");
            }
            if (getSessionMap().containsKey("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_CORREOCONTACTO")) {
                correo = (String) getSessionMap().get("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_CORREOCONTACTO");
                getSessionMap().remove("PAGE_CLIENTES_PROSPECTO_CREAR_INFO_CORREOCONTACTO");
            }

            armarRequest();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    private void armarRequest() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_BPM_PROCESS_INVOCATION);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWS = servicio.getNumeroParametros();

            //Detalle del Cliente
            requestDetalleCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioDetalle = iServiciosESB.findByUniqueName(WS_CLIENTE_DETALLE);
            requestDetalleCliente.setEndpoint(servicioDetalle.getInterfazEndpoint());
            requestDetalleCliente.setMethod(servicioDetalle.getMetodo());
            requestDetalleCliente.setNamespacePort(servicioDetalle.getNamespace());
            requestDetalleCliente.setServiceName(servicioDetalle.getQnameServicio());
            requestDetalleCliente.setWsdlUrl(servicioDetalle.getUrlWsdl());
            //Cargamos el numero de parametros
            logger.info("numeroParametrosWS_DetalleCliente en ClientesProspectoCrearFacesBean: " + servicioDetalle.getNumeroParametros());
            numeroParametrosWS_DetalleCliente = servicioDetalle.getNumeroParametros();

            //Consulta Existencia Cliente
            requestConsultaExistenciaCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioConsultaExistenciaCliente = iServiciosESB.findByUniqueName(WS_CLIENTE_CONSULTA_EXISTE);
            requestConsultaExistenciaCliente.setEndpoint(servicioConsultaExistenciaCliente.getInterfazEndpoint());
            requestConsultaExistenciaCliente.setMethod(servicioConsultaExistenciaCliente.getMetodo());
            requestConsultaExistenciaCliente.setNamespacePort(servicioConsultaExistenciaCliente.getNamespace());
            requestConsultaExistenciaCliente.setServiceName(servicioConsultaExistenciaCliente.getQnameServicio());
            requestConsultaExistenciaCliente.setWsdlUrl(servicioConsultaExistenciaCliente.getUrlWsdl());
            //Cargamos el numero de parametros
            logger.info("numeroParametrosWS_consultaExistenciaCliente en ClientesProspectoCrearFacesBean: " + servicioConsultaExistenciaCliente.getNumeroParametros());
            numeroParametrosWS_consultaExistenciaCliente = servicioConsultaExistenciaCliente.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Se carga el Archivo en el Content Servers
     *
     * @param is
     * @param filename
     * @param contentType
     * @param tipo
     * @return
     */
    public HashMap<String, String> upload(InputStream is, String filename, String contentType) {
        if (is != null) {
            try {
                logger.info(" *********** Subiendo Documento UCM *********************** ");
                String name = "" + System.currentTimeMillis();
                String title = "HabeasDataContato_" + userSession.getUserLogin() + "_" + this.nit;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                return iRidcConnection.checkingDocsSoporteHabeasDataCliente(is, name, title, contentType, this.nit, "S", this.contacto, this.cedulaGerentelegal);
            } catch (ServiceException ex) {
                logger.error("Error Subiendo el documento de la cotización al UCM bean: " + ex.getMessage());
                return null;
            }
        } else {
            logger.error("No se ha cargado un documento en la cotización: ");
        }
        return new HashMap<String, String>();
    }

    @EJB
    private IHabeasDataContactoEBSLocal iHabeasDataContactoEBSLocal;

    private boolean enviarSolicitud() {

        try {
            //Se validan campos no requeridos
            this.cedulaRepresentantelegal = this.cedulaRepresentantelegal != null ? this.cedulaRepresentantelegal.toUpperCase() : "";
            this.cedulaGerentelegal = this.cedulaGerentelegal != null ? this.cedulaGerentelegal.toUpperCase() : "";
            this.nombreRepresentantelegal = this.nombreRepresentantelegal != null ? this.nombreRepresentantelegal.toUpperCase() : "";
            this.apellidoRepresentantelegal = this.apellidoRepresentantelegal != null ? this.apellidoRepresentantelegal.toUpperCase() : "";
            this.nombreGerentelegal = this.nombreGerentelegal != null ? this.nombreGerentelegal.toUpperCase() : "";
            this.apellidoGerentelegal = this.apellidoGerentelegal != null ? this.apellidoGerentelegal.toUpperCase() : "";
            this.apellidoempresa = this.apellidoempresa != null ? this.apellidoempresa.toUpperCase() : "";
            //
            ResultadoVisitaVO resultadoVisitaVO = new ResultadoVisitaVO();
            resultadoVisitaVO.setNombreUsuario(userSession.getUserLogin());
            resultadoVisitaVO.setCreacionProspecto("TRUE");
            resultadoVisitaVO.setNitClienteProspecto(nit);
            resultadoVisitaVO.setNombreClienteProspecto(empresa.toUpperCase());
            resultadoVisitaVO.setApellidoCliente(apellidoempresa.toUpperCase());
            resultadoVisitaVO.setCedulaRepresentantelegal(this.cedulaRepresentantelegal);
            resultadoVisitaVO.setCedulaGerentelegal(this.cedulaGerentelegal);
            resultadoVisitaVO.setNombreRepresentantelegal(this.nombreRepresentantelegal);
            resultadoVisitaVO.setApellidoRepresentanteLegal(this.apellidoRepresentantelegal);
            resultadoVisitaVO.setNombreGerentelegal(this.nombreGerentelegal);
            resultadoVisitaVO.setApellidoGerenteGeneral(this.apellidoGerentelegal);
            String paisDireccion = obtenerValorTipo(pais, listaPaises);

            resultadoVisitaVO.setIdPaisDireccionProspecto(pais);
            resultadoVisitaVO.setNombrePaisDireccionProspecto(paisDireccion);
            if (esPaisColombia) {
                resultadoVisitaVO.setIdCiudadDireccionProspecto(ciudad);
                resultadoVisitaVO.setIdDepartamentoDireccionProspecto(departamento);
                String ciudadDireccion = obtenerValorTipo(ciudad, listaCiudad);
                String departamentoDireccion = obtenerValorTipo(departamento, listaDepto);
                resultadoVisitaVO.setCiudadDireccionProspecto(ciudadDireccion);
                resultadoVisitaVO.setNombreDepartamentoDireccionProspecto(departamentoDireccion);
                Tipo departamentoTipo = iTipoService.findByTipoId(Long.parseLong(departamento));
                Tipo indicativoCliente = iTipoService.findByTipoNombreEtiqueta("INDICATIVO", departamentoTipo.getTipoEtiqueta());
                resultadoVisitaVO.setIndicativo(indicativoCliente.getTipoValor());
                resultadoVisitaVO.setIndicativoContacto(indicativoCliente.getTipoValor());
            } else {
                resultadoVisitaVO.setCiudadDireccionProspecto(ciudad);
                resultadoVisitaVO.setNombreDepartamentoDireccionProspecto(departamento);
            }
            resultadoVisitaVO.setDireccionDireccionProspecto(direccion);
            resultadoVisitaVO.setTelefonoPrincipalDireccionProspecto(telefono);
            resultadoVisitaVO.setIdLineaProspecto(linea);
            resultadoVisitaVO.setIdSectorIndustrialProspecto(sector);
            String sectorNombre = obtenerValorTipo(sector, listaSectores);
            resultadoVisitaVO.setNombreSectorIndustriaProspecto(sectorNombre);
            resultadoVisitaVO.setObservacionProspecto(observacion);

            resultadoVisitaVO.setNombreContactoProspecto(contacto);
            resultadoVisitaVO.setTelefonoContactoProspecto(telefonoContacto);
            resultadoVisitaVO.setCelularContactoProspecto(celular);
            resultadoVisitaVO.setCorreoContactoProspecto(correo);

            String cargoNombre = obtenerValorTipo(cargo, listaCargos);
            resultadoVisitaVO.setIdCargoContactoProspecto(cargo);
            resultadoVisitaVO.setCargoContactoProspecto(cargoNombre);
            List<TipoDocumentos> tipoDocumentos = new ArrayList<TipoDocumentos>();
            logger.info("Se Diligenciaron los datos, se va a cargar los documentos ");
            //Id Content Personal
            if (file != null) {
                logger.info(" *********** Subiendo Documento DATA PERSONAL *********************** ");
                String nameDocument = "";
                int idDocumentoContent = 0;
                nameDocument = file.getFileName();
                HashMap<String, String> rFile;
                String webLocation = "";
                try {
                    rFile = upload(file.getInputstream(), file.getFileName(), file.getContentType());
                    logger.info("Carga de Documento Entrada");
                    if (rFile == null) {
                        idDocumentoContent = -1;
                    } else {
                        if (!rFile.isEmpty()) {
                            idDocumentoContent = Integer.parseInt(rFile.get("id"));
                            webLocation = rFile.get("webLocation");
                        }
                    }
                    resultadoVisitaVO.setIdContentPersonales("" + idDocumentoContent);
                    logger.info("Cheking File Habeas Data , con id " + idDocumentoContent);
                    TipoDocumentos _tipoDoc = new TipoDocumentos();
                    _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                    _tipoDoc.setTituloDocumento(file.getFileName());
                    tipoDocumentos.add(_tipoDoc);
                } catch (IOException ex) {
                    logger.error("Error creando contacto " + ex.getMessage(), ex);
                }
            }

            if (fileRUT != null) {

                logger.info(" *********** Subiendo Documento RUT *********************** ");
                String nameDocument = "";
                int idDocumentoContent = 0;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto(
                        fileRUT.getInputstream(),
                        System.currentTimeMillis() + "",
                        "DOCUMENTO_CLIENTE_RUT_" + nit + "_" + empresa.toUpperCase(),
                        fileRUT.getContentType(),
                        nit,
                        userSession.getUsuario().getUsuario(),
                        "DOCUMENTO_CLIENTE_RUT_" + nit + "_" + empresa.toUpperCase(),
                        nit,
                        "DOCUMENTO_SOPORTE_HABEAS_DATA_CLIENTE_" + nit + "_" + empresa.toUpperCase());
                if (mapaRespuesta == null) {
                    idDocumentoContent = -1;
                } else {
                    if (!mapaRespuesta.isEmpty()) {
                        idDocumentoContent = Integer.parseInt(mapaRespuesta.get("id"));
                    }
                }
                TipoDocumentos _tipoDoc = new TipoDocumentos();
                _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                _tipoDoc.setTituloDocumento(fileRUT.getFileName());
                tipoDocumentos.add(_tipoDoc);
                fileRUT = null;
            }

            if (fileCamaraComercio != null) {

                logger.info(" *********** Subiendo Documento *********************** ");
                int idDocumentoContent = 0;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto(
                        fileCamaraComercio.getInputstream(),
                        System.currentTimeMillis() + "",
                        "DOCUMENTO_CLIENTE_CAMARA_COMERCIO_" + nit + "_" + empresa.toUpperCase(),
                        fileCamaraComercio.getContentType(),
                        nit,
                        userSession.getUsuario().getUsuario(),
                        "DOCUMENTO_CLIENTE_CAMARA_COMERCIO_" + nit + "_" + empresa.toUpperCase(),
                        nit,
                        "DOCUMENTO_CLIENTE_CAMARA_COMERCIO_" + nit + "_" + empresa.toUpperCase());

                if (mapaRespuesta == null) {
                    idDocumentoContent = -1;
                } else {
                    if (!mapaRespuesta.isEmpty()) {
                        idDocumentoContent = Integer.parseInt(mapaRespuesta.get("id"));
                    }
                }
                TipoDocumentos _tipoDoc = new TipoDocumentos();
                _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                _tipoDoc.setTituloDocumento(fileCamaraComercio.getFileName());
                tipoDocumentos.add(_tipoDoc);
                fileCamaraComercio = null;
            }

            if (fileGerencia != null) {

                logger.info(" *********** Subiendo Documento *********************** ");
                int idDocumentoContent = 0;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto(
                        fileGerencia.getInputstream(),
                        System.currentTimeMillis() + "",
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase(),
                        fileGerencia.getContentType(),
                        nit,
                        userSession.getUsuario().getUsuario(),
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase(),
                        nit,
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase());
                if (mapaRespuesta == null) {
                    idDocumentoContent = -1;
                } else {
                    if (!mapaRespuesta.isEmpty()) {
                        idDocumentoContent = Integer.parseInt(mapaRespuesta.get("id"));
                    }
                }
                TipoDocumentos _tipoDoc = new TipoDocumentos();
                _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                _tipoDoc.setTituloDocumento(fileGerencia.getFileName());
                tipoDocumentos.add(_tipoDoc);
                fileGerencia = null;
            }

            if (fileContacto != null) {

                logger.info(" *********** Subiendo Documento *********************** ");
                int idDocumentoContent = 0;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto(
                        fileContacto.getInputstream(),
                        System.currentTimeMillis() + "",
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase(),
                        fileContacto.getContentType(),
                        nit,
                        userSession.getUsuario().getUsuario(),
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase(),
                        nit,
                        "DOCUMENTO_CLIENTE_GERENCIA_" + nit + "_" + empresa.toUpperCase());

                String nombre = GerenerNombreUnicos.getNombre(contacto.trim());

                if (nombre != null) {

                    HabeasDataContacto habeasDataContacto = new HabeasDataContacto();
                    habeasDataContacto.setIdContent(mapaRespuesta.get("id"));
                    habeasDataContacto.setIdDocName(mapaRespuesta.get("dDocName"));
                    habeasDataContacto.setNit(nit);
                    habeasDataContacto.setIdentificacion(nombre);
                    habeasDataContacto.setOrigen(Constants.ORIGEN_DOC_CLIENTE_CONTACTO_PROSPECTO);

                    iHabeasDataContactoEBSLocal.save(habeasDataContacto);
                }
                if (mapaRespuesta == null) {
                    idDocumentoContent = -1;
                } else {
                    if (!mapaRespuesta.isEmpty()) {
                        idDocumentoContent = Integer.parseInt(mapaRespuesta.get("id"));
                    }
                }
                TipoDocumentos _tipoDoc = new TipoDocumentos();
                _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                _tipoDoc.setTituloDocumento(fileContacto.getFileName());
                tipoDocumentos.add(_tipoDoc);
                fileContacto = null;
            }

            if (fileRepresentanteLegal != null) {

                logger.info(" *********** Subiendo Documento *********************** ");
                int idDocumentoContent = 0;
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                HashMap<String, String> mapaRespuesta = iRidcConnection.checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto(
                        fileRepresentanteLegal.getInputstream(),
                        System.currentTimeMillis() + "",
                        "DOCUMENTO_CLIENTE_REPRESENTANTE_LEGAL_" + nit + "_" + empresa.toUpperCase(),
                        fileRepresentanteLegal.getContentType(),
                        nit,
                        userSession.getUsuario().getUsuario(),
                        "DOCUMENTO_CLIENTE_REPRESENTANTE_LEGAL_" + nit + "_" + empresa.toUpperCase(),
                        nit,
                        "DOCUMENTO_CLIENTE_REPRESENTANTE_LEGAL_" + nit + "_" + empresa.toUpperCase());

                if (mapaRespuesta == null) {
                    idDocumentoContent = -1;
                } else {
                    if (!mapaRespuesta.isEmpty()) {
                        idDocumentoContent = Integer.parseInt(mapaRespuesta.get("id"));
                    }
                }
                TipoDocumentos _tipoDoc = new TipoDocumentos();
                _tipoDoc.setIdContentDocumento(String.valueOf(idDocumentoContent));
                _tipoDoc.setTituloDocumento(fileRepresentanteLegal.getFileName());
                tipoDocumentos.add(_tipoDoc);
                fileRepresentanteLegal = null;
            }

            /**/
            //se convierte el objeto a String
            if (!tipoDocumentos.isEmpty()) {
                resultadoVisitaVO.setDocumentos(tipoDocumentos);
            }
            String strRequest = Utils.marshal(resultadoVisitaVO);

            //se pasan los unicos 2 parámetros
            String[] paramsService = new String[numeroParametrosWS];
            paramsService[0] = strRequest;
            paramsService[1] = WS_PROCESS_ENTITY_RESULTADO_VISITA;
            request.setParams(paramsService);

            userSession.getClientWs().consumeService(request);

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error Creando Prospecto " + ex.getMessage(), ex);
            return false;
        } catch (JAXBException ex) {
            logger.error("Error Creando Prospecto " + ex.getMessage(), ex);
            return false;
        } catch (ServiceException ex) {
            logger.error("Error Creando Prospecto " + ex.getMessage(), ex);
            return false;
        } catch (Exception ex) {
            logger.error("Error Creando Prospecto " + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    /**
     * Evento de Creación llamado desde la Pagina
     */
    public void enviarAction() {
        logger.info("Enviando Solicitud de creacion de prospecto ");
        //VALIDAMOS
        //El NIT del cliente NO Exista
        try {
            if (validarClienteExiste(this.nit)) {
                performErrorMessages("El cliente con NIT " + this.nit + " Ya Existe.");
                return;
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex);
            performErrorMessages("Ha ocurrido un error validando el NIT: " + ex.getMessage());
            return;
        } catch (UtilException ex) {
            logger.error(ex);
            performErrorMessages("Ha ocurrido un error validando el NIT: " + ex.getMessage());
            return;
        }

        boolean validaRepresentanteLegal = (cedulaRepresentantelegal != null && cedulaRepresentantelegal.trim().length() > 0)
                || (nombreRepresentantelegal != null && nombreRepresentantelegal.trim().length() > 0)
                || (apellidoRepresentantelegal != null && apellidoRepresentantelegal.trim().length() > 0);

        boolean validaGerente = (cedulaGerentelegal != null && cedulaGerentelegal.trim().length() > 0)
                || (nombreGerentelegal != null && nombreGerentelegal.trim().length() > 0)
                || (apellidoGerentelegal != null && apellidoGerentelegal.trim().length() > 0);

        boolean validaContacto = (contacto != null && contacto.trim().length() > 0) || (apellidoempresa != null && apellidoempresa.trim().length() > 0);

        if (validaRepresentanteLegal && fileRepresentanteLegal == null) {
            performErrorMessages("DEBE CARGAR EL ARCHIVO HABEAS DATA DEL REPRESENTANTE LEGAL. LOS ARCHIVOS ADJUNTOS DEBE VOLVERLOS A CARGAR");
            return;
        }
        if (validaGerente && fileGerencia == null) {
            performErrorMessages("DEBE CARGAR EL ARCHIVO HABEAS DATA DE GERENCIA. LOS ARCHIVOS ADJUNTOS DEBE VOLVERLOS A CARGAR");
            return;
        }
        /* if (validaContacto && fileContacto == null) {
            performErrorMessages("DEBE CARGAR EL ARCHIVO HABEAS DATA DEL CONTACTO. LOS ARCHIVOS ADJUNTOS DEBE VOLVERLOS A CARGAR");
            return;
        } */
        if (validaContacto && (contacto == null || contacto.trim().length() == 0)) {
            performErrorMessages("AL INGRESAR CONTACTO, DEBE INGRESAR NOMBRES Y APELLIDOS COMPLETOS");
            return;
        }
        if (validaContacto && (apellidoempresa == null || apellidoempresa.trim().length() == 0)) {
            performErrorMessages("AL INGRESAR CONTACTO, DEBE INGRESAR NOMBRES Y APELLIDOS COMPLETOS");
            return;
        }

        if (enviarSolicitud()) {
            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_CLIENTES_CONSULTA_KEY);
            try {
                redirect(navigationFaces.navigation.get(outcome));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }

        } else {
            performErrorMessages("Ha ocurrido un problema la solicitud de cliente prospecto, no ha podido ser enviada ");
        }
        //logger.info("Productos Seleccionados: " + this.selectProductoCotizacion.size());
        /*for (ProductoVO pr : this.selectProductoCotizacion) {
         logger.info("Productos Seleccionados: " + this.selectProductoCotizacion);
         }*/

    }

    public void nitValueChangeListener(ValueChangeEvent event) {
        logger.info("********** Metodo nitValueChangeListener **********");
        UIInput input = (UIInput) event.getComponent();
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        try {
            if (validarClienteExiste(newValue.toString())) {
                performErrorMessages("El cliente con NIT " + newValue.toString() + " Ya Existe.");
                return;
            }
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex);
            performErrorMessages("Ha ocurrido un error validando el NIT: " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error(ex);
            performErrorMessages("Ha ocurrido un error validando el NIT: " + ex.getMessage());
        }
    }

    public boolean validarClienteExiste(String nitValidar) throws IntelcomMiddlewareException, UtilException {

        logger.info("********** Metodo validarClienteExiste **********");
        boolean clienteExiste = false;

        List<ClienteVO> _listaClienteConsultado = consultaExistenciaCliente(nitValidar);
        if (_listaClienteConsultado != null && _listaClienteConsultado.size() > 0) {

            for (ClienteVO unClienteConsultado : _listaClienteConsultado) {
                if (unClienteConsultado.getNitCliente().equals(nitValidar)) {
                    logger.info("El Cliente con NIT " + nitValidar + " idnetificado como: " + unClienteConsultado.getNombreCliente() + " YA EXISTE!!!!");
                    clienteExiste = true;
                }
            }
        }
        return clienteExiste;
    }

    //Consulta detalle de Cliente
    private ClienteVO consultaDetalleCliente(String idCliente) throws IntelcomMiddlewareException, UtilException {
        logger.info("********** Metodo consultaDetalleCliente **********");
        logger.info("Nùmero de paràmetros: " + numeroParametrosWS_DetalleCliente);

        String[] paramsService = new String[numeroParametrosWS_DetalleCliente];
        paramsService[0] = idCliente;

        requestDetalleCliente.setParams(paramsService);

        String responseStr = userSession.getClientWs().consumeService(requestDetalleCliente);
        ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
        if (response != null && response.getClientes() != null) {
            logger.info("responseStr: " + responseStr);
            return response.getClientes().get(0);
        }
        return null;
    }

    //Consultar la existencia del Cliente
    private List<ClienteVO> consultaExistenciaCliente(String idCliente) throws IntelcomMiddlewareException, UtilException {
        logger.info("********** Metodo consultaExisteCliente **********");
        logger.info("Numero de parametros: " + numeroParametrosWS_consultaExistenciaCliente);

        String[] paramsService = new String[numeroParametrosWS_consultaExistenciaCliente];
        paramsService[0] = idCliente;

        requestConsultaExistenciaCliente.setParams(paramsService);

        String responseStr = userSession.getClientWs().consumeService(requestConsultaExistenciaCliente);
        ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
        if (response != null && response.getClientes() != null) {
            logger.info("responseStr: " + responseStr);
            return response.getClientes();
        }
        return null;
    }

    /* public void enviarAction(ActionEvent actionEvent) {
        
     if (enviarSolicitud()) {
     // Se redirecciona a la pagina de consulta
     String outcome = getViewRedirect(PAGE_CLIENTES_CONSULTA_KEY);
            
     try {
     redirect(navigationFaces.navigation.get(outcome));
     } catch (IOException ex) {
     logger.error(ex.getMessage());
     }
            
     } else {
     performErrorMessages("Ha ocurrido un problema la solicitud de creación de prospecto no ha podido ser enviada ");
     }
        
     }*/
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<Tipo> getListapaises() {
        return listaPaises;
    }

    public void setListapaises(List<Tipo> listapaises) {
        this.listaPaises = listapaises;
    }

    public List<Tipo> getListaSectores() {
        return listaSectores;
    }

    public void setListaSectores(List<Tipo> listaSectores) {
        this.listaSectores = listaSectores;

    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public List<Tipo> getListaCiudad() {
        return listaCiudad;
    }

    public void setListaCiudad(List<Tipo> listaCiudad) {
        this.listaCiudad = listaCiudad;
    }

    public List<Tipo> getListaDepto() {
        return listaDepto;
    }

    public void setListaDepto(List<Tipo> listaDepto) {
        this.listaDepto = listaDepto;
    }

    public void cargarDepto(ValueChangeEvent evento) throws ServiceException {
        listaCiudad = null;
        listaDepto = iTipoService.findByTipoNombrePadre(Long.parseLong(evento.getNewValue().toString(), 10));
        esPaisColombia = evento.getNewValue().toString().equals("" + userSession.getTipoPais_Colombia().getIdTipo());
    }

    public void cargarCiudad(ValueChangeEvent evento) throws ServiceException {
        listaCiudad = null;
        listaCiudad = iTipoService.findByTipoNombrePadre(Long.parseLong(evento.getNewValue().toString(), 10));

    }

    public String obtenerValorTipo(String idTipoStr, List<Tipo> listaValores) {
        Long idTipo = Long.parseLong(idTipoStr, 10);

        for (Tipo item : listaValores) {
            if (item.getIdTipo().equals(idTipo)) {
                return item.getTipoValor();
            }
        }
        return null;
    }

    public boolean isEsPaisColombia() {
        return esPaisColombia;
    }

    public void setEsPaisColombia(boolean esPaisColombia) {
        this.esPaisColombia = esPaisColombia;
    }

    public String getNombreRepresentantelegal() {
        return nombreRepresentantelegal;
    }

    public void setNombreRepresentantelegal(String nombreRepresentantelegal) {
        this.nombreRepresentantelegal = nombreRepresentantelegal;
    }

    public String getCedulaRepresentantelegal() {
        return cedulaRepresentantelegal;
    }

    public void setCedulaRepresentantelegal(String cedulaRepresentantelegal) {
        this.cedulaRepresentantelegal = cedulaRepresentantelegal;
    }

    public String getNombreGerentelegal() {
        return nombreGerentelegal;
    }

    public void setNombreGerentelegal(String nombreGerentelegal) {
        this.nombreGerentelegal = nombreGerentelegal;
    }

    public String getCedulaGerentelegal() {
        return cedulaGerentelegal;
    }

    public void setCedulaGerentelegal(String cedulaGerentelegal) {
        this.cedulaGerentelegal = cedulaGerentelegal;
    }

    public List<Tipo> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Tipo> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public String getApellidoempresa() {
        return apellidoempresa;
    }

    public void setApellidoempresa(String apellidoempresa) {
        this.apellidoempresa = apellidoempresa;
    }

    public String getApellidoRepresentantelegal() {
        return apellidoRepresentantelegal;
    }

    public void setApellidoRepresentantelegal(String apellidoRepresentantelegal) {
        this.apellidoRepresentantelegal = apellidoRepresentantelegal;
    }

    public String getApellidoGerentelegal() {
        return apellidoGerentelegal;
    }

    public void setApellidoGerentelegal(String apellidoGerentelegal) {
        this.apellidoGerentelegal = apellidoGerentelegal;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFileRUT() {
        return fileRUT;
    }

    public void setFileRUT(UploadedFile fileRUT) {
        this.fileRUT = fileRUT;
    }

    public UploadedFile getFileCamaraComercio() {
        return fileCamaraComercio;
    }

    public void setFileCamaraComercio(UploadedFile fileCamaraComercio) {
        this.fileCamaraComercio = fileCamaraComercio;
    }

    public UploadedFile getFileRepresentanteLegal() {
        return fileRepresentanteLegal;
    }

    public void setFileRepresentanteLegal(UploadedFile fileRepresentanteLegal) {
        this.fileRepresentanteLegal = fileRepresentanteLegal;
    }

    public UploadedFile getFileGerencia() {
        return fileGerencia;
    }

    public void setFileGerencia(UploadedFile fileGerencia) {
        this.fileGerencia = fileGerencia;
    }

    public UploadedFile getFileContacto() {
        return fileContacto;
    }

    public void setFileContacto(UploadedFile fileContacto) {
        this.fileContacto = fileContacto;
    }

    public boolean isMostrar1() {

        if (cedulaRepresentantelegal != null && cedulaRepresentantelegal.trim().length() > 0) {
            return true;
        }
        if (nombreRepresentantelegal != null && nombreRepresentantelegal.trim().length() > 0) {
            return true;
        }
        if (apellidoRepresentantelegal != null && apellidoRepresentantelegal.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public boolean isMostrar2() {

        if (cedulaGerentelegal != null && cedulaGerentelegal.trim().length() > 0) {
            return true;
        }
        if (nombreGerentelegal != null && nombreGerentelegal.trim().length() > 0) {
            return true;
        }
        if (apellidoGerentelegal != null && apellidoGerentelegal.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public boolean isMostrar3() {

        if (contacto != null && contacto.trim().length() > 0) {
            return true;
        }
        if (apellidoempresa != null && apellidoempresa.trim().length() > 0) {
            return true;
        }
        return false;
    }

}
