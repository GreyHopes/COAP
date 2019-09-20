package utils.formatter;

public class UIntFormatter implements FormatterInterface{

    private static UIntFormatter instance = null;

    @Override
    public String formatValue(String value) {
        return null;
    }

    public static UIntFormatter getInstance()
    {
        if(instance == null)
        {
            instance = new UIntFormatter();
        }

        return instance;
    }
}
