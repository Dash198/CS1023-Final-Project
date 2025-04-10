import arbitraryarithmetic.*;

public class TestAInt {
    public static void main(String[] args) {
        // Addition tests
        test("123", "456", "+", "579");
        test("-123", "456", "+", "333");
        test("123", "-456", "+", "-333");
        test("-123", "-456", "+", "-579");

        // Subtraction tests
        test("456", "123", "-", "333");
        test("123", "456", "-", "-333");
        test("-123", "456", "-", "-579");
        test("123", "-456", "-", "579");
        test("-123", "-456", "-", "333");
        test("-456", "-123", "-", "-333");

        // Multiplication tests
        test("123", "0", "*", "0");
        test("0", "456", "*", "0");
        test("123", "1", "*", "123");
        test("-123", "1", "*", "-123");
        test("-123", "-456", "*", "56088");
        test("123", "456", "*", "56088");

        // Division tests
        test("56088", "456", "/", "123");
        test("123", "1", "/", "123");
        test("123", "-1", "/", "-123");
        test("-56088", "-456", "/", "123");
        test("5", "2", "/", "2");
        test("9", "3", "/", "3");
        test("0", "123", "/", "0");

        // Edge / corner cases
        test("1000000000000000000000000", "1", "+", "1000000000000000000000001");
        test("999999999999999999999999", "1", "+", "1000000000000000000000000");
        test("1000000000000000000000000", "999999999999999999999999", "-", "1");
        test("-999999999999999999999999", "-1", "+", "-1000000000000000000000000");

        // Sign edge cases
        test("0", "0", "+", "0");
        test("0", "0", "-", "0");
        test("0", "123", "-", "-123");
        test("123", "0", "-", "123");
    }

    private static void test(String a, String b, String op, String expected) {
        AInteger A = new AInteger(a);
        AInteger B = new AInteger(b);
        AInteger result;

        switch (op) {
            case "+": result = A.add(B); break;
            case "-": result = A.subtract(B); break;
            case "*": result = A.multiply(B); break;
            case "/": result = A.divide(B); break;
            default:
                System.out.println("Invalid operation: " + op);
                return;
        }

        String resStr = result.getValue().toString();
        boolean passed = resStr.equals(expected);

        System.out.printf("Test: %s %s %s = %s | %s\n", a, op, b, resStr, passed ? "PASS" : "FAIL (expected " + expected + ")");
    }
}
