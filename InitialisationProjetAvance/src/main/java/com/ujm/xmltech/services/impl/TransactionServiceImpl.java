package com.ujm.xmltech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iso.std.iso._20022.tech.xsd.pain_008_001.CustomerDirectDebitInitiationV02;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.factory.FileFactory;
import com.ujm.xmltech.factory.TransactionFactory;
import com.ujm.xmltech.processor.FilterTransactionProcessor;
import com.ujm.xmltech.services.TransactionService;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;
import java.util.Iterator;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao dao;
    /**
     * Persist transaction informations
     *
     * @param debitInitiation
     */
    @Override
    public void createTransaction(CustomerDirectDebitInitiationV02 debitInitiation) {
        /* Creat processor */
        FileFactory fileFactory = new FileFactory();
        TransactionFactory transactionFactory = new TransactionFactory();
        
        /* Errors */
        FilterTransactionProcessor filterTransactionPro = new FilterTransactionProcessor();

        try {
            /* Persist File */
            File file = fileFactory.creatFile(debitInitiation.getGrpHdr());
            dao.createFile(file);

            /* Persist Transaction & ErrorCode */
            Iterator<PaymentInstructionInformation4> itDebitInitiation = debitInitiation.getPmtInf().iterator();
            while (itDebitInitiation.hasNext()) {
                Iterator<DirectDebitTransactionInformation9> itDebitTransaction = itDebitInitiation.next().getDrctDbtTxInf().iterator();
                while (itDebitTransaction.hasNext()) {
                    dao.createTransaction(
                            filterTransactionPro.process(
                                    transactionFactory.creatTransaction(
                                            itDebitTransaction.next(), file)));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Transaction getTransactionById(long id) {
        return dao.findTransactionById(id);
    }

}
