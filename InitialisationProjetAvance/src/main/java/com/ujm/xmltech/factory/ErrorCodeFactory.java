/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ujm.xmltech.factory;

import com.ujm.xmltech.entity.ErrorCode;
import com.ujm.xmltech.entity.Transaction;

/**
 *
 * @author nicolas
 */
public class ErrorCodeFactory {
    
    /**
     * Creat a ErrorCode
     * @param transaction 
     * @param code 
     * @return ErrorCode
     */
    public ErrorCode creatErrorCode(Transaction transaction, String code) {
        ErrorCode errorCode = new ErrorCode();
        
        errorCode.setTransation_id(transaction.getId());
        errorCode.setCode(code);
        
        return errorCode;
    }
}
