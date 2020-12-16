/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.Usuario;

/**
 *
 * @author juan.toledo
 */
public class AsesorVO {

    private final Long idAsesor;
    private final String name;
    private final String login;

    public AsesorVO(Builder builder) {
        this.idAsesor = builder.idAsesor;
        this.name = builder.name;
        this.login = builder.login;
    }

    public static class Builder {

        private Long idAsesor;
        private String name;
        private String login;

        public Builder() {
        }

        public AsesorVO.Builder idAsesor(Long idAsesor) {
            this.idAsesor = idAsesor;
            return this;
        }

        public AsesorVO.Builder name(String name) {
            this.name = name;
            return this;
        }

        public AsesorVO.Builder login(String login) {
            this.login = login;
            return this;
        }

        public AsesorVO build() {
            return new AsesorVO(this);
        }
    }

    public static AsesorVO toAsesorVO(Usuario usuario) {
        return new AsesorVO.Builder()
                .idAsesor(usuario.getIdUsuario())
                .name(usuario.getNombre())
                .login(usuario.getUsuario())
                .build();
    }

    public Long getIdAsesor() {
        return idAsesor;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }
    
    
}
