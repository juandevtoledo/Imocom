/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.CarteraVO;
import com.imocom.intelcom.ws.ebs.vo.entities.FacturaVO;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "CarteraResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "CarteraResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class CarteraResponseVO implements Serializable {

    
    private List<CarteraVO> carteras;
    private List<FacturaVO> facturas;

    /**
     * 
     * @return 
     */
    public List<CarteraVO> getCarteras() {
        return carteras;
    }

    /**
     * 
     * @param carteras 
     */
    @XmlElement
    public void setCarteras(List<CarteraVO> carteras) {
        this.carteras = carteras;
    }

    /**
     * 
     * @return 
     */
    public List<FacturaVO> getFacturas() {
        return facturas;
    }

    /**
     * 
     * @param facturas 
     */
    @XmlElement
    public void setFacturas(List<FacturaVO> facturas) {
        this.facturas = facturas;
    }

    
}
