package utils.responses.clientErrors;

/**
 * Represents a Request entity too large response.
 */
public class RequestEntityTooLargeResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Request entity too large response.
     */
    public RequestEntityTooLargeResponse()
    {
        super();
        codeSubfield = 13;
        payload = "Request Entity Too Large";
    }
}