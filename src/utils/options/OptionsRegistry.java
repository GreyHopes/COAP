package utils.options;

import utils.exceptions.MessageParsingException;
import utils.exceptions.UnsupportedOptionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Option registry to store and find all supported options.
 */
public class OptionsRegistry
{
    /**
     * The Registry with all the options as an option number - class couple.
     */
    public static Map<Integer,Class> registry;

    /**
     * We only want one registry per execution so we will use the Instance pattern
     */
    private static OptionsRegistry instance = null;

    /**
     * Instantiates a new Options registry with all the supported options.
     */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OptionsRegistry getInstance()
    {
        if(instance == null)
        {
            instance = new OptionsRegistry();
        }

        return instance;
    }

    /**
     * Generate a new option object from an option number.
     *
     * @param optionNumber the option number
     * @return the CoAP option
     * @throws UnsupportedOptionException No option corresponding to the number was found
     * @throws MessageParsingException Thrown to indicate an error while searching for the option.
     */
    public CoAPOption generateOptionFromNumber(int optionNumber) throws UnsupportedOptionException, MessageParsingException
    {
        //We look in the registry if we support the option
        Class optionClass = registry.get(optionNumber);

        //If not
        if(optionClass == null)
        {
            //Critical Option must be reject
            if((optionNumber & 1) == 1 && (optionNumber & 2) == 0)
            {
                throw new UnsupportedOptionException();
            }
            else if((optionNumber & 1) == 0 && (optionNumber & 2) == 2)
            {
                //Elective option must be ignored
                throw new UnsupportedOptionException(true);
            }
            else
            {
                //Invalid option number we reject the message
                throw new UnsupportedOptionException();
            }
        }

        try
        {
            //We generate the option using the class constructor
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
