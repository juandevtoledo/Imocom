/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.interfaces.IOracleConsultas;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class OracleCanalesEbsImpl implements IOracleConsultas{

    /**
     * 
     * @param iServicioGenerico
     * @return
     * @throws Exception 
     */
    public List<Tipo> getCanalesEBS( IServicioGenericoLocal<Long, Parametros> iServicioGenerico ) throws Exception {
        
        List<Tipo> valores = new ArrayList<Tipo>();
        
        String usuario = iServicioGenerico.findEntityById(11L, Parametros.class).getValor();
        String clave = iServicioGenerico.findEntityById(12L, Parametros.class).getValor();
        String url = iServicioGenerico.findEntityById(13L, Parametros.class).getValor();
        String clase = iServicioGenerico.findEntityById(14L, Parametros.class).getValor();
        String consulta = iServicioGenerico.findEntityById(15L, Parametros.class).getValor();
        
        Class.forName(clase);
        Connection connection = DriverManager.getConnection(url,usuario,clave);
        
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery( consulta );
        while (rset.next()){
            Tipo t = new Tipo();
            t.setTipoNombre( rset.getString(1) );
            t.setTipoValor( rset.getString(2) );
            valores.add( t );
        }
        stmt.close();

        connection.close();
        
        return valores;
        
    }
    
}
