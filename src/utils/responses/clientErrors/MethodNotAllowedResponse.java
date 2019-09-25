package utils.responses.clientErrors;

/**
 * Represents a Method not allowed response.
 */
public class MethodNotAllowedResponse  extends ClientErrorResponse
{
    /**
     * Instantiates a new Method not allowed response.
     */
    public MethodNotAllowedResponse()
    {
        super();
        codeSubfield = 5;
        payload = "Method Not Allowed";
    }
}