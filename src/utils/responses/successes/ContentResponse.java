package utils.responses.successes;

/**
 * Represents a Content response.
 */
public class ContentResponse extends SuccessResponse {
    /**
     * Instantiates a new Content response.
     */
    public ContentResponse()
    {
        super();
        codeSubfield = 5;
    }

    /**
     * Instantiates a new Content response with content.
     *
     * @param content the content
     */
    public ContentResponse(String content)
    {
        this();
        payload = content;
    }
}
