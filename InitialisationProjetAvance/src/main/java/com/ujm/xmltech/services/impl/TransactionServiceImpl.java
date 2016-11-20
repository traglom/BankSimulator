package com.ujm.xmltech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.processor.FilterTransactionProcessor;
import com.ujm.xmltech.services.TransactionService;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao dao;

    /**
     * Persist transaction informations
     *
     * @param transactionData
     */
    @Override
    public void createTransaction(PaymentInstructionInformation4 transactionData) {
        Iterator <DirectDebitTransactionInformation9> it = transactionData.getDrctDbtTxInf().iterator();
        while (it.hasNext()) {
            FilterTransactionProcessor filterTranPro = new FilterTransactionProcessor();
            try {
                dao.createTransaction(filterTranPro.process(it.next()));
            } catch (Exception ex) {
                Logger.getLogger(TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Transaction getTransactionById(long id) {
        return dao.findTransactionById(id);
    }

}
