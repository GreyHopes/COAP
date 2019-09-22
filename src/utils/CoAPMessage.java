package utils;

import utils.exceptions.MessageFormattingException;
import utils.exceptions.OptionFormatingException;
import utils.options.CoAPOption;
import utils.options.OptionsArray;

public abstract class CoAPMessage {
    //CoAP version number must be set to 01
    private String version = "01";

    private final String PAYLOAD_MARKER = "11111111";

    /*
    Message type
    Confirmable              0
    Non Confirmable       1
    Acknowledgment      2
    Reset                     3

    Default is 0
     */
    protected int type = 0;

    //Length of the token field
    protected int tokenLength = 0;

    protected int token = 0;
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

    public void setMessageId(int newId) {
        messageId = newId;
    }

    public void setType(int newType) {
        type = newType;
    }

    public int getCodeClass() {
        return codeClass;
    }

    public static CoAPMessage parse(String binaryMessage) throws MessageFormattingException
    {
        try()
        {
            //Extract version
            String rawBinary = binaryMessage.substring(0,2);
            if(rawBinary != "01")
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
            int parsedToken = 0;

            if(parsedTokenLength > 0)
            {
                index = index + 8 * parsedTokenLength;

                parsedToken = Integer.parseInt(binaryMessage.substring(32,index),2);
            }

            OptionsArray parsedOptions = new OptionsArray();

            //Extracting options if any and not detecting the payload marker
            while(binaryMessage.length() > index && !binaryMessage.substring(index,index+4).equals("1111"))
            {
                int parsedDelta = Integer.parseInt(binaryMessage.substring(index,index+4));

                //Parsing option number with delta
                int parsedOptionNumber = 0;

                if(parsedOptions.size() == 0)
                {
                    parsedOptionNumber = parsedDelta;
                }
                else
                {
                    parsedOptionNumber = parsedDelta + parsedOptions.getLastOption().getOptionNumber();
                }


            }
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
            //TODO Token sur 8n bits

            outputBuilder.append(Integer.toBinaryString(token));
        }

        //Options if any

        String optionsBinString = options.formatForMessage();
        outputBuilder.append(optionsBinString);

        if(payload.length() > 0)
        {
            //TODO Payload ?

            outputBuilder.append(PAYLOAD_MARKER);
            outputBuilder.append(payload);
        }

        return outputBuilder.toString();
    }
}
