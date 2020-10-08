/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Menu;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IMenuServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Stateless;

/**
 * <strong>Aplicación</strong>     : IMOCOM Interoperabilidad GEL-XML.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 9, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Stateless
public class MenuServiceBean extends AbstractService<Long, Menu> implements IMenuServiceLocal {

    public MenuServiceBean() {
        super(Menu.class);
    }

    /**
     * 
     * @return
     * @throws ServiceException 
     */
    public List<Menu> findByAllParentMenu() throws ServiceException {
        return em.createNamedQuery("Menu.findByAllParentMenu", entityClass).getResultList();
    }

    public List<Menu> findByAllParentMenuHijos(Menu menuPadre) throws ServiceException {
        return em.createNamedQuery("Menu.findByMenuPadre", entityClass).setParameter("menuPadre", menuPadre).getResultList();
    }
}