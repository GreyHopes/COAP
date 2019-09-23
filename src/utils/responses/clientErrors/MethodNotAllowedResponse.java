package utils.responses.clientErrors;

public class MethodNotAllowedResponse  extends ClientErrorResponse
{
    public MethodNotAllowedResponse()
    {
        super();
        codeSubfield = 5;
        payload = "Method Not Allowed";
    }
}