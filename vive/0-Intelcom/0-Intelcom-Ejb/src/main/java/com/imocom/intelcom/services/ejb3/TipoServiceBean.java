/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@Stateless
public class TipoServiceBean extends AbstractService<Long, Tipo> implements ITipoServiceLocal {

    private static final Logger logger = Logger.getLogger(TipoServiceBean.class);

    public TipoServiceBean() {
        super(Tipo.class);
    }

  
    public Tipo findByTipoId(Long id) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("idTipo", id);
            return findEntitySingleResult("Tipo.findByIdTipo", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+id);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+id);
            return null;
        }
        
    }

    /**
     *
     * @param tipoNombre
     * @return
     * @throws ServiceException
     */
    public List<Tipo> findByTipoNombre(String tipoNombre) throws ServiceException {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoNombre", tipoNombre);

            return (List<Tipo>) findResultList("Tipo.findByTipoNombre", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre ,e);
            return null; 
        }
    }

    public List<Tipo> findByTipoNombrePadre(Long idPadre) throws ServiceException {

        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoPadre", idPadre);

            return (List<Tipo>) findResultList("Tipo.findByTipoPadre", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+idPadre ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+idPadre ,e);
            return null; 
        }

    }

    public Tipo findByEtiqueta(String etiqueta) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoEtiqueta", etiqueta);
            return findEntitySingleResult("Tipo.findByTipoEtiqueta", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+etiqueta ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+etiqueta ,e);
            return null; 
        }
    }

    public Tipo findByTipoNombreEtiqueta(String tipoNombre, String etiqueta) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoNombre", tipoNombre);
            params.put("tipoEtiqueta", etiqueta);
            return findEntitySingleResult("Tipo.findByTipoNombreTipoEtiqueta", params);
       } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        
        
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre +" "+etiqueta ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre+" "+etiqueta ,e);
            return null; 
        }
    }

    public Tipo findByTipoNombreValorTipopadre(String tipoNombre, String tipoValor, Long idPadre) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoNombre", tipoNombre);
            params.put("tipoValor", tipoValor);
            params.put("tipoPadre", idPadre);

            return findEntitySingleResult("Tipo.findByTipoNombreValorTipopadre", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre +" "+tipoValor ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre+" "+tipoValor ,e);
            return null; 
        }
    }

    public Tipo findByTipoNombreValor(String tipoNombre, String tipoValor) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipoNombre", tipoNombre);
            params.put("tipoValor", tipoValor);

            return findEntitySingleResult("Tipo.findByTipoNombreValor", params);
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        } catch (NoResultException e) {
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre +" "+tipoValor ,e);
            return null; 
        } catch(Exception e){
            logger.error("EROR BUSNCANDO TIPO "+tipoNombre+" "+tipoValor ,e);
            return null; 
        }
    }

}
