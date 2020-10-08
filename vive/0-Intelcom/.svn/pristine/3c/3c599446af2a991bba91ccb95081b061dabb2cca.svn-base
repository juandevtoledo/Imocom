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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "ROL")
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdRol", query = "SELECT r FROM Rol r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Rol.findByRolNombre", query = "SELECT r FROM Rol r WHERE r.rolNombre = :rolNombre")})
public class Rol extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Rol_seq_gen")
    @SequenceGenerator(name="Rol_seq_gen", sequenceName="SEQ_ROL", allocationSize = 1)
    @Column(name = "ID_ROL")
    private Long idRol;
    
    @Basic(optional = false)
    @Column(name = "ROL_NOMBRE")
    private String rolNombre;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private Set<RolUsuario> rolUsuarioSet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private Set<RolMenu> rolMenuSet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private Set<RolRecurso> rolRecursoSet;

    public Rol() {
    }

    public Rol(Long idRol) {
        this.idRol = idRol;
    }

    public Rol(Long idRol, String rolNombre) {
        this.idRol = idRol;
        this.rolNombre = rolNombre;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Set<RolUsuario> getRolUsuarioSet() {
        return rolUsuarioSet;
    }

    public void setRolUsuarioSet(Set<RolUsuario> rolUsuarioSet) {
        this.rolUsuarioSet = rolUsuarioSet;
    }

    public Set<RolMenu> getRolMenuSet() {
        return rolMenuSet;
    }

    public void setRolMenuSet(Set<RolMenu> rolMenuSet) {
        this.rolMenuSet = rolMenuSet;
    }

    public Set<RolRecurso> getRolRecursoSet() {
        return rolRecursoSet;
    }

    public void setRolRecursoSet(Set<RolRecurso> rolRecursoSet) {
        this.rolRecursoSet = rolRecursoSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Rol[ idRol=" + idRol + " ]";
    }

    public String getKeyModel() {
        if (this.idRol != null)
            return String.valueOf(this.idRol);
        
        return null;
    }

}
