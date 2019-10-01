package utils.responses;

public class EmptyResponse extends CoAPResponse
{
    public EmptyResponse()
    {
        super();
        this.codeSubfield = 0;
        this.payload="";
        this.codeClass = 0;
    }
}
