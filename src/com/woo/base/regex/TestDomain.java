package com.woo.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDomain {

    public static void main(String[] args) {
       /*
        String regEx = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,62}:([1-9][0-9]*)$";

        String testStr="www.ddddd.comd:10111111";

        Pattern pattern=Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(testStr);
        if(matcher.find())
           System.out.println(matcher.group());
        else
            System.out.println("not found !");


        String reg2="^[\\s]*((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))[\\s]*(:[1-9][0-9]*)$";
        String testStr2="192.169.12.22:90902222";

        Pattern pattern2=Pattern.compile(reg2);
        Matcher matcher2 = pattern2.matcher(testStr2);
        if(matcher2.find())
            System.out.println(matcher2.group());
        else
            System.out.println("not found !");
            */


       String regex="(%\\{\\w+\\}t)";
       String testStr="%"+"{yyyyMMddHHmmss\\}t %U %m %a %D time 1527067898568";

       if(Pattern.matches(testStr,regex)){
           System.out.println("true");
       }

    }
}
