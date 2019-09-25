package utils.responses.clientErrors;

/**
 * Represents a Forbidden response.
 */
public class ForbiddenResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Forbidden response.
     */
    public ForbiddenResponse()
    {
        super();
        codeSubfield = 3;
        payload = "Forbidden";
    }
}
