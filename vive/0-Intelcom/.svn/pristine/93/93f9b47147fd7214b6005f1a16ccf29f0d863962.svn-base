/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rc
 */
@Local
public interface ITipoServiceLocal extends IService<Long, Tipo>{
    
    /**
     * 
     * @param tipoNombre
     * @return
     * @throws ServiceException 
     */
    List<Tipo> findByTipoNombre(String tipoNombre) throws ServiceException;
    List<Tipo> findByTipoNombrePadre(Long idPadre) throws ServiceException;
    Tipo findByEtiqueta(String etiqueta) throws ServiceException;
    Tipo findByTipoNombreEtiqueta(String tipoNombre,String etiqueta) throws ServiceException;
    Tipo findByTipoNombreValorTipopadre(String tipoNombre,String tipoValor,Long idPadre) throws ServiceException;
    Tipo findByTipoNombreValor(String tipoNombre,String tipoValor) throws ServiceException;
    Tipo findByTipoId(Long id) throws ServiceException;
}
