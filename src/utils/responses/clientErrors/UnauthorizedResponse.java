package utils.responses.clientErrors;

/**
 * Represents an Unauthorized response.
 */
public class UnauthorizedResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Unauthorized response.
     */
    public UnauthorizedResponse()
    {
        super();
        codeSubfield = 1;
        payload = "Unauthorized";
    }
}

