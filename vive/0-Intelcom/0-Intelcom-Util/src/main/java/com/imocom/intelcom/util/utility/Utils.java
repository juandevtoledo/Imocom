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

package com.imocom.intelcom.util.utility;

import com.imocom.intelcom.util.exceptions.UtilException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : 22/08/2014
 * <br/><br/>
 * <strong>Target</strong>         : Generic utilities for general purpose.
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 */
public class Utils {
    
    private static final Logger logger = Logger.getLogger(Utils.class);
    
    /**
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static String convertStreamToString(File file) throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(file));
        return fileContent;
    }
    
    
    
    /**
     *
     * @param destinationPath
     * @param inputStreamFile
     * @param filename
     * @return
     * @throws GelUtilException
     * @throws java.io.FileNotFoundException
     */
    public static String writeToDiskAnZipFile(String destinationPath, InputStream inputStreamFile, String filename) throws UtilException, FileNotFoundException, IOException {
        
        File destination = new File(destinationPath.concat(File.separator).concat(String.valueOf(DateUtil.getAppCurrentDate().getTime())).concat(File.separator).concat(filename));
        logger.info("Preparando escritura de archivo en carpeta: " + destinationPath);
        
        if (inputStreamFile == null || destinationPath == null) {
            logger.warn("archive and destination must be set");
            throw new UtilException("archive and destination must be set", Level.WARN);
        }
        
        File dest = new File(destinationPath);
        if (!dest.exists() || !dest.isDirectory()) {
            logger.warn("the destination must exist and point to a directory: "+ destinationPath);
            throw new UtilException("the destination must exist and point to a directory: "+ destinationPath, Level.WARN);
        }
        
        logger.info("Iniciando escritura de archivo...");
        writeFileToDisk(inputStreamFile, destination);
        
        File fileDest = new File(destination.getParent());
        InputStream newInputStreamFile = new FileInputStream(destination);
        
        String parentPath = extractArchive(newInputStreamFile, fileDest);
        logger.info("Escritura de archivo OK");
        
        return destination.getParent() + File.separator + parentPath;
    }
    
    /**
     *
     * @param zipFile
     * @param destination
     * @return
     */
    public static String extractArchive(InputStream zipFile, File destination) throws UtilException {
        
        byte[] buffer = new byte[1024];
        String parentPath = "";
        
        try{
            //create output directory is not exists
            if(!destination.exists()){
                destination.mkdir();
            }
            
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(zipFile);
            
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            
            int parentCount = 0;
            
            while(ze != null){
                
                String fileName = ze.getName();
                File newFile = new File(destination.getPath() + File.separator + fileName);
                
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                Pattern pattern = Pattern.compile(".*\\.xsd*.");
                Matcher matcher = pattern.matcher(fileName);
                
                if (newFile.isDirectory() || !matcher.matches()) {
                    newFile.mkdir();
                    
                    if (parentCount == 0) {
                        parentPath = newFile.getName();
                        parentCount++;
                    }
                } else {
                    
                    FileOutputStream fos = new FileOutputStream(newFile);
                    
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    
                    fos.close();
                }
                ze = zis.getNextEntry();
                
            }
            
            zis.closeEntry();
            zis.close();
            
            logger.info("Extracción de archivo terminada");
            
        }catch(IOException ex){
            throw new UtilException(ex, Level.ERROR);
        }
        
        return parentPath;
    }
    
    /**
     *
     * @param fileImpuInputStream
     * @param destinationFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeFileToDisk(InputStream fileImpuInputStream, File destinationFile) throws FileNotFoundException, IOException {
        
        byte[] buffer = new byte[8 * 1024];
        try {
            
            File directory  = new File(destinationFile.getParent());
            
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            OutputStream output = new FileOutputStream(destinationFile);
            try {
                int bytesRead;
                while ((bytesRead = fileImpuInputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                output.close();
            }
        } finally {
            fileImpuInputStream.close();
        }
    }
    
    /**
     *  
     * <p> Método que dado un MIE-XML String well-formed, construye su representación <br />
     * en objeto, definido en su namespace </p>
     * 
     * @author cguzman
     * @param response
     * @return
     * @throws JAXBException
     */
    public static <E> Object unmarshal(Class<E> clazz, String response) throws UtilException {
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(response));
            JAXBElement<E> responseOut = jaxbUnmarshaller.unmarshal(source, clazz);
            return (E) responseOut.getValue();
        } catch (JAXBException ex) {
            logger.error("Error durante transformación de respuesta de servicio, detalles: " + ex.getMessage());
            throw new UtilException(ex, Level.ERROR);
        }
    }
    
    /**
     * <p> Método que dado un objeto JAXB, devuelve su representación como un MIME-XML <br />
     * String well-formed, según la definicón de su namespace </p> 
     * 
     * @author cguzman
     * @param response
     * @return
     * @throws JAXBException
     */
    public static String marshal(Object response) throws JAXBException {
        
        final JAXBContext jaxbContext = JAXBContext.newInstance(response.getClass());
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        final StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(response, writer);
        
        return writer.toString();
    }
    
    public static String remove1(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
    
    
    public static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    public static String formatNumber(Long value){
        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            simbolo.setGroupingSeparator(',');
            DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);            
            //Integer v =10000000;
          return formateador.format(value);
    }
    
    
}
