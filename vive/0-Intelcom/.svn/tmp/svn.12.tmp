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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "COTIZACION_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "CotizacionProducto.findAll", query = "SELECT c FROM CotizacionProducto c"),
    @NamedQuery(name = "CotizacionProducto.findByIdCotizacionProducto", query = "SELECT c FROM CotizacionProducto c WHERE c.idCotizacionProducto = :idCotizacionProducto"),
    @NamedQuery(name = "CotizacionProducto.findByIdproducto", query = "SELECT c FROM CotizacionProducto c WHERE c.idproducto = :idproducto"),
    @NamedQuery(name = "CotizacionProducto.findByCantidad", query = "SELECT c FROM CotizacionProducto c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "CotizacionProducto.findByIdCotizacion", query = "SELECT c FROM CotizacionProducto c WHERE c.idCotizacion.idCotizacion = :idCotizacion"),
    @NamedQuery(name = "CotizacionProducto.findByValorunitario", query = "SELECT c FROM CotizacionProducto c WHERE c.valorunitario = :valorunitario")})
public class CotizacionProducto extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id   
    @GeneratedValue(strategy=GenerationType.AUTO, generator="CotizacionProducto_seq_gen")
    @SequenceGenerator(name="CotizacionProducto_seq_gen", sequenceName="SEQ_COTIZACION_PRODUCTO", allocationSize = 1)
    @Column(name = "ID_COTIZACION_PRODUCTO")
    private Long idCotizacionProducto;
    
    @Column(name = "IDPRODUCTO")
    private Long idproducto;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @Column(name = "VALORUNITARIO")
    private Double valorunitario;
    
    
    @JoinColumn(name = "ID_COTIZACION", referencedColumnName = "ID_COTIZACION")
    @ManyToOne
    private Cotizacion idCotizacion;
    
    @Column(name = "NOMBREPRODUCTO")
    private String nombreProducto;

    public CotizacionProducto() {
    }

    public CotizacionProducto(Long idCotizacionProducto) {
        this.idCotizacionProducto = idCotizacionProducto;
    }

    public Long getIdCotizacionProducto() {
        return idCotizacionProducto;
    }

    public void setIdCotizacionProducto(Long idCotizacionProducto) {
        this.idCotizacionProducto = idCotizacionProducto;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorunitario() {
        return valorunitario;
    }

    public void setValorunitario(Double valorunitario) {
        this.valorunitario = valorunitario;
    }

    public Cotizacion getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Cotizacion idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacionProducto != null ? idCotizacionProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CotizacionProducto)) {
            return false;
        }
        CotizacionProducto other = (CotizacionProducto) object;
        if ((this.idCotizacionProducto == null && other.idCotizacionProducto != null) || (this.idCotizacionProducto != null && !this.idCotizacionProducto.equals(other.idCotizacionProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.CotizacionProducto[ idCotizacionProducto=" + idCotizacionProducto + " ]";
    }

    public String getKeyModel() {
        if (this.idCotizacionProducto != null)
            return String.valueOf(this.idCotizacionProducto);
        
        return null;
    }

}
