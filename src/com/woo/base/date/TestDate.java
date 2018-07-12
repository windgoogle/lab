package com.woo.base.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TestDate {


    public static void main(String[] args) throws Exception{
        String teststr="20180523172944462";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=sdf.parse(teststr);
        System.out.println(date.getTime());
        //SimpleDateFormat sdf2=new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        //sdf2.setTimeZone(TimeZone.getDefault());
      //  String test2="23/May/2018:17:31:48 +0800";
       // System.out.println(sdf2.parse(test2));
       // SimpleDateFormat sdf2=new SimpleDateFormat();
       // String dateStr=sdf2.format(new Date());
        //System.out.println(dateStr);

      //  System.out.println(sdf2.format(new Date()));
    }
}
