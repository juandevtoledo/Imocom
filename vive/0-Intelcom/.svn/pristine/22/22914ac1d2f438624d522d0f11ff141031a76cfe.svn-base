/*
 * Copyright (c) 2014 FONADE. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FONADE.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of FONADE.
 */

package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.Map;
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
public class ServiciosEBSBean extends AbstractService<Long, ServiciosEbs> implements IServiciosEBSLocal {
    
    private static final Logger logger = Logger.getLogger(ServiciosEBSBean.class.getName());

    public ServiciosEBSBean() {
        super(ServiciosEbs.class);
    }    
    

    /**
     * 
     * @param uniqueName
     * @return
     * @throws ServiceException 
     */
    public ServiciosEbs findByUniqueName(String uniqueName) throws ServiceException {
        
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("servicio", uniqueName);
            
            return findEntitySingleResult("ServiciosEbs.findByServicio", params);
        } catch (PersistenceException ex) {
            logger.error("Error tomando el servicio: ".concat(uniqueName).concat(", detalles: ").concat(ex.getLocalizedMessage()));
            throw new ServiceException(ex.getLocalizedMessage(), Level.ERROR);
        }       
    }

}
