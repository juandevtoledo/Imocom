/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.entities.Rol;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.IRolesServiceLocal;
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
public class ProductoServiceBean extends AbstractService<Long, Producto> implements IProductoServiceLocal {

    private static final Logger logger = Logger.getLogger(ProductoServiceBean.class);

    public ProductoServiceBean() {
        super(Producto.class);
    }

    public List<Producto> buscarProductos(String descripcion) throws ServiceException {
        try {
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            String qlString = "SELECT p FROM Producto p  ";
            em.flush();
            return (List<Producto>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
