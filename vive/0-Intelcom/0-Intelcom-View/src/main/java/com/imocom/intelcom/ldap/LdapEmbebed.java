/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ldap;

import com.imocom.intelcom.view.beans.UsuarioPerfilFacesBean;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * Clase que se conecta al embebed LdapEmbebed de weblogic u actualiza la constraseña
 de un usuario
 *
 * @author Carlos Guzman
 */
public class LdapEmbebed {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LdapEmbebed.class);
    private String domain;
    private String ldapHost;
    private String searchBase;
    private String user;
    private String password;

    public LdapEmbebed(String domain, String ldapHost, String searchBase, String user, String password) {
        this.domain = domain;
        this.ldapHost = ldapHost;
        this.searchBase = searchBase;
        this.user = user;
        this.password = password;
    }

    private SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        return searchControls;
    }

    /**
     * Metodo que se autentica contra LdapEmbebed embebed
     *
     * @param user
     * @param pass
     * @return
     */
    private LdapContext authenticate() {
        LdapContext ctxGC = null;
        try {

            Hashtable<String, String> environment = new Hashtable<String, String>();
            //environment.put(LdapContext.CONTROL_FACTORIES, "com.sun.jndi.LdapEmbebed.ControlFactory");
            environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            environment.put(Context.PROVIDER_URL, ldapHost);
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, user);
            environment.put(Context.SECURITY_CREDENTIALS, password);
            //environment.put(Context.STATE_FACTORIES, "PersonStateFactory");
            //environment.put(Context.OBJECT_FACTORIES, "PersonObjectFactory");

            // This is the actual Authentication piece. Will throw javax.naming.AuthenticationException
            // if the users password is not correct. Other exceptions may include IO (server not found) etc.
            ctxGC = new InitialLdapContext(environment, null);
        } catch (NamingException ex) {
            logger.error("Error Actualizando Contraseñana,"+ex);
        } catch(Exception ex ){
             logger.error("Error Actualizando Contraseñana,"+ex);
        }
        return ctxGC;
    }

    public Boolean updatePasswordLdapUser(String uid,String password) {
        logger.info("Actualizando Contraseña,"+uid);
        LdapContext ctxGC = this.authenticate();
        if (ctxGC != null) {
            try {
                ModificationItem[] mods = new ModificationItem[1];
                Attribute mod0 = new BasicAttribute("userpassword", password);
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
                String userLdap= "uid="+uid+","+searchBase;
                ctxGC.modifyAttributes(userLdap, mods);
                return true;
            } catch (NamingException ex) {                
                 logger.error("Error Actualizando Contraseñana,"+ex);
            }catch(Exception ex){
                 logger.error("Error Actualizando Contraseñana,"+ex);
            }
        }
        return false;
    }
    
    public Boolean updateMailLdapUser(String uid,String mail) {
        logger.info("Actualizando Contraseña,"+uid);
        LdapContext ctxGC = this.authenticate();
        if (ctxGC != null) {
            try {
                ModificationItem[] mods = new ModificationItem[1];
                Attribute mod0 = new BasicAttribute("mail", mail);
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
                String userLdap= "uid="+uid+","+searchBase;
                ctxGC.modifyAttributes(userLdap, mods);
                return true;
            } catch (NamingException ex) {                
                 logger.error("Error Actualizando Contraseñana,"+ex);
            }catch(Exception ex){
                 logger.error("Error Actualizando Contraseñana,"+ex);
            }
        }
        return false;
    }
}
