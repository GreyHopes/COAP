package utils;

import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormattingException;
import utils.exceptions.UnrecognizedOptionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

/**
 * Static function to ease the communication process
 */
public class CommunicationUtilities
{
    /**
     * Generate a 4 byte token with a SecureRandom.
     *
     * @return the token
     */
    public static byte[] generateToken()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[4];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Receive a binary string.
     *
     * @param in the input stream
     * @return the string received
     * @throws IOException Exception thrown when an io error has occurred
     */
    public static String receiveBinaryString(InputStream in) throws IOException {
        StringBuilder receivedBinaryStringBuilder = new StringBuilder();

        int bytesRead;
        do
        {
            bytesRead = in.read();
            if(bytesRead != -1)
            {
                //We format the string to be sure everything is on 8 bits
                receivedBinaryStringBuilder.append(String.format("%8s",Integer.toBinaryString(bytesRead)).replace(' ', '0'));
            }
        }while(in.available() != 0);

        return receivedBinaryStringBuilder.toString();
    }

    /**
     * Receives a message.
     *
     * @param in The InputStream
     * @return the received message
     * @throws IOException Thrown if an io error occurred
     * @throws MessageFormattingException Thrown of an error was found in the message formatting
     * @throws MessageParsingException Thrown if an error occurred while parsing the message
     * @throws UnrecognizedOptionException Thrown if an unrecognized option was found and the message must be rejected
     */
    public static CoAPMessage receiveMessage(InputStream in) throws IOException, MessageFormattingException, MessageParsingException, UnrecognizedOptionException {

        String receivedBinaryString = receiveBinaryString(in);
        return CoAPMessage.parse(receivedBinaryString);
    }

    /**
     * Send binary string.
     *
     * @param out The OutputStream
     * @param toSend The string to send
     * @throws IOException Thrown if an IO error occurred while sending.
     */
    public static void sendBinaryString(OutputStream out,String toSend) throws IOException
    {
        byte[] byteArraytoSend = BinaryUtils.binaryStringToBytes(toSend);
        out.write(byteArraytoSend);
        out.flush();
    }

    /**
     * Send message.
     *
     * @param out The OutputStream
     * @param message the message to send
     * @throws IOException Thrown if an IO error occurred while sending.
     * @throws MessageFormattingException Thrown if an error occurred while formatting the message
     * @throws OptionFormattingException Thrown if an error occurred while formatting an option
     */
    public static void sendMessage(OutputStream out,CoAPMessage message) throws IOException, MessageFormattingException, OptionFormattingException {
        String binaryStringToSend = message.format();
        sendBinaryString(out,binaryStringToSend);
    }
}
