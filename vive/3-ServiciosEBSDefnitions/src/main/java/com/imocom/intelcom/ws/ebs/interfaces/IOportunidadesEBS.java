/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.interfaces;

import com.imocom.intelcom.ws.ebs.exceptions.ImocomWebserviceException;
import com.imocom.intelcom.ws.ebs.vo.response.ActualizarOportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadActualizarVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadMasivoResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadesActualizarAsesorResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * <strong>AplicaciÃ³n</strong> : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong> : Webservice creado para gestionar las oportunidades
 * en la EBS.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface IOportunidadesEBS {

//    /**
//     * 
//     * @param nitCliente
//     * @param nombreCliente
//     * @param idAsesor
//     * @return
//     * @throws ImocomWebserviceException 
//     */
//    @WebMethod(operationName = "oportunidadesCliente")
//    public String oportunidadesCliente(@WebParam(name = "nitCliente") String nitCliente,
//            @WebParam(name = "nombreCliente") String nombreCliente,
//            @WebParam(name = "idAsesor") String idAsesor) throws ImocomWebserviceException;
    /**
     *
     * @param idOportunidad
     * @return
     * @throws com.imocom.intelcom.ws.ebs.exceptions.ImocomWebserviceException
     */
    @WebMethod(operationName = "detalleOportunidad")
    public OportunidadResponseVO detalleOportunidad(@WebParam(name = "idOportunidad") String idOportunidad) throws ImocomWebserviceException;

    /**
     * Metodo que retorna las oportunidades por los siguientes filtros
     *
     * @param idAsesor
     * @param nitCliente
     * @param probabilidadEjecucion
     * @param probabilidadExito
     * @param idEtapaOportunidad
     * @param idEstadoOportunidad
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesClienteFiltros")
    public OportunidadResponseVO oportunidadesClienteFiltros(@WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "nitCliente") String nitCliente,
            @WebParam(name = "probabilidadEjecucion") String probabilidadEjecucion,
            @WebParam(name = "probabilidadExito") String probabilidadExito,
            @WebParam(name = "idEtapaOportunidad") String idEtapaOportunidad,
            @WebParam(name = "idEstadoOportunidad") String idEstadoOportunidad) throws ImocomWebserviceException;

    /**
     * Metodo que retorna las oportunidades que se pueden registrar en un vista.
     *
     * @param idAsesor Identificador del asesor
     * @param nitCliente Nit del cliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesVisitas")
    public OportunidadResponseVO oportunidadesVisitas(@WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "nitCliente") String nitCliente) throws ImocomWebserviceException;

    /**
     * Método que retorna las oportunidades por filtro de nombre y etapa
     *
     * @param idAsesor
     * @param nombre
     * @param etapa
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesNombreEtapa")
    public OportunidadResponseVO oportunidadesNombreEtapa(@WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "etapa") String etapa)
            throws ImocomWebserviceException;

    /**
     * Web Service que retorna una lista de oportunidades, asociadas a una lista
     * de ids separadas por coma
     *
     * @param idOportunidades Lista de id de oportunidades separas por ,
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesPorIds")
    public OportunidadResponseVO oportunidadesPorIds(@WebParam(name = "idOportunidades") String idOportunidades)
            throws ImocomWebserviceException;

    /**
     * Método que retorna las oportunidades por filtro de nombre y etapa
     *
     * @param idAsesor
     * @param nombre
     * @param etapa
     * @param estado
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesNombreEtapaEstado")
    public OportunidadResponseVO oportunidadesNombreEtapaEstado(@WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "etapa") String etapa,
            @WebParam(name = "estado") String estado
    )
            throws ImocomWebserviceException;

    /**
     * Método que retorna las oportunidades por filtro de nombre, etapa, estado
     * y NIT del cliente
     *
     * @param idAsesor
     * @param nombre
     * @param etapa
     * @param estado
     * @param nitCliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "oportunidadesNombreEtapaEstadoCliente")
    public OportunidadResponseVO oportunidadesNombreEtapaEstadoCliente(@WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "etapa") String etapa,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "nitCliente") String nitCliente
    )
            throws ImocomWebserviceException;

    @WebMethod(operationName = "oportunidadesConsultaAsesorMasivo")
    public OportunidadMasivoResponseVO oportunidadesConsultaAsesorMasivo(@WebParam(name = "idAsesor") String idAsesor
    )
            throws ImocomWebserviceException;

    @WebMethod(operationName = "oportunidadesActualizarAsesor")
    public OportunidadesActualizarAsesorResponseVO oportunidadesActualizarAsesor(
            @WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "idOportunidad") String idOportunidad
    )
            throws ImocomWebserviceException;

    @WebMethod(operationName = "oportunidadActualizar")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "oportunidadActualizar", targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", className = "interfaces.ebs.ws.intelcom.imocom.com.OportunidadActualizar")
    @ResponseWrapper(localName = "oportunidadActualizarResponse", targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", className = "interfaces.ebs.ws.intelcom.imocom.com.OportunidadActualizarResponse")
    public OportunidadActualizarVO oportunidadActualizar(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "idOportunidad") String idOportunidad,
            @WebParam(name = "valorOriginal") String valorOriginal,
            @WebParam(name = "fechaCierre") String fechaCierre,
            @WebParam(name = "moneda") String moneda,
            @WebParam(name = "probabilidadEjecucion") String probabilidadEjecucion,
            @WebParam(name = "probabilidadImocom") String probabilidadImocom,
            @WebParam(name = "codProducto") String codProducto
    )
            throws ImocomWebserviceException;
    
     /**
     * 
     * @param fechaCierre
     * @param codProducto
     * @param probabilidadEjecucion
     * @param moneda
     * @param usuario
     * @param valorOriginal
     * @param probabilidadImocom
     * @param idOportunidad
     * @return
     *     returns interfaces.ebs.ws.intelcom.imocom.com.ActualizarOportunidadVO
     * @throws ImocomWebserviceException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "actualizarOportunidad", targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", className = "interfaces.ebs.ws.intelcom.imocom.com.ActualizarOportunidad")
    @ResponseWrapper(localName = "actualizarOportunidadResponse", targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/", className = "interfaces.ebs.ws.intelcom.imocom.com.ActualizarOportunidadResponse")
    public ActualizarOportunidadVO actualizarOportunidad(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "idOportunidad", targetNamespace = "")
        String idOportunidad,
        @WebParam(name = "valorOriginal", targetNamespace = "")
        String valorOriginal,
        @WebParam(name = "fechaCierre", targetNamespace = "")
        String fechaCierre,
        @WebParam(name = "moneda", targetNamespace = "")
        String moneda,
        @WebParam(name = "probabilidadEjecucion", targetNamespace = "")
        String probabilidadEjecucion,
        @WebParam(name = "probabilidadImocom", targetNamespace = "")
        String probabilidadImocom,
        @WebParam(name = "codProducto", targetNamespace = "")
        String codProducto)
        throws ImocomWebserviceException
    ;

}
