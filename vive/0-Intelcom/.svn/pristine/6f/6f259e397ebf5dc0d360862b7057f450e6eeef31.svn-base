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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligancia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Generic service implementation.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 * @param <K> Entity ID Datatype
 * @param <E> extend entity from @link{GenericAbstractEntity}
 */
public abstract class AbstractService<K, E extends AbstractEntity> implements IService<K, E> {
    
    private static final Logger logger = Logger.getLogger(AbstractService.class);
    
    @PersistenceContext(unitName = "InteligenciaComercial-PU")
    protected EntityManager em;
    
    protected Class<E> entityClass;
    
    /**
     *
     * @param entityClass
     */
    public AbstractService(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 
     * @param entityClass 
     */
    public void setEntityClass(Class<E> clazz) {
        this.entityClass = clazz;
    }
        
    /**
     *
     * @param entity
     * @throws co.gov.fonade.gelxml.interoperabilidad.persistence.util.PersistenceException
     */
    public E save(E entity) throws PersistenceException {
        em.persist(entity);
        return entity;
    }
    
    /**
     *
     * @param entity
     * @throws co.gov.fonade.gelxml.interoperabilidad.persistence.util.PersistenceException
     */
    public E update(E entity) throws PersistenceException {
        em.merge(entity);
        return entity;
    }
    
    /**
     * @param id
     * @throws co.gov.fonade.gelxml.interoperabilidad.persistence.util.PersistenceException
     */
    public void remove(K id) throws PersistenceException {
        E entity = findById(id);
        em.remove(entity);        
    }
    
    /**
     * +
     * @return
     */
    public List<E> findAll() throws PersistenceException {
        return em.createNamedQuery(entityClass.getSimpleName() + ".findAll").getResultList();
    }
    
    /**
     *
     * @param id
     * @return
     */
    public E findById(K id) throws PersistenceException {
        return em.find(entityClass, id);
    }
    
    
    /**
     *
     * @param namedQuery
     * @param parametersMap
     * @return
     */
    public E findEntitySingleResult(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException {
        
        E genericEntity;
        
        try {
            Query query = em.createNamedQuery(namedQuery, entityClass);
            
            if (!parametersMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            genericEntity = (E) query.getSingleResult();
            
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            genericEntity = null;
            
        } catch (NonUniqueResultException ex) {
            logger.error(ex.getMessage());
            genericEntity = null;
            
        }
        
        return genericEntity;
    }
    
    /**
     * 
     * @param namedQuery
     * @param parametersMap
     * @return
     * @throws PersistenceException 
     */
    public List<? extends AbstractEntity> findResultList(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException {
        
        List<? extends AbstractEntity>  genericEntityList;
        
        try {
            Query query = em.createNamedQuery(namedQuery, entityClass);
            
            if (!parametersMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            genericEntityList = query.getResultList();
            
        } catch (Exception ex) {            
            throw new PersistenceException(ex, Level.WARN);
        }
        return genericEntityList;
    }
    
  /**
   * Método que recibe un query generico y los objtos de lista de parametros
   * @param Query
   * @param parametersMap
   * @return
   * @throws PersistenceException 
   */
    public List<? extends AbstractEntity> findResulQuerytList(String Query, Map<String, Object> parametersMap) throws PersistenceException {
        
        List<? extends AbstractEntity>  genericEntityList;
        
        try {
            Query query = em.createQuery(Query, entityClass);
            
            if (!parametersMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            genericEntityList = query.getResultList();
            
            
        } catch (Exception ex) {            
            throw new PersistenceException(ex, Level.WARN);
        }
        return genericEntityList;
    }
    
    /**
     *
     * @param namedQuery
     * @param parametersMap
     * @return
     */
    public List<?> findResultObjectList(String namedQuery, Map<String, Object> parametersMap) throws PersistenceException {
        
        List<?>  genericEntityList;
        
        try {
            Query query = em.createNamedQuery(namedQuery);
            
            if (!parametersMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            genericEntityList = query.getResultList();
            
        } catch (Exception ex) {            
            throw new PersistenceException(ex, Level.WARN);
        }
        return genericEntityList;
    }
    
    /**
     * Generic execution of an stored procedure
     *
     * @param prcName
     *              Stored procedure name in jpa
     * @param parametersMap
     *              parameters map (parameter-name and parameter value in jpa) that stored procedure needs to execution.
     */
    public void executeStoredProcedure(String prcName, Map<String, Object> parametersMap) throws PersistenceException {
        try {
            Query query = em.createNamedQuery(prcName);
            
            if (parametersMap != null) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            query.executeUpdate();
            
        } catch (Exception ex) {            
            throw new PersistenceException(ex, Level.WARN);
        }
    }
    
    /**
     * Generic execution of an function procedure
     *
     * @param prcName
     *              Stored function name in jpa
     * @param parametersMap
     *              parameters map (parameter-name and parameter value in jpa) that stored function needs to execution.
     */
    public Object executeStoredFunction(String fncName, Map<String, Object> parametersMap) throws PersistenceException {
        try {
            Query query = em.createNamedQuery(fncName);
            
            if (parametersMap != null) {
                for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
            
        } catch (Exception ex) {            
            throw new PersistenceException(ex, Level.WARN);
        }
    }
    
}
