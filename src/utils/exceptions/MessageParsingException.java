package utils.exceptions;

/**
 *  Exception to handle errors in message parsing.
 */
public class MessageParsingException extends Throwable{
    /**
     * Instantiates a new Message parsing exception with a string message.
     *
     * @param s the message
     */
    public MessageParsingException(String s)
    {
        super(s);
    }

    /**
     * Instantiates a new Message parsing exception.
     */
    public MessageParsingException()
    {
        super();
    };
}
