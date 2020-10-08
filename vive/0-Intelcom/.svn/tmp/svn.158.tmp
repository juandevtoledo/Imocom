/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.view.util;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 15, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public class WebServiceUtil {
    
    private static final Logger logger = Logger.getLogger(WebServiceUtil.class);
    
    /**
     *
     * @param response
     * @return
     * @throws JAXBException
     */
    public static <E> Object unmarshal(Class<E> clazz, String response) throws ViewException {
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(response));
            JAXBElement<E> responseOut = jaxbUnmarshaller.unmarshal(source, clazz);
            return (E) responseOut.getValue();
        } catch (JAXBException ex) {
            logger.error("Error durante transformación de respuesta de servicio, detalles: " + ex.getMessage());
            throw new ViewException(ex, Level.ERROR);
        }
    }
}
