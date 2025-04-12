package arbitraryarithmetic;

import java.util.InputMismatchException;

public class AInteger {

    /*
     * AInteger
     * 
     * This class aims to achieve infinitely precise integer operations, capable of handling
     * an arbitrary amount of digits much bigger than the bounds of the usual int/long data
     * types in Java. This is achieved through storing the integers as Strings, and then performing
     * the operations digit by digit to obtain the result String. Since there is no limit on the size
     * of a String, we can store as many digits as we want.
     * 
     */

    private String value;   // Private String which stores the value of the Integer assigned to this object.

    public AInteger(){
        // Default constructor that initialises the object with the value 0.

        this.value = "0";
    }

    public AInteger(String s){
        // Alternative constructor that assigns a String value passed to it. Note that the String must be an Integer.

        s = trimZeroes(s);      // TrimZeroes trims any leading zeroes in the String.
        this.value = s;
    }

    public AInteger(AInteger other){
        // Copy constructor that initiliases the value of this object to the value of the other object.

        this.value = other.value;
    }

    public static AInteger parse(String s){
        // A static function that creates an instance of AInteger from a given String.

        // Check if the String is a number or else throw an exception.
        if(s != null && s.matches("\\d+")){
            return new AInteger(s);
        }
        else{
            throw new InputMismatchException("Error: Expected input to be an integer!");
        }
    }

    public String getValue(){
        // Accessor function to return the value of the object.

        return this.value;
    }

    private boolean isNeg(){
        // A function that check whether the current value is negative.

        return this.value.charAt(0) == '-';
    }

    private AInteger makePos(){
        // A function that takes in a negative integer String and removes the leading '-' to make it positive.

        return AInteger.parse(this.value.substring(1));
    }

    private String trimZeroes(String s){
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
    
    private boolean isZero(){
        // Checks if the value of the Object is zero or not.

        return (this.value.equals("0") || this.value.equals("-0"));
    }

    private boolean firstIsGreater(String x1, String x2){
        // Checks if the first integer String is greater than the second one.

        // Checking number of digits - Greater number will have greater number of digits.
        if(x1.length() > x2.length()){
            return true;
        }
        else if(x1.length() < x2.length()){
            return false;
        }

        // Equal number digits case - check digit by digit.
        else{
            for(int i=0; i<x1.length();i++){
                if(x1.charAt(i) > x2.charAt(i)){
                    return true;
                }
                else if(x2.charAt(i) > x1.charAt(i)){
                    return false;
                }
            }
            return false;       // Returns false even if the Strings are equal.
        }
    }

    private String addString(String x1, String x2){
        // This function adds to non-negative Strings to give a non-negative sum.

        String result = "";     // Empty result String - we will prepend digits to it one by one.

        // Store the lengths of the two Strings.
        int n1 = x1.length();
        int n2 = x2.length();

        int carry = 0;      // Carry variable.

        // If the numbers have a different number of digits, pad the smaller one on the left with zeroes.
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

        // Digit-wise addition of the numbers and prepending the resulting units digit to the result.
        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;

            sum%=10;
            result = Integer.toString(sum) + result;
        }

        // Prepend the last carry if it is non-zero.
        if(carry > 0){
            result = Integer.toString(carry) + result;
        }

        // Remove leading zeroes from the final result.
        return trimZeroes(result);
    }

    private String subtractString(String x1, String x2){
        // Subtracts x1 and x2 where x1 >= x2 to give a non-negative difference.


        // Checking for equality and directly returning 0.
        if(x1.equals(x2))
            return "0";

        String result = "";     // String which will store the final result.

        // Compare the lengths of the numbers, the one with lesser digits will be padded with zeroes from the left (which is the second number here).
        int n1 = x1.length();
        int n2 = x2.length();

        int diff = n1-n2;
        String zeroes = "";

        for(int i=0;i<diff;i++){
            zeroes += "0";
        }

        x2 = zeroes + x2;
        n2 = n1;

        boolean given = false;       // Boolean to check whether the previous digit has borrowed from the current digit.

        // Digit-wise subtraction. Checks for borrowing as well, and each resulting digit is prepended to the result.
        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            if(given){      // If this digit has given to the previous digit, decrement it by one.
                a1 -= 1;
                given = false;
            }

            if(a1<a2 && i>0){       // Borrowing condition.
                given = true;
                a1 += 10;
            }

            int d = a1 - a2;
            result = Integer.toString(d)+result;
        }

        // Return the final result String with any leading zeroes removed.
        return trimZeroes(result);
    }

    private String multiplyString(String x1, String x2){
        // Multiplies two non-negative x1 and x2 to give a non-negative product.

        String result = "0";        // Initial product is zero.

        for(int i=x2.length()-1;i>=0;i--){

            int a2 = x2.charAt(i) - '0';
            String currProduct = "";        // Current Product, will be added to the result at the end.
            
            // Multiply each digit of the first number with the i'th digit of the second. Store the result in currProduct.
            int carry = 0;
            for(int j = x1.length()-1; j>=0; j--){
                int a1 = x1.charAt(j) - '0';
                int product = a1 * a2 + carry;

                carry = product/10;
                currProduct = Integer.toString(product%10) + currProduct;
            }

            // If carry is non-zero, prepend that as well.
            if(carry > 0){
                currProduct = Integer.toString(carry) + currProduct;
            }

            // Add zeroes on the right of the product to ensure it has the correct power of 10.
            for(int j=0;j<x2.length()-1-i;j++){
                currProduct += "0";
            }

            // Add the final product with the result.
            result = addString(result, currProduct);
        }

        // Return the result without leading zeroes.
        return trimZeroes(result);
    }
   
    private String divideString(String x1, String x2){
        // Divides two non-negative x1 and x2 to give the non-negative integer quotient.

        String result = "";            // String to store the quotient.
        String currDividend = "";      // String which stores the current dividend.

        // Division Loop.
        for(int i=0;i<x1.length();i++){

            currDividend += x1.charAt(i);       // Append the next digit to currDividend.
            currDividend = trimZeroes(currDividend);    // Trim any leading zeroes which may have come from the previous iteration.
            
            // Keep subtracting x2 from currDividend until it becomes less than x2.
            int q = 0;
            while(firstIsGreater(currDividend, x2) || currDividend.equals(x2)){
                currDividend = subtractString(currDividend, x2);
                currDividend = trimZeroes(currDividend);
                q++;
            }

            // Update the quotient.
            result += Integer.toString(q);
        }

        // Trim the leading zeroes and return the result.
        return trimZeroes(result);
    }
    
    public AInteger add(AInteger other){
        // Adds two AInteger objects to give another AInteger sum.

        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");     // Zero case - return 0.
        }
        if(!(other.isNeg() || this.isNeg())){
            return AInteger.parse(addString(this.value, other.value));      // Both positive case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());      // (-a) + b case where a,b >= 0.
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());      // a + (-b) case here.
        }
        else{
            return AInteger.parse("-"+this.makePos().add(other.makePos()).value);   // (-a) + (-b) case.
        }

    }

    public AInteger subtract(AInteger other){
        // Subtracts two AInteger objects to give their AInteger difference.

        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");     // 0 case where a = b = 0.
        }
        if(!(other.isNeg() || this.isNeg())){           // a - b case.
            
            if(firstIsGreater(this.value, other.value)){
                return AInteger.parse(subtractString(this.value, other.value));     // if a > b return a - b.
            }
            else{
                return AInteger.parse("-"+subtractString(other.value, this.value));     // else return -(b - a).
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AInteger.parse("-"+this.makePos().add(other).value);     // (-a) - b case.
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());           // a - (-b) case.
        }
        else{
            return other.makePos().subtract(this.makePos());        // (-a) - (-b) case.
        }
    }

    public AInteger multiply(AInteger other){
        // Multiplies two AIntegers to give their AInteger product.

        if(this.isZero() || other.isZero()){
            return AInteger.parse("0");     // If either a or b are 0, then return 0.
        }
        if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){    // If both have the same sign, return the positive product.

            if(!this.isNeg()){
                return AInteger.parse(multiplyString(this.value, other.value));     // a * b case.
            }
            else{
                return AInteger.parse(multiplyString(this.makePos().value, other.makePos().value));     // (-a) * (-b) case.
            }
        }
        else{                                                                       // Return the negative product if signs differ.
            
            if(this.isNeg()){
                return AInteger.parse("-" + other.multiply(this.makePos()).value);      // (-a) * b case.
            }
            else{
                return AInteger.parse("-" + this.multiply(other.makePos()).value);      // a * (-b) case.
            }
        }
    }

    public AInteger divide(AInteger other){
        // Divides two AIntegers to give their AInteger quotient.

        if(other.value.equals("0")){
            throw new ArithmeticException("Error: Division by zero.");      // If b is 0, then throw an exception.
        }
        else if(this.value.equals("0")){
            return AInteger.parse("0");                             // If a is 0, then return 0.
        }
        else{

            if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){    // If both have the same sign, return the positive coefficient.
                
                if(!this.isNeg()){
                    return AInteger.parse(divideString(this.value, other.value));   // a / b case.
                }
                else{
                    return AInteger.parse(divideString(this.makePos().value, other.makePos().value));   // (-a) / (-b) case.
                }
            }
            else{                   // Negative quotient if signs differ.

                if(this.isNeg()){
                    return AInteger.parse("-" + this.makePos().divide(other).value);        // (-a) / b case.
                }
                else{
                    return AInteger.parse("-" + this.divide(other.makePos()).value);        // a / (-b) case.
                }
            }
        }
    }
}