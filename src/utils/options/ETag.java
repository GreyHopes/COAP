package utils.options;

import utils.formatter.OpaqueFormatter;


/**
 * The ETag  option.
 * According to RFC 7252 :
 * <i>
 *  An entity-tag is intended for use as a resource-local identifier for
 *  differentiating between representations of the same resource that
 *  vary over time.
 * </i>
 */
public class ETag extends CoAPOption
{
    /**
     * Instantiates a new E tag.
     */
    public ETag()
    {
        this.number = 4;
        this.formatter = OpaqueFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 8;
    }

    /**
     * Instantiates a new E tag with a value.
     *
     * @param s the value
     */
    public ETag(String s)
    {
        this();
        value = s;
    }
}
