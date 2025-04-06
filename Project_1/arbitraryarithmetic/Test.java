package arbitraryarithmetic;

public class Test {
    public static void main(String[] args) {
        AInteger a = new AInteger("14344163160445929942680697312322");
        AInteger b = new AInteger("23017167694823904478474013730519");
        AInteger c = a.multiply(b);

        System.out.println("Expected: -5, Actual: " + c.getValue());

        if (c.getValue().equals("330162008905899217578310782382075660760972861550182008086155118")) {
            System.out.println("Test passed ✅");
        } else {
            System.out.println("Test failed ❌");
        }
    }
}
