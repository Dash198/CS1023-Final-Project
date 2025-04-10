package arbitraryarithmetic;

public class AFloat {

      /*
     * AFloat
     * 
     * This class aims to achieve infinitely precise float operations, capable of handling
     * an arbitrary amount of digits much bigger than the bounds of the usual float/double data
     * types in Java. This is achieved through storing the numbers as Strings, and then performing
     * the operations digit by digit to obtain the result String. Since there is no limit on the size
     * of a String, we can store as many digits as we want.
     * 
     */

    private String value;       // Private String to store the value of the current object.
    private int divPrecision = 1000;        // Store the max number of decimal places we want to go till during division.

    public AFloat(){
        // Default constructor which initiliases the value of the object to 0.0

        this.value = "0.0";
    }

    public AFloat(String s){
        // This constructor initialises the value in s to this object. The String s must be an integer or a decimal.

        s = addZeroes(s);       // Adds a '.0' if s is an integer.
        s = trimZeroes(s);      // Trim any leading zeroes from the left or any trailing zeroes to the right.
        this.value = addZeroes(s);
    }

    public AFloat(AFloat other){
        // Copy constructor that initiliases the value of this object to the value of the other object.

        this.value = other.value;
    }

    private String addZeroes(String val){
        // Adds a '.0' to an integer String.

        if(!(val.contains("."))){
            val += ".0";
        }
        return val;
    }

    public static AFloat parse(String s){
        // This function returns an instance of AFloat with the value s.

        return new AFloat(s);
    }

    public String getValue(){
        // Accessor function to return the value of the object.

        return this.value;
    }

    private boolean isGreater(AFloat other){
        // Function that determines whether the value of this object is greater than the other object.

        // Store the integer and fractional parts separately.
        String[] x1 = this.value.split("\\.");
        String[] x2 = other.value.split("\\.");

        // Comparing the lengths of the integer parts, longer one is greater.
        if(x1[0].length()>x2[0].length()){
            return true;
        }
        else if(x1[0].length()<x2[0].length()){
            return false;
        }

        // Digit-wise comparison of the integer parts.
        for(int i = x1[0].length()-1;i>=0;i--){
            if(x1[0].charAt(i) > x2[0].charAt(i)){
                return true;
            }
            else if(x2[0].charAt(i) > x1[0].charAt(i)){
                return false;
            }
        }

        // If both the fractional parts are of different lengths, we will check the digits uptil the end of the smaller one.
        int min = (x1[1].length()<x2[1].length()?x1[1].length():x2[1].length());
        for(int i = 0;i<min;i++){
            if(x1[1].charAt(i) > x2[1].charAt(i)){
                return true;
            }
            else if(x2[1].charAt(i) > x1[1].charAt(i)){
                return false;
            }
        }
        
        // If the digits are equal till then, we will treat the number with greater number of fractional digits as the larger one. Equal case is treated as false.
        return (min==x1[1].length()?false:true);
    }

    private String trimIntZeroes(String s){
        // Function to trim the leading zeroes in an integer.

        if(s.equals("0") || s.equals("-0")){
            return "0";     // Return a zero directly if it's a simple 0.
        }
        else{
            // Check for negativity.
            boolean isNeg = false;
            if(s.charAt(0)=='-'){
                isNeg = true;
                s = s.substring(1);     // Start trimming from the next character.
            }

            int index=0;        // Variable to store the index of the first non-zero digit in the String from the left.
            while(index < s.length() && s.charAt(index)=='0'){
                index++;        // Keep incrementing the index until the end of the String or a non-zero digit is reached.
            }
            if(index==s.length()){
                return "0";     // If the end of the String is reached then the entire number must be zero.
            }
            else{
                return (isNeg?"-":"") + s.substring(index);     // Return the number starting from the first non-zero digit with appropriate signs.
            }
        }
    }

    private String trimZeroes(String s){
        // Function to trim the leading zeroes from the integer part and trailing zeroes from the fractional part in a float.
        
        String[] x = s.split("\\.");        // Store the integer and fractional parts separately,
        String result = "";

        boolean isNeg = false;  // Boolean to check if the number is negative.

        // Trim leading zeroes from the integer part (Not using trimIntZeros directly as it will return 0 for a number of the type -0.x).
        if(x[0].equals("0")){
            result = "0";           // Zero case.
        }
        else{
            // Check for negativity.
            if(x[0].charAt(0)=='-'){
                isNeg = true;
                x[0] = x[0].substring(1);       // Start trimming from the next character.
            }

            // Again, remove the leading zeroes.
            int index=0;
            while(index < x[0].length() && x[0].charAt(index)=='0'){
                index++;
            }
            if(index==x[0].length()){
                result = "0";
            }
            else{
                result = x[0].substring(index);
            }
        }

        result += ".";      // Add the decimal point.

        // Removing trailing zeroes from the fractional part.
        // Pretty much the same logic as removing leading zeroes in the integer part, but here we go right to left instead of left to right.
        if(x[1].equals("0")){
            result += "0";
        }
        else{
            int index=x[1].length()-1;
            while(index >=0 && x[1].charAt(index)=='0'){
                index--;
            }
            if(index==-1){
                result += "0";
            }
            else{
                result += x[1].substring(0,index+1);
            }
        }
        return (isNeg && !result.equals("0.0")?"-":"") + result;        // Negativity check.
    }

    private boolean isZero(){
        // Function to check if the value of the current object is zero.

        String s = trimZeroes(this.value);
        return (s.equals("0.0") || s.equals("-0.0"));
    }

    private boolean isNeg(){
        // Function to check if the value of the curent object is negative.

        return this.value.charAt(0) == '-';
    }

    private AFloat makePos(){
        // Function which removes the leading '-' from the string and returns a new AFloat.

        return AFloat.parse(this.value.substring(1));
    }

    private boolean firstIntIsGreater(String x1, String x2){
        // Another function to compare two integers lengthwise first and then digit-wise.

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

    private String addIntString(String x1, String x2){
        // Adds two non-negative INTEGERS x1 and x2 and returns their non-negative sum.

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

        return trimIntZeroes(result);
    }

    private String addString(String x1, String x2){
        // Adds two non-negative decimals x1 and x2 and returns their non-negative sum.

        String[] a = x1.split("\\.");
        String[] b = x2.split("\\.");

        String result = "";

        String o1 = a[1];
        String o2 = b[1];

        int n1 = o1.length();
        int n2 = o2.length();

        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 += zeroes;
            n1 = n2;
        }
        else{
            o2 += zeroes;
            n2 = n1;
        }

        int carry = 0;
        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;
            result = Integer.toString(sum%10) + result;
        }

        result = "." + result;

        o1 = a[0];
        o2 = b[0];

        n1 = o1.length();
        n2 = o2.length();

        diff = (n1<n2?n2-n1:n1-n2);
        zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 = zeroes + o1;
            n1 = n2;
        }
        else{
            o2 = zeroes + o2;
            n2 = n1;
        }

        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;
            result = Integer.toString(sum%10) + result;
        }

        if(carry > 0){
            result = Integer.toString(carry) + result;
        }

        return trimZeroes(result);
    }

    private String subtractIntString(String x1, String x2){
        // Subtracts two non-negative INTEGERS x1, x2 where x1 > x2 and returns their difference.

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

        boolean given = false;

        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            if(given){
                a1 -= 1;
                given = false;
            }

            if(a1<a2 && i>0){
                given = true;
                a1 += 10;
            }

            int d = a1 - a2;
            result = Integer.toString(d)+result;
        }

        return trimIntZeroes(result);
    }

    public String subtractString(String a, String b){
        // Subtracts two non-negative decimals a, b, where a > b and returns their non-negative difference.

        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        String result = "";

        String o1 = x1[1];
        String o2 = x2[1];

        int n1 = o1.length();
        int n2 = o2.length();

        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";            
        }
        if(n1<n2){
            o1 += zeroes;
            n1 = n2;
        }
        else{
            o2 += zeroes;
            n2 = n1;
        }

        boolean given = false;
        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            if(given){
                a1--;
                given = false;
            }

            if(a1<a2){
                given = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

        result = "." + result;

        o1 = x1[0];
        o2 = x2[0];

        n1 = o1.length();
        n2 = o2.length();

        diff = (n1<n2?n2-n1:n1-n2);
        zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 = zeroes + o1;
            n1 = n2;
        }
        else{
            o2 = zeroes + o2;
            n2 = n1;
        }

        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            if(given){
                a1--;
                given = false;
            }

            if(a1<a2){
                given = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

        return trimZeroes(result);
    }

    private String multiplyString(String a, String b){
        // Multiplies two non-negative decimals a, b and returns their product.

        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        int decimalPoints = x1[1].length() + x2[1].length();

        String o1 = x1[0] + x1[1];
        String o2 = x2[0] + x2[1];

        int n1 = o1.length();
        int n2 = o2.length();
        String result = "0";

        for(int i=n2-1;i>=0;i--){
            int a2 = o2.charAt(i) - '0';
            String currProduct = "";
            
            int carry = 0;
            for(int j = n1-1; j>=0; j--){
                int a1 = o1.charAt(j) - '0';
                int product = a1 * a2 + carry;

                carry = product/10;
                currProduct = Integer.toString(product%10) + currProduct;
            }

            if(carry > 0){
                currProduct = Integer.toString(carry) + currProduct;
            }

            for(int j=0;j<n2-1-i;j++){
                currProduct += "0";
            }

            result = addIntString(result, currProduct);
        }

        while(result.length()<=decimalPoints){
            result = "0" + result;
        }
        int n = result.length();
        result = result.substring(0,n-decimalPoints) + "." + result.substring(n-decimalPoints);
        return trimZeroes(result);
    }

    private String divideString(String a, String b){
        // Divides two positive decimals a and b, and returns the result with an arbitrary precision (1000 decimal places for now).

        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        int diff = (x1[1].length()<x2[1].length()?x2[1].length()-x1[1].length():x1[1].length()-x2[1].length());

        String o1 = x1[0] + x1[1];
        String o2 = x2[0] + x2[1];

        o1 = trimIntZeroes(o1);
        o2 = trimIntZeroes(o2);

        int n1 = o1.length();

        String result = "0";
        String currDividend = "";

        for(int i=0;i<n1;i++){
            currDividend += o1.charAt(i);
            currDividend = trimIntZeroes(currDividend);
            int q = 0;
            while(firstIntIsGreater(currDividend, o2) || currDividend.equals(o2)){
                currDividend = subtractIntString(currDividend, o2);
                currDividend = trimIntZeroes(currDividend);
                q++;
            }
            result += Integer.toString(q);
        }

        int index = 0;
        for(; index<this.divPrecision-diff && !currDividend.equals("0");index++){
            currDividend += "0";
            currDividend = trimIntZeroes(currDividend);
            int q = 0;
            while(firstIntIsGreater(currDividend, o2) || currDividend.equals(o2)){
                currDividend = subtractIntString(currDividend, o2);
                currDividend = trimIntZeroes(currDividend);
                q++;
            }
            result += Integer.toString(q);   
        }

        int n = result.length();
        int decimalPoints = index + (x1[1].length() > x2[1].length()?diff:-diff);
        
        if(decimalPoints>0){
            result = result.substring(0,n-decimalPoints) + "." + result.substring(n-decimalPoints);
            result = trimZeroes(result);
        }
        else{
            while(decimalPoints < 0){
                result += "0";
                decimalPoints++;
            }
        }
        
        
        return result;
    }

    public AFloat add(AFloat other){
        if(this.isZero() && other.isZero()){
            return new AFloat();
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(addString(this.value, other.value));
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());
        }
        else{
            return AFloat.parse("-" + this.makePos().add(other.makePos()).value);
        }
    }

    public AFloat subtract(AFloat other){
        if(this.isZero() && other.isZero()){
            return new AFloat();
        }
        else if(!(this.isNeg() || other.isNeg())){
            if(this.isGreater(other)){
                return AFloat.parse(subtractString(this.value, other.value));
            }
            else{
                return AFloat.parse("-" + subtractString(other.value, this.value));
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().add(other).value);
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());
        }
        else{
            return other.makePos().subtract(this.makePos());
        }
    
    }

    public AFloat multiply(AFloat other){
        if(this.isZero() || other.isZero()){
            return new AFloat();
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(multiplyString(this.value, other.value));
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().multiply(other).value);
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.multiply(other.makePos()).value);
        }
        else{
            return this.makePos().multiply(other.makePos());
        }
    }

    public AFloat divide(AFloat other){
        if(this.isZero() || other.isZero()){
            if(other.isZero()){
                throw new ArithmeticException("Error: Division by zero.");
            }
            else{
                return new AFloat();
            }
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(divideString(this.value, other.value));
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().divide(other).value);
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.divide(other.makePos()).value);
        }
        else{
            return this.makePos().divide(other.makePos());
        }
    }
}
