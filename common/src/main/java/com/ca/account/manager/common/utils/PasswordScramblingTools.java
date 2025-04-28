package com.ca.account.manager.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Scramble a password
 */
public final class PasswordScramblingTools {
    static private final String SCRAMBLED_PREFIX = "scrambled:";
    static private final String CLEARTEXT_PREFIX = "cleartext:";
    static private final Pattern SCRAMBLED_PATTERN = Pattern.compile("^\\d*$");

    /**
     * Private constructor to forbid instantiations of this class
     */
    private PasswordScramblingTools() {
    }

    public static char nineComplement(char i) {
        final int r = Character.getNumericValue(i);
        return Character.forDigit(9 - r, 10);
    }

    public static String scramble(String in) {
        if ((in == null) || (in.trim().length() == 0)) {
            return null;
        }

        final byte[] inbytes = in.getBytes(StandardCharsets.ISO_8859_1);
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < inbytes.length; ++i) {
            if (inbytes[i] < 10) {
                builder.append("00");
                builder.append(Byte.toString(inbytes[i]));
            } else if (inbytes[i] < 100) {
                builder.append("0");
                builder.append(Byte.toString(inbytes[i]));
            } else {
                builder.append(Byte.toString(inbytes[i]));
            }
        }

        final String out = builder.toString();
        final char[] res = out.toCharArray();
        int i = 1;
        int j = ((out.length() / 2) * 2) - 1;
        while (i <= j) {
            final char t0 = nineComplement(res[i]);
            final char t1 = nineComplement(res[j]);
            res[j] = t0;
            res[i] = t1;
            i += 2;
            j -= 2;
        }

        return SCRAMBLED_PREFIX + new String(res);
    }

    public static String descramble(String in) {
        // Special case: empty string
        if ((in == null) || (in.trim().length() == 0)) {
            return null;
        }

        if (in.startsWith(CLEARTEXT_PREFIX)) {
            // Case 1: cleartext prefix set...
            return in.substring(CLEARTEXT_PREFIX.length());
        } else if (in.startsWith(SCRAMBLED_PREFIX)) {
            // Case 2: scrambled prefix set...
            return descrambleReally(in.substring(SCRAMBLED_PREFIX.length()));
        } else {
            // Shitty case... try to guess...
            if (SCRAMBLED_PATTERN.matcher(in).matches()) {
                return descrambleReally(in);
            } else {
                return in;
            }
        }

    }

    private static String descrambleReally(String in) {
        final char[] res = in.toCharArray();
        final int outlen = res.length / 3;
        final int n = res.length / 2;
        int i = 1;
        int j = (n * 2) - 1;
        char t0, t1;
        while (i <= j) {
            t0 = nineComplement(res[i]);
            t1 = nineComplement(res[j]);
            res[j] = t0;
            res[i] = t1;
            i += 2;
            j -= 2;
        }

        try {
            /* Convert numbers to chars */
            final byte[] bytes = new byte[outlen];
            for (i = 0, j = 0; i < outlen; ++i, j += 3) {
                bytes[i] = Byte.parseByte("" + res[j] + res[j + 1] + res[j + 2]);
            }
            return new String(bytes, StandardCharsets.ISO_8859_1);
        } catch (final NumberFormatException nfe) {
            throw new Error("Invalid scrambled password.");
        }
    }
}
