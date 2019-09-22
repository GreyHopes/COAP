package utils.exceptions;

public class MessageParsingException extends Throwable{
    public MessageParsingException(String s)
    {
        super(s);
    }

    public MessageParsingException()
    {
        super();
    };
}
