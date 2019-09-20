package utils.formatter;

public interface FormatterInterface
{
    String formatValue(String value);

    static FormatterInterface getInstance()
    {
        return null;
    };
}
