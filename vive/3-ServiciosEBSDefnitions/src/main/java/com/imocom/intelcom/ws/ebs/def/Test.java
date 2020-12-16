/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.def;

import com.imocom.intelcom.ws.ebs.exceptions.ImocomWebserviceException;
import com.imocom.intelcom.ws.ebs.interfaces.IOportunidadesEBS;
import com.imocom.intelcom.ws.ebs.vo.response.ActualizarOportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadActualizarVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadMasivoResponseVO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author juan.toledo
 */
public class Test {

    public static void main(String[] args) throws MalformedURLException, ImocomWebserviceException, IOException {
      //  consultaMasiva();
        actualizar();
    }
    
     private static void actulixarAsesor() throws MalformedURLException, ImocomWebserviceException {
         
     }
    private static void actualizar() throws MalformedURLException, ImocomWebserviceException, IOException {
        Service service = getService();
        IOportunidadesEBS portWs = service.getPort(IOportunidadesEBS.class);
        ActualizarOportunidadVO response = portWs.actualizarOportunidad(
                "LAMAYA",
                "149096",
                null,
                "2020-12-24T12:00:00",
                "EUR",
                "Alta",
                "Alta",
                "2148");
        System.out.println(" ---  " + response.getResultadoOperacion());
       // System.out.println(" " + response.getOportunidades().size());
    }

    private static void consultaMasiva() throws MalformedURLException, ImocomWebserviceException, IOException {
        Service service = getService();
        IOportunidadesEBS portWs = service.getPort(IOportunidadesEBS.class);
        OportunidadMasivoResponseVO response = portWs.oportunidadesConsultaAsesorMasivo("FVARGAS");
        System.out.println(" " + response.getResultadoOperacion());
        System.out.println(" " + response.getOportunidades().size());
    }

    private static Service getService() throws MalformedURLException, IOException {
        URL urlWSDL = new URL("http://bpmtest.imocom.com.co:8001/soa-infra/services/default/Oportunidades/ConsultaOportunidadesEBS?WSDL");
        URLConnection con = urlWSDL.openConnection();
        con.setUseCaches(false);
        
        QName qname = new QName("http://com.imocom.intelcom.ws.ebs.interfaces/", "ConsultaOportunidadesEBS");
        Service service = Service.create(con.getURL(), qname);
        return service;
    }
}
