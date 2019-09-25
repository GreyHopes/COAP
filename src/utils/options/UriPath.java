package utils.options;

import utils.formatter.StringFormatter;


/**
 * The UriPath  option.
 * According to RFC 7252 :
 * <i>
 * each Uri-Path Option specifies one segment of the absolute path to
 * the resource
 * </i>
 */
public class UriPath extends CoAPOption
{
    /**
     * Instantiates a new Uri path.
     */
    public UriPath()
    {
        this.number = 11;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Uri path with a value.
     *
     * @param s the value
     */
    public UriPath(String s)
    {
        this();
        value = s;
    }
}