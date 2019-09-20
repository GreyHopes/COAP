package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;

public class ContentFormat extends CoAPOption
{
    public ContentFormat()
    {
        this.number = 12;
        this.formatter = UIntFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 2;
    }

    @Override
    public void setValue(String value) {

    }
}