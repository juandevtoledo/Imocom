/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.support;

import com.imocom.intelcom.commons.exceptions.CommonException;
import com.imocom.intelcom.commons.util.CommonConstants;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import com.imocom.intelcom.ws.util.WebserviceUtil;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Nov 15, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
public class ServiciosEBSImocomClient {

    private static final Logger logger = Logger.getLogger(ServiciosEBSImocomClient.class);

    /**
     *
     * @param request
     * @return
     * @throws com.imocom.intelcom.commons.exceptions.CommonException
     */
    public Object consumeService(MiddlewareServiceRequestVO request) throws CommonException {

        try {
            logger.info("Preparando consumo de servicio " + request.getServiceName() + " en EBS...");
            URL wsdlUrl = new URL(request.getWsdlUrl());
            logger.info("URL localizada --> " + wsdlUrl.toString());
            QName qname = new QName(request.getNamespacePort(), request.getServiceName());

            logger.info("Localizando el servicio a consumir");
            Service service = Service.create(wsdlUrl, qname);

            logger.info("Tomando puerto del servicio web de EBS");
            Class clazz = Class.forName(request.getEndpoint());
            Object portWs = service.getPort(clazz);

            Class[] paramsClass = null;
            List paramsValues = new ArrayList();

            if (request.getInterfaceType() != null && CommonConstants.MIDDLEWARE_INTERFACE_TYPE_PROCESS.equals(request.getInterfaceType())) {

                String data = request.getParams()[0];
                String type = request.getParams()[1];
                logger.info("PROC: type:" + type + " Data: " + data);
                Class paramClass = Class.forName(type);

                paramsClass = new Class[1];
                paramsClass[0] = paramClass;

                JAXBElement requestObj = WebserviceUtil.unmarshal(paramClass, data);
                if (requestObj != null) {
                    paramsValues.add(requestObj.getValue());
                }

            } else if (request.getInterfaceType() == null || CommonConstants.MIDDLEWARE_INTERFACE_TYPE_SERVICE.equals(request.getInterfaceType())) {
                logger.info("wsdl  Method:" + request.getWsdlUrl());
                logger.info("MDW WSDL:" + request.getWsdlUrl());
                //logger.info("MDW Method:" + method.toString());
                logger.info("MDW Param:" + Arrays.toString(request.getParams()));
                logger.info("MDW Param Length:" + request.getParams().length);
                paramsClass = new Class[request.getParams().length];
                for (int i = 0; i < request.getParams().length; i++) {
                    paramsClass[i] = String.class;
                }
                logger.info("Method:" + request.getMethod() + " Params: " + request.getParams().length);
                paramsValues.addAll(Arrays.asList(request.getParams()));
            }

            Method method = clazz.getDeclaredMethod(request.getMethod(), paramsClass);

            logger.info("MDW Param:"+request.getEndpoint());
            logger.info("Solicitando respuesta del servicio...");
            Object response = method.invoke(portWs, paramsValues.toArray());
            
            logger.info("Object response = " + response);

            return response;

        } catch (MalformedURLException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        }  catch (SecurityException ex) {
            System.out.println("error mdw "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        } catch (Exception ex) {
            System.out.println("error mdw "+ex.getMessage());
            ex.printStackTrace();
            logger.error(ex.getMessage());
            throw new CommonException(ex.getMessage(), ex);
        }
    }
}
