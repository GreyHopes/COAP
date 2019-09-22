package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class LocationPath extends CoAPOption
{
    public LocationPath()
    {
        this.number = 8;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    public LocationPath(String s)
    {
        this();
        value = s;
    }
}