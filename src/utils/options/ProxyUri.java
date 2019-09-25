package utils.options;

import utils.formatter.StringFormatter;

/**
 * The ProxyUri  option.
 * According to RFC 7252 :
 * <i>
 * The Proxy-Uri Option is used to make a request to a forward-proxy.
 * </i>
 */
public class ProxyUri extends CoAPOption
{
    /**
     * Instantiates a new Proxy uri.
     */
    public ProxyUri() {
        this.number = 35;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 1034;
    }

    /**
     * Instantiates a new Proxy uri with a value.
     *
     * @param s the value
     */
    public ProxyUri(String s) {
        this();
        value = s;
    }
}