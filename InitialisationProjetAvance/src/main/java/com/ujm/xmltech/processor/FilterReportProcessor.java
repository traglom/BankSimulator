package com.ujm.xmltech.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ujm.xmltech.model.Report;
import com.ujm.xmltech.services.TransactionService;

public class FilterReportProcessor implements ItemProcessor<Report, Report> {

  private String ignored = "joel";
  private String anonym = "leeyy";
  private long threshold = 28000000;

  @Autowired
  TransactionService service;

  @Override
  public Report process(Report item) throws Exception {

    if (ignored.equals(item.getName())) {
      return null;
    } else if (anonym.equals(item.getName())) {
      item.setName("******");
    }
    if (item.getIncome() * item.getAge() > threshold) {
      item.setRisk(true);
    }
    if (StringUtils.hasText(item.getName())) {
      item.setName(item.getName().toUpperCase());
    }
    //For the project
    service.createTransaction();

    return item;
  }
}