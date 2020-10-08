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
@Table(name = "RECURSO")
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r"),
    @NamedQuery(name = "Recurso.findByIdRecurso", query = "SELECT r FROM Recurso r WHERE r.idRecurso = :idRecurso"),
    @NamedQuery(name = "Recurso.findByRecursoNombre", query = "SELECT r FROM Recurso r WHERE r.recursoNombre = :recursoNombre")})
public class Recurso extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Recurso_seq_gen")
    @SequenceGenerator(name="Recurso_seq_gen", sequenceName="SEQ_RECURSO", allocationSize = 1)
    @Column(name = "ID_RECURSO")
    private Long idRecurso;
    
    @Column(name = "RECURSO_NOMBRE")
    private String recursoNombre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRecurso")
    private Set<RolRecurso> rolRecursoSet;

    public Recurso() {
    }

    public Recurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getRecursoNombre() {
        return recursoNombre;
    }

    public void setRecursoNombre(String recursoNombre) {
        this.recursoNombre = recursoNombre;
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
        hash += (idRecurso != null ? idRecurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.idRecurso == null && other.idRecurso != null) || (this.idRecurso != null && !this.idRecurso.equals(other.idRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Recurso[ idRecurso=" + idRecurso + " ]";
    }

    public String getKeyModel() {
        if (this.idRecurso != null)
            return String.valueOf(this.idRecurso);
        
        return null;
    }

}
