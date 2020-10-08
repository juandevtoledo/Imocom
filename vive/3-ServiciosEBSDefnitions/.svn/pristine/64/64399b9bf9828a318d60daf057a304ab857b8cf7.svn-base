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
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import com.imocom.intelcom.ws.ebs.vo.response.ProcesosBPMResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <strong>AplicaciÃ³n</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         : Webservice creado para gestionar las oportunidades en la EBS.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface IProcesosBPM {

    /**
     * 
     * @param request
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "procesoResultadoVisita")
    public ProcesosBPMResponseVO procesoResultadoVisita(@WebParam(name = "request") ResultadoVisitaVO request) throws ImocomWebserviceException;
    
      /**
     * 
     * @param request
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "actualizacionCliente")
    public ProcesosBPMResponseVO actualizacionCliente(@WebParam(name = "request") ResultadoVisitaVO request) throws ImocomWebserviceException;
}
