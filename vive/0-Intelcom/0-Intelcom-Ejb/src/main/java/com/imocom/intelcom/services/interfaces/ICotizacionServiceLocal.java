/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rc
 */
@Local
public interface ICotizacionServiceLocal extends IService<Long, Cotizacion>{
    
    
    /**
     * Metodo que se encarga de hacer la búsqueda de cotizaciones por filtro
     * @param idCliente
     * @param idOportunidad
     * @param vencimiento
     * @param tipoConsulta
     * @return
     * @throws ServiceException 
     */
   public List<Cotizacion> buscarCotizacionCliOporVenc(String idCliente,Long idOportunidad,String vencimiento, String tipoConsulta,String userLogin)throws ServiceException;
   /**
    * 
    * @param idCotizacion
    * @param aceptacion
    * @param observacion 
    */
   public void actualizarCotizacion(Long idCotizacion,String aceptacion,String observacion);
   /**
    * 
    * @param linea
    * @param tipo
    * @return 
    */
   public String procCoodigoCotizacion(String linea, String tipo); 
   /**
    * Busca las cotizaciones elaboradas asociadas a una oportunidad
    * @param idOportunidad identificador de oportunidad
    * @return 
    */
   public List<Cotizacion> buscarCotizacionOpElaborada(Long idOportunidad) throws ServiceException;
   /**
    * Se actualiza el tipo de cotizacion por un id de cotizacion
    * @param idCotizacion id de cotizacion 
    * @param idOferta id oferta
    */
   public void actualizarCotizacion(Long idCotizacion, Tipo idOferta);
}