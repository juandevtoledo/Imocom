/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.ServiciosEbs;
import com.imocom.intelcom.services.interfaces.IServiciosEBSLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.exceptions.UtilException;
import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.WS_CARTERA_CONSULTA_CLIENTE;
import com.imocom.intelcom.util.utility.Utils;
import com.imocom.intelcom.view.AbstractFacesBean;
import com.imocom.intelcom.view.vo.Cartera;
import com.imocom.intelcom.view.vo.Factura;
import com.imocom.intelcom.ws.ebs.vo.entities.CarteraVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.FacturaVO;
import com.imocom.intelcom.ws.ebs.vo.response.CarteraResponseVO;
import com.imocom.intelcom.ws.exception.IntelcomMiddlewareException;
import com.imocom.intelcom.ws.interfaces.jaxws.MiddlewareServiceRequestVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped

public class CarteraConsultarFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CarteraConsultarFacesBean.class);
    private static final long serialVersionUID = 1L;

    private MiddlewareServiceRequestVO requestConsultaCartera;
    private int numeroParametrosWS_ConsultaCartera = 0;

    private Cartera cartera;

    @EJB
    private IServiciosEBSLocal iServiciosESB;

    @Override
    protected void build() {
        ClienteVO clienteSeleccionado;
        clienteSeleccionado = (ClienteVO) getSessionMap().get(CLIENT_ID_PARAM);
        cargarDatosCartera(clienteSeleccionado.getNitCliente());
    }

    private void cargarDatosCartera(String nit) {
        armarRequest_ConsultaCartera();
        String[] paramsService = new String[numeroParametrosWS_ConsultaCartera];
        paramsService[0] = nit;
        paramsService[1] = userSession.getUserLogin();

        requestConsultaCartera.setParams(paramsService);

        try {
            String responseStr = userSession.getClientWs().consumeService(requestConsultaCartera);
            CarteraResponseVO response = (CarteraResponseVO) Utils.unmarshal(CarteraResponseVO.class, responseStr);

            if (response.getFacturas() != null) {
                for (CarteraVO carteraVO : response.getCarteras()) {
                    cartera = new Cartera(carteraVO);
                }
            }

            //Carga facturas
            List<Factura> facturas = new ArrayList<Factura>();
            if (response.getFacturas() != null) {
                for (FacturaVO facturaVO : response.getFacturas()) {
                    facturas.add(new Factura(facturaVO));
                }
            }
            if (cartera != null) {
                cartera.setFacturas(facturas);
            }

        } catch (UtilException ex) {
            logger.error(ex.getMessage());
        } catch (IntelcomMiddlewareException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void armarRequest_ConsultaCartera() {
        try {
            requestConsultaCartera = new MiddlewareServiceRequestVO();
            ServiciosEbs servicio = iServiciosESB.findByUniqueName(WS_CARTERA_CONSULTA_CLIENTE);
            requestConsultaCartera.setEndpoint(servicio.getInterfazEndpoint());
            requestConsultaCartera.setMethod(servicio.getMetodo());
            requestConsultaCartera.setNamespacePort(servicio.getNamespace());
            requestConsultaCartera.setServiceName(servicio.getQnameServicio());
            requestConsultaCartera.setWsdlUrl(servicio.getUrlWsdl());
            //Cargamos el número de parametros
            numeroParametrosWS_ConsultaCartera = servicio.getNumeroParametros();

        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

}
