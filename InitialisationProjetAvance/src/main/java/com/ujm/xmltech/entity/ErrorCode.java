package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ErrorCode implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    
    private long transation_id;
    
    private String code;
}
