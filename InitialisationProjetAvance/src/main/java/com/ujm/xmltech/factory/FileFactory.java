/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ujm.xmltech.factory;

import com.ujm.xmltech.entity.File;
import com.ujm.xmltech.utils.BankSimulationConstants;
import iso.std.iso._20022.tech.xsd.pain_008_001.GroupHeader39;
import java.util.Calendar;

/**
 *
 * @author nicolas
 */
public class FileFactory {
    
    /**
     * Creat a File
     * @param head
     * @param name
     * @return File
     */
    public File creatFile(GroupHeader39 head, String name) {
        File file = new File();
        
        file.setFile_id(head.getMsgId());
        file.setName(name);
        file.setDone(false);
        
        return file;
    }
}
