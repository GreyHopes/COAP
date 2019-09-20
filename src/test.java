import utils.options.*;

public class test {

    public static void test(CoAPOption option)
    {
        System.out.println(option.getClass());
    }

    public static void main(String[] args)
    {
        IfNoneMatch a = new IfNoneMatch();
        test(a);
    }
}
