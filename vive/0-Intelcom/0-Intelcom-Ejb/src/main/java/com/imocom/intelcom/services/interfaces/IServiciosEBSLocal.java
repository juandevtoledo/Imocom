/*
 * Copyright (c) 2014 IMOC0M. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOC0M.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOC0M.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOC0M Sistema inteligencia comercial.
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
public interface IServiciosEBSLocal extends IService<Long, ServiciosEbs> {
    
    
    /**
     * 
     * @param uniqueName
     * @return
     * @throws ServiceException 
     */
    public ServiciosEbs findByUniqueName(String uniqueName) throws ServiceException;

}
