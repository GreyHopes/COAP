package utils.requests;

import utils.options.OptionsArray;

/**
 * Represents a POST request
 */
public class POSTRequest extends CoAPRequest
{
    /**
     * Instantiates a new Post request.
     */
    public POSTRequest()
    {
        super();
        codeSubfield = 2;
        options = new OptionsArray();
    }
}
