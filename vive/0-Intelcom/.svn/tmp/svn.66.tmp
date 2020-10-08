/*
 * Copyright (c) 2014 FONADE. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FONADE.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of FONADE.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.AbstractEntity;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import javax.ejb.Local;

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
@Local
public interface IServicioGenericoLocal<K, E extends AbstractEntity> extends IService<Long, AbstractEntity>{
    
    /**
     * 
     * @param id
     * @param entityClass
     * @return
     * @throws ServiceException 
     */
    public E findEntityById(Long id, Class<E> entityClass) throws ServiceException;
    
    /**
     * 
     * @param clazz
     * @param entity
     * @return
     * @throws ServiceException 
     */
    public AbstractEntity saveEntity(Class<AbstractEntity> clazz, AbstractEntity entity ) throws ServiceException;
    
}
