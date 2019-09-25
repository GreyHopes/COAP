package utils.options;

import utils.formatter.StringFormatter;

/**
 * The ProxyScheme  option.
 * According to RFC 7252 it is used to the specify the scheme used on a proxy request
 */
public class ProxyScheme extends CoAPOption
{
    /**
     * Instantiates a new Proxy scheme.
     */
    public ProxyScheme() {
        this.number = 39;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 255;
    }

    /**
     * Instantiates a new Proxy scheme with a value.
     *
     * @param s the value
     */
    public ProxyScheme(String s) {
        this();
        value = s;
    }

}