/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.util;

import com.imocom.intelcom.commons.exceptions.CommonException;
import com.imocom.intelcom.ws.ebs.vo.entities.ResultadoVisitaVO;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 1, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class WebserviceUtil {
   
    private static final Logger logger = Logger.getLogger(WebserviceUtil.class);
    
    /**
     *
     * @param respose
     * @return
     * @throws JAXBException
     */
    public static String marshal(Object response) throws JAXBException {
        
        final JAXBContext jaxbContext = JAXBContext.newInstance(response.getClass());
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        final StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(response, writer);
        
        return writer.toString();
    }   
    
    
        /**
     *
     * @param response
     * @return
     * @throws JAXBException
     */
    public static JAXBElement unmarshal(Class clazz, String response) throws CommonException {
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(response));
            JAXBElement responseOut = jaxbUnmarshaller.unmarshal(source, clazz);            
            return responseOut;
        } catch (JAXBException ex) {
            logger.error("Error: "+ex.getErrorCode()+" "+ex.getLocalizedMessage()+ " "+ex.getCause());
            logger.error("Error durante transformación de respuesta de servicio, detalles: " + ex.getMessage());
            throw new CommonException("Error durante transformación de respuesta de servicio, detalles: " + ex.getMessage(), ex);
        } catch (Exception t) {
            t.printStackTrace();
            throw new CommonException("Error durante transformación de respuesta de servicio, detalles: " + t.getMessage(), t);
        }
    }
}
