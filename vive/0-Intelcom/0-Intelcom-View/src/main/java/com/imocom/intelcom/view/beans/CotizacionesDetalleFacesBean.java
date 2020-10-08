/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_PROCESS;
import static com.imocom.intelcom.commons.util.CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE;
import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.CotizacionProducto;
import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.interfaces.ICotizacionProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.COTIZACION_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.PAGE_COTIZACIONES_CONSULTAR_KEY;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_ACTUALIZAR_CREACION;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_DETALLE;
import static com.imocom.intelcom.util.utility.Constants.WS_PROCESS_ENTITY_RESULTADO_COTIZACION;
import static com.imocom.intelcom.util.utility.Constants.WS_PRODUCTO_DETALLE;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.ws.ebs.vo.entities.CotizacionesVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class CotizacionesDetalleFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CotizacionesDetalleFacesBean.class);
    private static final long serialVersionUID = 1L;
    private Cotizacion cotizacionSelect;
    private List<CotizacionProducto> cotizacionProductoSelect;
    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestProducto;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParamatrosWSProducto = 0;
    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestCliente;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParamatrosWSCliente = 0;

    @EJB
    private IServiciosEBSLocal iServiciosESB;
    @EJB
    private ICotizacionProductoServiceLocal iCotizacionProductoEJB;
    /**
     * getDocument fileDowload
     */
    private StreamedContent file;

    private RidcConnectionServiceBean iRidcConnection;

    /**
     * Archivo de Cargue de Documento para subir al Content Server
     */
    private UploadedFile fileAct;
    /**
     * Parametros para invocar MDW para crear Cotizacion
     */
    private MiddlewareServiceRequestVO request;
    /**
     * NÃºmero de paramatos para request de CreaciÃ³n de CotizaciÃ³n
     */
    private int numeroParametrosWS = 0;

    private String tipoMoneda;

    @Override
    protected void build() {
        cotizacionSelect = (Cotizacion) getSessionMap().get(COTIZACION_ID_PARAM);
        logger.info("la  cotizacion seleccionada es --> " + ((cotizacionSelect != null) ? cotizacionSelect.getIdCotizacion() : "No seleccionado"));
        armarRequest();
        // loadProductos(cotizacionSelect.getCotizacionProductoSet());
        loadProductos(cotizacionSelect.getIdCotizacion());
        cotizacionSelect.setNombreCliente(getClienteDetalle(cotizacionSelect.getNit()));
        cotizacionSelect.getIdTipomoneda();
        /**
         * if (cotizacionSelect.getIdarchivocotizacion() != null) {
         *
         * } else { cotizacionSelect.setIdarchivocotizacion(406L);
         * logger.info("Id de Archivo HARD CODE: " + 406);
         *
         * }*
         */
        logger.info("Id de Archivo: " + cotizacionSelect.getIdarchivocotizacion());

    }

    private void armarRequest() {
        try {
            /**
             * Se arma Request para crear cotizaciÃ³n en la EBS a travez del MDW
             */
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioCot = iServiciosESB.findByUniqueName(WS_BPM_ACTUALIZAR_CREACION);
            request.setEndpoint(servicioCot.getInterfazEndpoint());
            request.setMethod(servicioCot.getMetodo());
            request.setNamespacePort(servicioCot.getNamespace());
            request.setServiceName(servicioCot.getQnameServicio());
            request.setWsdlUrl(servicioCot.getUrlWsdl());
            request.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_PROCESS);
            //Cargamos el nÃºmero de parametros
            numeroParametrosWS = servicioCot.getNumeroParametros();

            /**
             * Se arma Request para buscar informacion del producto
             */
            requestProducto = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_PRODUCTO_DETALLE);
            requestProducto.setEndpoint(servicio.getInterfazEndpoint());
            requestProducto.setMethod(servicio.getMetodo());
            requestProducto.setNamespacePort(servicio.getNamespace());
            requestProducto.setServiceName(servicio.getQnameServicio());
            requestProducto.setWsdlUrl(servicio.getUrlWsdl());
            requestProducto.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSProducto = servicio.getNumeroParametros();
            /**
             * Se arma Request para buscar informacion del cliente
             */
            requestCliente = new MiddlewareServiceRequestVO();
            ServiciosEbs servicioCliente = iServiciosESB.findByUniqueName(WS_CLIENTE_DETALLE);
            requestCliente.setEndpoint(servicioCliente.getInterfazEndpoint());
            requestCliente.setMethod(servicioCliente.getMetodo());
            requestCliente.setNamespacePort(servicioCliente.getNamespace());
            requestCliente.setServiceName(servicioCliente.getQnameServicio());
            requestCliente.setWsdlUrl(servicioCliente.getUrlWsdl());
            requestCliente.setInterfaceType(MIDDLEWARE_INTERFACE_TYPE_SERVICE);
            //Cargamos el nÃºmero de parametros
            numeroParamatrosWSCliente = servicioCliente.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private String getClienteDetalle(String cliente) {
        String[] paramsService = new String[numeroParamatrosWSProducto];
        paramsService[0] = cliente;
        requestCliente.setParams(paramsService);
        String responseStr;
        try {
            responseStr = userSession.getClientWs().consumeService(requestCliente);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response.getClientes() != null) {
                return response.getClientes().get(0).getNombreCliente();
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al buscar clientes, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al buscar clientes, " + ex.getMessage());
        }
        return cliente;
    }

    private String getProductoDetalle(String idProducto) {
        String[] paramsService = new String[numeroParamatrosWSProducto];
        paramsService[0] = idProducto;
        requestProducto.setParams(paramsService);
        String responseStr;
        try {
            responseStr = userSession.getClientWs().consumeService(requestProducto);
            ProductosResponseVO response = (ProductosResponseVO) Utils.unmarshal(ProductosResponseVO.class, responseStr);
            if (response.getProductos() != null) {
                return response.getProductos().get(0).getNombre();
            }

        } catch (IntelcomMiddlewareException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        } catch (UtilException ex) {
            logger.error("Error al busar productos de una oportunidad, " + ex.getMessage());
        }
        return idProducto;
    }

    private void loadProductos(Set<CotizacionProducto> cotizacionProductoList) {
        if (cotizacionProductoSelect == null) {
            cotizacionProductoSelect = new ArrayList<CotizacionProducto>();
        } else {
            cotizacionProductoSelect.clear();
        }

        for (CotizacionProducto cp : cotizacionProductoList) {
            cp.setNombreProducto(getProductoDetalle(Long.toString(cp.getIdproducto())));
            cotizacionProductoSelect.add(cp);
        }
    }

    private void loadProductos(Long idCotizacion) {
        try {
            if (cotizacionProductoSelect == null) {
                cotizacionProductoSelect = new ArrayList<CotizacionProducto>();
            } else {
                cotizacionProductoSelect.clear();
            }
            List<CotizacionProducto> cotizacionProductoList = iCotizacionProductoEJB.buscarCotizacionProductoPorCot(idCotizacion);
            for (CotizacionProducto cp : cotizacionProductoList) {
                cp.setNombreProducto(getProductoDetalle(Long.toString(cp.getIdproducto())));
                cotizacionProductoSelect.add(cp);
            }
            //cotizacionProductoSelect = iCotizacionProductoEJB.buscarCotizacionProductoPorCot(idCotizacion);
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }

    }

    public void downloadRenditionRidc() {
        FacesContext fc = FacesContext.getCurrentInstance();
        byte[] bytes;
        HttpServletResponse response
                = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        logger.info("*************** Metodo downloadRenditionRidc");
        try {
            if (this.cotizacionSelect.getIdarchivocotizacion() != null) {
                String id = Long.toString(this.cotizacionSelect.getIdarchivocotizacion());
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
    }

    /**
     * Se carga el Archivo en el Content Servers
     *
     * @param is
     * @param filename
     * @param contentType
     * @return
     */
    public HashMap<String, String> upload(InputStream is, String filename, String contentType) {
        if (is != null) {
            try {
                logger.info(" *********** Subiendo Documento UCM *********************** ");
                String name = "" + System.currentTimeMillis();
                String title = "COTAUT_" + userSession.getUserLogin() + "_" + this.cotizacionSelect.getNombreOportunidad();
                iRidcConnection = new RidcConnectionServiceBean(userSession.getRidcUrl(), userSession.getRidcUser(), userSession.getRidcPassword());
                return iRidcConnection.checkingDocs(is, filename, name, title, contentType,"CotizacionAutomaticaAsesor");
            } catch (ServiceException ex) {
                logger.error("Error Subiendo el documento de la cotizaciÃ³n al UCM bean: " + ex.getMessage());
                return null;
            }
        } else {
            logger.error("No se ha cargado un documento en la cotizaciÃ³n: ");
        }
        return new HashMap<String, String>();
    }

    public void enviarAction() {
        if (enviarSolicitud()) {
            // Se redirecciona a la pagina de consulta
            String outcome = getViewRedirect(PAGE_COTIZACIONES_CONSULTAR_KEY);
            try {
                redirect(navigationFaces.navigation.get(outcome));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        } else {
            performErrorMessages("Ha ocurrido un problema en la actualizacion de cotizacion.. ");
        }

    }

    public Boolean enviarSolicitud() {
        logger.info("Actualizando documento :  " + fileAct);
        if (fileAct != null) {
            try {
                HashMap<String, String> rFile = upload(fileAct.getInputstream(), fileAct.getFileName(), fileAct.getContentType());
                if (rFile != null) {
                    logger.info("Se ha cargado cotizacion al content Server: " + rFile.get("id") + " : " + rFile.get("webLocation"));
                    CotizacionesVO cotizacionVO = new CotizacionesVO();
                    cotizacionVO.setNombreUsuario(userSession.getUserLogin());
                    cotizacionVO.setCodigo(cotizacionSelect.getCodigo());
                    cotizacionVO.setUrlContentArchivoAdjSolicitud(rFile.get("webLocation"));
                    cotizacionVO.setIdArchivoAdjSolicitud(rFile.get("id"));
                    cotizacionVO.setNombreArchivoAdjSolicitud(fileAct.getFileName());
                    //se convierte el objeto a String
                    String strRequest = Utils.marshal(cotizacionVO);
                    logger.info("Request UD Cotizacion : " + strRequest);
                    //se pasan los unicos 2 parÃ¡metros
                    String[] paramsService = new String[numeroParametrosWS];
                    paramsService[0] = strRequest;
                    paramsService[1] = WS_PROCESS_ENTITY_RESULTADO_COTIZACION;
                    request.setParams(paramsService);

                    userSession.getClientWs().consumeService(request);
                    return true;
                } else {
                    logger.error("Error Cargando archivo de cotizacion");
                }
            } catch (IOException ex) {
                logger.error("Error Creando una cotizaciÃ³n: " + ex.getMessage());
            } catch (JAXBException ex) {
                logger.error("Error Creando una cotizaciÃ³n: " + ex.getMessage());
            } catch (IntelcomMiddlewareException ex) {
                logger.error("Error Creando una cotizaciÃ³n: " + ex.getMessage());
            }
        }
        return false;
    }

    public Cotizacion getCotizacionSelect() {
        return cotizacionSelect;
    }

    public void setCotizacionSelect(Cotizacion cotizacionSelect) {
        this.cotizacionSelect = cotizacionSelect;
    }

    public List<CotizacionProducto> getCotizacionProductoSelect() {
        return cotizacionProductoSelect;
    }

    public void setCotizacionProductoSelect(List<CotizacionProducto> cotizacionProductoSelect) {
        this.cotizacionProductoSelect = cotizacionProductoSelect;
    }

    public UploadedFile getFileAct() {
        return fileAct;
    }

    public void setFileAct(UploadedFile fileAct) {
        this.fileAct = fileAct;
    }

    public String getTipoMoneda() {
        if (cotizacionSelect.getIdTipomoneda() != null) {
            return cotizacionSelect.getIdTipomoneda().getTipoValor();
        }
        return tipoMoneda;
    }

}
