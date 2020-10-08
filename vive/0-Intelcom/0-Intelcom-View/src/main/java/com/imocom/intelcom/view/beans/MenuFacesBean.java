/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*/
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.persistence.entities.Menu;
import com.imocom.intelcom.persistence.entities.Rol;
import com.imocom.intelcom.persistence.entities.RolMenu;
import com.imocom.intelcom.services.interfaces.ILeadServiceLocal;
import com.imocom.intelcom.services.interfaces.IMenuServiceLocal;
import com.imocom.intelcom.services.interfaces.IRolesServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.util.utility.Constants;
import static com.imocom.intelcom.util.utility.Constants.REDIRECT_PARAM;
import com.imocom.intelcom.view.AbstractFacesBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * <strong>Aplicación</strong>     : IMOCOM Interoperabilidad GEL-XML.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Aug 25, 2014
 * <br/><br/>
 * <strong>Target</strong>         : Menu component to presentation layer (JSF Managed Bean)
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@ManagedBean
@SessionScoped
public class MenuFacesBean extends AbstractFacesBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(MenuFacesBean.class);
    private static final long serialVersionUID = 1L;
    
    private MenuModel menuModel;
    
    @EJB
    private IMenuServiceLocal iMenuService;
    
    @EJB
    private IRolesServiceLocal iRol;
    
    @EJB
    private ILeadServiceLocal iLeadServiceLocal;
    
    
    /**
     * Build menu items in order to user logged.
     */
    public void build() {
        
        menuModel = new DefaultMenuModel();
        try {
            List<Menu> menuToDisplay = new ArrayList<Menu>();
            Set<String> rolUsers =  userSession.getUserSessionRoles();
            for(String r : rolUsers){
                Rol role = iRol.findByUniqueName(r);
                Set<RolMenu> roleMenuList = role.getRolMenuSet();
                for(RolMenu rM : roleMenuList){
                    menuToDisplay.add(rM.getIdMenu());
                }
            }            
            
            for (Menu menu : menuToDisplay) {
                List<Menu> items = iMenuService.findByAllParentMenuHijos(menu);
                if (items != null && !items.isEmpty()) {
                    
                    DefaultSubMenu submenu = new DefaultSubMenu();
                    submenu.setId(menu.getKeyModel());
                    submenu.setLabel(menu.getMenuNombre());
                        submenu.setIcon(menu.getMenuIcon());
                    
                    for (Menu item : items) {
                        
                        DefaultMenuItem menuItem = new DefaultMenuItem();
                        menuItem.setId(item.getKeyModel());
                        menuItem.setImmediate(true);
                        menuItem.setAjax(false);
                        menuItem.setCommand(Constants.DEFAULT_ITEMACTION);
                        menuItem.setParam(REDIRECT_PARAM, item.getMenuAccion());
                        menuItem.setIcon(item.getMenuIcon());
                        menuItem.setValue(item.getMenuNombre());
                        
                        submenu.addElement(menuItem);
                    }
                    menuModel.addElement(submenu);
                } else {
                    
                    int leadas = 0;
                    if( menu.getMenuNombre().equalsIgnoreCase("Leads") ){
                        leadas = iLeadServiceLocal.contarLeadAsesor( userSession.getUserLogin() );
                    }
                    
                    DefaultSubMenu submenu = new DefaultSubMenu();
                    DefaultMenuItem menuItem = new DefaultMenuItem();
                    menuItem.setId(menu.getKeyModel());
                    menuItem.setImmediate(true);
                    menuItem.setAjax(false);
                    menuItem.setCommand(Constants.DEFAULT_ITEMACTION);
                    menuItem.setParam(REDIRECT_PARAM, menu.getMenuAccion());
                    menuItem.setIcon(menu.getMenuIcon());                    
                    if( leadas > 0 ){
                        menuItem.setValue(menu.getMenuNombre().concat( " ("+ leadas +")" ));
                        menuItem.setStyle("color:#FF0000");
                    }else{
                        menuItem.setValue(menu.getMenuNombre());
                    }
                    menuItem.setValue(menu.getMenuNombre().concat( leadas > 0 ? " ("+ leadas +")" : ""));
                    
                    submenu.addElement(menuItem);
                    
                    menuModel.addElement(submenu);
                    
                }
                                
            }
        } catch (ServiceException ex) {
            logger.log(ex.getLevel(), ex.getMessage());
        }
    }
    
    /**
     *
     * @return
     */
    public String redirect() {
        String redirect = getParameterMap().get(REDIRECT_PARAM);
        return super.redirectTo(redirect);
    }
    
    
    /**
     * @return the menuModel {@link MenuModel}
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }
    
    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }
}
