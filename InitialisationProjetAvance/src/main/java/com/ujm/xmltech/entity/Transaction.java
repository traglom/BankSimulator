package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    
    private long amount;
    private String end_to_end_id, debtor, creditor, date, currency, sequence, file_id;
    private static final long serialVersionUID = 8315057757268890401L;
}
