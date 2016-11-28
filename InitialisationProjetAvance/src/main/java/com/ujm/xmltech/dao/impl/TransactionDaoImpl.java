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
    public Transaction createTransaction(Transaction transaction) {
        entityManager.persist(transaction);
        entityManager.flush();
        return transaction;
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
    public File findFileByName(String name) {
        Query query = entityManager.createQuery("SELECT f FROM File f WHERE f.name = :name").setParameter("name", name);
        try {
            return (File) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Transaction haveAFrstEntry(String endToEndId) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.end_to_end_id = :endToEndId AND t.sequence = 'FRST'").setParameter("endToEndId", endToEndId);
        try {
            return (Transaction) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createFile(File file) {
        entityManager.persist(file);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createErrorCode(ErrorCode errorCode) {
        entityManager.persist(errorCode);
    }
}
