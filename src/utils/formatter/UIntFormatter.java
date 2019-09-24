package utils.formatter;

public class UIntFormatter implements FormatterInterface
{

    private static UIntFormatter instance = null;

    @Override
    public String formatValue(String value)
    {
        int intValue = Integer.parseInt(value);

        StringBuilder outputBuilder = new StringBuilder(Integer.toBinaryString(intValue));

        while(outputBuilder.length() % 8 != 0)
        {
            outputBuilder.insert(0,"0");
        }

        return outputBuilder.toString();
    }

    public static UIntFormatter getInstance()
    {
        if(instance == null)
        {
            instance = new UIntFormatter();
        }

        return instance;
    }

    @Override
    public String parseValue(String rawBinary) {
        return String.valueOf(Integer.parseInt(rawBinary,2));
    }
}
