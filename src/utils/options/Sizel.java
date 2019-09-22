package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;

public class Sizel extends CoAPOption
{
    public Sizel()
    {
        this.number = 60;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 4;
    }

    public Sizel(int sizel)
    {
        this();
        value = String.valueOf(sizel);
    }
}
