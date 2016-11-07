package com.ujm.xmltech.dao;

import com.ujm.xmltech.entity.Transaction;

public interface TransactionDao {

  void createTransaction(Transaction transaction);

  Transaction findTransactionById(long id);

}
