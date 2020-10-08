/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.imocom.intelcom.util.utility.Constants;
import java.io.InputStream;
import java.net.URL;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

/**
 *
 * @author Carlos Guzman
 */
public class CreateDocx {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CreateDocx.class);
    private HashMap<String, String> codReplace;
    private String nameNewDocument;

    public CreateDocx(HashMap<String, String> codReplace, String nameNewDocument) {
        this.codReplace = codReplace;
        this.nameNewDocument = nameNewDocument;
    }
    
    private void addPicture(final InputStream fileName, final int id, int width, int height, final XWPFRun run) {
        FileInputStream fileInputStream = null;
        InputStream is=fileName;

        try {
            //fileInputStream = new FileInputStream(fileName);
            final String blipId = run.getDocument().addPictureData(is, Document.PICTURE_TYPE_JPEG);

            final int EMU = 9525;
            width *= EMU;
            height *= EMU;
            //String blipId = getAllPictures().get(id).getPackageRelationship().getId();

            final CTInline inline = run.getCTR().addNewDrawing().addNewInline();

            final String picXml = "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                    + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                    + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
                    + id
                    + "\" name=\"Generated\"/>"
                    + "            <pic:cNvPicPr/>"
                    + "         </pic:nvPicPr>"
                    + "         <pic:blipFill>"
                    + "            <a:blip r:embed=\""
                    + blipId
                    + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                    + "            <a:stretch>"
                    + "               <a:fillRect/>"
                    + "            </a:stretch>"
                    + "         </pic:blipFill>"
                    + "         <pic:spPr>"
                    + "            <a:xfrm>"
                    + "               <a:off x=\"0\" y=\"0\"/>"
                    + "               <a:ext cx=\""
                    + width
                    + "\" cy=\""
                    + height
                    + "\"/>"
                    + "            </a:xfrm>"
                    + "            <a:prstGeom prst=\"rect\">"
                    + "               <a:avLst/>"
                    + "            </a:prstGeom>"
                    + "         </pic:spPr>"
                    + "      </pic:pic>" + "   </a:graphicData>" + "</a:graphic>";

            //CTGraphicalObjectData graphicData = inline.addNewGraphic().addNewGraphicData();
            XmlToken xmlToken = null;
            xmlToken = XmlToken.Factory.parse(picXml);
            inline.set(xmlToken);
            //graphicData.set(xmlToken);

            inline.setDistT(0);
            inline.setDistB(0);
            inline.setDistL(0);
            inline.setDistR(0);

            final CTPositiveSize2D extent = inline.addNewExtent();
            extent.setCx(width);
            extent.setCy(height);

            final CTNonVisualDrawingProps docPr = inline.addNewDocPr();
            docPr.setId(id);
            docPr.setName("Picture " + id);
            docPr.setDescr("Generated");
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //close streams 
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (final IOException ioEx) {
                    //can be ignored
                }
            }
        }

    }
    
    private void _updateDoc(XWPFDocument doc, String key, String value) throws FileNotFoundException, IOException {

         for (XWPFParagraph p : doc.getParagraphs()) {

            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                   
                      
                    if (text != null && text.contains(key)) {
                       
                      if ((value != null && !"".equals(value)) && (key.equals("L0010LINKIMAGENMAQUINA")||key.equals("L0011LINKTEC")||key.equals("L0012LINKACCESORIOS"))){
                          logger.info("Agregando imagen al documento con valor de: " + value);  
                          try{
                                logger.info("Add image "+value);
                                InputStream is;
                                is = new URL(value).openStream();
                                try {
                                    logger.info(" ************ Add image upload ********************** ");
                                    //doc.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG);
                                    this.addPicture(is, doc.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG), 100, 50, r);
                                } catch (InvalidFormatException ex) {
                                     ex.printStackTrace();
                                }
                            }catch(Exception ex){                                
                                    logger.error("error capturando imagen "+ex.getMessage(),ex);
                                  text = text.replace(key, value);
                                  r.setText(text, 0);
                            }
                          
                            //r.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "logo.JPG",300,300); 
                        }  else{ 
                            text = text.replace(key, value);
                            r.setText(text, 0);
                        }
                      
                        //r.s
                    }
                }
            }
        }
        List<XWPFParagraph> xwpfParagraphs = doc.getParagraphs();

        List<XWPFTable> tables = doc.getTables();
        for (XWPFTable xwpfTable : tables) {
            List<XWPFTableRow> tableRows = xwpfTable.getRows();
            for (XWPFTableRow xwpfTableRow : tableRows) {
                List<XWPFTableCell> tableCells = xwpfTableRow
                        .getTableCells();
                for (XWPFTableCell xwpfTableCell : tableCells) {
                    xwpfParagraphs = xwpfTableCell.getParagraphs();
                    for (XWPFParagraph p : xwpfParagraphs) {

                        List<XWPFRun> runs = p.getRuns();
                        if (runs != null) {
                            for (XWPFRun r : runs) {
                                String text = r.getText(0);
                                
                                if (text != null && text.contains(key)) {
                                    text = text.replace(key, value);
                                    r.setText(text, 0);
                                    //r.s
                                }
                            }
                        }
                    }

                }
            }
        }
        doc.write(new FileOutputStream(nameNewDocument));
    }

    public InputStream replace() {
        try {
            String filepath = Constants.TEMPLATE_COTIZACIONES;
            XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath));
            for (Map.Entry e : codReplace.entrySet()) {
                try {
                    logger.info(e.getKey() + " " + e.getValue());
                    //Se debe impedir que se intente reemplazar por strings nulos o vacios
                    if(e.getValue()!= null && !"".equals(e.getValue().toString())){
                        _updateDoc(doc, e.getKey().toString(), e.getValue().toString());
                    }                    

                } catch (IOException ex) {
                    logger.error("Error Creando Cotizacion", ex);
                }
            }
            InputStream is = new FileInputStream(nameNewDocument);
            logger.info("Inputsream: " + is.toString());
            return is;
        } catch (IOException ex) {
            logger.error("Error Creando Cotizacion", ex);
        }
        return null;
    }

}
