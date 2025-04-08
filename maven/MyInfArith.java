import target.aarithmetic.*;

public class MyInfArith {
    public static void main(String[] args){
        String op = args[1];
        AInteger x1 = AInteger.parse(args[2]);
        AInteger x2 = AInteger.parse(args[3]);

        AInteger y;

        if(op.equals("add")){
            y = x1.add(x2);
        }
        else if(op.equals("sub")){
            y = x1.subtract(x2);
        }
        else if(op.equals("mul")){
            y = x1.multiply(x2);
        }
        else if(op.equals("div")){
            y = x1.divide(x2);
        }
        else{
            y = new AInteger();
        }

        System.out.println(y.getValue());
    }
}
