package com.ujm.xmltech.services;

import com.ujm.xmltech.entity.Transaction;
import iso.std.iso._20022.tech.xsd.pain_008_001.CustomerDirectDebitInitiationV02;

public interface TransactionService {

    void createTransaction(CustomerDirectDebitInitiationV02 debitInitiation);

    Transaction getTransactionById(long id);

}
