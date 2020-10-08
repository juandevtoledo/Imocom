/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.OportunidadVisita;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IEventosServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@Stateless
public class EventosServiceBean extends AbstractService<Long, Visita> implements IEventosServiceLocal {

    private static final Logger logger = Logger.getLogger(EventosServiceBean.class);

    public EventosServiceBean() {
        super(Visita.class);
    }

    /**
     * Metodo que consultara los eventos para un asesor y un cliente especificos
     *
     * @param asesor
     * @param idCliente
     * @return
     * @throws ServiceException
     */
    public List<Visita> findByClienteAsesor(Usuario asesor, Long idCliente) throws ServiceException {
        return findInitialResultsByClienteAsesor(asesor, idCliente);
    }

    /**
     * Método que consulta los primeros resultados que se mostraran en la carga
     * de la página
     *
     * @param asesor
     * @param idCliente
     * @return
     * @throws ServiceException
     */
    public List<Visita> findInitialResultsByClienteAsesor(Usuario asesor, Long idCliente) throws ServiceException {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("idCliente", idCliente);
            param.put("idAsesor", asesor);

            return (List<Visita>) findResultList("Visita.findByAsesorCliente", param);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, Level.ERROR);
        }

    }

    /**
     * Metodo que se encarga de recibir todos los valores de los filtros dados
     * por el usuario en la página de la aplicación que consulta eventos x
     * Cliente. Evalua todos los filtros y en base a los que tienen algun valor
     * redirige a las consultas respectivas
     *
     * @param asesor
     * @param idCliente
     * @param idTipoEvento
     * @param fechaConsulta
     * @param oportunidad
     * @return
     * @throws ServiceException
     */
    public List<Visita> findPageResultsForEventsByClient(Usuario asesor, Long idCliente, Long idTipoEvento, Date fechaConsulta, String oportunidad) throws ServiceException {
        return findInitialResultsByClienteAsesor(asesor, idCliente);
    }

    /**
     *
     * @param fecha
     * @param idAsesor
     * @return
     * @throws ServiceException
     */
    public List<Visita> buscarEventoFechaAsesor(Date fechaInicio, Date fechaFin, Usuario idAsesor) throws ServiceException {
        return em.createNamedQuery("Visita.buscarEventosXFechaAsesor", entityClass).setParameter("idAsesor", idAsesor).setParameter("fechainicio", fechaInicio).setParameter("fechafinal", fechaFin).getResultList();
    }

    public List<Visita> findInitialResultsByClienteAsesorTipoFechaOportunidad(Usuario idAsesor, Long idCliente, Tipo idTipo, Date fecha, Date fechaFinal, Long idPortunidad) throws ServiceException {

        try {
            String qlString = "SELECT DISTINCT  v FROM Visita v LEFT OUTER JOIN v.oportunidadVisitaSet op WHERE  v.idcliente = :idCliente and v.idasesor = :idAsesor";
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            paramaters.put("idCliente", idCliente);
            paramaters.put("idAsesor", idAsesor);
            if (fecha != null) {
                qlString += "  and v.fechainicio BETWEEN :fechainicio AND :fechafinal";
                paramaters.put("fechainicio", fecha);
                paramaters.put("fechafinal", fechaFinal);
            }
            if (idTipo != null) {
                qlString += "  and v.idTipovisita = :tipovisita";
                paramaters.put("tipovisita", idTipo);
            }
            if(idPortunidad!=null){
                qlString += "  and op.idOportunidad = :idOportunidad";
                paramaters.put("idOportunidad", idPortunidad);
            }
            System.out.println("Fecha " + fecha + " idTipo: " + idTipo);
            System.out.println("Query de Busqueda de visitas: " + qlString);
     
            return (List<Visita>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }

    }

    public List<OportunidadVisita> finOportunidadesXVisita(Long idVisita) throws ServiceException {
         return em.createNamedQuery("OportunidadVisita.findByIdVisitaId", OportunidadVisita.class).setParameter("idVisita", idVisita).getResultList();
    }

    public void removeVisita(Long idVisita) throws ServiceException {
        int executeUpdateOp = em.createQuery("DELETE FROM OportunidadVisita o where o.idVisita.idvisita = :idVisita ").setParameter("idVisita", idVisita).executeUpdate();
        int executeUpdate   = em.createQuery("DELETE FROM Visita v where v.idvisita  = :idVisita ").setParameter("idVisita", idVisita).executeUpdate();
    }

}
