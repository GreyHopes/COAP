package utils.responses;

import utils.CoAPMessage;

public abstract class CoAPResponse extends CoAPMessage
{
    public CoAPResponse()
    {
        type = ACKNOWLEDGMENT;
    }
}
