package utils;

import utils.exceptions.*;
import utils.formatter.StringFormatter;
import utils.options.CoAPOption;
import utils.options.OptionsArray;
import utils.options.OptionsRegistry;

public abstract class CoAPMessage {
    //CoAP version number must be set to 01
    private String version = "01";

    private static final String PAYLOAD_MARKER = "11111111";

    public static final int CONFIRMABLE = 0;
    public static final int RESET = 3;
    public static final int NON_CONFIRMABLE = 1;
    public static final int ACKNOWLEDGMENT = 2;
    /*
    Message type
    Confirmable              0
    Non Confirmable       1
    Acknowledgment      2
    Reset                     3

    Default is 0
     */
    protected int type = CONFIRMABLE;

    //Length of the token field
    protected int tokenLength = 0;

    protected byte[] token = {};
    /*
    Message code composed of class c (3 bits 0-7) and subfield (5 bits 0-31) similar to HTTP
    Class :
    Request         0
    Success         2
    Client Error    4
    Server Error   5
     */
    protected int codeClass;
    protected int codeSubfield;

    protected int messageId = 0;

    protected OptionsArray options;

    protected String payload = "";

    public void setOptions(OptionsArray newArray){options = newArray;}

    public void setMessageId(int newId) {
        messageId = newId;
    }

    public void setType(int newType) {
        type = newType;
    }

    public int getCodeClass() {
        return codeClass;
    }

    public void setToken(byte[] token) {
        this.token = token;
        tokenLength = token.length;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isConfirmable()
    {
        return type == CONFIRMABLE;
    }

    public int getType() {
        return type;
    }

    public int getTokenLength() {
        return tokenLength;
    }

    public byte[] getToken() {
        return token;
    }

    public int getCodeSubfield() {
        return codeSubfield;
    }

    public int getMessageId() {
        return messageId;
    }

    public OptionsArray getOptions() {
        return options;
    }

    public String getPayload() {
        return payload;
    }

    public static CoAPMessage parse(String binaryMessage) throws MessageFormattingException, MessageParsingException {
        try
        {
            //Extract version
            String rawBinary = binaryMessage.substring(0,2);
            if(!rawBinary.equals("01"))
            {
                throw new MessageFormattingException("Incorect version");
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

            int index = 32;

            //Extracting token if any
            byte[] parsedToken = {};

            if(parsedTokenLength > 0)
            {
                index = index + 8 * parsedTokenLength;

                parsedToken = BinaryUtils.binaryStringToBytes(binaryMessage.substring(32,index));
            }

            CoAPMessage message = null;

            try {
                message = MessageRegistry.getInstance().generateMessageFromCode(parsedCodeClass,parsedCodeSubfield);
            } catch (UnsupportedMessageClassException e) {
                e.printStackTrace();
                throw new MessageFormattingException("Unsupported message Class");
            }

            message.setMessageId(parsedMessageId);
            message.setType(parsedType);
            message.setToken(parsedToken);

            OptionsArray parsedOptions = new OptionsArray();

            //Extracting options if any and not detecting the payload marker
            while(binaryMessage.length() > index && !binaryMessage.substring(index,index+4).equals("1111"))
            {
                int parsedDelta = Integer.parseInt(binaryMessage.substring(index,index+4),2);

                if(parsedDelta > 14)
                {
                    throw new MessageFormattingException("Invalid delta");
                }
                index+=4;

                //Parsing option length

                int parsedOptionLength = Integer.parseInt(binaryMessage.substring(index,index+4),2);

                if(parsedOptionLength > 14)
                {
                    throw new MessageFormattingException("Invalid length");
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

                String value = binaryMessage.substring(index,index + 8 * parsedOptionLength);
                index += 8 * parsedOptionLength;

                CoAPOption newOption = null;

                try
                {
                    newOption = OptionsRegistry.getInstance().generateOptionFromNumber(parsedOptionNumber);
                } catch (UnsupportedOptionException e) {
                    e.printStackTrace();
                    throw new MessageFormattingException("Unsupported option");
                } catch (MessageParsingException e) {
                    throw e;
                }

                //TODO Reformatter la value
                newOption.setValue(value);

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
            throw new MessageFormattingException();
        }
    }

    public void addOption(CoAPOption option)
    {
        options.add(option);
    }

    public String format() throws MessageFormattingException, OptionFormatingException {
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
                //Transforming byte into binary string
                tokenbuilder.append(String.format("%8s",Integer.toBinaryString(tokenByte & 0xFF)).replace(' ', '0'));
            }
            outputBuilder.append(tokenbuilder.toString());
        }

        //Options if any

        String optionsBinString = options.formatForMessage();
        outputBuilder.append(optionsBinString);

        if(payload.length() > 0)
        {
            outputBuilder.append(PAYLOAD_MARKER);

            //TODO
            System.out.println("Payload");
            System.out.println(payload);
            System.out.println(StringFormatter.getInstance().formatValue(payload));

            outputBuilder.append(StringFormatter.getInstance().formatValue(payload));
        }

        return outputBuilder.toString();
    }
}
