package com.woo.base.string;

public class MyTest {
    public static void main(String[] args) {
        String str1 = "aaaaa";
        String str2 = "dd";
        String str3="DD";
        System.out.println("-----:"+"dd".compareTo("aaaaa"));
        System.out.println("-----:"+"F".compareTo("a"));
        System.out.println("-----:"+"fffff".compareTo("DD"));
        System.out.println("-----:"+"EEEEE".compareTo("dd"));
        System.out.println("-----:"+compareIgnoreCase("fffff","DD"));
    }



    /**
     * Compare given char chunk with String ignoring case.
     * Return -1, 0 or +1 if inferior, equal, or superior to the String.
     */
    public static final int compareIgnoreCase( String name,
                                              String compareTo) {
        int result = 0;
        int start=0;
        int end=name.length();
        //char[] c = name.getBuffer();
        char[] c = name.toCharArray();
        int len = compareTo.length();
        if ((end - start) < len) {
            len = end - start;
        }
        for (int i = 0; (i < len) && (result == 0); i++) {
            if (Ascii.toLower(c[i + start]) > Ascii.toLower(compareTo.charAt(i))) {
                result = 1;
            } else if (Ascii.toLower(c[i + start]) < Ascii.toLower(compareTo.charAt(i))) {
                result = -1;
            }
        }
        if (result == 0) {
            if (compareTo.length() > (end - start)) {
                result = -1;
            } else if (compareTo.length() < (end - start)) {
                result = 1;
            }
        }
        return result;
    }
}

