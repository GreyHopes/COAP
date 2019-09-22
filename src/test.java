import utils.*;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.OptionFormatingException;
import utils.formatter.UIntFormatter;
import utils.options.UriPath;
import utils.options.UriPort;
import utils.requests.GETRequest;
import utils.responses.clientErrors.BadRequestResponse;

public class test {
    public static void main(String[] args)
    {
        GETRequest test = new GETRequest();

        test.addOption(new UriPort(27));


        try {
            System.out.println(test.format());
        } catch (MessageFormattingException e) {
            e.printStackTrace();
        } catch (OptionFormatingException e) {
            e.printStackTrace();
        }
    }
}
