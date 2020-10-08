/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SeleccionObjectoDTO {
    
    private boolean seleccionado;
    private ProductoVO productoVO;

    public SeleccionObjectoDTO() {
    }

    public SeleccionObjectoDTO(boolean seleccionado, ProductoVO productoVO) {
        this.seleccionado = seleccionado;
        this.productoVO = productoVO;
    }
    
    public ProductoVO getProductoVO() {
        return productoVO;
    }

    public void setProductoVO(ProductoVO productoVO) {
        this.productoVO = productoVO;
    }
    
    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
}
