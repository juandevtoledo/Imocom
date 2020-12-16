/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.Cotizacion;
import com.imocom.intelcom.persistence.entities.Marca;
import com.imocom.intelcom.persistence.entities.Modelo;
import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.entities.Visita;
import com.imocom.intelcom.services.interfaces.IProductoServiceLocal;
import com.imocom.intelcom.services.interfaces.ImodeloServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.util.DateOracleFormate;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadMasivoVO;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public final class Oportunidad {

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
    private String fechaCierreMostrar;
    private String probabilidadEjecucion;
    private String probabilidadExito;

    private List<ProductoVO> productos;
    private List<Observacion> observaciones;
    private List<Visita> eventos;
    private List<Cotizacion> cotizaciones;
    private List<Modelo> modelos;

    //Hard Fixed Code
    private String concecutivoCotizacion;
    private String producto;
    private String marca;
    private String modelo;
    private String moneda;
    private String trmOportunidad;
    private String valorOriginal;
    private String fechaEstimadaFacturacion;
    private String probabilidadNegocio;
    //
    private Marca idMarca;
    private Modelo idModelo;
    private Producto idProducto;
    private ImodeloServiceLocal imodeloServiceLocal;
    private IProductoServiceLocal iProductoServiceLocal;
    private List<Producto> productosOp;
    private Date fechaCierreDate;

    public Oportunidad() {
    }

    public Oportunidad(OportunidadVO vo, ImodeloServiceLocal imodeloServiceLocal, IProductoServiceLocal iProductoServiceLocal) {
        this(vo);
        this.imodeloServiceLocal = imodeloServiceLocal;
        this.iProductoServiceLocal = iProductoServiceLocal;
    }

    public Oportunidad(OportunidadMasivoVO vo, ImodeloServiceLocal imodeloServiceLocal, IProductoServiceLocal iProductoServiceLocal) {
        this.imodeloServiceLocal = imodeloServiceLocal;
        this.iProductoServiceLocal = iProductoServiceLocal;
        setOportunidad(vo);

    }

    private void setOportunidad(OportunidadMasivoVO vo) {
        this.idOportunidad = vo.getIdOportunidad();
        this.nombreOportunidad = vo.getDescripcion();
        this.idEstadoOportunidad = vo.getEstadoOportunidad();
        this.nombreEtapa = vo.getEtapaOportunidad();
        this.fechaCierre = vo.getFechaCierre();
        this.idIncoterm = vo.getIncoterm();
        this.idMoneda = vo.getMoneda();
        this.monto = vo.getMonto();
        this.valorOriginal = vo.getMonto();                
        this.nit = vo.getNitCliente();
        this.nombreCliente = vo.getNombreCliente();
        this.nombreOportunidad = vo.getNombreOportunidad();
        this.idProbabilidadEjecucion = vo.getProbabilidadEjecucion();
        this.idProbabilidadExito = vo.getProbabilidadExito();
        this.idTipoOportunidad = vo.getTipoOportunidad();
        this.idCanalEntrada = vo.getCanalEntrada();
        this.fechaCierreMostrar = vo.getFechaCierre();
        this.producto = vo.getProducto();
        this.marca = vo.getMarca();
        this.modelo = vo.getModelo();
        this.moneda = vo.getMoneda();
        this.fechaCierreDate = DateOracleFormate.convertToDate(vo.getFechaCierre());
        setMarcaModeloByProductEntity();
        calcularProbalidad();

    }

    private void setMarcaModeloByProductEntity() throws NumberFormatException {
        try {
            Producto productoEntity = iProductoServiceLocal.buscarProducto(Long.valueOf(this.producto));
            this.idMarca = new Marca();
            this.idMarca.setNombre(productoEntity.getIdModelo().getIdMarca().getNombre());
            this.idModelo = new Modelo();
            this.idModelo.setNombre(productoEntity.getIdModelo().getNombre());
            this.idProducto = new Producto();
            this.idProducto.setDescripcion(productoEntity.getDescripcion());
        } catch (ServiceException ex) {
            Logger.getLogger(Oportunidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Oportunidad(OportunidadVO vo) {
        idOportunidad = vo.getIdOportunidad();
        nombreOportunidad = vo.getDescripcion();

        idEstadoOportunidad = vo.getEstadoOportunidad();
        nombreEtapa = vo.getEtapaOportunidad();

        fechaCierre = vo.getFechaCierre();

        idIncoterm = vo.getIncoterm();
        idMoneda = vo.getMoneda();
        monto = vo.getMonto();
        nit = vo.getNitCliente();
        nombreCliente = vo.getNombreCliente();
        nombreOportunidad = vo.getNombreOportunidad();
        observacionPedido = vo.getObservacion();
        idProbabilidadEjecucion = vo.getProbabilidadEjecucion();
        idProbabilidadExito = vo.getProbabilidadExito();
        idTipoOportunidad = vo.getTipoOportunidad();
        idCanalEntrada = vo.getCanalEntrada();
        if (vo.getIdArchivoOrdenCompra() != null) {
            idArchivoOrdenCompra = vo.getIdArchivoOrdenCompra().trim();
        }
        fechaCierreMostrar = vo.getFechaCierre();
        this.concecutivoCotizacion = "01-COT";
        this.producto = "PR-01-MAC-APPLE";
        this.marca = "DELL";
        this.modelo = "Model";
        this.moneda = "COP";
        this.trmOportunidad = "0.1";
        this.valorOriginal = "200000";
        this.fechaEstimadaFacturacion = vo.getFechaCierre();
        this.probabilidadNegocio = "Baja";
        this.idMarca = new Marca();
        this.idMarca.setNombre("DELL");
        this.idModelo = new Modelo();
        this.idModelo.setNombre("Model1");
        this.idProducto = new Producto();
        this.idProducto.setDescripcion("PR-01-MAC-APPLE");
        calcularProbalidad();
        //vo.getDivision();
        //vo.getIdAsesor();
    }

    public void cargarModelos() {
        System.out.println("Cargando Modelos by marcas " + this.idMarca.getIdMarca());
        try {
            modelos = imodeloServiceLocal.buscarModeloPorMarca(this.idMarca.getIdMarca());
        } catch (ServiceException ex) {
            System.out.println("Error cargando modelo msg : " + ex.getMessage());
            modelos = new ArrayList<Modelo>();
        }
    }

    public void cargarProducto() {

        try {
            productosOp = iProductoServiceLocal.buscarProductos(idMarca.getLinea(), "", null, idMarca.getIdMarca(), idModelo.getIdModelo());
        } catch (ServiceException ex) {
            System.out.println("Error cargando modelo msg : " + ex.getMessage());
            productos = new ArrayList<ProductoVO>();
        }

    }

    public void calcularProbalidad() {
        System.out.println("probabilidadEjecucion -> " + this.probabilidadEjecucion);
        System.out.println("probabilidadExito -> " + this.probabilidadExito);
        if (idProbabilidadEjecucion == null || this.idProbabilidadExito == null) {
            this.probabilidadNegocio = "0%";
        } else {
            System.out.println("probabilidadEjecucion-> " + this.idProbabilidadEjecucion);
            System.out.println("probabilidadExito-> " + this.idProbabilidadExito);
            if (this.idProbabilidadEjecucion.toLowerCase().contains("alta") && this.idProbabilidadExito.toLowerCase().contains("alta")) {
                this.probabilidadNegocio = "80%";
            } else if (this.idProbabilidadEjecucion.toLowerCase().contains("alta") && this.idProbabilidadExito.toLowerCase().contains("media")) {
                this.probabilidadNegocio = "50%";
            } else if (this.idProbabilidadEjecucion.toLowerCase().contains("media") && this.idProbabilidadExito.toLowerCase().contains("alta")) {
                this.probabilidadNegocio = "50%";
            } else if (this.idProbabilidadEjecucion.toLowerCase().contains("media") && this.idProbabilidadExito.toLowerCase().contains("media")) {
                this.probabilidadNegocio = "50%";
            } else {
                this.probabilidadNegocio = "10%";
            }
            System.out.println(" probabilidadNegocio " + probabilidadNegocio);
        }
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

    public String getFechaCierreMostrar() {
        return DateOracleFormate.formatToGrid(this.fechaCierreDate);
    }

    public void setFechaCierreMostrar(String fechaCierreMostrar) {
        this.fechaCierreMostrar = fechaCierreMostrar;
    }

    public String getProbabilidadEjecucion() {
        return probabilidadEjecucion;
    }

    public void setProbabilidadEjecucion(String probabilidadEjecucion) {
        this.probabilidadEjecucion = probabilidadEjecucion;
    }

    public String getProbabilidadExito() {
        return probabilidadExito;
    }

    public void setProbabilidadExito(String probabilidadExito) {
        this.probabilidadExito = probabilidadExito;
    }

    public String getConcecutivoCotizacion() {
        return concecutivoCotizacion;
    }

    public void setConcecutivoCotizacion(String concecutivoCotizacion) {
        this.concecutivoCotizacion = concecutivoCotizacion;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTrmOportunidad() {
        return trmOportunidad;
    }

    public void setTrmOportunidad(String trmOportunidad) {
        this.trmOportunidad = trmOportunidad;
    }

    public String getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(String valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public String getFechaEstimadaFacturacion() {
        return fechaEstimadaFacturacion;
    }

    public void setFechaEstimadaFacturacion(String fechaEstimadaFacturacion) {
        this.fechaEstimadaFacturacion = fechaEstimadaFacturacion;
    }

    public String getProbabilidadNegocio() {
        return probabilidadNegocio;
    }

    public void setProbabilidadNegocio(String probabilidadNegocio) {
        this.probabilidadNegocio = probabilidadNegocio;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Modelo getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Modelo idModelo) {
        this.idModelo = idModelo;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public List<Producto> getProductosOp() {
        return productosOp;
    }

    public void setProductosOp(List<Producto> productosOp) {
        this.productosOp = productosOp;
    }

    public Date getFechaCierreDate() {
        return fechaCierreDate;
    }

    public void setFechaCierreDate(Date fechaCierreDate) {
        this.fechaCierreDate = fechaCierreDate;
    }
    
    

}
