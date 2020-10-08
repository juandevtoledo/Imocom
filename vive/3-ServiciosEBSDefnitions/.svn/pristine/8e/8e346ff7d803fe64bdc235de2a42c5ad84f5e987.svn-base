/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.ebs.vo.response;

import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>AplicaciÃ³n</strong>     : IMOCOM Sistema Inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong>         :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "ProductosResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ProductosResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ProductosResponseVO {
        
    private List<ProductoVO> productos;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
    
    /**
     * 
     * @return 
     */
    public List<ProductoVO> getProductos() {
        return productos;
    }

    /**
     * 
     * @param productos 
     */
    @XmlElement(name = "productos")
    public void setProductos(List<ProductoVO> productos) {
        this.productos = productos;
    }
    
    
}
