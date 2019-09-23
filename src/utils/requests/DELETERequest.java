package utils.requests;

import utils.options.OptionsArray;

public class DELETERequest extends CoAPRequest
{
    public DELETERequest()
    {
        super();
        codeSubfield = 4;
        options = new OptionsArray();
    }
}
