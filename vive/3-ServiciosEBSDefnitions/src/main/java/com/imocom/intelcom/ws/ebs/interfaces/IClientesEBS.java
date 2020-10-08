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
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <strong>AplicaciÃ³n</strong>     : IMOCOM Sistema de Inteligencia comercial.
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
public interface IClientesEBS {
    
   /**
    * Consulta los clientes de la EBS por filtros NitCliente, NombreCliente, Asesor, Tipo Cliente , diasSinVista
    * @param nitCliente
    * @param nombreCliente
    * @param idAsesor
    * @param tipoCliente
    * @param diasSinVisita : Los dias no se ha registrado visitas para un cliente
     * @param clienesPropios: Booleando que identifica si el los clientes a buscar son propios
    * @return
    * @throws ImocomWebserviceException 
    */
    @WebMethod(operationName = "consultaClienteNit")
    public ClienteResponseVO consultaClienteNit(@WebParam(name = "nitCliente") String nitCliente,
            @WebParam(name = "nombreCliente") String nombreCliente,
            @WebParam(name = "idAsesor") String idAsesor,
            @WebParam(name = "tipoCliente") String tipoCliente,
            @WebParam(name = "diasSinVisita") String diasSinVisita,
             @WebParam(name = "clienesPropios") String clienesPropios
            )throws ImocomWebserviceException;
    
    /**
     * 
     * @param nitCliente
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "detalleClienteNit")
    public ClienteResponseVO detalleClienteNit(@WebParam(name = "nitCliente") String nitCliente) throws ImocomWebserviceException;
    
    /**
     *
     * @param nitCliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "consultarContactosPorCliente")
    public ClienteResponseVO consultarContactosPorCliente(@WebParam(name = "nitCliente") String nitCliente) throws ImocomWebserviceException;
    
    /**
     * Servicio Web que retorna los posicion geografica (Latitud , Longitud) de los 
     * clientes de un asesor.
     * @param idAsesor Identificador del asesor (Requerido) 
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "geoConsulta")
    public ClienteResponseVO geoConsulta(@WebParam(name = "idAsesor") String idAsesor)throws ImocomWebserviceException;
    
    
    /**
    * Servicio web que verifica la existencia de un cliente en la EBS, buscando por el NIT del cliente
    * Si encuentra coincidencias con el NIT, de forma exacta, retorna la lista de clientes
    * @param nitCliente
     * @return
     * @throws ImocomWebserviceException
     */
    @WebMethod(operationName = "consultaExistenciaCliente")
    public ClienteResponseVO consultaExistenciaCliente(@WebParam(name = "nitCliente") String nitCliente) throws ImocomWebserviceException;
    
    
}
