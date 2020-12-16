/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Modelo;
import com.imocom.intelcom.persistence.entities.Moneda;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IMonedaServiceLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@Stateless
public class MonedaServiceBean extends AbstractService<Long, Moneda> implements IMonedaServiceLocal {

    private static final Logger logger = Logger.getLogger(ModeloServiceBean.class);

    public MonedaServiceBean() {
        super(Moneda.class);
    }

    public List<Moneda> buscarMonedas() throws ServiceException {
        try {
            String qlString = "SELECT m FROM Moneda m ";
            return (List<Moneda>) findResulQuerytList(qlString, new HashMap<String, Object>());
        } catch (PersistenceException ex) {
            logger.error("Error consultando moneda , msg : {} "+ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
