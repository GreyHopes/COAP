package utils.responses.serverErrors;

public class BadGatewayResponse extends ServerErrorResponse
{
    public BadGatewayResponse()
    {
        super();
        codeSubfield = 2;
        payload = "Bad Gateway";
    }
}
