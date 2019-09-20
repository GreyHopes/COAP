package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class LocationQuery extends CoAPOption
{
    public LocationQuery()
    {
        this.number = 20;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 0;
        this.maxSize = 255;
    }

    @Override
    public void setValue(String value) {

    }
}
