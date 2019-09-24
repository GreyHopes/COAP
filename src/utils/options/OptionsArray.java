package utils.options;

import utils.exceptions.OptionFormatingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OptionsArray
{
    protected List<CoAPOption> options;
    protected List<Class> authorizedClasses;

    public OptionsArray()
    {
        options = new ArrayList<>();
        authorizedClasses = new ArrayList<>();
    }

    public void add(CoAPOption option)
    {
        if(authorizedClasses.isEmpty() || authorizedClasses.contains(option.getClass()))
        {
            options.add(option);
        }
    }

    public int size()
    {
        return options.size();
    }

    public boolean isEmpty()
    {
        return options.isEmpty();
    }

    public void remove(CoAPOption option)
    {
        options.remove(option);
    }

    public void sort()
    {
        options.sort(Comparator.comparingInt(CoAPOption::getOptionNumber));
    }

    public String formatForMessage() throws OptionFormatingException
    {
        sort();

        if(options.isEmpty())
        {
            return "";
        }

        StringBuilder output = new StringBuilder(options.get(0).format(null));

        for(int i = 1;i<options.size();i++)
        {
            output.append(options.get(i).format(options.get(i-1)));
        }

        return output.toString();
    }

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

    public CoAPOption getLastOption()
    {
        return options.get(options.size()-1);
    }

    public void setAuthorizedClasses(List<Class> list)
    {
        authorizedClasses = list;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append(" ");
        for(CoAPOption option : options)
        {
            stringBuilder.append(option.getClass()).append(" : ").append(option.getValue()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() -1).append("}");
        return stringBuilder.toString();
    }
}
