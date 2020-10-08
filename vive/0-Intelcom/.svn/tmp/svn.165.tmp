/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class Oportunidad {

    private String idOportunidad;
    private String nombreOportunidad;
    private String descripcionOportunidad;
    private String idTipoOportunidad;
    private String nombreTipoOportunidad;

    private String nit;
    private String nombreCliente;
    private String idMoneda;
    private String nombreMoneda;
    private String idEstadoOportunidad;
    private String nombreEstadoOportunidad;
    private String monto;
    private String fechaCierre;
    private String idEtapa;
    private String nombreEtapa;
    private String idProbabilidadEjecucion;
    private String nombreProbabilidadEjecucion;
    private String idProbabilidadExito;
    private String nombreProbabilidadExito;
    private String idIncoterm;
    private String nombreIncoterm;
    private String idCanalEntrada;
    private String nombreCanalEntrada;
    private String idMotivoCierre;
    private String nombreMotivoCierre;
    private String notaCierre;
    private String observacionPedido;
     private String idArchivoOrdenCompra;
    private String nombreEtapaOportunidad;
    
    

    private List<ProductoVO> productos;
    private List<Observacion> observaciones;
    private List<Visita> eventos;
    private List<Cotizacion> cotizaciones;

    public Oportunidad() {
    }
    
    public Oportunidad(OportunidadVO vo) {
        idOportunidad = vo.getIdOportunidad();
        nombreOportunidad = vo.getDescripcion();
        
        idEstadoOportunidad = vo.getEstadoOportunidad();
        nombreEtapa = vo.getEtapaOportunidad();
        
        fechaCierre = vo.getFechaCierre();
        
        idIncoterm = vo.getIncoterm();
        idMoneda= vo.getMoneda();
        monto = vo.getMonto();
        nit = vo.getNitCliente();
        nombreCliente = vo.getNombreCliente();
        nombreOportunidad = vo.getNombreOportunidad();
        observacionPedido = vo.getObservacion();
        idProbabilidadEjecucion = vo.getProbabilidadEjecucion();
        idProbabilidadExito = vo.getProbabilidadExito();
        idTipoOportunidad = vo.getTipoOportunidad();
        idCanalEntrada=vo.getCanalEntrada();
        if(vo.getIdArchivoOrdenCompra()!=null){
            idArchivoOrdenCompra=vo.getIdArchivoOrdenCompra().trim();
        }
        
        //vo.getDivision();
        //vo.getIdAsesor();
    }
    

    public String getIdOportunidad() {
        return idOportunidad;
    }

    public void setIdOportunidad(String idOportunidad) {
        this.idOportunidad = idOportunidad;
    }

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }

    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }

    public String getDescripcionOportunidad() {
        return descripcionOportunidad;
    }

    public void setDescripcionOportunidad(String descripcionOportunidad) {
        this.descripcionOportunidad = descripcionOportunidad;
    }

    public String getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(String idEtapa) {
        this.idEtapa = idEtapa;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public void setNombreEtapa(String nombreEtapa) {
        this.nombreEtapa = nombreEtapa;
    }

    public String getIdTipoOportunidad() {
        return idTipoOportunidad;
    }

    public void setIdTipoOportunidad(String idTipoOportunidad) {
        this.idTipoOportunidad = idTipoOportunidad;
    }

    public String getIdProbabilidadEjecucion() {
        return idProbabilidadEjecucion;
    }

    public void setIdProbabilidadEjecucion(String idProbabilidadEjecucion) {
        this.idProbabilidadEjecucion = idProbabilidadEjecucion;
    }

    public String getIdProbabilidadExito() {
        return idProbabilidadExito;
    }

    public void setIdProbabilidadExito(String idProbabilidadExito) {
        this.idProbabilidadExito = idProbabilidadExito;
    }

    public String getIdIncoterm() {
        return idIncoterm;
    }

    public void setIdIncoterm(String idIncoterm) {
        this.idIncoterm = idIncoterm;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getIdCanalEntrada() {
        return idCanalEntrada;
    }

    public void setIdCanalEntrada(String idCanalEntrada) {
        this.idCanalEntrada = idCanalEntrada;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreMoneda() {
        return nombreMoneda;
    }

    public void setNombreMoneda(String nombreMoneda) {
        this.nombreMoneda = nombreMoneda;
    }

    public String getIdEstadoOportunidad() {
        return idEstadoOportunidad;
    }

    public void setIdEstadoOportunidad(String idEstadoOportunidad) {
        this.idEstadoOportunidad = idEstadoOportunidad;
    }

    public String getNombreEstadoOportunidad() {
        return nombreEstadoOportunidad;
    }

    public void setNombreEstadoOportunidad(String nombreEstadoOportunidad) {
        this.nombreEstadoOportunidad = nombreEstadoOportunidad;
    }

    public String getNombreProbabilidadEjecucion() {
        return nombreProbabilidadEjecucion;
    }

    public void setNombreProbabilidadEjecucion(String nombreProbabilidadEjecucion) {
        this.nombreProbabilidadEjecucion = nombreProbabilidadEjecucion;
    }

    public String getNombreProbabilidadExito() {
        return nombreProbabilidadExito;
    }

    public void setNombreProbabilidadExito(String nombreProbabilidadExito) {
        this.nombreProbabilidadExito = nombreProbabilidadExito;
    }

    public String getNombreIncoterm() {
        return nombreIncoterm;
    }

    public void setNombreIncoterm(String nombreIncoterm) {
        this.nombreIncoterm = nombreIncoterm;
    }

    public String getNombreCanalEntrada() {
        return nombreCanalEntrada;
    }

    public void setNombreCanalEntrada(String nombreCanalEntrada) {
        this.nombreCanalEntrada = nombreCanalEntrada;
    }

    public List<ProductoVO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVO> productos) {
        this.productos = productos;
    }

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }

    public List<Visita> getEventos() {
        return eventos;
    }

    public void setEventos(List<Visita> eventos) {
        this.eventos = eventos;
    }

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public String getNombreTipoOportunidad() {
        return nombreTipoOportunidad;
    }

    public void setNombreTipoOportunidad(String nombreTipoOportunidad) {
        this.nombreTipoOportunidad = nombreTipoOportunidad;
    }

    public String getIdMotivoCierre() {
        return idMotivoCierre;
    }

    public void setIdMotivoCierre(String idMotivoCierre) {
        this.idMotivoCierre = idMotivoCierre;
    }

    public String getNombreMotivoCierre() {
        return nombreMotivoCierre;
    }

    public void setNombreMotivoCierre(String nombreMotivoCierre) {
        this.nombreMotivoCierre = nombreMotivoCierre;
    }

    public String getNotaCierre() {
        return notaCierre;
    }

    public void setNotaCierre(String notaCierre) {
        this.notaCierre = notaCierre;
    }

    public String getObservacionPedido() {
        return observacionPedido;
    }

    public void setObservacionPedido(String observacionPedido) {
        this.observacionPedido = observacionPedido;
    }

    public String getIdArchivoOrdenCompra() {
        return idArchivoOrdenCompra;
    }

    public void setIdArchivoOrdenCompra(String idArchivoOrdenCompra) {
        this.idArchivoOrdenCompra = idArchivoOrdenCompra;
    }

    public String getNombreEtapaOportunidad() {
        return nombreEtapaOportunidad;
    }

    public void setNombreEtapaOportunidad(String nombreEtapaOportunidad) {
        this.nombreEtapaOportunidad = nombreEtapaOportunidad;
    }
    
    

}
