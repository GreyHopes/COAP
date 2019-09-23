package utils.responses.clientErrors;

public class UnauthorizedResponse extends ClientErrorResponse
{
    public UnauthorizedResponse()
    {
        super();
        codeSubfield = 1;
        payload = "Unauthorized";
    }
}

