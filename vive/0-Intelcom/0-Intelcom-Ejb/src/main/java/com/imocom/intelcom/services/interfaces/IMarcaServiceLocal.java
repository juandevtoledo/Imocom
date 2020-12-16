/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Marca;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan.toledo
 */
@Local
public interface IMarcaServiceLocal {

    public List<Marca> buscarMarcasPorLinea(String idLinea) throws ServiceException;

    public Marca crearMarca(Marca marca) throws ServiceException;
}
