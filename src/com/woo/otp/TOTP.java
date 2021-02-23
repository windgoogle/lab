package com.woo.otp;

import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TOTP {

    // TC = floor((unixtime(now) − unixtime(T0)) / TS)
    // TC = floor(unixtime(now) / 30)
    // TOTP = HASH(SecretKey, TC)
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("\\d");
        String key = UUID.randomUUID().toString().replace("-", "");

        for (int i = 0; i < 70; i++) {

            String TC = String.valueOf((int) Math.floor(new Date().getTime() / 1000 / 30));
            String TOTP = sha1(TC + key);

            Matcher matcher = pattern.matcher(TOTP);
            String result = "";
            while (matcher.find()) {
                result += matcher.group();
            }
            result = result.substring(result.length() - 6);
            System.out.println(i + "  --  " + result);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String sha1(String srcStr) {
        return hash("SHA-1", srcStr);
    }

    public static String hash(String algorithm, String srcStr) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
