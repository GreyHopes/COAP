package utils.responses.successes;

import utils.responses.CoAPResponse;

/**
 * Represents a Success response.
 */
public abstract class SuccessResponse extends CoAPResponse
{
    /**
     * Instantiates a new Success response.
     */
    public SuccessResponse()
    {
        super();
        codeClass = 2;
    }
}
