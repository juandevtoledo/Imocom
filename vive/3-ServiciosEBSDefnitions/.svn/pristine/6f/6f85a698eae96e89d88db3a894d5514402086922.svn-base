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
import com.imocom.intelcom.ws.ebs.vo.response.NotasResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Carlos Guzman
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public interface INotasWSEBS {
   
    /**
     * Servicio Web que consultas las notas asocidas a una oportunidad de negocio
     * @param idOportunidad identificador de la oportunidad (Requerido)
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "consultarNota")
    public NotasResponseVO consultarNota(@WebParam(name = "idOportunidad") String idOportunidad) throws ImocomWebserviceException;
    
    /**
     * Metodo que consultas las notas de un cliente realizadas por un asesor
     * @param nit identificador del cliente
     * @param asesor user_id del asesor
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "consultarNotaXNit")
    public NotasResponseVO consultarNotaXNit(@WebParam(name = "nit") String nit,@WebParam(name = "asesor") String asesor) throws ImocomWebserviceException;
    
    /**
     * Consulta los asesores que han visitado a un cliente
     * @param nit nit del cliente
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "consultaAsesoresVisita")
    public NotasResponseVO consultaAsesoresVisita(@WebParam(name = "nit") String nit) throws ImocomWebserviceException;
    
}
