/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 *
 *
 */
package com.imocom.intelcom.view.security;

import com.imocom.intelcom.persistence.entities.Parametros;
import com.imocom.intelcom.persistence.entities.Rol;
import com.imocom.intelcom.persistence.entities.RolRecurso;
import com.imocom.intelcom.persistence.entities.Tipo;
import com.imocom.intelcom.persistence.entities.Usuario;
import com.imocom.intelcom.services.interfaces.IRolesServiceLocal;
import com.imocom.intelcom.services.interfaces.IServicioGenericoLocal;
import com.imocom.intelcom.services.interfaces.ITipoServiceLocal;
import com.imocom.intelcom.services.interfaces.IUsuarioServiceLocal;
import com.imocom.intelcom.services.util.ServiceException;
import static com.imocom.intelcom.util.utility.Constants.LDAP_DOMAIN;
import static com.imocom.intelcom.util.utility.Constants.LDAP_HOST;
import static com.imocom.intelcom.util.utility.Constants.LDAP_PASSWORD;
import static com.imocom.intelcom.util.utility.Constants.LDAP_USER;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_NAMESPACE_ID;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_SERVICE_NAME_ID;
import static com.imocom.intelcom.util.utility.Constants.MIDDLEWARE_WSDL_ID;
import static com.imocom.intelcom.util.utility.Constants.RIDC_PASSWORD;
import static com.imocom.intelcom.util.utility.Constants.RIDC_URL;
import static com.imocom.intelcom.util.utility.Constants.RIDC_USER;
import static com.imocom.intelcom.util.utility.Constants.ROLES_EJB_JNDI;
import static com.imocom.intelcom.util.utility.Constants.TIPO_ESTADO_EVENTO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_PAIS;
import static com.imocom.intelcom.util.utility.Constants.TIPO_VALOR_ESTADO_EVENTO_EJECUTADO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_VALOR_ESTADO_EVENTO_PROGRAMADO;
import static com.imocom.intelcom.util.utility.Constants.TIPO_VALOR_PAIS_COLOMBIA;
import static com.imocom.intelcom.util.utility.Constants.USUARIO_EJB_JNDI;
import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.view.auth.JAASHelper;
import com.imocom.intelcom.view.util.ViewException;
import com.imocom.intelcom.ws.client.IntelcomWSClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import weblogic.security.principal.WLSGroupImpl;
import weblogic.security.principal.WLSUserImpl;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong> : Component to handle session user.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@ManagedBean
@SessionScoped
public class UserSession implements Serializable {

    private static final Logger logger = Logger.getLogger(UserSession.class);

    private String _userLogin;

    private String _userDesc;

    private Set<String> _userSessionRoles;

    private Set<String> _userResourcesAllow;

    private String _remoteAddr;

    private boolean _loggedIn;

    private boolean _mobile;

    private String _pageTitle;

    private final JAASHelper _helper;

    private Usuario _usuario;

    private List<String> _opcionesMenuEmergente;

    private IntelcomWSClient clientWs;
    
    private Tipo tipoPais_Colombia;
    
    private Tipo tipoEvento_Programado;
    private Tipo tipoEvento_Ejecutado;
    

    @EJB(name = ROLES_EJB_JNDI)
    private IRolesServiceLocal iRolesService;

    @EJB(name = USUARIO_EJB_JNDI)
    private IUsuarioServiceLocal iUsuarioService;

    @EJB
    private IServicioGenericoLocal<Long, Parametros> iServicioGenerico;
    
    @EJB
    private ITipoServiceLocal iTipoService;
    

    /**
     * URL DE Conexion al content Server
     */
    private String _ridcUrl;
    /**
     * Password de conexión al content Server
     */
    private String _ridcPassword;
    /**
     * User de conexión al content Server
     */
    private String _ridcUser;
    /**
     * Ruta de la imagen del archivo a mostrar
     */
    private String image;
    
     /**
     * URL DE Conexion al content Server
     */
    private String _ldapHost;
    /**
     * Password de conexión al content Server
     */
    private String _ldapdomain;
    /**
     * User de conexión al content Server
     */
    private String _ldapUser;
    /**
     * Ruta de la imagen del archivo a mostrar
     */
    private String _ldappassword;

    /**
     *
     */
    public UserSession() {
        _helper = new JAASHelper();
        _opcionesMenuEmergente = new ArrayList<String>();
        _opcionesMenuEmergente.add("Salir");
    }

    public String getRidcUrl() {
        return _ridcUrl;
    }

    public void setRidcUrl(String _ridcUrl) {
        this._ridcUrl = _ridcUrl;
    }

    public String getRidcPassword() {
        return _ridcPassword;
    }

    public void setRidcPassword(String _ridcPassword) {
        this._ridcPassword = _ridcPassword;
    }

    public String getRidcUser() {
        return _ridcUser;
    }

    public void setRidcUser(String _ridcUser) {
        this._ridcUser = _ridcUser;
    }

    /**
     *
     * @return
     */
    public String getUserLogin() {
        return _userLogin;
    }

    /**
     *
     * @param _userLogin
     */
    public void setUserLogin(String _userLogin) {
        this._userLogin = _userLogin;
    }

    /**
     *
     * @return
     */
    public Set<String> getUserSessionRoles() {
        return _userSessionRoles;
    }

    /**
     *
     * @param _userSessionRoles
     */
    public void setUserSessionRoles(Set<String> _userSessionRoles) {
        this._userSessionRoles = _userSessionRoles;
    }

    /**
     *
     * @return
     */
    public String getPageTitle() {
        return _pageTitle;
    }

    /**
     *
     * @param _pageTitle
     */
    public void setPageTitle(String _pageTitle) {
        this._pageTitle = _pageTitle;
    }

    /**
     *
     * @param role
     * @return
     */
    public boolean isUserInRole(String role) {

        if (_userSessionRoles != null && _userSessionRoles.isEmpty()) {
            return _userSessionRoles.contains(role);
        }

        return false;
    }

    /**
     *
     * @return
     */
    public Set<String> getUserResourcesAllow() {
        return _userResourcesAllow;
    }

    /**
     *
     * @param _userResourcesAllow
     */
    public void setUserResourcesAllow(Set<String> _userResourcesAllow) {
        this._userResourcesAllow = _userResourcesAllow;
    }

    /**
     *
     * @return
     */
    public String getRemoteAddr() {
        return _remoteAddr;
    }

    /**
     *
     * @param _remoteAddr
     */
    public void setRemoteAddr(String _remoteAddr) {
        this._remoteAddr = _remoteAddr;
    }

    /**
     *
     * @return
     */
    public boolean isLoggedIn() {
        return _loggedIn;
    }

    /**
     *
     * @param _loggedIn
     */
    public void setLoggedIn(boolean _loggedIn) {
        this._loggedIn = _loggedIn;
    }

    /**
     *
     * @param username
     * @param password
     * @throws ViewException
     */
    public void validate(String username, String password) throws ViewException, java.lang.LinkageError {

        try {
            logger.info("Encriptando credenciales");
            //String encryptPasswd = iUsuarioService.getEncriptedPasswd(password);
            String encryptPasswd=password;
            logger.info("Autenticando usando JAAS Helper...");
            if (_helper.authenticate(username, encryptPasswd)) {
                loadUserData(_helper.getSubject());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            throw new ViewException("Se ha presentado un error durante la validación del usuario: " + username, Level.WARN);
        }/* catch (java.lang.LinkageError ex){
         logger.error("Error en proceso de Autenticacion Exception: "+ex.getMessage());
         throw new ViewException("Se ha presentado un error durante la validación del usuario: " + username, Level.WARN);
         }*/

    }

    /**
     *
     * @throws ViewException
     */
    private void loadUserData(Subject subject) throws ViewException {

        _userSessionRoles = new HashSet<String>();
        _userResourcesAllow = new HashSet<String>();
        try {
            Set<Principal> principals = subject.getPrincipals();
            if (principals != null && !principals.isEmpty()) {
                for (Object p : principals) {
                    if (p instanceof WLSGroupImpl) {
                        String rolename = ((WLSGroupImpl) p).getName();
                        _userSessionRoles.add(rolename);
                        Rol rol = iRolesService.findByUniqueName(rolename);

                        for (RolRecurso rr : rol.getRolRecursoSet()) {
                            _userResourcesAllow.add(rr.getIdRecurso().getRecursoNombre());
                        }
                    } else if (p instanceof WLSUserImpl) {
                        _userLogin = ((WLSUserImpl) p).getName();
                        logger.info("Obteniendo informacion del usuario: " + _userLogin);
                        _usuario = iUsuarioService.findByUsuario(_userLogin);

                    }
                }
                _remoteAddr = getRemoteAddr();
            }
            _loggedIn = true;

            //cargar el cliente de MiddleWare para consumo de servicios
            try {
                String wsdlUrl = iServicioGenerico.findEntityById(MIDDLEWARE_WSDL_ID, Parametros.class).getValor();
                String namespace = iServicioGenerico.findEntityById(MIDDLEWARE_NAMESPACE_ID, Parametros.class).getValor();
                String serviceName = iServicioGenerico.findEntityById(MIDDLEWARE_SERVICE_NAME_ID, Parametros.class).getValor();
                clientWs = new IntelcomWSClient(wsdlUrl, namespace, serviceName);

                this._ridcUrl = iServicioGenerico.findEntityById(RIDC_URL, Parametros.class).getValor();
                this._ridcUser = iServicioGenerico.findEntityById(RIDC_USER, Parametros.class).getValor();
                this._ridcPassword = iServicioGenerico.findEntityById(RIDC_PASSWORD, Parametros.class).getValor();
                
                this._ldapHost = iServicioGenerico.findEntityById(LDAP_HOST, Parametros.class).getValor();
                this._ldapdomain = iServicioGenerico.findEntityById(LDAP_DOMAIN, Parametros.class).getValor();
                this._ldapUser = iServicioGenerico.findEntityById(LDAP_USER, Parametros.class).getValor();
                this._ldappassword = iServicioGenerico.findEntityById(LDAP_PASSWORD, Parametros.class).getValor();
                 logger.info("AUTH "+_userLogin+" : "+DateUtil.formatShortDateTime(new Date()));

            } catch (ServiceException ex) {
                logger.error(ex.getMessage());
                throw new ViewException("Se ha presentado un error durante la carga del cliente MiddleWare", Level.WARN);

            }
          //  _loadImag();
        } catch (ServiceException ex) {
            String errorMessage = "Se ha presentado un error indeterminado durante la carga de los roles del usuario";
            logger.error(errorMessage + ", detalles: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
        }

    }

    /**
     *
     * @return
     */
    public String terminateSession() {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            String method = "terminateSession";
            logger.info("Cerrando sesión... ");
            context.getExternalContext().invalidateSession();
            request.getSession().invalidate();
            _helper.logout();
            logger.info("Sesión invalidada... ");
            return "/authenticate/login.jsf?faces-redirect=true";
        } catch (ViewException ex) {
            String errorMessage = "Se ha presentado un error indeterminado durante el cierre de sesión del usuario";
            logger.error(errorMessage + ", detalles: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
        }

        return null;
    }

    /**
     *
     * @return
     */
    public boolean isMobile() {
        return _mobile;
    }

    /**
     *
     * @param _mobile
     */
    public void setMobile(boolean _mobile) {
        this._mobile = _mobile;
    }

    /**
     *
     * @return
     */
    public String getUserDesc() {
        return _userDesc;
    }

    /**
     *
     * @param _userDesc
     */
    public void setUserDesc(String _userDesc) {
        this._userDesc = _userDesc;
    }

    public Usuario getUsuario() {
        return _usuario;
    }

    public void setUsuario(Usuario _usuario) {
        this._usuario = _usuario;
    }

    /**
     *
     * @return
     */
    public List<String> getOpcionesMenuEmergente() {
        return _opcionesMenuEmergente;
    }

    /**
     *
     * @param _opcionesMenuEmergente
     */
    public void setOpcionesMenuEmergente(List<String> _opcionesMenuEmergente) {
        this._opcionesMenuEmergente = _opcionesMenuEmergente;
    }

    public IntelcomWSClient getClientWs() {
        return clientWs;
    }

    public void _loadImag() {
        if (image == null) {
            InputStream is = null;
            try {
                logger.info("Buscando Imagen De usuario");
                String filePathString = "/home/oracle/" + _userLogin + ".png";
                File f = new File(filePathString);
                if (f.exists()) {
                    logger.info("Archivo Existe...:" + filePathString);
                } else {
                    filePathString = "C:\\Users\\Carlos Guzman\\Documents\\user_logo.jpg";

                }
                is = new FileInputStream(filePathString);
               // image = new DefaultStreamedContent(is, "image/jpeg");
            } catch (FileNotFoundException ex) {
                logger.error("Error Cargando Archivo", ex);
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.error("Error Cargando Archivo", ex);
                }
            }
        }

    }

    public String getImage() {
        return  "C:\\Users\\Carlos Guzman\\Documents\\user_logo.jpg";
    }

    public void String(String image) {
        this.image = image;
    }

    public String getLdapHost() {
        return _ldapHost;
    }

    public void setLdapHost(String _ldapHost) {
        this._ldapHost = _ldapHost;
    }

    public String getLdapdomain() {
        return _ldapdomain;
    }

    public void setLdapdomain(String _ldapdomain) {
        this._ldapdomain = _ldapdomain;
    }

    public String getLdapUser() {
        return _ldapUser;
    }

    public void setLdapUser(String _ldapUser) {
        this._ldapUser = _ldapUser;
    }

    public String getLdappassword() {
        return _ldappassword;
    }

    public void setLdappassword(String _ldappassword) {
        this._ldappassword = _ldappassword;
    }

    public Tipo getTipoPais_Colombia() {
        if(tipoPais_Colombia==null){
            try {
                tipoPais_Colombia = iTipoService.findByTipoNombreValor(TIPO_PAIS, TIPO_VALOR_PAIS_COLOMBIA);
            } catch (ServiceException ex) {
                logger.error("Error Cargando tipoPais_Colombia", ex);
            }
        }
        return tipoPais_Colombia;
    }

    public Tipo getTipoEvento_Programado() {
        if(tipoEvento_Programado==null){
            try {
                tipoEvento_Programado = iTipoService.findByTipoNombreValor(TIPO_ESTADO_EVENTO, TIPO_VALOR_ESTADO_EVENTO_PROGRAMADO);
            } catch (ServiceException ex) {
                logger.error("Error Cargando tipoEvento_Programado", ex);
            }
        }
        return tipoEvento_Programado;
    }
    
    public Tipo getTipoEvento_Ejecutado() {
        if(tipoEvento_Ejecutado==null){
            try {
                tipoEvento_Ejecutado = iTipoService.findByTipoNombreValor(TIPO_ESTADO_EVENTO, TIPO_VALOR_ESTADO_EVENTO_EJECUTADO);
            } catch (ServiceException ex) {
                logger.error("Error Cargando tipoEvento_Ejecutado", ex);
            }
        }
        return tipoEvento_Ejecutado;
    }

}
