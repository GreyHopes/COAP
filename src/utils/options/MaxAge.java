package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;

public class MaxAge extends CoAPOption
{
    public MaxAge()
    {
        this.number = 14;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 4;
        this.value = "60";
    }

    public MaxAge(int age)
    {
        this();
        value = String.valueOf(age);
    }
}