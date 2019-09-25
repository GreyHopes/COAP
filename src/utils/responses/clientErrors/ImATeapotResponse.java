package utils.responses.clientErrors;

/**
 * Represents a Im a teapot response.
 */
public class ImATeapotResponse extends ClientErrorResponse
{
    /**
     * Instantiates a new Im a teapot response.
     */
    public ImATeapotResponse()
    {
        super();
        codeSubfield = 18;
        payload = "I'm a teapot";
    }
}
