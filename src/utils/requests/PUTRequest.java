package utils.requests;

import utils.options.OptionsArray;

public class PUTRequest extends CoAPRequest {
    public PUTRequest()
    {
        codeSubfield = 3;
        options = new OptionsArray();
    }
}
