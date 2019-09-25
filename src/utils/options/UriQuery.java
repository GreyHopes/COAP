package utils.options;

import utils.formatter.StringFormatter;

/**
 * The UriQuery option.
 * According to RFC 7252 :
 * <i>
 * each Uri-Query Option specifies one argument parameterizing the
 * resource
 * </i>
 */
public class UriQuery extends CoAPOption
{
    /**
     * Instantiates a new Uri query.
     */
    public UriQuery()
    {
        this.number = 15;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Uri query with a value.
     *
     * @param s the value
     */
    public UriQuery(String s)
    {
        this();
        value = s;
    }
}
