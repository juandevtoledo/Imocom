/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.Tipo;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public interface IOracleConsultas {
    
    /**
     * 
     * @param iServicioGenerico
     * @return
     * @throws Exception 
     */
    public List<Tipo> getCanalesEBS( IServicioGenericoLocal<Long, Parametros> iServicioGenerico ) throws Exception;
    
}
