/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.response.EventoResponseVO;
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
@XmlRootElement(name = "EventosEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "EventosEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class EventosEBSResponse implements Serializable {
    
    private EventoResponseVO eventoResponse;

    /**
     * 
     * @return 
     */
    public EventoResponseVO getEventoResponse() {
        return eventoResponse;
    }

    /**
     * 
     * @param eventoResponse 
     */
    @XmlElement
    public void setEventoResponse(EventoResponseVO eventoResponse) {
        this.eventoResponse = eventoResponse;
    }
}
