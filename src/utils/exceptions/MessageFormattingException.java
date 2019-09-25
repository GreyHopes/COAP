package utils.exceptions;

/**
 * Exception to handle message formatting errors
 */
public class MessageFormattingException extends Throwable
{
    /**
     * Instantiates a new Message formatting exception with a string message.
     *
     * @param s The message
     */
    public MessageFormattingException(String s)
    {
        super(s);
    }

    /**
     * Instantiates a new Message formatting exception.
     */
    public MessageFormattingException()
    {
        super();
    }
}
