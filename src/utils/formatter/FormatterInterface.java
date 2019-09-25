package utils.formatter;

/**
 * Interface for all value formatters.
 */
public interface FormatterInterface
{
    /**
     * Format value string.
     *
     * @param value the value
     * @return the string
     */
    String formatValue(String value);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static FormatterInterface getInstance()
    {
        return null;
    };

    /**
     * Parse value string.
     *
     * @param rawBinary the raw binary
     * @return the string
     */
    String parseValue(String rawBinary);
}
