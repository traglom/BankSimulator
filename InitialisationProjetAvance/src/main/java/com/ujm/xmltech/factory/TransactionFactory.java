package com.ujm.xmltech.factory;

import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.entity.Transaction;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;

/**
 *
 * @author nicolas
 */
public class TransactionFactory {

    /**
     * Creat a transaction
     * @param debitTransaction
     * @param file
     * @return Transaction
     */
    public Transaction creatTransaction(DirectDebitTransactionInformation9 debitTransaction, File file) {
        Transaction transaction = new Transaction();
        
        transaction.setDebtor("???");
        transaction.setCreditor("???");
        transaction.setAmount(debitTransaction.getInstdAmt().getValue().longValue());
        transaction.setDate(debitTransaction.getDrctDbtTx().getMndtRltdInf().getDtOfSgntr().toString());
        transaction.setEnd_to_end_id(debitTransaction.getPmtId().getEndToEndId());
        transaction.setFile_id(file.getId());

        return transaction;
    }
}