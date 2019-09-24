package utils.formatter;

public class EmptyFormatter implements FormatterInterface
{
    private static EmptyFormatter instance = null;

    @Override
    public String formatValue(String value) {
        return "";
    }

    public static EmptyFormatter getInstance()
    {
        if(instance == null)
        {
            instance = new EmptyFormatter();
        }

        return instance;
    }

    @Override
    public String parseValue(String rawBinary)
    {
        return "";
    }
}
