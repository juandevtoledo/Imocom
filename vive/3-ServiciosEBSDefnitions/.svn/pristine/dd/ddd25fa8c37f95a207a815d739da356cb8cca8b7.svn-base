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
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadesBPMResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Carlos Guzman
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface IOportunidadesBPM {
    
     /* Servicio Web que genera un peticion de un proceso de Oportunidades en la BPM
     * @param request objeto compuesto Que envia el Objeto hacia la BPM: el objeto enviado es una OportunidadesVO 
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "procesoOportunidades")
    public OportunidadesBPMResponseVO procesoOportunidades(@WebParam(name = "request")OportunidadBPMVO request) throws ImocomWebserviceException;
    
    /* Servicio Web que genera un peticion de un proceso de Oportunidades en la BPM
     * @param request objeto compuesto Que envia el Objeto hacia la BPM: el objeto enviado es una OportunidadesVO 
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "procesoActualizacionOportunidades")
    public OportunidadesBPMResponseVO procesoActualizacionOportunidades(@WebParam(name = "request")OportunidadBPMVO request) throws ImocomWebserviceException;    
    
    /* Servicio Web que genera un peticion de un proceso de carge Oportunidades en la BPM a travez de una archivo excel
     * @param request objeto compuesto Que envia el Objeto hacia la BPM: el objeto enviado es una OportunidadesVO 
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "cargueOportunidades")
    public OportunidadesBPMResponseVO cargueOportunidades(@WebParam(name = "request")OportunidadBPMVO request) throws ImocomWebserviceException;
    /**
     * Metodo que actualiza el producto de una oportunidad invocando un API de la EBS
     * @param request objeto compuesto Que envia el Objeto hacia la BPM: el objeto enviado es una OportunidadesVO
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "procesoActualizacionOportunidadeProducto")
    public OportunidadesBPMResponseVO procesoActualizacionOportunidadeProducto(@WebParam(name = "request")OportunidadBPMVO request) throws ImocomWebserviceException;
    
}
