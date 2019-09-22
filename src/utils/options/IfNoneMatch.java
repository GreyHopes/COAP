package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.EmptyFormatter;

public class IfNoneMatch extends CoAPOption
{
    public IfNoneMatch()
    {
        this.number = 5;
        this.formatter = EmptyFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 0;
    }
}