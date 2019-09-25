package utils.options;

import utils.formatter.StringFormatter;

/**
 * The LocationPath  option.
 * According to RFC 7252 :
 * <i>
 *  The Location-Path and Location-Query Options together indicate a
 *  relative URI that consists either of an absolute path, a query
 *  string, or both
 * </i>
 */
public class LocationPath extends CoAPOption
{
    /**
     * Instantiates a new Location path.
     */
    public LocationPath()
    {
        this.number = 8;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Location path with a value.
     *
     * @param s the value
     */
    public LocationPath(String s)
    {
        this();
        value = s;
    }
}