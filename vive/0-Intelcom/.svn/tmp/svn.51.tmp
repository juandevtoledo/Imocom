/*
 * Copyright (c) 2014 FONADE. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FONADE.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of FONADE.
 */

package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.persistence.AbstractEntity;
import com.imocom.intelcom.persistence.entities.OportunidadVisita;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.util.ServiceException;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : FONADE Interoperabilidad GEL-XML.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 21, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Stateless
public class ServicioGenericoBean extends AbstractService<Long, AbstractEntity> implements IServicioGenericoLocal<Long, AbstractEntity> {
    
    private static final Logger logger = Logger.getLogger(ServicioGenericoBean.class.getName());

    public ServicioGenericoBean() {
        super(AbstractEntity.class);
    }

    /**
     * 
     * @param <E>
     * @param id
     * @return
     * @throws ServiceException 
     */
    public AbstractEntity findEntityById(Long id, Class<AbstractEntity> clazz) throws ServiceException {        
        try {
            setEntityClass(clazz);
            return findById(id);
        } catch (PersistenceException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new ServiceException(ex, Level.ERROR);
        }
    }
    
    /**
     * 
     * @param clazz
     * @param entity
     * @return
     * @throws ServiceException 
     */
    public AbstractEntity saveEntity(Class<AbstractEntity> clazz, AbstractEntity entity ) throws ServiceException {
        
        try {
            setEntityClass(clazz);
            return save(entity);
        } catch (PersistenceException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new ServiceException(ex, Level.ERROR);
        }
    }
}
