package utils.formatter;

/**
 * Formatter for the UInt format.
 */
public class UIntFormatter implements FormatterInterface
{

    private static UIntFormatter instance = null;

    @Override
    public String formatValue(String value)
    {
        int intValue = Integer.parseInt(value);

        StringBuilder outputBuilder = new StringBuilder(Integer.toBinaryString(intValue));

        //We had extras 0s to ensure that everything is of length 8n bits
        while(outputBuilder.length() % 8 != 0)
        {
            outputBuilder.insert(0,"0");
        }

        return outputBuilder.toString();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
