/*
* Copyright (c) 2014 IMOCOM All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/
package com.imocom.intelcom.ws.ebs.interfaces;

import com.imocom.intelcom.ws.ebs.exceptions.ImocomWebserviceException;
import com.imocom.intelcom.ws.ebs.vo.response.CarteraResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Dec 1, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface ICarteraEBS {
    
    /**
     *
     * @param nitCliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "consultaDatosCartera")
    public CarteraResponseVO consultaDatosCartera(@WebParam(name = "nitCliente") String nitCliente, 
            @WebParam(name = "idAsesor") String idAsesor) throws ImocomWebserviceException;
    
    /**
     *
     * @param nitCliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "consultaFacturasCartera")
    public CarteraResponseVO consultaFacturasCartera(@WebParam(name = "nitCliente") String nitCliente, 
            @WebParam(name = "idAsesor") String idAsesor) throws ImocomWebserviceException;
}
