package client;

import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.options.UriHost;
import utils.options.UriPath;
import utils.options.UriPort;
import utils.requests.GETRequest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMain
{
    public static final String SERVER_ADRESS = "localhost";
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
            test.addOption(new UriPath("temperature"));
            test.setPayload("TEST");

            CommunicationUtilities.sendMessage(out,test);
            CoAPMessage response = CommunicationUtilities.receiveMessage(in);
            System.out.println(response);
            socket.close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
