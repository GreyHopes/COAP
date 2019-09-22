package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;

public class Accept extends CoAPOption
{
    public Accept()
    {
        this.number = 17;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    public Accept(int accept)
    {
        this();
        value = String.valueOf(accept);
    }
}