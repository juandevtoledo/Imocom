/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Marca;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IMarcaServiceLocal;
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
public class MarcaServiceBean extends AbstractService<Long, Marca> implements IMarcaServiceLocal {

    private static final Logger logger = Logger.getLogger(MarcaServiceBean.class);

    public MarcaServiceBean() {
        super(Marca.class);
    }

    public List<Marca> buscarMarcasPorLinea(String idLinea) throws ServiceException {
      try {
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            paramaters.put("linea", idLinea);
            
            String qlString = "SELECT m FROM Marca m  WHERE m.linea = :linea";
            em.flush();
            return (List<Marca>) findResulQuerytList(qlString,  paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

    public Marca crearMarca(Marca marca) throws ServiceException {
        try {
            return save(marca);
        } catch (PersistenceException ex) {
            logger.error("Error creando marca : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
