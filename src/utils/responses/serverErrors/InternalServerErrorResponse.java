package utils.responses.serverErrors;

public class InternalServerErrorResponse extends ServerErrorResponse
{
    public InternalServerErrorResponse()
    {
        super();
        codeSubfield = 0;
        payload = "Internal Server Error";
    }
}
