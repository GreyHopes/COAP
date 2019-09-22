package utils.exceptions;

public class MessageFormattingException extends Throwable
{
    public MessageFormattingException(String s)
    {
        super(s);
    }

    public MessageFormattingException()
    {
        super();
    }
}
