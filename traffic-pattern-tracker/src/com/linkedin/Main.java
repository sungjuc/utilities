package com.linkedin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.transformation.SortedList;


public class Main {
  public static int counter = 0;

  public static void main(String[] args)
      throws IOException, InterruptedException, ParseException {
    test();
  }

  public static void mssh(LogData logData, String dateTime)
      throws IOException, InterruptedException {
    System.out.println(String.format("[mssh] logData.treeId = %s, dataTime = %s", logData.treeId, dateTime));
    counter++;
    String fileName = "tmp/result" + counter + ".txt";
    Process p = Runtime.getRuntime().exec("./test_runner.sh " + logData.treeId + " " + dateTime + " " + fileName);
    PrintStream out = new PrintStream(p.getOutputStream());
    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while (in.ready()) {
      String s = in.readLine();
      System.out.println(s);
    }
    out.println("exit");

    p.waitFor();

    System.out.println("done");

    //post processing
    File report = new File(fileName);
    List<LogData> tempResult = new ArrayList<LogData>();
    tempResult.add(logData);

    BufferedReader reader = new BufferedReader(new FileReader(report));
    String line = null;
    while ((line = reader.readLine()) != null) {
      Matcher matcher = Common.pattern.matcher(line);
      if (matcher.find()) {
        try {
          LogData data = new LogData(matcher);
          tempResult.add(data);
        } catch (Exception e) {

        }
      }
    }
    report.delete();

    if (tempResult.size() > 1) {
      System.out.println("=======================================================");
      Collections.sort(tempResult);
      System.out.println("result size: " + tempResult.size());
      System.out.println("-----------------------------------------------------");
      for (LogData data : tempResult) {
        System.out.println(data.topCaller);
        System.out.println(data.pageKey);
        System.out.println(data.callStacks);
        System.out.println(data.api);
        System.out.println("-----------------------------------------------------");
      }
      System.out.println("=======================================================");
    }
  }

  public static void test()
      throws IOException, InterruptedException, ParseException {

    //String dateTime= "((?:19|20)\\d\\d)/(0?[1-9]|1[012])/([12][0-9]|3[01]|0?[1-9]) ([0-1][0-9]:[0-9][0-9]"
    //    + ":[0-9][0-9].[0-9][0-9][0-9])";

    System.out.println("HEy");
    FileListFilter dummy = new FileListFilter();
    LogStorage logStorage = new LogStorage("lor1-app6139.prod/", dummy);

    String line = null;
    while ((line = logStorage.readLine()) != null) {
      Matcher matcher = Common.pattern.matcher(line);
      if (matcher.matches()) {
        LogData logData = new LogData(matcher);
        String newDate = logData.date.substring(0, logData.date.indexOf(":"));
        newDate = newDate.replaceAll("/", "-");
        newDate = newDate.replaceAll(" ", "-");
        mssh(logData, newDate);
      }
    }
  }
}


