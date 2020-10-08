/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ws.ebs.vo.entities.FacturaVO;

/**
 *
 * @author rc
 */
public class Factura {

    private String numero;
    private String fechaVencimiento;
    private String diasVencimiento;
    private String valor;
    private String valorVencido;
    private String centroCosto;
    private String valorPorVencer;
    private String asesor;

    public Factura(FacturaVO detalleCartera) {
        numero = detalleCartera.getIdFactura();
        fechaVencimiento = detalleCartera.getFechaVencimiento();
        diasVencimiento = detalleCartera.getNroDiasVencimiento();
        valor = detalleCartera.getValorFactura();
        valorVencido = detalleCartera.getValorVencido();
        centroCosto = detalleCartera.getCentroCosto();
        valorPorVencer=detalleCartera.getValorPorVencer();
        asesor=detalleCartera.getAsesor();

    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDiasVencimiento() {
        return diasVencimiento;
    }

    public void setDiasVencimiento(String diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorVencido() {
        return valorVencido;
    }

    public void setValorVencido(String valorVencido) {
        this.valorVencido = valorVencido;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getValorPorVencer() {
        return valorPorVencer;
    }

    public void setValorPorVencer(String valorPorVencer) {
        this.valorPorVencer = valorPorVencer;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }
    
    

}
