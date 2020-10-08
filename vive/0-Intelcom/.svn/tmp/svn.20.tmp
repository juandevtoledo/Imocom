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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Abril 17, 2017
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Rafael Blanco (rblanco) - POINTMIND S.A.S. - rafael.blanco@pointmind.com
 * rafael.blanco@pointmind.com
 *
 */
@Entity
@Table(name = "PROYECTO")
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAllByNitCliente", query = "SELECT p FROM Proyecto p WHERE p.nitCliente = :nitCLiente")
})
public class Proyecto extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Proyecto_seq_gen")
    @SequenceGenerator(name = "Proyecto_seq_gen", sequenceName = "SEQ_PROYECTO", allocationSize = 1)
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;
    
    @Column(name = "NITCLIENTE")
    private String nitCliente;
    
    @Column(name = "NOMBRECLIENTE")
    private String nombreCliente;

    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "MONTO")
    private Double monto;

    @Column(name = "ANIO")
    private String anio;

    @Column(name = "PROYECTOPLAN")
    private String proyectoPlan;

    @Column(name = "OBSERVACION")
    private String observacion;
    
    @Column(name = "MONEDA")
    private String moneda;
    
    @Transient
    private String montoString;
    
    @Transient
    private String montoStringEdit;
    
    
    public Proyecto() {
    }

    public Proyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Proyecto[ idProyecto=" + idProyecto + " ]";
    }

    public String getKeyModel() {
        if (this.idProyecto != null) {
            return String.valueOf(this.idProyecto);
        }

        return null;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getProyectoPlan() {
        return proyectoPlan;
    }

    public void setProyectoPlan(String proyectoPlan) {
        this.proyectoPlan = proyectoPlan;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    private static final DecimalFormat DECIMAL_FORMAT = (DecimalFormat) NumberFormat.getNumberInstance( new Locale("es", "CO") );
    public String getMontoString() {
        if( monto == null ){
            return "";
        }
        return "$"+DECIMAL_FORMAT.format( monto );
    }

    public String getMontoStringEdit() {
        if( monto == null ){
            return "";
        }
        return String.valueOf( monto.longValue() );
    }

    public void setMontoStringEdit(String montoStringEdit) {
        this.montoStringEdit = montoStringEdit;
        if( montoStringEdit != null && montoStringEdit.trim().length() > 0 ){
            monto = new Double( montoStringEdit.trim() );
        }
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    
}
