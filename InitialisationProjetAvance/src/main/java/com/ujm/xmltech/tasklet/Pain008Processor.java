package com.ujm.xmltech.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ujm.xmltech.services.TransactionService;

public class Pain008Processor implements Tasklet {

  @Autowired
  private TransactionService service;

  @Override
  public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
    service.createTransaction();
    System.out.println("transaction created");
    return RepeatStatus.FINISHED;
  }

}
