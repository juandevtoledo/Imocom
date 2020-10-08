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
@Table(name = "ROL_MENU")
@NamedQueries({
    @NamedQuery(name = "RolMenu.findAll", query = "SELECT r FROM RolMenu r"),
    @NamedQuery(name = "RolMenu.findByIdRolMenu", query = "SELECT r FROM RolMenu r WHERE r.idRolMenu = :idRolMenu")})
public class RolMenu extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="RolMenu_seq_gen")
    @SequenceGenerator(name="RolMenu_seq_gen", sequenceName="SEQ_ROL_MENU", allocationSize = 1)
    @Column(name = "ID_ROL_MENU")
    private Long idRolMenu;
    
    @JoinColumn(name = "ID_MENU", referencedColumnName = "ID_MENU")
    @ManyToOne(optional = false)
    private Menu idMenu;
    
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol idRol;

    public RolMenu() {
    }

    public RolMenu(Long idRolMenu) {
        this.idRolMenu = idRolMenu;
    }

    public Long getIdRolMenu() {
        return idRolMenu;
    }

    public void setIdRolMenu(Long idRolMenu) {
        this.idRolMenu = idRolMenu;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
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
        hash += (idRolMenu != null ? idRolMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolMenu)) {
            return false;
        }
        RolMenu other = (RolMenu) object;
        if ((this.idRolMenu == null && other.idRolMenu != null) || (this.idRolMenu != null && !this.idRolMenu.equals(other.idRolMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.RolMenu[ idRolMenu=" + idRolMenu + " ]";
    }
    
    public String getKeyModel() {
        if (this.idRolMenu != null)
            return String.valueOf(this.idRolMenu);
        
        return null;
    }

}
