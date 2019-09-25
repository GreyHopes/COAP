package utils.options;

import utils.formatter.UIntFormatter;

/**
 * The MaxAge  option.
 * According to RFC 7252 :
 * <i>
 *  The Max-Age Option indicates the maximum time a response may be
 *  cached before it is considered not fresh.
 * </i>
 */
public class MaxAge extends CoAPOption
{
    /**
     * Instantiates a new Max age.
     */
    public MaxAge()
    {
        this.number = 14;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 4;
        this.value = "60";
    }

    /**
     * Instantiates a new Max age with a value.
     *
     * @param age the age
     */
    public MaxAge(int age)
    {
        this();
        value = String.valueOf(age);
    }
}