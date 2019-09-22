package utils.options;

import utils.exceptions.MessageParsingException;
import utils.exceptions.UnsupportedOptionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class OptionsRegistry
{
    public static Map<Integer,Class> registry;
    private static OptionsRegistry instance = null;

    public OptionsRegistry()
    {
        registry = new HashMap<>();
        registry.put(1,IfMatch.class);
        registry.put(3,UriHost.class);
        registry.put(4,ETag.class);
        registry.put(5,IfNoneMatch.class);
        registry.put(7,UriPort.class);
        registry.put(8,LocationPath.class);
        registry.put(11,UriPath.class);
        registry.put(12,ContentFormat.class);
        registry.put(14,MaxAge.class);
        registry.put(15,UriQuery.class);
        registry.put(17,Accept.class);
        registry.put(20,LocationQuery.class);
        registry.put(35,ProxyUri.class);
        registry.put(39,ProxyScheme.class);
        registry.put(60,Sizel.class);
    }

    public static OptionsRegistry getInstance()
    {
        if(instance == null)
        {
            instance = new OptionsRegistry();
        }

        return instance;
    }

    public CoAPOption generateOptionFromNumber(int optionNumber) throws UnsupportedOptionException, MessageParsingException
    {
        Class optionClass = registry.get(optionNumber);
        if(optionClass == null)
        {
            throw new UnsupportedOptionException();
        }

        try
        {
            Constructor<CoAPOption> constructor= optionClass.getDeclaredConstructor();
            CoAPOption output = constructor.newInstance();

            return output;
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating option");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating option");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating option");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new MessageParsingException("Error while creating option");
        }
    }
}
