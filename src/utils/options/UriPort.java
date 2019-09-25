package utils.options;

import utils.formatter.UIntFormatter;

/**
 * The UriPort  option.
 * According to RFC 7252 :
 * <i>
 * the Uri-Port Option specifies the transport-layer port number of
 * the resource
 * </i>
 */
public class UriPort extends CoAPOption
{
    /**
     * Instantiates a new Uri port.
     */
    public UriPort()
    {
        this.number = 7;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    /**
     * Instantiates a new Uri port with a value.
     *
     * @param port the port
     */
    public UriPort(int port)
    {
        this();
        value = String.valueOf(port);
    }
}
