package utils.responses.successes;

/**
 * Represents a Created response.
 */
public class CreatedResponse extends SuccessResponse
{
    /**
     * Instantiates a new Created response.
     */
    public CreatedResponse()
    {
        super();
        codeSubfield = 1;
    }

    /**
     * Instantiates a new Created response with content.
     *
     * @param content the content
     */
    public CreatedResponse(String content)
    {
        this();
        payload = content;
    }

}
