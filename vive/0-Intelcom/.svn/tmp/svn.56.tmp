/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.CotizacionProducto;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Guzman
 */
@Local
public interface ICotizacionProductoServiceLocal extends IService<Long, CotizacionProducto>{
    /**
     * Metodo que retorna los productos asociados a una cotización filtrando por los campos del:
     * Cliente, Oportunidad, Vencimiento, TipoConsulta
     * @param idCliente
     * @param idOportunidad
     * @param vencimiento
     * @param tipoConsulta
     * @return
     * @throws ServiceException 
     */
    public List<CotizacionProducto> buscarCotizacionProductoCliOporVenc(String idCliente,String idOportunidad,String vencimiento, String tipoConsulta)throws ServiceException;
    /**
     * Retorna el producto de una cotizacion
     * @param idCotizacion
     * @return
     * @throws ServiceException 
     */
    public List<CotizacionProducto> buscarCotizacionProductoPorCot(Long idCotizacion) throws ServiceException;
}
