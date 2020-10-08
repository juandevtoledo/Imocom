/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.ws.ebs.vo.response;



import com.imocom.intelcom.ws.ebs.vo.entities.ClienteVO;
import com.imocom.intelcom.ws.ebs.vo.entities.ContactoVO;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <strong>Aplicación</strong>     : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong>           : Dec 15, 2014
 * <br/><br/>
 * <strong>Target</strong>         : 
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. - carlos.guzman@pointmind.com
 *
 */
@XmlRootElement(name = "ClienteResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
@XmlType(name = "ClienteResponseVO", namespace = "http://com.imocom.intelcom.ws.ebs.interfaces/")
public class ClienteResponseVO implements Serializable {
    
    private List<ClienteVO> clientes;
    private List<ContactoVO> contactos; 

    /**
     * 
     * @return 
     */
    public List<ClienteVO> getClientes() {
        return clientes;
    }

    /**
     * 
     * @param clientes 
     */
    @XmlElement
    public void setClientes(List<ClienteVO> clientes) {
        this.clientes = clientes;
    }

    /**
     * 
     * @return 
     */
    public List<ContactoVO> getContactos() {
        return contactos;
    }

    /**
     * 
     * @param contactos 
     */
    @XmlElement
    public void setContactos(List<ContactoVO> contactos) {
        this.contactos = contactos;
    }  
}