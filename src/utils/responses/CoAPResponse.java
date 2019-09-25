package utils.responses;

import utils.CoAPMessage;

/**
 * Represents a CoAP response
 */
public abstract class CoAPResponse extends CoAPMessage
{
    /**
     * Instantiates a new Co ap response.
     * By default the response is an ACK
     */
    public CoAPResponse()
    {
        super();
        type = ACKNOWLEDGMENT;
    }
}
