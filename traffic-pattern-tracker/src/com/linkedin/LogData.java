package com.linkedin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;


public class LogData implements Comparable<LogData> {
  public static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");
  String treeId;
  String originalCallId;
  String topCaller;
  String date;
  Long unixTime;
  List<String> callStacks = null;
  String pageKey;
  String api;
  String host;

  final String originalLog;

  LogData(Matcher matcher, String originalLog)
      throws ParseException {
    topCaller = matcher.group(1);
    originalCallId = matcher.group(2).trim();
    date = matcher.group(3);
    callStacks = buildCallStack(matcher.group(4));
    pageKey = matcher.group(5);
    treeId = matcher.group(6).trim();
    api = matcher.group(7);

    this.originalLog = originalLog;

    unixTime = (date == null) ? -1L : format.parse(date).getTime();
  }

  public boolean equals(LogData obj) {
    return (this.treeId == obj.treeId && this.topCaller == obj.topCaller);
  }

  public List<String> buildCallStack(String stackString) {
    List<String> result = new ArrayList<String>();
    try {
      if (stackString.contains("]")) {
        String tempString = stackString;
        tempString = tempString.replaceAll("]", "");
        tempString = tempString.replaceAll("[\\[]", "\n");
        host = tempString.substring(tempString.lastIndexOf("\n") + 1);
        tempString = tempString.substring(0, tempString.lastIndexOf("\n"));
        result =  Arrays.asList(tempString.split("\n"));
      } else {
        result.add(topCaller);
        host = stackString;
      }
    } catch (Exception e) {
      System.out.println("XXXXX Exception during buildCallStack processing.");
      System.out.println(originalLog);
      result = new ArrayList<String>();
    }
    return result;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(date).append("\n");
    sb.append(api).append("\n");
    return sb.toString();
  }

  @Override
  public int compareTo(LogData o) {
    int diff = (int) (this.unixTime - o.unixTime);
    if (diff != 0) {
      System.out.println("XXXXX Different timestamp");
      System.out.println(this.originalLog);
      System.out.println(o.originalLog);
      return diff;
    }
    return compareCallStack(this.callStacks, o.callStacks);
  }

  private int compareCallStack(List<String> callStacks, List<String> callStacks1) {
    int idx = Math.min(callStacks.size(), callStacks1.size());
    int diff = 0;
    for (int i = 0; i < idx; i++) {
      diff = callStacks.get(i).compareTo(callStacks1.get(i));
      if (diff != 0) {
        return diff;
      }
    }

    diff = callStacks.size() - callStacks1.size();

    return diff;
  }

  public boolean isDarkTraffic() {
    return this.pageKey.contains("DarkCanary");
  }
}
