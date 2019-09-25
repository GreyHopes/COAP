package utils.responses.clientErrors;

/**
 * Represents a Bad option response.
 */
public class BadOptionResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Bad option response.
     */
    public BadOptionResponse()
    {
        super();
        codeSubfield = 2;
        payload = "Bad option";
    }
}
