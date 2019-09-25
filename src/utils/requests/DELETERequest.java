package utils.requests;

import utils.options.OptionsArray;

/**
 * Represents a DELETE request.
 */
public class DELETERequest extends CoAPRequest
{
    /**
     * Instantiates a new Delete request.
     */
    public DELETERequest()
    {
        super();
        codeSubfield = 4;
        options = new OptionsArray();
    }
}
