/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ridc.RidcConnectionServiceBean;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.beans.CotizacionesDetalleFacesBean;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class DocumentosClienteDTO {
    
    private String nombreCliente;
    private String tipo;
    private String titulo;
    private String nombreDocumento;
    private String urlDocContent;    
    private String idDoc;
    private String idDocName;
     
    public DocumentosClienteDTO() {
    }

    public DocumentosClienteDTO(String nombreCliente, String tipo, String titulo, String nombreDocumento) {
        this.nombreCliente = nombreCliente;
        this.tipo = tipo;
        this.titulo = titulo;
        this.nombreDocumento = nombreDocumento;
    }

    public DocumentosClienteDTO(String nombreCliente, String tipo, String titulo, String nombreDocumento, String urlDocContent) {
        this.nombreCliente = nombreCliente;
        this.tipo = tipo;
        this.titulo = titulo;
        this.nombreDocumento = nombreDocumento;
        this.urlDocContent = urlDocContent;
    }

    public DocumentosClienteDTO(String nombreCliente, String tipo, String titulo, String nombreDocumento, String urlDocContent, String idDoc, String idDocName) {
        this.nombreCliente = nombreCliente;
        this.tipo = tipo;
        this.titulo = titulo;
        this.nombreDocumento = nombreDocumento;
        this.urlDocContent = urlDocContent;
        this.idDoc = idDoc;
        this.idDocName = idDocName;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getUrlDocContent() {
        return urlDocContent;
    }

    public void setUrlDocContent(String urlDocContent) {
        this.urlDocContent = urlDocContent;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getIdDocName() {
        return idDocName;
    }

    public void setIdDocName(String idDocName) {
        this.idDocName = idDocName;
    }
    
    
    
}
