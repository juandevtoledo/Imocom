/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ridc;

import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.vo.DocumentosClienteDTO;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;

/**
 * Clase de conexión con el Content Server UCM.
 *
 * @author Carlos Guzman
 */
public class RidcConnectionServiceBean {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RidcConnectionServiceBean.class);
    private String url = "";
    private String user = "";
    private String password = "";
    private IdcClient idClient;
    private IdcContext userContext;
    private HashMap<String, String> types = new HashMap<String, String>();

    public RidcConnectionServiceBean(String url, String user, String password) throws ServiceException {

        try {
            /**
             * Conexion al content estableciendo el UserContext
             */
            this.url = url;
            this.user = user;
            this.password = password;
            Integer thead = 30;
            Integer timeOut = 130000;
            loadContentType();
            IdcClientManager manager = new IdcClientManager();
            idClient = manager.createClient(this.url);
            userContext = new IdcContext(this.user, this.password);
            idClient.getConfig().setConnectionSize(thead);
            idClient.getConfig().setSocketTimeout(timeOut);
        } catch (IdcClientException ex) {
            logger.error("Error Conectando al Servicio UCM : " + ex.getMessage());
            throw new ServiceException("Error Conectandose al UCM: " + ex.getMessage(), org.apache.log4j.Level.ERROR);
        }
    }

    /**
     * se carga los mimes typs por defecto
     */
    private void loadContentType() {
        types.put(".doc", "application/msword");
        types.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        types.put("xls", "application/vnd.ms-excel");
        types.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        types.put("ppt", "application/vnd.ms-powerpoint");
        types.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        types.put("pdf", "application/pdf");
        types.put("jpe", "image/jpeg");
        types.put("jpg", "image/jpeg");
        types.put("jpeg", "image/jpeg");
        types.put("png", "image/png");

    }
    
     /**
     * Query para consultar archivos de cotizacion
     *
     * @param did
     * @param idCliente
     * @param idAsesor
     * @param idOportunidad
     * @return
     */
    private String builderQuery(String did, String idCliente, String idAsesor, String idOportunidad) {
        String query = "dDocType <matches> `" + "Cotizaciones" + "`";
        query += " <AND>";
        if (!did.equals("")) {
            query += " dID <substring> `" + did + "`";
            query += " <AND>";
        }
        if (!idCliente.equals("")) {
            query += " xID_CLIENTE_COTIZACION <substring> `" + idCliente.toUpperCase() + "`";
            query += " <AND>";
        }
        if (!idAsesor.equals("")) {
            query += " xASESOR <substring> `" + idAsesor + "`";
            query += " <AND>";
        }
        if (!idOportunidad.equals("")) {
            query += " xOPORTUNIDAD <substring> `" + idOportunidad + "`";
            query += " <AND>";
        }
        if (query.endsWith("<AND>")) {
            return query.substring(0, query.lastIndexOf("<AND>"));
        } else {
            return query;
        }
    }

    /**
     * Método que retorna los Bytes de un archivo adjunto
     *
     * @param id Document Id
     * @return
     * @throws IOException
     */
    public byte[] getBytesRendition(String id) throws IOException {
        try {

            DataBinder dataBinderReq = idClient.createBinder();
            System.out.println("Get File Rendition: Id " + id);
            dataBinderReq.putLocal("IdcService", "GET_FILE");
            dataBinderReq.putLocal("dID", id);
            dataBinderReq.putLocal("Rendition", "FileAttach");
            dataBinderReq.putLocal("allowInterrupt", "1");
            dataBinderReq.putLocal("dUser", "JFACOSTAR");
            IdcContext userContextRidc = new IdcContext(user);
            ServiceResponse response
                    = idClient.sendRequest(userContextRidc, dataBinderReq);
            InputStream is = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            is = response.getResponseStream();
            int next = is.read();
            while (next > -1) {
                bos.write(next);
                next = is.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            return result;
        } catch (IdcClientException e) {
            System.out.println("[" + System.currentTimeMillis() + "][ERROR] Error En metodo (getBytesRendition) para poder: (" + id + ")"
                    + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("no se obtubo documento valido...........");
        return null;
    }

    public byte[] getBytesFile(String id) throws IOException {
        try {

            System.out.println("Get File: Id " + id);
            DataBinder binder = idClient.createBinder();
            binder.putLocal("IdcService", "GET_FILE");
            binder.putLocal("dID", id);
            binder.putLocal("SctDateStamp", "20130725133800");
            ServiceResponse response = idClient.sendRequest(userContext, binder);
            InputStream is = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            is = response.getResponseStream();
            int next = is.read();
            while (next > -1) {
                bos.write(next);
                next = is.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            response.close();
            return result;
        } catch (IdcClientException e) {
            System.out.println("[" + System.currentTimeMillis() + "][ERROR] Error En metodo (getBytesFile) para poder: (" + id + ")"
                    + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Metodo para buscar doocumentos en el content Server
     *
     * @param idc
     * @param idCliente id del cliente al cual se le guardo el documento
     * @param idAsesor id del asesor al cual se le guardo el documento
     * @param idOportunidad identificador de la oportunidad
     * @return
     * @throws ServiceException
     */
    public HashMap<String, String> searchDocs(String idc, String idCliente, String idAsesor, String idOportunidad) throws ServiceException {
        try {
            DataBinder binder = idClient.createBinder();
            userContext.clearCookies();
            userContext.clearHeaders();
            userContext.clearParameters();
            String queryText = this.builderQuery(idc, "", "", "");
            System.out.println("Query: " + queryText);
            binder.putLocal("IdcService", "GET_SEARCH_RESULTS");
            binder.putLocal("searchFormType", "standard");
            binder.putLocal("SearchQueryFormat", "UNIVERSAL");
            binder.putLocal("AdvSearch", "True");
            binder.putLocal("SortField", "dInDate");
            binder.putLocal("SortOrder", "Desc");
            binder.putLocal("vcrContentType", "Cotizaciones");
            binder.putLocal("QueryText", queryText);
            ServiceResponse response = idClient.sendRequest(userContext, binder);
            DataBinder binder2 = response.getResponseAsBinder();
            DataResultSet resultSet = binder2.getResultSet("SearchResults");
            HashMap<String, String> values = new HashMap<String, String>();
            for (DataObject dataObject : resultSet.getRows()) {
                String id = dataObject.get("dID");
                String name = dataObject.get("dOriginalName");
                String format = dataObject.get("dFormat");
                values.put("dID", id);
                values.put("dOriginalName", name);
                values.put("dFormat", format);

            }
            return values;
        } catch (IdcClientException ex) {
            Logger.getLogger(RidcConnectionServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String _formatWebLocation(String _url) {
        logger.info("url Path: " + url);
        String urlFormat = _url.substring(_url.indexOf("weblayout/") + 10, _url.length());
        logger.info("url Format: " + urlFormat);
        String ext = urlFormat.substring(urlFormat.lastIndexOf("."), urlFormat.length());
        logger.info("url Ext: " + ext);
        urlFormat = urlFormat.substring(0, urlFormat.lastIndexOf("~")) + ext;
        logger.info("url Return: " + urlFormat);

        return urlFormat;
    }

    /**
     * Obtiene los mimes types a partir de el nombre del archivo
     *
     * @param filename
     * @return
     */
    private String contentType(String filename) {
        int f = filename.lastIndexOf(".") + 1;
        System.out.println("LastIn: " + f);
        String contenType = filename.substring(f, filename.length());
        System.out.println("contentType: " + contenType);
        if (types.containsKey(contenType)) {
            return types.get(contenType);
        }
        return "application/octet-stream";

    }
    
    private void updateDocs(String docId){
        
    }
        
    public List<DocumentosClienteDTO> searchDocsClienteHabeasData(String nitCLiente,String urlContent) throws Exception {
        try {
            
            String queryText = "dDocType <matches> `DocumentosCliente` <AND> xNITCLIENTE <matches> `" + nitCLiente + "`";
            
            queryText += " <AND> xDOCTIPO <matches> `HABEASDATA` <AND> dDocTitle <matches> `DOCUMENTO SOPORTE HABEAS DATA CLIENTE`";
            
            DataBinder binder = idClient.createBinder();
            userContext.clearCookies();
            userContext.clearHeaders();
            userContext.clearParameters();
            
            System.out.println("Query: " + queryText);
            binder.putLocal("IdcService", "GET_SEARCH_RESULTS");
            binder.putLocal("searchFormType", "standard");
            binder.putLocal("SearchQueryFormat", "UNIVERSAL");
            binder.putLocal("AdvSearch", "True");
            binder.putLocal("SortField", "dInDate");
            binder.putLocal("SortOrder", "Desc");            
            binder.putLocal("QueryText", queryText);
            ServiceResponse response = idClient.sendRequest(userContext, binder);
            DataBinder binder2 = response.getResponseAsBinder();
            DataResultSet resultSet = binder2.getResultSet("SearchResults");
            
            List<DocumentosClienteDTO> listaDocumentosClienteDTO = new ArrayList<DocumentosClienteDTO>();
            
            for (DataObject dataObject : resultSet.getRows()) {

                String id = dataObject.get("dID");
                String idDocName = dataObject.get("dDocName");
                String name = dataObject.get("dOriginalName");
                String format = dataObject.get("dFormat");
                String cliente = dataObject.get("xNOMBRECLIENTE");
                String tipo = dataObject.get("xDOCTIPO");
                String tiuloDoc = dataObject.get("dDocTitle");
                String file = dataObject.get("URL");
                String ano = dataObject.get("xANO");
                
                System.out.println("cliente="+cliente);
                System.out.println("tipo="+tipo);
                System.out.println("tiuloDoc="+tiuloDoc);
                System.out.println("file="+file);
                
                //listaDocumentosClienteDTO.add( new DocumentosClienteDTO( cliente , tipo, ano, name, "http://bpmtest.imocom.com.co:16200"+file, id, idDocName));
                listaDocumentosClienteDTO.add( new DocumentosClienteDTO( cliente , tipo, ano, name, urlContent+file, id, idDocName));
            }
            return listaDocumentosClienteDTO;
        } catch (IdcClientException ex) {
            Logger.getLogger(RidcConnectionServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param nitCLiente
     * @param docTipo
     * @param anio
     * @param nombreDocumento
     * @return
     * @throws Exception 
     */
    public List<DocumentosClienteDTO> searchDocsClienteCotizacionAutomatica(String nitCLiente, String docTipo, String anio, String nombreDocumento) throws Exception {
        try {
            
            String queryText = "dDocType <matches> `DocumentosCliente` <AND> xNITCLIENTE <matches> `" + nitCLiente + "`";
            
            if( docTipo != null && docTipo.trim().length() > 0 ){
                queryText += " <AND> xDOCTIPO <matches> `" + docTipo + "`";
            }
            if( anio != null  && anio.trim().length() > 0 ){
                queryText += " <AND> xANO <matches> `" + anio + "`";
            }
            if( nombreDocumento != null  && nombreDocumento.trim().length() > 0 ){
                queryText += " <AND> dDocTitle <matches> `" + nombreDocumento + "`";
            }
                        
            
            DataBinder binder = idClient.createBinder();
            userContext.clearCookies();
            userContext.clearHeaders();
            userContext.clearParameters();
            
            System.out.println("Query: " + queryText);
            binder.putLocal("IdcService", "GET_SEARCH_RESULTS");
            binder.putLocal("searchFormType", "standard");
            binder.putLocal("SearchQueryFormat", "UNIVERSAL");
            binder.putLocal("AdvSearch", "True");
            binder.putLocal("SortField", "dInDate");
            binder.putLocal("SortOrder", "Desc");            
            binder.putLocal("QueryText", queryText);
            ServiceResponse response = idClient.sendRequest(userContext, binder);
            DataBinder binder2 = response.getResponseAsBinder();
            DataResultSet resultSet = binder2.getResultSet("SearchResults");
            
            List<DocumentosClienteDTO> listaDocumentosClienteDTO = new ArrayList<DocumentosClienteDTO>();
            
            for (DataObject dataObject : resultSet.getRows()) {

                String id = dataObject.get("dID");
                String name = dataObject.get("dOriginalName");
                String format = dataObject.get("dFormat");
                String cliente = dataObject.get("xNOMBRECLIENTE");
                String tipo = dataObject.get("xDOCTIPO");
                String tiuloDoc = dataObject.get("dDocTitle");
                String file = dataObject.get("URL");
                String ano = dataObject.get("xANO");
                
                System.out.println("cliente="+cliente);
                System.out.println("tipo="+tipo);
                System.out.println("tiuloDoc="+tiuloDoc);
                System.out.println("file="+file);
                
                listaDocumentosClienteDTO.add( new DocumentosClienteDTO( cliente , tipo, ano, tiuloDoc, "http://bpmtest.imocom.com.co:16200"+file));
            }
            return listaDocumentosClienteDTO;
        } catch (IdcClientException ex) {
            Logger.getLogger(RidcConnectionServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    /**
     * 
     * @param file
     * @param name
     * @param fileName
     * @param contenType
     * @param nitCliente
     * @param quien
     * @param contacto
     * @param cedula
     * @return
     * @throws ServiceException 
     */  
    public HashMap<String, String> checkingDocsSoporteHabeasDataCliente( InputStream file, String name, String fileName, String contenType, String nitCliente, String quien, String contacto, String cedula ) throws ServiceException {
        try {
            
            logger.info("Checking documento soporte habeas data data..");
            
            logger.info("file: " + file);
            logger.info("name: " + name);
            logger.info("fileName: " + fileName);
            logger.info("nitCliente: " + nitCliente);
            logger.info("quien: " + quien);
            
            DataBinder dataBinderReq = idClient.createBinder();
            dataBinderReq.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            dataBinderReq.putLocal("dSecurityGroup", "Public");
            dataBinderReq.putLocal("dDocName", name);        
            dataBinderReq.putLocal("xDOCTIPO", "HABEASDATA");        
            dataBinderReq.putLocal("dDocType", "DocumentosCliente"); 
            dataBinderReq.putLocal("dDocTitle", "DOCUMENTO SOPORTE HABEAS DATA CLIENTE");
            dataBinderReq.putLocal("dDocAuthor", user);
            dataBinderReq.putLocal("xNOMBRECLIENTE",  contacto);
            dataBinderReq.putLocal("xNITCLIENTE",  nitCliente);
            dataBinderReq.putLocal("xASESOR",  quien);
            dataBinderReq.putLocal("xCEDULA",  cedula);
            dataBinderReq.putLocal("dOriginalName", fileName);
            dataBinderReq.putLocal("dFormat", contenType);
            dataBinderReq.putLocal("xCpdIsLocked", "0");    
            TransferFile tf = new TransferFile(file, name, file.available(),contenType);
            dataBinderReq.addFile("primaryFile", tf);
            ServiceResponse severiceResponse = idClient.sendRequest(userContext, dataBinderReq);
            //DataBinder dataBinderResp = severiceResponse.getResponseAsBinder();
            //DataResultSet resultSet = dataBinderResp.getResultSet("SearchResults");
            String id = severiceResponse.getResponseAsBinder().getLocal("dID");
            String webfilePath = severiceResponse.getResponseAsBinder().getLocal("WebfilePath");
            logger.info("Add File Sucesufull User : " + user + " Buscan Id del Documento " + id);
            severiceResponse.close();
            HashMap<String, String> _values = new HashMap<String, String>();
            _values.put("id", id);
            _values.put("dDocName", severiceResponse.getResponseAsBinder().getLocal("dDocName"));
            //_values.put("webLocation", this._formatWebLocation(webfilePath));
            return _values;
            
        } catch (FileNotFoundException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IOException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IdcClientException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                logger.error("Error checkin UCM ", ex);
                throw (new ServiceException("Error Subiendo Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
            }
        }
    }

    
    
    public HashMap<String, String> checkingDocsSoporteHabeasDataClienteRutCamaraRepresentanteGerenteContacto( InputStream file, String name, String fileName, String contenType, String nitCliente, String quien, String contacto, String cedula, String adicionalArchivo ) throws ServiceException {
        try {
            
            logger.info("Checking documento soporte habeas data data..");
            
            logger.info("file: " + file);
            logger.info("name: " + name);
            logger.info("fileName: " + fileName);
            logger.info("nitCliente: " + nitCliente);
            logger.info("quien: " + quien);
            
            DataBinder dataBinderReq = idClient.createBinder();
            dataBinderReq.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            dataBinderReq.putLocal("dSecurityGroup", "Public");
            dataBinderReq.putLocal("dDocName", name);        
            dataBinderReq.putLocal("xDOCTIPO", "HABEASDATA");        
            dataBinderReq.putLocal("dDocType", "DocumentosCliente"); 
            dataBinderReq.putLocal("dDocTitle", adicionalArchivo);
            dataBinderReq.putLocal("dDocAuthor", user);
            dataBinderReq.putLocal("xNOMBRECLIENTE",  contacto);
            dataBinderReq.putLocal("xNITCLIENTE",  nitCliente);
            dataBinderReq.putLocal("xASESOR",  quien);
            dataBinderReq.putLocal("xCEDULA",  cedula);
            dataBinderReq.putLocal("dOriginalName", fileName);
            dataBinderReq.putLocal("dFormat", contenType);
            dataBinderReq.putLocal("xCpdIsLocked", "0");    
            TransferFile tf = new TransferFile(file, name, file.available(),contenType);
            dataBinderReq.addFile("primaryFile", tf);
            ServiceResponse severiceResponse = idClient.sendRequest(userContext, dataBinderReq);
            //DataBinder dataBinderResp = severiceResponse.getResponseAsBinder();
            //DataResultSet resultSet = dataBinderResp.getResultSet("SearchResults");
            String id = severiceResponse.getResponseAsBinder().getLocal("dID");
            String webfilePath = severiceResponse.getResponseAsBinder().getLocal("WebfilePath");
            logger.info("Add File Sucesufull User : " + user + " Buscan Id del Documento " + id);
            severiceResponse.close();
            HashMap<String, String> _values = new HashMap<String, String>();
            _values.put("id", id);
            _values.put("dDocName", severiceResponse.getResponseAsBinder().getLocal("dDocName"));
            //_values.put("webLocation", this._formatWebLocation(webfilePath));
            return _values;
            
        } catch (FileNotFoundException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IOException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IdcClientException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                logger.error("Error checkin UCM ", ex);
                throw (new ServiceException("Error Subiendo Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
            }
        }
    }
    
    
    /**
     * 
     * @param file
     * @param name
     * @param fileName
     * @param contenType
     * @param titulo
     * @param comentarios
     * @param nitCliente
     * @param anio
     * @param docTipo
     * @param nombreCLiente
     * @param quien
     * @return
     * @throws ServiceException 
     */
    public HashMap<String, String> checkingDocsCotizacionAutomatica( InputStream file, String name, String fileName, String contenType, String titulo, String comentarios, String nitCliente, String anio, String docTipo, String nombreCLiente, String quien ) throws ServiceException {
        try {
            
            logger.info("Checking documento cotizacion automatica..");
            
            logger.info("file: " + file);
            logger.info("name: " + name);
            logger.info("fileName: " + fileName);
            logger.info("titulo: " + titulo);
            logger.info("comentarios: " + comentarios);
            logger.info("nitCliente: " + nitCliente);
            logger.info("anio: " + anio);
            logger.info("docTipo: " + docTipo);
            logger.info("nombreCLiente: " + nombreCLiente);
            logger.info("quien: " + quien);
            
            
            DataBinder dataBinderReq = idClient.createBinder();
            dataBinderReq.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            dataBinderReq.putLocal("dSecurityGroup", "Public");
            dataBinderReq.putLocal("dDocName", name);            
            dataBinderReq.putLocal("dDocType", "DocumentosCliente"); 
            dataBinderReq.putLocal("dDocTitle", titulo);
            dataBinderReq.putLocal("dDocAuthor", user);
            dataBinderReq.putLocal("xComments",  comentarios);
            dataBinderReq.putLocal("xNITCLIENTE",  nitCliente);
            dataBinderReq.putLocal("xANO",  anio);
            dataBinderReq.putLocal("xASESOR",  quien);
            dataBinderReq.putLocal("xDOCTIPO",  docTipo);
            dataBinderReq.putLocal("xNOMBRECLIENTE",  nombreCLiente);                    
            dataBinderReq.putLocal("dOriginalName", fileName);
            dataBinderReq.putLocal("dFormat", contenType);
            dataBinderReq.putLocal("xCpdIsLocked", "0");    
            TransferFile tf = new TransferFile(file, name, file.available(),contenType);
            dataBinderReq.addFile("primaryFile", tf);
            ServiceResponse severiceResponse = idClient.sendRequest(userContext, dataBinderReq);
            //DataBinder dataBinderResp = severiceResponse.getResponseAsBinder();
            //DataResultSet resultSet = dataBinderResp.getResultSet("SearchResults");
            String id = severiceResponse.getResponseAsBinder().getLocal("dID");
            String webfilePath = severiceResponse.getResponseAsBinder().getLocal("WebfilePath");
            logger.info("Add File Sucesufull User : " + user + " Buscan Id del Documento " + id);
            severiceResponse.close();
            HashMap<String, String> _values = new HashMap<String, String>();
            _values.put("id", id);
            _values.put("dDocName", severiceResponse.getResponseAsBinder().getLocal("dDocName"));
            //_values.put("webLocation", this._formatWebLocation(webfilePath));
            return _values;
            
        } catch (FileNotFoundException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IOException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IdcClientException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                logger.error("Error checkin UCM ", ex);
                throw (new ServiceException("Error Subiendo Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
            }

        }

    }
    
    
    public HashMap<String, String> checkingDocsOportunidad(InputStream file, String name, String fileName, String title, String contenType,String idOportunidad,String nitCliente,String asesor,String tipoProceso) throws ServiceException {
        InputStream attachment = null;
        try {
            contenType = contentType(name);
            logger.info("Checking Docs :  +" + name + " Content Type: " + contenType);
            DataBinder dataBinderReq = idClient.createBinder();
            dataBinderReq.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            dataBinderReq.putLocal("dSecurityGroup", "Public");
            dataBinderReq.putLocal("dDocName", fileName);
            dataBinderReq.putLocal ("dDocAccount", "");
            dataBinderReq.putLocal("dDocType", "Cotizaciones");
            dataBinderReq.putLocal("dDocTitle", title);
            dataBinderReq.putLocal("dDocTitle", title);
            dataBinderReq.putLocal("dDocAuthor", user);
            dataBinderReq.putLocal("xCpdIsLocked", "0");
            dataBinderReq.putLocal("xOPORTUNIDAD", idOportunidad);
            dataBinderReq.putLocal("xNITCLIENTE", nitCliente);
            dataBinderReq.putLocal("xASESOR", asesor);
            dataBinderReq.putLocal("xTIPODOCUMENTOPROCESO",tipoProceso);
            dataBinderReq.putLocal("dOriginalName", name);
            dataBinderReq.putLocal("dFormat", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            attachment = file;
            TransferFile tf
                    = new TransferFile(attachment, name, attachment.available(),
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            dataBinderReq.addFile("primaryFile", tf);
            ServiceResponse severiceResponse = idClient.sendRequest(userContext, dataBinderReq);
            DataBinder dataBinderResp = severiceResponse.getResponseAsBinder();
            DataResultSet resultSet = dataBinderResp.getResultSet("SearchResults");
            String id = severiceResponse.getResponseAsBinder().getLocal("dID");
            String webfilePath = severiceResponse.getResponseAsBinder().getLocal("WebfilePath");
            logger.info("Add File Sucesufull User : " + user + " Buscan Id del Documento " + id);
            severiceResponse.close();
            HashMap<String, String> _values = new HashMap<String, String>();
            _values.put("id", id);
            _values.put("webLocation", this._formatWebLocation(webfilePath));
            return _values;
        } catch (FileNotFoundException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IOException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IdcClientException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } finally {
            try {
                attachment.close();

            } catch (IOException ex) {
                logger.error("Error checkin UCM ", ex);
                throw (new ServiceException("Error Subiendo Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
            }

        }

    }
    /**
     * Metodo para hacer un cheking en el content server de una cotizaciòn
     *
     * @param file Archivo a guardar
     * @param name Nombre como se gurdad archivo en el content server (Unico)
     * @param fileName Nombre de archivo
     * @param title
     * @param contenType Tipo de archivo
     * @param codigoCotizacion
     * @return Retorna numero de con que se guarda el documento
     * @throws ServiceException
     */
    public HashMap<String, String> checkingDocs(InputStream file, String name, String fileName, String title, String contenType,String codigoCotizacion) throws ServiceException {
        InputStream attachment = null;
        try {
            contenType = contentType(name);
            logger.info("Checking Docs :  +" + name + " Content Type: " + contenType);
            DataBinder dataBinderReq = idClient.createBinder();
            dataBinderReq.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            dataBinderReq.putLocal("dSecurityGroup", "Public");
            dataBinderReq.putLocal("dDocName", fileName);
            dataBinderReq.putLocal ("dDocAccount", "");
            dataBinderReq.putLocal("dDocType", "Cotizaciones");
            dataBinderReq.putLocal("dDocTitle", title);
            dataBinderReq.putLocal("dDocTitle", title);
            dataBinderReq.putLocal("dDocAuthor", user);
            dataBinderReq.putLocal("xCpdIsLocked", "0");
            //dataBinderReq.putLocal("xOPORTUNIDAD", idOportunidad);
            //dataBinderReq.putLocal("xID_CLIENTE_COTIZACION", codigoCotizacion);
            dataBinderReq.putLocal("xTIPODOCUMENTOPROCESO", codigoCotizacion);
            dataBinderReq.putLocal("dOriginalName", name);
            dataBinderReq.putLocal("dFormat", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            attachment = file;
            TransferFile tf
                    = new TransferFile(attachment, name, attachment.available(),
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            dataBinderReq.addFile("primaryFile", tf);
            ServiceResponse severiceResponse
                    = idClient.sendRequest(userContext, dataBinderReq);
            DataBinder dataBinderResp = severiceResponse.getResponseAsBinder();
            DataResultSet resultSet = dataBinderResp.getResultSet("SearchResults");
            String id = severiceResponse.getResponseAsBinder().getLocal("dID");
            String webfilePath = severiceResponse.getResponseAsBinder().getLocal("WebfilePath");
            logger.info("Add File Sucesufull User : " + user + " Buscan Id del Documento " + id);
            severiceResponse.close();
            HashMap<String, String> _values = new HashMap<String, String>();
            _values.put("id", id);
            _values.put("webLocation", this._formatWebLocation(webfilePath));
            return _values;
        } catch (FileNotFoundException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IOException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } catch (IdcClientException ex) {
            logger.error("Error checkin UCM ", ex);
            throw (new ServiceException("Error Subiendo UCM Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
        } finally {
            try {
                attachment.close();

            } catch (IOException ex) {
                logger.error("Error checkin UCM ", ex);
                throw (new ServiceException("Error Subiendo Ridc: " + ex.getMessage(), org.apache.log4j.Level.ERROR));
            }

        }

    }

}
