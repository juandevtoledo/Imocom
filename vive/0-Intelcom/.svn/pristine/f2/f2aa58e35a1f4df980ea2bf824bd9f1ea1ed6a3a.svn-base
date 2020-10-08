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
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Mayo 7, 2017
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Rafael Blanco (rblanco) - POINTMIND S.A.S. - rafael.blanco@pointmind.com
 * rafael.blanco@pointmind.com
 *
 */
@Entity
@Table(name = "LEAD")
@NamedQueries({
    @NamedQuery(name = "Lead.findAll", query = "SELECT p FROM Lead p ")
})
public class Lead extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Lead_seq_gen")
    @SequenceGenerator(name = "Lead_seq_gen", sequenceName = "SEQ_LEAD", allocationSize = 1)
    @Column(name = "ID_LEAD")
    private Long idLead;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "ASESOR")
    private String asesor;
    
    @Column(name = "PRODUCTO_CODIGO")
    private String codigoProducto;
    
    @Column(name = "PRODUCTO_NOMBRE")
    private String nombreProducto;
    
    @Column(name = "PRODUCTO_LINEA")
    private String lineaProducto;
    
    @Column(name = "PRODUCTO_MARCA")
    private String marcaProducto;
    
    @Column(name = "PRODUCTO_MODELO")
    private String modeloProducto;
    
    @Column(name = "PRODUCTO_CANTIDAD")
    private Integer cantidadProducto;
    
    @Column(name = "CANAL")
    private String canal;
    
    @Column(name = "NIT")
    private String nit;
    
    @Column(name = "EMPRESA")
    private String empresa;
    
    @Column(name = "NOMBRE_CONTACTO")
    private String nombreContacto;
    
    @Column(name = "APELLIDO_CONTACTO")
    private String apellidoContacto;
    
    @Column(name = "CORREO_CONTACTO")
    private String correoContacto;
    
    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;
    
    @Column(name = "OBSERVACION")
    private String observacion;
    
    @Column(name = "ULTIMA_ACTUALIZACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaUltimaActualizacion;
    
    @Column(name = "USUARIO_ANTERIOR")
    private String usuarioAnterior;
    
    @Column(name = "USUARIO_CANCELA")
    private String usuarioCancela;
    
    @Column(name = "FECHA_CANCELA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCancela;
    
    @Column(name = "OBSERVACION_CANCELA")
    private String observacionCancela;
    
    
    @Column(name = "USUARIO_REASIGNA")
    private String usuarioReasigna;
    
    @Column(name = "FECHA_REASIGNA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaReasigna;
    
    @Column(name = "OBSERVACION_REASIGNA")
    private String observacionReasigna;
    
    @Transient
    private boolean clienteInvalido;
    
    @Column(name = "QUIEN_REPORTA")
    private String quienReporta;
    
    public Lead() {
    }

    public Lead(Long idLead) {
        this.idLead = idLead;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLead != null ? idLead.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lead)) {
            return false;
        }
        Lead other = (Lead) object;
        if ((this.idLead == null && other.idLead != null) || (this.idLead != null && !this.idLead.equals(other.idLead))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Lead[ idLead=" + idLead + " ]";
    }

    public String getKeyModel() {
        if (this.idLead != null) {
            return String.valueOf(this.idLead);
        }

        return null;
    }

    public Long getIdLead() {
        return idLead;
    }

    public void setIdLead(Long idLead) {
        this.idLead = idLead;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getApellidoContacto() {
        return apellidoContacto;
    }

    public void setApellidoContacto(String apellidoContacto) {
        this.apellidoContacto = apellidoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getLineaProducto() {
        return lineaProducto;
    }

    public void setLineaProducto(String lineaProducto) {
        this.lineaProducto = lineaProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public String getModeloProducto() {
        return modeloProducto;
    }

    public void setModeloProducto(String modeloProducto) {
        this.modeloProducto = modeloProducto;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }


    public String getUsuarioCancela() {
        return usuarioCancela;
    }

    public void setUsuarioCancela(String usuarioCancela) {
        this.usuarioCancela = usuarioCancela;
    }

    public Date getFechaCancela() {
        return fechaCancela;
    }

    public void setFechaCancela(Date fechaCancela) {
        this.fechaCancela = fechaCancela;
    }

    public String getObservacionCancela() {
        return observacionCancela;
    }

    public void setObservacionCancela(String observacionCancela) {
        this.observacionCancela = observacionCancela;
    }

    public String getUsuarioReasigna() {
        return usuarioReasigna;
    }

    public void setUsuarioReasigna(String usuarioReasigna) {
        this.usuarioReasigna = usuarioReasigna;
    }

    public Date getFechaReasigna() {
        return fechaReasigna;
    }

    public void setFechaReasigna(Date fechaReasigna) {
        this.fechaReasigna = fechaReasigna;
    }

    public String getObservacionReasigna() {
        return observacionReasigna;
    }

    public void setObservacionReasigna(String observacionReasigna) {
        this.observacionReasigna = observacionReasigna;
    }

    public String getUsuarioAnterior() {
        return usuarioAnterior;
    }

    public void setUsuarioAnterior(String usuarioAnterior) {
        this.usuarioAnterior = usuarioAnterior;
    }

    public boolean isClienteInvalido() {
        return clienteInvalido;
    }

    public void setClienteInvalido(boolean clienteInvalido) {
        this.clienteInvalido = clienteInvalido;
    }

    public String getQuienReporta() {
        return quienReporta;
    }

    public void setQuienReporta(String quienReporta) {
        this.quienReporta = quienReporta;
    }

    
}
