package com.ujm.xmltech.services;

import com.ujm.xmltech.entity.Transaction;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;

public interface TransactionService {

    void createTransaction(PaymentInstructionInformation4 transaction);

    Transaction getTransactionById(long id);

}
