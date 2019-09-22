package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class UriQuery extends CoAPOption
{
    public UriQuery()
    {
        this.number = 15;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    public UriQuery(String s)
    {
        this();
        value = s;
    }
}
