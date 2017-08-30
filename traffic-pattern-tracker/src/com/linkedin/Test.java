package com.linkedin;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;


public class Test {
  public static void main(String[] args)
      throws ParseException, IOException {

    String line = "2017/08/27 00:00:00.008 [(lor1-app0330.prod.linkedin.com,l1proxy,voyager/api/feed/updates,E8XkQ9uK3hQAdxfzFisAAA==,2017/08/26 23:59:59.950)[lor1-app22161,voyager-api-feed,/feed/updates?numComments=2&numLikes=2&q=findFeed&moduleKey=home-feed:phone&count=20 GET][lor1-app22226,feed-mixer,/feedItems?q=useCase&viewer=urn%3Ali%3Amember%3A78571936&useCase=PHONE_HOMEPAGE_VOYAGER&count=20&start=0&locale=%28country:US%2Clanguage:en%29 GET][lor1-app6139,cloudsession,/edges/ GET]] [p_flagship3_feed_pill] [E8XkQ9uK3hQAdxfzFisAAA==] [PublicAccessLog] (/edges/(type:MemberToAnet)?fields=to&filter=State%20%3D%203&froms=List(urn%3Ali%3Amember%3A78571936)&q=bulkFromAndSingleType [GET finder:bulkFromAndSingleType]) = (SC:200, size:949) in 2ms (secure=true,ipVersion=4,http=HTTP/1.1,streaming=true,traceEnabled=true,debugEnabled=false)";
    Matcher matcher = Common.pattern.matcher(line);

    if (matcher.find()) {
      LogData logData = new LogData(matcher);
    }
  }
}
