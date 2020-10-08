/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */

package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligancia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 28, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Local
public interface IUsuarioServiceLocal extends IService<Long, Usuario> {
    
    
    /**
     * Metodo que consulta un usuario por llave primaria
     * @param idUsuario
     * @return
     * @throws ServiceException 
     */
    Usuario findByIdUsuario(Long idUsuario) throws ServiceException;
    
    
    /**
     * Metodo que consulta un usuario por llave primaria
     * @param usuario
     * @return
     * @throws ServiceException 
     */
    Usuario findByUsuario(String usuario) throws ServiceException;

    /**
     * Metodo que actuliza un usuario
     * @param usuario
     * @throws ServiceException 
     */
    void updateUsuario(Usuario usuario) throws ServiceException;
    
    /**
     * 
     * @param passwd
     * @return
     * @throws ServiceException 
     */
    
    String getEncriptedPasswd(String passwd) throws ServiceException;

    /**
     * Metodo que consulta un usuario por nombre usuario
     * @param nombreUsuario
     * @return
     * @throws ServiceException 
     */
    List<Usuario> findByNombreParcialUsuario(String nombre) throws ServiceException;
    
}
