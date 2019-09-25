package utils.options;

import utils.formatter.OpaqueFormatter;

/**
 * The IfMatch  option.
 * According to RFC 7252 :
 * <i>
 *  The If-Match Option MAY be used to make a request conditional on the
 *  current existence or value of an ETag for one or more representations
 *  of the target resource.
 * </i>
 */
public class IfMatch extends CoAPOption
{
    /**
     * Instantiates a new If match.
     */
    public IfMatch()
    {
        this.number = 1;
        this.formatter = OpaqueFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 8;
    }

    /**
     * Instantiates a new If match with a value.
     *
     * @param s the value
     */
    public IfMatch(String s)
    {
        this();
        value = s;
    }

}
