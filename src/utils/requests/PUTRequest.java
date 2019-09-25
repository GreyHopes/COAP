package utils.requests;

import utils.options.OptionsArray;

/**
 * Represents a PUT request
 */
public class PUTRequest extends CoAPRequest {
    /**
     * Instantiates a new Put request.
     */
    public PUTRequest()
    {
        super();
        codeSubfield = 3;
        options = new OptionsArray();
    }
}
