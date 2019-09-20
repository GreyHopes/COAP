package utils;

import utils.options.OptionsArray;

public abstract class CoAPMessage
{
    //CoAP version number must be set to 01
    private String version = "01";

    /*
    Message type
    Confirmable              0
    Non Confirmable       1
    Acknowledgment      2
    Reset                     3

    Default is 0
     */
    protected int type = 0;

    //Length of the token field
    protected int tokenLength = 0;

    protected int token = 0;
    /*
    Message code composed of class c (3 bits 0-7) and subfield (5 bits 0-31) similar to HTTP
    Class :
    Request         0
    Success         2
    Client Error    4
    Server Error   5
     */
    protected int codeClass = 0;
    protected int codeSubfield = 0;

    protected OptionsArray options;

    protected String payload = "";
}
