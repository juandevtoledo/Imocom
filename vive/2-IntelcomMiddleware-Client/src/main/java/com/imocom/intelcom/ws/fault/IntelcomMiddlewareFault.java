/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imocom.intelcom.ws.fault;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author rc
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntelcomMiddlewareFault", namespace = "http://com.imocom.intelcom.ws.interfaces/", propOrder = {
    "faultCode", "faultInfo"
})
public class IntelcomMiddlewareFault {
    
    private String faultInfo;
    private String faultCode;
    
    /**
     * 
     */
    public IntelcomMiddlewareFault() {
    }
    
    /**
     * 
     * @return 
     */
    public String getFaultInfo() {
        return faultInfo;
    }
    
    /**
     * 
     * @param faultInfo 
     */
    public void setFaultInfo(String faultInfo) {
        this.faultInfo = faultInfo;
    }
    
    /**
     * 
     * @return 
     */
    public String getFaultCode() {
        return faultCode;
    }
    
    /**
     * 
     * @param faultCode 
     */
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
}
