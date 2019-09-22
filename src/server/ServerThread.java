package server;

import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormatingException;
import utils.formatter.StringFormatter;
import utils.requests.DELETERequest;
import utils.requests.GETRequest;
import utils.requests.POSTRequest;
import utils.requests.PUTRequest;
import utils.responses.CoAPResponse;
import utils.responses.clientErrors.MethodNotAllowedResponse;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ServerThread extends Thread
{
    protected Socket clientSocket;
    protected Map<Class, Method> requestHandlers;

    protected OutputStream out;
    protected InputStream in;

    public ServerThread(Socket socket) throws Exception
    {
        clientSocket = socket;
        requestHandlers.put(GETRequest.class,getClass().getDeclaredMethod("handleGET", GETRequest.class));
        requestHandlers.put(POSTRequest.class,getClass().getDeclaredMethod("handlePOST", POSTRequest.class));
        requestHandlers.put(PUTRequest.class,getClass().getDeclaredMethod("handlePUT", PUTRequest.class));
        requestHandlers.put(DELETERequest.class,getClass().getDeclaredMethod("handleDELETE", DELETERequest.class));

        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
    }

    public void run()
    {
        try {
            CoAPMessage requestReceived = CommunicationUtilities.receiveMessage(in);

            if(requestHandlers.get(requestReceived.getClass()) != null)
            {

            }
            else
            {
                //Method not allowed
                MethodNotAllowedResponse response = new MethodNotAllowedResponse();

                if(requestReceived.isConfirmable())
                {
                    response.setMessageId(requestReceived.getMessageId());
                    response.setToken(requestReceived.getToken());
                }
            }


        } catch (IOException e) {
            //TODO IoException server
            e.printStackTrace();
        } catch (MessageFormattingException e) {
            //TODO Message formatting
            e.printStackTrace();
        } catch (MessageParsingException e) {
            //TODO Message Parsing
            e.printStackTrace();
        }
    }

    public CoAPResponse handleGET(GETRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse handlePOST(POSTRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse handlePUT(PUTRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse handleDELETE(DELETERequest request) throws Exception
    {
        return null;
    }
}
