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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de Inteligencia Comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Configuration to application security component.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
public final class SecurityConfig {
    
    private static final Logger logger = Logger.getLogger(SecurityConfig.class);
    
    private static final Properties props = new Properties();
    private static final String FILTER_EXCLUDED_LIST = "gelxml.security.web.page.excluded.resource[#]";
    private static final String FILTERES_RESOURCES_LIST = "gelxml.security.web.page.filtered.resource[#]";
    
    private static final String LOGIN_PAGE = "gelxml.security.access.login.page";
    private static final String REQUEST_PARAM = "gelxml.security.request.param";
    
    private static final String WEB_INDEX_PAGE = "gelxml.security.web.index.page";
    private static final String WEB_ACCESS_DENIED_PAGE = "gelxml.security.web.access.denied.page";
    private static final String WEB_GENERIC_ERROR_PAGE = "gelxml.security.web.generic.error.page";
    
    private static final String MOBILE_INDEX_PAGE = "gelxml.security.mobile.index.page";
    private static final String MOBILE_ACCESS_DENIED_PAGE = "gelxml.security.mobile.access.denied.page";
    private static final String MOBILE_GENERIC_ERROR_PAGE = "gelxml.security.mobile.generic.error.page";
    
    
    
    static {
        
        InputStream in;
        
        try {
            in = SecurityConfig.class.getClassLoader().getResourceAsStream("security.properties");
            props.load(in);
            in.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    /**
     *
     * @param key
     * @return
     */
    public static String getConfigPropertieValue(String key) {
        return props.getProperty(key);
    }
    
    /**
     *
     * @return
     */
    public static List<String> getFilterExcludedList() {
        return getPropertiesList(FILTER_EXCLUDED_LIST);
    }
    
    /**
     *
     * @return
     */
    public static List<String> getFilteredResourcesList() {
        return getPropertiesList(FILTERES_RESOURCES_LIST);
    }
    
    /**
     *
     * @return
     */
    public static String getWebIndexPage() {
        return props.getProperty(WEB_INDEX_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getWebAccessDeniedPage() {
        return props.getProperty(WEB_ACCESS_DENIED_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getWebGenericErrorPage() {
        return props.getProperty(WEB_GENERIC_ERROR_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getMobileIndexPage() {
        return props.getProperty(MOBILE_INDEX_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getMobileAccessDeniedPage() {
        return props.getProperty(MOBILE_ACCESS_DENIED_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getMobileGenericErrorPage() {
        return props.getProperty(MOBILE_GENERIC_ERROR_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getLoginPage() {
        return props.getProperty(LOGIN_PAGE);
    }
    
    /**
     *
     * @return
     */
    public static String getRequestParam() {
        return props.getProperty(REQUEST_PARAM);
    }
    
    /**
     *
     * @param key
     * @return
     */
    private static List<String> getPropertiesList(final String key) {
        List<String> properties = new ArrayList<String>();
        int i = 0;
        while(true) {
            String item = props.getProperty(key.replace("#", String.valueOf(i)));
            if (item == null) { break; }
            properties.add(item);
            i++;
        }
        
        return properties;
    }
}
