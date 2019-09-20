package utils.requests;

import utils.options.OptionsArray;

public class DELETERequest extends CoAPRequest
{
    public DELETERequest()
    {
        codeSubfield = 4;
        options = new OptionsArray();
    }
}
