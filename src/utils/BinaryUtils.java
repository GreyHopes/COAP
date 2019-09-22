package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class BinaryUtils
{
    private static final byte[] EMPTY_ARRAY = {};

    public static byte[] binaryStringToBytes(String s)
    {
        if(s.length() == 0)
        {
            return EMPTY_ARRAY;
        }

        return new BigInteger(s, 2).toByteArray();
    }

    public static String bytesToString(byte[] bytes)
    {
        if(bytes == null)
        {
            return "";
        }

        return new String(bytes,0,bytes.length, StandardCharsets.UTF_8);
    }

    public static String binaryStringToString(String s)
    {
        return bytesToString(binaryStringToBytes(s));
    }
}
