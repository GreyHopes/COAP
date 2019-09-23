package utils.responses.serverErrors;

public class ServiceUnavailableResponse extends ServerErrorResponse
{
    public ServiceUnavailableResponse()
    {
        super();
        codeSubfield = 3;
        payload = "Service Unavailable";
    }
}