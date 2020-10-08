/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.util;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class GerenerNombreUnicos {
    
    /**
     * 
     * @param nombreContacto
     * @param apellidoContacto
     * @return 
     */
    //public static String getNombre(  final String nombreContacto, final String apellidoContacto ){
    public static String getNombre(  final String nombreContacto){
        
        try {
    
            String[] palabras = nombreContacto.trim().split(" ");
            StringBuilder sb = new StringBuilder("");            
            for (String palabra : palabras) {
             
                if( palabra.trim().length() == 0 ){
                    continue;
                }
                sb.append(palabra);
                
            }                        
            
            return sb.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
