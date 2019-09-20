package utils.responses.successes;

import utils.responses.CoAPResponse;

public abstract class SuccessResponse extends CoAPResponse
{
    public SuccessResponse()
    {
        codeClass = 2;
    }
}
