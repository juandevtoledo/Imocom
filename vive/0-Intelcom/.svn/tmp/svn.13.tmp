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
import javax.persistence.FetchType;
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
@Table(name = "INVITADO_VISITA")
@NamedQueries({
    @NamedQuery(name = "InvitadoVisita.findAll", query = "SELECT i FROM InvitadoVisita i"),
    @NamedQuery(name = "InvitadoVisita.findByIdInvitadoVisita", query = "SELECT i FROM InvitadoVisita i WHERE i.idInvitadoVisita = :idInvitadoVisita")})
public class InvitadoVisita extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="InvitadoVisita_seq_gen")
    @SequenceGenerator(name="InvitadoVisita_seq_gen", sequenceName="SEQ_INVITADO_VISITA", allocationSize = 1)
    @Column(name = "ID_INVITADO_VISITA")
    private Long idInvitadoVisita;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario idUsuario;
    
    @JoinColumn(name = "ID_VISITA", referencedColumnName = "IDVISITA")
    @ManyToOne
    private Visita idVisita;
    
    public InvitadoVisita() {
    }
    
    public InvitadoVisita(Long idInvitadoVisita) {
        this.idInvitadoVisita = idInvitadoVisita;
    }
    
    public Long getIdInvitadoVisita() {
        return idInvitadoVisita;
    }
    
    public void setIdInvitadoVisita(Long idInvitadoVisita) {
        this.idInvitadoVisita = idInvitadoVisita;
    }
    
    public Usuario getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
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
        hash += (idInvitadoVisita != null ? idInvitadoVisita.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvitadoVisita)) {
            return false;
        }
        InvitadoVisita other = (InvitadoVisita) object;
        if ((this.idInvitadoVisita == null && other.idInvitadoVisita != null) || (this.idInvitadoVisita != null && !this.idInvitadoVisita.equals(other.idInvitadoVisita))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.InvitadoVisita[ idInvitadoVisita=" + idInvitadoVisita + " ]";
    }
    
    public String getKeyModel() {
        if (this.idInvitadoVisita != null)
            return String.valueOf(this.idInvitadoVisita);
        
        return null;
    }
    
}
