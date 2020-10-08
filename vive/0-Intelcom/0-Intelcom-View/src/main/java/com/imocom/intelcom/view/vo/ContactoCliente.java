/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.ws.ebs.vo.entities.ContactoVO;



/**
 *
 * @author rc
 */
public class ContactoCliente {
    
    private boolean editando = true;
    
    private static final String[] encoded = {  "<", ">", "\"", "\'" , "Ñ" };
    private static final String[] decoded = {  "&lt;", "&gt;", "&quot;", "&apos;" , "Ntilde;"};

    public ContactoCliente() {
        setId("");
        setNombre("");
        setCargo("");
        
        setTelefono("");
        setCelular("");
        setCorreo("");
        setCargo("");
        setApellido("");
    }    
    
    public ContactoCliente(ContactoVO contacto){
        setId(contacto.getId());
        setNombre(contacto.getNombre());
        setCargo(contacto.getCargo());
        
        setTelefono(contacto.getTelefono());
        setCelular(contacto.getCelular());
        setCorreo(contacto.getCorreo());
        setCargo(contacto.getCargo());
        setApellido(contacto.getApellido());
        //setCorreoContacto(" <a href=\"mailto:"+cliente.getCorreoContactoPpal()+"\">"+cliente.getCorreoContactoPpal()+"</a>"); 
    }

    public ContactoCliente(ContactoCliente contacto){
        setId(contacto.getId());
        setNombre(contacto.getNombre());
        setCargo(contacto.getCargo());
        setTelefono(contacto.getTelefono());
        setCelular(contacto.getCelular());
        setCorreo(contacto.getCorreo());
        setApellido(contacto.getApellido());
        //setCorreoContacto(" <a href=\"mailto:"+cliente.getCorreoContactoPpal()+"\">"+cliente.getCorreoContactoPpal()+"</a>"); 
        setHabeasData(contacto.getHabeasData());
        setDatosSensible(contacto.getDatosSensible());
        setCedulaContactoProspecto(contacto.getCedulaContactoProspecto() );
    }
    
    
    String id;
    String nombre;
    String nombreEscape;
    String cargo;
    String telefono;
    String celular;
    String correo;
    String apellido;
    String apellidoEscape;
    String habeasData;
    String datosSensible;
    String cedulaContactoProspecto;

    public String getNombre() {
        return nombre;
    }

    public String getNombreEscape() {
        return nombreEscape;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.setNombreEscape(nombre);
    }

    public void setNombreEscape(String nombre) {
        this.nombreEscape = escape(nombre);
    }
    
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellidoEscape() {
        return apellidoEscape;
    }

    public String getApellido() {
        return apellido;
    }
    
    public void setApellidoEscape(String apellido) {
        this.apellidoEscape = escape(apellido);
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
        this.setApellidoEscape(apellido);
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public String getHabeasData() {
        return habeasData;
    }

    public void setHabeasData(String habeasData) {
        this.habeasData = habeasData;
    }

    public String getDatosSensible() {
        return datosSensible;
    }

    public void setDatosSensible(String datosSensible) {
        this.datosSensible = datosSensible;
    }

    public String getCedulaContactoProspecto() {
        return cedulaContactoProspecto;
    }

    public void setCedulaContactoProspecto(String cedulaContactoProspecto) {
        this.cedulaContactoProspecto = cedulaContactoProspecto;
    }
    
    public static String escape(String input) {
    return translateAll(input, encoded, decoded);
    }
    
    public static String unescape(String input) {
    return translateAll(input, decoded, encoded);
    }
    
    
    public static String translateAll(String input, String[] patterns, String[] replacements) {
    String result = input;

    for (int i = 0; i < patterns.length; i++) {
        result = result.replaceAll(patterns[i], replacements[i]);
    }

    return result;
}

    
}
