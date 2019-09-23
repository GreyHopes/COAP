package utils.responses.clientErrors;

public class UnsupportedContentFormatResponse extends ClientErrorResponse
{
    public UnsupportedContentFormatResponse()
    {
        super();
        codeSubfield = 15;
        payload = "Unsupported Content Format Response";
    }
}