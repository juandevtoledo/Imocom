/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import javax.ejb.Local;

/**
 *
 * @author Carlos Guzman
 */
@Local
public interface IParametrosServiceLocal extends IService<Long, Parametros>{
     Parametros findByTipoId(Long id) throws ServiceException;
}
