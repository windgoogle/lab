package com.woo.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String pattern ="queryTimeout=(\\d+)(.*)";
        Pattern regex=Pattern.compile(pattern);
        Matcher m=regex.matcher("QueryTimeoutInterceptor(queryTimeout=12)");
        if(m.find()){
            System.out.println(m.group(0));
            System.out.println(m.group(1));
            System.out.println(m.group(2));

        }
    }
}
