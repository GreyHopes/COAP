package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class UriHost extends CoAPOption
{
    public UriHost()
    {
        this.number = 3;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 255;
    }

    public UriHost(String s)
    {
        this();
        value = s;
    }

}
