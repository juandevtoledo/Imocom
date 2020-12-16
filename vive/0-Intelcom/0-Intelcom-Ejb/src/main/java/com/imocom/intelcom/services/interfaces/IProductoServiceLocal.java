/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan.toledo
 */
@Local
public interface IProductoServiceLocal {

    public List<Producto> buscarProductos(String linea, String descripcion, Long tipo, Long idMarca, Long Modelo) throws ServiceException;

    public Producto crear(Producto producto) throws ServiceException;
    
    public Producto editar(Producto producto) throws ServiceException;
    
     public Producto buscarProducto(Long idProducto) throws ServiceException;

}
