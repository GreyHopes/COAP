package utils.responses.clientErrors;

public class NotAcceptable extends ClientErrorResponse
{
    public NotAcceptable()
    {
        super();
        codeSubfield = 6;
        payload = "Not Acceptable";
    }
}
