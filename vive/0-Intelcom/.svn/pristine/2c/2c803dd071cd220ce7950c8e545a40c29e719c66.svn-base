/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.CotizacionProducto;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.ICotizacionProductoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author Carlos Guzman
 */
@Stateless
public class CotizacionProductoServiceBean extends AbstractService<Long, CotizacionProducto> implements ICotizacionProductoServiceLocal {

    private static final Logger logger = Logger.getLogger(EventosServiceBean.class);

    public CotizacionProductoServiceBean() {
        super(CotizacionProducto.class);
    }

    public List<CotizacionProducto> buscarCotizacionProductoCliOporVenc(String idCliente, String idOportunidad, String vencimiento, String tipoConsulta) throws ServiceException {
        return em.createNamedQuery("CotizacionProducto.findAll", entityClass).getResultList();
    }

    public List<CotizacionProducto> buscarCotizacionProductoPorCot(Long idCotizacion) throws ServiceException {
        try {
            String qlString = "SELECT c FROM CotizacionProducto c WHERE c.idCotizacion.idCotizacion = :idCotizacion";
            em.flush();
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            paramaters.put("idCotizacion", idCotizacion);
            return (List<CotizacionProducto>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
             logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
