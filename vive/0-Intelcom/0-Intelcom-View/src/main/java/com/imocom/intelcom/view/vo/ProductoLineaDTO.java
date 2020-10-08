/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.ContadorCotLinea;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ProductoLineaDTO {
    
    private ProductoVO productoVO;
    private ContadorCotLinea contadorCotLinea;

    public ProductoLineaDTO() {
    }

    public ProductoLineaDTO(ProductoVO productoVO, ContadorCotLinea contadorCotLinea) {
        this.productoVO = productoVO;
        this.contadorCotLinea = contadorCotLinea;
    }

    public ProductoVO getProductoVO() {
        return productoVO;
    }

    public void setProductoVO(ProductoVO productoVO) {
        this.productoVO = productoVO;
    }

    public ContadorCotLinea getContadorCotLinea() {
        return contadorCotLinea;
    }

    public void setContadorCotLinea(ContadorCotLinea contadorCotLinea) {
        this.contadorCotLinea = contadorCotLinea;
    }    
    
}
