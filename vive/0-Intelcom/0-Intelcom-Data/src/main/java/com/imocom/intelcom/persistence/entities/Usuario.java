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
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.NamedStoredFunctionQueries;
import org.eclipse.persistence.annotations.NamedStoredFunctionQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 * <strong>Aplicación</strong> : IMOCOM Sistema de inteligencia comercial.
 * <br/>
 * <br/>
 * <strong>Date</strong> : Oct 27, 2014
 * <br/><br/>
 * <strong>Target</strong> :
 *
 * @author Carlos Guzman (cguzman) - PointMind S.A.S. -
 * carlos.guzman@pointmind.com
 *
 */
@Entity
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByNombreParcial", query = "SELECT u FROM Usuario u WHERE UPPER(u.nombre) LIKE :nombre"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")})

@NamedStoredFunctionQueries({
    @NamedStoredFunctionQuery(name = "Usuario.encryptFunction", functionName = "UTIL.encrypt",
            parameters = {
                @StoredProcedureParameter(name = "p_plainText", queryParameter = "password", mode = ParameterMode.IN, type = String.class)},
            returnParameter = @StoredProcedureParameter(queryParameter = "passwdEncrypt", type = String.class)
    )
})
public class Usuario extends AbstractEntity implements Serializable, IDataModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Usuario_seq_gen")
    @SequenceGenerator(name = "Usuario_seq_gen", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "LINEA")
    private String linea;

    @Column(name = "DIVISION")
    private String division;
    
    @Column(name = "CIUDAD")
    private String ciudad;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    

    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @OneToMany(mappedBy = "idUsuario")
    private Set<InvitadoVisita> invitadoVisitaSet;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private RolUsuario rolUsuario;

    @OneToMany(mappedBy = "idasesor")
    private Set<Visita> visitaSet;

    public Usuario() {
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Long idUsuario, String usuario, String password) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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
    
    

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<InvitadoVisita> getInvitadoVisitaSet() {
        return invitadoVisitaSet;
    }

    public void setInvitadoVisitaSet(Set<InvitadoVisita> invitadoVisitaSet) {
        this.invitadoVisitaSet = invitadoVisitaSet;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Set<Visita> getVisitaSet() {
        return visitaSet;
    }

    public void setVisitaSet(Set<Visita> visitaSet) {
        this.visitaSet = visitaSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imocom.intelcom.persistence.entities.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    public String getKeyModel() {
        if (this.idUsuario != null) {
            return String.valueOf(this.idUsuario);
        }

        return null;
    }

}
