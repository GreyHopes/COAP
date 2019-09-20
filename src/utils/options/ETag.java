package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.OpaqueFormatter;

public class ETag extends CoAPOption
{
    public ETag()
    {
        this.number = 4;
        this.formatter = OpaqueFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 8;
    }

    @Override
    public void setValue(String value) {

    }
}
