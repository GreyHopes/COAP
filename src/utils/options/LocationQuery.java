package utils.options;

import utils.formatter.StringFormatter;

/**
 * The LocationQuery  option.
 * According to RFC 7252 :
 * <i>
 *  The Location-Path and Location-Query Options together indicate a
 *  relative URI that consists either of an absolute path, a query
 *  string, or both
 * </i>
 */
public class LocationQuery extends CoAPOption
{
    /**
     * Instantiates a new Location query.
     */
    public LocationQuery()
    {
        this.number = 20;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Location query with a value.
     *
     * @param s the value
     */
    public LocationQuery(String s)
    {
        this();
        value = s;
    }

}
