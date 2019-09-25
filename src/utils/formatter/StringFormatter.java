package utils.formatter;

import utils.BinaryUtils;

import java.nio.charset.StandardCharsets;

/**
 * Formatter for the string format.
 */
public class StringFormatter implements FormatterInterface{

    private static StringFormatter instance = null;

    @Override
    public String formatValue(String value)
    {
         StringBuilder output = new StringBuilder();

         for(byte valueByte : value.getBytes(StandardCharsets.UTF_8))
         {
             //Transforming byte into binary string ensuring every byte is written on 8 bits
             output.append(String.format("%8s",Integer.toBinaryString(valueByte & 0xFF)).replace(' ', '0'));
         }

         return output.toString();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static StringFormatter getInstance()
    {
        if(instance == null)
        {
            instance = new StringFormatter();
        }

        return instance;
    }

    @Override
    public String parseValue(String rawBinary) {
        return BinaryUtils.binaryStringToString(rawBinary);
    }
}
