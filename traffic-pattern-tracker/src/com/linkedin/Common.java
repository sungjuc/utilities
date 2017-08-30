package com.linkedin;

import java.util.regex.Pattern;


public class Common {
  public final static String dateTime = "19|20\\d\\d/\\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d";
  public final static String regex = dateTime + " \\[\\((.*),(.*),(" + dateTime
      + ")\\)\\[(.*)\\]\\] \\[(.*)\\] \\[(.*)\\] \\[PublicAccessLog\\] \\((.*)\\)";
  public final static Pattern pattern = Pattern.compile(regex);
}
