/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author juan.toledo
 */
public class DateOracleFormate {
    
    public static String formatToGrid(Date date) {
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
         return format.format(date);
    }
    
    public static String formatToSendService(Date date) {
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");  
         return format.format(date);
    }
    public static Date convertToDate(String oracledate) {
        String date = convertJavaDate(oracledate);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");       
        Date dateFormat = Calendar.getInstance().getTime();
        try {
            dateFormat = format.parse(date);          
            System.out.println("current Date " + dateFormat);
        } catch (ParseException ex) {
            System.out.println("ex "+ex.getMessage());
        }
        return dateFormat;
    }

    private static String convertJavaDate(String oracledate) {
        String dateSplit[] = oracledate.split("-");
        return dateSplit[0].concat("-").concat(monthConverter(dateSplit[1])).concat("-").concat(dateSplit[2]);
    }

    private static String monthConverter(String Month) {

        switch (Month) {
            case "JAN":
                return "01";
            case "FEB":
                return "02";
            case "MAR":
                return "03";
            case "APR":
                return "04";
            case "MAY":
                return "05";
            case "JUN":
                return "06";
            case "JUL":
                return "07";
            case "AUG":
                return "08";
            case "SEP":
                return "09";
            case "OCT":
                return "10";
            case "NOV":
                return "11";
            case "DEC":
                return "12";
            default:
                return "12";
        }

    }
}
