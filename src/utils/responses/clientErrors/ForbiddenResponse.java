package utils.responses.clientErrors;

public class ForbiddenResponse extends ClientErrorResponse
{
    public ForbiddenResponse()
    {
        super();
        codeSubfield = 3;
        payload = "Forbidden";
    }
}
