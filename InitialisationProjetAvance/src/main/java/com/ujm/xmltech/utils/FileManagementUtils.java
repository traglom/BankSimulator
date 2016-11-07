package com.ujm.xmltech.utils;

import java.io.File;

public class FileManagementUtils {

  public static File retrieveFileToProcess() {
    File toReturn = null;
    File folder = new File(BankSimulationConstants.IN_DIRECTORY);
    for (File file : folder.listFiles()) {
      System.out.println("File found : " + file.getName());
      toReturn = file;
    }
    return toReturn;
  }

}
