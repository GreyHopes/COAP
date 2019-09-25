package utils.exceptions;

import utils.CoAPMessage;

/**
 * Exception to handle unrecognized options
 */
public class UnrecognizedOptionException extends Throwable {

    /**
     * There is some specific processing in this case that needs the message to choose how the endpoint will handle the message.
     * So we forward it with the exception
     */
    private CoAPMessage messageToForward = null;

    /**
     * Instantiates a new Unrecognized option exception with a message to forward for later use.
     *
     * @param message the message
     */
    public UnrecognizedOptionException(CoAPMessage message)
    {
        super();
        messageToForward = message;
    }

    /**
     * Gets message forwaded.
     *
     * @return the message forwaded
     */
    public CoAPMessage getMessageForwaded()
    {
        return messageToForward;
    }
}
