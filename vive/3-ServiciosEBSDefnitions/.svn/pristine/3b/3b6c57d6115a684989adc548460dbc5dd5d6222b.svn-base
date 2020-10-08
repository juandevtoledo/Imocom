/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <strong>AplicaciÃ³n</strong> : IMOCOM Sistema de Inteligecia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Nov 24, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "contacto", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ContactoVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/",
        propOrder = {"nombre", "cargo", "telefono", "celular", "correo", "id","apellido"})
public class ContactoVO implements Serializable {

    private String id;
    private String nombre;
    private String apellido;
    private String cargo;
    private String telefono;
    private String celular;
    private String correo;
    //private String habeasData;    

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    @XmlElement(name = "nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getCargo() {
        return cargo;
    }

    /**
     *
     * @param cargo
     */
    @XmlElement(name = "cargo")
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     *
     * @return
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     *
     * @param telefono
     */
    @XmlElement(name = "telefono")
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return
     */
    public String getCelular() {
        return celular;
    }

    /**
     *
     * @param celular
     */
    @XmlElement(name = "celular")
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     *
     * @return
     */
    public String getCorreo() {
        return correo;
    }

    /**
     *
     * @param correo
     */
    @XmlElement(name = "correo")
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    @XmlElement(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /*public String getHabeasData() {
        return habeasData;
    }

    public void setHabeasData(String habeasData) {
        this.habeasData = habeasData;
    }*/
    
}
