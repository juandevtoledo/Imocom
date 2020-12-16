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
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.toledo
 */
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByCodigo", query = "SELECT p FROM Producto p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByEstado", query = "SELECT p FROM Producto p WHERE p.estado = :estado"),
    @NamedQuery(name = "Producto.findByCotizable", query = "SELECT p FROM Producto p WHERE p.cotizable = :cotizable"),
    @NamedQuery(name = "Producto.findByCatalogo", query = "SELECT p FROM Producto p WHERE p.catalogo = :catalogo")})
public class Producto extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Producto_seq_gen")
    @SequenceGenerator(name = "Producto_seq_gen", sequenceName = "SEQ_PRODUCTO", allocationSize = 1)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "COTIZABLE")
    private BigInteger cotizable;
    @Column(name = "CATALOGO")
    private BigInteger catalogo;
    @JoinColumn(name = "ID_MODELO", referencedColumnName = "ID_MODELO")
    @ManyToOne(optional = false)
    private Modelo idModelo;
    @JoinColumn(name = "ID_MONEDA", referencedColumnName = "ID_MONEDA")
    @ManyToOne(optional = false)
    private Moneda idMoneda;
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne(optional = false)
    private TipoProducto idTipo;
    @Column(name = "INCOTERM")
    private String incoterm;
    @Transient
    private String cotizableString;
    @Transient
    private String catalogoString;

    public Producto() {
    }

    public Producto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getCotizable() {
        return cotizable;
    }

    public void setCotizable(BigInteger cotizable) {
        this.cotizable = cotizable;
    }

    public BigInteger getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(BigInteger catalogo) {
        this.catalogo = catalogo;
    }

    public Modelo getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Modelo idModelo) {
        this.idModelo = idModelo;
    }

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public TipoProducto getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoProducto idTipo) {
        this.idTipo = idTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Producto[ idProducto=" + idProducto + " ]";
    }

    public String getKeyModel() {
        if (this.idProducto != null) {
            return String.valueOf(this.idProducto);
        }

        return null;
    }

    public String getCotizableString() {
        if (this.cotizable.intValue() == 1) {
            return "SI";
        } else {
            return "NO";
        }
    }

    public void setCotizableString(String cotizableString) {
        this.cotizableString = cotizableString;
    }

    public String getCatalogoString() {
        if (this.catalogo.intValue() == 1) {
            return "SI";
        } else {
            return "NO";
        }
    }

    public void setCatalogoString(String catalogoString) {
        this.catalogoString = catalogoString;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }
    
    

}
