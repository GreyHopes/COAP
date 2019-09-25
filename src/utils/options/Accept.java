package utils.options;

import utils.formatter.UIntFormatter;

/**
 * The Accept option.
 * According to RFC 7252 :
 * <i>
 *  The CoAP Accept option can be used to indicate which Content-Format is acceptable to the client.
 * </i>
 */
public class Accept extends CoAPOption
{
    /**
     * Instantiates a new Accept.
     */
    public Accept()
    {
        this.number = 17;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    /**
     * Instantiates a new Accept.
     *
     * @param accept the accepted content format
     */
    public Accept(int accept)
    {
        this();
        value = String.valueOf(accept);
    }
}