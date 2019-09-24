package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.FormatterInterface;



public abstract class CoAPOption
{
    //Formatting codes
    public final static int DELTA_MINUS_13 = 13;
    public final static int DELTA_MINUS_269 = 14;
    public final static int LENGTH_MINUS_13 = 13;
    public final static int LENGTH_MINUS_269 = 14;

    protected int number = 0;
    protected int length = 0;

    protected int minSize = 0;
    protected int maxSize = 0;

    protected String value = "";

    protected FormatterInterface formatter = null;

    String format(CoAPOption previous) throws OptionFormatingException
    {
        if(formatter.formatValue(value).length()/8 < minSize || formatter.formatValue(value).length()/8> maxSize)
        {
            throw new OptionFormatingException();
        }

        int previousOptionNumber = previous == null ? 0 : previous.getOptionNumber();
        int delta = number - previousOptionNumber;

        String output = "";
        StringBuilder toAdd = new StringBuilder();

        //Calulating Delta

        //If delta cannot be on 4 bits
        int newDelta = 0;
        if(delta > 268)
        {
            delta = delta - 269;
            newDelta = DELTA_MINUS_269;
            toAdd = new StringBuilder(Integer.toBinaryString(newDelta));
        }
        else if(delta > 12)
        {
            delta = delta - 13;
            newDelta = DELTA_MINUS_13;
            toAdd = new StringBuilder(Integer.toBinaryString(newDelta));
        }
        else
        {
            String deltaBinString = String.format("%4s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(deltaBinString);
            delta = -1;
        }

        output += toAdd.toString();

        //Length

        //Formatted value length is always 8n bits
        String valueFormatted = formatter.formatValue(value);

        int length = valueFormatted.length()/8;

        //If length cannot be on 4 bits
        int newLength = 0;
        if(length > 268)
        {
            length = length - 269;
            newLength = LENGTH_MINUS_269;
            toAdd = new StringBuilder(Integer.toBinaryString(newLength));
        }
        else if(length > 12)
        {
            length = length - 13;
            newLength = LENGTH_MINUS_13;
            toAdd = new StringBuilder(Integer.toBinaryString(newLength));
        }
        else
        {
            String lengthBinString = String.format("%4s",Integer.toBinaryString(length)).replace(' ', '0');

            toAdd = new StringBuilder(lengthBinString);
            length = -1;
        }



        output += toAdd.toString();

        //Extra delta bits
        if(newDelta == DELTA_MINUS_269)
        {
            String extraDeltaBinString = String.format("%16s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(extraDeltaBinString);
            output += toAdd.toString();
        }

        if(newDelta == DELTA_MINUS_13)
        {
            String extraDeltaBinString = String.format("%8s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(extraDeltaBinString);

            output += toAdd.toString();
        }

        //Extra length bits

        if(newLength == LENGTH_MINUS_269)
        {
            String extraLengthBinString = String.format("%16s",Integer.toBinaryString(length)).replace(' ', '0');
            toAdd = new StringBuilder(extraLengthBinString);

            output += toAdd.toString();
        }

        if(newLength == DELTA_MINUS_13)
        {
            String extraLengthBinString = String.format("%8s",Integer.toBinaryString(length)).replace(' ', '0');
            toAdd = new StringBuilder(extraLengthBinString);

            output += toAdd.toString();
        }

        //Value

        toAdd = new StringBuilder(valueFormatted);

        output += toAdd.toString();

        return output;
    };

    public int getOptionNumber()
    {
        return number;
    }

    public void setValue(String newValue)
    {
        value = newValue;
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getValue() {
        return value;
    }

    public FormatterInterface getFormatter() {
        return formatter;
    }

    public void setFormatter(FormatterInterface formatter) {
        this.formatter = formatter;
    }
}
