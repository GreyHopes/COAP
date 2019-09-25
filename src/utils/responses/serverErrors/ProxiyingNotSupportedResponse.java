package utils.responses.serverErrors;

/**
 * Represents aProxiying not supported response.
 */
public class ProxiyingNotSupportedResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Proxiying not supported response.
     */
    public ProxiyingNotSupportedResponse()
    {
        super();
        codeSubfield = 5;
        payload = "Proxying not supported";
    }
}
