package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class UriPath extends CoAPOption
{
    public UriPath()
    {
        this.number = 11;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    public UriPath(String s)
    {
        this();
        value = s;
    }
}