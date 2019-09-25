package utils.responses.clientErrors;

/**
 * Represents an Unsupported content format response.
 */
public class UnsupportedContentFormatResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Unsupported content format response.
     */
    public UnsupportedContentFormatResponse()
    {
        super();
        codeSubfield = 15;
        payload = "Unsupported Content Format Response";
    }
}