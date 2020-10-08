/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Proyecto;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligancia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Abril 18, 2017
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Rafael Blanco (rblanco) - POINTMIND S.A.S. - rafael.blanco@pointmind.com
 *
 */
@Local
public interface IProyectoServiceLocal extends IService<Long, Proyecto> {
    

    /**
     * 
     * @param nitCliente
     * @param nombre
     * @param anio
     * @param proyectoPlan
     * @return
     * @throws ServiceException 
     */
    List<Proyecto> findListByNitCliente( String nitCliente, String nombre, String anio, String proyectoPlan ) throws ServiceException;
    
}
