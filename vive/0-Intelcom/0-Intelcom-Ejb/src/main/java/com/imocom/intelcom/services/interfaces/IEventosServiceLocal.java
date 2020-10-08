/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.interfaces;

import com.imocom.intelcom.persistence.entities.OportunidadVisita;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.services.IService;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rc
 */
@Local
public interface IEventosServiceLocal extends IService<Long, Visita> {
   
   /**
    * Metodo que consultara los eventos para un asesor y un cliente especificos
    * @param idAsesor
    * @param idCliente
    * @return
    * @throws ServiceException 
    */ 
   public List<Visita> findByClienteAsesor(Usuario idAsesor, Long idCliente ) throws ServiceException;
   
   /**
    * Método que consulta los primeros resultados que se mostraran en la carga de la página
    * @param idAsesor
    * @param idCliente
    * @return
    * @throws ServiceException 
    */
   public List<Visita> findInitialResultsByClienteAsesor(Usuario idAsesor, Long idCliente) throws ServiceException;
 
    
   public List<Visita> buscarEventoFechaAsesor (Date fechainicio,Date fechafinal,Usuario idAsesor) throws ServiceException;
   /**
    * Método que retorna los eventos asociados a un asesor por filtos de cliente, fecha, oportunidad
    * @param idAsesor
    * @param idCliente
    * @param fecha
    * @param idPortunidad
    * @return
    * @throws ServiceException 
    */
   public List<Visita> findInitialResultsByClienteAsesorTipoFechaOportunidad(Usuario idAsesor, Long idCliente,Tipo idTipo,Date fecha,Date fechaFinal,Long idPortunidad) throws ServiceException;
   
   
   public List<OportunidadVisita> finOportunidadesXVisita(Long idVisita) throws ServiceException;
   
   public void removeVisita(Long idVisita) throws ServiceException;
   
}
