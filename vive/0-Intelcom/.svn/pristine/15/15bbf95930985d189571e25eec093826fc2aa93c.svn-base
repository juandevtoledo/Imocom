/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.servlet;

import static com.imocom.intelcom.util.utility.Constants.IMAGE_DEFAULT;
import static com.imocom.intelcom.util.utility.Constants.PROFILE_PATH;
import com.imocom.intelcom.view.security.UserSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Carlos Guzman
 */
public class UserProfileImageServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UserProfileImageServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserSession userSession = (UserSession) ((HttpServletRequest) request).getSession().getAttribute("userSession");
            userSession.getUserLogin();
            String _userLogin = userSession.getUserLogin();
            File f = null;
            String filePathString = "";
            // Get image file.
            String filePathStringLower = PROFILE_PATH + _userLogin.toLowerCase() + ".png";
            String filePathStringUpper = PROFILE_PATH + _userLogin.toUpperCase() + ".png";
                      
            if (this.existFile(filePathStringLower)) {
                logger.info("Imagen Users Lower:...:" + filePathStringLower);  
                filePathString = filePathStringLower;
            }else if (this.existFile(filePathStringUpper)){
                logger.info("Imagen Users Upper:...:" + filePathStringUpper);
                 filePathString = filePathStringUpper;
            }else{
                logger.info("default image:...:" + filePathStringUpper);
                filePathString = PROFILE_PATH + IMAGE_DEFAULT; 
            }      
                       
            logger.info("Imagen Encontrada...:" + filePathString);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePathString));

            // Get image contents.
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();

            // Write image contents to response.
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            logger.info("Error Encontrando la Imagen: " + e.getMessage());
            logger.error("Error Encontrando la Imagen: " + e.getMessage(), e);

        }
    }
    
    public Boolean existFile(String path){
        File f = new File(path);
        return f.exists();
    }
}
