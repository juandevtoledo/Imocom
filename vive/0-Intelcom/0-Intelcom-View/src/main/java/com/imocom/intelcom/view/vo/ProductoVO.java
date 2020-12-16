/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

import com.imocom.intelcom.persistence.entities.Modelo;
import com.imocom.intelcom.persistence.entities.Moneda;
import com.imocom.intelcom.persistence.entities.Producto;
import com.imocom.intelcom.persistence.entities.TipoProducto;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author juan.toledo
 */
public class ProductoVO {

    private final Long idProducto;
    private final String codigo;
    private final String descripcion;
    private final BigDecimal precio;
    private final String estado;
    private final Integer cotizable;
    private final Integer catalogo;
    private final Long idModelo;
    private final Long idMoneda;
    private final Long tipoProducto;
    private final String incoterm;

    private Moneda toMoneda() {
        return new Moneda(new BigDecimal(idMoneda));
    }

    private Modelo toModelo() {
        return new Modelo(idModelo);
    }

    private TipoProducto toTipoProducto() {
        return new TipoProducto(tipoProducto);
    }

    public Producto toProducto() {
        Producto producto = new Producto();
        producto.setCodigo(this.codigo);
        producto.setCatalogo(BigInteger.valueOf(this.catalogo));
        producto.setCotizable(BigInteger.valueOf(cotizable));
        producto.setDescripcion(descripcion);
        producto.setEstado(this.estado);
        producto.setPrecio(precio);
        producto.setIdModelo(toModelo());
        producto.setIdMoneda(toMoneda());
        producto.setIdTipo(toTipoProducto());
        producto.setIdProducto(idProducto);
        producto.setIncoterm(this.incoterm);
        return producto;
    }

    public ProductoVO(Builder builder) {
        this.codigo = builder.codigo;
        this.descripcion = builder.descripcion;
        this.precio = builder.precio;
        this.estado = builder.estado;
        this.cotizable = builder.cotizable;
        this.catalogo = builder.catalogo;
        this.idModelo = builder.idModelo;
        this.idMoneda = builder.idMoneda;
        this.tipoProducto = builder.tipoProducto;
        this.idProducto=builder.idProducto;
        this.incoterm=builder.incoterm;
    }

    public static class Builder {

        private String codigo;
        private String descripcion;
        private BigDecimal precio;
        private String estado;
        private Integer cotizable;
        private Integer catalogo;
        private Long idModelo;
        private Long idMoneda;
        private Long tipoProducto;
        private Long idProducto;
        private String incoterm;

        public Builder() {
        }

        public Builder codigo(String p) {
            this.codigo = p;
            return this;
        }

        public Builder descripcion(String p) {
            this.descripcion = p;
            return this;
        }

        public Builder precio(BigDecimal p) {
            this.precio = p;
            return this;
        }

        public Builder estado(String p) {
            this.estado = p;
            return this;
        }

        public Builder cotizable(String p) {
            if (PerteneceValores.SI.name().equals(p)) {
                this.cotizable = 1;
            } else {
                this.cotizable = 0;
            }
            return this;
        }

        public Builder catalogo(String p) {
            if (PerteneceValores.SI.name().equals(p)) {
                this.catalogo = 1;
            } else {
                this.catalogo = 0;
            }
            return this;
        }

        public Builder idModelo(Long p) {
            this.idModelo = p;
            return this;
        }

        public Builder idMoneda(Long p) {
            this.idMoneda = p;
            return this;
        }

        public Builder tipoProducto(Long p) {
            this.tipoProducto = p;
            return this;
        }
        
        public Builder idProducto(Long p) {
            this.idProducto = p;
            return this;
        }
        
        public Builder incoterm(String p) {
            this.incoterm = p;
            return this;
        }

        public ProductoVO build() {
            return new ProductoVO(this);
        }

    }

    @Override
    public String toString() {
        return "ProductoVO{" + "idProducto=" + idProducto + ", codigo=" + codigo + ", descripcion=" + descripcion + ", precio=" + precio + ", estado=" + estado + ", cotizable=" + cotizable + ", catalogo=" + catalogo + ", idModelo=" + idModelo + ", idMoneda=" + idMoneda + ", tipoProducto=" + tipoProducto + ", incoterm=" + incoterm + '}';
    }

    

}
