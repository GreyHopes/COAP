package utils.options;

import utils.formatter.UIntFormatter;

/**
 * The Size1  option.
 * According to RFC 7252 :
 * <i>
 * The Size1 option provides size information about the resource
 *  representation in a request.
 * </i>
 */
public class Sizel extends CoAPOption
{
    /**
     * Instantiates a new Sizel.
     */
    public Sizel()
    {
        this.number = 60;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 4;
    }

    /**
     * Instantiates a new Sizel with a value.
     *
     * @param sizel the value.
     */
    public Sizel(int sizel)
    {
        this();
        value = String.valueOf(sizel);
    }
}
