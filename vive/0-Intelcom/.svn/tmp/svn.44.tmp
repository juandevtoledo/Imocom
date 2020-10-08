/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.InvitadoVisita;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IInvitadoVisitaServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 27, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Stateless
public class InvitadoVisitaBean extends AbstractService<Long, InvitadoVisita> implements IInvitadoVisitaServiceLocal {

    private static final Logger logger = Logger.getLogger(InvitadoVisitaBean.class);
    
    public InvitadoVisitaBean(){
        super(InvitadoVisita.class);
    }

    /**
     * Metodo que manejará el cargue de la consulta inicial al cargar la página.
     * @return
     * @throws ServiceException 
     */

    public List<InvitadoVisita> findInitialResults() throws ServiceException {
        //return null;
        return em.createNamedQuery("InvitadoVisita.findAll", entityClass).getResultList();
    }
    
    /**
     * Metodo que permite consultar un Cliente Temporal por ID
     * @param idInvitadoVisita Id de cliente temporal que se quiere consultar
     * @return
     * @throws ServiceException 
     */
    public InvitadoVisita findByIdInvitadoVisita(Long idInvitadoVisita) throws ServiceException {
        try {
            return findById(idInvitadoVisita);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, Level.ERROR);
        }
    }
    
}
