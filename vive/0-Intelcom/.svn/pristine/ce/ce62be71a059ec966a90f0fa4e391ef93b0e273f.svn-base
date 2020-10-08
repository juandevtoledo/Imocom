/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Menu;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligancia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 9, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Local
public interface IMenuServiceLocal extends IService<Long, Menu> {

    /**
     * lista de menus
     * @return
     * @throws ServiceException 
     */
    List<Menu> findByAllParentMenu() throws ServiceException;
    /**
     * Lista de hijos de un menu padre
     * @param menuPadre id del menu padre
     * @return
     * @throws ServiceException 
     */
    List<Menu> findByAllParentMenuHijos( Menu menuPadre) throws ServiceException;
}
