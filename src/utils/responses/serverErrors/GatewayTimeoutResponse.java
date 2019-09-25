package utils.responses.serverErrors;

/**
 * Represents a Gateway timeout response.
 */
public class GatewayTimeoutResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Gateway timeout response.
     */
    public GatewayTimeoutResponse()
    {
        super();
        codeSubfield = 4;
        payload = "Gateway timeout";
    }
}
