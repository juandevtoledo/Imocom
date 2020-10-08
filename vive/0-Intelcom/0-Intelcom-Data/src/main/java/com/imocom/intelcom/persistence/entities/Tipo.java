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
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Oct 27, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Entity
@Table(name = "TIPO")
@NamedQueries({
    @NamedQuery(name = "Tipo.findAll", query = "SELECT t FROM Tipo t order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByIdTipo", query = "SELECT t FROM Tipo t WHERE t.idTipo = :idTipo order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByTipoNombre", query = "SELECT t FROM Tipo t WHERE t.tipoNombre = :tipoNombre order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByTipoEtiqueta", query = "SELECT t FROM Tipo t WHERE t.tipoEtiqueta = :tipoEtiqueta order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByTipoValor", query = "SELECT t FROM Tipo t WHERE t.tipoValor = :tipoValor order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByTipoNombreTipoEtiqueta", query = "SELECT t FROM Tipo t WHERE t.tipoNombre = :tipoNombre and t.tipoEtiqueta = :tipoEtiqueta order by t.idTipo"),
    @NamedQuery(name = "Tipo.findByTipoNombreValorTipopadre", query = "SELECT t FROM Tipo t WHERE t.tipoNombre = :tipoNombre and t.tipoValor = :tipoValor and t.tipoPadre = :tipoPadre"),
    @NamedQuery(name = "Tipo.findByTipoNombreValor", query = "SELECT t FROM Tipo t WHERE t.tipoNombre = :tipoNombre and t.tipoValor = :tipoValor"),
    @NamedQuery(name = "Tipo.findByTipoPadre", query = "SELECT t FROM Tipo t WHERE t.tipoPadre = :tipoPadre order by t.idTipo")})
public class Tipo extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Tipo_seq_gen")
    @SequenceGenerator(name="Tipo_seq_gen", sequenceName="SEQ_TIPO", allocationSize = 1)
    @Column(name = "ID_TIPO")
    private Long idTipo;
    
    @Column(name = "TIPO_NOMBRE")
    private String tipoNombre;
    
    @Column(name = "TIPO_ETIQUETA")
    private String tipoEtiqueta;
    
    @Column(name = "TIPO_VALOR")
    private String tipoValor;
    
    @Column(name = "TIPO_PADRE")
    private Long tipoPadre;
    
    @OneToMany(mappedBy = "idTipomoneda")
    private Set<Cotizacion> cotizacionSet;
    
    @OneToMany(mappedBy = "idIncoterm")
    private Set<Cotizacion> cotizacionSet1;
    
    @OneToMany(mappedBy = "idEstadocotizacion")
    private Set<Cotizacion> cotizacionSet2;
    
    @OneToMany(mappedBy = "idtipooferta")
    private Set<Cotizacion> cotizacionSet3;
    
    @OneToMany(mappedBy = "idEstado")
    private Set<Visita> visitaSet;
    
    @OneToMany(mappedBy = "idTipovisita")
    private Set<Visita> visitaSet1;

    public Tipo() {
    }

    public Tipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public String getTipoEtiqueta() {
        return tipoEtiqueta;
    }

    public void setTipoEtiqueta(String tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Long getTipoPadre() {
        return tipoPadre;
    }

    public void setTipoPadre(Long tipoPadre) {
        this.tipoPadre = tipoPadre;
    }

    public Set<Cotizacion> getCotizacionSet() {
        return cotizacionSet;
    }

    public void setCotizacionSet(Set<Cotizacion> cotizacionSet) {
        this.cotizacionSet = cotizacionSet;
    }

    public Set<Cotizacion> getCotizacionSet1() {
        return cotizacionSet1;
    }

    public void setCotizacionSet1(Set<Cotizacion> cotizacionSet1) {
        this.cotizacionSet1 = cotizacionSet1;
    }

    public Set<Cotizacion> getCotizacionSet2() {
        return cotizacionSet2;
    }

    public void setCotizacionSet2(Set<Cotizacion> cotizacionSet2) {
        this.cotizacionSet2 = cotizacionSet2;
    }

    public Set<Cotizacion> getCotizacionSet3() {
        return cotizacionSet3;
    }

    public void setCotizacionSet3(Set<Cotizacion> cotizacionSet3) {
        this.cotizacionSet3 = cotizacionSet3;
    }

    public Set<Visita> getVisitaSet() {
        return visitaSet;
    }

    public void setVisitaSet(Set<Visita> visitaSet) {
        this.visitaSet = visitaSet;
    }

    public Set<Visita> getVisitaSet1() {
        return visitaSet1;
    }

    public void setVisitaSet1(Set<Visita> visitaSet1) {
        this.visitaSet1 = visitaSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipo)) {
            return false;
        }
        Tipo other = (Tipo) object;
        if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Tipo[ idTipo=" + idTipo + " ]";
    }
    
    public String getKeyModel() {
        if (this.idTipo != null)
            return String.valueOf(this.idTipo);
        
        return null;
    }

}
