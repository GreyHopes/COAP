package utils;

import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormatingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class CommunicationUtilities
{
    public static byte[] generateToken()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[4];
        random.nextBytes(bytes);
        return bytes;
    }

    public static String receiveBinaryString(InputStream in) throws IOException {
        StringBuilder receivedBinaryStringBuilder = new StringBuilder();

        int bytesRead;
        do
        {
            bytesRead = in.read();
            if(bytesRead != -1)
            {
                receivedBinaryStringBuilder.append(String.format("%8s",Integer.toBinaryString(bytesRead)).replace(' ', '0'));
            }
        }while(bytesRead != -1);

        return receivedBinaryStringBuilder.toString();
    }

    public static CoAPMessage receiveMessage(InputStream in) throws IOException, MessageFormattingException, MessageParsingException {
        String receivedBinaryString = receiveBinaryString(in);
        return CoAPMessage.parse(receivedBinaryString);
    }

    public static void sendBinaryString(OutputStream out,String toSend) throws IOException
    {
        byte[] byteArraytoSend = BinaryUtils.binaryStringToBytes(toSend);
        out.write(byteArraytoSend);
        out.flush();
    }

    public static void sendMessage(OutputStream out,CoAPMessage message) throws IOException, MessageFormattingException, OptionFormatingException {
        String binaryStringToSend = message.format();
        sendBinaryString(out,binaryStringToSend);
    }
}
