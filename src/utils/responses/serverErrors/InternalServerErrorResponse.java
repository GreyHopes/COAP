package utils.responses.serverErrors;

/**
 * Represents a Internal server error response.
 */
public class InternalServerErrorResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Internal server error response.
     */
    public InternalServerErrorResponse()
    {
        super();
        codeSubfield = 0;
        payload = "Internal Server Error";
    }
}
