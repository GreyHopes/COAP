package server;

import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.ContentFormatRegistry;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormatingException;
import utils.options.Accept;
import utils.options.ProxyScheme;
import utils.options.ProxyUri;
import utils.options.UriPath;
import utils.requests.*;
import utils.responses.CoAPResponse;
import utils.responses.clientErrors.BadRequestResponse;
import utils.responses.clientErrors.ImATeapotResponse;
import utils.responses.clientErrors.MethodNotAllowedResponse;
import utils.responses.clientErrors.UnsupportedContentFormatResponse;
import utils.responses.serverErrors.ProxiyingNotSupportedResponse;

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

            CoAPResponse response = null;

            //Rejecting proxy requests because not implemented
            if(requestReceived.getOptions().containsOneOfClass(ProxyUri.class) || requestReceived.getOptions().containsOneOfClass(ProxyScheme.class))
            {
                response = new ProxiyingNotSupportedResponse();
            }
            else
            {
                //Method allowed
                if(requestHandlers.get(requestReceived.getClass()) != null)
                {
                    response = (CoAPResponse)requestHandlers.get(requestReceived.getClass()).invoke(this,requestReceived);
                }
                else
                {
                    //Method not allowed
                    response = new MethodNotAllowedResponse();
                }
            }

            response.setToken(requestReceived.getToken());

            if(requestReceived.isConfirmable())
            {
                response.setMessageId(requestReceived.getMessageId());
            }
            else
            {
                response.setType(CoAPMessage.NON_CONFIRMABLE);
            }

            clientSocket.close();
            CommunicationUtilities.sendMessage(out,response);

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
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        if(request.getOptions().containsOneOfClass(Accept.class))
        {
            if(Integer.parseInt(request.getOptions().getOneOfClass(Accept.class).getValue()) != ContentFormatRegistry.TEXT_PLAIN)
            {
                return new UnsupportedContentFormatResponse();
            }
        }

        return new ImATeapotResponse();
    }

    public CoAPResponse post(POSTRequest request) throws Exception
    {
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        if(request.getOptions().containsOneOfClass(Accept.class))
        {
            if(Integer.parseInt(request.getOptions().getOneOfClass(Accept.class).getValue()) != ContentFormatRegistry.TEXT_PLAIN)
            {
                return new UnsupportedContentFormatResponse();
            }
        }

        //TODO Récupérer la valeur

        return new ImATeapotResponse();
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
