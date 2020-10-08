/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.ws.interfaces.jaxws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 14, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "MiddlewareServiceRequestVO", namespace = "http://com.imocom.intelcom.ws.interfaces/")
@XmlType(name = "MiddlewareServiceRequestVO", namespace = "http://com.imocom.intelcom.ws.interfaces/")
public class MiddlewareServiceRequestVO implements Serializable {
    
    private String wsdlUrl;
    private String namespacePort;
    private String serviceName;
    private String endpoint;
    private String method;
    private String[] params;
    private String interfaceType;
    

    /**
     * 
     * @return 
     */
    public String getWsdlUrl() {
        return wsdlUrl;
    }

    /**
     * 
     * @param wsdlUrl 
     */
    @XmlElement
    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    /**
     * 
     * @return 
     */
    public String getNamespacePort() {
        return namespacePort;
    }

    /**
     * 
     * @param port 
     */
    @XmlElement
    public void setNamespacePort(String namespacePort) {
        this.namespacePort = namespacePort;
    }

    /**
     * 
     * @return 
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 
     * @param serviceName 
     */
    @XmlElement
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 
     * @return 
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 
     * @param endpoint 
     */
    @XmlElement
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 
     * @return 
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     * @param method 
     */
    @XmlElement
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 
     * @return 
     */
    public String[] getParams() {
        return params;
    }

    /**
     * 
     * @param params 
     */
    @XmlElement
    public void setParams(String[] params) {
        this.params = params;
    }

    /**
     * 
     * @return 
     */
    public String getInterfaceType() {
        return interfaceType;
    }

    /**
     * 
     * @param interfaceType 
     */
    @XmlElement
    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }
    
    
}