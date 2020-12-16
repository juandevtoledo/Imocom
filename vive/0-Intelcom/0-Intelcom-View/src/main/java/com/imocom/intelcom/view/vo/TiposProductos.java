/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.TipoProducto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juan.toledo
 */
public enum TiposProductos {

    MAQUINA(1, "MAQUINA"),
    ACCESORIO(2, "ACCESORIO"),
    REPUESTO(3, "REPUESTO"),;

    private final int codigo;
    private final String texto;

    private TiposProductos(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public int getCodigo() {
        return codigo;
    }

    public static List<String> getTiposProductos() {
        List<String> tipoList = new ArrayList<String>();
        for (TiposProductos t : TiposProductos.values()) {
            tipoList.add(t.getTexto());
        }
        return tipoList;
    }

}
