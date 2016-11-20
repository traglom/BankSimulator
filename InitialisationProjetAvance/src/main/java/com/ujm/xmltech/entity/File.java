package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class File implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    
    private String name, file_id;
    private boolean done;
}
