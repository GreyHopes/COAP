package utils.options;

import utils.formatter.UIntFormatter;

/**
 * The Content Format  option.
 * According to RFC 7252 :
 * <i>
 *  The Content-Format Option indicates the representation format of the message payload.
 * </i>
 */
public class ContentFormat extends CoAPOption
{
    /**
     * Instantiates a new Content format.
     */
    public ContentFormat()
    {
        this.number = 12;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    /**
     * Instantiates a new Content format with a string value.
     *
     * @param s the value
     */
    public ContentFormat(String s)
    {
        this();
        value = s;
    }
}