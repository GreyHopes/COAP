package utils.options;

import utils.exceptions.OptionFormattingException;
import utils.formatter.FormatterInterface;

/**
 * Abstract class for a CoAP option
 */
public abstract class CoAPOption
{
    /**
     * The constant DELTA_MINUS_13.
     */
    public final static int DELTA_MINUS_13 = 13;
    /**
     * The constant DELTA_MINUS_269.
     */
    public final static int DELTA_MINUS_269 = 14;
    /**
     * The constant LENGTH_MINUS_13.
     */
    public final static int LENGTH_MINUS_13 = 13;
    /**
     * The constant LENGTH_MINUS_269.
     */
    public final static int LENGTH_MINUS_269 = 14;

    /**
     * Option Number.
     */
    protected int number = 0;
    /**
     * Option length.
     */
    protected int length = 0;

    /**
     * Min length according to RFC 7252.
     */
    protected int minSize = 0;
    /**
     * Max length according to RFC 7252.
     */
    protected int maxSize = 0;

    /**
     * The Value.
     */
    protected String value = "";

    /**
     * The Formatter used to format the value.
     */
    protected FormatterInterface formatter = null;

    /**
     * Format the option to a binary string.
     * According to RFC 7252 an option is formatted as follows
     * <table summary="Option format">
     *   <tr>
     *     <th>0</th>
     *     <th>1</th>
     *     <th>2</th>
     *     <th>3</th>
     *     <th>4</th>
     *     <th>5</th>
     *     <th>6</th>
     *     <th>7</th>
     *   </tr>
     *   <tr>
     *     <td colspan="4">Option Delta</td>
     *     <td colspan="4">Option Length</td>
     *   </tr>
     *   <tr>
     *     <td colspan="8">Option Delta extended if needed (0-2 bytes)</td>
     *   </tr>
     *   <tr>
     *     <td colspan="8">Option Length extended if needed (0-2 bytes)</td>
     *   </tr>
     *   <tr>
     *     <td colspan="8">Option Value (0 or more bytes)</td>
     *   </tr>
     * </table>
     * @param previous the previous option for delta calculation
     * @return a binary string that represents the option acording to the guidelines in RFC 7252.
     * @throws OptionFormattingException the option formating exception
     */
    String format(CoAPOption previous) throws OptionFormattingException
    {
        if(formatter.formatValue(value).length()/8 < minSize || formatter.formatValue(value).length()/8> maxSize)
        {
            throw new OptionFormattingException();
        }
        //Calulating Delta
        //The delta is calculated depending on the previous option number in the list
        //We assume that options are sorted according to their option number
        int previousOptionNumber = previous == null ? 0 : previous.getOptionNumber();
        int delta = number - previousOptionNumber;

        String output = "";
        StringBuilder toAdd = new StringBuilder();

        //If delta cannot be on 4 bits
        //The new delta is here to store the delta that we added here to see if extra bits needs to be added
        int newDelta = 0;
        if(delta > 268)
        {
            delta = delta - 269;

            //This special code tells the recipient that their will be 16 bits following the newt portion.
            //These 16 bits are an unsigned int representing delta - 269
            newDelta = DELTA_MINUS_269;
            toAdd = new StringBuilder(Integer.toBinaryString(newDelta));
        }
        else if(delta > 12)
        {
            delta = delta - 13;

            //This special code tells the recipient that their will be 8 bits following the next portion.
            //These 8 bits are an unsigned int representing delta - 13
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

        //Formatted value length is assumed to be on 8n bits
        String valueFormatted = formatter.formatValue(value);

        int length = valueFormatted.length()/8;

        //Similar behaviour as the delta
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
            //According to the code we fill the extra 16 bits of the delta - 269
            String extraDeltaBinString = String.format("%16s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(extraDeltaBinString);
            output += toAdd.toString();
        }

        if(newDelta == DELTA_MINUS_13)
        {
            //According to the code we fill the extra 16 bits of the delta - 13
            String extraDeltaBinString = String.format("%8s",Integer.toBinaryString(delta)).replace(' ', '0');
            toAdd = new StringBuilder(extraDeltaBinString);

            output += toAdd.toString();
        }

        //Extra length bits
        //Same behaviour has delta
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

        //Adding the Value formatted as a String by default

        toAdd = new StringBuilder(valueFormatted);

        output += toAdd.toString();

        return output;
    };

    /**
     * Gets option number.
     *
     * @return the option number
     */
    public int getOptionNumber()
    {
        return number;
    }

    /**
     * Sets value.
     *
     * @param newValue the new value
     */
    public void setValue(String newValue)
    {
        value = newValue;
    };

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets length.
     *
     * @param length the length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Gets min size.
     *
     * @return the min size
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Sets min size.
     *
     * @param minSize the min size
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    /**
     * Gets max size.
     *
     * @return the max size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets max size.
     *
     * @param maxSize the max size
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets formatter.
     *
     * @return the formatter
     */
    public FormatterInterface getFormatter() {
        return formatter;
    }

    /**
     * Sets formatter.
     *
     * @param formatter the formatter
     */
    public void setFormatter(FormatterInterface formatter) {
        this.formatter = formatter;
    }
}
