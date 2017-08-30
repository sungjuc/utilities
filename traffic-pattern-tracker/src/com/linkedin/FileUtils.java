package com.linkedin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;


public class FileUtils {
  public static File[] getWhiteListFileList(String dirName, FilenameFilter filter) {
    String dataDirectoryStr = dirName;
    System.out.println("[Utils.getFileList] Getting file list from: " + dataDirectoryStr);
    File dataDirectory = new File(dataDirectoryStr);
    return dataDirectory.listFiles(filter);
  }

  public static File[] getFileList(String dirName) {
    String dataDirectoryStr = dirName;
    System.out.println("[Utils.getFileList] Getting file list from: " + dataDirectoryStr);
    File dataDirectory = new File(dataDirectoryStr);
    return dataDirectory.listFiles();
  }

  public static File[] getDirectoryList(String dirName) {
    String dataDirectoryStr = dirName;
    System.out.println("[Utils.getFileList] Getting file list from: " + dataDirectoryStr);
    File dataDirectory = new File(dataDirectoryStr);

    File[] results = dataDirectory.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    });

    return results;
  }
}
