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
import com.imocom.intelcom.ws.ebs.vo.entities.CotizacionesVO;
import com.imocom.intelcom.ws.ebs.vo.response.CotizacionesBPMResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <strong>AplicaciÃ³n</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         : Webservice creado para el proceso de cotizaciones contra LA BPM.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */

@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface ICotizacionesBPM {
    /**
     * Servicio Web que genera un peticion de un proceso de cotizacion en la BPM
     * @param request objeto compuesto Que envia el Objeto hacia la BPM: el objeto enviado es una CotizacionesVO 
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "procesoCotizaciones")
    public CotizacionesBPMResponseVO procesoCotizaciones(@WebParam(name = "request") CotizacionesVO request) throws ImocomWebserviceException;
    /**
     * Servicio Web que genera una actualización de cotización.
     * @param request
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "actualizacionCotizaciones")
    public CotizacionesBPMResponseVO actualizacionCotizaciones(@WebParam(name = "request") CotizacionesVO request) throws ImocomWebserviceException;
    
}
