package utils.options;

import utils.exceptions.OptionFormatingException;
import utils.formatter.FormatterInterface;



public abstract class CoAPOption
{
    //Formatting codes
    final int DELTA_MINUS_13 = 13;
    final int LENGTH_MINUS_13 = 13;

    protected int number = 0;
    protected int length = 0;

    protected int minSize = 0;
    protected int maxSize = 0;

    protected String value = "";

    protected FormatterInterface formatter = null;

    String format(CoAPOption previous) throws OptionFormatingException
    {
        if(value.length() < minSize || value.length() > maxSize)
        {
            throw new OptionFormatingException();
        }

        int previousOptionNumber = previous == null ? number : previous.getOptionNumber();
        int delta = number - previousOptionNumber;

        String output = "";
        StringBuilder toAdd = new StringBuilder();

        //Calulating Delta

        //If delta cannot be on 4 bits
        if(delta > 15)
        {
            delta = delta - 13;

            toAdd = new StringBuilder(Integer.toBinaryString(DELTA_MINUS_13));
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

        int length = valueFormatted.length();

        //If length cannot be on 4 bits
        if(length > 15)
        {
            length = length - 13;

            toAdd = new StringBuilder(Integer.toBinaryString(LENGTH_MINUS_13));
        }
        else
        {
            String lengthBinString = String.format("%4s",Integer.toBinaryString(length)).replace(' ', '0');
            toAdd = new StringBuilder(Integer.toBinaryString(length));
            length = -1;
        }

        output += toAdd.toString();

        //Extra delta bits

        if(delta > 0)
        {
            String extraDeltaBinString = String.format("%8s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(extraDeltaBinString);

            output += toAdd.toString();
        }

        //Extra length bits

        if(length > 0)
        {
            String extraLengthBinString = String.format("%8s",Integer.toBinaryString(length)).replace(' ', '0');
            toAdd = new StringBuilder(Integer.toBinaryString(length));

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
}
