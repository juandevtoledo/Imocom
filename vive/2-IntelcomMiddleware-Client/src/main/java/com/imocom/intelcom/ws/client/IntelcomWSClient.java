/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*
*/
package com.imocom.intelcom.ws.client;

import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.fault.IntelcomMiddlewareFault;
import com.imocom.intelcom.ws.interfaces.IIntelcomMiddleware;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
public class IntelcomWSClient {
    
    private static final Logger logger = Logger.getLogger(IntelcomWSClient.class);
    private final String wsdlUrl;
    private final String namespace;
    private final String serviceName;
    
    /**
     * 
     * @param wsdlUrl
     * @param namespace
     * @param serviceName 
     */
    public IntelcomWSClient(String wsdlUrl, String namespace, String serviceName) {
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.serviceName = serviceName;
    }
    
    
    
    /**
     * 
     * @param request
     * @return
     * @throws IntelcomMiddlewareException 
     */
    public String consumeService(MiddlewareServiceRequestVO request) throws IntelcomMiddlewareException {
        
        try {
            logger.info("Preparando consumo de servicio de interoperabilidad en la aplicacón de interoperabilidad");
            URL urlWSDL = new URL(wsdlUrl);
            logger.info("URL localizada --> " + urlWSDL.toString());
            QName qname = new QName(namespace, serviceName);
            logger.info("Solicitando respuesta del servicio...[Params ]: "+request.getParams().length+" : "+request.getMethod()+" : "+request.getWsdlUrl()+" Array: "+Arrays.toString(request.getParams()));
            logger.info("Localizando el servicio a consumir");
            Service service = Service.create(urlWSDL, qname);
            
            logger.info("Tomando puerto del servicio web de interoperabilidad");
            IIntelcomMiddleware portWs = service.getPort(IIntelcomMiddleware.class);
            
            logger.info("Solicitando respuesta del servicio...[Params ]: "+request.getParams().length+" : "+request.getMethod()+" : "+request.getWsdlUrl()+" Array: "+Arrays.toString(request.getParams()));
            String response = portWs.doProcess(request);
            
            //logger.info("Respuesta del Cliente de Web Service: " + response);
            
            return response;
            
        } catch (MalformedURLException ex) {
            logger.error(ex);
            
            IntelcomMiddlewareFault fault = new IntelcomMiddlewareFault();
            fault.setFaultCode("MID-001");
            fault.setFaultInfo("detalles: ".concat(ex.getMessage()));
            
            throw new IntelcomMiddlewareException(ex.getLocalizedMessage(), fault); 
            
        }catch (IntelcomMiddlewareException ex) {
            logger.error(ex);
            
            IntelcomMiddlewareFault fault = new IntelcomMiddlewareFault();
            fault.setFaultCode("MID-002");
            fault.setFaultInfo("detalles: ".concat(ex.getMessage()));
            
            throw new IntelcomMiddlewareException(ex.getLocalizedMessage(), fault); 
        }
    }
}
