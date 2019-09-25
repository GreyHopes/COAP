import utils.options.UriPort;
import utils.requests.GETRequest;

public class test {
    public static void main(String[] args)
    {
        GETRequest test = new GETRequest();

        test.addOption(new UriPort(27));
        test.setPayload("TEST");

        System.out.println(test.toString());

        System.out.println(9 & 1);
        System.out.println((11 & 1) == 1 && (11 & 2) == 0);

        System.out.println(9 & 2);
        System.out.println(10 & 2);

        System.out.println(11 & 1);
        System.out.println(11 & 2);
    }
}
