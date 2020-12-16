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
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
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
public class ModeloServiceBean extends AbstractService<Long, Modelo> implements ImodeloServiceLocal {

    private static final Logger logger = Logger.getLogger(ModeloServiceBean.class);

    public ModeloServiceBean() {
        super(Modelo.class);
    }

    public List<Modelo> buscarModeloPorMarca(Long idMarca) throws ServiceException {
        try {
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            paramaters.put("idMarca", idMarca);

            String qlString = "SELECT m FROM Modelo m  WHERE m.idMarca.idMarca = :idMarca";
            em.flush();
            return (List<Modelo>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

    public Modelo crearModelo(Modelo modelo) throws ServiceException {
        try {
            return save(modelo);
        } catch (PersistenceException ex) {
            logger.error("Error creando modelo : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
