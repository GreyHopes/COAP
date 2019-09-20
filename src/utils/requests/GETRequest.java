package utils.requests;

import utils.options.OptionsArray;

public class GETRequest extends CoAPRequest
{
    public GETRequest()
    {
        codeSubfield = 1;

        options = new OptionsArray();
    }
}
