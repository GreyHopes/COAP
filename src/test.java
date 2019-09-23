import utils.*;
import utils.exceptions.MessageFormattingException;
import utils.exceptions.MessageParsingException;
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
        test.setPayload("TEST");


        try {
            String a = test.format();
            System.out.println(a);
            CoAPMessage b = CoAPMessage.parse(a);
            System.out.println(b.getPayload());
        } catch (MessageFormattingException e) {
            e.printStackTrace();
        } catch (OptionFormatingException e) {
            e.printStackTrace();
        } catch (MessageParsingException e) {
            e.printStackTrace();
        }
    }
}
