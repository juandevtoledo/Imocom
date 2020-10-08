/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imocom.intelcom.ws.interfaces;

import com.imocom.intelcom.commons.exceptions.CommonException;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author rc
 */
@WebService(targetNamespace = "http://com.imocom.intelcom.ws.interfaces/")
public interface IIntelcomMiddleware {
    
    /**
     * 
     * @param request
     * @return
     * @throws com.imocom.intelcom.ws.exception.IntelcomMiddlewareException 
     */
    @WebMethod(operationName = "doProcess")
    public String doProcess(@WebParam(name = "request") MiddlewareServiceRequestVO  request) throws IntelcomMiddlewareException;
}
