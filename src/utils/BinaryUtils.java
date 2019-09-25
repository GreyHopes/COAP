package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Static class with some utilitarian function for handing binary string
 */
public class BinaryUtils
{
    private static final byte[] EMPTY_ARRAY = {};

    /**
     * Binary string to  byte [ ].
     *
     * @param s the string
     * @return the byte [ ]
     */
    public static byte[] binaryStringToBytes(String s)
    {
        if(s.length() == 0)
        {
            return EMPTY_ARRAY;
        }

        return new BigInteger(s, 2).toByteArray();
    }

    /**
     * Bytes to UTF_8 string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String bytesToString(byte[] bytes)
    {
        if(bytes == null)
        {
            return "";
        }

        return new String(bytes,0,bytes.length, StandardCharsets.UTF_8);
    }

    /**
     * Binary string to UTF_8 string.
     *
     * @param s the binary string
     * @return the string
     */
    public static String binaryStringToString(String s)
    {
        return bytesToString(binaryStringToBytes(s));
    }
}
