package utils.responses.successes;

/**
 * Represents a Changed response.
 */
public class ChangedResponse extends SuccessResponse
{
    /**
     * Instantiates a new Changed response.
     */
    public ChangedResponse()
    {
        super();
        codeSubfield = 4;
    }

    /**
     * Instantiates a new Changed response with content.
     *
     * @param content the content
     */
    public ChangedResponse(String content)
    {
        this();
        payload = content;
    }
}
