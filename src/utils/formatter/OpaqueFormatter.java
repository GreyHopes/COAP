package utils.formatter;

import utils.BinaryUtils;

import java.nio.charset.StandardCharsets;

//Opaque format acording to RFC 2608
public class OpaqueFormatter implements FormatterInterface{

    private static OpaqueFormatter instance = null;

    @Override
    public String formatValue(String value) {
        StringBuilder output = new StringBuilder();

        for(byte valueByte : value.getBytes(StandardCharsets.UTF_8))
        {
            //Transforming byte into binary string
            output.append(String.format("%8s",Integer.toBinaryString(valueByte & 0xFF)).replace(' ', '0'));
        }

        output.insert(0,"11111111");

        return output.toString();
    }

    public static OpaqueFormatter getInstance()
    {
        if(instance == null)
        {
            instance = new OpaqueFormatter();
        }

        return instance;
    }

    @Override
    public String parseValue(String rawBinary) {
        String rawBinaryWithoutHeader = rawBinary.substring(8);
        return BinaryUtils.binaryStringToString(rawBinaryWithoutHeader);
    }
}
