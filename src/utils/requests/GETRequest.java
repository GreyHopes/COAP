package utils.requests;

import utils.options.OptionsArray;

/**
 * Represents a GET request
 */
public class GETRequest extends CoAPRequest
{
    /**
     * Instantiates a new Get request.
     */
    public GETRequest()
    {
        super();
        codeSubfield = 1;
        options = new OptionsArray();
    }
}
