package utils.responses.serverErrors;

/**
 * Represents a Not implemented response.
 */
public class NotImplementedResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Not implemented response.
     */
    public NotImplementedResponse()
    {
        super();
        codeSubfield = 1;
        payload = "Not implemented";
    }
}