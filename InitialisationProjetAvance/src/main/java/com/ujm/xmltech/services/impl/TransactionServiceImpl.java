package com.ujm.xmltech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.services.TransactionService;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionDao dao;

  public void createTransaction() {
    Transaction transaction = new Transaction();
    transaction.setAmount(600);
    transaction.setEndToEndId("uniqIdentifier");
    System.out.println("----------------PASSE------------");
    dao.createTransaction(transaction);
  }
  
  public Transaction getTransactionById(long id){
	  return dao.findTransactionById(id);
  }
  
}
