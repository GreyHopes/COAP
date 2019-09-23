package utils.responses.clientErrors;

public class NotFoundResponse extends ClientErrorResponse
{
    public NotFoundResponse()
    {
        super();
        codeSubfield = 4;
        payload = "Not Found";
    }
}