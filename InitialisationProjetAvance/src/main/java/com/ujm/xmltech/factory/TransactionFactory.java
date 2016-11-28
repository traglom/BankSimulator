package com.ujm.xmltech.factory;

import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.utils.BankSimulationConstants;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;

/**
 *
 * @author nicolas
 */
public class TransactionFactory {

    /**
     * Creat a transaction
     *
     * @param debitTransaction
     * @param file
     * @param date
     * @return Transaction
     */
    public Transaction creatTransaction(DirectDebitTransactionInformation9 debitTransaction, File file, String date) {
        Transaction transaction = new Transaction();
        
        /* Set data */
        transaction.setDebtor(BankSimulationConstants.MY_BANK_IDENTIFIER);
        transaction.setCreditor(debitTransaction.getDbtrAgt().getFinInstnId().getBIC().substring(0, 4));
        transaction.setCurrency(debitTransaction.getInstdAmt().getCcy());
        transaction.setAmount(debitTransaction.getInstdAmt().getValue().longValue());
        transaction.setDate(date);
        transaction.setEnd_to_end_id(debitTransaction.getPmtId().getEndToEndId());
        transaction.setSequence(debitTransaction.getPmtTpInf().getSeqTp().toString());
        transaction.setFile_id(file.getFile_id());            
        
        return transaction;
    }
}
