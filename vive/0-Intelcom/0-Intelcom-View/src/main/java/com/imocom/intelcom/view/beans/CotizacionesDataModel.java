/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Carlos Guzman
 */
public class CotizacionesDataModel extends ListDataModel<ProductoVO> implements SelectableDataModel<ProductoVO>{

    public CotizacionesDataModel(List<ProductoVO> data) {
        super(data);
    }    
    
    public Object getRowKey(ProductoVO t) {
        return t.getModelo();
    }

    public ProductoVO getRowData(String string) {
        List<ProductoVO>  products=(List<ProductoVO>)getWrappedData();
        for (ProductoVO product : products){
            if(product.getModelo().equals(string))
                return product;
        }
        return null;
    }
    
    public void onRowSelect(SelectEvent event) {
        System.out.println("Car Selected"+ ((ProductoVO) event.getObject()).getCodigo());
        
    }
 
    public void onRowUnselect(UnselectEvent event) {
        System.out.println("Car UnSelected"+ ((ProductoVO) event.getObject()).getCodigo());
    }
    
}
