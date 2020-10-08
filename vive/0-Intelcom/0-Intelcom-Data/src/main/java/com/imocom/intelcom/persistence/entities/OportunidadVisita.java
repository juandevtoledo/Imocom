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
import javax.persistence.Transient;

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
@Table(name = "OPORTUNIDAD_VISITA")
@NamedQueries({
    @NamedQuery(name = "OportunidadVisita.findAll", query = "SELECT o FROM OportunidadVisita o"),
    @NamedQuery(name = "OportunidadVisita.findByIdOportunidadVisita", query = "SELECT o FROM OportunidadVisita o WHERE o.idOportunidadVisita = :idOportunidadVisita"),
    @NamedQuery(name = "OportunidadVisita.findByIdOportunidad", query = "SELECT o FROM OportunidadVisita o WHERE o.idOportunidad = :idOportunidad"),
    @NamedQuery(name = "OportunidadVisita.findByIdVisita", query = "SELECT o FROM OportunidadVisita o WHERE o.idVisita = :idVisita"),
    @NamedQuery(name = "OportunidadVisita.findByIdVisitaId", query = "SELECT o FROM OportunidadVisita o WHERE o.idVisita.idvisita = :idVisita")
    })

public class OportunidadVisita extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="OportunidadVisita_seq_gen")
    @SequenceGenerator(name="OportunidadVisita_seq_gen", sequenceName="SEQ_OPORTUNIDAD_VISITA", allocationSize = 1)
    @Column(name = "ID_OPORTUNIDAD_VISITA")
    private Long idOportunidadVisita;
    
    @Column(name = "ID_OPORTUNIDAD")
    private Long idOportunidad;
    
    @Column(name = "RESULTADO_VISITA")
    private String resultadoVisita;

    
    @JoinColumn(name = "ID_VISITA", referencedColumnName = "IDVISITA")
    @ManyToOne
    private Visita idVisita;
    
    @Transient
    private String nombreOportunidad;
    
    @Transient
    private boolean selected;

    

    
    

    public OportunidadVisita() {
    }

    public OportunidadVisita(Long idOportunidadVisita) {
        this.idOportunidadVisita = idOportunidadVisita;
    }

    public Long getIdOportunidadVisita() {
        return idOportunidadVisita;
    }

    public void setIdOportunidadVisita(Long idOportunidadVisita) {
        this.idOportunidadVisita = idOportunidadVisita;
    }

    public Long getIdOportunidad() {
        return idOportunidad;
    }

    public void setIdOportunidad(Long idOportunidad) {
        this.idOportunidad = idOportunidad;
    }

    public Visita getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Visita idVisita) {
        this.idVisita = idVisita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOportunidadVisita != null ? idOportunidadVisita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OportunidadVisita)) {
            return false;
        }
        OportunidadVisita other = (OportunidadVisita) object;
        if ((this.idOportunidadVisita == null && other.idOportunidadVisita != null) || (this.idOportunidadVisita != null && !this.idOportunidadVisita.equals(other.idOportunidadVisita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.OportunidadVisita[ idOportunidadVisita=" + idOportunidadVisita + " ]";
    }

    public String getKeyModel() {
        if (this.idOportunidadVisita != null)
            return String.valueOf(this.idOportunidadVisita);
        
        return null;
    }

    public String getNombreOportunidad() {
        return nombreOportunidad;
    }

    public void setNombreOportunidad(String nombreOportunidad) {
        this.nombreOportunidad = nombreOportunidad;
    }

    public String getResultadoVisita() {
        return resultadoVisita;
    }

    public void setResultadoVisita(String resultadoVisita) {
        this.resultadoVisita = resultadoVisita;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    
    
}
