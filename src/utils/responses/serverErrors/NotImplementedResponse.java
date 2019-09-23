package utils.responses.serverErrors;

public class NotImplementedResponse extends ServerErrorResponse
{
    public NotImplementedResponse()
    {
        super();
        codeSubfield = 1;
        payload = "Not implemented";
    }
}