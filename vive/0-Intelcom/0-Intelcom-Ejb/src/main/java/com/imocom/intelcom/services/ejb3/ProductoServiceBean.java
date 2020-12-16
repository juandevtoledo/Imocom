/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
@Stateless
public class ProductoServiceBean extends AbstractService<Long, Producto> implements IProductoServiceLocal {

    private static final Logger logger = Logger.getLogger(ProductoServiceBean.class);

    public ProductoServiceBean() {
        super(Producto.class);
    }

    public List<Producto> buscarProductos(String linea, String descripcion, Long tipo, Long idMarca, Long idModelo) throws ServiceException {
        try {
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            String qlString = "SELECT p FROM Producto p WHERE p.idModelo.idMarca.linea = :linea ";
            paramaters.put("linea", linea);

            if (descripcion != null) {
                qlString += " AND UPPER(p.descripcion) LIKE '%" + descripcion.toUpperCase() + "%'";
            }
            if (tipo != null) {
                qlString += " AND p.idTipo.idTipo = :tipo";
                paramaters.put("tipo", tipo);
            }

            if (idMarca != null) {
                qlString += " AND p.idModelo.idMarca.idMarca = :idMarca ";
                paramaters.put("idMarca", idMarca);
            }

            if (idModelo != null) {
                qlString += " AND p.idModelo.idModelo = :idModelo ";
                paramaters.put("idModelo", idModelo);
            }

            em.flush();
            return (List<Producto>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error("Error buscando productos : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

    public Producto crear(Producto producto) throws ServiceException {
        try {
            return save(producto);
        } catch (PersistenceException ex) {
            logger.error("Error creando productos : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

    public Producto editar(Producto prdct) throws ServiceException {
        try {
            return update(prdct);
        } catch (PersistenceException ex) {
            logger.error("Error actualizando productos : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

    public Producto buscarProducto(Long idProducto) throws ServiceException {
        try {
            /*HashMap<String, Object> paramaters = new HashMap<String, Object>();
            String qlString = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto ";
            paramaters.put("idProducto", idProducto);*/
            return findById(idProducto);
        } catch (PersistenceException ex) {
            logger.error("Error buscando  producto por key : " + ex.getMessage(), ex);
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }

    }
    
}
