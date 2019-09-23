package client;

import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormatingException;
import utils.options.UriHost;
import utils.options.UriPort;
import utils.requests.GETRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain
{
    public static final String SERVER_ADRESS = "192.168.0.185";
    public static final int SERVER_PORT = 5683;

    public static void main(String[] args)
    {
        try {
            InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
            Socket socket = new Socket(serverAdress,SERVER_PORT);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            GETRequest test = new GETRequest();
            test.addOption(new UriHost(SERVER_ADRESS));
            test.addOption(new UriPort(SERVER_PORT));
            test.setPayload("TEST");

            CommunicationUtilities.sendMessage(out,test);
            CoAPMessage response = CommunicationUtilities.receiveMessage(in);
            System.out.println("Received");
            System.out.println(response.getPayload());

            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessageFormattingException e) {
            e.printStackTrace();
        } catch (OptionFormatingException e) {
            e.printStackTrace();
        } catch (MessageParsingException e) {
            e.printStackTrace();
        }

    }
}
