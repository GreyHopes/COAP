package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class ProxyScheme extends CoAPOption
{
    public ProxyScheme() {
        this.number = 39;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 255;
    }

    public ProxyScheme(String s) {
        this();
        value = s;
    }

}