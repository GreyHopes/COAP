package utils.responses.clientErrors;

import utils.responses.CoAPResponse;

public abstract class ClientErrorResponse extends CoAPResponse
{
    public ClientErrorResponse()
    {
        codeClass = 4;
    }
}