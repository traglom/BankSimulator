package com.ujm.xmltech.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ujm.xmltech.adapter.JaxbDateAdapter;

@XmlRootElement(name = "record")
public class Report {

  private int refId;
  private String name;
  private int age;
  private Date dob;
  private long income;
  private boolean risk;

  @XmlAttribute(name = "refId")
  public int getRefId() {
    return refId;
  }

  public void setRefId(int refId) {
    this.refId = refId;
  }

  @XmlElement(name = "age")
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @XmlElement
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlJavaTypeAdapter(JaxbDateAdapter.class)
  @XmlElement
  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  @XmlElement
  public long getIncome() {
    return income;
  }

  public void setIncome(long income) {
    this.income = income;
  }

  // for csv demo only
  public String getCsvDob() {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(getDob());

  }

  public void setRisk(boolean risk) {
    this.risk = risk;
  }

  public boolean isRisk() {
    return risk;
  }

}