package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.HabeasDataContacto;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IHabeasDataContactoEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@Stateless
public class HabeasDataContactoServiceBean extends AbstractService<String, HabeasDataContacto> implements IHabeasDataContactoEBSLocal {

    private static final Logger logger = Logger.getLogger(HabeasDataContactoServiceBean.class);

    public HabeasDataContactoServiceBean() {
        super(HabeasDataContacto.class);
    }

    /**
     * 
     * @param identificacion
     * @return
     * @throws ServiceException 
     */
     public List<HabeasDataContacto> findAllByIdentificacion(String identificacion) throws ServiceException {
        
        try {
            
            Map<String, Object> params = new HashMap<String, Object>();     
            params.put("identificacion", identificacion);
            
            return (List<HabeasDataContacto>) findResultObjectList("HabeasDataContacto.findAllByIdentificacion", params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
    }

     /**
      * 
      * @param nit
      * @return
      * @throws ServiceException 
      */
    public List<HabeasDataContacto> findAllByInicioNit(String nit) throws ServiceException {
        
        try {
            
            Map<String, Object> params = new HashMap<String, Object>();     
            String sql = "SELECT p FROM HabeasDataContacto p WHERE p.identificacion LIKE '"+nit+"%'";
            return (List<HabeasDataContacto>) findResulQuerytList( sql , params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
        
    }

    /**
     * 
     * @param identificacion
     * @param nit
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByIdentificacionYNit(String identificacion, String nit) throws ServiceException {
        
        try {
            
            Map<String, Object> params = new HashMap<String, Object>();     
            params.put("identificacion", identificacion);
            params.put("nit", nit);
            
            return (List<HabeasDataContacto>) findResultObjectList("HabeasDataContacto.findAllByIdentificacionYnit", params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
        
    }

    /**
     * 
     * @param nit
     * @param docName
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByNitYdocName(String nit, String docName) throws ServiceException {
        
        try {
            
            Map<String, Object> params = new HashMap<String, Object>();     
            params.put("docName", docName);
            params.put("nit", nit);
            
            return (List<HabeasDataContacto>) findResultObjectList("HabeasDataContacto.findAllByNnitYdocName", params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
        
    }

    /**
     * 
     * @param nit
     * @param docName
     * @param nombreContacto
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByNitYdocNameYnombre(String nit, String docName, String nombreContacto) throws ServiceException {
        
        try {
            
            Map<String, Object> params = new HashMap<String, Object>();     
            params.put("docName", docName);
            params.put("nit", nit);
            params.put("identificacion", nombreContacto);
            
            return (List<HabeasDataContacto>) findResultObjectList("HabeasDataContacto.findAllByNnitYdocNameyNOMBREcONTACTO", params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
        
    }

}
