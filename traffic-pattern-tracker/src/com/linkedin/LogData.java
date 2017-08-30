package com.linkedin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
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

  LogData(Matcher matcher)
      throws ParseException {
    topCaller = matcher.group(1);
    originalCallId = matcher.group(2).trim();
    date = matcher.group(3);
    callStacks = buildCallStack(matcher.group(4));
    pageKey = matcher.group(5);
    treeId = matcher.group(6).trim();
    api = matcher.group(7);

    unixTime = (date == null) ? -1L : format.parse(date).getTime();
  }

  public boolean equals(LogData obj) {
    return (this.treeId == obj.treeId && this.topCaller == obj.topCaller);
  }

  public List<String> buildCallStack(String stackString) {
    List<String> result = new LinkedList<String>();

    String tempString = stackString;
    tempString = tempString.replaceAll("]", "");
    tempString = tempString.replaceAll("[\\[]", "\n");

    return Arrays.asList(tempString.split("\n"));
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(date).append("\n");
    sb.append(api).append("\n");
    return sb.toString();
  }

  @Override
  public int compareTo(LogData o) {
    int diff =  (int) (this.unixTime - o.unixTime);
    if (diff != 0)
      return diff;
    return compareCallStack(this.callStacks, o.callStacks);
  }

  private int compareCallStack(List<String> callStacks, List<String> callStacks1) {
    int idx = 0;
    return 0;
  }
}
