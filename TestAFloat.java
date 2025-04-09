import arbitraryarithmetic.*;

public class TestAFloat {
    public static void main(String[] args) {
        test("0.0", "0.0");
        test("0.0", "1234567890.0987654321");
        test("1.0", "0.0");
        test("-1.0", "0.0");
        test("9999999999.9999", "0.0001");
        test("1.9999", "2.0001");
        test("-1.0000", "-1.0000");
        test("-123.456", "789.123");
        test("0.00000001", "0.00000009");
        test("1000000000.000000001", "0.000000009");

        // edge sign flips
        test("999.999", "0.001");   // edge round over
        test("-999.999", "-0.001"); // edge round under
        test("-0.0001", "0.0001");  // tiny cancel

        // multiplication madness
        testMul("1.5", "2.0");
        testMul("0.001", "0.001");
        testMul("123456789.987654321", "0.000000001");
        testMul("-1.1", "1.1");

        // division doom
        testDiv("1.0", "3.0");
        testDiv("123.456", "0.0001");
        testDiv("0.000001", "0.000001");
        testDiv("0.0", "100.0");
        testDiv("100.0", "0.0"); // expect error

        System.out.println("Done testing.");
    }

    static void test(String s1, String s2) {
        AFloat a = new AFloat(s1);
        AFloat b = new AFloat(s2);

        System.out.println("--------");
        System.out.println("a = " + a.getValue());
        System.out.println("b = " + b.getValue());

        System.out.println("a + b = " + a.add(b).getValue());
        System.out.println("a - b = " + a.subtract(b).getValue());
        System.out.println("b - a = " + b.subtract(a).getValue());
    }

    static void testMul(String s1, String s2) {
        AFloat a = new AFloat(s1);
        AFloat b = new AFloat(s2);

        System.out.println("--------");
        System.out.println("a = " + a.getValue());
        System.out.println("b = " + b.getValue());

        System.out.println("a * b = " + a.multiply(b).getValue());
        System.out.println("b * a = " + b.multiply(a).getValue());
    }

    static void testDiv(String s1, String s2) {
        AFloat a = new AFloat(s1);
        AFloat b = new AFloat(s2);

        System.out.println("--------");
        System.out.println("a = " + a.getValue());
        System.out.println("b = " + b.getValue());

        System.out.println("a / b = " + a.divide(b).getValue());
    }
}
