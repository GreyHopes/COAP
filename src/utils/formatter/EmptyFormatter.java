package utils.formatter;

/**
 * Formatter for the empty format type.
 */
public class EmptyFormatter implements FormatterInterface
{
    private static EmptyFormatter instance = null;

    @Override
    public String formatValue(String value) {
        return "";
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
