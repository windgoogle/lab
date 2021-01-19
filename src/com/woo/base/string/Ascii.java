package com.woo.base.string;

/**
        * This class implements some basic ASCII character handling functions.
        *
        * @author dac@eng.sun.com
 * @author James Todd [gonzo@eng.sun.com]
        */
public  class Ascii {
    /*
     * Character translation tables.
     */
    public static final byte[] toLower = new byte[256];

    /*
     * Character type tables.
     */
    public static final boolean[] isDigit = new boolean[256];

    public static final long OVERFLOW_LIMIT = Long.MAX_VALUE / 10;

    /*
     * Initialize character translation and type tables.
     */
    static {
        for (int i = 0; i < 256; i++) {
            toLower[i] = (byte)i;
        }

        for (int lc = 'a'; lc <= 'z'; lc++) {
            int uc = lc + 'A' - 'a';

            toLower[uc] = (byte)lc;
        }

        for (int d = '0'; d <= '9'; d++) {
            isDigit[d] = true;
        }
    }

    /**
     * Returns the lower case equivalent of the specified ASCII character.
     * @param c The char
     * @return the lower case equivalent char
     */
    public static int toLower(int c) {
        return toLower[c & 0xff] & 0xff;
    }

    /**
     * @return <code>true</code> if the specified ASCII character is a digit.
     * @param c The char
     */
    public static boolean isDigit(int c) {
        return isDigit[c & 0xff];
    }

    /**
     * Parses an unsigned long from the specified subarray of bytes.
     * @param b the bytes to parse
     * @param off the start offset of the bytes
     * @param len the length of the bytes
     * @return the long value
     * @exception NumberFormatException if the long format was invalid
     */
    public static long parseLong(byte[] b, int off, int len)
            throws NumberFormatException
    {
        int c;

        if (b == null || len <= 0 || !isDigit(c = b[off++])) {
            throw new NumberFormatException();
        }

        long n = c - '0';
        while (--len > 0) {
            if (isDigit(c = b[off++]) &&
                    (n < OVERFLOW_LIMIT || (n == OVERFLOW_LIMIT && (c - '0') < 8))) {
                n = n * 10 + c - '0';
            } else {
                throw new NumberFormatException();
            }
        }

        return n;
    }
}