/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.persistence.entities;

import com.imocom.intelcom.persistence.AbstractEntity;
import com.imocom.intelcom.persistence.IDataModel;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Guzman
 */
@Entity
@Table(name = "CONTADOR_COT_LINEA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContadorCotLinea.findAll", query = "SELECT c FROM ContadorCotLinea c ORDER BY c.idlinea "),
    @NamedQuery(name = "ContadorCotLinea.findByValor", query = "SELECT c FROM ContadorCotLinea c WHERE c.valor = :valor"),
    @NamedQuery(name = "ContadorCotLinea.findByIdlinea", query = "SELECT c FROM ContadorCotLinea c WHERE c.idlinea = :idlinea"),
    @NamedQuery(name = "ContadorCotLinea.findByCodCotizacion", query = "SELECT c FROM ContadorCotLinea c WHERE c.codCotizacion = :codCotizacion")})
public class ContadorCotLinea extends AbstractEntity implements Serializable, IDataModel {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private short valor;
    @Id
    @Basic(optional = false)
    @Column(name = "IDLINEA")
    private String idlinea;
    @Basic(optional = false)
    @Column(name = "COD_COTIZACION")
    private String codCotizacion;

    public ContadorCotLinea() {
    }

    public ContadorCotLinea(String idlinea) {
        this.idlinea = idlinea;
    }

    public ContadorCotLinea(String idlinea, short valor, String codCotizacion) {
        this.idlinea = idlinea;
        this.valor = valor;
        this.codCotizacion = codCotizacion;
    }

    public short getValor() {
        return valor;
    }

    public void setValor(short valor) {
        this.valor = valor;
    }

    public String getIdlinea() {
        return idlinea;
    }

    public void setIdlinea(String idlinea) {
        this.idlinea = idlinea;
    }

    public String getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(String codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlinea != null ? idlinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContadorCotLinea)) {
            return false;
        }
        ContadorCotLinea other = (ContadorCotLinea) object;
        if ((this.idlinea == null && other.idlinea != null) || (this.idlinea != null && !this.idlinea.equals(other.idlinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.ContadorCotLinea[ idlinea=" + idlinea + " ]";
    }

    public String getKeyModel() {
         if (this.idlinea != null) {
            return String.valueOf(this.idlinea);
        }

        return null;
    }
    
}
