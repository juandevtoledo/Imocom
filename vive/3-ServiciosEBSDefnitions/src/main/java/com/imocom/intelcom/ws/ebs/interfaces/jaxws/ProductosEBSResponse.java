/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/

package com.imocom.intelcom.ws.ebs.interfaces.jaxws;

import com.imocom.intelcom.ws.ebs.vo.response.ProductosResponseVO;
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
@XmlRootElement(name = "ProductosEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ProductosEBSResponse", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ProductosEBSResponse implements Serializable {
    
    private String _return;
    private ProductosResponseVO productosResponseVO;

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
    public ProductosResponseVO getProductosResponseVO() {
        return productosResponseVO;
    }

    /**
     * 
     * @param productosResponseVO 
     */
    @XmlElement(name = "productosResponseVO")
    public void setProductosResponseVO(ProductosResponseVO productosResponseVO) {
        this.productosResponseVO = productosResponseVO;
    }    
}
