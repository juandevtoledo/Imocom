/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.util;

import com.imocom.intelcom.util.utility.DateUtil;
import com.imocom.intelcom.ws.ebs.vo.entities.OportunidadVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ProductoVO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Carlos Guzman
 */
public class CreatePDF {

    private static final Logger logger = Logger.getLogger(CreatePDF.class);
    private static String FILE = "";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public static InputStream createPDFCotizacion(String user, String nameDocument, OportunidadVO oportunidad, ProductoVO producto) {
        try {

            Document document = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(document, new FileOutputStream(nameDocument));
            document.open();
            //addMetaData(document);
            addTitlePage(document, oportunidad, producto, user);

            document.close();
            logger.info("Create File Ct: " + nameDocument);
            InputStream is = new FileInputStream(nameDocument);
            logger.info("Inputsream: " + is.toString());
            return is;
        } catch (DocumentException ex) {
            logger.error("Error Creando Cotizacion", ex);
        } catch (FileNotFoundException ex) {
            logger.error("Error Creando Cotizacion", ex);
        } catch (IOException ex) {
            logger.error("Error Creando Cotizacion", ex);
        }
        return null;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Cotizacion Producto Imocom");
        document.addSubject("Generacion Cotizacion");
        document.addKeywords("Cotizacion, Imocom");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document, OportunidadVO oportunidad, ProductoVO producto, String user)
            throws DocumentException {

        document.add(new Chunk("Bogota, " + DateUtil.formatShortTime(new Date())));
        //
        Paragraph par = new Paragraph();
        CreatePDF.addEmptyLine(par, 2);
        document.add(par);
        document.add(new Paragraph("IMOCOM."));
        //
        par = new Paragraph();
        CreatePDF.addEmptyLine(par, 1);
        document.add(par);
        document.add(new Paragraph("Señor " + oportunidad.getNombreCliente() + ", Se le presenta la siguiente cotización del producto seleccionado: "));
        //
        CreatePDF.addEmptyLine(par, 2);
        document.add(par);
        document.add(new Paragraph("Oportunidad: " + oportunidad.getNombreOportunidad()));
        //
        CreatePDF.addEmptyLine(par, 2);
        document.add(par);
        document.add(new Paragraph("Producto: "));
        //
        par = new Paragraph();
        CreatePDF.addEmptyLine(par, 3);
        document.add(par);
        PdfPTable table = new PdfPTable(4);
        createTable(table, producto);
        document.add(table);
        //
        par = new Paragraph();
        CreatePDF.addEmptyLine(par, 2);
        document.add(par);
        document.add(new Paragraph("Asesor Comercial, "));
        //
        par = new Paragraph();
        CreatePDF.addEmptyLine(par, 2);
        document.add(par);
        document.add(new Paragraph(user));
    }

    private static void createTable(PdfPTable table, ProductoVO producto) {
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell c1 = new PdfPCell(new Phrase("Nombre Del Producto"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor Unitario"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cantidad"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor Total"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(producto.getNombre());
        table.addCell(producto.getPrecioListaSinFormato());
        String cantidad = producto.getCantidad();
        if (producto.getCantidad() == null) {
            cantidad = "1";
        }
        table.addCell(cantidad);
        Integer cant = 1;
        Long precioFormato = 0L;
        try {
            cant = Integer.parseInt(cantidad);
        } catch (NumberFormatException nf) {
            cant = 1;
        }

        try {
            precioFormato = Long.parseLong(producto.getPrecioListaSinFormato());
        } catch (NumberFormatException nf) {
            cant = 1;
        }
        table.addCell(""+(precioFormato*cant));

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
