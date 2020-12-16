/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.vo;

/**
 *
 * @author juan.toledo
 */
public enum PerteneceValores {
    SI(1),
    NO(0);   
    
    private int value;

    private PerteneceValores(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    } 
}
