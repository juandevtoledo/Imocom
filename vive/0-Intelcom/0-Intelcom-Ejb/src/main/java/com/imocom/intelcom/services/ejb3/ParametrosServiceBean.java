/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;


import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IParametrosServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 *
 * @author Carlos Guzman
 */
@Stateless
public class ParametrosServiceBean extends AbstractService<Long, Parametros> implements IParametrosServiceLocal{
    
     private static final Logger logger = Logger.getLogger(ParametrosServiceBean.class);

    public ParametrosServiceBean() {
         super(Parametros.class);
    }
    public Parametros findByTipoId(Long id) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("idParametro", id);
            return findEntitySingleResult("Parametros.findByIdParametro", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
    }
    
}
