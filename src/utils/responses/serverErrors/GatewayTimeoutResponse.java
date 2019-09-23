package utils.responses.serverErrors;

public class GatewayTimeoutResponse extends ServerErrorResponse
{
    public GatewayTimeoutResponse()
    {
        super();
        codeSubfield = 4;
        payload = "Gateway timeout";
    }
}
