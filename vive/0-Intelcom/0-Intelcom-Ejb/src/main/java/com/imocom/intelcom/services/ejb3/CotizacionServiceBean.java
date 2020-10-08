/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.ICotizacionServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@Stateless
public class CotizacionServiceBean extends AbstractService<Long, Cotizacion> implements ICotizacionServiceLocal {

    private static final Logger logger = Logger.getLogger(EventosServiceBean.class);

    public CotizacionServiceBean() {
        super(Cotizacion.class);
    }

    public List<Cotizacion> buscarCotizacionCliOporVenc(String idCliente, Long idOportunidad, String vencimiento, String tipoConsulta, String userLogin) throws ServiceException {
        try {
            String qlString = "SELECT c FROM Cotizacion c ";
            String sqlWhere = "";
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            if (userLogin != null) {
                sqlWhere = "WHERE c.asesor = :asesor ";
                paramaters.put("asesor", userLogin);
            }
            if (idCliente != null) {
                if (sqlWhere.equals("")) {
                    sqlWhere = "WHERE c.nit = :nit ";
                } else {
                    sqlWhere += "AND c.nit = :nit ";
                }
                paramaters.put("nit", idCliente);
            }
            if (idOportunidad != null) {
                if (sqlWhere.equals("")) {
                    sqlWhere += "WHERE c.idOportunidad = :idOportunidad ";
                } else {
                    sqlWhere += "AND  c.idOportunidad = :idOportunidad ";
                }
                paramaters.put("idOportunidad", idOportunidad);
            }
            if (tipoConsulta != null) {
                if (sqlWhere.equals("")) {
                    sqlWhere += " WHERE  (CASE WHEN c.idEstadocotizacion.tipoEtiqueta = 'Pendiente' THEN 'solicitud' ";
                    sqlWhere += " WHEN c.idEstadocotizacion.tipoEtiqueta = 'Asignada' THEN 'solicitud' ";
                    sqlWhere += " ELSE 'cotizacion' END) =:tipoConsulta ";
                } else {
                    sqlWhere += " AND  (CASE WHEN c.idEstadocotizacion.tipoEtiqueta = 'Pendiente' THEN 'solicitud' ";
                    sqlWhere += " WHEN c.idEstadocotizacion.tipoEtiqueta = 'Asignada' THEN 'solicitud' ";
                    sqlWhere += " ELSE 'cotizacion' END) =:tipoConsulta ";
                }
                paramaters.put("tipoConsulta", tipoConsulta);
            }
            qlString = qlString + sqlWhere + " ORDER BY c.idCotizacion DESC";
            em.flush();
            return (List<Cotizacion>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }

    }

    public void actualizarCotizacion(Long idCotizacion, String aceptacion, String observacion) {

        String queryUpdate = "UPDATE Cotizacion SET aceptacioncliente=:aceptacion , observacionasesor=:observacion WHERE idCotizacion=:idCotizacion ";
        em.createQuery(queryUpdate).setParameter("aceptacion", aceptacion).setParameter("observacion", observacion).setParameter("idCotizacion", idCotizacion).executeUpdate();

    }
    
    public void actualizarCotizacion(Long idCotizacion, Tipo idOferta) {
        String queryUpdate = "UPDATE Cotizacion SET idtipooferta=:idtipooferta  WHERE idCotizacion=:idCotizacion ";
        em.createQuery(queryUpdate).setParameter("idtipooferta", idOferta).setParameter("idCotizacion", idCotizacion).executeUpdate();
    }

    /**
     * 
     * @param linea
     * @param tipo
     * @return 
     */
    public String procCoodigoCotizacion(String linea, String tipo) {
        try {
            java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
            logger.info("Conn: " + connection);
            logger.info("Tipo de Cotizacion: " + tipo);
            logger.info("Linea de Cotizacion: " + linea);
            CallableStatement stmt = connection.prepareCall("{call REGISTRARCOTIZACION.calcular_numcotizacion(?,?,?)}");
            stmt.setString(1, linea);
            stmt.setString(2, tipo);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.execute();
            String ret = stmt.getString(3);
            logger.info("Codigo Cotizacion: " + ret);
            connection.close();
            return ret;
        } catch (SQLException ex) {
            logger.error("Error Capturando codigo de cotización:" + ex.getMessage());
        }
        return "";

    }
    /**
    * Busca las cotizaciones elaboradas asociadas a una oportunidad
    * @param idOportunidad identificador de oportunidad
    * @return 
    */
    public List<Cotizacion> buscarCotizacionOpElaborada(Long idOportunidad) throws ServiceException {
        try {

            String qlString = "SELECT c FROM Cotizacion c ";
            qlString += "WHERE c.idOportunidad = :idOportunidad ";
            qlString += " AND   c.idEstadocotizacion.tipoEtiqueta = 'Elaborada'";
            HashMap<String, Object> paramaters = new HashMap<String, Object>();
            paramaters.put("idOportunidad", idOportunidad);
            em.flush();
            return (List<Cotizacion>) findResulQuerytList(qlString, paramaters);
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ex, org.apache.log4j.Level.ERROR);
        }
    }

}
