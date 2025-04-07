package arbitraryarithmetic;

public class AInteger {
    private String value;

    AInteger(){
        this.value = "0";
    }

    public AInteger(String s){
        this.value = s;
    }

    public AInteger(AInteger other){
        this.value = other.value;
    }

    public static AInteger parse(String s){
        return new AInteger(s);
    }

    public String getValue(){
        return this.value;
    }

    private boolean isNeg(){
        return this.value.charAt(0) == '-';
    }

    private AInteger makePos(){
        return AInteger.parse(this.value.substring(1));
    }

    private String trimZeroes(String s){
        if(s.equals("0") || s.equals("-0")){
            return "0";
        }
        else{
            int index=0;
            for(;s.charAt(index)=='0';index++);
            return s.substring(index);
        }
    }
    
    private boolean isZero(){
        return (this.value.equals("0") || this.value.equals("-0"));
    }

    private boolean isGreater(AInteger other){
        String x1 = this.value;
        String x2 = other.value;

        if(x1.length() > x2.length()){
            return true;
        }
        else if(x1.length() < x2.length()){
            return false;
        }
        else{
            for(int i=0; i<x1.length();i++){
                if(x1.charAt(i) > x2.charAt(i)){
                    return true;
                }
                else if(x2.charAt(i) > x1.charAt(i)){
                    return false;
                }
            }
            return false;
        }
    }

    private boolean firstIsGreater(String x1, String x2){
        if(x1.length() > x2.length()){
            return true;
        }
        else if(x1.length() < x2.length()){
            return false;
        }
        else{
            for(int i=0; i<x1.length();i++){
                if(x1.charAt(i) > x2.charAt(i)){
                    return true;
                }
                else if(x2.charAt(i) > x1.charAt(i)){
                    return false;
                }
            }
            return false;
        }
    }

    private String addString(String x1, String x2){
        String result = "";
        int n1 = x1.length(), n2 = x2.length();
        int carry = 0;

        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes+="0";
        }

        if(n1<n2){
            x1 = zeroes + x1;
            n1 += diff;
        }
        else{
            x2 = zeroes + x2;
            n2 += diff;
        }

        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;

            sum%=10;
            result = Integer.toString(sum) + result;
        }

        if(carry > 0){
            result = Integer.toString(carry) + result;
        }

        return trimZeroes(result);
    }

    private String subtractString(String x1, String x2){
        if(x1.equals(x2))
            return "0";
        String result = "";

        int n1 = x1.length();
        int n2 = x2.length();

        int diff = n1-n2;
        String zeroes = "";

        for(int i=0;i<diff;i++){
            zeroes += "0";
        }

        x2 = zeroes + x2;
        n2 = n1;

        boolean carrying = false;

        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            if(carrying){
                a1 -= 1;
                carrying = false;
            }

            if(a1<a2 && i>0){
                carrying = true;
                a1 += 10;
            }

            int d = a1 - a2;
            result = Integer.toString(d)+result;
        }

        return trimZeroes(result);
    }

    private String multiplyString(String x1, String x2){
        String result = "0";
        for(int i=0;i<x2.length();i++){
            int a2 = x2.charAt(i) - '0';
            String currProduct = "";
            for(int j=x2.length()-1;j>i;j--){
                currProduct += "0";
            }
            int carry = 0;
            for(int j = x1.length()-1; j>=0; j--){
                int a1 = x1.charAt(j) - '0';
                int product = a1 * a2 + carry;

                carry = product/10;
                currProduct = Integer.toString(product%10) + currProduct;
            }

            if(carry > 0){
                currProduct = Integer.toString(carry) + currProduct;
            }

            result = addString(result, currProduct);
        }

        return trimZeroes(result);
    }
   
    private String divideString(String x1, String x2){
        String result = "";
        String currDividend = "";
        for(int i=0;i<x1.length();i++){
            currDividend += x1.charAt(i);
 
            int q = 0;
            for(;firstIsGreater(currDividend, x2) || currDividend.equals(x2);q++){
                currDividend = subtractString(currDividend, x2);
            }
            result += Integer.toString(q);
            
        }

        return trimZeroes(result);
    }
    
    public AInteger add(AInteger other){
        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");
        }
        if(!(other.isNeg() || this.isNeg())){
            return AInteger.parse(addString(this.value, other.value));
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());
        }
        else{
            return AInteger.parse("-"+this.makePos().add(other.makePos()).value);
        }

    }

    public AInteger subtract(AInteger other){
        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");
        }
        if(!(other.isNeg() || this.isNeg())){
            if(this.isGreater(other)){
                return AInteger.parse(subtractString(this.value, other.value));
            }
            else{
                return AInteger.parse("-"+subtractString(other.value, this.value));
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AInteger.parse("-"+this.makePos().add(other).value);
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());
        }
        else{
            return other.makePos().subtract(this.makePos());
        }
    }

    public AInteger multiply(AInteger other){
        if(this.isZero() || other.isZero()){
            return AInteger.parse("0");
        }
        if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){
            if(!this.isNeg()){
                return AInteger.parse(multiplyString(this.value, other.value));
            }
            else{
                return AInteger.parse(multiplyString(this.makePos().value, other.makePos().value));
            }
        }
        else{
            if(this.isNeg()){
                return AInteger.parse("-" + other.multiply(this.makePos()).value);
            }
            else{
                return AInteger.parse("-" + this.multiply(other.makePos()).value);
            }
        }
    }

    public AInteger divide(AInteger other){
        if(other.value.equals("0")){
            System.out.println("Error: Division by zero!");
            return null;
        }
        else if(this.value.equals("0")){
            return AInteger.parse("0");
        }
        else{
            if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){
                if(!this.isNeg()){
                    return AInteger.parse(divideString(this.value, other.value));
                }
                else{
                    return AInteger.parse(divideString(this.makePos().value, other.makePos().value));
                }
            }
            else{
                if(this.isNeg()){
                    return AInteger.parse("-" + this.makePos().divide(other).value);
                }
                else{
                    return AInteger.parse("-" + this.divide(other.makePos()).value);
                }
            }
        }
    }

    public static void main(String[] args){
        String op = args[0];
        AInteger x1 = AInteger.parse(args[1]);
        AInteger x2 = AInteger.parse(args[2]);

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

        System.out.println(y.value);
    }
}