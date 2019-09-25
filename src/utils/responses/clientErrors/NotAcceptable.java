package utils.responses.clientErrors;

/**
 * Represents a Not acceptable response.
 */
public class NotAcceptable extends ClientErrorResponse
{
    /**
     * Instantiates a new Not acceptable.
     */
    public NotAcceptable()
    {
        super();
        codeSubfield = 6;
        payload = "Not Acceptable";
    }
}
