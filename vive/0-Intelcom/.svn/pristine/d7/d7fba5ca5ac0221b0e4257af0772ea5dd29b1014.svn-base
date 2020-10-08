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


package com.imocom.intelcom.services;

import com.imocom.intelcom.persistence.AbstractEntity;
import com.imocom.intelcom.persistence.util.PersistenceException;
import java.util.List;
import java.util.Map;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligancia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Generic service interface.
 * 
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 * @param <K> Entity ID Datatype
 * @param <E> extend entity from @link{GenericAbstractEntity}
 */
public interface IService<K, E extends AbstractEntity> {
    
    /**
     *
     * @param entity
     * @throws PersistenceException
     */
    E save(E entity) throws PersistenceException;
    
    /**
     *
     * @param entity
     * @throws PersistenceException
     */
    E update(E entity) throws PersistenceException;
    
    /**
     *
     * @param id
     * @throws PersistenceException
     */
    void remove(K id) throws PersistenceException;
    
    
    /**
     * +
     * @return
     * @throws co.gov.fonade.gelxml.interoperabilidad.services.util.ServiceException
     */
    List<? extends AbstractEntity> findAll() throws PersistenceException;
    
    /**
     *
     * @param id
     * @return
     * @throws co.gov.fonade.gelxml.interoperabilidad.services.util.ServiceException
     */
    E findById(K id) throws PersistenceException;
    
    /**
     * 
     * @param namedQuery
     * @param parametersMap
     * @return 
     * @throws co.gov.fonade.gelxml.interoperabilidad.services.util.ServiceException 
     */
    E findEntitySingleResult(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException;
    
    /**
     * 
     * @param namedQuery
     * @param parametersMap
     * @return
     * @throws co.gov.fonade.gelxml.interoperabilidad.services.util.ServiceException 
     */
    List<? extends AbstractEntity> findResultList(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException;

    /**
     *
     * @param prcName
     * @param parametersMap
     * @throws co.gov.fonade.gelxml.interoperabilidad.services.util.ServiceException
     */
    void executeStoredProcedure(String prcName, Map<String, Object> parametersMap) throws PersistenceException;

    /**
     *
     * @param namedQuery
     * @param parametersMap
     * @return
     */
    List<?> findResultObjectList(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException;
    
    /**
     * 
     * @param fncName
     * @param parametersMap
     * @return
     * @throws PersistenceException 
     */
    Object executeStoredFunction(String fncName, Map<String, Object> parametersMap) throws PersistenceException;
    
}
