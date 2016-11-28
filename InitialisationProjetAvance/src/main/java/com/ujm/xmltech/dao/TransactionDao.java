package com.ujm.xmltech.dao;

import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.entity.ErrorCode;

public interface TransactionDao {

    void createFile(File file);

    Transaction createTransaction(Transaction transaction);
    
    Transaction haveAFrstEntry(String endToEndId);

    File findFileByName(String name);
    
    void createErrorCode(ErrorCode errorCode);

    Transaction findTransactionById(long id);

}
