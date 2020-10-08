/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.InvitadoVisita;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rc
 */
@Local
public interface IInvitadoVisitaServiceLocal extends IService<Long, InvitadoVisita> {
    /**
     * Metodo que manejará el cargue de la consulta inicial al cargar la página.
     * @return
     * @throws ServiceException
     */
    List<InvitadoVisita> findInitialResults() throws ServiceException;
    
    /**
     * Metodo que permite consultar un Cliente Temporal por ID
     * @param idInvitadoVisita Id de cliente temporal que se quiere consultar
     * @return
     * @throws ServiceException
     */
    InvitadoVisita findByIdInvitadoVisita(Long idInvitadoVisita) throws ServiceException;
}
