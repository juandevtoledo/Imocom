/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ws.ebs.vo.entities.CarteraVO;
import com.imocom.intelcom.ws.ebs.vo.entities.FacturaVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rc
 */
public class Cartera {

    private String nit;
    private String nombreCliente;
    private String tipoCliente;
    private String cupoCredito;
    private String cupoDisponible;
    private String totalCartera;
     private String totalCarteraAsesor;

    private List<Factura> facturas;

    public Cartera(CarteraVO carteraVO) {
        nit = carteraVO.getNitCliente();
        nombreCliente = carteraVO.getNombreCliente();
        tipoCliente = carteraVO.getTipoCliente();
        cupoCredito = carteraVO.getCupoCredito();
        cupoDisponible = carteraVO.getCupoCreditoDisponible();
        totalCartera = carteraVO.getTotalCartera();
        totalCarteraAsesor=carteraVO.getTotalCarteraAsesor();

        

    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCupoCredito() {
        return cupoCredito;
    }

    public void setCupoCredito(String cupoCredito) {
        this.cupoCredito = cupoCredito;
    }

    public String getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(String cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getTotalCartera() {
        return totalCartera;
    }

    public void setTotalCartera(String totalCartera) {
        this.totalCartera = totalCartera;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public String getTotalCarteraAsesor() {
        return totalCarteraAsesor;
    }

    public void setTotalCarteraAsesor(String totalCarteraAsesor) {
        this.totalCarteraAsesor = totalCarteraAsesor;
    }
    
    
}
