package utils.responses.serverErrors;

public class ProxiyingNotSupportedResponse extends ServerErrorResponse
{
    public ProxiyingNotSupportedResponse()
    {
        super();
        codeSubfield = 5;
        payload = "Proxying not supported";
    }
}
