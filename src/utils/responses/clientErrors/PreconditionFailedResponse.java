package utils.responses.clientErrors;

public class PreconditionFailedResponse extends ClientErrorResponse
{
    public PreconditionFailedResponse()
    {
        super();
        codeSubfield = 12;
        payload = "Precondition failed";
    }
}
