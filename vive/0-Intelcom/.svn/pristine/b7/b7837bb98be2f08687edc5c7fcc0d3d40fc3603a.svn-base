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
@Table(name = "ROL_RECURSO")
@NamedQueries({
    @NamedQuery(name = "RolRecurso.findAll", query = "SELECT r FROM RolRecurso r"),
    @NamedQuery(name = "RolRecurso.findByIdRolRecurso", query = "SELECT r FROM RolRecurso r WHERE r.idRolRecurso = :idRolRecurso")})
public class RolRecurso extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="RolRecurso_seq_gen")
    @SequenceGenerator(name="RolRecurso_seq_gen", sequenceName="SEQ_ROL_RECURSO", allocationSize = 1)
    @Column(name = "ID_ROL_RECURSO")
    private Long idRolRecurso;
    
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO")
    @ManyToOne(optional = false)
    private Recurso idRecurso;
    
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol idRol;

    public RolRecurso() {
    }

    public RolRecurso(Long idRolRecurso) {
        this.idRolRecurso = idRolRecurso;
    }

    public Long getIdRolRecurso() {
        return idRolRecurso;
    }

    public void setIdRolRecurso(Long idRolRecurso) {
        this.idRolRecurso = idRolRecurso;
    }

    public Recurso getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Recurso idRecurso) {
        this.idRecurso = idRecurso;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolRecurso != null ? idRolRecurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolRecurso)) {
            return false;
        }
        RolRecurso other = (RolRecurso) object;
        if ((this.idRolRecurso == null && other.idRolRecurso != null) || (this.idRolRecurso != null && !this.idRolRecurso.equals(other.idRolRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.RolRecurso[ idRolRecurso=" + idRolRecurso + " ]";
    }
    
    public String getKeyModel() {
        if (this.idRolRecurso != null)
            return String.valueOf(this.idRolRecurso);
        
        return null;
    }

}
