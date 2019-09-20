package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;

public class ProxyUri extends CoAPOption
{
    public ProxyUri() {
        this.number = 35;
        this.formatter = StringFormatter.getInstance();
        this.minSize = 1;
        this.maxSize = 1034;
    }

    @Override
    public void setValue(String value) {

    }
}