package utils.options;

import utils.formatter.EmptyFormatter;

/**
 * The IfNoneMatch  option.
 * According to RFC 7252 :
 * <i>
 *   The If-None-Match Option MAY be used to make a request conditional on
 *  the nonexistence of the target resource
 * </i>
 */
public class IfNoneMatch extends CoAPOption
{
    /**
     * Instantiates a new If none match.
     */
    public IfNoneMatch()
    {
        this.number = 5;
        this.formatter = EmptyFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 0;
    }
}