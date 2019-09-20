package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.OpaqueFormatter;

public class IfMatch extends CoAPOption
{
    public IfMatch()
    {
        this.number = 1;
        this.formatter = OpaqueFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 8;
    }

    @Override
    public void setValue(String value) {

    }
}
