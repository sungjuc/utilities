package com.linkedin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;


public class FileListFilter implements FilenameFilter {
  private String[] whiteList = null;

  public FileListFilter(String[] whiteList) {
    this.whiteList = whiteList;
  }

  public FileListFilter() {
  }

  @Override
  public boolean accept(File dir, String name) {
    if (whiteList == null)
      return true;

    System.out.println("[FileListFilter] dir: " + dir + "\tname: " + name);
    for (String white: whiteList) {
      if (name.contains(white)) {
        return true;
      }
    }
    return false;
  }

  public String toString() {
    return Arrays.toString(whiteList);
  }
}
