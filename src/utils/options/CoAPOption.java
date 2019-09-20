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

    protected String value ="";

    protected FormatterInterface formatter = null;

    String format(CoAPOption previous) throws OptionFormatingException
    {
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
            toAdd = new StringBuilder(Integer.toBinaryString(delta));
            delta = -1;
        }

        //Adding extra 0s
        if(toAdd.length() < 4)
        {
            for(int i = toAdd.length();i<4;i++)
            {
                toAdd.insert(0, "0");
            }
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
            toAdd = new StringBuilder(Integer.toBinaryString(length));
            length = -1;
        }

        //Adding extra 0s
        if(toAdd.length() < 4)
        {
            for(int i = toAdd.length();i<4;i++)
            {
                toAdd.insert(0, "0");
            }
        }

        output += toAdd.toString();

        //Extra delta bits

        if(delta > 0)
        {
            toAdd = new StringBuilder(Integer.toBinaryString(delta));

            //Adding extra 0s
            if(toAdd.length() < 8)
            {
                for(int i = toAdd.length();i<8;i++)
                {
                    toAdd.insert(0, "0");
                }
            }

            output += toAdd.toString();
        }

        //Extra length bits

        if(length > 0)
        {
            toAdd = new StringBuilder(Integer.toBinaryString(length));

            //Adding extra 0s
            if(toAdd.length() < 8)
            {
                for(int i = toAdd.length();i<8;i++)
                {
                    toAdd.insert(0, "0");
                }
            }

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

    abstract void setValue(String value);
}
