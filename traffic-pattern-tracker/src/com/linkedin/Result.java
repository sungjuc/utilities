package com.linkedin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Result {

  List<LogData> _logDataList;

  Result(List<LogData> logDataList) {
    _logDataList = logDataList;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("-------------------------------------------------------------------------------------------------------------------------------------------\n");
    Collections.sort(_logDataList);

    int recordCount = 1;

    List<String> currentStack = new ArrayList<String>();
    for (LogData logData: _logDataList) {
      int level = 0;
      boolean needToPrint = true;
      for (String stack: logData.callStacks) {
        if (currentStack.size() <= level) {
          //first visit
          currentStack.add(level, stack);
          needToPrint = true;
        } else if (currentStack.get(level).equals(stack)) {
          needToPrint = false;
        } else {
          int currentStackSize = currentStack.size();
          for (int i = currentStackSize - 1; i >= level; i-- ) {
            currentStack.remove(i);
          }
          currentStack.add(level, stack);
          needToPrint = true;
        }

        if (needToPrint) {
          for (int i = 0; i< level; i++) {
            sb.append(" --- ");
          }
          sb.append(stack).append("\n");
        }
        level++;
      }
      sb.append(recordCount).append(" ========> ").append(logData.host).append(" [ ").append(logData.treeId).append(" ").append(logData.unixTime).append(" ] ").append(logData.api).append("\n");
      recordCount++;
    }
    return sb.toString();
  }
}
