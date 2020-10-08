/*
* Copyright (c) 2014 IMOCOM. All Rights Reserved.
*
* This software is the confidential and proprietary information of IMOCOM.
* ("Confidential Information").
* It may not be copied or reproduced in any manner without the express
* written permission of IMOCOM.
*
*/
package com.imocom.intelcom.commons.exceptions;

/**
 *
 * @author rc
 */
public class CommonException extends Exception {

    /**
     * Creates a new instance of <code>CommonException</code> without detail
     * message.
     */
    public CommonException() {
    }

    /**
     * Constructs an instance of <code>CommonException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CommonException(String msg) {
        super(msg);
    }

    /**
     * 
     * @param message
     * @param cause 
     */
    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }   
}
