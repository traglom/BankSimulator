package com.ujm.xmltech.processor;

import com.ujm.xmltech.dao.TransactionDao;
import org.springframework.batch.item.ItemProcessor;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.factory.ErrorCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FilterTransactionProcessor implements ItemProcessor<Transaction, Transaction> {

    @Autowired
    private TransactionDao dao;
    
    @Override
    public Transaction process(Transaction transaction) throws Exception {
        ErrorCodeFactory errorCodeFactory = new ErrorCodeFactory();
        
        if (transaction.getAmount() < 1) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC001"));
            System.out.println("RJC001");
            transaction.setAmount(0);
        }
        if (transaction.getAmount() > 10000) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC002"));
            System.out.println("RJC002");
            transaction.setAmount(10000);
        }
        if (!"EUR".equals(transaction.getCurrency())) {
            //dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC003"));
            System.out.println("RJC003");
            transaction.setCurrency("EUR");
        }

        return transaction;
    }
}
