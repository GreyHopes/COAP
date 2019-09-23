package utils.responses.clientErrors;

public class ImATeapotResponse extends ClientErrorResponse
{
    public ImATeapotResponse()
    {
        super();
        codeSubfield = 18;
        payload = "I'm a teapot";
    }
}
