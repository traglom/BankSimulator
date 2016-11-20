/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ujm.xmltech.factory;

import com.ujm.xmltech.entity.File;
import iso.std.iso._20022.tech.xsd.pain_008_001.GroupHeader39;

/**
 *
 * @author nicolas
 */
public class FileFactory {
    
    /**
     * Creat a File
     * @param head
     * @return File
     */
    public File creatFile(GroupHeader39 head) {
        File file = new File();
        
        file.setFile_id(head.getMsgId());
        file.setName("???");
        file.setDone(false);
        
        return file;
    }
}
