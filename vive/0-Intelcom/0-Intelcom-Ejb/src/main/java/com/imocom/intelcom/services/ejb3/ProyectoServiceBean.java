package com.imocom.intelcom.services.ejb3;

import com.imocom.intelcom.persistence.entities.Proyecto;
import com.imocom.intelcom.persistence.util.PersistenceException;
import com.imocom.intelcom.services.AbstractService;
import com.imocom.intelcom.services.interfaces.IProyectoServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@Stateless
public class ProyectoServiceBean extends AbstractService<Long, Proyecto> implements IProyectoServiceLocal {

    private static final Logger logger = Logger.getLogger(ProyectoServiceBean.class);

    public ProyectoServiceBean() {
        super(Proyecto.class);
    }

    /**
     * 
     * @param nitCliente
     * @param nombre
     * @param anio
     * @param proyectoPlan
     * @return
     * @throws ServiceException 
     */
    public List<Proyecto> findListByNitCliente( String nitCliente, String nombre, String anio, String proyectoPlan ) throws ServiceException{
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("nitCLiente", nitCliente);
            
            String sql = "SELECT p FROM Proyecto p WHERE p.nitCliente = :nitCLiente ";
            
            if( nombre != null && nombre.trim().length() > 0 ){                
                sql += " AND UPPER(p.nombre) LIKE '%"+nombre.trim().toUpperCase()+"%'";
            }
            
            if( anio != null && anio.trim().length() > 0 ){
                params.put("anio", anio.trim());
                sql += " AND p.anio = :anio";
            }
            
            if( proyectoPlan != null && proyectoPlan.trim().length() > 0 ){
                params.put("proyectoPlan", proyectoPlan.trim());
                sql += " AND p.proyectoPlan = :proyectoPlan";
            }

            return (List<Proyecto>) findResulQuerytList( sql, params );
            
        } catch (PersistenceException ex) {
            logger.error(ex);
            throw new ServiceException(ex, Level.ERROR);
        }
    }

}
