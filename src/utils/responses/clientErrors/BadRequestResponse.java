package utils.responses.clientErrors;

/**
 * Represents a Bad request response.
 */
public class BadRequestResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Bad request response.
     */
    public BadRequestResponse()
    {
        super();
        codeSubfield = 0;
        payload = "Bad Request";
    }
}
