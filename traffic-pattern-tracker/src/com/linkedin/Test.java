package com.linkedin;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;


public class Test {
  public static void main(String[] args)
      throws ParseException, IOException {

    String line = "2017/08/27 00:09:34.146 [(lor1-app18748,interest-discovery,dark canary original request tree id: RR1E8mCL3hQATUrMVysAAA==,2017/08/27 00:09:34.130)[lor1-app19028,interest-discovery,/interestDiscoveryActivitiesV2 GET][lor1-app6139,cloudsession,/edges/ GET]] [DarkCanary-p_flagship3_search_srp_content] [kGn1mpEe72wYbCeT/15JcQ==] [PublicAccessLog] (/edges/(type:MemberToMember)?count=2000&fields=to&froms=List(urn%3Ali%3Amember%3A477073024)&q=bulkFromAndSingleType&sortOrder=SCORE_DESC&start=0 [GET finder:bulkFromAndSingleType]) = (SC:200, size:1087) in 1ms (secure=true,ipVersion=4,http=HTTP/1.1,streaming=true,traceEnabled=true,debugEnabled=false)";
    Matcher matcher = Common.pattern.matcher(line);
    LogData logData = null;
    if (matcher.find()) {
      logData = new LogData(matcher, line);
    }

    System.out.println("XXX" + logData.treeId);
    System.out.println("XXX" + logData.originalCallId);
    System.out.println("XXX" + logData.callStacks);
    System.out.println("XXX" + logData.pageKey);
    System.out.println("XXX" + logData.host);
    System.out.println("XXX" + logData.topCaller);
    System.out.println("XXX" + logData.toString());
    System.out.println("XXX" + logData.originalLog);
  }
}
