/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import com.imocom.intelcom.ldap.LdapEmbebed;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.services.interfaces.IUsuarioServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import com.imocom.intelcom.view.AbstractFacesBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author rc
 */
@ManagedBean
@ViewScoped
public class UsuarioPerfilFacesBean extends AbstractFacesBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioPerfilFacesBean.class);
    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    private String password;
    private String passwordConfirmation;

    @EJB
    private IUsuarioServiceLocal iUsuarioServiceLocal;

    @Override
    protected void build() {
        try {
            usuario = iUsuarioServiceLocal.findByUsuario(userSession.getUserLogin());
        } catch (ServiceException ex) {
            logger.error(ex.getMessage());
        }

    }

    public void actualizarPasswordAction(ActionEvent actionEvent) {
        //Validamos que los campos de pasword sean iguales
        if (validarPassword() == null) {
            //Se valida que el password tenga algun valor
            if (getPassword() != null && getPassword().trim().length() > 0) {
                String domain = userSession.getLdapdomain();
                String ldapHost = userSession.getLdapHost();
                String searchBase = "ou=people,ou=myrealm," + domain;
                String user = userSession.getLdapUser();
                String passwordLdap = userSession.getLdappassword();
                LdapEmbebed ldap = new LdapEmbebed(domain, ldapHost, searchBase, user, passwordLdap);
                ldap.updatePasswordLdapUser(userSession.getUserLogin(), getPassword());
                performInfoMessages("Cambio de clave se ha realizado exitosamente");
            }
        } else {
            //Se envia el mensaje que indica que los campos password no son iguales.
            performErrorMessages(validarPassword());
            return;
        }

    }
    public void actualizarCorreo() {
     
                String domain = userSession.getLdapdomain();
                String ldapHost = userSession.getLdapHost();
                String searchBase = "ou=people,ou=myrealm," + domain;
                String user = userSession.getLdapUser();
                String passwordLdap = userSession.getLdappassword();
                LdapEmbebed ldap = new LdapEmbebed(domain, ldapHost, searchBase, user, passwordLdap);
                ldap.updateMailLdapUser(userSession.getUserLogin(),usuario.getCorreo());
                         

    }

    public void actualizarAction(ActionEvent actionEvent) {
        try {

            iUsuarioServiceLocal.updateUsuario(usuario);
            actualizarCorreo();
            performInfoMessages("La actualización de datos del perfil se ha realizado exitosamente");
        } catch (ServiceException ex) {
            performErrorMessages("Ha ocurrido un problema con la actualización de datos del perfil");
            logger.error(ex.getMessage());
        }

    }

    /**
     * Metodo que se encarga de validar los 2 campos de password y enviar el
     * mensaje correspondiente a la pantalla del usuario
     *
     * @return Cuando retorna null indica que los campos son iguales.
     */
    private String validarPassword() {
        boolean iguales = true;
        if (getPassword() != null) {
            if (!getPassword().equals(getPasswordConfirmation())) {
                iguales = false;
            }
        }
        if (getPasswordConfirmation() != null) {
            if (!getPasswordConfirmation().equals(getPassword())) {
                iguales = false;
            }
        }
        if (!iguales) {
            return "los valores de Contraseña y Confirmación de contraseña deben ser iguales";
        }

        return null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
