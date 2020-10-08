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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Nov 21, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@Entity
@Table(name = "SERVICIOS_EBS")
@NamedQueries({
    @NamedQuery(name = "ServiciosEbs.findAll", query = "SELECT s FROM ServiciosEbs s"),
    @NamedQuery(name = "ServiciosEbs.findByIdServicio", query = "SELECT s FROM ServiciosEbs s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "ServiciosEbs.findByServicio", query = "SELECT s FROM ServiciosEbs s WHERE s.servicio = :servicio"),
    @NamedQuery(name = "ServiciosEbs.findByDescripcion", query = "SELECT s FROM ServiciosEbs s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "ServiciosEbs.findByUrlWsdl", query = "SELECT s FROM ServiciosEbs s WHERE s.urlWsdl = :urlWsdl"),
    @NamedQuery(name = "ServiciosEbs.findByQnameServicio", query = "SELECT s FROM ServiciosEbs s WHERE s.qnameServicio = :qnameServicio"),
    @NamedQuery(name = "ServiciosEbs.findByInterfazEndpoint", query = "SELECT s FROM ServiciosEbs s WHERE s.interfazEndpoint = :interfazEndpoint"),
    @NamedQuery(name = "ServiciosEbs.findByMetodo", query = "SELECT s FROM ServiciosEbs s WHERE s.metodo = :metodo"),
    @NamedQuery(name = "ServiciosEbs.findByNumeroParametros", query = "SELECT s FROM ServiciosEbs s WHERE s.numeroParametros = :numeroParametros"),
    @NamedQuery(name = "ServiciosEbs.findByEstado", query = "SELECT s FROM ServiciosEbs s WHERE s.estado = :estado")})
public class ServiciosEbs extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private Long idServicio;
    
    @Basic(optional = false)
    @Column(name = "SERVICIO")
    private String servicio;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "URL_WSDL")
    private String urlWsdl;
    
    @Basic(optional = false)
    @Column(name = "QNAME_SERVICIO")
    private String qnameServicio;
    
    @Basic(optional = false)
    @Column(name = "INTERFAZ_ENDPOINT")
    private String interfazEndpoint;
        
    @Basic(optional = false)
    @Column(name = "NAMESPACE")
    private String namespace;
    
    @Basic(optional = false)
    @Column(name = "METODO")
    private String metodo;
    
    @Column(name = "NUMERO_PARAMETROS")
    private Short numeroParametros;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "TIPO_INTERFAZ")
    private String tipoInterfaz;
    
    @Column(name = "ENTIDAD_PETICION")
    private String entidadPeticion;

    public ServiciosEbs() {
    }

    public ServiciosEbs(Long idServicio) {
        this.idServicio = idServicio;
    }

    public ServiciosEbs(Long idServicio, String servicio, String urlWsdl, String qnameServicio, String interfazEndpoint, String metodo) {
        this.idServicio = idServicio;
        this.servicio = servicio;
        this.urlWsdl = urlWsdl;
        this.qnameServicio = qnameServicio;
        this.interfazEndpoint = interfazEndpoint;
        this.metodo = metodo;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlWsdl() {
        return urlWsdl;
    }

    public void setUrlWsdl(String urlWsdl) {
        this.urlWsdl = urlWsdl;
    }

    public String getQnameServicio() {
        return qnameServicio;
    }

    public void setQnameServicio(String qnameServicio) {
        this.qnameServicio = qnameServicio;
    }

    public String getInterfazEndpoint() {
        return interfazEndpoint;
    }

    public void setInterfazEndpoint(String interfazEndpoint) {
        this.interfazEndpoint = interfazEndpoint;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Short getNumeroParametros() {
        return numeroParametros;
    }

    public void setNumeroParametros(Short numeroParametros) {
        this.numeroParametros = numeroParametros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }   

    public String getTipoInterfaz() {
        return tipoInterfaz;
    }

    public void setTipoInterfaz(String tipoInterfaz) {
        this.tipoInterfaz = tipoInterfaz;
    }

    public String getEntidadPeticion() {
        return entidadPeticion;
    }

    public void setEntidadPeticion(String entidadPeticion) {
        this.entidadPeticion = entidadPeticion;
    }  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiciosEbs)) {
            return false;
        }
        ServiciosEbs other = (ServiciosEbs) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.ServiciosEbs[ idServicio=" + idServicio + " ]";
    }

    /**
     * 
     * @return 
     */
    public String getKeyModel() {
        if (this.idServicio != null)
            return String.valueOf(this.idServicio);
        
        return null;
    }

}
