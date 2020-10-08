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
import com.imocom.intelcom.ws.ebs.vo.response.EventoResponseVO;
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
public interface IEventosEBS {
    
    /**
     * 
     * <p>En parametro correoInvitados se deben diligenciar las cuentas de correo <br />
     * separadas por coma (,) <br />
     * 
     * Ex. cuenta1@server.com,cuenta2@server.com,cuenta3@server.com </p>
     * 
     * @param correoOrigen
     * @param titulo
     * @param descripcion
     * @param ubicacion
     * @param correosInvitados
     * @param fechaHoraInicio
     * @param fechaHoraFinalizacion
     * @return
     * @throws ImocomWebserviceException 
     */
    @WebMethod(operationName = "agendarEvento")
    public EventoResponseVO agendarEvento(@WebParam(name = "correoOrigen") String correoOrigen,
            @WebParam(name = "titulo") String titulo,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "ubicacion") String ubicacion,
            @WebParam(name = "correosInvitados") String correosInvitados,
            @WebParam(name = "fechaHoraInicio") String fechaHoraInicio,
            @WebParam(name = "fechaHoraFinalizacion") String fechaHoraFinalizacion) throws ImocomWebserviceException;
    
}
