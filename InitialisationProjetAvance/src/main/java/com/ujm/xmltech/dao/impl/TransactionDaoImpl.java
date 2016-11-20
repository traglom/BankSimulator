package com.ujm.xmltech.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.ErrorCode;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.utils.BankSimulationConstants;

@Repository("TransactionDao")
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext(unitName = BankSimulationConstants.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createTransaction(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public Transaction findTransactionById(long id) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.id = :id").setParameter("id", id);
        return (Transaction) query.getSingleResult();
        //        Les QObject sont generes par le plugin apt. Si vous ne parvenez pas a le faire fonctionner alors utilisez la maniere ci-dessus.
        //        JPAQuery q = new JPAQuery();
        //        QTransaction transaction = QTransaction.transaction;
        //        q.from(transaction);
        //        q.where(transaction.id.eq(id));
        //        return q.uniqueResult(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createFile(File file) {
        entityManager.persist(file);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createErrorCode(ErrorCode errorCode) {
        System.out.println(errorCode.getCode());
        entityManager.persist(errorCode);
    }
}
