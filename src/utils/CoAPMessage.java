package utils;

import utils.exceptions.*;
import utils.formatter.StringFormatter;
import utils.options.CoAPOption;
import utils.options.OptionsArray;
import utils.options.OptionsRegistry;

/**
 * Represents a CoAP message
 */
public abstract class CoAPMessage
{
    //CoAP version number must be set to 01
    private String version = "01";


    private static final String PAYLOAD_MARKER = "11111111";

    //Types constants
    /**
     * The constant CONFIRMABLE.
     */
    public static final int CONFIRMABLE = 0;
    /**
     * The constant RESET.
     */
    public static final int RESET = 3;
    /**
     * The constant NON_CONFIRMABLE.
     */
    public static final int NON_CONFIRMABLE = 1;
    /**
     * The constant ACKNOWLEDGMENT.
     */
    public static final int ACKNOWLEDGMENT = 2;
    /**
     * The Type.
    * Message type
    * Confirmable              0
    * Non Confirmable       1
    * Acknowledgment      2
    * Reset                     3
    * Default is 0
     */
    protected int type = CONFIRMABLE;

    /**
     * The Token length.
     */
    protected int tokenLength = 0;

    /**
     * The Token.
     */
    protected byte[] token = {};
    /**
     * The Code class.
     */
    /**
    * Message code composed of class c (3 bits 0-7) and subfield (5 bits 0-31) similar to HTTP
    * Class :
    * Request         0
    * Success         2
    * Client Error    4
    * Server Error   5
     */
    protected int codeClass;
    /**
     * The Code subfield.
     */
    protected int codeSubfield;

    /**
     * The Message id.
     */
    protected int messageId = 0;

    /**
     * The Options.
     */
    protected OptionsArray options;

    /**
     * The Payload.
     */
    protected String payload = "";

    /**
     * Set options.
     *
     * @param newArray the new array
     */
    public void setOptions(OptionsArray newArray){options = newArray;}

    /**
     * Sets message id.
     *
     * @param newId the new id
     */
    public void setMessageId(int newId) {
        messageId = newId;
    }

    /**
     * Sets type.
     *
     * @param newType the new type
     */
    public void setType(int newType) {
        type = newType;
    }

    /**
     * Gets code class.
     *
     * @return the code class
     */
    public int getCodeClass() {
        return codeClass;
    }

    /**
     * Sets token and token length.
     *
     * @param token the token
     */
    public void setToken(byte[] token) {
        this.token = token;
        tokenLength = token.length;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Tells us if the message is confirmable.
     *
     * @return boolean
     */
    public boolean isConfirmable()
    {
        return type == CONFIRMABLE;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Gets token length.
     *
     * @return the token length
     */
    public int getTokenLength() {
        return tokenLength;
    }

    /**
     * Get token.
     *
     * @return the token
     */
    public byte[] getToken() {
        return token;
    }

    /**
     * Gets code subfield.
     *
     * @return the code subfield
     */
    public int getCodeSubfield() {
        return codeSubfield;
    }

    /**
     * Gets message id.
     *
     * @return the message id
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Gets options.
     *
     * @return the options
     */
    public OptionsArray getOptions() {
        return options;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Instantiates a new Co ap message.
     */
    public CoAPMessage()
    {
        options = new OptionsArray();
    }

    /**
     * Add option.
     *
     * @param option the option
     */
    public void addOption(CoAPOption option)
    {
        options.add(option);
    }

    /**
     * Parse a co ap message from a binary string.
     *
     * @param binaryMessage the binary message
     * @return the co ap message
     * @throws MessageParsingException If an error occurred while parsing the message
     * @throws UnrecognizedOptionException If an option isn't supported and the message must be rejected
     */
    public static CoAPMessage parse(String binaryMessage) throws MessageParsingException, UnrecognizedOptionException {
        try
        {
            //Extract version
            String rawBinary = binaryMessage.substring(0,2);

            //Version MUST be 01
            if(!rawBinary.equals("01"))
            {
                throw new MessageParsingException("Incorect version");
            }

            //Extract type
            int parsedType = Integer.parseInt(binaryMessage.substring(2,4),2);

            //Extract token length
            int parsedTokenLength = Integer.parseInt(binaryMessage.substring(4,8),2);

            //Extract code class
            int parsedCodeClass = Integer.parseInt(binaryMessage.substring(8,11),2);

            //Extract code subfield
            int parsedCodeSubfield = Integer.parseInt(binaryMessage.substring(11,16),2);

            //Extract message id
            int parsedMessageId = Integer.parseInt(binaryMessage.substring(16,32),2);

            //Setting index to the next par
            int index = 32;

            //Extracting token if any
            byte[] parsedToken = {};

            if(parsedTokenLength > 0)
            {
                index = index + 8 * parsedTokenLength;

                parsedToken = BinaryUtils.binaryStringToBytes(binaryMessage.substring(32,index));
            }

            CoAPMessage message = null;

            //Generating the message from the registry
            try {
                message = MessageRegistry.getInstance().generateMessageFromCode(parsedCodeClass,parsedCodeSubfield);
            } catch (UnsupportedMessageClassException e) {
                e.printStackTrace();
                throw new MessageParsingException("Unsupported message Class");
            }

            //Setting a few variables
            message.setMessageId(parsedMessageId);
            message.setType(parsedType);
            message.setToken(parsedToken);

            //We will now parse the options
            OptionsArray parsedOptions = new OptionsArray();

            //Extracting options if any and not detecting the payload marker
            while(binaryMessage.length() > index && !binaryMessage.substring(index,index+4).equals("1111"))
            {
                //Checking the delta
                int parsedDelta = Integer.parseInt(binaryMessage.substring(index,index+4),2);

                if(parsedDelta > 14)
                {
                    throw new MessageParsingException("Invalid delta");
                }
                index+=4;

                //Parsing option length

                int parsedOptionLength = Integer.parseInt(binaryMessage.substring(index,index+4),2);

                if(parsedOptionLength > 14)
                {
                    throw new MessageParsingException("Invalid length");
                }
                index+=4;

                //Parsing extra delta bits
                if(parsedDelta == CoAPOption.DELTA_MINUS_269)
                {
                    int parsedExtraDelta = Integer.parseInt(binaryMessage.substring(index,index+16),2);
                    parsedDelta = parsedExtraDelta + 269;
                    index+=16;
                }

                if(parsedDelta == CoAPOption.DELTA_MINUS_13)
                {
                    int parsedExtraDelta = Integer.parseInt(binaryMessage.substring(index,index+8),2);
                    parsedDelta = parsedExtraDelta + 13;
                    index+=8;
                }

                //Parsing option number with  complete delta
                int parsedOptionNumber = 0;

                if(parsedOptions.size() == 0)
                {
                    parsedOptionNumber = parsedDelta;
                }
                else if (parsedDelta < 13)
                {
                    parsedOptionNumber = parsedDelta + parsedOptions.getLastOption().getOptionNumber();
                }

                //Parsing extra length
                if(parsedOptionLength == CoAPOption.LENGTH_MINUS_269)
                {
                    int parsedExtraOptionLength = Integer.parseInt(binaryMessage.substring(index,index+16),2);
                    parsedOptionLength = parsedExtraOptionLength + 269;
                    index+=16;
                }

                if(parsedOptionLength == CoAPOption.LENGTH_MINUS_13)
                {
                    int parsedExtraOptionLength = Integer.parseInt(binaryMessage.substring(index,index+8),2);
                    parsedOptionLength = parsedExtraOptionLength + 13;
                    index+=8;
                }

                String parsedValue = binaryMessage.substring(index,index + 8 * parsedOptionLength);
                index += 8 * parsedOptionLength;

                CoAPOption newOption = null;

                //We will generate the option from registry
                try
                {
                    newOption = OptionsRegistry.getInstance().generateOptionFromNumber(parsedOptionNumber);
                } catch (UnsupportedOptionException e) {
                    e.printStackTrace();

                    //Silently ignoring unrecognized elective option
                    if(!e.getSilentIgnore())
                    {
                        throw new UnrecognizedOptionException(message);
                    }

                    continue;
                } catch (MessageParsingException e) {
                    throw e;
                }

                newOption.setValue(newOption.getFormatter().parseValue(parsedValue));

                parsedOptions.add(newOption);
            }

            message.setOptions(parsedOptions);

            String parsedRawPayload = "";
            //Parsing payload if any
            if(binaryMessage.length() > index+8 && binaryMessage.substring(index,index+8).equals(PAYLOAD_MARKER))
            {
                index+=8;
                parsedRawPayload = binaryMessage.substring(index);
            }

            String parsedPayload = BinaryUtils.binaryStringToString(parsedRawPayload);

            message.setPayload(parsedPayload);

            return message;
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            throw new MessageParsingException("Error while parsing message");
        }
    }


    /**
     * Format the message to a binary string according to the format of RFC 7252.
     *<table summary="Message format">
     *   <tr>
     *     <th>0</th>
     *     <th>1</th>
     *     <th>2</th>
     *     <th>3</th>
     *     <th>4</th>
     *     <th>5</th>
     *     <th>6</th>
     *     <th>7</th>
     *     <th>8</th>
     *     <th>9</th>
     *     <th>10</th>
     *     <th>11</th>
     *     <th>12</th>
     *     <th>13</th>
     *     <th>14</th>
     *     <th>15</th>
     *   </tr>
     *   <tr>
     *     <td colspan="2">Version</td>
     *     <td colspan="2">Type</td>
     *     <td colspan="4">Token Length</td>
     *     <td colspan="8">Code</td>
     *   </tr>
     *   <tr>
     *     <td colspan="16">Token (if any TKL 0-8 bytes)<br></td>
     *   </tr>
     *   <tr>
     *     <td colspan="16">Options (if any)</td>
     *   </tr>
     *   <tr>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td>1</td>
     *     <td colspan="8">Payload (if any)</td>
     *   </tr>
     * </table>
     * @return the binary string representing the message
     * @throws MessageFormattingException Thrown if an error occurred while formatting the message
     * @throws OptionFormattingException  Thrown if an error occured while formattting an option
     */
    public String format() throws MessageFormattingException, OptionFormattingException
    {
        StringBuilder outputBuilder = new StringBuilder();

        //Version on 2 bits

        outputBuilder.append(version);

        //Type on 2 bits

        if(type > 3)
        {
            throw new MessageFormattingException("Incorrect type size");
        }

        String typeBinString = String.format("%2s",Integer.toBinaryString(type)).replace(' ', '0');
        outputBuilder.append(typeBinString);

        //Token length on 4 bits

        if(tokenLength > 8)
        {
            throw new MessageFormattingException("Incorrect token length");
        }

        String tokenLengthBinString = String.format("%4s",Integer.toBinaryString(tokenLength)).replace(' ', '0');
        outputBuilder.append(tokenLengthBinString);

        //Code class on 3 bits

        if(codeClass > 5)
        {
            throw  new MessageFormattingException("Incorect code class");
        }

        String codeClassBinString = String.format("%3s",Integer.toBinaryString(codeClass)).replace(' ', '0');
        outputBuilder.append(codeClassBinString);

        //Code subfield on 5 bits

        if(codeSubfield > 31)
        {
            throw  new MessageFormattingException("Incorect code class");
        }

        String codeSubfieldBinString = String.format("%5s",Integer.toBinaryString(codeSubfield)).replace(' ', '0');
        outputBuilder.append(codeSubfieldBinString);

        //Message Id on 16 bits

        if(messageId > 65535)
        {
            throw new MessageFormattingException("Message id too big");
        }

        String messageIdBinString = String.format("%16s",Integer.toBinaryString(messageId)).replace(' ', '0');
        outputBuilder.append(messageIdBinString);

        //Token if any

        if(tokenLength > 0)
        {
            StringBuilder tokenbuilder = new StringBuilder();

            for(byte tokenByte : token)
            {
                //Transforming byte into binary string and ensuring it's on 8n bits
                tokenbuilder.append(String.format("%8s",Integer.toBinaryString(tokenByte & 0xFF)).replace(' ', '0'));
            }
            outputBuilder.append(tokenbuilder.toString());
        }

        //Options if any

        String optionsBinString = options.formatForMessage();
        outputBuilder.append(optionsBinString);

        //Payload if any

        if(payload.length() > 0)
        {
            //Adding the payload marker
            outputBuilder.append(PAYLOAD_MARKER);
            outputBuilder.append(StringFormatter.getInstance().formatValue(payload));
        }

        return outputBuilder.toString();
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass()).append(" ");
        stringBuilder.append(" Options : ").append(" ").append(options.toString());
        stringBuilder.append(" Token : ").append(" ").append(BinaryUtils.bytesToString(token));
        stringBuilder.append(" Message ID").append(" ").append(messageId);
        stringBuilder.append(" Payload ").append(" ").append(payload);
        return stringBuilder.toString();
    }
}
