package com.imocom.intelcom.ws.ebs.interfaces.jaxws;



/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 4, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "ClientesEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ClientesEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ClientesEBSResponse implements Serializable {
    
    private ClienteResponseVO clienteResponse;

    /**
     * 
     * @return 
     */
    public ClienteResponseVO getClienteResponse() {
        return clienteResponse;
    }

    /**
     * 
     * @param clienteResponse 
     */
    @XmlElement(name = "return")
    public void setClienteResponse(ClienteResponseVO clienteResponse) {
        this.clienteResponse = clienteResponse;
    } 
}
