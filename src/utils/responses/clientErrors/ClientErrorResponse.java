package utils.responses.clientErrors;

import utils.responses.CoAPResponse;

/**
 * Represents a Client error response.
 */
public abstract class ClientErrorResponse extends CoAPResponse
{
    /**
     * Instantiates a new Client error response.
     */
    public ClientErrorResponse()
    {
        super();
        codeClass = 4;
    }
}
