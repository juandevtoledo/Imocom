/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.persistence.entities;

import com.imocom.intelcom.persistence.AbstractEntity;
import com.imocom.intelcom.persistence.IDataModel;
import com.imocom.intelcom.util.utility.DateUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author rc
 */
@Entity
@Table(name = "COTIZACION")
@NamedStoredProcedureQuery(
        name = "calcular_numcotizacion",
        procedureName = "REGISTRARCOTIZACION.calcular_numcotizacion",
        returnsResultSet = false,
        parameters = {
            @StoredProcedureParameter(queryParameter = "param1", name = "p1", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param4", name = "p4", direction = Direction.OUT, type = String.class)
        }
)

@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAll", query = "SELECT c FROM Cotizacion c"),
    @NamedQuery(name = "Cotizacion.findByIdCotizacion", query = "SELECT c FROM Cotizacion c WHERE c.idCotizacion = :idCotizacion"),
    @NamedQuery(name = "Cotizacion.findByNit", query = "SELECT c FROM Cotizacion c WHERE c.nit = :nit"),
    @NamedQuery(name = "Cotizacion.findByIdOportunidad", query = "SELECT c FROM Cotizacion c WHERE c.idOportunidad = :idOportunidad"),
    @NamedQuery(name = "Cotizacion.findByIdEspecialista", query = "SELECT c FROM Cotizacion c WHERE c.idEspecialista = :idEspecialista"),
    @NamedQuery(name = "Cotizacion.findByIdProveedor", query = "SELECT c FROM Cotizacion c WHERE c.idProveedor = :idProveedor"),
    @NamedQuery(name = "Cotizacion.findByNombreproveedor", query = "SELECT c FROM Cotizacion c WHERE c.nombreproveedor = :nombreproveedor"),
    @NamedQuery(name = "Cotizacion.findByNombrecontactoproveedor", query = "SELECT c FROM Cotizacion c WHERE c.nombrecontactoproveedor = :nombrecontactoproveedor"),
    @NamedQuery(name = "Cotizacion.findByTelefonocontactoproveedor", query = "SELECT c FROM Cotizacion c WHERE c.telefonocontactoproveedor = :telefonocontactoproveedor"),
    @NamedQuery(name = "Cotizacion.findByFechasolicitud", query = "SELECT c FROM Cotizacion c WHERE c.fechasolicitud = :fechasolicitud"),
    @NamedQuery(name = "Cotizacion.findByFechacreacioncotizacion", query = "SELECT c FROM Cotizacion c WHERE c.fechacreacioncotizacion = :fechacreacioncotizacion"),
    @NamedQuery(name = "Cotizacion.findByFechacompromisoentrega", query = "SELECT c FROM Cotizacion c WHERE c.fechacompromisoentrega = :fechacompromisoentrega"),
    @NamedQuery(name = "Cotizacion.findByFechavencimientosolicitud", query = "SELECT c FROM Cotizacion c WHERE c.fechavencimientosolicitud = :fechavencimientosolicitud"),
    @NamedQuery(name = "Cotizacion.findByValortotal", query = "SELECT c FROM Cotizacion c WHERE c.valortotal = :valortotal"),
    @NamedQuery(name = "Cotizacion.findByAceptacioncliente", query = "SELECT c FROM Cotizacion c WHERE c.aceptacioncliente = :aceptacioncliente"),
    @NamedQuery(name = "Cotizacion.findByIdAprobador", query = "SELECT c FROM Cotizacion c WHERE c.idAprobador = :idAprobador"),
    @NamedQuery(name = "Cotizacion.findByGeneradaporaplicacion", query = "SELECT c FROM Cotizacion c WHERE c.generadaporaplicacion = :generadaporaplicacion"),
    @NamedQuery(name = "Cotizacion.findByIdArchivosolicitud", query = "SELECT c FROM Cotizacion c WHERE c.idArchivosolicitud = :idArchivosolicitud"),
    @NamedQuery(name = "Cotizacion.findByElaboradaproveedor", query = "SELECT c FROM Cotizacion c WHERE c.elaboradaproveedor = :elaboradaproveedor"),
    @NamedQuery(name = "Cotizacion.findByIdarchivocotizacion", query = "SELECT c FROM Cotizacion c WHERE c.idarchivocotizacion = :idarchivocotizacion"),
    @NamedQuery(name = "Cotizacion.findByObservacionasesor", query = "SELECT c FROM Cotizacion c WHERE c.observacionasesor = :observacionasesor"),
    @NamedQuery(name = "Cotizacion.findByObservacionjefelinea", query = "SELECT c FROM Cotizacion c WHERE c.observacionjefelinea = :observacionjefelinea"),
    @NamedQuery(name = "Cotizacion.findByObservacionespecialista", query = "SELECT c FROM Cotizacion c WHERE c.observacionespecialista = :observacionespecialista")})

public class Cotizacion extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Cotizacion_seq_gen")
    @SequenceGenerator(name = "Cotizacion_seq_gen", sequenceName = "SEQ_COTIZACION", allocationSize = 1)
    @Column(name = "ID_COTIZACION")
    private Long idCotizacion;

    @Column(name = "NIT")
    private String nit;

    @Column(name = "ID_OPORTUNIDAD")
    private Long idOportunidad;

    @Column(name = "ID_ESPECIALISTA")
    private Integer idEspecialista;

    @Column(name = "ID_PROVEEDOR")
    private Integer idProveedor;

    @Column(name = "NOMBREPROVEEDOR")
    private String nombreproveedor;

    @Column(name = "NOMBRECONTACTOPROVEEDOR")
    private String nombrecontactoproveedor;

    @Column(name = "TELEFONOCONTACTOPROVEEDOR")
    private String telefonocontactoproveedor;

    @Column(name = "FECHASOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasolicitud;

    @Column(name = "FECHACREACIONCOTIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacioncotizacion;

    @Column(name = "FECHACOMPROMISOENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacompromisoentrega;

    @Column(name = "FECHAVENCIMIENTOSOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencimientosolicitud;
    
    @Column(name = "FECHA_PEDIDO_VENTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimadaPedidoVenta;
    
    @Column(name = "FECHA_ENTREGA_PEDIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimadaEntrega;
    
    @Column(name = "FECHA_FACTURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimadaFacturacion;
    
    @Column(name = "TIENEDESCUENTO")
    private String tieneDescuento;
    
    @Column(name = "PORCENTAJEDESCUENTO")
    private Integer porcentajeDescuento;

    @Column(name = "VALORTOTAL")
    private Double valortotal;

    @Column(name = "ACEPTACIONCLIENTE", length = 30)
    private String aceptacioncliente;

    @Column(name = "ID_APROBADOR")
    private Integer idAprobador;

    @Column(name = "GENERADAPORAPLICACION")
    private String generadaporaplicacion;

    @Column(name = "ID_ARCHIVOSOLICITUD")
    private Long idArchivosolicitud;

    @Column(name = "ELABORADAPROVEEDOR")
    private String elaboradaproveedor;

    @Column(name = "IDARCHIVOCOTIZACION")
    private Long idarchivocotizacion;

    @Column(name = "DIAS_VIGENCIA")
    private String diasVigencia;

    @Column(name = "OBSERVACIONASESOR", length = 1337)
    private String observacionasesor;

    @Column(name = "OBSERVACIONJEFELINEA")
    private String observacionjefelinea;

    @Column(name = "OBSERVACIONESPECIALISTA")
    private String observacionespecialista;

    @Column(name = "ASESOR")
    private String asesor;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DIRIGIDA")
    private String dirigida;
    
    @Column(name = "TELEFONO")
    private String telefono;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "CORREO")
    private String correo;
   
    @JoinColumn(name = "ID_TIPOMONEDA", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo idTipomoneda;

    @JoinColumn(name = "ID_INCOTERM", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo idIncoterm;

    @JoinColumn(name = "ID_ESTADOCOTIZACION", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo idEstadocotizacion;

    @JoinColumn(name = "IDTIPOOFERTA", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo idtipooferta;

    @JoinColumn(name = "ID_VISITAGENERADOR", referencedColumnName = "IDVISITA")
    @ManyToOne
    private Visita idVisitagenerador;

    @OneToMany(mappedBy = "idCotizacion")
    private Set<CotizacionProducto> cotizacionProductoSet;
    
    @Column(name = "INT_NOMBRE_COMPETENCIA")
    private String inteligenciaCompetencia;
    
    @Column(name = "INT_RAZON")
    private String inteligenciaRazon;
    
    @Column(name = "INT_OBSERVACION")
    private String inteligenciaObservaciones;
    
    private transient String nombreOportunidad;
    private transient String nombreCliente;
    private transient String nombreProducto;
    private transient String fechaSolicitudFormat;
    private transient String fechaVencimientoFormat;
    private transient String codigoVersion;
    
    private static final String[] encoded = {  "<", ">", "\"", "\'" , "Ñ" };
    private static final String[] decoded = {  "&lt;", "&gt;", "&quot;", "&apos;" , "Ntilde;"};

    public Cotizacion() {
    }

    public Cotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Long getIdOportunidad() {
        return idOportunidad;
    }

    public void setIdOportunidad(Long idOportunidad) {
        this.idOportunidad = idOportunidad;
    }

    public Integer getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(Integer idEspecialista) {
        this.idEspecialista = idEspecialista;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getNombrecontactoproveedor() {
        return nombrecontactoproveedor;
    }

    public void setNombrecontactoproveedor(String nombrecontactoproveedor) {
        this.nombrecontactoproveedor = nombrecontactoproveedor;
    }

    public String getTelefonocontactoproveedor() {
        return telefonocontactoproveedor;
    }

    public void setTelefonocontactoproveedor(String telefonocontactoproveedor) {
        this.telefonocontactoproveedor = telefonocontactoproveedor;
    }

    public Date getFechasolicitud() {
        return fechasolicitud;
    }

    public void setFechasolicitud(Date fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    public Date getFechacreacioncotizacion() {
        return fechacreacioncotizacion;
    }

    public void setFechacreacioncotizacion(Date fechacreacioncotizacion) {
        this.fechacreacioncotizacion = fechacreacioncotizacion;
    }

    public Date getFechacompromisoentrega() {
        return fechacompromisoentrega;
    }

    public void setFechacompromisoentrega(Date fechacompromisoentrega) {
        this.fechacompromisoentrega = fechacompromisoentrega;
    }

    public Date getFechavencimientosolicitud() {
        return fechavencimientosolicitud;
    }

    public void setFechavencimientosolicitud(Date fechavencimientosolicitud) {
        this.fechavencimientosolicitud = fechavencimientosolicitud;
    }

    public Double getValortotal() {
        return valortotal;
    }

    public void setValortotal(Double valortotal) {
        this.valortotal = valortotal;
    }

    public String getAceptacioncliente() {
        return aceptacioncliente;
    }

    public void setAceptacioncliente(String aceptacioncliente) {
        this.aceptacioncliente = aceptacioncliente;
    }

    public Integer getIdAprobador() {
        return idAprobador;
    }

    public void setIdAprobador(Integer idAprobador) {
        this.idAprobador = idAprobador;
    }

    public String getGeneradaporaplicacion() {
        return generadaporaplicacion;
    }

    public void setGeneradaporaplicacion(String generadaporaplicacion) {
        this.generadaporaplicacion = generadaporaplicacion;
    }

    public Long getIdArchivosolicitud() {
        return idArchivosolicitud;
    }

    public void setIdArchivosolicitud(Long idArchivosolicitud) {
        this.idArchivosolicitud = idArchivosolicitud;
    }

    public String getElaboradaproveedor() {
        return elaboradaproveedor;
    }

    public void setElaboradaproveedor(String elaboradaproveedor) {
        this.elaboradaproveedor = elaboradaproveedor;
    }

    public Long getIdarchivocotizacion() {
        return idarchivocotizacion;
    }

    public void setIdarchivocotizacion(Long idarchivocotizacion) {
        this.idarchivocotizacion = idarchivocotizacion;
    }

    public String getDiasVigencia() {
        return diasVigencia;
    }

    public void setDiasVigencia(String diasVigencia) {
        this.diasVigencia = diasVigencia;
    }

    public String getObservacionasesor() {
        return observacionasesor;
    }

    public void setObservacionasesor(String observacionasesor) {
        this.observacionasesor = observacionasesor;
    }

    public String getObservacionjefelinea() {
        return observacionjefelinea;
    }

    public void setObservacionjefelinea(String observacionjefelinea) {
        this.observacionjefelinea = observacionjefelinea;
    }

    public String getObservacionespecialista() {
        return observacionespecialista;
    }

    public void setObservacionespecialista(String observacionespecialista) {
        this.observacionespecialista = observacionespecialista;
    }

    public Tipo getIdTipomoneda() {
        return idTipomoneda;
    }

    public void setIdTipomoneda(Tipo idTipomoneda) {
        this.idTipomoneda = idTipomoneda;
    }

    public Tipo getIdIncoterm() {
        return idIncoterm;
    }

    public void setIdIncoterm(Tipo idIncoterm) {
        this.idIncoterm = idIncoterm;
    }

    public Tipo getIdEstadocotizacion() {
        return idEstadocotizacion;
    }

    public void setIdEstadocotizacion(Tipo idEstadocotizacion) {
        this.idEstadocotizacion = idEstadocotizacion;
    }

    public Tipo getIdtipooferta() {
        return idtipooferta;
    }

    public void setIdtipooferta(Tipo idtipooferta) {
        this.idtipooferta = idtipooferta;
    }

    public Visita getIdVisitagenerador() {
        return idVisitagenerador;
    }

    public void setIdVisitagenerador(Visita idVisitagenerador) {
        this.idVisitagenerador = idVisitagenerador;
    }

    public Set<CotizacionProducto> getCotizacionProductoSet() {
        return cotizacionProductoSet;
    }

    public void setCotizacionProductoSet(Set<CotizacionProducto> cotizacionProductoSet) {
        this.cotizacionProductoSet = cotizacionProductoSet;
    }

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }

    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public void setCodigoVersion(String codigoversion) {
        this.codigoVersion = codigoversion;
    }
    
    public String getCodigoVersion() {
        return codigoVersion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacion != null ? idCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.idCotizacion == null && other.idCotizacion != null) || (this.idCotizacion != null && !this.idCotizacion.equals(other.idCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Cotizacion[ idCotizacion=" + idCotizacion + " ]";
    }

    public String getKeyModel() {
        if (this.idCotizacion != null) {
            return String.valueOf(this.idCotizacion);
        }

        return null;
    }

    public String getFechaSolicitudFormat() {
        if (fechasolicitud != null) {
            return DateUtil.formatShortDateTime(fechasolicitud);
        }
        return fechaSolicitudFormat;
    }

    public void setFechaSolicitudFormat(String fechaSolicitudFormat) {
        this.fechaSolicitudFormat = fechaSolicitudFormat;
    }

    public String getFechaVencimientoFormat() {
        if (fechavencimientosolicitud != null) {
            return DateUtil.formatShortDateTime(fechavencimientosolicitud);
        }
        return fechaSolicitudFormat;
    }

    public void setFechaVencimientoFormat(String fechaVencimientoFormat) {
        this.fechaVencimientoFormat = fechaVencimientoFormat;
    }

    public String getBackgroundColor() {
        if (this.fechavencimientosolicitud != null&&this.idEstadocotizacion!=null&&!this.idEstadocotizacion.getTipoEtiqueta().equals("Elaborada")) {
            if (DateUtil.fechaEsMenorActual(fechavencimientosolicitud)) {
                return "red";
            }
        }
        return "white";
    }
    
    public String getTextColor() {
        if (this.fechavencimientosolicitud != null&&this.idEstadocotizacion!=null&&!this.idEstadocotizacion.getTipoEtiqueta().equals("Elaborada")) {
            if (DateUtil.fechaEsMenorActual(fechavencimientosolicitud)) {
                return "white";
            }
        }
        return "black";
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDirigida() {
        return dirigida;
    }

    public void setDirigida(String dirigida) {
        this.dirigida = this.unescape(dirigida);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaEstimadaPedidoVenta() {
        return fechaEstimadaPedidoVenta;
    }

    public void setFechaEstimadaPedidoVenta(Date fechaEstimadaPedidoVenta) {
        this.fechaEstimadaPedidoVenta = fechaEstimadaPedidoVenta;
    }

    public Date getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(Date fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public Date getFechaEstimadaFacturacion() {
        return fechaEstimadaFacturacion;
    }

    public void setFechaEstimadaFacturacion(Date fechaEstimadaFacturacion) {
        this.fechaEstimadaFacturacion = fechaEstimadaFacturacion;
    }

    public String getTieneDescuento() {
        return tieneDescuento;
    }

    public void setTieneDescuento(String tieneDescuento) {
        this.tieneDescuento = tieneDescuento;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
        
    public String getFechaEstimadaFacturacionFormat() {
        if (fechaEstimadaFacturacion != null) {
            return DateUtil.formatShortDate(fechaEstimadaFacturacion);
        }
        return "";
    }
    public String getFechaEstimadaPedidoVentaFormat() {
        if (fechaEstimadaPedidoVenta != null) {
            return DateUtil.formatShortDate(fechaEstimadaPedidoVenta);
        }
        return "";
    }
    public String getFechaEstimadaEntregaFormat() {
        if (fechaEstimadaEntrega != null) {
            return DateUtil.formatShortDate(fechaEstimadaEntrega);
        }
        return "";
    }

    public String getInteligenciaCompetencia() {
        return inteligenciaCompetencia;
    }

    public void setInteligenciaCompetencia(String inteligenciaCompetencia) {
        this.inteligenciaCompetencia = inteligenciaCompetencia;
    }

    public String getInteligenciaRazon() {
        return inteligenciaRazon;
    }

    public void setInteligenciaRazon(String inteligenciaRazon) {
        this.inteligenciaRazon = inteligenciaRazon;
    }

    public String getInteligenciaObservaciones() {
        return inteligenciaObservaciones;
    }

    public void setInteligenciaObservaciones(String inteligenciaObservaciones) {
        this.inteligenciaObservaciones = inteligenciaObservaciones;
    }
    
    
    public static String escape(String input) {
       return translateAll(input, encoded, decoded);
    }
    
    public static String unescape(String input) {
       
        return translateAll(input, decoded, encoded);
   
    }
    
    
    public static String translateAll(String input, String[] patterns, String[] replacements) {
    String result = input;

    for (int i = 0; i < patterns.length; i++) {
        result = result.replaceAll(patterns[i], replacements[i]);
    }

    return result;
   }

     private static String convertirCadena(String cadena) throws UnsupportedEncodingException {
        return cadena == null ? "" : new String(cadena.getBytes("iso-8859-1"), "UTF-8");
    } 
}
