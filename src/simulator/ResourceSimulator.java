package simulator;

import java.util.HashMap;
import java.util.Map;

/**
 * ResourceSimulator simulates the behaviour of a resource system to test our server.
 */
public class ResourceSimulator
{
    /**
     * Contains all the resources and their values
     */
    private Map<String,String> resources;

    /**
     * To ensure that the resources persists during the execution the simulator uses the Instance pattern
     */
    private static ResourceSimulator instance = null;

    /**
     * The constant NOT_FOUND.
     */
    public final static int NOT_FOUND = -1;
    /**
     * The constant SUCCESS.
     */
    public final static int SUCCESS = 0;
    /**
     * The constant CHANGED.
     */
    public final static int CHANGED = 1;
    /**
     * The constant CREATED.
     */
    public final static int CREATED = 2;

    /**
     * Instantiates a new Resource simulator.
     */
    public ResourceSimulator()
    {
        resources = new HashMap<>();
        resources.put("temperature","22.2 C");
        resources.put("teapot","I'm not a teapot");
    }

    /**
     * Simulates the processing of a post request.
     * In this simulation POST will modify the resource if found or return a NOT_FOUND error
     *
     * @param path The path to the resource
     * @param value the new value for the resource
     * @return The int code of the operation result, it can be :
     * <ul>
     *     <li>NOT_FOUND if no resource was found</li>
     *     <li>CHANGED if the operation was successful</li>
     * </ul>
     */
    public int postResource(String path, String value)
    {
        if(getResource(path) == null)
        {
            return NOT_FOUND;
        }
        else
        {
            resources.put(path,value);
        }

        return CHANGED;
    }

    /**
     * Simulates the processing of a put request.
     *
     * @param path The path to the resource
     * @param value The  value for the resource
     * @return The int code of the operation result, it can be :
     * <ul>
     *     <li>CREATED if a new resource have been created</li>
     *     <li>CHANGED if a resources have been changed</li>
     * </ul>
     */
        public int putResource(String path, String value)
    {
        String result = resources.put(path,value);

        if(result ==null)
        {
            return CREATED;
        }
        else
        {
            return CHANGED;
        }

    }

    /**
     * Simulates the processing of a delete request.
     *
     * @param path The path to the resource
     * @return The int code of the operation result, it can be :
     * <ul>
     *     <li>NOT_FOUND if no resource was found</li>
     *      <li>SUCCESS if the operation was successful</li>
     * </ul>
     */
    public int deleteResource(String path)
    {
        if(resources.get(path) == null)
        {
            return NOT_FOUND;
        }
        else
        {
            resources.remove(path);
            return SUCCESS;
        }
    }

    /**
     * Simulates the processing of a get request.
     *
     * @param path The path to the resource
     * @return the resource, can be <code>null</code> if nothing was found
     */
    public String getResource(String path)
    {
        return resources.get(path);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ResourceSimulator getInstance()
    {
        if(instance == null)
        {
            instance = new ResourceSimulator();
        }

        return instance;
    }
}
