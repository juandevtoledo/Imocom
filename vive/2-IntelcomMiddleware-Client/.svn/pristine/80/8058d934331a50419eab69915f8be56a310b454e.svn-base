/*
 * Copyright (c) 2014 FONADE. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FONADE.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of FONADE.
 */

package com.imocom.intelcom.ws.interfaces.jaxws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : FONADE Interoperabilidad GEL-XML.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 15, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "MiddlewareServiceResponse", namespace = "http://interfaces.ws.intelcom.imocom.com/")
@XmlType(name = "MiddlewareServiceResponse", namespace = "http://interfaces.ws.intelcom.imocom.com/")
public class MiddlewareServiceResponse implements Serializable {

    private String _return;
    private MiddlewareServiceRequestVO middlewareServiceRequestVO;

    /**
     * 
     * @return 
     */
    public String getReturn() {
        return _return;
    }

    /**
     * 
     * @param _return 
     */
    @XmlElement(name = "return")
    public void setReturn(String _return) {
        this._return = _return;
    }

    /**
     * 
     * @return 
     */
    public MiddlewareServiceRequestVO getMiddlewareServiceRequestVO() {
        return middlewareServiceRequestVO;
    }

    /**
     * 
     * @param middlewareServiceRequestVO 
     */
    @XmlElement(name = "MiddlewareServiceRequestVO")
    public void setMiddlewareServiceRequestVO(MiddlewareServiceRequestVO middlewareServiceRequestVO) {
        this.middlewareServiceRequestVO = middlewareServiceRequestVO;
    }
    
    
    
}
