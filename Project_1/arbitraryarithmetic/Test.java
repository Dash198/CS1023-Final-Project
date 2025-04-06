package arbitraryarithmetic;

public class Test {
    public static void main(String[] args) {
        AInteger a = new AInteger(args[0]);
        AInteger b = new AInteger(args[2]);
        AInteger c;

        if(args[1].equals("add")){
            c = a.add(b);
        }
        else if(args[1].equals("sub")){
            c = a.subtract(b);
        }
        else if(args[1].equals("mul")){
            c = a.multiply(b);
        }
        else{
            c = a.divide(b);
        }

        System.out.println("Expected: -5, Actual: " + c.getValue());

        if (c.getValue().equals("17804979")) {
            System.out.println("Test passed ✅");
        } else {
            System.out.println("Test failed ❌");
        }
    }
}
