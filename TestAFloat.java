import arbitraryarithmetic.*;

public class TestAFloat {
    public static void main(String[] args) {
        testAdd("1.5", "2.5", "4.0");
        testAdd("-1.12", "6.88", "5.76");
        testAdd("0.0", "123.456", "123.456");
        testAdd("999.99", "0.01", "1000.0");
        testAdd("-5.5", "-4.5", "-10.0");
        testAdd("3.75", "-1.25", "2.5");
        testAdd("-3.75", "1.25", "-2.5");
        testAdd("1000.000", "-1000.000", "0.0");

        testSubtract("5.0", "3.0", "2.0");
        testSubtract("3.0", "5.0", "-2.0");
        testSubtract("0.0", "0.0", "0.0");
        testSubtract("123.456", "23.456", "100.0");
        testSubtract("-5.5", "-4.5", "-1.0");
        testSubtract("-4.5", "-5.5", "1.0");
        testSubtract("1.25", "-3.75", "5.0");
        testSubtract("-1.25", "3.75", "-5.0");
    }

    private static void testAdd(String a, String b, String expected) {
        AFloat x = new AFloat(a);
        AFloat y = new AFloat(b);
        AFloat result = x.add(y);
        String msg = String.format("add(%s, %s) = %s [expected %s]",
            a, b, result.getValue(), expected);
        assert result.getValue().equals(expected) : "FAILED: " + msg;
        System.out.println("PASSED: " + msg);
    }

    private static void testSubtract(String a, String b, String expected) {
        AFloat x = new AFloat(a);
        AFloat y = new AFloat(b);
        AFloat result = x.subtract(y);
        String msg = String.format("subtract(%s, %s) = %s [expected %s]",
            a, b, result.getValue(), expected);
        assert result.getValue().equals(expected) : "FAILED: " + msg;
        System.out.println("PASSED: " + msg);
    }
}
