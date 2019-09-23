package utils.responses.clientErrors;

public class BadRequestResponse extends ClientErrorResponse
{
    public BadRequestResponse()
    {
        super();
        codeSubfield = 0;
        payload = "Bad Request";
    }
}
