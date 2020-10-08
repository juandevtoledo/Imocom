/*
 * Copyright (c) 2014 IMOC0M. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOC0M.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOC0M.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.HabeasDataContacto;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOC0M Sistema inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Junio, 2017
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - Rafael G Blanco B
 *
 */
@Local
public interface IHabeasDataContactoEBSLocal extends IService<String, HabeasDataContacto> {
    
    
    /**
     * 
     * @param identificacion
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByIdentificacion(String identificacion) throws ServiceException;
    
    /**
     * 
     * @param nit
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByInicioNit(String nit) throws ServiceException;
    
    /**
     * 
     * @param identificacion
     * @param nit
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByIdentificacionYNit( String identificacion, String nit) throws ServiceException;
    
    /**
     * 
     * @param nit
     * @param docName
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByNitYdocName( String nit, String docName ) throws ServiceException;
    
    /**
     * 
     * @param nit
     * @param docName
     * @param nombreContacto
     * @return
     * @throws ServiceException 
     */
    public List<HabeasDataContacto> findAllByNitYdocNameYnombre( String nit, String docName, String nombreContacto ) throws ServiceException;

}
