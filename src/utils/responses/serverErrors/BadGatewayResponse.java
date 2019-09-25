package utils.responses.serverErrors;

/**
 * Represents a Bad gateway response.
 */
public class BadGatewayResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Bad gateway response.
     */
    public BadGatewayResponse()
    {
        super();
        codeSubfield = 2;
        payload = "Bad Gateway";
    }
}
