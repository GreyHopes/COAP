package utils.responses.serverErrors;

import utils.responses.CoAPResponse;

/**
 * Represents a Server error response.
 */
public abstract class ServerErrorResponse extends CoAPResponse
{
    /**
     * Instantiates a new Server error response.
     */
    public ServerErrorResponse()
    {
        super();
        codeClass = 5;
    }
}
