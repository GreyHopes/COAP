package utils.responses.serverErrors;

import utils.responses.CoAPResponse;

public abstract class ServerErrorResponse extends CoAPResponse
{
    public ServerErrorResponse()
    {
        codeClass = 5;
    }
}
