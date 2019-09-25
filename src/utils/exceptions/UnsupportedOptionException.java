package utils.exceptions;

/**
 * Exception to handle an unsupported option
 */
public class UnsupportedOptionException extends Throwable
{
    /**
     * In some cases the option can be silently ignored
     */
    private boolean silentIgnore = false;

    /**
     * Instantiates a new Unsupported option exception.
     */
    public UnsupportedOptionException()
    {
        super();
    }

    /**
     * Instantiates a new Unsupported option exception.
     *
     * @param ignore Boolean that says if we need to ignore the exception
     */
    public UnsupportedOptionException(boolean ignore)
    {
        silentIgnore = ignore;
    }

    /**
     * Gets silent ignore.
     *
     * @return the silent ignore
     */
    public boolean getSilentIgnore()
    {
        return silentIgnore;
    }
}
