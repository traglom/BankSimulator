package com.ujm.xmltech.processor;

import org.springframework.batch.item.ItemProcessor;

import com.ujm.xmltech.entity.Transaction;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;

public class FilterTransactionProcessor implements ItemProcessor<DirectDebitTransactionInformation9, Transaction> {

    @Override
    public Transaction process(DirectDebitTransactionInformation9 transactionData) throws Exception {
        Transaction transaction = new Transaction();
        
        transaction.setAmount(transactionData.getInstdAmt().getValue().longValue());
        transaction.setEndToEndId(transactionData.getPmtId().getEndToEndId());
        transaction.setName(transactionData.getPmtTpInf().getInstrPrty().toString());
        transaction.setDate(transactionData.getDrctDbtTx().getMndtRltdInf().getDtOfSgntr().toString());
        transaction.setWorked(1);
        
        if (transaction.getAmount() < 1)
            System.out.println("RJC001");
        if (transaction.getAmount() > 10000)
            System.out.println("RJC002");
        if (!"EUR".equals(transactionData.getInstdAmt().getCcy()))
            System.out.println("RJC003");

        return transaction;
    }
}
