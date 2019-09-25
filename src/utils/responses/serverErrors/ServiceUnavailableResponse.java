package utils.responses.serverErrors;

/**
 * Represents a Service unavailable response.
 */
public class ServiceUnavailableResponse extends ServerErrorResponse
{
    /**
     * Instantiates a new Service unavailable response.
     */
    public ServiceUnavailableResponse()
    {
        super();
        codeSubfield = 3;
        payload = "Service Unavailable";
    }
}