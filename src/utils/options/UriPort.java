package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;

public class UriPort extends CoAPOption
{
    public UriPort()
    {
        this.number = 7;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    public UriPort(int port)
    {
        this();
        value = String.valueOf(port);
    }
}
