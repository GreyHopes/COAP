package utils.responses.clientErrors;

/**
 * Represents a Not found response.
 */
public class NotFoundResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Not found response.
     */
    public NotFoundResponse()
    {
        super();
        codeSubfield = 4;
        payload = "Not Found";
    }
}