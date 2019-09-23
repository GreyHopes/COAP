package utils.responses.clientErrors;

public class RequestEntityTooLargeResponse extends ClientErrorResponse
{
    public RequestEntityTooLargeResponse()
    {
        super();
        codeSubfield = 13;
        payload = "Request Entity Too Large";
    }
}