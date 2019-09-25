package server;

import simulator.ResourceSimulator;
import utils.CoAPMessage;
import utils.CommunicationUtilities;
import utils.ContentFormatRegistry;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
import utils.exceptions.OptionFormattingException;
import utils.exceptions.UnrecognizedOptionException;
import utils.options.*;
import utils.requests.*;
import utils.responses.CoAPResponse;
import utils.responses.clientErrors.*;
import utils.responses.serverErrors.InternalServerErrorResponse;
import utils.responses.serverErrors.ProxiyingNotSupportedResponse;
import utils.responses.successes.ChangedResponse;
import utils.responses.successes.ContentResponse;
import utils.responses.successes.CreatedResponse;
import utils.responses.successes.DeletedResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread that manages the communication with the client.
 */
public class ServerThread extends Thread
{
    /**
     * The Client socket.
     */
    protected Socket clientSocket;
    /**
     * The Request handlers.
     */
    protected Map<Class, Method> requestHandlers;

    /**
     * The Output stream.
     */
    protected OutputStream out;
    /**
     * The Input stream.
     */
    protected InputStream in;

    /**
     * Instantiates a new Server thread.
     *
     * @param socket the client socket
     * @throws Exception If any error occurred while creating the thread
     */
    public ServerThread(Socket socket) throws Exception
    {
        clientSocket = socket;

        //Filling the map with the links to the handling methods
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

            //Receiving and parsing the message
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
                    //Prevalidate the request with recurrent requirements
                    response = preRejectRequests((CoAPRequest)requestReceived);

                    //Request got through pre validation we can process it
                    if(response == null)
                    {
                        //Calling the handler to generate the response
                        response = (CoAPResponse)requestHandlers.get(requestReceived.getClass()).invoke(this,requestReceived);
                    }
                }
                else
                {
                    //Method not allowed
                    response = new MethodNotAllowedResponse();
                }
            }

            //Setting the token
            response.setToken(requestReceived.getToken());

            if(requestReceived.isConfirmable())
            {
                //If the request is confirmable we piggyback the message id
                response.setMessageId(requestReceived.getMessageId());
            }
            else
            {
                //Else we pass the response to NON as it is an ACK by default
                response.setType(CoAPMessage.NON_CONFIRMABLE);
            }

            //Sending the response and closing the connexion
            CommunicationUtilities.sendMessage(out,response);
            clientSocket.close();
        }
        catch (UnrecognizedOptionException e)
        {
            CoAPMessage forwadedMessage = e.getMessageForwaded();

            //If there is a message to forward and the request was confirmable we send a Bad Request
            if(forwadedMessage != null && forwadedMessage.isConfirmable())
            {
                try
                {
                    BadRequestResponse response = new BadRequestResponse();
                    CommunicationUtilities.sendMessage(out,response);
                    clientSocket.close();
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                    System.out.println("Error while rejecting bad request, terminating");
                }
            }
            else
            {
                System.out.println("Unrecognized option, rejecting and terminating");
                e.printStackTrace();
            }
        }
        catch (OptionFormattingException e) {
            System.out.println("Error while formatting message, terminating");
            e.printStackTrace();
        }  catch (IOException e) {
            System.out.println("IO error Must terminate thread for client at "+clientSocket.getInetAddress().toString());
            e.printStackTrace();
        } catch (MessageFormattingException e) {
            System.out.println("Error while formatting message, terminating");
            e.printStackTrace();
        } catch (MessageParsingException e) {
            System.out.println("Error while parsing message, rejecting and terminating");
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Illegal access Must terminate thread for client at "+clientSocket.getInetAddress().toString());
        } catch (InvocationTargetException e) {
            System.out.println("Invocation error Must terminate thread for client at "+clientSocket.getInetAddress().toString());
            e.printStackTrace();
        }
    }

    /**
     * Pre validate requests co ap response.
     *
     * @param request the request
     * @return a coap response can be <code>null</code> if the response is valid
     */
    public CoAPResponse preRejectRequests(CoAPRequest request)
    {
        if(request.getOptions().containsOneOfClass(Accept.class))
        {
            if(Integer.parseInt(request.getOptions().getOneOfClass(Accept.class).getValue()) != ContentFormatRegistry.TEXT_PLAIN)
            {
                return new UnsupportedContentFormatResponse();
            }
        }

        return null;
    }

    /**
     * Handles the get request
     *
     * @param request the request
     * @return the co ap response
     */
    public CoAPResponse get(GETRequest request)
    {
        //If no path is specified
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        String path = request.getOptions().getOneOfClass(UriPath.class).getValue();

        //This part is specific to the behaviour of our simulator
        String ressource = ResourceSimulator.getInstance().getResource(path);

        if(ressource == null)
        {
            return new NotFoundResponse();
        }

        return new ContentResponse(ressource);
    }

    /**
     * Handles a POST Request.
     *
     * @param request the request
     * @return the co ap response
     */
    public CoAPResponse post(POSTRequest request)
    {
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        if(request.getPayload() == null || request.getPayload().length() == 0)
        {
            return new BadRequestResponse();
        }

        //This part is specific to the behaviour of our simulator
        String path = request.getOptions().getOneOfClass(UriPath.class).getValue();
        String value = request.getPayload();

        int result = ResourceSimulator.getInstance().postResource(path,value);

        if(result == ResourceSimulator.NOT_FOUND)
        {
            return new NotFoundResponse();
        }

        if(result == ResourceSimulator.CHANGED)
        {
            return new ChangedResponse(value);
        }

        return new InternalServerErrorResponse();
    }

    /**
     * Handles a put request
     *
     * @param request the request
     * @return the co ap response
     */
    public CoAPResponse put(PUTRequest request)
    {
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        if(request.getPayload() == null || request.getPayload().length() == 0)
        {
            return new BadRequestResponse();
        }

        String path = request.getOptions().getOneOfClass(UriPath.class).getValue();
        String value = request.getPayload();

        int result = ResourceSimulator.getInstance().putResource(path,value);

        //Changing the response depending on the result of the operation
        if(result == ResourceSimulator.CREATED)
        {
            return new CreatedResponse(value);
        }

        if(result == ResourceSimulator.CHANGED)
        {
            return new ChangedResponse(value);
        }

        //We should not get here but if we do it's an error
        return new InternalServerErrorResponse();
    }

    /**
     * Handles the delete request
     *
     * @param request the request
     * @return the co ap response
     */
    public CoAPResponse delete(DELETERequest request)
    {
        if(!request.getOptions().containsOneOfClass(UriPath.class))
        {
            return new BadRequestResponse();
        }

        String path = request.getOptions().getOneOfClass(UriPath.class).getValue();
        int deleteResult = ResourceSimulator.getInstance().deleteResource(path);

        //Changing the response depending on the operation result
        if(deleteResult == ResourceSimulator.NOT_FOUND)
        {
            return new NotFoundResponse();
        }

        if(deleteResult == ResourceSimulator.SUCCESS)
        {
            return new DeletedResponse();
        }

        return new InternalServerErrorResponse();
    }
}
