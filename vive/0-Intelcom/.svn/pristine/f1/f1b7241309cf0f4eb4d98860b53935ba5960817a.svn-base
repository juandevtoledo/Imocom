/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.util;

import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ESTADO_OP;
import static com.imocom.intelcom.util.utility.Constants.ATR_OPORTUNIDAD_CREACION_ETAPA_OP;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.view.beans.OportunidadesCrearFacesBean;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadBPMVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;

/**
 * Clase que lee un documento en excel de oportunidades
 *
 * @author Carlos Guzman
 */
public class ReadExcel implements Serializable {

    private static final Logger logger = Logger.getLogger(ReadExcel.class);
    private static final long serialVersionUID = 1L;
    private HashMap<Integer, String> _meses = new HashMap<Integer, String>();
    /**
     * Parametros para invocar MDW para Buscar un nombre de un producto
     */
    private MiddlewareServiceRequestVO requestProducto;
    /**
     * NÃƒÂºmero de paramatos para request de CreaciÃƒÂ³n de CotizaciÃƒÂ³n
     */
    private int numeroParamatrosWSProducto = 0;
    @EJB
    private IServiciosEBSLocal iServiciosESB;

   


    public Boolean registroValido(OportunidadBPMVO oportunidadBPM) {

        return !(oportunidadBPM.getNombreUsuario().equals("") && oportunidadBPM.getIdCliente().equals("") && oportunidadBPM.getObservacion().equals("")
                && oportunidadBPM.getIdCanalEntrada().equals("")  && oportunidadBPM.getProbabilidadImocom().equals("")
                && oportunidadBPM.getProbabilidadProyecto().equals(""));
    }

    public ReadExcel() {
        _meses.put(1, "JAN");
        _meses.put(2, "FEB");
        _meses.put(3, "MAR");
        _meses.put(4, "APR");
        _meses.put(5, "MAY");
        _meses.put(6, "JUN");
        _meses.put(7, "JUL");
        _meses.put(8, "AUG");
        _meses.put(9, "SEP");
        _meses.put(10, "OCT");
        _meses.put(11, "NOV");
        _meses.put(12, "DIC");
        // _userSession = userSession;
      
    }

    /**
     * Método que convierte fecha a un formato de entreda de la SOA
     *
     * @param fecha
     * @return
     */
    public String convertFecha(String fecha) throws IntelcomMiddlewareException {
        try {
            String fechaAry[] = fecha.split("\\/");
            String dia = fechaAry[0];
            Integer mesInt = Integer.parseInt(fechaAry[1]);
            String mes = _meses.get(mesInt);
            String year = fechaAry[2];
            if (fechaAry[2].length() > 2) {
                year = fechaAry[2].substring(2);
            }
            return dia + "-" + mes + "-" + year;
        } catch (Exception ex) {
            throw new IntelcomMiddlewareException("Error en formato de fecha, Debe ser (dd/MM/yyyy): " + ex.getMessage());
        }
    }

    public String trimExcel(String _value) {
        if (_value != null) {
            return _value.trim();
        }
        return "";
    }

    /**
     * Metodo que lee un documento y genera un listado de elementos
     * oportunidadesVO
     *
     * @param fileExcel
     * @return
     */
    public List<OportunidadBPMVO> do_proccess(InputStream fileExcel) {
        try {
            WorkbookSettings set = new WorkbookSettings();
            set.setEncoding("UTF-8");
            Workbook archivoExcel = Workbook.getWorkbook(fileExcel, set);
            Sheet hoja = archivoExcel.getSheet(0);
            int numFilas = hoja.getRows();
            logger.info("Nombre de la Hoja\t" + hoja.getName());
            List<OportunidadBPMVO> OportunidadBPMVOList = new ArrayList<OportunidadBPMVO>();

            for (int fila = 1; fila < numFilas; fila++) {
                OportunidadBPMVO OportunidadBPMVO = new OportunidadBPMVO();
                String asesor = hoja.getCell(0, fila).getContents();
                asesor = trimExcel(asesor);
                String nit = hoja.getCell(1, fila).getContents();
                nit = trimExcel(nit);
                String descripcion = hoja.getCell(2, fila).getContents();
                descripcion = trimExcel(descripcion);
                // String monto = hoja.getCell(3, fila).getContents();
                // monto = trimExcel(monto);
                String canal = hoja.getCell(3, fila).getContents();
                canal = trimExcel(canal);
                String fechaCierre = hoja.getCell(4, fila).getContents();
                fechaCierre = trimExcel(fechaCierre);
                //String moneda = hoja.getCell(6, fila).getContents();
                //moneda = trimExcel(moneda);
                String probCliente = hoja.getCell(5, fila).getContents();
                probCliente = trimExcel(probCliente);
                String probImoc = hoja.getCell(6, fila).getContents();
                probImoc = trimExcel(probImoc);
                String tipoOpr = hoja.getCell(7, fila).getContents();
                tipoOpr = trimExcel(tipoOpr);
                String codPro = hoja.getCell(8, fila).getContents();
                codPro = trimExcel(codPro);
                String cant = hoja.getCell(9, fila).getContents();
                cant = trimExcel(cant);
                OportunidadBPMVO.setNombreUsuario(asesor);
                OportunidadBPMVO.setIdCliente(nit);
                OportunidadBPMVO.setObservacion(descripcion);
                OportunidadBPMVO.setNombreOportunidad(descripcion);

                OportunidadBPMVO.setIdCanalEntrada(canal);
                //OportunidadBPMVO.setIdTipoMoneda(moneda);
                OportunidadBPMVO.setProbabilidadImocom(probImoc);
                OportunidadBPMVO.setProbabilidadProyecto(probCliente);
                OportunidadBPMVO.setIdTipoOportunidad(tipoOpr);
                OportunidadBPMVO.setRespuestaCreacion("");
                try {
                    logger.info("[READ EXCEL] : "+fechaCierre);
                    Date _dateCierre = DateUtil.getStringStandartToDateYear(fechaCierre);
                      logger.info("[READ EXCEL date] : "+_dateCierre.toString());
                    String dateOportunidad = DateUtil.formatBPMTime(_dateCierre);
                    logger.info("[READ EXCEL FORMAT BPM FORMAT] : "+dateOportunidad);
                    OportunidadBPMVO.setFechaEstimadaCierre(dateOportunidad);
                    OportunidadBPMVO.setFechaCreacion(DateUtil.formatBPMTime(new Date()));
                    OportunidadBPMVO.setFechaModificacion(DateUtil.formatBPMTime(new Date()));
                } catch (Exception ex) {
                    OportunidadBPMVO.setRespuestaCreacion(ex.getMessage());
                }
                //OportunidadBPMVO.setFechaCreacion(DateUtil.formatBPMTime(new Date()));
                //OportunidadBPMVO.setFechaModificacion(DateUtil.formatBPMTime(new Date()));
                OportunidadBPMVO.setIdEstadoOportunidad(ATR_OPORTUNIDAD_CREACION_ESTADO_OP);
                OportunidadBPMVO.setIdEtapaOportunidad(ATR_OPORTUNIDAD_CREACION_ETAPA_OP);
                //Se busca el producto enviado

                ProductoVO productoVO = new ProductoVO();
                productoVO.setCodigo(codPro);
                productoVO.setCantidad(cant);
                List<ProductoVO> productList = new ArrayList<ProductoVO>();
                productList.add(productoVO);
                OportunidadBPMVO.setOportunidadProducto(productList);
                /*if (productoVO != null) {
                 productoVO.setCantidad(cant);
                 if (productoVO.getPrecioLista() == null || productoVO.getPrecioLista().equals("")) {
                 OportunidadBPMVO.setMonto("0");
                 } else {
                 OportunidadBPMVO.setMonto(productoVO.getPrecioLista());
                 }
                 if (productoVO.getMoneda() == null || productoVO.getMoneda().equals("")) {
                 OportunidadBPMVO.setIdTipoMoneda("USD");        
                 } else {
                 OportunidadBPMVO.setIdTipoMoneda(productoVO.getMoneda());
                 }
                 OportunidadBPMVO.setLinea(productoVO.getLinea());

                 List<ProductoVO> productList = new ArrayList<ProductoVO>();
                 productList.add(productoVO);
                 OportunidadBPMVO.setOportunidadProducto(productList);
                 */
                if (registroValido(OportunidadBPMVO)) {
                    OportunidadBPMVOList.add(OportunidadBPMVO);
                }
            }
            return OportunidadBPMVOList;
        } catch (IOException ex) {
            logger.error("Error leyendo archivo de Excel [IOException]: " + ex.getMessage());
        } catch (BiffException ex) {
            logger.error("Error leyendo archivo de Excel [BiffException]: " + ex.getMessage());
        }

        return null;
    }
}
