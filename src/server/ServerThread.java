package server;

import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormatingException;
import utils.requests.*;
import utils.responses.CoAPResponse;
import utils.responses.clientErrors.ImATeapotResponse;
import utils.responses.clientErrors.MethodNotAllowedResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
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
        requestHandlers = new HashMap<>();
        requestHandlers.put(GETRequest.class,getClass().getDeclaredMethod("get", GETRequest.class));
        requestHandlers.put(POSTRequest.class,getClass().getDeclaredMethod("post", POSTRequest.class));
        requestHandlers.put(PUTRequest.class,getClass().getDeclaredMethod("put", PUTRequest.class));
        requestHandlers.put(DELETERequest.class,getClass().getDeclaredMethod("delete", DELETERequest.class));

        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
        System.out.println("Thread Initialized");
    }

    public void run()
    {
        try {
            System.out.println("Treating request");
            CoAPMessage requestReceived = CommunicationUtilities.receiveMessage(in);
            if(requestHandlers.get(requestReceived.getClass()) != null)
            {
                CoAPResponse response = (CoAPResponse)requestHandlers.get(requestReceived.getClass()).invoke(this,requestReceived);
                CommunicationUtilities.sendMessage(out,response);
                System.out.println("Job's done");
                clientSocket.close();
            }
            else
            {
                //Method not allowed
                MethodNotAllowedResponse response = new MethodNotAllowedResponse();
                response.setToken(requestReceived.getToken());

                if(requestReceived.isConfirmable())
                {
                    response.setMessageId(requestReceived.getMessageId());
                }
                else
                {
                    response.setType(CoAPMessage.NON_CONFIRMABLE);
                }

                CommunicationUtilities.sendMessage(out,response);
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO error Must terminate thread for client at "+clientSocket.getInetAddress().toString());
        } catch (MessageFormattingException e) {
            //TODO Message formatting
            e.printStackTrace();
        } catch (MessageParsingException e) {
            //TODO Message Parsing
            e.printStackTrace();
        } catch (OptionFormatingException e) {
            //TODO
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Illegal access Must terminate thread for client at "+clientSocket.getInetAddress().toString());
        } catch (InvocationTargetException e) {
            System.out.println("Invocation error Must terminate thread for client at "+clientSocket.getInetAddress().toString());
            e.printStackTrace();
        }
    }

    public CoAPResponse internalServerError(CoAPRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse get(GETRequest request) throws Exception
    {
        System.out.println("Coucou dans l'invocation");
        return new ImATeapotResponse();
    }

    public CoAPResponse post(POSTRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse put(PUTRequest request) throws Exception
    {
        return null;
    }

    public CoAPResponse delete(DELETERequest request) throws Exception
    {
        return null;
    }
}
