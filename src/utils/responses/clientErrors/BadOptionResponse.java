package utils.responses.clientErrors;

public class BadOptionResponse extends ClientErrorResponse
{
    public BadOptionResponse()
    {
        super();
        codeSubfield = 2;
        payload = "Bad option";
    }
}
