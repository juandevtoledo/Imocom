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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Oct 27, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@Entity
@Table(name = "MENU")
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m order by m.idMenu"),
    @NamedQuery(name = "Menu.findByIdMenu", query = "SELECT m FROM Menu m WHERE m.idMenu = :idMenu order by m.idMenu"),
    @NamedQuery(name = "Menu.findByMenuNombre", query = "SELECT m FROM Menu m WHERE m.menuNombre = :menuNombre order by m.idMenu"),
    @NamedQuery(name = "Menu.findByMenuAccion", query = "SELECT m FROM Menu m WHERE m.menuAccion = :menuAccion order by m.idMenu"),
    @NamedQuery(name = "Menu.findByMenuOrden", query = "SELECT m FROM Menu m WHERE m.menuOrden = :menuOrden order by m.idMenu"),
    @NamedQuery(name = "Menu.findByMenuIcon", query = "SELECT m FROM Menu m WHERE m.menuIcon = :menuIcon order by m.idMenu"),
    @NamedQuery(name = "Menu.findByMenuPadre", query = "SELECT m FROM Menu m WHERE m.menuPadre = :menuPadre order by m.idMenu"),
    @NamedQuery(name = "Menu.findByAllParentMenu", query = "SELECT m FROM Menu m WHERE m.menuPadre is null order by m.idMenu")})
public class Menu extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Menu_seq_gen")
    @SequenceGenerator(name = "Menu_seq_gen", sequenceName = "SEQ_MENU", allocationSize = 1)
    @Column(name = "ID_MENU")
    private Long idMenu;

    @Column(name = "MENU_NOMBRE")
    private String menuNombre;

    @Column(name = "MENU_ACCION")
    private String menuAccion;

    @Column(name = "MENU_ORDEN")
    private Long menuOrden;

    @Column(name = "MENU_ICON")
    private String menuIcon;

    @JoinColumn(name = "MENU_PADRE", referencedColumnName = "ID_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    @OrderBy("idMenu ASC")
    private Menu menuPadre;

    @OneToMany(mappedBy = "menuPadre", fetch = FetchType.LAZY)
    @OrderBy("idMenu ASC")
    private Set<Menu> menuHijoSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenu")
    private Set<RolMenu> rolMenuSet;

    public Menu() {
    }

    public Menu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public String getMenuNombre() {
        return menuNombre;
    }

    public void setMenuNombre(String menuNombre) {
        this.menuNombre = menuNombre;
    }

    public String getMenuAccion() {
        return menuAccion;
    }

    public void setMenuAccion(String menuAccion) {
        this.menuAccion = menuAccion;
    }

    public Long getMenuOrden() {
        return menuOrden;
    }

    public void setMenuOrden(Long menuOrden) {
        this.menuOrden = menuOrden;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Menu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

    public Set<Menu> getMenuHijoSet() {
        return menuHijoSet;
    }

    public void setMenuHijoSet(Set<Menu> menuHijoSet) {
        this.menuHijoSet = menuHijoSet;
    }

    public Set<RolMenu> getRolMenuSet() {
        return rolMenuSet;
    }

    public void setRolMenuSet(Set<RolMenu> rolMenuSet) {
        this.rolMenuSet = rolMenuSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Menu[ idMenu=" + idMenu + " ]";
    }

    public String getKeyModel() {
        if (this.idMenu != null) {
            return String.valueOf(this.idMenu);
        }

        return null;
    }

}
