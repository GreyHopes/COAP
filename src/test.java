import utils.*;
import utils.responses.clientErrors.BadRequestResponse;

public class test {
    public static void main(String[] args)
    {
        BadRequestResponse a = new BadRequestResponse();
        System.out.println(a.getCodeClass());
    }
}
