package utils.options;

import utils.formatter.StringFormatter;

/**
 * The UriHost  option.
 * According to RFC 7252 :
 * <i>
 * the Uri-Host Option specifies the Internet host of the resource
 * being requested,
 * </i>
 */
public class UriHost extends CoAPOption
{
    /**
     * Instantiates a new Uri host.
     */
    public UriHost()
    {
        this.number = 3;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Uri host with a value.
     *
     * @param s the value
     */
    public UriHost(String s)
    {
        this();
        value = s;
    }

}
