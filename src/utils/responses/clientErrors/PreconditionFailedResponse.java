package utils.responses.clientErrors;

/**
 * Represents a Precondition failed response.
 */
public class PreconditionFailedResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Precondition failed response.
     */
    public PreconditionFailedResponse()
    {
        super();
        codeSubfield = 12;
        payload = "Precondition failed";
    }
}
