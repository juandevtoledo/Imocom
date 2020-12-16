/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
 */
package com.imocom.intelcom.view.security;

import static com.imocom.intelcom.util.utility.Constants.CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.REDIRECT_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_CLIENT_CONSULTADO_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_CLIENT_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_MENU_OP_REQUEST_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_OPORTUNITY_ID_PARAM;
import static com.imocom.intelcom.util.utility.Constants.SPECIFIC_OPORTUNITY_PROBABILITY_ID_PARAM;
import com.imocom.intelcom.view.util.JSFUtils;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong> : This class handle application navigations rules.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@ManagedBean(name = "navigationFaces")
@ApplicationScoped
public class NavigationFaces extends JSFUtils {

    public final Map<String, String> navigation = new HashMap<String, String>();
    private static final long serialVersionUID = 1520318172495977648L;

    {
        navigation.put(getViewRedirect("redirect.view.page.index"), toIndex());
        navigation.put(getViewRedirect("redirect.view.page.perfil"), toPerfilUsuario());
        navigation.put(getViewRedirect("redirect.view.page.cartera.consultar"), toCarteraConsulta());
        navigation.put(getViewRedirect("redirect.view.page.clientes.consulta"), toClientesConsulta());
        navigation.put(getViewRedirect("redirect.view.page.clientes.mapa"), toClientesMapa());
        navigation.put(getViewRedirect("redirect.view.page.clientes.detalle"), toClientesDetalle());
        navigation.put(getViewRedirect("redirect.view.page.clientes.detalleConsultado"), toClientesDetalleConsultado());
        navigation.put(getViewRedirect("redirect.view.page.clientes.actualizacion"), toClientesActualizacion());
        navigation.put(getViewRedirect("redirect.view.page.clientes.prospecto.crear"), toClientesProspectoCrear());
        navigation.put(getViewRedirect("redirect.view.page.clientes.contacto.actualizar"), toClientesContactoActualizacion());
        navigation.put(getViewRedirect("redirect.view.page.eventos.consultar"), toEventosConsultar());
        navigation.put(getViewRedirect("redirect.view.page.eventos.crear"), toEventosCrear());
        navigation.put(getViewRedirect("redirect.view.page.eventos.crearXCliente"), toEventosCrearXCliente());
        navigation.put(getViewRedirect("redirect.view.page.eventos.crearXOportunidad"), toEventosCrearXOportunidad());
        navigation.put(getViewRedirect("redirect.view.page.eventos.crearXOportunidadProbabilidad"), toEventosCrearXOportunidadProbabilidad());
        navigation.put(getViewRedirect("redirect.view.page.eventos.crearConResultado"), toEventosCrearConResultado());
        navigation.put(getViewRedirect("redirect.view.page.eventos.detalle"), toEventosDetalle());
        navigation.put(getViewRedirect("redirect.view.page.eventos.modificar"), toEventosModificar());
        navigation.put(getViewRedirect("redirect.view.page.eventos.detalleXCliente"), toEventosDetalleXCliente());
        navigation.put(getViewRedirect("redirect.view.page.eventos.resultado"), toEventosResultado());
        navigation.put(getViewRedirect("redirect.view.page.eventos.resultadoXCliente"), toEventosResultadoXCliente());
        navigation.put(getViewRedirect("redirect.view.page.eventos.calendario"), toEventosCalendario());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.consultar"), toOportunidadesConsulta());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.consultar.menu"), toOportunidadesConsultaMenu());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.crear"), toOportunidadesCrear());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.detalle"), toOportunidadesDetalle());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.seguimiento"), toOportunidadesSeguimiento());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.probabilidad"), toOportunidadesProbabilidad());
        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.consultar"), toCotizacionesConsultar());
        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.crear"), toCotizacionesCrear());
        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.detalle"), toCotizacionesDetalle());
        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.actualizar"), toCotizacionesActualizar());
        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.registrarseguimiento"), toRegistroSegumiento());
        navigation.put(getViewRedirect("redirect.view.page.inventario.consulta"), toInventarioConsulta());
        navigation.put(getViewRedirect("redirect.view.page.clientes.visitas"), toClientesVisitas());
        navigation.put(getViewRedirect("redirect.view.page.oportunidades.crear.excel"), toOportunidadesCrearExcel());

        navigation.put(getViewRedirect("redirect.view.page.clientes.documento.documentacion"), "/pages/secured/mobile/clientes/consulta-documentos.jsf?faces-redirect=true");
        navigation.put(getViewRedirect("redirect.view.page.clientes.documento.proyecto"), "/pages/secured/mobile/clientes/consulta-proyectos.jsf?faces-redirect=true");
        navigation.put(getViewRedirect("redirect.view.page.clientes.documento.cargar"), "/pages/secured/mobile/clientes/cargar-documentos.jsf?faces-redirect=true");

        navigation.put(getViewRedirect("redirect.view.page.clientes.proyecto.cargar"), "/pages/secured/mobile/clientes/cargar-proyectos.jsf?faces-redirect=true");
        navigation.put(getViewRedirect("redirect.view.page.clientes.proyecto.consultar"), "/pages/secured/mobile/clientes/consulta-proyectos.jsf?faces-redirect=true");

        navigation.put(getViewRedirect("redirect.view.page.leads.consultar"), "/pages/secured/mobile/leads/consulta-leads.jsf?faces-redirect=true");

        navigation.put(getViewRedirect("redirect.view.page.leads.crear"), "/pages/secured/mobile/leads/crear-leads.jsf?faces-redirect=true");

        navigation.put(getViewRedirect("redirect.view.page.cotizaciones.version"), "/pages/secured/mobile/cotizaciones/cotizaciones-crear_version.jsf?faces-redirect=true");

        navigation.put(getViewRedirect("redirect.view.page.oportunidad.actualizarprobabilidad"), toOportunidadActualizarProbabilidad());

        navigation.put(getViewRedirect("redirect.view.page.productos.version"), "/pages/secured/mobile/productos/consultor-productos.jsf?faces-redirect=true");
        navigation.put(getViewRedirect("redirect.view.page.productos.crear"), "/pages/secured/mobile/productos/crear_producto.jsf?faces-redirect=true");
        navigation.put(getViewRedirect("redirect.view.page.modificacion.masiva"), "/pages/secured/mobile/modificacion-masiva/oportunida-cliente-editar.jsf?faces-redirect=true");

    }

    /**
     *
     * @return
     */
    public String getRedirectParamName() {
        return REDIRECT_PARAM;
    }

    /**
     *
     * @return
     */
    public String getClientIdParamName() {
        return CLIENT_ID_PARAM;
    }

    /**
     * Go to index page.
     *
     * @return Index page name.
     */
    private String toIndex() {
        return "/pages/secured/index_m.jsf?faces-redirect=true";
    }

    private String toPerfilUsuario() {
        return "/pages/secured/mobile/usuarios/usuario-perfil.jsf?faces-redirect=true";
    }

    private String toCarteraConsulta() {
        return "/pages/secured/mobile/cartera/cartera-consultar.jsf?faces-redirect=true";
    }

    private String toClientesConsulta() {
        return "/pages/secured/mobile/clientes/clientes-nombre-nit.jsf?faces-redirect=true";
    }

    private String toClientesMapa() {
        return "/pages/secured/mobile/clientes/clientes-mapa.jsf?faces-redirect=true";
    }

    private String toClientesDetalle() {
        return "/pages/secured/mobile/clientes/clientes-detalle.jsf?faces-redirect=true";
    }

    private String toClientesDetalleConsultado() {
        return toClientesDetalle() + "&" + SPECIFIC_CLIENT_CONSULTADO_ID_PARAM + "=true";
    }

    private String toClientesActualizacion() {
        return "/pages/secured/mobile/clientes/clientes-actualizar.jsf?faces-redirect=true";
    }

    private String toClientesContactoActualizacion() {
        return "/pages/secured/mobile/clientes/clientes-contacto-actualizar.jsf?faces-redirect=true";
    }

    private String toClientesProspectoCrear() {
        return "/pages/secured/mobile/clientes/clientes-prospecto-crear.jsf?faces-redirect=true";
    }

    private String toEventosConsultar() {
        return "/pages/secured/mobile/eventos/eventos-consulta.jsf?faces-redirect=true";
    }

    private String toEventosCrear() {
        return "/pages/secured/mobile/eventos/eventos-crear.jsf?faces-redirect=true";
    }

    private String toEventosCrearXCliente() {
        return toEventosCrear() + "&" + SPECIFIC_CLIENT_ID_PARAM + "=true";
    }

    private String toEventosCrearXOportunidad() {
        return toEventosCrearXCliente() + "&" + SPECIFIC_OPORTUNITY_ID_PARAM + "=true";
    }

    private String toEventosCrearXOportunidadProbabilidad() {
        return toEventosCrear() + "&" + SPECIFIC_OPORTUNITY_PROBABILITY_ID_PARAM + "=true";
    }

    private String toEventosCrearConResultado() {
        return "/pages/secured/mobile/eventos/eventos-crear-con-resultado.jsf?faces-redirect=true";
    }

    private String toEventosDetalle() {
        return "/pages/secured/mobile/eventos/eventos-detalle.jsf?faces-redirect=true";
    }

    private String toEventosModificar() {
        return "/pages/secured/mobile/eventos/eventos-modificar.jsf?faces-redirect=true";
    }

    private String toEventosDetalleXCliente() {
        return toEventosDetalle() + "&" + SPECIFIC_CLIENT_ID_PARAM + "=true";
    }

    private String toEventosResultado() {
        return "/pages/secured/mobile/eventos/eventos-resultado.jsf?faces-redirect=true";
    }

    private String toEventosResultadoXCliente() {
        return toEventosResultado() + "&" + SPECIFIC_CLIENT_ID_PARAM + "=true";
    }

    private String toOportunidadesConsulta() {
        return "/pages/secured/mobile/oportunidades/oportunidades-clientes.jsf?faces-redirect=true";
    }

    private String toOportunidadesConsultaMenu() {
        return toOportunidadesConsulta() + "&" + SPECIFIC_MENU_OP_REQUEST_ID_PARAM + "=true";
    }

    private String toOportunidadesCrear() {
        return "/pages/secured/mobile/oportunidades/oportunidades-crear.jsf?faces-redirect=true";
    }

    private String toOportunidadesCrearExcel() {
        return "/pages/secured/mobile/oportunidades/oportunidades-crearexcel.jsf?faces-redirect=true";
    }

    private String toOportunidadesDetalle() {
        return "/pages/secured/mobile/oportunidades/oportunidades-detalle.jsf?faces-redirect=true";
    }

    private String toOportunidadesSeguimiento() {
        return "/pages/secured/mobile/oportunidades/oportunidades-seguimiento.jsf?faces-redirect=true";
    }

    private String toOportunidadesProbabilidad() {
        return "/pages/secured/mobile/oportunidades/oportunidades-probabilidad.jsf?faces-redirect=true";
    }

    private String toEventosCalendario() {
        return "/pages/secured/mobile/eventos/eventos-calendario.jsf?faces-redirect=true";
    }

    private String toCotizacionesConsultar() {
        return "/pages/secured/mobile/cotizaciones/cotizaciones-consultar.jsf?faces-redirect=true";
    }

    private String toCotizacionesCrear() {
        return "/pages/secured/mobile/cotizaciones/cotizaciones-crear.jsf?faces-redirect=true";
    }

    private String toCotizacionesDetalle() {
        return "/pages/secured/mobile/cotizaciones/cotizaciones-detalle.jsf?faces-redirect=true";
    }

    private String toCotizacionesActualizar() {
        return "/pages/secured/mobile/cotizaciones/cotizaciones-actualizar.jsf?faces-redirect=true";
    }

    private String toInventarioConsulta() {
        return "/pages/secured/mobile/inventario/inventario-consulta.jsf?faces-redirect=true";
    }

    private String toRegistroSegumiento() {
        return "/pages/secured/mobile/cotizaciones/registro-seguimiento.jsf?faces-redirect=true";
    }

    private String toClientesVisitas() {
        return "/pages/secured/mobile/clientes/clientes-visitas.jsf?faces-redirect=true";
    }

    private String toOportunidadActualizarProbabilidad() {
        return "/pages/secured/mobile/oportunidades/oportunidad-actualizarprobabilidad.jsf?faces-redirect=true";
    }

}
