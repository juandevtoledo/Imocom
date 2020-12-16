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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "ROL_USUARIO")
@NamedQueries({
    @NamedQuery(name = "RolUsuario.findAll", query = "SELECT r FROM RolUsuario r"),
    @NamedQuery(name = "RolUsuario.findByIdRolUsuario", query = "SELECT r FROM RolUsuario r WHERE r.idRolUsuario = :idRolUsuario"),
    @NamedQuery(name = "RolUsuario.findByRolUno", query = "SELECT r FROM RolUsuario r WHERE r.idRol.idRol = 1 ORDER BY r.usuario.usuario ASC"),
    @NamedQuery(name = "RolUsuario.findByIdUsuario", query = "SELECT r FROM RolUsuario r WHERE r.idUsuario = :idUsuario")})
public class RolUsuario extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="RolUsuario_seq_gen")
    @SequenceGenerator(name="RolUsuario_seq_gen", sequenceName="SEQ_ROL_USUARIO", allocationSize = 1)
    @Column(name = "ID_ROL_USUARIO")
    private Long idRolUsuario;
    
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private long idUsuario;
    
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol idRol;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public RolUsuario() {
    }

    public RolUsuario(Long idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public RolUsuario(Long idRolUsuario, long idUsuario) {
        this.idRolUsuario = idRolUsuario;
        this.idUsuario = idUsuario;
    }

    public Long getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(Long idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolUsuario != null ? idRolUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolUsuario)) {
            return false;
        }
        RolUsuario other = (RolUsuario) object;
        if ((this.idRolUsuario == null && other.idRolUsuario != null) || (this.idRolUsuario != null && !this.idRolUsuario.equals(other.idRolUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.RolUsuario[ idRolUsuario=" + idRolUsuario + " ]";
    }
    
    public String getKeyModel() {
        if (this.idRolUsuario != null)
            return String.valueOf(this.idRolUsuario);
        
        return null;
    }

}
