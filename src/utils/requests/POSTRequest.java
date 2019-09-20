package utils.requests;

import utils.options.OptionsArray;

public class POSTRequest extends CoAPRequest
{
    public POSTRequest()
    {
        codeSubfield = 2;

        options = new OptionsArray();
    }
}
