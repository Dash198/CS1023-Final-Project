package arbitraryarithmetic;

public class Test {
    public static void main(String[] args) {
        AInteger a = new AInteger("100");
        AInteger b = new AInteger("100");
        AInteger c = a.subtract(b);

        System.out.println("Expected: 1, Actual: " + c.getValue());

        if (c.getValue().equals("-54628730626339781337950941125431")) {
            System.out.println("Test passed ✅");
        } else {
            System.out.println("Test failed ❌");
        }
    }
}
