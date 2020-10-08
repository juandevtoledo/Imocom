/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Rol;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 11, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Local
public interface IRolesServiceLocal extends IService<Long, Rol> {

    /**
     * 
     * @param uniqueName
     * @return
     * @throws ServiceException 
     */
    Rol findByUniqueName(String uniqueName) throws ServiceException;
    
    /**
     * 
     * @return
     * @throws ServiceException 
     */
    List listaDeUsuariosRolUno() throws ServiceException;
}
