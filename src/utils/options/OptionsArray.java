package utils.options;

import utils.exceptions.OptionFormattingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class is used to make an array of options that can manipulate all the options of a message.
 * This class is also used to generate all the option portion of a message.
 */
public class OptionsArray
{
    /**
     * The Options.
     */
    protected List<CoAPOption> options;
    /**
     * The Authorized classes.
     */
    protected List<Class> authorizedClasses;

    /**
     * Instantiates a new Options array.
     */
    public OptionsArray()
    {
        options = new ArrayList<>();
        authorizedClasses = new ArrayList<>();
    }

    /**
     * Add an option to the array.
     *
     * @param option the option
     */
    public void add(CoAPOption option)
    {
        if(authorizedClasses.isEmpty() || authorizedClasses.contains(option.getClass()))
        {
            options.add(option);
        }
    }

    /**
     * @return the size of the array.
     */
    public int size()
    {
        return options.size();
    }

    /**
     * @return if the array is empty or not.
     */
    public boolean isEmpty()
    {
        return options.isEmpty();
    }

    /**
     * Remove an option.
     *
     * @param option the option to remobe
     */
    public void remove(CoAPOption option)
    {
        options.remove(option);
    }

    /**
     * Sort the array by option number.
     */
    public void sort()
    {
        options.sort(Comparator.comparingInt(CoAPOption::getOptionNumber));
    }

    /**
     * Format the options for message string.
     *
     * @return the string
     * @throws OptionFormattingException Thrown ff an error occurred while formatting
     */
    public String formatForMessage() throws OptionFormattingException
    {
        //We sort the array
        sort();

        if(options.isEmpty())
        {
            return "";
        }

        StringBuilder output = new StringBuilder(options.get(0).format(null));

        //Foreach options we generate the binary string using the previous option
        for(int i = 1;i<options.size();i++)
        {
            output.append(options.get(i).format(options.get(i-1)));
        }

        return output.toString();
    }

    /**
     * Tells if the array contains an option of the specified class
     *
     * @param search the class to search
     * @return boolean
     */
    public boolean containsOneOfClass(Class search)
    {
        for(CoAPOption option : options)
        {
            if(option.getClass().equals(search))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets one option of specified class.
     *
     * @param search the search
     * @return one option of the class, can be <code>null</code>
     */
    public CoAPOption getOneOfClass(Class search)
    {
        for(CoAPOption option : options)
        {
            if(option.getClass().equals(search))
            {
                return option;
            }
        }
        return null;
    }

    /**
     * @return the last option of the array
     */
    public CoAPOption getLastOption()
    {
        return options.get(options.size()-1);
    }

    /**
     * Sets authorized classes.
     *
     * @param list A list of classes
     */
    public void setAuthorizedClasses(List<Class> list)
    {
        authorizedClasses = list;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        //We will format the array as a sort of json object
        stringBuilder.append("{").append(" ");
        for(CoAPOption option : options)
        {
            stringBuilder.append(option.getClass()).append(" : ").append(option.getValue()).append(",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() -1).append("}");
        return stringBuilder.toString();
    }
}
