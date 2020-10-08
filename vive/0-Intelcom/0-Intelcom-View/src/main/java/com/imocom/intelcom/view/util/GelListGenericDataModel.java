/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*
*
*/
package com.imocom.intelcom.view.util;

import com.imocom.intelcom.persistence.IDataModel;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Configuration to application security component.
 *
 * @author  cguzman - PointMind S.A.S. - carlos.guzman@pointmind.com
 * @param <T>
 */
public class GelListGenericDataModel<T extends IDataModel> extends ListDataModel<T> implements SelectableDataModel<T> {

    /**
     * 
     * @param data 
     */
    public GelListGenericDataModel(List<T> data) {
        super(data);
    }

    /**
     * 
     * @param t
     * @return 
     */
    @Override
    public Object getRowKey(T t) {
        
        if (t != null)
            return  t.getKeyModel();
        
        return null;       
    }

    /**
     * 
     * @param string
     * @return 
     */
    @Override
    public T getRowData(String string) {

        List<T> objs = (List<T>) getWrappedData();

        for (T idata : objs) {

            if (string.equals(idata.getKeyModel())) {
                return idata;
            }
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int getRowCount(){
        return super.getRowCount();
    }
}
