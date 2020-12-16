
/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.TipoProducto;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.ITipoProductoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@Stateless
public class TipoProductoServiceBean extends AbstractService<Long, TipoProducto> implements ITipoProductoServiceLocal {

    private static final Logger logger = Logger.getLogger(ProductoServiceBean.class);

    public TipoProductoServiceBean() {
        super(TipoProducto.class);
    }

    public List<TipoProducto> buscarTipoProducto() throws ServiceException {
        try {
            String qlString = "SELECT t FROM TipoProducto t ORDER BY t.idTipo ASC ";
            return (List<TipoProducto>) findResulQuerytList(qlString, new HashMap<String, Object>());
        } catch (PersistenceException ex) {
            logger.error("Error consultando tipo producto " + ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
