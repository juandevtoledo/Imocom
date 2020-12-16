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
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_ACTUALIZACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.WS_BPM_OPORTUNIDAD_ACTUALIZAR;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDADACTUALIZAR;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDADESASIGNARASESOR;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDADESCONSULTAASESORMASIVO;
import static com.imocom.intelcom.util.utility.Constants.WS_OPORTUNIDAD_CONSULTA_x_FILTROS;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.beans.OportunidaClienteManagementFacesBean;
import com.imocom.intelcom.view.util.DateOracleFormate;
import com.imocom.intelcom.view.vo.Oportunidad;
import com.imocom.intelcom.ws.client.IntelcomWSClient;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadMasivoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadMasivoResponseVO;
import com.imocom.intelcom.ws.ebs.vo.response.OportunidadResponseVO;
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
public class OportunidadService {

    private static final Logger logger = Logger.getLogger(OportunidadService.class);
    private MiddlewareServiceRequestVO requestOportunidad;
    private int numeroParametrosWSOportunidad = 0;
    private MiddlewareServiceRequestVO requestAsiganarAsesor;
    private int numeroParametrosWSOportunidadAsignarAsesor = 0;
    private MiddlewareServiceRequestVO requestActualizar;
    private int numeroParametrosWSOportunidadActualizar = 0;
    private final IntelcomWSClient getClientWs;
    private final IServiciosEBSLocal iServiciosESB;
    private final ImodeloServiceLocal imodeloServiceLocal;
    private final IProductoServiceLocal iProductoServiceLocal;

    public OportunidadService(IServiciosEBSLocal iServiciosESB, IntelcomWSClient getClientWs, ImodeloServiceLocal imodeloServiceLocal, IProductoServiceLocal iProductoServiceLocal) {
        this.iServiciosESB = iServiciosESB;
        this.getClientWs = getClientWs;
        this.imodeloServiceLocal = imodeloServiceLocal;
        this.iProductoServiceLocal = iProductoServiceLocal;
        armarRequestWSOportunidad();
        armarRequestWSAsignarAsesor();
        armarRequestWSAActualizar();
    }

    private void armarRequestWSAsignarAsesor() {
        try {
            requestAsiganarAsesor = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDADESASIGNARASESOR);
            requestAsiganarAsesor.setEndpoint(servicio.getInterfazEndpoint());
            requestAsiganarAsesor.setMethod(servicio.getMetodo());
            requestAsiganarAsesor.setNamespacePort(servicio.getNamespace());
            requestAsiganarAsesor.setServiceName(servicio.getQnameServicio());
            requestAsiganarAsesor.setWsdlUrl(servicio.getUrlWsdl());
            requestAsiganarAsesor.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWSOportunidadAsignarAsesor = servicio.getNumeroParametros();
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequestWSOportunidad() {
        try {
            requestOportunidad = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDADESCONSULTAASESORMASIVO);
            requestOportunidad.setEndpoint(servicio.getInterfazEndpoint());
            requestOportunidad.setMethod(servicio.getMetodo());
            requestOportunidad.setNamespacePort(servicio.getNamespace());
            requestOportunidad.setServiceName(servicio.getQnameServicio());
            requestOportunidad.setWsdlUrl(servicio.getUrlWsdl());
            requestOportunidad.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWSOportunidad = servicio.getNumeroParametros();
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequestWSAActualizar() {
        try {
            requestActualizar = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_OPORTUNIDADACTUALIZAR);
            requestActualizar.setEndpoint(servicio.getInterfazEndpoint());
            requestActualizar.setMethod(servicio.getMetodo());
            requestActualizar.setNamespacePort(servicio.getNamespace());
            requestActualizar.setServiceName(servicio.getQnameServicio());
            requestActualizar.setWsdlUrl(servicio.getUrlWsdl());
            requestActualizar.setInterfaceType(servicio.getTipoInterfaz());
            //Cargamos el número de parametros
            numeroParametrosWSOportunidadActualizar = servicio.getNumeroParametros();
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public List<Oportunidad> buscar(String login) {

        List<Oportunidad> oportunidades = new ArrayList<Oportunidad>();

        try {
            String[] paramsService = new String[numeroParametrosWSOportunidad];
            paramsService[0] = login;
            //paramsService[0] = "FVARGAS";
            requestOportunidad.setParams(paramsService);
            String responseStr = getClientWs.consumeService(requestOportunidad);
            logger.info("Oportunidades Clientes Filtro: " + responseStr);
            System.out.println("Oportunidades Clientes Filtro: " + responseStr);
            OportunidadMasivoResponseVO oportunidadResponseVO = (OportunidadMasivoResponseVO) Utils.unmarshal(OportunidadMasivoResponseVO.class, responseStr);
            if (oportunidadResponseVO != null && oportunidadResponseVO.getOportunidades() != null) {
                List<OportunidadMasivoVO> oportunidadesTmp = oportunidadResponseVO.getOportunidades();
                System.out.println(" sizing " + oportunidadesTmp.size() + " iProductoServiceLocal " + iProductoServiceLocal);
                logger.info(" sizing " + oportunidadesTmp.size() + " iProductoServiceLocal " + iProductoServiceLocal);
                for (OportunidadMasivoVO oportunidadTmp : oportunidadesTmp) {
                    oportunidades.add(new Oportunidad(oportunidadTmp, imodeloServiceLocal, iProductoServiceLocal));
                }
            }
        } catch (IntelcomMiddlewareException ex) {
            java.util.logging.Logger.getLogger(OportunidadService.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error consultando oportunidad : " + ex.getMessage(), ex);
        } catch (UtilException ex) {
            java.util.logging.Logger.getLogger(OportunidadService.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("Error consultando oportunidad : " + ex.getMessage(), ex);
        }
        return oportunidades;
    }

    public void asignar(String idAsesor, String idOportunidad) {

        String[] paramsService = new String[numeroParametrosWSOportunidadAsignarAsesor];
        paramsService[0] = idAsesor;
        paramsService[1] = idOportunidad;
        try {
            requestAsiganarAsesor.setParams(paramsService);
            String responseStr = getClientWs.consumeService(requestAsiganarAsesor);
            System.out.println("Oportunidades Asignar Asesor response : " + responseStr);
        } catch (IntelcomMiddlewareException ex) {
            java.util.logging.Logger.getLogger(OportunidadService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar(String login, Oportunidad oportunidad) {
        String[] paramsService = new String[numeroParametrosWSOportunidadActualizar];
        paramsService[0] = login;
        paramsService[1] = oportunidad.getIdOportunidad();
        paramsService[2] = oportunidad.getValorOriginal() != null ? oportunidad.getValorOriginal() : "0";
        paramsService[3] = DateOracleFormate.formatToSendService(oportunidad.getFechaCierreDate());
        paramsService[4] = oportunidad.getMoneda();
        paramsService[5] = oportunidad.getIdProbabilidadEjecucion();
        paramsService[6] = oportunidad.getIdProbabilidadExito();
        paramsService[7] = oportunidad.getProducto();
        try {
            requestActualizar.setParams(paramsService);
            String responseStr = getClientWs.consumeService(requestActualizar);
            System.out.println("Actualizar Oportunidades  : " + responseStr);
        } catch (IntelcomMiddlewareException ex) {
            java.util.logging.Logger.getLogger(OportunidadService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
