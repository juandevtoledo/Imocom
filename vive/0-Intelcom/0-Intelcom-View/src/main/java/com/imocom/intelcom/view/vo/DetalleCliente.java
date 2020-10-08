/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.NotaVO;
import java.util.List;

/**
 *
 * @author rc
 */
public class DetalleCliente {

    public DetalleCliente(ClienteVO cliente) {
        setNit(cliente.getNitCliente());
        setNombre(cliente.getNombreCliente());
        setTipoCliente(cliente.getTipoCliente());
        setNivelAtencion(cliente.getNivelAtencion());
        setPais(cliente.getPais());
        setDepartamento(cliente.getDepartamento());
        setCiudad(cliente.getCiudad());
        setDireccion(cliente.getDireccionPpal());
        setTelefono(cliente.getTelefonoPpal());
        setNombreContacto(cliente.getNombreContactoPpal());
        setTelefonoContacto(cliente.getTelefonoContanctoPpal());
        setCorreoContacto(cliente.getCorreoContactoPpal());
        setPerteneceAsesor(cliente.getPerteneceAsesor());
        //setCorreoContacto(" <a href=\"mailto:"+cliente.getCorreoContactoPpal()+"\">"+cliente.getCorreoContactoPpal()+"</a>");  

    }

    public DetalleCliente(DetalleCliente cliente) {
        setNit(cliente.getNit());
        setNombre(cliente.getNombre());
        setTipoCliente(cliente.getTipoCliente());
        setNivelAtencion(cliente.getNivelAtencion());
        setPais(cliente.getPais());
        setDepartamento(cliente.getDepartamento());
        setCiudad(cliente.getCiudad());
        setDireccion(cliente.getDireccion());
        setTelefono(cliente.getTelefono());
        setNombreContacto(cliente.getNombreContacto());
        setTelefonoContacto(cliente.getTelefonoContacto());
        setCorreoContacto(cliente.getCorreoContacto());
        setPerteneceAsesor(cliente.isPerteneceAsesor());
        setListaContactos(cliente.listaContactos);
        //setCorreoContacto(" <a href=\"mailto:"+cliente.getCorreoContactoPpal()+"\">"+cliente.getCorreoContactoPpal()+"</a>");  

    }

    String nit;
    String nombre;
    String tipoCliente;
    String nivelAtencion;
    String pais;
    String departamento;
    String ciudad;
    String direccion;
    String telefono;
    String nombreContacto;
    String telefonoContacto;
    String correoContacto;
    boolean perteneceAsesor;

    List<ContactoCliente> listaContactos;
    List<NotaVO> listaNotas;
    List<NotaVO> listaAsesores;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(String nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public List<ContactoCliente> getListaContactos() {
        return listaContactos;
    }

    public void setListaContactos(List<ContactoCliente> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public List<NotaVO> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(List<NotaVO> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public List<NotaVO> getListaAsesores() {
        return listaAsesores;
    }

    public void setListaAsesores(List<NotaVO> listaAsesores) {
        this.listaAsesores = listaAsesores;
    }

    public boolean isPerteneceAsesor() {
        return perteneceAsesor;
    }

    public void setPerteneceAsesor(boolean perteneceAsesor) {
        this.perteneceAsesor = perteneceAsesor;
    }

}
