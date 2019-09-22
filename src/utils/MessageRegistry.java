package utils;

import utils.exceptions.MessageParsingException;
import utils.exceptions.UnsupportedMessageClassException;
import utils.requests.*;
import utils.responses.clientErrors.*;
import utils.responses.serverErrors.*;
import utils.responses.successes.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MessageRegistry
{
    public static Map<Integer,Map<Integer,Class>> registry;
    private static MessageRegistry instance = null;

    public MessageRegistry()
    {
        registry = new HashMap<>();

        //Requests
        Map<Integer,Class> tempRegistry = new HashMap<>();
        tempRegistry.put(1, GETRequest.class);
        tempRegistry.put(2, POSTRequest.class);
        tempRegistry.put(3, PUTRequest.class);
        tempRegistry.put(4, DELETERequest.class);

        registry.put(0,tempRegistry);

        //Successes
        tempRegistry.clear();
        tempRegistry.put(1, CreatedResponse.class);
        tempRegistry.put(2, DeletedResponse.class);
        tempRegistry.put(3, ValidResponse.class);
        tempRegistry.put(4, ChangedResponse.class);
        tempRegistry.put(5, ContentResponse.class);

        registry.put(2,tempRegistry);

        //Client errors
        tempRegistry.clear();
        tempRegistry.put(0, BadRequestResponse.class);
        tempRegistry.put(1, UnauthorizedResponse.class);
        tempRegistry.put(2, BadOptionResponse.class);
        tempRegistry.put(3, ForbiddenResponse.class);
        tempRegistry.put(4, NotFoundResponse.class);
        tempRegistry.put(5, MethodNotAllowedResponse.class);
        tempRegistry.put(6, NotAcceptable.class);
        tempRegistry.put(12, PreconditionFailedResponse.class);
        tempRegistry.put(13, RequestEntityTooLargeResponse.class);
        tempRegistry.put(15, UnsupportedContentFormatResponse.class);

        registry.put(4,tempRegistry);

        //Server Errors
        tempRegistry.clear();
        tempRegistry.put(0, InternalServerErrorResponse.class);
        tempRegistry.put(1, NotImplementedResponse.class);
        tempRegistry.put(2, BadGatewayResponse.class);
        tempRegistry.put(3, ServiceUnavailableResponse.class);
        tempRegistry.put(4, GatewayTimeoutResponse.class);
        tempRegistry.put(5, ProxiyingNotSupportedResponse.class);

        registry.put(5,tempRegistry);
    }

    public static MessageRegistry getInstance()
    {
        if(instance == null)
        {
            instance = new MessageRegistry();
        }

        return instance;
    }

    public CoAPMessage generateMessageFromCode(int codeClass,int codeSubfield) throws UnsupportedMessageClassException, MessageParsingException {

        Map<Integer,Class> messageClassRegistry = registry.get(codeClass);
        if(messageClassRegistry == null)
        {
            throw new UnsupportedMessageClassException();
        }

        Class messageClass = messageClassRegistry.get(codeSubfield);
        if(messageClass == null)
        {
            throw new UnsupportedMessageClassException();
        }

        try
        {
            Constructor<CoAPMessage> constructor= messageClass.getDeclaredConstructor();
            CoAPMessage output = constructor.newInstance();

            return output;
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating message class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating message class");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating message class");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating message class");
        }

    }
}
