package raw_source.arbitraryarithmetic.extras;
import raw_source.arbitraryarithmetic.*;

public class TestAFloat {
    public static void main(String[] args) {
                // repeating decimal approximations
                test("1.0", "3.0", "/", "0.3333333333");  // will depend on your precision limit
                test("2.0", "3.0", "/", "0.6666666666");
        
                // alignment tests
                test("0.0000000000001", "0.0000000000001", "+", "0.0000000000002");
                test("999999999999.999999999999", "0.000000000001", "+", "1000000000000.0");
        
                // large number edge cases
                test("999999999999.999", "1.001", "*", "1000999999999.998999");
                test("0.000000000001", "0.000000000001", "*", "0.000000000000000000000001");
        
                // test precision and cleanup
                test("0.1000000000000", "0.0000000000000", "+", "0.1");
                test("0.500000000000", "0.500000000000", "-", "0.0");
        
                // multiple carry operations
                test("999.999", "0.001", "+", "1000.0");
                test("1000.0", "0.001", "-", "999.999");
        
                // borrowing and carry underflow
                test("1000.000", "0.001", "-", "999.999");
                test("1000.000", "999.999", "-", "0.001");
        
                // multiplying by negative one
                test("123456.789", "-1.0", "*", "-123456.789");
                test("-123456.789", "-1.0", "*", "123456.789");
        
                // division with lots of zero-padding
                test("1.000000", "1000000.0", "/", "0.000001");
        
                // zero edge cases
                test("-0.0", "0.0", "+", "0.0");
                test("-0.0", "-0.0", "-", "0.0");
                test("0.0", "0.1", "*", "0.0");
                test("0.0", "0.1", "/", "0.0");
        
                // subtracting nearly equal floats
                test("1.0000000000001", "1.0000000000000", "-", "0.0000000000001");
                test("1000.0000001", "999.9999999", "-", "0.0000002");
    }

    public static void test(String a, String b, String op, String expected) {
        AFloat af1 = new AFloat(a);
        AFloat af2 = new AFloat(b);
        AFloat result = null;

        switch (op) {
            case "+": result = af1.add(af2); break;
            case "-": result = af1.subtract(af2); break;
            case "*": result = af1.multiply(af2); break;
            case "/": result = af1.divide(af2); break;
            default: System.out.println("Unknown operator " + op); return;
        }

        String actual = result.getValue().toString();
        if (!actual.equals(expected)) {
            System.out.println("❌ Test failed: " + a + " " + op + " " + b);
            System.out.println("   Expected: " + expected + " but got: " + actual);
        } else {
            System.out.println("✅ " + a + " " + op + " " + b + " = " + actual);
        }
    }
}
