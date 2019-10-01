package client;

import utils.CoAPMessage;
import utils.CoAPVariables;
import utils.CommunicationUtilities;
import utils.options.UriHost;
import utils.options.UriPath;
import utils.options.UriPort;
import utils.requests.*;
import utils.responses.EmptyResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class ClientMain
{
    public static final String SERVER_ADRESS = "localhost";
    public static final int SERVER_PORT = 5683;

    public static void main(String[] args)
    {
        try {
            confirmableGET();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    public static void simpleGET(String resource) throws Throwable
    {
        InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
        Socket socket = new Socket(serverAdress,SERVER_PORT);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        CoAPRequest request = new GETRequest();
        request.addOption(new UriHost(SERVER_ADRESS));
        request.addOption(new UriPort(SERVER_PORT));
        request.addOption(new UriPath(resource));
        request.setType(CoAPMessage.NON_CONFIRMABLE);

        CommunicationUtilities.sendMessage(out,request);
        CoAPMessage response = CommunicationUtilities.receiveMessage(in);
        if(response.getType() == CoAPMessage.NON_CONFIRMABLE)
        {
            System.out.println(response);
        }
        else
        {
            System.out.println("Invalid response");
        }

        socket.close();
    }

    public static void simplePUT() throws Throwable
    {
        simpleGET("test");

        InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
        Socket socket = new Socket(serverAdress,SERVER_PORT);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        CoAPRequest request = new PUTRequest();
        request.addOption(new UriHost(SERVER_ADRESS));
        request.addOption(new UriPort(SERVER_PORT));
        request.addOption(new UriPath("test"));
        request.setPayload("Ceci est un test");
        request.setType(CoAPMessage.NON_CONFIRMABLE);

        CommunicationUtilities.sendMessage(out,request);
        CoAPMessage response = CommunicationUtilities.receiveMessage(in);
        if(response.getType() == CoAPMessage.NON_CONFIRMABLE)
        {
            System.out.println(response);
        }
        else
        {
            System.out.println("Invalid response");
        }

        socket.close();

        simpleGET("test");
    }

    public static void simplePOST() throws Throwable
    {
        simpleGET("temperature");

        InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
        Socket socket = new Socket(serverAdress,SERVER_PORT);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        CoAPRequest request = new POSTRequest();
        request.addOption(new UriHost(SERVER_ADRESS));
        request.addOption(new UriPort(SERVER_PORT));
        request.addOption(new UriPath("temperature"));
        request.setPayload("23.2 C");
        request.setType(CoAPMessage.NON_CONFIRMABLE);

        CommunicationUtilities.sendMessage(out,request);
        CoAPMessage response = CommunicationUtilities.receiveMessage(in);
        if(response.getType() == CoAPMessage.NON_CONFIRMABLE)
        {
            System.out.println(response);
        }
        else
        {
            System.out.println("Invalid response");
        }

        socket.close();

        simpleGET("temperature");
    }

    public static void simpleDELETE() throws Throwable
    {
        simpleGET("teapot");

        InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
        Socket socket = new Socket(serverAdress,SERVER_PORT);


        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        CoAPRequest request = new DELETERequest();
        request.addOption(new UriHost(SERVER_ADRESS));
        request.addOption(new UriPort(SERVER_PORT));
        request.addOption(new UriPath("teapot"));
        request.setType(CoAPMessage.NON_CONFIRMABLE);

        CommunicationUtilities.sendMessage(out,request);
        CoAPMessage response = CommunicationUtilities.receiveMessage(in);
        if(response.getType() == CoAPMessage.NON_CONFIRMABLE)
        {
            System.out.println(response);
        }
        else
        {
            System.out.println("Invalid response");
        }

        socket.close();

        simpleGET("teapot");
    }

    public static void confirmableGET() throws Throwable
    {

        InetAddress serverAdress = InetAddress.getByName(SERVER_ADRESS);
        Socket socket = new Socket(serverAdress,SERVER_PORT);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        CoAPRequest request = new GETRequest();
        request.addOption(new UriHost(SERVER_ADRESS));
        request.addOption(new UriPort(SERVER_PORT));
        request.addOption(new UriPath("temperature"));
        request.setType(CoAPMessage.CONFIRMABLE);

        double timeout = CoAPVariables.ACK_TIMEOUT + Math.random() * CoAPVariables.ACK_RANDOM_FACTOR;
        int retransmit = 0;

        CommunicationUtilities.sendMessage(out,request);

        while(retransmit <= CoAPVariables.MAX_RETRANSMIT)
        {
            try
            {
                timeout = timeout * Math.pow(2,retransmit);
                socket.setSoTimeout((int)Math.round(timeout * 1000));

                CoAPMessage response = CommunicationUtilities.receiveMessage(in);

                if(response.getType() != CoAPMessage.ACKNOWLEDGMENT && response.getType() != CoAPMessage.RESET)
                {
                    throw new Exception("Error message received was not an ACK or a RST");
                }
                else
                {
                    if(response.getType() == CoAPMessage.ACKNOWLEDGMENT && response instanceof EmptyResponse)
                    {
                        socket.setSoTimeout(60000);
                        response = CommunicationUtilities.receiveMessage(in);
                    }

                    System.out.println(response);
                }
            }
            catch (SocketTimeoutException ex)
            {
                if(socket.getSoTimeout() == 60000)
                {
                    throw new Exception("Timeout for separate response transmission failed");
                }
                else
                {
                    retransmit++;
                    if(retransmit <= CoAPVariables.MAX_RETRANSMIT) CommunicationUtilities.sendMessage(out,request);
                    continue;
                }
            }

            break;
        }

        if(retransmit > CoAPVariables.MAX_RETRANSMIT)
        {
            throw new Exception("Terminating retransmission, transmission failed");
        }
    }
}
