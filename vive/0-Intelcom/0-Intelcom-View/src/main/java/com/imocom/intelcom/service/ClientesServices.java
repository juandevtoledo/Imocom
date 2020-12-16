/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.service;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTEASIGNARASESOR;
import static com.imocom.intelcom.util.utility.Constants.WS_CLIENTE_CONSULTA;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.beans.ClientesNombreNitFacesBean;
import com.imocom.intelcom.ws.client.IntelcomWSClient;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.response.ClienteResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.toledo
 */
public class ClientesServices {

    private static final Logger logger = Logger.getLogger(ClientesNombreNitFacesBean.class);
    private MiddlewareServiceRequestVO request;
    private int numeroParametrosWS = 0;
    private MiddlewareServiceRequestVO requestAsignarAsesor;
    private int numeroParametrosAsignarAsesorWS = 0;
    private final IServiciosEBSLocal iServiciosESB;
    private final IntelcomWSClient getClientWs;

    public ClientesServices(IServiciosEBSLocal iServiciosESB, IntelcomWSClient getClientWs) {
        this.iServiciosESB = iServiciosESB;
        this.getClientWs = getClientWs;
        armarRequestWS();
        armarRequestAsignarAsesorWS();
    }

    private void armarRequestWS() {
        try {
            request = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CLIENTE_CONSULTA);
            request.setEndpoint(servicio.getInterfazEndpoint());
            request.setMethod(servicio.getMetodo());
            request.setNamespacePort(servicio.getNamespace());
            request.setServiceName(servicio.getQnameServicio());
            request.setWsdlUrl(servicio.getUrlWsdl());
            request.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            logger.info("Parámetros asignados a numeroParametrosWS:" + servicio.getNumeroParametros());
            numeroParametrosWS = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequestAsignarAsesorWS() {
        try {
            requestAsignarAsesor = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CLIENTEASIGNARASESOR);
            requestAsignarAsesor.setEndpoint(servicio.getInterfazEndpoint());
            requestAsignarAsesor.setMethod(servicio.getMetodo());
            requestAsignarAsesor.setNamespacePort(servicio.getNamespace());
            requestAsignarAsesor.setServiceName(servicio.getQnameServicio());
            requestAsignarAsesor.setWsdlUrl(servicio.getUrlWsdl());
            requestAsignarAsesor.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            logger.info("Parámetros asignados a numeroParametrosWS:" + servicio.getNumeroParametros());
            numeroParametrosAsignarAsesorWS = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public List<ClienteVO> buscarAction(String asesor) {
        String[] paramsService = new String[numeroParametrosWS];
        paramsService[0] = "";
        paramsService[1] = "";
        paramsService[2] = asesor;
        paramsService[3] = "";
        paramsService[4] = "";
        paramsService[5] = "1";
        request.setParams(paramsService);
        List<ClienteVO> listaClientes = new ArrayList<ClienteVO>();
        try {
            String responseStr = getClientWs.consumeService(request);
            System.out.println("Response Clientes: " + responseStr);
            ClienteResponseVO response = (ClienteResponseVO) Utils.unmarshal(ClienteResponseVO.class, responseStr);
            if (response != null && response.getClientes() != null) {
                listaClientes = response.getClientes();
            }
        } catch (IntelcomMiddlewareException ex) {
            java.util.logging.Logger.getLogger(ClientesServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UtilException ex) {
            java.util.logging.Logger.getLogger(ClientesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClientes;
    }

    public void asignar(String asesorOriginal,String idAsesor, String nitCliente) {
        String[] paramsService = new String[numeroParametrosAsignarAsesorWS];
        paramsService[0] = asesorOriginal;
        paramsService[1] = idAsesor;
        paramsService[2] = nitCliente;
        try {
            requestAsignarAsesor.setParams(paramsService);
            String responseStr = getClientWs.consumeService(requestAsignarAsesor);
            System.out.println("Cliente Asignar Asesor response : " + responseStr);
        } catch (IntelcomMiddlewareException ex) {
            java.util.logging.Logger.getLogger(OportunidadService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
