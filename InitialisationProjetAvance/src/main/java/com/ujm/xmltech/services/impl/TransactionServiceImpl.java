package com.ujm.xmltech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iso.std.iso._20022.tech.xsd.pain_008_001.CustomerDirectDebitInitiationV02;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.factory.ErrorCodeFactory;
import com.ujm.xmltech.factory.FileFactory;
import com.ujm.xmltech.factory.TransactionFactory;
import com.ujm.xmltech.services.TransactionService;
import com.ujm.xmltech.utils.BankSimulationConstants;
import com.ujm.xmltech.utils.Banks;
import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        String nameReport = BankSimulationConstants.MY_BANK_IDENTIFIER+"-"+
                Calendar.getInstance().get(Calendar.DATE)+"/"+
                (Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+
                Calendar.getInstance().get(Calendar.YEAR)+"-"+
                Calendar.getInstance().get(Calendar.HOUR)+":"+
                Calendar.getInstance().get(Calendar.MINUTE);
        
        /* File already exist */
        if(dao.findFileByName(debitInitiation.getGrpHdr().getMsgId()) != null) {
            System.out.println("File already exists");
            return;
        }
        
        /* Creat processor */
        FileFactory fileFactory = new FileFactory();
        TransactionFactory transactionFactory = new TransactionFactory();

        try {
            /* Persist File */
            File file = fileFactory.creatFile(debitInitiation.getGrpHdr(), debitInitiation.getGrpHdr().getMsgId());
            dao.createFile(file);

            /* Persist Transaction & ErrorCode */
            Iterator<PaymentInstructionInformation4> itDebitInitiation = debitInitiation.getPmtInf().iterator();
            while (itDebitInitiation.hasNext()) {
                PaymentInstructionInformation4 itDebitInitiationCurrent = itDebitInitiation.next();
                Iterator<DirectDebitTransactionInformation9> itDebitTransaction = itDebitInitiationCurrent.getDrctDbtTxInf().iterator();
                String date = itDebitInitiationCurrent.getReqdColltnDt().toString();
                while (itDebitTransaction.hasNext()) {
                    /* Persist & Check */
                    checker(dao.createTransaction(transactionFactory.creatTransaction(
                            itDebitTransaction.next(),
                            file,
                            date)));
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

    private Transaction checker(Transaction transaction) throws ParseException {
        ErrorCodeFactory errorCodeFactory = new ErrorCodeFactory();

        for (Banks bank : Banks.values()) {
            if (!transaction.getCreditor().equals(bank.toString())) {
                dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC000"));
                break;
            }
        }
        if (transaction.getAmount() < 1) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC001"));
        }
        if (transaction.getAmount() < 1) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC001"));
        }
        if (transaction.getAmount() > 10000) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC002"));
        }
        if (!"EUR".equals(transaction.getCurrency())) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC003"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

        /* Current date */
        Calendar current = Calendar.getInstance();
        current.setTime(current.getTime());

        /* File date */
        Calendar fileDate = Calendar.getInstance();
        fileDate.setTime(sdf.parse(transaction.getDate()));

        if (fileDate.before(current)) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC004"));
        }

        /* Over 13 month */
        Calendar toofar = Calendar.getInstance();
        toofar.setTime(toofar.getTime());
        toofar.add(Calendar.MONTH, 13);

        if (fileDate.after(toofar)) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC005"));
        }

        /* Over 2 days*/
        Calendar twoDays = Calendar.getInstance();
        twoDays.setTime(twoDays.getTime());
        twoDays.add(Calendar.DAY_OF_MONTH, 2);

        if (fileDate.before(twoDays) && "RCUR".equals(transaction.getSequence())) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC006"));
        }

        /* Over 5 days */
        Calendar fiveDays = Calendar.getInstance();
        fiveDays.setTime(fiveDays.getTime());
        fiveDays.add(Calendar.DAY_OF_MONTH, 5);

        if (fileDate.before(fiveDays) && "FRST".equals(transaction.getSequence())) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC007"));
        }
        
        
        /* Have no FRST */
        if ("RCUR".equals(transaction.getSequence()) && dao.haveAFrstEntry(transaction.getEnd_to_end_id()) == null) {
            dao.createErrorCode(errorCodeFactory.creatErrorCode(transaction, "RJC008"));
        }

        return transaction;
    }
}
