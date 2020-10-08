/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.ContadorCotLinea;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IContadorCotLineaServiceLocal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Carlos Guzman
 */
@Stateless
public class ContadorCotLineaServiceBean extends AbstractService<Long, ContadorCotLinea> implements IContadorCotLineaServiceLocal{

    public ContadorCotLineaServiceBean() {
         super(ContadorCotLinea.class);
    }

    public List<ContadorCotLinea> buscarLienas() {
       return em.createNamedQuery("ContadorCotLinea.findAll",entityClass).getResultList();
    }
    
}
